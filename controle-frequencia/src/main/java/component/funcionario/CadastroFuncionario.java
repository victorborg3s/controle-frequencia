package component.funcionario;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.FuncionarioService;
import entity.Funcionario;

import javax.swing.JTextField;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastroFuncionario extends JDialog {

	private static final long serialVersionUID = -5753914351986818460L;
	private final JPanel contentPanel = new JPanel();
	private CadastroFuncionario self;
	private Funcionario funcionario = null;
	private JTextField codigoTextField;
	private JTextField nomeTextField;
	private JTextField senhaTextField;
	private JCheckBox ativoCheckBox;

	/**
	 * Create the dialog.
	 */
	public CadastroFuncionario(JDialog window, Integer id) {
		super(window, "Cadastro de Funcionario", true);
		self = this;
		setResizable(false);
		setBounds(100, 100, 305, 224);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		contentPanel.setLayout(new MigLayout("", "[95.00px][163.00]", "[23px][23px][][][]"));
		{
			JLabel codigoLabel = new JLabel("C\u00F3digo");
			JLabel senhaLabel = new JLabel("Senha");
			codigoTextField = new JTextField();
			codigoTextField.setEditable(false);
			codigoTextField.setColumns(8);
			senhaTextField = new JTextField();
			senhaTextField.setColumns(15);

			JLabel nomeLabel = new JLabel("Nome");
			nomeTextField = new JTextField();
			nomeTextField.setColumns(24);
			ativoCheckBox = new JCheckBox("Ativo");
			ativoCheckBox.setSelected(true);

			contentPanel.add(codigoLabel);
			contentPanel.add(senhaLabel, "wrap");
			contentPanel.add(codigoTextField);
			contentPanel.add(senhaTextField, "wrap");
			contentPanel.add(nomeLabel, "wrap");
			contentPanel.add(nomeTextField, "span 2, wrap");
			contentPanel.add(ativoCheckBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton salvarButton = new JButton("Salvar");
				salvarButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveFuncionario();
						self.dispose();
					}
				});
				salvarButton.setActionCommand("OK");
				buttonPane.add(salvarButton);
				getRootPane().setDefaultButton(salvarButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						self.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		carregarDadosFuncionario(id);
	}

	protected void saveFuncionario() {
		funcionario.setNome(nomeTextField.getText());
		funcionario.setSenha(senhaTextField.getText());
		funcionario.setAtivo(ativoCheckBox.isSelected());
		if (funcionario.getId() != null && funcionario.getId().intValue() != 0) {
			FuncionarioService.update(funcionario);
		} else {
			FuncionarioService.save(funcionario);
		}
	}

	private void carregarDadosFuncionario(Integer id) {
		if (id != null) {
			this.funcionario = FuncionarioService.getFuncionarioById(id);
			codigoTextField.setText(this.funcionario.getId().toString());
			nomeTextField.setText(this.funcionario.getNome());
			senhaTextField.setText(this.funcionario.getSenha());
			ativoCheckBox.setSelected(this.funcionario.getAtivo());
		} else {
			this.funcionario = new Funcionario();
		}
	}

}
