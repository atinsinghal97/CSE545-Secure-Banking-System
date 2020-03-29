package web;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import database.SessionManager;
import forms.UserProfile;
import forms.UserProfileSearch;
import model.User;

@Component(value = "userServiceImpl")
public class UserServiceImpl {
	public UserProfileSearch getLockedUserProfiles(String role) {
		if (!WebSecurityConfig.currentSessionHasAnyAuthority("tier2"))
			return null;
		
		List<User> users = null;
		List<UserProfile> userProfiles = null;
		UserProfileSearch userProfileSearch = new UserProfileSearch();
		
		Session s = SessionManager.getSession("");
		
		try {
			users = s.createQuery("FROM User WHERE status = 0 AND role = :role", User.class)
					.setParameter("role", role).getResultList();
			userProfiles = users.stream().map(u -> new UserProfile(u)).collect(Collectors.toList());
			userProfileSearch.setUserProfiles(userProfiles);
		} catch (Exception e) {
			
		} finally {
			s.close();
		}
		
		return userProfileSearch;
	}
}
