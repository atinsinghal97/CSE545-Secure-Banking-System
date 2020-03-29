package security;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import bankApp.application.Application;
import database.SessionManager;
import model.User;

@Component
public class AuthenticationSuccessEventListener 
  implements ApplicationListener<AuthenticationSuccessEvent> {
 
    @Autowired
    private LoginAttemptService loginAttemptService;
 
    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails) 
          e.getAuthentication().getDetails();
        
        Application.executorService.submit(() -> {
            Session s = SessionManager.getSession("");
            Transaction txn = null;

            try {
            	User u = s.createQuery("FROM User WHERE username = :username AND status = 1", User.class)
            		.setParameter("username", e.getAuthentication().getName())
            		.getSingleResult();

            	txn = s.beginTransaction();
            	u.setIncorrectAttempts(0);
            	s.update(u);

            	if (txn != null && txn.isActive()) txn.commit();
            } catch (Exception ex) {
            	if (txn != null && txn.isActive()) txn.rollback();
            	ex.printStackTrace();
            } finally {
            	s.close();
            }
        });
        
        loginAttemptService.loginSucceeded(auth.getRemoteAddress());
    }
}
