package es.ucm.fdi.business;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.profilemanagement.ProfileManagerSA;
import es.ucm.fdi.business.requesthandling.HandlerMapper;
import es.ucm.fdi.business.searchengine.SearchEngineSA;
import es.ucm.fdi.business.sessionmanagement.SessionManagerSA;
import es.ucm.fdi.business.tagmanagement.TagManagerSA;
import es.ucm.fdi.business.ticketmanagement.TicketManagerSA;

/**
 * Controller to manage {@code RequestPOJO}s issued by the app clients and
 * control their states until completion.
 */
public class FrontController {
	private static Map<String, AnswerPOJO> data;

	private static SearchEngineSA sesa;
	private static ProfileManagerSA pmsa;
	private static TicketManagerSA tmsa;
	private static SessionManagerSA smsa;
	private static TagManagerSA tagmsa;

	public FrontController(SearchEngineSA searchEngine,
			ProfileManagerSA profileManager, TicketManagerSA ticketManager,
			SessionManagerSA sessionManager, TagManagerSA tagManager) {
		data = new ConcurrentHashMap<String, AnswerPOJO>();

		sesa = searchEngine;
		pmsa = profileManager;
		tmsa = ticketManager;
		smsa = sessionManager;
		tagmsa = tagManager;
	}

	// need sync?
	public synchronized String request(final RequestPOJO rp) {
		String id;
		StringBuilder sb = new StringBuilder();

		id = sb.toString();
		data.put(id, null);
		// Provisional
		new Thread() {
			public void run() {
				HandlerMapper.mapRequest(rp.getType(), rp).handle();

			}
		};

		return id;
	}

	public AnswerPOJO poll(String id) {
		AnswerPOJO answer = data.get(id);
		data.remove(id);
		return answer;
	}

	public synchronized void addAnswer(String id, AnswerPOJO answer) {
		data.put(id, answer);
	}

	/**
	 * @return the sesa
	 */
	public static SearchEngineSA getSearchEngineSA() {
		return sesa;
	}

	/**
	 * @return the pmsa
	 */
	public static ProfileManagerSA getProfileManagerSA() {
		return pmsa;
	}

	/**
	 * @return the tmsa
	 */
	public static TicketManagerSA getTicketManagerSA() {
		return tmsa;
	}

	/**
	 * @return the smsa
	 */
	public static SessionManagerSA getSessionManagerSA() {
		return smsa;
	}

	/**
	 * @return the tagmsa
	 */
	public static TagManagerSA getTagManagerSA() {
		return tagmsa;
	}
}