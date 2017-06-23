package component.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import component.BasicCRUD;
import entity.Funcionario;
import entity.Registro;
import entity.RegistroTipo;

public class AdministracaoMenu extends JMenu {

	private static final long serialVersionUID = -1646690183936252590L;

	public AdministracaoMenu() {
		super("Administração");
		JMenuItem sysParam = new JMenuItem("Parâmetros do Sistema");
		JMenuItem crudFuncionario = new JMenuItem("Cadastro de Funcionários");
		JMenuItem admAjusteRegistro = new JMenuItem("Ajuste de Registro");
		JMenuItem relFrequencia = new JMenuItem("Relatório de Frequência");
		
		crudFuncionario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] fieldsTitle = {"Código", "Nome", "Senha", "Ativo"};
				String[] fieldsName = {"id", "nome", "senha", "ativo"};
				Class<?>[] fieldsType = {Integer.class, String.class, String.class, Boolean.class};
				BasicCRUD telaFuncionario = new BasicCRUD("Cadastro de Funcionários", 
						Funcionario.class, fieldsTitle, fieldsName, fieldsType);
				telaFuncionario.setVisible(true);
			}
		});
		
		admAjusteRegistro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] fieldsTitle = {"Funcionário", "Tipo", "Momento"};
				String[] fieldsName = {"funcionario", "tipo", "momento"};
				Class<?>[] fieldsType = {Funcionario.class, RegistroTipo.class, Date.class};
				BasicCRUD telaFuncionario = new BasicCRUD("Registros de Ponto", 
						Registro.class, fieldsTitle, fieldsName, fieldsType);
				telaFuncionario.setVisible(true);
			}
		});
		
		this.add(sysParam);
		this.add(crudFuncionario);
		this.add(admAjusteRegistro);
		this.add(relFrequencia);
	}
	
}
