	package es.ucm.fdi.business.requesthandling;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.requesthandling.tools.RequestType;

/*
 * This class is used to associate different requests with their respective handler.
 */
public class HandlerMapper {
	private static Map<RequestType, RequestHandler> requestHandler;
	
	//static instantiation of the map
	static{
		requestHandler = new ConcurrentHashMap<RequestType, RequestHandler>();
	}
	
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
