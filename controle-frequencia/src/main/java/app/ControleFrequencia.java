package app;

import component.MainWindowFrame;

public class ControleFrequencia {

	private MainWindowFrame janelaPrincipal;

	public static void main(String[] args) {
		new ControleFrequencia().apresentaTelaPrincipal();
	}

	public ControleFrequencia() {
		try {
			this.janelaPrincipal = new MainWindowFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void apresentaTelaPrincipal() {
		janelaPrincipal.setVisible(true);
	}

}
