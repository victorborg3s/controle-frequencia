package component.registro;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import entity.Funcionario;
import entity.RegistroTipo;

import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.RegistroService;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class ListagemRegistro extends JDialog {

	private static final long serialVersionUID = -7936096922182586772L;
	private final JPanel contentPanel = new JPanel();
	private ListagemRegistro self;
	private JTable table;

	/**
	 * Create the dialog.
	 */
	public ListagemRegistro(JFrame frame) {
		super(frame, "Registros", true);
		self = this;
		setResizable(false);
		setBounds(100, 100, 600, 387);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			contentPanel.add(scrollPane);
			{
				table = new JTable(new DefaultTableModel());
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				refreshData();
				scrollPane.setViewportView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton newButton = new JButton("Novo");
				newButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						carregarCadastro(null);
					}
				});
				buttonPane.add(newButton);
			}
			{
				JButton editButton = new JButton("Editar");
				editButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (table.getSelectedRow() != -1) {
							carregarCadastro(table.getModel().getValueAt(table.getSelectedRow(), 0));
						} else {
							JOptionPane.showMessageDialog(contentPanel,
									"É necessário escolher uma linha da listagem para editar.");
						}
					}
				});
				buttonPane.add(editButton);
			}
			{
				JButton cancelButton = new JButton("Fechar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						self.dispose();
					}
				});
				cancelButton.setActionCommand("Fechar");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void carregarCadastro(Object value) {
		CadastroRegistro cadastro = new CadastroRegistro(this, (Integer) value);
		cadastro.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				refreshData();
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		cadastro.setVisible(true);
	}

	protected void refreshData() {
		table.setModel(new DefaultTableModel(RegistroService.getAllRegistrosAsArray(),
				new String[] { "C\u00F3digo", "Funcionario", "Tipo", "Momento" }) {

			private static final long serialVersionUID = 8353999914011601804L;
			Class<?>[] columnTypes = new Class[] { Integer.class, Funcionario.class, RegistroTipo.class, Date.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(3).setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = new JLabel();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				label.setText(dateFormat.format((Date) value));
				return label;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
	}

}
