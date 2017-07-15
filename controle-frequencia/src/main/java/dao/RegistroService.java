package dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entity.Funcionario;
import entity.Registro;
import entity.RegistroTipo;
import util.HibernateUtils;

public class RegistroService {

	@SuppressWarnings("unchecked")
	public static List<Registro> getAllRegistros() {
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
		List<Funcionario> funcionarios = FuncionarioService.getAllFuncionarios();
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		Registro reg = null;

		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}
		for (Funcionario funcionario : funcionarios) {
			reg = new Registro();
			reg.setFuncionario(funcionario);
			reg.setTipo(RegistroTipo.ENTRADA);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR, -8);
			reg.setMomento(c.getTime());
			session.save(reg);

			reg = new Registro();
			reg.setFuncionario(funcionario);
			reg.setTipo(RegistroTipo.SAIDA);
			reg.setMomento(Calendar.getInstance().getTime());
			session.save(reg);
		}
		tr.commit();
	}

	public static Object[][] getAllRegistrosAsArray() {
		List<Registro> data = getAllRegistros();
		int fieldQuantity = PropertyUtils.getPropertyDescriptors(Registro.class).length;
		Object[][] arrayData = new Object[data.size()][fieldQuantity];

		int row = 0;
		for (Registro registro : data) {
			arrayData[row][0] = registro.getId();
			arrayData[row][1] = registro.getFuncionario();
			arrayData[row][2] = registro.getTipo();
			arrayData[row][3] = registro.getMomento();
			row++;
		}
		
		return arrayData;
	}

	public static void update(Registro registro) {
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}

		session.update(registro);

		tr.commit();
	}

	public static void save(Registro registro) {
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}

		session.save(registro);

		tr.commit();
	}

	public static Registro getRegistroById(Integer id) {
		Registro registro = null;

		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}

		registro = session.get(Registro.class, id);

		tr.commit();

		return registro;
	}
	
}
