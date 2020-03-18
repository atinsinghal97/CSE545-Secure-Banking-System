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
import model.Request;
import model.Customer;
import model.CustomerDetail;

@Controller
public class CreateAccountController {

	
//	@RequestMapping(value= {"/OpenAccount"}, method = RequestMethod.POST)
//	public ModelAndView OpenAccount(HttpServletRequest request, HttpSession session){
//		ModelMap model = new ModelMap();
//		try {
//			Customer newcust = (Customer) session.getAttribute("");
////			if (newcust == null && session.getAttribute("OtpValid") == null)
////			{
////				return new ModelAndView("redirect:/login");
////			}
//			session.setAttribute("OtpValid", null);
//			String role = (String) session.getAttribute("role");
//			model.addAttribute("role",role);
//			return new ModelAndView(("CreateNewAccount"), model);
//		}
//		catch(Exception ex)
//		{
//			return new ModelAndView("Login");
//		}
//	}

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
		
		Session s = SessionManager.getSession("");
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			Customer customer = new Customer();
			customer.setUsername(username);
			customer.setPassword(passwordEncoder.encode(password));
			customer.setUserType(userType);
			s.save(customer);
			CustomerDetail CustomerDetail;
			Date date = new SimpleDateFormat("mm-dd-yyyy").parse(dateOfBirth);

			Integer uid = user.getUserId();
			CustomerDetail = new CustomerDetail();
			CustomerDetail.setUser(customer);
			CustomerDetail.setFirstName(firstname);
			CustomerDetail.setMiddleName(middlename);
			CustomerDetail.setLastName(lastname);
			CustomerDetail.setEmail(email);
			CustomerDetail.setPhone(phone);
			CustomerDetail.setAddress1(address);
			CustomerDetail.setAddress2("");
			CustomerDetail.setCity("");
			CustomerDetail.setDateOfBirth(date);
			CustomerDetail.setProvince("");
			CustomerDetail.setSsn(ssn);
			CustomerDetail.setTier("");
			CustomerDetail.setZip(100);
			CustoCustomerDetailmerUserDetail.setQuestion1(secquestion1);
			CustomerDetail.setQuestion2(secquestion2);
			CustomerDetail.save(userDetail);
			
			Request r = new Request();
			r.setUser2(customer);
			r.setTypeOfRequest(0);
			r.setTypeOfAccount(accType);
			s.save(r);
			
			Account a = new Account();
			a.setUser2(customer);
			a.setAccountNumber("1234");
			a.setAccountType("savings");
			a.setApprovalStatus(false);
			a.setInterest(new BigDecimal(0.8));
			a.setCreatedDate(new Date());
			a.setCurrentBalance(new BigDecimal(100000));
			s.save(a);
			
			if (tx.isActive())
			    tx.commit();
			s.close();
		
		} catch (ParseException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			s.close();
		}
		
		return new ModelAndView("redirect:/login");
    }
}
