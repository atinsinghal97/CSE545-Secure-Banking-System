package web;

import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.stereotype.Component;

import constants.Constants;

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
//        http.csrf().disable();
        http.authorizeRequests()
	        .antMatchers("/users/**").hasRole(Constants.USER)//USER role can access /users/**
	        .antMatchers("/admin/**").hasRole(Constants.ADMIN)
	        .antMatchers("/Tier2/**").hasAuthority(Constants.TIER2)
	        .antMatchers("/Tier2**").hasAuthority(Constants.TIER2) 
	        .antMatchers("/Admin/**").hasAuthority(Constants.ADMIN)
	        .antMatchers("/Tier1**").hasAuthority(Constants.TIER1)
	        .antMatchers("/Tier1/**").hasAuthority(Constants.TIER1)
	        .antMatchers("/login").permitAll()// anyone can access /quests/**
	        .antMatchers("/externalregister").permitAll()// anyone can access /quests/**
	        .antMatchers("/register").permitAll()// anyone can access /quests/**
	        .antMatchers("/Update").permitAll()
	        .antMatchers("/Search").permitAll()
	        .antMatchers("/ChangeValue").permitAll()
	        .antMatchers("/Appointment").hasAuthority(Constants.CUSTOMER)
	        .antMatchers("/AppointmentCreate").hasAuthority(Constants.CUSTOMER)
	        .antMatchers("/ViewAppointments").hasAnyAuthority(Constants.TIER1,Constants.TIER2)
	        // .antMatchers("/Download").permitAll()
	        .antMatchers("/forgot_password").permitAll()
	        .antMatchers("/reset_password").permitAll()
	        .antMatchers("/change_password").hasAuthority(Constants.CHANGE_PASSWORD_PRIVILEGE)
	        .antMatchers("/AdminDashboard").hasAuthority(Constants.ADMIN)
	        .antMatchers("/EmployeeView").hasAuthority(Constants.ADMIN)
	        .antMatchers("/EmployeeInsert").hasAuthority(Constants.ADMIN)
	        .antMatchers("/EmployeeUpdate").hasAuthority(Constants.ADMIN)
	        .antMatchers("/EmployeeDelete").hasAuthority(Constants.ADMIN)
	        .antMatchers("/SystemLogs").hasAuthority(Constants.ADMIN)
	        .antMatchers("/homepage").hasAuthority(Constants.CUSTOMER)
	        .antMatchers("/ServiceRequest").hasAuthority(Constants.CUSTOMER)
	        .antMatchers("/PendingTransactions").hasAuthority(Constants.CUSTOMER)
	        .antMatchers("/CashiersCheck").hasAuthority(Constants.CUSTOMER)
	        .antMatchers("/PrimeAccount").hasAuthority(Constants.CUSTOMER)
	        .antMatchers("/generateAccountOtp").hasAuthority(Constants.CUSTOMER)
	        .antMatchers("/generateAppointmentOtp").hasAuthority(Constants.CUSTOMER)
	        .antMatchers("/SignInHistory").hasAnyAuthority(Constants.TIER1,Constants.TIER2,Constants.ADMIN,Constants.CUSTOMER)
	        .antMatchers("/js/**").permitAll()
	        .antMatchers("/css/**").permitAll()
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
        http.sessionManagement().maximumSessions(1);
//        http.headers()
//        	.xssProtection()
//            .disable()
//            .httpStrictTransportSecurity();
//            .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'"));
    }
     
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
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

    	return false;
    }
    
    public static void forceLogout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken)
        	return;
        auth.setAuthenticated(false);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public static String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}