package component.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import component.ListagemFuncionario;

public class AdministracaoMenu extends JMenu {

	private static final long serialVersionUID = -1646690183936252590L;
	private JFrame mainFrame;

	@SuppressWarnings("unused")
	private AdministracaoMenu() {
		
	}
	
	public AdministracaoMenu(JFrame frame) {
		super("Administração");
		mainFrame = frame;
		JMenuItem sysParam = new JMenuItem("Parâmetros do Sistema");
		JMenuItem crudFuncionario = new JMenuItem("Cadastro de Funcionários");
		JMenuItem admAjusteRegistro = new JMenuItem("Ajuste de Registro");
		JMenuItem relFrequencia = new JMenuItem("Relatório de Frequência");
		
		crudFuncionario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ListagemFuncionario dialog = new ListagemFuncionario(mainFrame);
				dialog.setVisible(true);
			}
		});
		
		admAjusteRegistro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		
		this.add(sysParam);
		this.add(crudFuncionario);
		this.add(admAjusteRegistro);
		this.add(relFrequencia);
	}
	
}
