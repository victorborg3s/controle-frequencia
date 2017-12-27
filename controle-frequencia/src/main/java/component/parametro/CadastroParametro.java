package component.parametro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ParametroService;
import entity.Parametro;
import entity.ParametroChave;

import javax.swing.JTextField;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastroParametro extends JDialog {

	private static final long serialVersionUID = -5753914351986818460L;
	private final JPanel contentPanel = new JPanel();
	private CadastroParametro self;
	private Parametro parametro;
	private JComboBox<ParametroChave> parametroChaveComboBox;
	private JTextField valorTextField;
	
	/**
	 * Create the dialog.
	 */
	public CadastroParametro(JDialog window, Integer id) {
		super(window, "Cadastro de Parâmetros", true);
		self = this;
		setResizable(false);
		setBounds(100, 100, 435, 224);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		contentPanel.setLayout(new MigLayout("", "[][]", "[][]"));
		{
			JLabel chaveLabel = new JLabel("Chave");
			JLabel valorLabel = new JLabel("Valor");
		
			valorTextField = new JTextField();
			valorTextField.setColumns(8);
			parametroChaveComboBox = new JComboBox<ParametroChave>(
					ParametroChave.values()
					);

			contentPanel.add(chaveLabel, "cell 0 0");
			contentPanel.add(valorLabel, "cell 1 0");
			contentPanel.add(parametroChaveComboBox, "cell 0 1");
			contentPanel.add(valorTextField, "cell 1 1");
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
		parametro.setChave((ParametroChave)parametroChaveComboBox.getSelectedItem());
		parametro.setValor(valorTextField.getText());
		if (parametro.getId() != null && parametro.getId().intValue() != 0) {
			ParametroService.update(parametro);
		} else {
			ParametroService.save(parametro);
		}
	}

	private void carregarDados(Integer id) {
		if (id != null) {
			this.parametro = ParametroService.getRegistroById(id);
			parametroChaveComboBox.setSelectedItem(this.parametro.getChave());
			valorTextField.setText(this.parametro.getValor());
		} else {
			this.parametro= new Parametro();
		}
	}

}
