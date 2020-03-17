package bankApp.web;

import org.hibernate.Session;

import bankApp.session.SessionManager;

public class CustomerDashboardController {

	@RequestMapping("/customer_dashboard"){
		Session s = SessionManager.getSession("");
	}
	
}
