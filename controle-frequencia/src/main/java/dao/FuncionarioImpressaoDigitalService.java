package dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entity.FuncionarioImpressaoDigital;
import util.HibernateUtils;

public class FuncionarioImpressaoDigitalService {
	
	@SuppressWarnings("unchecked")
	public static List<FuncionarioImpressaoDigital> getAllFuncionarioImpressaoDigital() {
		List<FuncionarioImpressaoDigital> data;

		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}

		CriteriaQuery<?> cq = session.getCriteriaBuilder().createQuery(FuncionarioImpressaoDigital.class);
		cq.from(FuncionarioImpressaoDigital.class);
		data = (List<FuncionarioImpressaoDigital>) session.createQuery(cq).getResultList();

		tr.commit();

		return data;
	}
	
}
