package bankApp.repositories;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import database.SessionManager;

import model.User;

@Component(value = "userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
		return new UserDetailsImpl(u);
	}

}