package web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import business.SysLogger;
import business.UserLog;

public class AuthSuccess implements AuthenticationSuccessHandler {
	
	SysLogger loginHistoryLogger;

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, 
    HttpServletResponse response, Authentication authentication)
    throws IOException 
  {
	  
      handle(request, response, authentication);
      clearAuthenticationAttributes(request);
  }

  protected void handle(HttpServletRequest request, 
    HttpServletResponse response, Authentication authentication)
    throws IOException 
  {

      String targetUrl = determineTargetUrl(authentication);
      
      loginHistoryLogger = new SysLogger();
      loginHistoryLogger.log("Login Attempt from IP: " + request.getRemoteAddr() + " recieved.");
  	  
  	  

  	  if (response.isCommitted()) {
          return;
      }

      redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  protected String determineTargetUrl(Authentication authentication) {
      boolean isUser = false;
      boolean isAdmin = false;
      boolean isTier2=false;
    
      Collection<? extends GrantedAuthority> authorities
       = authentication.getAuthorities();
      for (GrantedAuthority grantedAuthority : authorities) {
    	  System.out.println(grantedAuthority.getAuthority());
          if (grantedAuthority.getAuthority().equals("customer")) {
              isUser = true;
              try{
            	  loginHistoryLogger = new SysLogger();
            	  loginHistoryLogger.log("Customer login attempted");
              }
              catch(Exception e){}
              break;
              
          } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
              isAdmin = true;
              try{
            	  loginHistoryLogger = new SysLogger();
            	  loginHistoryLogger.log("Admin login attempted");
              }
              catch(Exception e){}
              break;
          }else if(grantedAuthority.getAuthority().equals("tier1")){
        	  try{
        		  loginHistoryLogger = new SysLogger();
        		  loginHistoryLogger.log("Tier 1 login attempted");
              }
              catch(Exception e){}
        	  return "/Tier1Dashboard";
          }
          else if (grantedAuthority.getAuthority().equals("tier2")) {
        	  try{
        		  loginHistoryLogger = new SysLogger();
        		  loginHistoryLogger.log("Tier 2 login attempted");
              }
              catch(Exception e){}
        	  isTier2 = true;
              break;
          }else if(grantedAuthority.getAuthority().equals("admin")){
        	  try{
        		  loginHistoryLogger = new SysLogger();
        		  loginHistoryLogger.log("Admin login attempted");
              }
              catch(Exception e){}
        	  return "/AdminDashboard";
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