package web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@Autowired
    private AuthenticationManager authManager;
	
	@RequestMapping("/login")
    public String hello(final HttpServletRequest request, Model model) {
	    HttpSession session = request.getSession(false);
        model.addAttribute("message", session.getAttribute("msg"));
        return "Login";
    }

	@RequestMapping("/homepage")
    public String process(Model model,
    		@RequestParam(value="username", required=false, defaultValue="") String username,
    		@RequestParam(value="password", required=false, defaultValue="") String password) {
		
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(x.getName());
		return "CustomerDashboard";
    }
}
