package es.ucm.fdi.business.sessionmanagement;

import java.util.NoSuchElementException;

import es.ucm.fdi.integration.data.SessionPOJO;

/**
 * Interface defining the functionalities to be used in session management. It
 * extends to an implemented class with the necessary attributes.
 * 
 * @version 08.05.2018
 * 
 * @author behergue [Beatriz Herguedas]
 * @author PabloHdez [Pablo Hernández]
 */
public interface SessionManagerSA {
    
    // ** SESSION CREATION ** //

    /**
     * Adds a given {@code SessionPOJO} to the app {@code SessionDAO} database,
     * checking the session creation time.
     * 
     * @param session - new {@code SessionPOJO} to be added
     * 
     * @throws IllegalArgumentException if session creation time is higher than
     *                                  actual local time
     */
    public void addNewSession(SessionPOJO session) throws 
            IllegalArgumentException;

    /**
     * Accesses an existing {@code SessionPOJO} and modifies its atribute
     * {@code lastAccessedTime} to actual time.
     * 
     * @param sessionID - id of {@code SessionPOJO} to be accesseds
     * 
     * @throws NoSuchElementException if {@code sessionID} not found in 
     *                                {@code sessionDAO} database
     */
    public void accessSession(String sessionID) throws NoSuchElementException;

    /**
     * Removes a {@code SessionPOJO} from the DAO map.
     * 
     * @param sessionID - id of {@code SessionPOJO} to be removed
     * 
     * @throws NoSuchElementException if {@code sessionID} not found in
     *                                 {@code sesssionDAO} database
     */
    public void removeSession(String sessionID) throws NoSuchElementException;
}