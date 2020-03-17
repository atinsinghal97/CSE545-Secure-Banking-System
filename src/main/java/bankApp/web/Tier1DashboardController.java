package bankApp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import bankApp.model.Tier1;

@Controller
public class Tier1DashboardController {
	@Autowired
	@RequestMapping(value = "/Tier1_Dashboard", method = RequestMethod.POST)
	public ModelAndView Dashboard(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		try {
			Tier1 tier1 = (Tier1) session.getAttribute("EmployeeObject");
			if (tier1 == null)
				return new ModelAndView("redirect:/login");
			else
				return new ModelAndView(("Tier1_Dashboard"), model);
		}
		catch(Exception e){
			return new ModelAndView("redirect:/login");
		}
	}
	
}
