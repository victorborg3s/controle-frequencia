package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import entity.Funcionario;

public class HibernateUtils {
	
	private static final SessionFactory sessionFactory;

	static {
		try {
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
					.configure("hibernate.cfg.xml").build();
			Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
			sessionFactory = metaData.getSessionFactoryBuilder().build();
		} catch (Throwable th) {

			System.err.println("Enitial SessionFactory creation failed" + th);
			throw new ExceptionInInitializerError(th);

		}
	}

	public static SessionFactory getSessionFactory() {

		return sessionFactory;

	}

	public static void shutdown() {
		sessionFactory.close();
	}

	public static void addDummyFuncionarioData() {
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		org.hibernate.Transaction tr = session.beginTransaction();
		Funcionario func = null;
		for (int i = 0; i < 10; i++) {
			func = new Funcionario();
			func.setNome("Dummy Funcionario " + i);
			func.setSenha("abc123");
			session.save(func);
		}
		tr.commit();
	}
	
}