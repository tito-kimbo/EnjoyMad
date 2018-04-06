package es.ucm.fdi.integration.util;

public class Opinion {
	private String user, opinion;
	private int rating;
	
	public Opinion(String user, String opinion, int rating){
		this.user = user;
		this.opinion = opinion;
		this.rating = rating;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getOpinion(){
		return opinion;
	}
	
	public int getRating(){
		return rating;
	}
}
