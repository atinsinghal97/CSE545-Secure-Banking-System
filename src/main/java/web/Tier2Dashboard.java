package web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Tier2Dashboard {
	
	
	@RequestMapping("/Tier2Dashboard")
    public String updatePage(final HttpServletRequest request, Model model) {
		
        return "Tier2Dashboard";
    }
	
	
	

}
