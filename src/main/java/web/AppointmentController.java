package web;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
import forms.Search;
import forms.SearchForm;
import model.Account;
import model.User;
import model.UserDetail;
import model.Appointment;
import forms.AppSearch;
import forms.AppSearchForm;

import java.util.ArrayList; 
import java.util.List; 
import java.util.Random; 




@Controller
public class AppointmentController {
	@RequestMapping("/Appointment")
    public ModelAndView home(final HttpServletRequest request, HttpSession session) {
		Object validOtp = session.getAttribute("OtpValid");
		if (validOtp == null || !(validOtp instanceof Integer)) {
			return new ModelAndView("redirect:/homepage"); 
		}

		return new ModelAndView("ScheduleAppointment");
    }

	@RequestMapping(value = "/AppointmentCreate", method = RequestMethod.POST)
    public ModelAndView appointmentCreate(
    		HttpServletRequest request, HttpSession session,
    		@RequestParam(required = true, name="appointment") String status,
    		@RequestParam(required = true, name="schedule_date") String dateapp,Model model) throws ParseException  {	
		Object validOtp = session.getAttribute("OtpValid");
		if (validOtp == null || !(validOtp instanceof Integer)) {
			return new ModelAndView("redirect:/homepage"); 
		}
		
		if (!WebSecurityConfig.currentSessionHasAnyAuthority("customer"))
			return new ModelAndView("Login"); 

		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		String username=x.getName();
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD"); 
		Date date = (Date)formatter.parse(dateapp);
		date.setMonth((date.getMonth() - 1 + 2) % 12 + 1);
		Session s = SessionManager.getSession("");
		List<User> user=null;
		user=s.createQuery("FROM User WHERE username = :username", User.class)
						.setParameter("username", username).getResultList();		 
		Transaction tx = null;
		tx = s.beginTransaction();
		List<User> employees=null;
		employees=s.createQuery("FROM User WHERE role = :tier1 OR role= :tier2", User.class)
				.setParameter("tier1", "tier1").setParameter("tier2", "tier2").getResultList();
		if(user.size()==0) {
			session.removeAttribute("OtpValid");
			return new ModelAndView("redirect:/login"); 
		}
		for(User temp : user )
		{
			Random rand = new Random(); 
			User random=employees.get(rand.nextInt(employees.size())); 
			Appointment app=new Appointment(); 
			app.setUser1(temp);
			app.setUser2(random);
			app.setCreatedDate(date);
			app.setAppointmentStatus(status);
			System.out.println(app.getCreatedDate());
			Date todayDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			System.out.println(sdf.format(todayDate).equals(sdf.format(date)));
			s.saveOrUpdate(app);
			
		}
		if (tx.isActive())
		    tx.commit();
		s.close();
		
		session.removeAttribute("OtpValid");
		
		session.setAttribute("message", "Appointment creation successful!");
		return new ModelAndView("redirect:/homepage");
    }
	
	@RequestMapping("/ViewAppointments")
    public ModelAndView viewAppointment(Model model) {
		if (!WebSecurityConfig.currentSessionHasAnyAuthority("tier2","tier1"))
			return new ModelAndView("Login"); 
		Session s = SessionManager.getSession("");
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		String username=x.getName();
		
		for (GrantedAuthority grantedAuthority : x.getAuthorities()) {
			if (grantedAuthority.getAuthority().equals("tier2"))
			{
				model.addAttribute("role", "tier2");
			}
			if (grantedAuthority.getAuthority().equals("tier1"))
			{
				model.addAttribute("role", "tier1");
			}		
		}
		
		User employee=null;
		employee=s.createQuery("FROM User WHERE username= :username", User.class)
				.setParameter("username", username).getSingleResult();
		AppSearchForm appSearchForm = new AppSearchForm();
	
		List<AppSearch> appSearch = new ArrayList<AppSearch>();
		List<Appointment> appointments=s.createQuery("FROM Appointment WHERE appointment_user_id = :uid", Appointment.class)
										.setParameter("uid", employee.getId()).getResultList();
	
		
		Date todayDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		for(Appointment temp : appointments )
		{	
			if(sdf.format(todayDate).equals(sdf.format(temp.getCreatedDate())))
			{
				AppSearch tempSearch=new AppSearch(temp.getUser1().getUsername(),temp.getAppointmentStatus());
				appSearch.add(tempSearch);
			}
			
		}
		
		if(appSearch.size()==0)
			return new ModelAndView("ViewAppointment" , "message", "No appointments today");
		appSearchForm.setAppSearchs(appSearch);
	
		return new ModelAndView("ViewAppointment" , "appSearchForm", appSearchForm);
        
    }
	
}

