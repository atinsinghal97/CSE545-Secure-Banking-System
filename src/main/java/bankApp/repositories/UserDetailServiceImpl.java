package bankApp.repositories;


import java.util.Arrays;
import java.util.Collection;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.hibernate.SessionManager;
import com.example.hibernate.User;

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
		}

		if(u == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		System.out.println("USER: " + u.getUsername());
		return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), getAuthority(u));
	}
	
	private Collection<? extends GrantedAuthority> getAuthority(User u) {
		return (Collection<? extends GrantedAuthority>) Arrays.asList(new SimpleGrantedAuthority(u.getUserType()));
	}

}