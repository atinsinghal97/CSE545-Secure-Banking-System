package web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import database.SessionManager;
import forms.Search;
import forms.SearchForm;
import model.Account;
import model.User;


@Controller
public class Tier2Actions{
	
	
	@RequestMapping(value = "/Tier2Search", method = RequestMethod.POST)
    public ModelAndView tier2Page(@RequestParam(required = true, name="accountnumber") String accNumber, Model model) {
		
		System.out.println("ACC NUMEBER  "+accNumber);
		
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
//		if (x == null || !x.isAuthenticated()) {
//			System.out.println("NOT AUTHENTICATED");
//			return new ModelAndView("Login");
//		}
		Boolean isTier2=false;
		for (GrantedAuthority grantedAuthority : x.getAuthorities()) {
			  System.out.println(grantedAuthority.getAuthority());
			    if (grantedAuthority.getAuthority().equals("tier2")) {
			    	System.out.println("Tier 2 Success");
			        isTier2 = true;
			        break;
			    }
			}
		if(isTier2)
		{
		Session s = SessionManager.getSession("");
		List<Account> account=null;
		System.out.println("Came here");
		account=s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", accNumber).getResultList();
		
		
		SearchForm searchForm = new SearchForm();
		//ArrayList<Search> search=new ArrayList<>();
		List<Search> search = new ArrayList<Search>();
		for(Account temp : account )
		{
			Boolean status=false;
			if(temp.getStatus()==1)
				status=true;
			Search tempSearch=new Search(temp.getAccountNumber(),temp.getCurrentBalance()+"",status);
			
			if(temp.getUser().getRole().equals("customer"))
			search.add(tempSearch);	
			System.out.println(temp.getUser().getRole());
			
		}
		System.out.println("Searchs");
		System.out.println(search);
		searchForm.setSearchs(search);
		
		
		
		
		return new ModelAndView("Tier2SearchAccount" , "searchForm", searchForm);
	
		}
		else
			return new ModelAndView("Login");
  
        
    }
	

	
	
	
	
	

}
