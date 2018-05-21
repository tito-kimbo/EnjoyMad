package es.ucm.fdi.business;

import es.ucm.fdi.business.profilemanagement.ProfileManagerSA;
import es.ucm.fdi.business.profilemanagement.ProfileManagerSAImp;
import es.ucm.fdi.business.searchengine.FilterMapper;
import es.ucm.fdi.business.searchengine.SearchEngineSA;
import es.ucm.fdi.business.searchengine.SearchEngineSAImp;
import es.ucm.fdi.business.searchengine.filters.LocationFilterStrategy;
import es.ucm.fdi.business.searchengine.filters.PriceFilterStrategy;
import es.ucm.fdi.business.searchengine.filters.RatingFilterStrategy;
import es.ucm.fdi.business.searchengine.filters.TagFilterStrategy;
import es.ucm.fdi.business.sessionmanagement.SessionManagerSA;
import es.ucm.fdi.business.sessionmanagement.SessionManagerSAImp;
import es.ucm.fdi.business.tagmanagement.TagManagerSA;
import es.ucm.fdi.business.tagmanagement.TagManagerSAImp;
import es.ucm.fdi.business.ticketmanagement.TicketManagerSA;
import es.ucm.fdi.business.ticketmanagement.TicketManagerSAImp;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.ClubDAOMySqlImp;
import es.ucm.fdi.integration.SessionDAO;
import es.ucm.fdi.integration.SessionDAOImp;
import es.ucm.fdi.integration.TagDAO;
import es.ucm.fdi.integration.TagDAOImp;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.UserDAOMySqlImp;

public class ProductionConfig {
	private static UserDAO users;
	private static ClubDAO clubs;
	private static TagDAO tags;
	private static SessionDAO sessions;
	
	private static FrontController fc;
	private static FilterMapper filters;
	
	//Public access keys for the filters
	public static String PRICE_FILTER    = "PriceFilter";
	public static String TAG_FILTER      = "TagFilter";
	public static String RATING_FILTER   = "RatingFilter";
	public static String LOCATION_FILTER = "LocationFilter";
	
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
		//tags = new TagDAOMySqlImp();
	}
	
	public static void addFilters(){
		//Here goes the equivalent to addFilters from FilterMapper
		FilterMapper.addFilter(PRICE_FILTER,    new PriceFilterStrategy());
		FilterMapper.addFilter(TAG_FILTER,      new TagFilterStrategy());
		FilterMapper.addFilter(RATING_FILTER,   new RatingFilterStrategy());
		FilterMapper.addFilter(LOCATION_FILTER, new LocationFilterStrategy());
	}
	
	private static void init(){
		initDAOs();
		//initDAOsSQL();
		addFilters();
		SearchEngineSA sesa = new SearchEngineSAImp(clubs);
		ProfileManagerSA pmsa = new ProfileManagerSAImp(clubs, users);
		TicketManagerSA ticketmsa = new TicketManagerSAImp(clubs, users);
		SessionManagerSA smsa = new SessionManagerSAImp(sessions);
		TagManagerSA tagmsa = new TagManagerSAImp(tags);
		
		//Create frontController
		fc = new FrontController(sesa, pmsa, ticketmsa,smsa, tagmsa);
	}
	
	
	public static void main(String[] args){
		ProductionConfig.init();
		//Now wait while exit() message is not thrown
	}
}