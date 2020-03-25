package web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class OTPController {
	@RequestMapping(value ="/generateAccountOtp", method = RequestMethod.GET)
	public ModelAndView generateLoginOtp(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		return new ModelAndView("OtpPage",model);
	}
	@RequestMapping(value ="/generateAppointmentOtp", method = RequestMethod.GET)
	public ModelAndView generateAppointment(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		return new ModelAndView("OtpPageForAppointment",model);
	}
	
	@RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> validateOtp(@RequestParam("otpnum") int otpnum, HttpSession session){

		final ResponseEntity<String> SUCCESS = ResponseEntity.status(200).body("Valid OTP");
		final ResponseEntity<String> FAILURE = ResponseEntity.status(405).body("Invalid OTP. Please try again!");
		return SUCCESS;	
	}
}