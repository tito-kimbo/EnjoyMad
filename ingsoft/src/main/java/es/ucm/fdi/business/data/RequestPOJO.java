package es.ucm.fdi.business.data;

import java.util.List;

import es.ucm.fdi.business.requesthandling.RequestHandler;
import es.ucm.fdi.business.requesthandling.tools.RequestType;

/**
 * Class that represents a request to be handled by the app. It contains
 * the information needed to build a certain {@link RequestHandler} 
 * implementation that will handle the request.
 * 
 * @version 18.05.2018
 */
public class RequestPOJO {
	
    /**
     * Type of request;
     */
    private RequestType type;

    /**
     * List of {@code String}s containing the necessary information
     * to handle the request.
     */
    private List<String> parameters;

    /**
     * Builds a new unprocessed request.
     */
    public RequestPOJO(RequestType t, List<String> params) {
        type = t;
        parameters = params;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public RequestType getType() {
        return type;
    }
}