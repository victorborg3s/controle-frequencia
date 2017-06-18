package component.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class AdministracaoMenu extends JMenu {

	private static final long serialVersionUID = -1646690183936252590L;

	public AdministracaoMenu() {
		super("Administração");
		this.add(new JMenuItem("Parâmetros do Sistema"));
		this.add(new JMenuItem("Cadastro de Funcionários"));
		this.add(new JMenuItem("Ajuste de Registro"));
		this.add(new JMenuItem("Relatório de Frequência"));
	}
	
}
