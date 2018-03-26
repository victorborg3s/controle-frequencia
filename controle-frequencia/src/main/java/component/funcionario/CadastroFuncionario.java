package component.funcionario;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import app.ControleFrequencia;
import dao.FuncionarioService;
import entity.Funcionario;
import entity.FuncionarioImpressaoDigital;

import javax.swing.JTextField;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
	private JLabel fingerImage;

	/**
	 * Create the dialog.
	 */
	public CadastroFuncionario(JDialog window, Integer id) {
		super(window, "Cadastro de Funcionario", true);
		self = this;
		setResizable(false);
		setBounds(100, 100, 417, 227);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		contentPanel.setLayout(new MigLayout("", "[95.00px][163.00px][100px]", "[23px][23px][][][]"));
		{
			JLabel codigoLabel = new JLabel("C\u00F3digo");
			JLabel senhaLabel = new JLabel("Senha");
			fingerImage = new JLabel();
			//fingerImage.setSize(new Dimension(100, 120));
			ControleFrequencia.setJLabelToDraw(fingerImage);
			JLabel status = new JLabel("Status...");
			ControleFrequencia.setJLabelStatus(status);
			
			JButton readFinger = new JButton("Ler Digital");
			readFinger.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ControleFrequencia.start();
				}
			});
			
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

			contentPanel.add(codigoLabel,     "cell 0 0");
			contentPanel.add(senhaLabel,      "cell 1 0");
			contentPanel.add(fingerImage,     "cell 2 0, span 1 4");
			contentPanel.add(codigoTextField, "cell 0 1");
			contentPanel.add(senhaTextField,  "cell 1 1");
			contentPanel.add(nomeLabel,       "cell 0 2");
			contentPanel.add(nomeTextField,   "cell 0 3, spanx 2");
			contentPanel.add(ativoCheckBox,   "cell 0 4");
			contentPanel.add(status,          "cell 1 4");
			contentPanel.add(readFinger,      "cell 2 4");
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
		FuncionarioImpressaoDigital fingerPrint = new FuncionarioImpressaoDigital();
		fingerPrint.setFingerprint(ControleFrequencia.getEnroller().getTemplate().serialize());
		funcionario.setDigital(fingerPrint);
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
			BufferedImage image;
			try { // TODO: carregar com imagem padrão de digital
				image = ImageIO.read(new FileInputStream(new File("")));
				this.fingerImage.setIcon(new ImageIcon(
						image.getScaledInstance(100, 120, Image.SCALE_DEFAULT)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.funcionario = new Funcionario();
		}
	}

	// TODO: Cadastro da digital
	
}
