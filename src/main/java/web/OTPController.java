package web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import database.SessionManager;
import model.User;
@Controller
public class OTPController {
	
	
	 OTPservice otpservice = new OTPservice();
	@RequestMapping(value ="/generateAccountOtp", method = RequestMethod.GET)
	public ModelAndView generateLoginOtp(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		
		int otp = otpservice.generateNewOTP(x.getName());
		System.out.print("otp"+ otp);
		
		
		return new ModelAndView("OtpPage",model);
	}
	@RequestMapping(value ="/generateAppointmentOtp", method = RequestMethod.GET)
	public ModelAndView generateAppointment(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		
		int otp = otpservice.generateNewOTP(x.getName());
		System.out.print("otp"+ otp);
		
		return new ModelAndView("OtpPageForAppointment",model);
	}
	
	@RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> validateOtp(@RequestParam("otpnum") int otpnumber, HttpSession session){
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		int otprecieved = otpservice.getOTP(x.getName());
		final ResponseEntity<String> SUCCESS = ResponseEntity.status(200).body("Valid OTP");
		final ResponseEntity<String> FAILURE = ResponseEntity.status(405).body("Invalid OTP. Please try again!");
		if(otpnumber==otprecieved) {
			session.setAttribute("OtpValid", otprecieved);
			otpservice.removeOtp(x.getName());
			return SUCCESS;
		}
		return FAILURE;
			
	}
}