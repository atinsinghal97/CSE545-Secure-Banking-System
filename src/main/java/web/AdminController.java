package web;

import java.text.ParseException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import forms.EmployeeForm;
import forms.EmployeeSearch;
import forms.EmployeeSearchForm;
import forms.UserForm;


@Controller
public class AdminController {
	@Resource(name = "employeeServiceImpl")
	EmployeeServiceImpl employeeServiceImpl;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
		model.addAttribute("employeeForm", new EmployeeForm());
		return "AdminRegistrationExternal";
    }
	
	@RequestMapping("/Admin/UpdateEmployee")
    public String employeeUpdate(final HttpServletRequest request, Model model) {
		model.addAttribute("employeeForm", new EmployeeSearch());
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
		if (employeeSearchForm == null)
			return "Login";
		else
			if (employeeSearchForm.getEmployeeSearchs().size()==0) {
				model.addAttribute("message", "An username not found");
				model.addAttribute("employeeForm", new EmployeeSearch());
				return "AdminEmployeeUpdate";
			} else {
				System.out.println("CAME HERE!!!!!!");
				System.out.println(employeeSearchForm.employeeSearchs.get(0).getEmail());
				model.addAttribute("employeeForm", employeeSearchForm.employeeSearchs.get(0));
				return "AdminEmployeeUpdate";
			}
    }

	@RequestMapping(value = "/Admin/UpdateValues", method = RequestMethod.POST)
    public ModelAndView changeValue(
    		@Valid @ModelAttribute("employeeForm") EmployeeSearch employeeForm,
    		BindingResult result,
            Map<String, Object> model)  {
		
        if (result.hasErrors()) {
        	return new ModelAndView("AdminEmployeeUpdate");
        }
        
        Boolean flag = employeeServiceImpl.updateEmployees(
        		employeeForm.getUserName(),
        		employeeForm.getEmail(),
        		employeeForm.getFirstName(),
        		employeeForm.getLastName(),
        		employeeForm.getMiddleName(),
        		employeeForm.getPhoneNumber());

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
    		@Valid @ModelAttribute("employeeForm") EmployeeForm employeeForm,
    		BindingResult result,
            Map<String, Object> model) throws ParseException {

        if (result.hasErrors()) {
        	return new ModelAndView("AdminRegistrationExternal");
        }

        Boolean flag = null;
        try {
        	flag = employeeServiceImpl.createEmployee(employeeForm.createUser(passwordEncoder));
        } catch (Exception e) {
        	flag = false;
        	e.printStackTrace();
        }

		if(flag==null)
			return new ModelAndView("Login");
		else
			if(flag)
				return new ModelAndView("AdminRegistrationExternal","message","Account was successfully created");
			else
				return new ModelAndView("AdminRegistrationExternal","message","Could not create an account");	
    }



	


	
	
	
	
}