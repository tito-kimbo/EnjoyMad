package es.ucm.fdi.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.profilemanagement.ProfileManagerSA;
import es.ucm.fdi.business.requesthandling.HandlerMapper;
import es.ucm.fdi.business.searchengine.CustomDataSA;
import es.ucm.fdi.business.searchengine.SearchEngineSA;
import es.ucm.fdi.business.sessionmanagement.SessionManagerSA;
import es.ucm.fdi.business.tagmanagement.TagManagerSA;
import es.ucm.fdi.business.ticketmanagement.TicketManagerSA;

/**
 * Controller to manage {@code RequestPOJO}s issued by the app clients and
 * control their states until completion.
 * 
 * @version 25.05.2018
 */
public class FrontController {
	private static AnswerPOJO NULL_ANSWER;
	private static Map<String, AnswerPOJO> data;
	private static ExecutorService executor;

	private SearchEngineSA sesa;
	private ProfileManagerSA pmsa;
	private TicketManagerSA tmsa;
	private SessionManagerSA smsa;
	private TagManagerSA tagmsa;
	private CustomDataSA cdsa;

	//THIS IS A ROUGH INITIALIZATION SINCE ConcurrentHashmap DOES NOT ACCEPT nulls
	static{
		List<Object> l = new ArrayList<Object>();
		l.add("/%NULL%/");
		NULL_ANSWER = new AnswerPOJO(l);
	}
	
	public FrontController(SearchEngineSA searchEngine,
			ProfileManagerSA profileManager, TicketManagerSA ticketManager,
			SessionManagerSA sessionManager, TagManagerSA tagManager,
			CustomDataSA customData, int maxThreads) {
		data = new ConcurrentHashMap<String, AnswerPOJO>();

		sesa = searchEngine;
		pmsa = profileManager;
		tmsa = ticketManager;
		smsa = sessionManager;
		tagmsa = tagManager;
		cdsa = customData;
		executor = Executors.newFixedThreadPool(maxThreads);
	}

	public synchronized String request(final RequestPOJO rp) {
		String id;
		StringBuilder sb = new StringBuilder();
		// OBS: rp initially has user's ID as id
		// Here we build the id
		// id= userID+requestType+SystemTime in ns
		sb.append(rp.getID());
		sb.append(rp.getType());
		sb.append(System.nanoTime());

		id = sb.toString();
		data.put(id, NULL_ANSWER);
		// Provisional
		executor.execute(HandlerMapper.mapRequest(rp.getType(),
				new RequestPOJO(id, rp)));
		return id;
	}

	public AnswerPOJO poll(String id) {
		AnswerPOJO answer = data.get(id);
		if(!answer.equals(NULL_ANSWER)){
			data.remove(id);
			return answer;
		}else{
			return null;
		}
	}

	public synchronized void addAnswer(String id, AnswerPOJO answer) {
		data.put(id, answer);
	}

	/**
	 * @return the sesa
	 */
	public SearchEngineSA getSearchEngineSA() {
		return sesa;
	}

	/**
	 * @return the pmsa
	 */
	public ProfileManagerSA getProfileManagerSA() {
		return pmsa;
	}

	/**
	 * @return the tmsa
	 */
	public TicketManagerSA getTicketManagerSA() {
		return tmsa;
	}

	/**
	 * @return the smsa
	 */
	public SessionManagerSA getSessionManagerSA() {
		return smsa;
	}

	/**
	 * @return the tagmsa
	 */
	public TagManagerSA getTagManagerSA() {
		return tagmsa;
	}
	
	/**
	 * @return the cdsa
	 * @return
	 */
	public CustomDataSA getCustomDataSA(){
		return cdsa;
	}
}