package bankApp.repositories;

import org.hibernate.Session;

import database.SessionManager;

public class UserServiceRepository {

	public static Boolean userExists(String username, String email, String ssn) {
		Session session = SessionManager.getSession("");
		try {
			Boolean exists = false;
			exists = session.createQuery("SELECT COUNT(*) > 0 FROM User WHERE username = :username", Boolean.class)
				.setParameter("username", username)
				.getSingleResult();

			if (exists) return true;

			exists = session.createQuery("SELECT COUNT(*) > 0 FROM UserDetail WHERE ssn = :ssn OR email = :email", Boolean.class)
				.setParameter("ssn", ssn)
				.setParameter("email", email)
				.getSingleResult();

			if (exists) return true;

		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}

		return false;
	}
}