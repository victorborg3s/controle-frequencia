package dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import entity.Funcionario;
import entity.FuncionarioImpressaoDigital;
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

	public static Funcionario[] getAllFuncionariosAsArray() {
		List<Funcionario> data = getAllFuncionarios();
		Funcionario[] arrayData = new Funcionario[data.size()];

		int row = 0;
		for (Funcionario funcionario : data) {
			arrayData[row] = funcionario;
			row++;
		}

		return arrayData;		
	}
	
	public static Object[][] getAllFuncionariosAsArrayOfArray() {
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

	public static Funcionario getFuncionarioById(Integer id) {
		Funcionario funcionario = null;

		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}
		
		Query<Funcionario> q = session.createQuery("SELECT f FROM Funcionario f LEFT JOIN FETCH f.digital d WHERE f.id = :id", Funcionario.class);
		q.setParameter("id", id);

		funcionario = (Funcionario) q.getSingleResult();

		tr.commit();

		return funcionario;
	}

	public static void update(Funcionario funcionario) {
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}

		session.update(funcionario);

		tr.commit();
	}

	public static void save(Funcionario funcionario) {
		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}

		session.save(funcionario);

		tr.commit();
	}

	private static Funcionario getFuncionarioByDigital(FuncionarioImpressaoDigital match) {
		List<Funcionario> funcionarios = null;

		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		Transaction tr = session.getTransaction();
		if (tr == null || !tr.isActive()) {
			tr = session.beginTransaction();
		}

		@SuppressWarnings("unchecked")
		Query<Funcionario> q = (Query<Funcionario>) session.createNamedQuery(Funcionario.QUERY_BY_DIGITAL);
		q.setParameter("digital", match);

		funcionarios = q.list();
		
		tr.commit();

		return funcionarios.get(0);
	}
	
	public Funcionario getByFingerprint(byte[] candidate) {
		FingerprintMatcher matcher = new FingerprintMatcher(new FingerprintTemplate(candidate));
		List<FuncionarioImpressaoDigital> digitais = FuncionarioImpressaoDigitalService.getAllFuncionarioImpressaoDigital();
		FuncionarioImpressaoDigital match = null;
		double higher = 0;
	    double threshold = 40;
		
	    for (FuncionarioImpressaoDigital digital : digitais) {
	        double score = matcher.match(digital.getFingerprintTemplate());
	        if (score > higher) {
	            higher = score;
	            match = digital;
	        }
	    }
	    
	    if (higher >= threshold) {
	    	return FuncionarioService.getFuncionarioByDigital(match);
	    } else {
	    	return null;
	    }
	}

}
