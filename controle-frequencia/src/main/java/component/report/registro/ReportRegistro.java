package component.report.registro;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import entity.Funcionario;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import report.FrequenciaReportFactory;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

public class ReportRegistro extends JDialog {

	private static final long serialVersionUID = -5753914351986818460L;
	private final JPanel contentPanel = new JPanel();
	private ReportRegistro self;
	private SimpleDateFormat dateFormat;
	private JComboBox<Funcionario> funcionarioComboBox;
	private JFormattedTextField inicioTextField;
	private JFormattedTextField fimTextField;
	
	/**
	 * Create the dialog.
	 */
	public ReportRegistro(JFrame mainFrame) {
		super(mainFrame, "Relatório de Registros por Funcionário", false);
		self = this;
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		setResizable(false);
		setBounds(mainFrame.getX() + 100, mainFrame.getY() + 100, 
				340, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		contentPanel.setLayout(new MigLayout("", 
									 		 "[140][140]",
									 		 "[][]"));
		{
			JLabel funcionarioLabel = new JLabel("Funcionario");
			JLabel periodoInicioLabel = new JLabel("Data Início");
			JLabel periodoFimLabel = new JLabel("Data Fim");
		
			try {
				inicioTextField = new JFormattedTextField(new MaskFormatter("##/##/####"));
				fimTextField = new JFormattedTextField(new MaskFormatter("##/##/####"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			inicioTextField.setInputVerifier(new InputVerifier() {
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
			fimTextField.setInputVerifier(new InputVerifier() {
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
			
			funcionarioComboBox = new JComboBox<Funcionario>(
					FuncionarioService.getAllFuncionariosAsArray()
					);
			funcionarioComboBox.setMinimumSize(new Dimension(310, (int)funcionarioComboBox.getSize().getHeight()));

			contentPanel.add(funcionarioLabel, 		"cell 0 0, span 2");
			contentPanel.add(funcionarioComboBox, 	"cell 0 1, span 2");
			contentPanel.add(periodoInicioLabel, 	"cell 0 2, growx");
			contentPanel.add(periodoFimLabel, 		"cell 1 2, growx");
			contentPanel.add(inicioTextField, 		"cell 0 3, growx");
			contentPanel.add(fimTextField, 			"cell 1 3, growx");
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton imprimirButton = new JButton("Imprimir");
				imprimirButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						imprimir();
					}
				});
				imprimirButton.setActionCommand("PRINT");
				buttonPane.add(imprimirButton);
				getRootPane().setDefaultButton(imprimirButton);
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

	}

	protected void imprimir() {
		FrequenciaReportFactory report = new FrequenciaReportFactory();
		try {
			report.imprimir((Funcionario)funcionarioComboBox.getSelectedItem(), 
					dateFormat.parse(inicioTextField.getText()),
					dateFormat.parse(fimTextField.getText()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
