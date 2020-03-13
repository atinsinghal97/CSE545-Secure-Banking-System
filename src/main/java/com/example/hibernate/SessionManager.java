package com.example.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionManager {
	private static SessionFactory sessionFactory = null;
	private static StandardServiceRegistry registry = null;
	
	public static void init() {
		registry = new StandardServiceRegistryBuilder()
			.configure() // configures settings from hibernate.cfg.xml
			.build();
	
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
			throw e;
		}
	}
	
	// TODO: Create Session based on role
	public static Session getSession(String role) {
		if (registry == null) init();
		/*
		SessionManager s = new SessionManager();
		
		Session session = s.getSession("");
		
		session.beginTransaction();
		List result = session.createQuery( "from User" ).list();
		for ( User user : (List<User>) result ) {
		    System.out.println( user.getUsername() );
		}
		session.getTransaction().commit();
		session.close();
		*/
		
		return sessionFactory.openSession();
	}
}