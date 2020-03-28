package web;

import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Resource(name = "userDetailService")
	private UserDetailsService userDetailsService;
	
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	   
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    	 auth.userDetailsService(userDetailsService);
    }

    
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new AuthSuccess();
    }
 
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new AuthFail();
    }
    
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests()
	        .antMatchers("/users/**").hasRole("USER")//USER role can access /users/**
	        .antMatchers("/admin/**").hasRole("ADMIN")
	        .antMatchers("/Tier2/**").hasAuthority("tier2") 
	        .antMatchers("/Admin/**").hasAuthority("admin")
	        .antMatchers("/Tier1**").hasAuthority("tier1")
	        .antMatchers("/Tier1/**").hasAuthority("tier1")
	        .antMatchers("/login").permitAll()// anyone can access /quests/**
	        .antMatchers("/externalregister").permitAll()// anyone can access /quests/**
	        .antMatchers("/register").permitAll()// anyone can access /quests/**
	        .antMatchers("/Update").permitAll()
	        .antMatchers("/Search").permitAll()
	        .antMatchers("/ChangeValue").permitAll()
	        .antMatchers("/Appointment").hasAuthority("customer")
	        .antMatchers("/AppointmentCreate").hasAuthority("customer")
	        .antMatchers("/ViewAppointments").hasAnyAuthority("tier1","tier2")
	        .antMatchers("/Download").permitAll()
	        .antMatchers("/forgot_password").permitAll()
	        .antMatchers("/reset_password").permitAll()
	        .antMatchers("/change_password").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
	        .antMatchers("/AdminDashboard").hasAuthority("admin")
	        .antMatchers("/EmployeeView").hasAuthority("admin")
	        .antMatchers("/EmployeeInsert").hasAuthority("admin")
	        .antMatchers("/EmployeeUpdate").hasAuthority("admin")
	        .antMatchers("/EmployeeDelete").hasAuthority("admin")
	        .antMatchers("/SystemLogs").hasAuthority("admin")
	        .antMatchers("/homepage").hasAuthority("customer")
	        .anyRequest().authenticated()//any other request just need authentication
	        .and()
	        .formLogin()
	        .loginPage("/login")
	        .loginProcessingUrl("/process_login")
	        .successHandler(myAuthenticationSuccessHandler())
	        .failureHandler(customAuthenticationFailureHandler())
		    .and()
		    .logout()
		    .logoutUrl("/perform_logout")
		    .invalidateHttpSession(false)
		    .deleteCookies("JSESSIONID")
		    .logoutSuccessUrl("/login");
        http.requiresChannel().anyRequest().requiresSecure();
    }
     
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    public static Stream<String> getCurrentSessionAuthority() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if(auth == null || !auth.isAuthenticated() || (auth instanceof AnonymousAuthenticationToken)) {
    		return null;
    	}

    	return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }
    
    public static Boolean currentSessionHasAnyAuthority(String... authorities) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if(auth == null || !auth.isAuthenticated() || (auth instanceof AnonymousAuthenticationToken)) {
    	return false;
    	}

    	for (String authority : authorities) {
	    	for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
		    	if (grantedAuthority.getAuthority().equals(authority)) {
		    	return true;
		    	}
	    	}
    	}

    	return null;
    }
}