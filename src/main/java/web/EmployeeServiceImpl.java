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

import database.SessionManager;
import model.User;
import model.UserDetail;
import forms.EmployeeSearch;
import forms.EmployeeSearchForm;

@Component(value = "employeeServiceImpl")
public class EmployeeServiceImpl {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private boolean isAdminSession() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null && auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("admin")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		
		return true;
	}
	
	public EmployeeSearchForm getEmployees(String username) {
		if (!isAdminSession())
			return null;
		
		Session s = SessionManager.getSession("");
		List<User> user=null;
		
		user=s.createQuery("FROM User WHERE username = :username", User.class)
				.setParameter("username", username).getResultList();	
		EmployeeSearchForm employeeSearchForm = new EmployeeSearchForm();
		List<EmployeeSearch> employeeSearch = new ArrayList<EmployeeSearch>();
		for(User temp : user )
		{
			if((temp.getRole().equals("tier2")||temp.getRole().equals("tier1")) && temp.getStatus()!=3)
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
		if (!isAdminSession())
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
		if (!isAdminSession())
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
	
	public Boolean createEmployee(String userType,String firstname,String middlename,String lastname,String username,String password,String email,String address,String phone,String dateOfBirth,String ssn,String secquestion1,String secquestion2) throws ParseException
	{
		if (!isAdminSession())
			return null;
		
		Session s = SessionManager.getSession("");
		Transaction tx = null;
		List<User> users=null;
		users=s.createQuery("FROM User WHERE username = :username", User.class)
				.setParameter("username", username).getResultList();
		if(users.size()==0)
		{			
			tx = s.beginTransaction();
			System.out.println(password+"@@@@@@@@@@");
			System.out.println(passwordEncoder);
			User user = new User();
			user.setUsername(username);
			user.setPassword(passwordEncoder.encode(password));
			user.setRole(userType);
			user.setStatus(1);
			s.saveOrUpdate(user);
			UserDetail userDetail;
			Date date = new SimpleDateFormat("mm-dd-yyyy").parse(dateOfBirth);
			Integer uid = user.getId();
			System.out.println("UID AFTER SAVE: " + uid);
			userDetail = new UserDetail();
			userDetail.setUser(user);
			userDetail.setFirstName(firstname);
			userDetail.setMiddleName(middlename);
			userDetail.setLastName(lastname);
			userDetail.setEmail(email);
			userDetail.setPhone(phone);
			userDetail.setAddress1(address);
			userDetail.setAddress2("");
			userDetail.setCity("");
			userDetail.setDateOfBirth(date);
			userDetail.setProvince("");
			userDetail.setSsn(ssn);
			userDetail.setZip(100L);
			userDetail.setQuestion1(secquestion1);
			userDetail.setQuestion2(secquestion2);
			s.saveOrUpdate(userDetail);
			if (tx.isActive())
			    tx.commit();
			s.close();
			return true;
		
		}
		else
			return false;



		}
	}
