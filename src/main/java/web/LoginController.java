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
    public String hello(Model model,
    		@RequestParam(value="username", required=false, defaultValue="") String username,
    		@RequestParam(value="password", required=false, defaultValue="") String password) {
        model.addAttribute("message", "");
        return "Login";
    }

	@PostMapping(path = "/process_login")
    public ModelAndView login(@RequestParam("username") final String username, @RequestParam("password") final String password, final HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authReq =
            new UsernamePasswordAuthenticationToken(username, password);
        System.out.println(username + ", " + password);
        Authentication auth = authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
        
        return new ModelAndView("redirect:/homepage");
	}

	@RequestMapping("/homepage")
    public String process(Model model,
    		@RequestParam(value="username", required=false, defaultValue="") String username,
    		@RequestParam(value="password", required=false, defaultValue="") String password) {
		return "CustomerDashboard";
    }
}
