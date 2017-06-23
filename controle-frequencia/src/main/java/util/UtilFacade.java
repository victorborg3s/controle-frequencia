package util;

import dao.FuncionarioService;
import dao.RegistroService;

public class UtilFacade {

	private UtilFacade() {
		
	}
	
	public static void prepareToExitApplication() {
		HibernateUtils.shutdown();
	}

	public static void initializeApplication() {
		HibernateUtils.getSessionFactory();

		

		// C�digo abaixo necess�rio apenas para teste
		// TODO remover linha abaixo
		FuncionarioService.addDummyFuncionarioData();
		RegistroService.addDummyRegistroData();
	}
	
}
