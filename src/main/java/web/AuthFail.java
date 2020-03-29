package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class AuthFail 
implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
	    response.setStatus(HttpStatus.UNAUTHORIZED.value());
	    HttpSession session = request.getSession(false);
	    
	    if (exception.getMessage().equalsIgnoreCase("blocked")) {
	    	session.setAttribute("msg", "Your IP has been blocked for 24 hours. Please contact the bank.");
	    } else {
		    session.setAttribute("msg", exception.getMessage());
	    }
	    
	    response.sendRedirect("/login?error=true");
	}
}