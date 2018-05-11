package es.ucm.fdi.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.integration.data.SessionPOJO;

/**
 * Implementation of {@link SessionDAO} that works with a database.
 * 
 * @version 08.05.2018
 */
public class SessionDAOImp implements SessionDAO {

    private Map<String, SessionPOJO> sessionMap;

    public SessionDAOImp() {
        sessionMap = new HashMap<String, SessionPOJO>();
    }

    /**
     * {@inheritDoc}
     */
    synchronized public List<SessionPOJO> getSessions() {
		List<SessionPOJO> aux = 
                new ArrayList<SessionPOJO>( sessionMap.values() );
        
        return aux;
	}

    /**
     * {@inheritDoc}
     * 
     * @param id {@inheritDoc}
     */
    synchronized public boolean exist(String id) {
		return sessionMap.containsKey(id);
	}

    /**
     * {@inheritDoc}
     * 
     * @param session {@inheritDoc}
     */
    synchronized public void addSession(SessionPOJO session) {
		sessionMap.put(session.getID(), session);
	}

    /**
     * {@inheritDoc}
     * 
     * @param id {@inheritDoc}
     */
    synchronized public void removeSession(String id) {
		sessionMap.remove(id);
	}

    /**
     * {@inheritDoc}
     * 
     * @param id {@inhertiDoc}
     */
    synchronized public SessionPOJO getSession(String id) {
		return sessionMap.get(id);
	}
}