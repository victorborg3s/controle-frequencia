package dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.beanutils.PropertyUtils;
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
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}
		
		CriteriaQuery<?> cq = session.getCriteriaBuilder().createQuery(Funcionario.class);
		cq.from(Funcionario.class);
		data = (List<Funcionario>) session.createQuery(cq).getResultList();

		tr.commit();
		
		return data;
	}
	
	public static Object[][] getAllFuncionariosAsArray() {
		List<Funcionario> data = getAllFuncionarios();
		int fieldQuantity = PropertyUtils.getPropertyDescriptors(Funcionario.class).length;
		Object[][] arrayData = new Object[data.size()][fieldQuantity];

		int row = 0;
		for (Funcionario funcionario : data) {
			arrayData[row][0] = funcionario.getId();
			arrayData[row][1] = funcionario.getNome();
			arrayData[row][2] = funcionario.getSenha();
			arrayData[row][3] = funcionario.getAtivo();
			row++;
		}
		
		return arrayData;
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
