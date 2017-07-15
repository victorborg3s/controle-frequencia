package util;

public class UtilFacade {

	private UtilFacade() {
		
	}
	
	public static void prepareToExitApplication() {
		HibernateUtils.shutdown();
	}

	public static void initializeApplication() {
		HibernateUtils.getSessionFactory();

		

		// C�digo abaixo necess�rio apenas para teste
//		FuncionarioService.addDummyFuncionarioData();
//		RegistroService.addDummyRegistroData();
	}
	
}
