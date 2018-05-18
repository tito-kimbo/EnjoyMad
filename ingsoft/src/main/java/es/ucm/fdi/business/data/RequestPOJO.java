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
 * 
 * @author PabloHdez [Pablo Hernández]
 */
public class RequestPOJO extends DataPOJO {
    
    static public enum RequestState {
        NOT_PROCESSED,
        IN_PROCESS,
        PROCESSED;
    }

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
     * State of the request.
     */
    private RequestState state;

    /**
     * Builds a new unprocessed request.
     */
    public RequestPOJO(String id, RequestType t, List<String> params) {
        super(id);
        type = t;
        parameters = params;

        state = RequestState.NOT_PROCESSED;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState newState) {
        state = newState;
    }

    public RequestType getType() {
        return type;
    }
}