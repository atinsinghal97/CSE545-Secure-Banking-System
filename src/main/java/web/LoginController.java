package web;

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

import com.example.hibernate.AdminId;
import com.example.hibernate.Customer;
import com.example.hibernate.CustomerId;
import com.example.hibernate.MerchantId;
import com.example.hibernate.SessionManager;
import com.example.hibernate.Tier1Id;
import com.example.hibernate.Tier2Id;
import com.example.hibernate.User;
import com.example.hibernate.UserDetails;
import com.example.hibernate.UserDetailsId;

@Controller
public class LoginController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping("/login")
    public String hello(final HttpServletRequest request, Model model) {
	    HttpSession session = request.getSession(false);
	    
	    if (session != null) {
		    Object msg = session.getAttribute("msg");
	        model.addAttribute("message", session.getAttribute("msg"));
	        if (msg != null)
	        	session.removeAttribute("msg");
	    }
        return "Login";
    }

	@RequestMapping("/register")
    public String registration(final HttpServletRequest request, Model model) {
		return "RegistrationExternal";
    }
	
	@RequestMapping(value = "/externalregister", method = RequestMethod.POST)
    public ModelAndView register(
    		@RequestParam(required = true, name="designation") String userType,
    		@RequestParam(required = false, name="firstname") String firstname,
    		@RequestParam(required = false, name="middlename") String middlename,
    		@RequestParam(required = false, name="lastname") String lastname,
    		@RequestParam(required = false, name="username") String username,
    		@RequestParam(required = false, name="password") String password,
    		@RequestParam(required = false, name="email") String email,
    		@RequestParam(required = false, name="address") String address,
    		@RequestParam(required = false, name="phone") String phone,
    		@RequestParam(required = false, name="date_of_birth") String dateOfBirth,
    		@RequestParam(required = false, name="ssn") String ssn,
    		@RequestParam(required = false, name="secquestion1") String secquestion1,
    		@RequestParam(required = false, name="secquestion2") String secquestion2) {
		
		Session s = SessionManager.getSession("");
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			User user = new User(username, passwordEncoder.encode(password), userType);
			s.save(user);
			UserDetailsId userDetailIds;
			UserDetails userDetail;
			Date date = new SimpleDateFormat("mm-dd-yyyy").parse(dateOfBirth);

			Integer uid = user.getUserId();
			System.out.println("UID AFTER SAVE: " + uid);
			userDetailIds = new UserDetailsId(uid, firstname, middlename, lastname, email, phone, "", address, "", "", "", 100, date, ssn, secquestion1, secquestion2);
			//s.save(userDetailIds);
			userDetail = new UserDetails(userDetailIds, user);
			s.save(userDetail);
			if (tx.isActive())
			    tx.commit();
			s.close();
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}
		
		return new ModelAndView("redirect:/login");
    }

	@RequestMapping("/homepage")
    public String home(final HttpServletRequest request, Model model) {
		
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(x.getName());
		return "CustomerDashboard";
    }
}
