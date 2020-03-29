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
		
		Session s = SessionManager.getSession("");
		List<User> user=null;
		
		user=s.createQuery("FROM User WHERE username = :username", User.class)
				.setParameter("username", username).getResultList();	
		EmployeeSearchForm employeeSearchForm = new EmployeeSearchForm();
		List<EmployeeSearch> employeeSearch = new ArrayList<EmployeeSearch>();
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
		employeeSearchForm.setEmployeeSearchs(employeeSearch);
		return employeeSearchForm;		
		
	}
	
	public Boolean updateEmployees(String userName,String email,String firstName,String lastName,String middleName,String phoneNumber) {	
		if (!WebSecurityConfig.currentSessionHasAnyAuthority("admin","tier2"))
			return null;
			
		 Session s = SessionManager.getSession("");
		 List<User> user=null;
		 user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", userName).getResultList();
		 
		Transaction tx = null;
		tx = s.beginTransaction();
		 if(user.size()==0)
			 return false;
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
			else
				return false;
		}
		if (tx.isActive())
		    tx.commit();
		s.close();
		return true;
   
		
	}
	
	public Boolean deleteEmployees(String userName,String firstName,String lastName) {
		if (!WebSecurityConfig.currentSessionHasAnyAuthority("admin"))
			return null;
		
		
		 Session s = SessionManager.getSession("");
		 List<User> user=null;
		 user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", userName).getResultList();
		 
		Transaction tx = null;
		tx = s.beginTransaction();
		 if(user.size()==0)
			 return false;
		for(User temp : user )
		{
			if((temp.getRole().equals("tier2")||temp.getRole().equals("tier1")) && temp.getStatus()!=3)
			{
			System.out.println("Get Here");
			UserDetail ud = new UserDetail();
			ud = s.createQuery("FROM UserDetail WHERE user_id = :uid", UserDetail.class)
			.setParameter("uid", temp.getId()).getSingleResult();
			if(ud.getLastName().equals(lastName) && ud.getFirstName().equals(firstName))
			{
				temp.setStatus(3);
				s.saveOrUpdate(temp);
			}
			else
				 return false;
			}
			else
				return false;
		}
		if (tx.isActive())
		    tx.commit();
		s.close();
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
