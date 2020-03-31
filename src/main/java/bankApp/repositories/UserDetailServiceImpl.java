package bankApp.repositories;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import database.SessionManager;

import model.User;
import security.LoginAttemptService;
import web.WebSecurityConfig;

@Component(value = "userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private LoginAttemptService loginAttemptService;
  
    @Autowired
    private HttpServletRequest request;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String ip = WebSecurityConfig.getClientIP(request);
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }
		
		Session s = SessionManager.getSession("");
		User u = null;
		try {
			u = s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			throw new UsernameNotFoundException("Invalid username or password.");
		} finally {
			s.close();
		}

		System.out.println("USER: " + u.getUsername());
		System.out.println("IP: " + ip);
//		final Logger logger = LoggerFactory.getLogger(this.getClass());
//      Prints when user is found but password is incorrect.		
//		logger.warn("Login attempt for User '" + u.getUsername() + "' from IP: "+ ip);
		return new UserDetailsImpl(u);
	}

}