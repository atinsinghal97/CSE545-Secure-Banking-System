package security;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import bankApp.application.Application;
import database.SessionManager;
import model.User;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    private LoginAttemptService loginAttemptService;

    private final int MAX_ATTEMPT = 3;
 
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails) 
          e.getAuthentication().getDetails();
        System.out.println("FAILED LOGIN BY: " + e.getAuthentication().getName());
        
        Application.executorService.submit(() -> {
            Session s = SessionManager.getSession("");
            Transaction txn = null;

            try {
            	User u = s.createQuery("FROM User WHERE username = :username", User.class)
            		.setParameter("username", e.getAuthentication().getName())
            		.getSingleResult();

            	txn = s.beginTransaction();
            	u.setIncorrectAttempts(u.getIncorrectAttempts() + 1);
            	if (u.getIncorrectAttempts() > MAX_ATTEMPT) {
            		u.setStatus(0);
            	}
            	s.update(u);
            	if (txn != null && txn.isActive()) txn.commit();
            	
            } catch (Exception ex) {
            	if (txn != null && txn.isActive()) txn.rollback();
            	ex.printStackTrace();
            } finally {
            	s.close();
            }
        });

        final Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.warn("Login attempt for User '" + e.getAuthentication().getName() + "' received from IP: " + auth.getRemoteAddress());
        logger.warn("Login attempt FAILED.");
        loginAttemptService.loginFailed(auth.getRemoteAddress());
    }
}