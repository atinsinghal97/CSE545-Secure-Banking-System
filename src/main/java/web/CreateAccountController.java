package web;

import org.springframework.ui.Model;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import database.SessionManager;
import model.Account;
import java.util.Optional;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import database.SessionManager;
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
