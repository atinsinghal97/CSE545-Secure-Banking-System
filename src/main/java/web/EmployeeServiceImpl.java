package web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import database.SessionManager;
import model.User;
import model.UserDetail;
import forms.EmployeeSearch;
import forms.EmployeeSearchForm;

@Component(value = "employeeServiceImpl")
public class EmployeeServiceImpl {
	public EmployeeSearchForm getEmployees(String username) {
		if (!WebSecurityConfig.currentSessionHasAnyAuthority("admin","tier2"))
			return null;

		EmployeeSearchForm employeeSearchForm = new EmployeeSearchForm();
		List<EmployeeSearch> employeeSearch = new ArrayList<EmployeeSearch>();
		Session s = SessionManager.getSession("");
		
		try {
			
			List<User> user=null;
			
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", username).getResultList();	
			Boolean isAdmin=false;
			Boolean isTier2=false;
			
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			for (GrantedAuthority grantedAuthority : x.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("tier2"))
				{
					isTier2=true;
				}
				if (grantedAuthority.getAuthority().equals("admin"))
				{
					isAdmin=true;
				}		
			}
			for(User temp : user )
			{
				if(((isAdmin&&(temp.getRole().equals("tier2")||temp.getRole().equals("tier1"))) || (isTier2&& temp.getRole().equals("customer"))) && temp.getStatus()!=3)
				{
				UserDetail ud = new UserDetail();
				ud = s.createQuery("FROM UserDetail WHERE user_id = :uid", UserDetail.class)
						.setParameter("uid",temp.getId()).getSingleResult();
				System.out.println(ud.getCity());
				EmployeeSearch tempSearch=new EmployeeSearch(temp.getUsername(),ud.getEmail(),ud.getFirstName(),ud.getLastName(),ud.getMiddleName(),ud.getPhone());	
				employeeSearch.add(tempSearch);	
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		
		employeeSearchForm.setEmployeeSearchs(employeeSearch);
		return employeeSearchForm;
	}
	
	public Boolean updateEmployees(String userName,String email,String firstName,String lastName,String middleName,String phoneNumber) {	
		if (!WebSecurityConfig.currentSessionHasAnyAuthority("admin","tier2"))
			return null;
			
		Session s = SessionManager.getSession("");
		Transaction tx = null;
		try {
			List<User> user=null;
			user=s.createQuery("FROM User WHERE username = :username", User.class)
				.setParameter("username", userName).getResultList();
			if (user.size()==0) {
				s.close();
				return false;
			}

			Boolean isAdmin=false;
			Boolean isTier2=false;
			
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			String username=x.getName();
			for (GrantedAuthority grantedAuthority : x.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("tier2"))
				{
					isTier2=true;
				}
				if (grantedAuthority.getAuthority().equals("admin"))
				{
					isAdmin=true;
				}		
			}
			
			tx = s.beginTransaction();
			for(User temp : user )
			{
				if(((isAdmin&&(temp.getRole().equals("tier2")||temp.getRole().equals("tier1"))) || (isTier2&& temp.getRole().equals("customer"))) && temp.getStatus()!=3)
				{
					UserDetail ud = new UserDetail();
					ud = s.createQuery("FROM UserDetail WHERE user_id = :uid", UserDetail.class)
					.setParameter("uid", temp.getId()).getSingleResult();
					ud.setEmail(email);
					ud.setFirstName(firstName);
					ud.setLastName(lastName);
					ud.setMiddleName(middleName);
					ud.setPhone(phoneNumber);
					s.saveOrUpdate(ud);
				}
				else {
					s.close();
					return false;
				}
			}
			
			if (tx.isActive()) tx.commit();
			
		} catch (Exception e) {
			if (tx != null && tx.isActive()) tx.rollback();
			s.close();
			return false;
		} finally {
			s.close();
		}

		return true;
	}
	
	public Boolean deleteEmployees(String userName,String firstName,String lastName) {
		if (!WebSecurityConfig.currentSessionHasAnyAuthority("admin"))
			return null;

		Session s = SessionManager.getSession("");
		Transaction tx = null;
		try {

			List<User> user=null;
			user = s.createQuery("FROM User WHERE username = :username", User.class)
				.setParameter("username", userName).getResultList();

			if (user.size()==0) {
				s.close();
				return false;
			}

			tx = s.beginTransaction();
			for (User temp : user) {
				if ((temp.getRole().equals("tier2")||temp.getRole().equals("tier1")) && temp.getStatus()!=3) {
					System.out.println("Get Here");
					UserDetail ud = new UserDetail();
					ud = s.createQuery("FROM UserDetail WHERE user_id = :uid", UserDetail.class)
						.setParameter("uid", temp.getId()).getSingleResult();
	
					if(ud.getLastName().equals(lastName) && ud.getFirstName().equals(firstName))
					{
						temp.setStatus(3);
						s.saveOrUpdate(temp);
					} else {
						s.close();
						return false;
					}
				}
				else {
					s.close();
					return false;
				}
			}

			if (tx != null && tx.isActive()) tx.commit();
			s.close();
			
		} catch (Exception e) {
			if (tx != null && tx.isActive()) tx.rollback();
		} finally {
			s.close();
		}

		return true;
	}
	
	public Boolean createEmployee(User user) {
		if (!WebSecurityConfig.currentSessionHasAnyAuthority("admin"))
			return null;
		
        Session session = SessionManager.getSession("");
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            session.save(user);
            session.save(user.getUserDetail());

            if (tx.isActive()) tx.commit();
            
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return false;
	}
}
