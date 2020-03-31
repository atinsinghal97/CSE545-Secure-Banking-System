package bankApp.repositories;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import model.User;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private User user;
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(User user) {
		this.user = user;
		this.authorities = (Collection<? extends GrantedAuthority>) Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
//		System.out.println("ME: "+user.getUsername());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.getStatus() == 1;
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getStatus() == 1;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.getStatus() == 1;
	}

	@Override
	public boolean isEnabled() {
		return user.getStatus() == 1;
	}

	public User getUser() {
		return user;
	}

}