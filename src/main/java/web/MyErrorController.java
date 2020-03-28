package web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController  {
 
    @RequestMapping("/error")
    public String handleError() {
        //do something like logging
//    	SecurityContextHolder.clearContext();
    	return "error";
    }
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
}