package component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import dao.FuncionarioService;

import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class ListagemFuncionario extends JDialog {

	private static final long serialVersionUID = -7936096922182586772L;
	private final JPanel contentPanel = new JPanel();
	private ListagemFuncionario self;
	private JTable funcionarioTable;

	/**
	 * Create the dialog.
	 */
	public ListagemFuncionario(JFrame frame) {
		super(frame, "Cadastro de Funcionário", true);
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
				funcionarioTable = new JTable(new DefaultTableModel() {
					private static final long serialVersionUID = 6293777456570174842L;
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
					@Override
					public void setValueAt(Object aValue, int row, int column) {
					}
				});
				funcionarioTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				funcionarioTable.setModel(new DefaultTableModel(FuncionarioService.getAllFuncionariosAsArray(),
						new String[] { "C\u00F3digo", "Nome", "Senha", "Ativo" }) {

					private static final long serialVersionUID = 9083678430887126590L;

					Class<?>[] columnTypes = new Class[] { Integer.class, String.class, String.class, Boolean.class };

					public Class<?> getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
				scrollPane.setViewportView(funcionarioTable);
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
						carregarCadastroFuncionario(null);
					}
				});
				buttonPane.add(newButton);
			}
			{
				JButton editButton = new JButton("Editar");
				editButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (funcionarioTable.getSelectedRow() != -1) {
							carregarCadastroFuncionario(
									funcionarioTable.getModel().getValueAt(funcionarioTable.getSelectedRow(), 0));
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

	protected void carregarCadastroFuncionario(Object value) {
		CadastroFuncionario cadFuncionario = new CadastroFuncionario(this, (Integer)value);
		cadFuncionario.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowClosed(WindowEvent e) {
				refreshData();
			}
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		cadFuncionario.setVisible(true);
	}

	protected void refreshData() {
		funcionarioTable.setModel(new DefaultTableModel(FuncionarioService.getAllFuncionariosAsArray(),
				new String[] { "C\u00F3digo", "Nome", "Senha", "Ativo" }) {

			private static final long serialVersionUID = 9083678430887126590L;

			Class<?>[] columnTypes = new Class[] { Integer.class, String.class, String.class, Boolean.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
	}

}
