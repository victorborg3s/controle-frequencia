package component;

import javax.swing.JPanel;

import component.button.RegistrarEntradaButton;
import component.button.RegistrarSaidaButton;
import net.miginfocom.swing.MigLayout;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 8016431364767538194L;

	public MainPanel() throws Exception {
		super(new MigLayout());
		try {
			this.add(new TimerPanel(), "span 2, wrap 30px, gapleft 40px, gaptop 100px");
		} catch (Exception e) {
			throw new Exception("Erro ao tentar adicionar timer � interface.",
					e);
		}
		try {
			this.add(new RegistrarEntradaButton(), "gapleft 40px");
			this.add(new RegistrarSaidaButton(), "gapleft 32px");
		} catch (Exception e) {
			throw new Exception("Erro ao tentar adicionar bot�es � interface.",
					e);
		}
	}
}
