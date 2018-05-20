package es.ucm.fdi.business.requesthandling;

import java.util.Map;

import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.requesthandling.tools.RequestType;

public class HandlerMapper {
	private static Map<RequestType, RequestHandler> requestHandler;
	
	public static RequestHandler mapRequest(RequestType rt, RequestPOJO rp){
		return (RequestHandler)requestHandler.get(rt).clone(rp);
	}
	
	public static void addHandler(RequestType rt, RequestHandler rh){
		requestHandler.put(rt, rh);
	}
	
	public static void removeHandler(RequestType rt){
		requestHandler.remove(rt);
	}
	
}
