package component.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import component.BasicCRUD;
import entity.Funcionario;

public class AdministracaoMenu extends JMenu {

	private static final long serialVersionUID = -1646690183936252590L;

	public AdministracaoMenu() {
		super("Administra��o");
		JMenuItem sysParam = new JMenuItem("Par�metros do Sistema");
		JMenuItem crudFuncionario = new JMenuItem("Cadastro de Funcion�rios");
		JMenuItem admAjusteRegistro = new JMenuItem("Ajuste de Registro");
		JMenuItem relFrequencia = new JMenuItem("Relat�rio de Frequ�ncia");
		
		crudFuncionario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] fieldsTitle = {"C�digo", "Nome", "Senha"};
				String[] fieldsName = {"id", "nome", "senha"};
				BasicCRUD telaFuncionario = new BasicCRUD("Cadastro de Funcion�rios", 
						Funcionario.class, fieldsTitle, fieldsName);
				telaFuncionario.setVisible(true);
			}
		});
		
		this.add(sysParam);
		this.add(crudFuncionario);
		this.add(admAjusteRegistro);
		this.add(relFrequencia);
	}
	
}
