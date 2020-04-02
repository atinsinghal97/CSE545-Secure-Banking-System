package web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import constants.Constants;

import org.hibernate.Transaction;


import database.SessionManager;


import forms.Search;
import forms.SearchForm;
import model.Account;
import model.User;


public class AccountServicesImpl {
	public SearchForm getAccounts(String accNumber) {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals(Constants.ADMIN) || grantedAuthority.getAuthority().equals(Constants.TIER2)||grantedAuthority.getAuthority().equals(Constants.TIER1)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return null;
			}
		}
		
		Session s = SessionManager.getSession("");
		List<Account> account=null;
		
		try {
			account=s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
					.setParameter("accountNumber", accNumber).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		
		SearchForm searchForm = new SearchForm();
		//ArrayList<Search> search=new ArrayList<>();
		List<Search> search = new ArrayList<Search>();
		for(Account temp : account )
		{
			Boolean status=false;
			if(temp.getStatus()==1)
				status=true;
			Search tempSearch=new Search(temp.getAccountNumber(),temp.getCurrentBalance()+"",status);	
			if(((currentSessionUser.equals(Constants.TIER1)||currentSessionUser.equals(Constants.TIER2))&&temp.getUser().getRole().equals(Constants.CUSTOMER)) ||(currentSessionUser.equals(Constants.ADMIN)&&(temp.getUser().getRole().equals(Constants.TIER1)||temp.getUser().getRole().equals(Constants.TIER2))) )
			search.add(tempSearch);		
		}	
		searchForm.setSearchs(search);
		return searchForm;		
		
	}
	
	public Boolean deleteAccounts(String accNumber) {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return null;
			}
		}

		Session s = SessionManager.getSession("");
		Transaction tx = null;
		try {
			
			List<Account> account=null;
			account = s.createQuery("FROM Account WHERE account_number = :accountNumber AND status=1", Account.class)
					.setParameter("accountNumber", accNumber).getResultList();
			if(account.size() == 0) {
				s.close();
				return false;
			}

			tx = s.beginTransaction();
			for(Account temp : account ) {
				if((currentSessionUser.equals("tier2")&&temp.getUser().getRole().equals("customer")) ||(currentSessionUser.equals("admin")&&(temp.getUser().getRole().equals("tier1")||temp.getUser().getRole().equals("tier2"))) )
				{
					temp.setStatus(3);
					s.saveOrUpdate(temp);
				}
			}
			
			if (tx.isActive()) tx.commit();
			
		} catch (Exception e) {
			if (tx != null && tx.isActive()) tx.rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}

		return true;
		
	}
	
	public SearchForm getAllPendingAccounts() {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return null;
			}
		}

		List<Account> account=null;
		Session s = SessionManager.getSession("");
		try {
			account=s.createQuery("FROM Account where status=0", Account.class)
					 .getResultList();
		} catch (Exception e) {
			// Pass
		} finally {
			s.close();
		}

		SearchForm searchForm = new SearchForm();
		
		if (account == null)
			return searchForm;

		//ArrayList<Search> search=new ArrayList<>();
		List<Search> search = new ArrayList<Search>();
		for(Account temp : account )
		{
			Boolean status=false;
			if(temp.getStatus()==1)
				status=true;
			Search tempSearch=new Search(temp.getAccountNumber(),temp.getCurrentBalance()+"",status);
			
			if((currentSessionUser.equals(Constants.TIER2)&&temp.getUser().getRole().equals(Constants.CUSTOMER)) ||(currentSessionUser.equals("admin")&&(temp.getUser().getRole().equals(Constants.TIER1)||temp.getUser().getRole().equals(Constants.TIER2))) )
			search.add(tempSearch);
			
		}
		searchForm.setSearchs(search);
		return searchForm;			
	}
	
	public Boolean authorizeAccounts(String accNumber) throws ParseException {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return null;
			}
		}

		Session s = SessionManager.getSession("");
		Transaction tx = null;
		try {
			
			List<Account> account=null;
			account=s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
					.setParameter("accountNumber", accNumber).getResultList();
			tx = s.beginTransaction();
			for(Account temp : account )
			{
				
				if((currentSessionUser.equals("tier2")&&temp.getUser().getRole().equals("customer")) ||(currentSessionUser.equals("admin")&&(temp.getUser().getRole().equals("tier1")||temp.getUser().getRole().equals("tier2"))) )
				{
					temp.setStatus(1);
					
					DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
					Date date = new Date();
					Date d = new SimpleDateFormat("mm-dd-yyyy").parse(dateFormat.format(date));
					temp.setApprovalDate(d);
					s.saveOrUpdate(temp);
				}
				else
				{
					if (tx.isActive()) tx.commit();
					s.close();
					return false;
				}
			}

			if (tx.isActive()) tx.commit();
			
		} catch (Exception e) {
			if (tx != null && tx.isActive()) tx.rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}
		
		return true;
	}
	
	public Boolean declineAccounts(String accNumber) throws ParseException {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return null;
			}
		}
		

		List<Account> account=null;
		Transaction tx = null;
		Session s = SessionManager.getSession("");
		try {
			account=s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
					.setParameter("accountNumber", accNumber).getResultList();
			
			tx = s.beginTransaction();
			for(Account temp : account )
			{	
				if((currentSessionUser.equals("tier2")&&temp.getUser().getRole().equals("customer")) ||(currentSessionUser.equals("admin")&&(temp.getUser().getRole().equals("tier1")||temp.getUser().getRole().equals("tier2"))) )
				{
					temp.setStatus(2);
					
					DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
					Date date = new Date();
					Date d = new SimpleDateFormat("mm-dd-yyyy").parse(dateFormat.format(date));
					temp.setApprovalDate(d);
					s.saveOrUpdate(temp);
				}
				else {
					if (tx.isActive()) tx.commit();
					s.close();
					return false;
				}
			}
			
			if (tx.isActive()) tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) tx.rollback();
		} finally {
			s.close();
		}

		return true;
	}
	
	public Boolean doesAccountExists(String accountNumber) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals(Constants.TIER1) || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}

		Session s = SessionManager.getSession("");
		Account account = null;
		try {
			account = s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", accountNumber).getSingleResult();
		} catch (NoResultException e){
			e.printStackTrace();
		} finally {
			s.close();
		}
		
		if(account == null)
			return false;

		return true;
	}
	
	public Boolean findAccount(String accountNumber) {
		Session s = SessionManager.getSession("");
		Account account = null;
		try {
			account = s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", accountNumber).getSingleResult();
		} catch (NoResultException e){
			e.printStackTrace();
		} finally {
			s.close();
		}

		if (account == null)
			return false;

		return true;
	}

	public boolean setPrimaryAccount(String account, User user) {
		
		Session s = SessionManager.getSession("");
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			List<Account> useraccounts = user.getAccounts();
			for(Account ua:useraccounts) {
				if(ua.getAccountNumber().equalsIgnoreCase(account)) {
					ua.setDefaultFlag(1);
					s.update(ua);

				}
				else if(ua.getDefaultFlag() != null && ua.getDefaultFlag() == 1) {
					ua.setDefaultFlag(0);
					s.update(ua);

				}
			}
			
			if (tx.isActive()) tx.commit();
		} catch (Exception e){
			if (tx != null && tx.isActive()) tx.rollback(); 
			e.printStackTrace();
			s.close();
			return false;
		} finally {
			s.close();
		}

		return true;
	}

	public boolean findByAccountNumberAndLastName(String recipientaccnum, String lastname) {
		Session s = SessionManager.getSession("");
		Account account = null;
		try {
			
			account = s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
					.setParameter("accountNumber", recipientaccnum).getSingleResult();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			s.close();
		}

		if (account == null)
			return false;
		
		if (!account.getUser().getUserDetail().getLastName().equals(lastname))
			return false;

		return true;
	}
	
	

}
