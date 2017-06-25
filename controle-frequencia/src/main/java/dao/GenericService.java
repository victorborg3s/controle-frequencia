package dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entity.BaseEntity;
import util.HibernateUtils;

public class GenericService {

	public static List<?> getAll(Class<?> clazz) {
		List<?> data = null;
		
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		org.hibernate.Transaction tr = session.beginTransaction();

		CriteriaQuery<?> cq = session.getCriteriaBuilder().createQuery(clazz);
		cq.from(clazz);
		data = session.createQuery(cq).getResultList();

		tr.commit();
		
		return data;
	}
	
	public static BaseEntity load(Class<?> clazz, Integer id) {
		BaseEntity entity = null;
		
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		org.hibernate.Transaction tr = session.beginTransaction();

		entity = (BaseEntity) session.get(clazz, id);

		tr.commit();
		
		return entity;
	}
	
}
