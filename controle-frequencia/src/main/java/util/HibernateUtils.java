package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	
	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();;
			
			
//			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
//					.configure("hibernate.cfg.xml").build();
//			Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
//			sessionFactory = metaData.getSessionFactoryBuilder().build();
		} catch (Throwable th) {

			System.err.println("Initial SessionFactory creation failed" + th);
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