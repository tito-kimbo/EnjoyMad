package es.ucm.fdi.business.requesthandling;

import es.ucm.fdi.business.ProductionConfig;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.UserDAO;

public class Initializator extends ProductionConfig {

	public static void initialize(){
		//Without SQL
		ProductionConfig.init();
	}
	
	public static UserDAO getUserDAO(){
		return ProductionConfig.getUserDAO();
	}
	
	public static ClubDAO getClubDAO(){
		return ProductionConfig.getClubDAO();
	}
}
