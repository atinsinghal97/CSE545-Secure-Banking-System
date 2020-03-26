package web;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import forms.EmployeeSearchForm;


@Controller
public class AdminController {
	@Resource(name = "employeeServiceImpl")
	EmployeeServiceImpl employeeServiceImpl;

	
	@RequestMapping("/Admin/Dashboard")
    public String hello(final HttpServletRequest request, Model model) {
	    HttpSession session = request.getSession(false);
	    
	    if (session != null) {
		    Object msg = session.getAttribute("msg");
	        model.addAttribute("message", session.getAttribute("msg"));
	        if (msg != null)
	        	session.removeAttribute("msg");
	    }
        return "AdminDashboard";
    }

	@RequestMapping("/Admin/SearchEmployee")
    public String employeeView(final HttpServletRequest request, Model model) {
		return "AdminEmployeeSearch";
    }
	
	@RequestMapping("/Admin/CreateEmployee")
    public String employeeInsert(final HttpServletRequest request, Model model) {
		return "AdminRegistrationExternal";
    }
	
	@RequestMapping("/Admin/UpdateEmployee")
    public String employeeUpdate(final HttpServletRequest request, Model model) {
		return "AdminEmployeeUpdate";
    }

	@RequestMapping("/Admin/DeleteEmployee")
    public String employeeDelete(final HttpServletRequest request, Model model) {
		return "AdminEmployeeDelete";
    }
	
	@RequestMapping("/Admin/SystemLogs")
    public String systemLogs(final HttpServletRequest request, Model model) {
		return "SystemLogs";
    }
	
	@RequestMapping(value = "/Admin/Search", method = RequestMethod.POST)
    public ModelAndView adminSearchPage(@RequestParam(required = true, name="username") String username, Model model) {
		EmployeeSearchForm employeeSearchForm=employeeServiceImpl.getEmployees(username);
		if(employeeSearchForm==null)
			return new ModelAndView("Login");
		else
			if(employeeSearchForm.getEmployeeSearchs().size()==0)
				return new ModelAndView("AdminEmployeeSearch" , "message", "An username not found");
			else
				return new ModelAndView("AdminEmployeeSearch" , "employeeSearchForm", employeeSearchForm);  
    }
	
	@RequestMapping(value = "/Admin/UpdateSearch", method = RequestMethod.POST)
    public String adminUpdateSearchPage(@RequestParam(required = true, name="username") String username, Model model) {
		EmployeeSearchForm employeeSearchForm=employeeServiceImpl.getEmployees(username);
		if(employeeSearchForm==null)
			return "Login";
		else
			if(employeeSearchForm.getEmployeeSearchs().size()==0)
			{
				model.addAttribute("message", "An username not found");
				return "AdminEmployeeUpdate";
			}		
			else
				{
				System.out.println("CAME HERE!!!!!!");
				System.out.println(employeeSearchForm.employeeSearchs.get(0).getEmail());
				model.addAttribute("userName", username);
				model.addAttribute("email",employeeSearchForm.employeeSearchs.get(0).getEmail());
				model.addAttribute("firstName",employeeSearchForm.employeeSearchs.get(0).getFirstName());
				model.addAttribute("lastName",employeeSearchForm.employeeSearchs.get(0).getLastName());
				model.addAttribute("middleName",employeeSearchForm.employeeSearchs.get(0).getMiddleName());
				model.addAttribute("phoneNumber",employeeSearchForm.employeeSearchs.get(0).getPhoneNumber());
				return "AdminEmployeeUpdate";
				}
    }
	@RequestMapping(value = "/Admin/UpdateValues", method = RequestMethod.POST)
    public ModelAndView changeValue(
    		@RequestParam(required = true, name="userName") String userName,
    		@RequestParam(required = true, name="email") String email,
    		@RequestParam(required = true, name="firstName") String firstName,
    		@RequestParam(required = true, name="lastName") String lastName,
    		@RequestParam(required = true, name="middleName") String middleName,
    		@RequestParam(required = true, name="phoneNumber") String phoneNumber,
    		final HttpServletRequest request, Model model)  {

		Boolean flag=employeeServiceImpl.updateEmployees(userName, email, firstName, lastName, middleName, phoneNumber);
		
		if(flag==null)
			return new ModelAndView("Login");
		else
			if(flag)
				return new ModelAndView("AdminEmployeeUpdate" , "message", "The Info username was updated");
			else
				return new ModelAndView("AdminEmployeeUpdate" , "message", "An username not found");
    }
	
	@RequestMapping(value = "/Admin/DelEmployee", method = RequestMethod.POST)
    public ModelAndView deleteEmployee(
    		@RequestParam(required = true, name="firstName") String firstName,
    		@RequestParam(required = true, name="lastName") String lastName,
    		@RequestParam(required = true, name="userName") String userName,
    		final HttpServletRequest request, Model model)  {

		Boolean flag=employeeServiceImpl.deleteEmployees(userName,firstName, lastName);
		if(flag==null)
			return new ModelAndView("Login");
		else
			if(flag)
				return new ModelAndView("AdminEmployeeDelete" , "message", "The username was deleted");
			else
				return new ModelAndView("AdminEmployeeDelete" , "message", "An Employee account was not found");
    }
	@RequestMapping(value = "/Admin/ExternalRegister", method = RequestMethod.POST)
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
    		@RequestParam(required = true, name="secquestion2") String secquestion2) throws ParseException {

		Boolean flag=employeeServiceImpl.createEmployee(userType,firstname,middlename,lastname,username,password,email,address,phone,dateOfBirth,ssn,secquestion1,secquestion2);
		if(flag==null)
			return new ModelAndView("Login");
		else
			if(flag)
				return new ModelAndView("AdminRegistrationExternal","message","Account was successfully created");
			else
				return new ModelAndView("AdminRegistrationExternal","message","An Active Username was already found");	
    }



	


	
	
	
	
}