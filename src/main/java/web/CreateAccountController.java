package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.ui.ModelMap;
@Controller
public class CreateAccountController {

	
//to create any new account

	@RequestMapping(value = "/openaccount", method = RequestMethod.POST)
    public ModelAndView register(
    		@RequestParam(required = true, name="designation") String userType,
    		@RequestParam(required = true, name="firstname") String firstname,
    		@RequestParam(required = true, name="middlename") String middlename,
    		@RequestParam(required = true, name="lastname") String lastname,
    		@RequestParam(required = true, name="username") String username,
    		@RequestParam(required = true, name="password") String password,
    		@RequestParam(required = true, name="email") String email,
    		@RequestParam(required = true, name="address") String address,
    		@RequestParam(required = true, name="phone") String phone,
    		@RequestParam(required = true, name="date_of_birth") String dateOfBirth,
    		@RequestParam(required = true, name="ssn") String ssn,
    		@RequestParam(required = true, name="secquestion1") String secquestion1,
    		@RequestParam(required = true, name="secquestion2") String secquestion2,
    		@RequestParam(required = true, name="account_type")String  accType) {
		
		ModelMap model = new ModelMap();
		
		return new ModelAndView("redirect:/login");
    }
	
	
}
