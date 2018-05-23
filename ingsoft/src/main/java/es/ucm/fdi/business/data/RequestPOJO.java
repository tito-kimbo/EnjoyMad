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
 * @version 18.05.2018
 */
public class RequestPOJO extends DataPOJO {
	
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
    public RequestPOJO(RequestType t, List<Object> params) {
    	super("");
    	type = t;
        parameters = params;
    }
    
    public RequestPOJO(String id, RequestPOJO rp){
    	super(id);
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