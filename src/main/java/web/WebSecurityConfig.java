package web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
          .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
          .and()
          .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
          .and()
          .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
    }
 
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
	        .antMatchers("/users/**").hasRole("USER")//USER role can access /users/**
	        .antMatchers("/admin/**").hasRole("ADMIN")//ADMIN role can access /admin/**
	        .antMatchers("/login").permitAll()// anyone can access /quests/**
	        .anyRequest().authenticated()//any other request just need authentication
	        .and()
	        .formLogin()
		    .defaultSuccessUrl("/homepage", true)
	        .failureUrl("/login?error=true")
		    .and()
		    .logout()
		    .logoutUrl("/logout");//enable form login
    }
     
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}