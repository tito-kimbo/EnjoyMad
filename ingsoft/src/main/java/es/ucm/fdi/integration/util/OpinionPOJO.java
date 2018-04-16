package es.ucm.fdi.integration.util;

public class OpinionPOJO {
	private String opinion;
	private double rating;
	
	public OpinionPOJO(String opinion, double rating){
		this.opinion = opinion;
		this.rating = rating;
	}
	
	public String getOpinion(){
		return opinion;
	}
	
	public double getRating(){
		return rating;
	}
}
