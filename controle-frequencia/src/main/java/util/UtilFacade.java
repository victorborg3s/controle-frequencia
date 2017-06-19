package util;

public class UtilFacade {

	private UtilFacade() {
		
	}
	
	public static void prepareToExitApplication() {
		HibernateUtils.shutdown();
	}

	public static void initializeApplication() {
		HibernateUtils.getSessionFactory();

	}
	
}
