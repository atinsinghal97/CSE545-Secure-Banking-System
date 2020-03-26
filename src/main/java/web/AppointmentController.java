package web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import database.SessionManager;
import model.Account;
import model.User;
import model.UserDetail;
import model.Appointment;





@Controller
public class AppointmentController {

	@RequestMapping("/Appointment")
    public String home(final HttpServletRequest request, Model model) {
		return "ScheduleAppointment";
    }
	
	
	

	
	@RequestMapping(value = "/AppointmentCreate", method = RequestMethod.POST)
    public ModelAndView changeValue(final HttpServletRequest request, Model model) throws ParseException  {
		
		
		
		String username="test";//Have harcoded test now.Username should be got from the httpsession
		String status=request.getParameter("appointment");
		String dateapp=request.getParameter("schedule_date");
		//String dateOfBirth=request.getParameter("DOB");
		
	
		System.out.println(dateapp);
		
		
		Date date = new SimpleDateFormat("mm-dd-yyyy").parse(dateapp);
		
		 Session s = SessionManager.getSession("");
		 
		 User u = null;
		
			u=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", username).getSingleResult();
			
			System.out.println("USER: " + u.getUsername());
			
			
	
		
			Integer uid = u.getId();
		
		System.out.println(uid);
		Transaction tx = null;
		
		tx = s.beginTransaction();
		Appointment app=new Appointment(); 
		app.setUser1(u);
		app.setUser2(u);
		app.setCreatedDate(date);
		app.setAppointmentStatus(status);
	
		
		s.saveOrUpdate(app);
		
		
		

		if (tx.isActive())
		    tx.commit();
		s.close();
    
		
	   
			

		
		
		
		
		
		
		return new ModelAndView("redirect:/homepage");
    }
	
	
	
	
}

