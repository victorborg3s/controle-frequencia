package component;

import java.awt.Component;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.beanutils.PropertyUtils;

import dao.GenericService;
import entity.BaseEntity;
import entity.RegistroTipo;
import net.miginfocom.swing.MigLayout;

public class BasicCRUD extends JFrame {

	private static final long serialVersionUID = 8032774481074295385L;
	private JPanel contentPanel;
	private List<?> data;
	private JTable dataGrid;
	private String[] fieldsTitle;
	private String[] fieldsName;
	private Class<?>[] fieldsType;

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
	public BasicCRUD(String title, Class<?> clazz, String[] fieldsTitle, String[] fieldsName, Class<?>[] fieldsType) {
		super(title);

		this.fieldsName = fieldsName;
		this.fieldsTitle = fieldsTitle;
		this.fieldsType = fieldsType;

		initDataGrid(clazz);

		setupDefaultOverallAppearance();
	}

	private void setupDefaultOverallAppearance() {
		this.setSize(new Dimension(600, 450));

		dataGrid.setRowHeight(30);
		JScrollPane scrPane = new JScrollPane(dataGrid);
		scrPane.setPreferredSize(new Dimension(565, 360));

		contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout());
		contentPanel.add(new JButton("Incluir"));
		contentPanel.add(new JButton("Alterar"), "wrap");
		contentPanel.add(scrPane, "span 6");
		this.add(contentPanel);

	}

	private void initDataGrid(Class<?> clazz) {
		this.data = GenericService.getAll(clazz);
		dataGrid = new JTable(new DefaultTableModel(fieldsTitle, data.size())) {

			private static final long serialVersionUID = -1071314488385082837L;

			@Override
			public Class<?> getColumnClass(int column) {
				return fieldsType[column];
			}

		};

		setUpDropDownColumns(this.dataGrid);

		Iterator<?> it = data.iterator();
		int linha = 0, coluna = 0;
		while (it.hasNext()) {
			Object entity = it.next();
			for (String fieldName : fieldsName) {
				dataGrid.setValueAt(getDataValue(entity, fieldName), linha, coluna);
				coluna++;
			}
			coluna = 0;
			linha++;
		}
	}

	private Object getDataValue(Object entity, String fieldName) {
		Object value = null;

		try {
			value = PropertyUtils.getProperty(entity, fieldName);
		} catch (SecurityException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}

		return value;
	}

	private class DropDownItem {
		private Integer id;
		private String description;

		public DropDownItem(Integer id, String description) {
			this.id = id;
			this.description = description;
		}

		public int getId() {
			return id;
		}

		public String getDescription() {
			return description;
		}

		public String toString() {
			return description;
		}
	}

	private class DropDownItemRenderer extends BasicComboBoxRenderer {

		private static final long serialVersionUID = -9152106777678754656L;

		public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (value != null) {
				DropDownItem item = (DropDownItem) value;
				setText(item.getDescription().toUpperCase());
			}

			if (index == -1) {
				DropDownItem item = (DropDownItem) value;
				setText("" + item.getId());
			}

			return this;
		}
	}

	private class ComboBoxTableCellRenderer extends JComboBox<Object> implements TableCellRenderer {

		private static final long serialVersionUID = -7733935933066447474L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setSelectedItem(value);
			return this;
		}

	}
	
	@SuppressWarnings("unchecked")
	private void setUpDropDownColumns(JTable table) {
		TableColumn tableColumn = null;

		for (int i = 0; i < this.fieldsType.length; i++) {
			if (this.fieldsType[i].isAssignableFrom(BaseEntity.class)) {
				tableColumn = table.getColumnModel().getColumn(i);
				List<?> innerData = GenericService.getAll(this.fieldsType[i]);
				JComboBox<?> comboBox = null;
				ComboBoxModel<DropDownItem> model1 = new DefaultComboBoxModel<DropDownItem>();
				ComboBoxModel<Object> model2 = new DefaultComboBoxModel<Object>();

				for (Object object : innerData) {
					((DefaultComboBoxModel<DropDownItem>) model1).addElement(new DropDownItem((Integer)getDataValue(object, "id"), object.toString()));
					((DefaultComboBoxModel<Object>) model2).addElement(new DropDownItem((Integer)getDataValue(object, "id"), object.toString()));
				}

				comboBox = new JComboBox<>(model1);
				comboBox.setRenderer(new DropDownItemRenderer());
				tableColumn.setCellEditor(new DefaultCellEditor(comboBox));
				
				ComboBoxTableCellRenderer renderer = new ComboBoxTableCellRenderer();
				renderer.setModel(model2);
				tableColumn.setCellRenderer(renderer);
				
//		        getContentPane().add(comboBox, BorderLayout.SOUTH );

				tableColumn.setCellEditor(new DefaultCellEditor(comboBox));
			} else if (this.fieldsType[i].isEnum()) {
				tableColumn = table.getColumnModel().getColumn(i);
				JComboBox<?> comboBox = null;
				ComboBoxModel<String> model1 = new DefaultComboBoxModel<String>();
				ComboBoxModel<Object> model2 = new DefaultComboBoxModel<Object>();

				//TODO Ajeitar para ser por reflexão. Nem sempre vai ser RegistroTipo.
				for (int j = 0; j < RegistroTipo.values().length; j++) {
					((DefaultComboBoxModel<String>)model1).addElement(RegistroTipo.values()[j].toString());
					((DefaultComboBoxModel<Object>) model2).addElement(RegistroTipo.values()[j].toString());
				}
				
				comboBox = new JComboBox<>(model1);
				tableColumn.setCellEditor(new DefaultCellEditor(comboBox));

				ComboBoxTableCellRenderer renderer = new ComboBoxTableCellRenderer();
				renderer.setModel(model2);
				tableColumn.setCellRenderer(renderer);
				
		        comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
//		        getContentPane().add(comboBox, BorderLayout.NORTH );

				tableColumn.setCellEditor(new DefaultCellEditor(comboBox));
			}
		}

		
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
