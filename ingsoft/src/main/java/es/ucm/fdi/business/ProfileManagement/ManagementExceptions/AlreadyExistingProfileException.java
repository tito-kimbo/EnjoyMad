package es.ucm.fdi.business.ProfileManagement.ManagementExceptions;

import java.lang.Exception;

public class AlreadyExistingProfileException extends Exception {

	public AlreadyExistingProfileException(String message) {
        super(message);
	}

}