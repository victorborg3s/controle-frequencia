package component.registro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import dao.FuncionarioService;
import dao.RegistroService;
import entity.Funcionario;
import entity.Registro;
import entity.RegistroTipo;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;

public class CadastroRegistro extends JDialog {

	private static final long serialVersionUID = -5753914351986818460L;
	private final JPanel contentPanel = new JPanel();
	private CadastroRegistro self;
	private Registro registro;
	private SimpleDateFormat dateFormat;
	private JTextField codigoTextField;
	private JComboBox<Funcionario> funcionarioComboBox;
	private JComboBox<RegistroTipo> registroTipoComboBox;
	private JFormattedTextField momentoTextField;
	
	/**
	 * Create the dialog.
	 */
	public CadastroRegistro(JDialog window, Integer id) {
		super(window, "Cadastro de Funcionario", true);
		self = this;
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		setResizable(false);
		setBounds(100, 100, 305, 224);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		contentPanel.setLayout(new MigLayout("", "[95.00px][163.00,grow]", "[23px][23px][][][]"));
		{
			JLabel codigoLabel = new JLabel("C\u00F3digo");
			JLabel funcionarioLabel = new JLabel("Funcionario");
			JLabel tipoLabel = new JLabel("Tipo");
			JLabel momentoLabel = new JLabel("Momento");
		
			try {
				momentoTextField = new JFormattedTextField(new MaskFormatter("##/##/#### ##:##"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			momentoTextField.setInputVerifier(new InputVerifier() {
				@Override
				public boolean verify(JComponent input) {
					boolean verified = false;
					JFormattedTextField tf = (JFormattedTextField)input;
					try {
						dateFormat.parse(tf.getText());
						verified = true;
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return verified;
				}
			});
			contentPanel.add(momentoTextField, "cell 1 3, growx");
			codigoTextField = new JTextField();
			codigoTextField.setEditable(false);
			codigoTextField.setColumns(8);
			registroTipoComboBox = new JComboBox<RegistroTipo>(
					RegistroTipo.values()
					);
			funcionarioComboBox = new JComboBox<Funcionario>(
					FuncionarioService.getAllFuncionariosAsArray()
					);

			contentPanel.add(codigoLabel, "cell 0 0");
			contentPanel.add(funcionarioLabel, "cell 1 0");
			contentPanel.add(codigoTextField, "cell 0 1");
			contentPanel.add(funcionarioComboBox, "cell 1 1 2 1");
			contentPanel.add(tipoLabel, "cell 0 2");
			contentPanel.add(momentoLabel, "cell 1 2");
			contentPanel.add(registroTipoComboBox, "cell 0 3");
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton salvarButton = new JButton("Salvar");
				salvarButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						save();
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

		carregarDados(id);
	}

	protected void save() {
		registro.setFuncionario((Funcionario)funcionarioComboBox.getSelectedItem());
		registro.setMomento(new Date());
		registro.setTipo((RegistroTipo)registroTipoComboBox.getSelectedItem());
		try {
			registro.setMomento(dateFormat.parse(momentoTextField.getText()));
			if (registro.getId() != null && registro.getId().intValue() != 0) {
				RegistroService.update(registro);
			} else {
				RegistroService.save(registro);
			}
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(contentPanel,
					"Formato de data inválido.");
			e.printStackTrace();
		}
	}

	private void carregarDados(Integer id) {
		if (id != null) {
			this.registro = RegistroService.getRegistroById(id);
			codigoTextField.setText(this.registro.getId().toString());
			funcionarioComboBox.setSelectedItem(registro.getFuncionario());
			registroTipoComboBox.setSelectedItem(this.registro.getTipo());
			momentoTextField.setText(dateFormat.format(registro.getMomento()));
		} else {
			this.registro = new Registro();
		}
	}

}
