package dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entity.Funcionario;
import util.HibernateUtils;

public class FuncionarioService {

	@SuppressWarnings("unchecked")
	public static List<Funcionario> getAllFuncionarios() {
		List<Funcionario> data;
		
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
//		Transaction tr = session.getTransaction();
//		if (tr == null || !tr.isActive()) {
//			tr = session.beginTransaction();
//		}

		CriteriaQuery<?> cq = session.getCriteriaBuilder().createQuery(Funcionario.class);
		cq.from(Funcionario.class);
		data = (List<Funcionario>) session.createQuery(cq).getResultList();

//		tr.commit();
		
		return data;
	}
	
	public static void addDummyFuncionarioData() {
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}
		Funcionario func = null;
		for (int i = 0; i < 10; i++) {
			func = new Funcionario();
			func.setNome("Dummy Funcionario " + i);
			func.setSenha("abc123");
			func.setAtivo(new Boolean(true));
			session.save(func);
		}
		tr.commit();
	}
	
}
