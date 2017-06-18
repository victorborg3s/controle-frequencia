package component.menu;

import javax.swing.JMenuBar;

public class MainMenuBar extends JMenuBar {

	private static final long serialVersionUID = -1907403146451296066L;

	public MainMenuBar() {
		super();
		this.add(new AdministracaoMenu());
	}
	
}
