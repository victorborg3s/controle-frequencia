package component.registro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dao.FuncionarioService;
import dao.RegistroService;
import entity.Funcionario;
import entity.Registro;
import entity.RegistroTipo;

import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegistroPonto extends JDialog {

	private static final long serialVersionUID = -5753914351986818460L;
	private RegistroPonto self;
	private Funcionario funcionario = null;
	private RegistroTipo registroTipo;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	/**
	 * Create the dialog.
	 */
	public RegistroPonto(JFrame window, RegistroTipo tipo) {
		super(window, true);
		if (tipo != null && tipo == RegistroTipo.ENTRADA) {
			this.setTitle("Registro de Entrada");
		} else if (tipo != null && tipo == RegistroTipo.SAIDA) {
			this.setTitle("Registro de Saída");
		}
		self = this;
		this.registroTipo = tipo;
		setResizable(false);
		setBounds(100, 100, 378, 152);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel mainPane = new JPanel();
			getContentPane().add(mainPane, BorderLayout.WEST);
			mainPane.setLayout(new MigLayout("", "[]", "[]"));

			JLabel lblCodigo = new JLabel("Código do Funcionário");
			mainPane.add(lblCodigo);
			JFormattedTextField txtCodigo = new JFormattedTextField(NumberFormat.getNumberInstance());
			txtCodigo.setColumns(20);
			mainPane.add(txtCodigo, "wrap");

			JLabel lblFuncionario = new JLabel("Funcionario: ");
			mainPane.add(lblFuncionario);
			JLabel lblFuncionarioValue = new JLabel("<nenhum funcionário identificado>");
			lblFuncionarioValue.setForeground(Color.BLUE);
			mainPane.add(lblFuncionarioValue, "wrap");

			txtCodigo.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					self.funcionario = FuncionarioService.getFuncionarioById(Integer.parseInt(txtCodigo.getText()));
					if (self.funcionario != null) {
						lblFuncionarioValue.setText(self.funcionario.getNome());
					} else {
						lblFuncionarioValue.setText("<nenhum funcionário identificado>");
					}
				}
			});
			txtCodigo.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						self.funcionario = FuncionarioService.getFuncionarioById(Integer.parseInt(txtCodigo.getText()));
						if (self.funcionario != null) {
							lblFuncionarioValue.setText(self.funcionario.getNome());
						} else {
							lblFuncionarioValue.setText("<nenhum funcionário identificado>");
						}
					}
				}
			});
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton salvarButton = new JButton("Ok");
				salvarButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (self.funcionario != null) {
							try {
								registrarFrequencia(self.registroTipo, self.funcionario);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							self.dispose();
						} else {
							JOptionPane.showMessageDialog(self,
									"Código de funcionário inválido. Nenhum funcionário identificado. "
											+ "Para poder efetivar um registro, é necessário digitar um código válido.",
									"Registro de Frequência Efetivado", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				salvarButton.setActionCommand("OK");
				buttonPane.add(salvarButton);
				getRootPane().setDefaultButton(salvarButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						self.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}

	protected void registrarFrequencia(RegistroTipo tipo, Funcionario funcionario) throws Exception {
		if (tipo == null) {
			throw new Exception("ERRO: o tipo de registro é obrigatório para salvar um registro de frequência.");
		}
		if (funcionario == null) {
			throw new Exception("ERRO: o funcionário é obrigatório para salvar um registro de frequência.");
		}

		Registro registro = new Registro();
		registro.setFuncionario(funcionario);
		registro.setTipo(tipo);
		registro.setMomento(new Date());
		RegistroService.save(registro);
		String message = "Registro de ";
		if (tipo == RegistroTipo.ENTRADA) {
			message = message + "entrada ";
		} else {
			message = message + "saída ";
		}
		message = message + " efetivado para o funcionário [" + funcionario.getNome() + "] em ["
				+ dateFormat.format(registro.getMomento()) + "].";
		JOptionPane.showMessageDialog(this, message, "Registro de Frequência Efetivado",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
