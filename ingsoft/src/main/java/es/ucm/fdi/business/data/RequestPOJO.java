package es.ucm.fdi.business.data;

import java.util.List;

import es.ucm.fdi.business.requesthandling.RequestHandler;
import es.ucm.fdi.business.requesthandling.tools.RequestType;
import es.ucm.fdi.integration.data.DataPOJO;

/**
 * Class that represents a request to be handled by the app. It contains
 * the information needed to build a certain {@link RequestHandler} 
 * implementation that will handle the request.
 * 
 * @version 25.05.2018
 */
public class RequestPOJO extends DataPOJO {
	
    /**
	 * Generated UID.
	 */
	private static final long serialVersionUID = -7131586157697244806L;

	/**
     * Type of request;
     */
    private RequestType type;

    /**
     * List of {@code String}s containing the necessary information
     * to handle the request.
     */
    private List<Object> parameters;

    /**
     * Builds a new unprocessed request.
     */
    public RequestPOJO(String userID, RequestType t, List<Object> params) {
    	super(userID);
    	type = t;
        parameters = params;
    }
    
    public RequestPOJO(String pollID, RequestPOJO rp){
    	super(pollID);
    	type = rp.type;
    	parameters = rp.parameters;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public RequestType getType() {
        return type;
    }
}