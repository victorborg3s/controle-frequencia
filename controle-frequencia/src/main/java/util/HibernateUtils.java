package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	
	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration()
					.configure()
					.setProperty("hibernate.connection.url", "jdbc:derby:C:/controle_frequencia/derby")
					.buildSessionFactory();
		} catch (Throwable th) {

			System.err.println("Initial SessionFactory creation failed " + th);
			throw new ExceptionInInitializerError(th);

		}
	}

	public static SessionFactory getSessionFactory() {

		return sessionFactory;

	}

	public static void shutdown() {
		sessionFactory.close();
	}

}