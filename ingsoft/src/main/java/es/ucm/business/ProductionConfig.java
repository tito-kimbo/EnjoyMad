package es.ucm.business;

import es.ucm.fdi.business.profilemanagement.ProfileManagerSA;
import es.ucm.fdi.business.profilemanagement.ProfileManagerSAImp;
import es.ucm.fdi.business.searchengine.FilterMapper;
import es.ucm.fdi.business.searchengine.SearchEngineSA;
import es.ucm.fdi.business.searchengine.SearchEngineSAImp;
import es.ucm.fdi.business.sessionmanagement.SessionManagerSA;
import es.ucm.fdi.business.sessionmanagement.SessionManagerSAImp;
import es.ucm.fdi.business.tagmanagement.TagManagerSA;
import es.ucm.fdi.business.tagmanagement.TagManagerSAImp;
import es.ucm.fdi.business.ticketmanagement.TicketManagerSA;
import es.ucm.fdi.business.ticketmanagement.TicketManagerSAImp;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.ClubDAOMySqlImp;
import es.ucm.fdi.integration.TagDAO;
import es.ucm.fdi.integration.TagDAOImp;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.UserDAOMySqlImp;

public class ProductionConfig {
	private static SearchEngineSA sesa;
	private static ProfileManagerSA pmsa;
	private static SessionManagerSA smsa;
	private static TicketManagerSA ticketmsa;
	private static TagManagerSA tagmsa;

	private static UserDAO users;
	private static ClubDAO clubs;
	private static TagDAO tags;
	
	private static FilterMapper filters;
	
	private void initDAOs(){
		users = new UserDAOImp();
		clubs = new ClubDAOImp();
		tags = new TagDAOImp();
	}
	
	private void initDAOsSQL(){
		users = new UserDAOMySqlImp();
		clubs = new ClubDAOMySqlImp();
		//Tag DAO SQL imp
		//tags = new TagDAOMySqlImp();
	}
	
	private void addFilters(){
		//Here goes the equivalent to addFilters from FilterMapper
	}
	
	private void init(){
		
		sesa = new SearchEngineSAImp(clubs);
		pmsa = new ProfileManagerSAImp(clubs, users);
		//smsa = new SessionManagerSAImp();
		ticketmsa = new TicketManagerSAImp(clubs, users);
		tagmsa = new TagManagerSAImp(tags);
		addFilters();
		
		//Create frontController
		//Stay on hold
	}

	/**
	 * @return the SearchEngineSA
	 */
	public static SearchEngineSA getSearchEngineSA() {
		return sesa;
	}

	/**
	 * @return the ProfileManagerSA
	 */
	public static ProfileManagerSA getProfileManagerSA() {
		return pmsa;
	}

	/**
	 * @return the SessionManagerSA
	 */
	public static SessionManagerSA getSessionManagerSA() {
		return smsa;
	}

	/**
	 * @return the TicketManagerSA
	 */
	public static TicketManagerSA getTicketmanagerSA() {
		return ticketmsa;
	}

	/**
	 * @return the TagManagerSA
	 */
	public static TagManagerSA getTagmanagerSA() {
		return tagmsa;
	}
	
}
