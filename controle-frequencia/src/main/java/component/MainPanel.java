package component;

import javax.swing.JPanel;

import component.button.RegistrarEntradaButton;
import component.button.RegistrarSaidaButton;
import component.listeners.MenuToggleListener;
import net.miginfocom.swing.MigLayout;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 8016431364767538194L;
	private MainWindowFrame mainFrame;
	
	public MainPanel(MainWindowFrame frame) throws Exception {
		super(new MigLayout());
		
		this.setFocusable(true);
		this.mainFrame = frame;
		this.addKeyListener(new MenuToggleListener(this.mainFrame));
		
		try {
			this.add(new TimerPanel(), "span 2, wrap 30px, gapleft 40px, gaptop 100px");
		} catch (Exception e) {
			throw new Exception("Erro ao tentar adicionar timer à interface.",
					e);
		}
		try {
			this.add(new RegistrarEntradaButton(), "gapleft 40px");
			this.add(new RegistrarSaidaButton(), "gapleft 32px");
		} catch (Exception e) {
			throw new Exception("Erro ao tentar adicionar botões à interface.",
					e);
		}
	}
}
