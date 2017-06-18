package component;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import component.menu.MainMenuBar;
import util.UtilFacade;

public class MainWindowFrame extends JFrame {

	private static final long serialVersionUID = -3865801470298239368L;
	private JFrame self;

	public MainWindowFrame() throws Exception {
		super("Controle de Frequencia");
		self = this;
		this.setSize(800, 600);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setJMenuBar(new MainMenuBar());
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			    System.out.println("MainWindowFrame closing event received");
			    int dialogResult = JOptionPane.showConfirmDialog(self, "Deseja realmente sair do sistema?","Sair do Sistema", JOptionPane.OK_CANCEL_OPTION);
			    if(dialogResult == JOptionPane.OK_OPTION){
				    UtilFacade.prepareToExitApplication();
				    self.dispose();
				    System.exit(0);
			    }
			}
		});
		
		try {
			this.setContentPane(new MainPanel());
		} catch (Exception e) {
			throw new Exception("Erro ao tentar adicionar painel principal à interface.", e);
		}
		
		UtilFacade.initializeApplication();
	}
	
}
