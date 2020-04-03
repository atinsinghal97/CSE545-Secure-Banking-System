package web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import database.SessionManager;
import model.SigninHistory;


@Controller
public class SignInHistoryController {

	@RequestMapping("/SignInHistory")
	public String page() {

//	@RequestMapping(value="/SignInHistory", method = RequestMethod.GET)
//	public ModelAndView page(final HttpServletRequest request) {

//        String currentSessionUser = request.getUserPrincipal().getName();
//		System.out.println(currentSessionUser);
//
//		Session s = SessionManager.getSession(currentSessionUser);
//		List<SigninHistory> history=null;
//
//		history=s.createQuery("FROM SigninHistory WHERE username = :username", SigninHistory.class).setParameter("username", currentSessionUser).getResultList();
//		System.out.println(history);
//		for(SigninHistory temp : history){
//			System.out.println("Login Attempts::"+temp.getId()+","+temp.getUsername()+","+temp.getIpAddress()+","+temp.getLoggedIn());
//		}
		


//		ModelAndView map = new ModelAndView("index");
//	    map.addObject("history", history);
//	    return map;
		

		return "SignInHistory";

	}
	@RequestMapping(value="/SignInHistory", method = RequestMethod.GET)
	public ModelAndView page(final HttpServletRequest request) {

        String currentSessionUser = request.getUserPrincipal().getName();
		System.out.println(currentSessionUser);

		Session s = SessionManager.getSession(currentSessionUser);
		List<SigninHistory> history=null;
		history=s.createQuery("FROM SigninHistory WHERE username = :username", SigninHistory.class).setParameter("username", currentSessionUser).getResultList();
		System.out.println(history);
		
		s.close();
		
		ModelAndView map = new ModelAndView("SignInHistory");
	    map.addObject("history", history);
	    return map;
	}
		
}