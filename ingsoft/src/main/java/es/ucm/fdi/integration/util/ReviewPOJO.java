package es.ucm.fdi.integration.util;

public class ReviewPOJO {
	private String opinion; // written opinion
	private double rate; // numeric rate
	
	public ReviewPOJO(String opinion, double rate){
		this.opinion = opinion;
		this.rate = rate;
	}
	
	public String getOpinion() {
		return opinion;
	}
	
	public double getRating() {
		return rate;
	}

	@Override
	public String toString() {
		String review = "";
		
		review += ( "Rate: " + rate );
		review += '\n';
		review += ( "Opinion: " + opinion );

		return review;
	}
}
