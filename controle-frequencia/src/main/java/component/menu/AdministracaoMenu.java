package component.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class AdministracaoMenu extends JMenu {

	private static final long serialVersionUID = -1646690183936252590L;

	public AdministracaoMenu() {
		super("Administra��o");
		this.add(new JMenuItem("Par�metros do Sistema"));
		this.add(new JMenuItem("Cadastro de Funcion�rios"));
		this.add(new JMenuItem("Ajuste de Registro"));
		this.add(new JMenuItem("Relat�rio de Frequ�ncia"));
	}
	
}
