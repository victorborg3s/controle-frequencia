package dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entity.Parametro;
import util.HibernateUtils;

public class ParametroService {

	@SuppressWarnings("unchecked")
	public static List<Parametro> getAllRegistros() {
		List<Parametro> data;

		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}

		CriteriaQuery<?> cq = session.getCriteriaBuilder().createQuery(Parametro.class);
		cq.from(Parametro.class);
		data = (List<Parametro>) session.createQuery(cq).getResultList();

		tr.commit();

		return data;
	}

	public static Object[][] getAllParametrosAsArray() {
		List<Parametro> data = getAllRegistros();
		int fieldQuantity = PropertyUtils.getPropertyDescriptors(Parametro.class).length;
		Object[][] arrayData = new Object[data.size()][fieldQuantity];

		int row = 0;
		for (Parametro registro : data) {
			arrayData[row][0] = registro.getId();
			arrayData[row][1] = registro.getChave();
			arrayData[row][2] = registro.getValor();
			row++;
		}
		
		return arrayData;
	}

	public static void update(Parametro registro) {
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}

		session.update(registro);

		tr.commit();
	}

	public static void save(Parametro registro) {
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}

		session.save(registro);

		tr.commit();
	}

	public static Parametro getRegistroById(Integer id) {
		Parametro registro = null;

		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}

		registro = session.get(Parametro.class, id);

		tr.commit();

		return registro;
	}
	
}
