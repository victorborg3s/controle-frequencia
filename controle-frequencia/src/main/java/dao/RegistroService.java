package dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entity.Funcionario;
import entity.Registro;
import entity.RegistroTipo;
import util.HibernateUtils;

public class RegistroService {

	@SuppressWarnings("unchecked")
	public static List<Registro> getAllFuncionarios() {
		List<Registro> data;
		
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}

		CriteriaQuery<?> cq = session.getCriteriaBuilder().createQuery(Registro.class);
		cq.from(Registro.class);
		data = (List<Registro>) session.createQuery(cq).getResultList();

		tr.commit();
		
		return data;
	}
	
	public static void addDummyRegistroData() {
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}
		Registro reg = null;
		List<Funcionario> funcionarios = FuncionarioService.getAllFuncionarios();
		
		for (Funcionario funcionario : funcionarios) {
			reg = new Registro();
			reg.setFuncionario(funcionario);
			reg.setTipo(RegistroTipo.ENTRADA);
			reg.setMomento(Calendar.getInstance().getTime());
			session.save(reg);

			reg = new Registro();
			reg.setFuncionario(funcionario);
			reg.setTipo(RegistroTipo.SAIDA);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR, 8);
			reg.setMomento(c.getTime());
			session.save(reg);
		}
		tr.commit();
	}	
	
}
