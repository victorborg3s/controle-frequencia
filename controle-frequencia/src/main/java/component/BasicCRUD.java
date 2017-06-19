package component;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import net.miginfocom.swing.MigLayout;
import util.HibernateUtils;

public class BasicCRUD extends JFrame {

	private static final long serialVersionUID = 8032774481074295385L;
	private JPanel contentPanel;
	private List<?> data;
	private JTable dataGrid;
	private String[] fieldsTitle;
	private String[] fieldsName;

	@SuppressWarnings("unused")
	private BasicCRUD() {

	}

	/**
	 * Create a form that allow basic operations over data table.
	 * 
	 * @param title
	 *            - window's title
	 * @param tableName
	 *            - table name which basic operations will be made
	 * @param fieldsTitle
	 *            - all fields title that will be shown in a grid
	 * @param fieldsName
	 *            - the fields that will be recovered from entity
	 */
	public BasicCRUD(String title, Class<?> clazz, String[] fieldsTitle, String[] fieldsName) {
		super(title);

		this.setLayout(new MigLayout());
		this.setDefaultOverallAppearance(clazz, fieldsTitle, fieldsName);
	}

	private void setDefaultOverallAppearance(Class<?> clazz, String[] fieldsTitle, String[] fieldsName) {
		initDataGrid(fieldsTitle);
		this.setSize(new Dimension(600, 450));
		contentPanel = new JPanel();
		this.add(contentPanel);
		contentPanel.add(new JButton("Incluir"));
		contentPanel.add(new JButton("Alterar"), "wrap");
		contentPanel.add(new JScrollPane(dataGrid), "span 6");
		this.fieldsName = fieldsName;
		this.fieldsTitle = fieldsTitle;
		
		HibernateUtils.addDummyFuncionarioData();

		SessionFactory sessFact = HibernateUtils.getSessionFactory();
		Session session = sessFact.getCurrentSession();
		org.hibernate.Transaction tr = session.beginTransaction();

		CriteriaQuery<?> cq = session.getCriteriaBuilder().createQuery(clazz);
		cq.from(clazz);
		data = session.createQuery(cq).getResultList();

		tr.commit();

		DefaultTableModel dtm = (DefaultTableModel) dataGrid.getModel();
		Iterator<?> it = data.iterator();
		Object[] row = new Object[20];
		int i = 0;
		while (it.hasNext()) {
			Object entity = it.next();
			for (String fieldName : fieldsName) {
				row[i] = getDataValue(entity, fieldName);
				i++;
			}
			i = 0;
			dtm.addRow(row);
		}

	}

	private void initDataGrid(String[] fieldsTitle2) {
		dataGrid = new JTable(new DefaultTableModel());
	}

	protected void setDataValue(Object entity, String fieldName, Object value) {
		Field field = null;

		try {
			field = entity.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		try {
			field.set(entity, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Object getDataValue(Object entity, String fieldName) {
		Object value = null;
		Field field = null;

		try {
			field = entity.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		try {
			value = field.get(entity);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public JTable getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(JTable dataGrid) {
		this.dataGrid = dataGrid;
	}

	public String[] getFieldsTitle() {
		return fieldsTitle;
	}

	public void setFieldsTitle(String[] fieldsTitle) {
		this.fieldsTitle = fieldsTitle;
	}

	public String[] getFieldsName() {
		return fieldsName;
	}

	public void setFieldsName(String[] fieldsName) {
		this.fieldsName = fieldsName;
	}

}
