package component.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import component.funcionario.ListagemFuncionario;
import component.parametro.ListagemParametro;
import component.registro.ListagemRegistro;
import component.report.registro.ReportRegistro;

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
		
		sysParam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ListagemParametro dialog = new ListagemParametro(mainFrame);
				dialog.setVisible(true);
			}
		});
		
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
				ListagemRegistro dialog = new ListagemRegistro(mainFrame);
				dialog.setVisible(true);
			}
		});
		
		relFrequencia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReportRegistro dialog = new ReportRegistro(mainFrame);
				dialog.setVisible(true);
			}
		});
		
		this.add(sysParam);
		this.add(crudFuncionario);
		this.add(admAjusteRegistro);
		this.add(relFrequencia);
	}
	
}
