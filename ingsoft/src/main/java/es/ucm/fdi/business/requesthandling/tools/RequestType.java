package es.ucm.fdi.business.requesthandling.tools;

import es.ucm.fdi.business.FrontController;

/**
 * Enum representing the different types of requests that
 * the {@link FrontController} can handle.
 */
public enum RequestType {
    REGISTER_ACCOUNT("Register Account"),
    DELETE_ACCOUNT("Delete Account"),

    ADD_REVIEW("Add Review"),
    DELETE_REVIEW("Delete Review"),
    MODIFY_CLUB("Modify Club"),
    MODIFY_USER("Modify User"),

    SEARCH_CLUB("Search Club"),
    BUY_TICKET("Buy Ticket");
    
    String str;
    private RequestType(String s){
    	str = s;
    }
    
    public String toString(){
    	return str;
    }
}