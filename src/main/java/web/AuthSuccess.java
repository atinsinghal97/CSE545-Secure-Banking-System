package web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.mysql.cj.Session;

import constants.Constants;
import database.SessionManager;
import model.SigninHistory;

public class AuthSuccess implements AuthenticationSuccessHandler {

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, 
    HttpServletResponse response, Authentication authentication)
    throws IOException {

	  final Logger logger = LoggerFactory.getLogger(this.getClass());
	  logger.warn("Login attempt for User '" + authentication.getName() + "' from IP: "+ WebSecurityConfig.getClientIP(request));
	  
	  String currentSessionUser = WebSecurityConfig.getCurrentSessionAuthority().filter(a -> a.equals(Constants.CUSTOMER) || a.equals(Constants.INDIVIDUAL) || a.equals(Constants.TIER1) || a.equals(Constants.TIER2) || a.equals(Constants.TIER3) || a.equals(Constants.ADMIN)).findFirst().orElse(null);

	  org.hibernate.Session session = SessionManager.getSession(currentSessionUser);
	  session.beginTransaction();
      
      SigninHistory history = new SigninHistory();
      history.setUsername(authentication.getName());
      history.setIpAddress(WebSecurityConfig.getClientIP(request));
      history.setLoggedIn(new Timestamp(System.currentTimeMillis()));
      
      session.save(history);
      session.getTransaction().commit();
      session.close();
	  
      handle(request, response, authentication);
      clearAuthenticationAttributes(request);
  }

  protected void handle(HttpServletRequest request, 
    HttpServletResponse response, Authentication authentication)
    throws IOException {
	  
      String targetUrl = determineTargetUrl(authentication);

      if (response.isCommitted()) {
          return;
      }

      redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  protected String determineTargetUrl(Authentication authentication) {
      boolean isUser = false;
      boolean isAdmin = false;
      boolean isTier2=false;
      
      final Logger logger = LoggerFactory.getLogger(this.getClass());
      logger.warn("Login SUCCESS. User Role: "+ authentication.getAuthorities());
    
      Collection<? extends GrantedAuthority> authorities
       = authentication.getAuthorities();
//      System.out.println("Test" + authentication);
      for (GrantedAuthority grantedAuthority : authorities) {
    	  System.out.println(grantedAuthority.getAuthority());
          if (grantedAuthority.getAuthority().equals("customer")) {
              isUser = true;
              break;
          } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
              isAdmin = true;
              break;
          }else if(grantedAuthority.getAuthority().equals("tier1")){
        	  return "/Tier1Dashboard";
          }
          else if (grantedAuthority.getAuthority().equals("tier2")) {
        	  isTier2 = true;
              break;
          }else if(grantedAuthority.getAuthority().equals("admin")){
        	  return "/Admin/Dashboard";
          }
      }

      if (isUser) {
          return "/homepage?user=true";}
//      } else if (isAdmin) {
//          return "/AdminDashboard";
//      } 
      else if(isTier2)
      {
    	  return "/Tier2/Dashboard";
      }
      else {
          throw new IllegalStateException();
      }
  }

  protected void clearAuthenticationAttributes(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      if (session == null) {
          return;
      }
      session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }

  public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
      this.redirectStrategy = redirectStrategy;
  }
  protected RedirectStrategy getRedirectStrategy() {
      return redirectStrategy;
  }
}