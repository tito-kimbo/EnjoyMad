package es.ucm.fdi.integration;

import java.util.List;

import es.ucm.fdi.integration.data.SessionPOJO;

/**
 * Interface declaring the methods that define the functionality of an 
 * implemented Session Data Access Object (DAO).
 *
 * @version 08.05.2018
 */
public interface SessionDAO {
    /**
     * Returns the full list of {@code SessionPOJO}s.
     * 
     * @return  list with sessions
     */
    public List<SessionPOJO> getSessions();

    /**
     * Searches a specific {@code SessionPOJO} with a given id to see if it
     * exists or not.
     * 
     * @param id - the searched {@code SessionPOJO} id
     * 
     * @return if the searched {@code SessionPOJO} exists
     */
    public boolean exist(String id);

    /**
     * Adds a new {@code SessionPOJO} to the DAO map.
     * 
     * @param session - {@code SessionPOJO} to be added
     */
    public void addSession(SessionPOJO session);

    /**
     * Removes an existing {@code SessionPOJO} from the DAO map.
     * 
     * @param id - id of {@code SessionPOJO} to be removed
     */
    public void removeSession(String id);

    /**
     * Returns the {@code SessionPOJO} which matches the given id.
     * 
     * @param id - id of the {@code SessionPOJO}
     * 
     * @return {@code SessionPOJO} that matches the id, null otherwise
     */
    public SessionPOJO getSession(String id);
}