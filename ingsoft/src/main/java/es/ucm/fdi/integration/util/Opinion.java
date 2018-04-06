package es.ucm.fdi.integration.util;

public class Opinion {
	private String opinion;
	private double rating;
	
	public Opinion(String opinion, double rating){
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
