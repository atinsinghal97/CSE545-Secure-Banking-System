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
import org.springframework.web.servlet.ModelAndView;

import constants.Constants;

import org.hibernate.Transaction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


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
				if (grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("tier2")||grantedAuthority.getAuthority().equals("tier1")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return null;
			}
		}
		
		Session s = SessionManager.getSession("");
		List<Account> account=null;
		account=s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", accNumber).getResultList();
		
		
		SearchForm searchForm = new SearchForm();
		//ArrayList<Search> search=new ArrayList<>();
		List<Search> search = new ArrayList<Search>();
		for(Account temp : account )
		{
			Boolean status=false;
			if(temp.getStatus()==1)
				status=true;
			Search tempSearch=new Search(temp.getAccountNumber(),temp.getCurrentBalance()+"",status);	
			if(((currentSessionUser.equals("tier1")||currentSessionUser.equals("tier2"))&&temp.getUser().getRole().equals("customer")) ||(currentSessionUser.equals("admin")&&(temp.getUser().getRole().equals("tier1")||temp.getUser().getRole().equals("tier2"))) )
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
				if (grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return null;
			}
		}
		
		Session s = SessionManager.getSession("");
		List<Account> account=null;
		account=s.createQuery("FROM Account WHERE account_number = :accountNumber AND status=1", Account.class)
				.setParameter("accountNumber", accNumber).getResultList();
		if(account.size()==0)
		{
			return false;
		}
		Transaction tx = null;
		tx = s.beginTransaction();
		for(Account temp : account )
		{

			
			if((currentSessionUser.equals("tier2")&&temp.getUser().getRole().equals("customer")) ||(currentSessionUser.equals("admin")&&(temp.getUser().getRole().equals("tier1")||temp.getUser().getRole().equals("tier2"))) )
			{
				temp.setStatus(3);
				s.saveOrUpdate(temp);
			}
			
	
		}

		
		if (tx.isActive())
		    tx.commit();
		s.close();
		return true;
		
	}
	
	public SearchForm getAllPendingAccounts() {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return null;
			}
		}
		Session s = SessionManager.getSession("");
		List<Account> account=null;
		account=s.createQuery("FROM Account where status=0", Account.class)
				 .getResultList();
		
		
		SearchForm searchForm = new SearchForm();
		//ArrayList<Search> search=new ArrayList<>();
		List<Search> search = new ArrayList<Search>();
		for(Account temp : account )
		{
			Boolean status=false;
			if(temp.getStatus()==1)
				status=true;
			Search tempSearch=new Search(temp.getAccountNumber(),temp.getCurrentBalance()+"",status);
			
			if((currentSessionUser.equals("tier2")&&temp.getUser().getRole().equals("customer")) ||(currentSessionUser.equals("admin")&&(temp.getUser().getRole().equals("tier1")||temp.getUser().getRole().equals("tier2"))) )
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
				if (grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return null;
			}
		}

		Session s = SessionManager.getSession("");
		List<Account> account=null;
		account=s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", accNumber).getResultList();
		//ArrayList<Search> search=new ArrayList<>()
		Transaction tx = null;
		tx = s.beginTransaction();
		for(Account temp : account )
		{
//			Boolean status=false;
//			if(temp.getStatus()==1)
//				status=true;
//			Search tempSearch=new Search(temp.getAccountNumber(),temp.getCurrentBalance()+"",status);
//			
//			if(temp.getUser().getRole().equals("customer"))
//			search.add(tempSearch);	
//			System.out.println(temp.getUser().getRole());
			
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
				if (tx.isActive())
				    tx.commit();
				s.close();
				return false;
			}
			
	
		}

		
		if (tx.isActive())
		    tx.commit();
		s.close();
		return true;
		
	}
	
	public Boolean declineAccounts(String accNumber) throws ParseException {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("admin") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return null;
			}
		}

		Session s = SessionManager.getSession("");
		List<Account> account=null;
		account=s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", accNumber).getResultList();
		//ArrayList<Search> search=new ArrayList<>()
		Transaction tx = null;
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
			else
			{
				if (tx.isActive())
				    tx.commit();
				s.close();
				return false;
			}
			
	
		}

		
		if (tx.isActive())
		    tx.commit();
		s.close();
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
		Transaction tx = null;
		tx = s.beginTransaction();
		Account account = null;
		try {
			account = s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", accountNumber).getSingleResult();
		}catch (NoResultException e){
			return false;
		}
		if(account == null)
			return false;
		
		if (tx.isActive())
		    tx.commit();
		s.close();
		return true;
	}
	
	public Boolean findAccount(int accountNumber) {
		Session s = SessionManager.getSession("");
		Transaction tx = null;
		tx = s.beginTransaction();
		Account account = null;
		try {
			account = s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", accountNumber).getSingleResult();
		}catch (NoResultException e){
			return false;
		}
		if(account == null)
			return false;
		
		if (tx.isActive())
		    tx.commit();
		s.close();
		return true;
	}

	public boolean setPrimaryAccount(Integer accountnumber, User user) {
		
		Session s = SessionManager.getSession("");
		Transaction tx = null;
		tx = s.beginTransaction();
		try {
			List<Account> useraccounts = user.getAccounts();
			for(Account ua:useraccounts) {
				if(Integer.parseInt(ua.getAccountNumber())==accountnumber) ua.setDefaultFlag(1);
				else if(ua.getDefaultFlag()==1)ua.setDefaultFlag(0);
				s.update(useraccounts);
			}
		}catch (Exception e){
			return false;
		}
		if (tx.isActive())
		    tx.commit();
		s.close();
		return true;
	}

	public boolean findByAccountNumberAndLastName(String recipientaccnum, String lastname) {
		Session s = SessionManager.getSession("");
		Transaction tx = null;
		Account account = null;
		tx = s.beginTransaction();
		try {
			
			account = s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
					.setParameter("accountNumber", recipientaccnum).getSingleResult();
			
		}catch(Exception e) {
			return false;
		}
		if(account == null)
			return false;
		if(!account.getUser().getUserDetail().getLastName().equals((lastname)))return false;
		if (tx.isActive())
		    tx.commit();
		s.close();
		return true;
	}
	
	

}
