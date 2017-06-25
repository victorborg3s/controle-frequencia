package component.menu;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class MainMenuBar extends JMenuBar {

	private static final long serialVersionUID = -1907403146451296066L;
	private JFrame mainFrame;

	public MainMenuBar(JFrame frame) {
		super();
		mainFrame = frame;
		this.add(new AdministracaoMenu(mainFrame));
	}
	
}
