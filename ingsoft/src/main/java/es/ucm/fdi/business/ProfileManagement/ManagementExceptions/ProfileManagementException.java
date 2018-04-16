package es.ucm.fdi.business.ProfileManagement.ManagementExceptions;

import java.lang.Exception;

public class ProfileManagementException extends Exception {
    /**
	 * Generated serial UID.
	 */
	private static final long serialVersionUID = 3671499596936349788L;

	public ProfileManagementException() {
        super();
    }

    public ProfileManagementException(String message) {
        super(message);
    }

    public ProfileManagementException(Throwable cause) {
        super(cause);
    }

    public ProfileManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}