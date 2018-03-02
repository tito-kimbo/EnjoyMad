package es.ucm.fdi.exceptions;

import java.lang.RuntimeException;

public class EntryNotFoundException extends RuntimeException {
	
	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2539036595887467648L;

	public EntryNotFoundException(String msg){
		super(msg);
	}
}
