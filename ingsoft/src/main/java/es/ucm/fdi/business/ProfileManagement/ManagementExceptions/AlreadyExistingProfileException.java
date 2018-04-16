package es.ucm.fdi.business.ProfileManagement.ManagementExceptions;

import java.lang.Exception;

public class AlreadyExistingProfileException extends Exception {

	/**
	 * Generated serial UID.
	 */
	private static final long serialVersionUID = 7446161276045262585L;

	public AlreadyExistingProfileException(String message) {
        super(message);
	}

}