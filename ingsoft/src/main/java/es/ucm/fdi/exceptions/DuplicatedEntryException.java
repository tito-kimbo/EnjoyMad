package es.ucm.fdi.exceptions;

import java.lang.RuntimeException;

public class DuplicatedEntryException extends RuntimeException{
	
	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -1653032292966907967L;

	public DuplicatedEntryException(String msg){
		super(msg);
	}
}
