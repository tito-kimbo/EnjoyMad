package es.ucm.fdi.business.sessionmanagement;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import es.ucm.fdi.integration.SessionDAO;
import es.ucm.fdi.integration.data.SessionPOJO;

/**
 * Class to be used as the Session Manager of the application. It implements the
 * functionality of {@link SessionManagerSA}.
 * 
 * @version 08.05.2018
 */
public class SessionManagerSAImp implements SessionManagerSA {

	private static SessionDAO sessionDAO;

	/**
	 * <p>
	 * Builds a {@link SessionManagerSAImp} whose functionality is:
	 * </p>
	 * <p>
	 * 1) To add and remove {@code SessionPOJO}s.
	 * </p>
	 * <p>
	 * 2) To access {@code SessionPOJO}s and update their access times.
	 * </p>
	 * 
	 * @param sessions
	 *            - the app {@code SessionDAOImp} database
	 */
	public SessionManagerSAImp(SessionDAO sessions) {
		sessionDAO = sessions;
	}

	public void addNewSession(SessionPOJO session)
			throws IllegalArgumentException {

		LocalDateTime time = LocalDateTime.now();

		// Is already registered?
		if (sessionDAO.exist(session.getID())) {
			throw new IllegalArgumentException("In SESSION creation: "
					+ "sessionID is already registered -> " + session.getID());
		}

		// Times checked
		if (time.compareTo(session.getCreationTime()) < 0) {
			throw new IllegalArgumentException("In SESSION creation: "
					+ "session creation time is not valid -> "
					+ session.getCreationTime());
		}

		if (time.compareTo(session.getLastAccessedTime()) < 0) {
			throw new IllegalArgumentException("In SESSION creation: "
					+ "session last access time is not valid -> "
					+ session.getLastAccessedTime());
		}

		// Addition to database.
		sessionDAO.addSession(session);
	}

	public void accessSession(String sessionID) throws NoSuchElementException {
		LocalDateTime time = LocalDateTime.now();
		SessionPOJO sessionToAccess = sessionDAO.getSession(sessionID);

		if (sessionToAccess == null) {
			throw new NoSuchElementException("In SESSION access: "
					+ "session not found in database. ID -> " + sessionID);
		}

		// XXX Esto debería ser más complejo quizá.
		sessionToAccess.setLastAccessedTime(time);
	}

	public void removeSession(String sessionID) throws NoSuchElementException {
		SessionPOJO removingSession = sessionDAO.getSession(sessionID);

		if (removingSession == null) {
			throw new NoSuchElementException("In SESSION removal: "
					+ "session not found in database. ID -> " + sessionID);
		}

		// XXX Esto debería ser más complejo quizá.
		sessionDAO.removeSession(sessionID);
	}
}