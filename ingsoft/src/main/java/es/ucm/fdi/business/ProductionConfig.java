package es.ucm.fdi.business;

import java.util.TimeZone;

import es.ucm.fdi.business.profilemanagement.*;
import es.ucm.fdi.business.requesthandling.*;
import es.ucm.fdi.business.requesthandling.tools.RequestType;
import es.ucm.fdi.business.searchengine.*;
import es.ucm.fdi.business.searchengine.filters.*;
import es.ucm.fdi.business.sessionmanagement.*;
import es.ucm.fdi.business.tagmanagement.*;
import es.ucm.fdi.business.ticketmanagement.*;
import es.ucm.fdi.integration.*;

/**
 * Class in charge of system initialization.
 * 
 * @version 25.05.2018
 */
public class ProductionConfig {
	//Experimental optimal value
	private static int THREAD_LIMIT = 15;
	
	private static boolean exit = false;
	private static boolean useSQL = false;
	private static UserDAO users;
	private static ClubDAO clubs;
	private static TagDAO tags;
	private static SessionDAO sessions;
	
	private static FrontController fc;
	
	//Public access keys for the filters
	public static final String PRICE_FILTER    = "PriceFilter";
	public static final String TAG_FILTER      = "TagFilter";
	public static final String RATING_FILTER   = "RatingFilter";
	public static final String LOCATION_FILTER = "LocationFilter";
	
	private static void initDAOs(){
		users = new UserDAOImp();
		clubs = new ClubDAOImp();
		tags = new TagDAOImp();
		sessions = new SessionDAOImp();
	}
	
	private static void initSQLDAOs(){
		users = new UserDAOMySqlImp();
		clubs = new ClubDAOMySqlImp();
		//Tag DAO SQL imp
		tags = new TagDAOMySqlImp();
	}
	
	public static void addFilters(){
		//Here goes the equivalent to addFilters from FilterMapper
		FilterMapper.addFilter(PRICE_FILTER,    new PriceFilterStrategy());
		FilterMapper.addFilter(TAG_FILTER,      new TagFilterStrategy());
		FilterMapper.addFilter(RATING_FILTER,   new RatingFilterStrategy());
		FilterMapper.addFilter(LOCATION_FILTER, new LocationFilterStrategy());
	}
	
	private static void addHandlers(){
		HandlerMapper.addHandler(RequestType.ADD_REVIEW, new AddReviewHandler(fc));
		HandlerMapper.addHandler(RequestType.BUY_TICKET, new BuyTicketHandler(fc));
		HandlerMapper.addHandler(RequestType.DELETE_ACCOUNT, new DeleteAccountHandler(fc));
		HandlerMapper.addHandler(RequestType.DELETE_REVIEW, new DeleteReviewHandler(fc));
		HandlerMapper.addHandler(RequestType.MODIFY_CLUB, new ModifyClubHandler(fc));
		HandlerMapper.addHandler(RequestType.MODIFY_USER, new ModifyUserHandler(fc));
		HandlerMapper.addHandler(RequestType.REGISTER_CLUB, new RegisterClubHandler(fc));
		HandlerMapper.addHandler(RequestType.REGISTER_USER, new RegisterUserHandler(fc));
		HandlerMapper.addHandler(RequestType.SEARCH_CLUB, new SearchClubHandler(fc));
	}
	
	public static void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
		if(useSQL){
			initSQLDAOs();	
		}else{
			initDAOs();	
		}
		addFilters();
		CustomDataSA cdsa = new CustomDataSAImp(users, clubs);
		SearchEngineSA sesa = new SearchEngineSAImp();
		TicketManagerSA ticketmsa = new TicketManagerSAImp(clubs, users);
		SessionManagerSA smsa = new SessionManagerSAImp(sessions);
		TagManagerSA tagmsa = new TagManagerSAImp(tags);
		ProfileManagerSA pmsa = new ProfileManagerSAImp(clubs, users, tagmsa);
		
		//Create frontController
		fc = new FrontController(sesa, pmsa, ticketmsa,smsa, tagmsa, cdsa, THREAD_LIMIT);
		addHandlers();
	}
	
	public static void init(boolean sql){
		useSQL = sql;
		init();
	}
	
	public static void exit(){
		exit = true;
	}
	
	public static FrontController getFrontController(){
		return fc;
	}
	
	public static boolean getExit(){
		return exit;
	}
	
	//THE FOLLOWING METHODS ARE FOR TESTING PURPOSES
	protected static UserDAO getUserDAO(){
		return users;
	}
	
	protected static ClubDAO getClubDAO(){
		return clubs;
	}
}