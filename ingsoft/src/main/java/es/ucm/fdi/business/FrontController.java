package es.ucm.fdi.business;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.requesthandling.HandlerMapper;

/**
 * Controller to manage {@code RequestPOJO}s issued by the app clients
 * and control their states until completion.
 */
public class FrontController {
	private static Map<String, AnswerPOJO> data;
	
	public FrontController(){
		data = new ConcurrentHashMap<String, AnswerPOJO>();
	}
	
	//need sync?
	public String request(final RequestPOJO rp){
		String id;
		StringBuilder sb = new StringBuilder();
		
		
		
		id = sb.toString();
		data.put(id, null);
		//Provisional
		new Thread(){
			public void run(){
				HandlerMapper.mapRequest(rp.getType(), rp).handle();	
			}
		};
		
		return id;
	}
	
	public AnswerPOJO poll(String id){
		AnswerPOJO answer = data.get(id);
		data.remove(id);	
		return answer;
	}
}