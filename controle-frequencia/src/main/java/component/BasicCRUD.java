package component;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.apache.commons.beanutils.PropertyUtils;

import dao.GenericService;
import entity.BaseEntity;
import net.miginfocom.swing.MigLayout;

public class BasicCRUD extends JDialog {

	private static final long serialVersionUID = 8032774481074295385L;
	private static final int ROW_EDITING_NONE = -11;

	private JPanel contentPanel;
	private List<?> data;
	private JTable dataGrid;
	private String[] fieldsTitle;
	private String[] fieldsName;
	private Class<?>[] fieldsType;
	private int currentEditingRow = ROW_EDITING_NONE;
	private JButton inserir, editar, salvar;

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
	public BasicCRUD(JFrame frame, String title, Class<?> clazz, String[] fieldsTitle, String[] fieldsName,
			Class<?>[] fieldsType) {
		super(frame, title, Dialog.ModalityType.DOCUMENT_MODAL);

		this.fieldsName = fieldsName;
		this.fieldsTitle = fieldsTitle;
		this.fieldsType = fieldsType;

		configureInserirButton();
		configureEditarButton();
		configureSalvarButton();
		initDataGrid(clazz);

		setupDefaultOverallAppearance();
	}

	private void configureSalvarButton() {
		this.salvar = new JButton("Salvar");
		this.salvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dataGrid.isEditing()) {
					dataGrid.getCellEditor().stopCellEditing();
					currentEditingRow = ROW_EDITING_NONE;
				}
			}
		});
	}

	private void configureEditarButton() {

		this.editar = new JButton("Editar");
		this.editar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dataGrid.getSelectedRow() > -1) {
					currentEditingRow = dataGrid.getSelectedRow();
					dataGrid.setEditingRow(dataGrid.getSelectedRow());
					for (int i = fieldsName.length - 1; i >= 0; i--) {
						dataGrid.editCellAt(currentEditingRow, i);
						dataGrid.getCellEditor(currentEditingRow, i);
					}
				}
			}
		});
	}

	private void configureInserirButton() {
		this.inserir = new JButton("Inseir");
		this.inserir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

	}

	private void setupDefaultOverallAppearance() {
		this.setSize(new Dimension(600, 450));

		dataGrid.setRowHeight(30);
		JScrollPane scrPane = new JScrollPane(dataGrid);
		scrPane.setPreferredSize(new Dimension(565, 360));

		contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout());
		contentPanel.add(inserir);
		contentPanel.add(editar);
		contentPanel.add(salvar, "wrap");
		contentPanel.add(scrPane, "span 5");
		this.add(contentPanel);

	}

	private void initDataGrid(Class<?> clazz) {
		this.data = GenericService.getAll(clazz);
		this.dataGrid = new JTable(new AbstractTableModel() {

			private static final long serialVersionUID = -1071314488385082837L;

			@Override
			public Class<?> getColumnClass(int column) {
				return fieldsType[column];
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return (row == currentEditingRow);
			}

			@Override
			public void setValueAt(Object value, int row, int col) {
				try {
					PropertyUtils.setProperty(data.get(row), fieldsName[col], value);
					this.fireTableCellUpdated(row, col);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Object value = null;
				try {
					value = PropertyUtils.getProperty(data.get(rowIndex), fieldsName[columnIndex]);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return value;
			}

			@Override
			public int getRowCount() {
				return data.size();
			}

			@Override
			public int getColumnCount() {
				return fieldsName.length;
			}
		});

		dataGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dataGrid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (currentEditingRow != ROW_EDITING_NONE) {
					if ((e.getFirstIndex() != currentEditingRow) || (e.getLastIndex() != currentEditingRow)) {
						dataGrid.getSelectionModel().setSelectionInterval(currentEditingRow, currentEditingRow);
					}
				}
			}
		});
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
		private Class<?> clazz;

		public DropDownItem(Integer id, String description, Class<?> clazz) {
			this.id = id;
			this.description = description;
			this.clazz = clazz;
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

		public Class<?> getClazz() {
			return clazz;
		}

	}

	private class ComboBoxItemTableCellEditor extends AbstractCellEditor implements TableCellEditor {

		private static final long serialVersionUID = 6754885924734535914L;
		private boolean cellEditingStopped = false;
		private JComboBox<DropDownItem> jComboBox = null;

		public ComboBoxItemTableCellEditor(JComboBox<DropDownItem> comboBox) {
			super();
			this.jComboBox = comboBox;
			jComboBox.addItemListener(new ItemListener() {
				@Override
				@SuppressWarnings("unchecked")
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						if (!cellEditingStopped) {
							JComboBox<DropDownItem> jcb = (JComboBox<DropDownItem>) e.getSource();
							Object selectedValue = null;
							DropDownItem ddi = (DropDownItem) jcb.getSelectedItem();
							if (ddi.getClazz().isEnum()) {
								selectedValue = ddi.getClazz().getEnumConstants()[ddi.getId()];
							} else if (BaseEntity.class.isAssignableFrom(ddi.getClazz())) {
								selectedValue = GenericService.load(ddi.getClazz(), new Integer(ddi.getId()));
							}
							dataGrid.getModel().setValueAt(selectedValue, dataGrid.getSelectedRow(),
									dataGrid.getSelectedColumn());
						}
						fireEditingStopped();
					}
				}
			});
			jComboBox.addPopupMenuListener(new PopupMenuListener() {
				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					cellEditingStopped = false;
				}

				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					cellEditingStopped = true;
					fireEditingCanceled();
				}

				@Override
				public void popupMenuCanceled(PopupMenuEvent e) {

				}
			});
		}

		@Override
		public Object getCellEditorValue() {
			return jComboBox.getSelectedItem();
		}

		@Override
		public boolean isCellEditable(EventObject anEvent) {
			JTable table = null;
			if ((anEvent != null) && (anEvent.getSource() != null)
					&& (JTable.class.isAssignableFrom(anEvent.getSource().getClass()))) {
				table = (JTable) anEvent.getSource();
			}
			return ((table != null) && (table.getSelectedRow() == currentEditingRow));
		}

		@Override
		public boolean shouldSelectCell(EventObject anEvent) {
			return true;
		}

		@Override
		public boolean stopCellEditing() {
			fireEditingStopped();
			return cellEditingStopped;
		}

		@Override
		public void cancelCellEditing() {
			// TODO Auto-generated method stub

		}

		@Override
		public void addCellEditorListener(CellEditorListener l) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeCellEditorListener(CellEditorListener l) {
			// TODO Auto-generated method stub

		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			DropDownItem auxDDI, selectedDDI = null;
			
			if (jComboBox != null) {
				for (int i = 0; i < jComboBox.getModel().getSize(); i++) {
					auxDDI = jComboBox.getModel().getElementAt(i);
					if (value.getClass().isEnum()) {
						if (auxDDI.getDescription().equals(value.toString())) {
							selectedDDI = auxDDI;
						}
					} else if (BaseEntity.class.isAssignableFrom(value.getClass())) {
						if (auxDDI.getId() == ((BaseEntity) value).getId().intValue()) {
							selectedDDI = auxDDI;
						}
					}
				}

				jComboBox.setSelectedItem(selectedDDI);
			}

			return jComboBox;
		}

	}

	private void setUpDropDownColumns(JTable table) {
		TableColumn tableColumn = null;

		for (int i = 0; i < this.fieldsType.length; i++) {
			if (BaseEntity.class.isAssignableFrom(this.fieldsType[i])) {
				tableColumn = table.getColumnModel().getColumn(i);
				List<?> innerData = GenericService.getAll(this.fieldsType[i]);
				JComboBox<DropDownItem> comboBox = new JComboBox<DropDownItem>();

				for (Object object : innerData) {
					comboBox.addItem(new DropDownItem((Integer) getDataValue(object, "id"), object.toString(),
							this.fieldsType[i]));
				}

				tableColumn.setCellEditor(new ComboBoxItemTableCellEditor(comboBox));
				tableColumn.setCellRenderer(new DefaultTableCellRenderer());
			} else if (this.fieldsType[i].isEnum()) {
				tableColumn = table.getColumnModel().getColumn(i);
				JComboBox<DropDownItem> comboBox = new JComboBox<DropDownItem>();

				for (int j = 0; j < this.fieldsType[i].getEnumConstants().length; j++) {
					comboBox.addItem(new DropDownItem(j, this.fieldsType[i].getEnumConstants()[j].toString(),
							this.fieldsType[i]));
				}

				tableColumn.setCellEditor(new ComboBoxItemTableCellEditor(comboBox));
				tableColumn.setCellRenderer(new DefaultTableCellRenderer());
			} else if (this.fieldsType[i].isAssignableFrom(Date.class)) {
				table.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {

					private static final long serialVersionUID = -5640539892438805072L;
					SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm");

					public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						if (value instanceof Date) {
							value = f.format(value);
						}
						return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					}
				});
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