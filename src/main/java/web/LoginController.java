package web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@RequestMapping("/login")
    public String hello(Model model,
    		@RequestParam(value="username", required=false, defaultValue="") String username,
    		@RequestParam(value="password", required=false, defaultValue="") String password) {
        model.addAttribute("message", "");
        return "Login";
    }

	@RequestMapping("/perform_login")
    public boolean process(Model model,
    		@RequestParam(value="username", required=false, defaultValue="") String username,
    		@RequestParam(value="password", required=false, defaultValue="") String password) {
		return true;
    }
}
