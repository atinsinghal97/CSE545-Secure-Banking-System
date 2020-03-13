package web;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthFail 
implements AuthenticationFailureHandler {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
	    response.setStatus(HttpStatus.UNAUTHORIZED.value());
	    HttpSession session = request.getSession(false);
	    session.setAttribute("msg", exception.getMessage());
	    
	    response.sendRedirect("/login?error=" + exception.getMessage());
	}
}