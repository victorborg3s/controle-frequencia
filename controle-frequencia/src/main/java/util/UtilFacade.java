package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UtilFacade {

	private UtilFacade() {
		
	}
	
	public static void prepareToExitApplication() {
		
	}

	public static void initializeApplication() {
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		org.hibernate.Transaction tr = session.beginTransaction();
		
		//TODO inserir parâmetros na base de dados
		
		//session.save(emp);
		tr.commit();
		System.out.println("Successfully inserted");
		sessFact.close();
	}
	
}
