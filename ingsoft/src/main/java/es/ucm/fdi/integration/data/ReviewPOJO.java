package es.ucm.fdi.integration.data;

/**
 * Class that represents an opinion left by a certain <code>User</code>.
 * 
 * @version 22.04.2018
 */
public class ReviewPOJO {
	private String opinion; // written opinion
	private double rate; // numeric rate
	
	/**
	 * Constructor for the class.
	 * 
	 * @param opinion	Text left as an opinion (possibly empty).
	 * @param rate		Numerical value of rating.
	 */
	public ReviewPOJO(String opinion, double rate){
		this.opinion = opinion;
		this.rate = rate;
	}
	
	/**
	 * Getter method for {@link #opinion}.
	 * 
	 * @return	The text left as opinion.
	 */
	public String getOpinion() {
		return opinion;
	}
	
	/**
	 * Getter method for {@link #rate}
	 * 
	 * @return	The numerical value rated.
	 */
	public double getRating() {
		return rate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String review = "";
		
		review += ( "Rate: " + rate );
		review += '\n';
		review += ( "Opinion: " + opinion );

		return review;
	}
	
	/**
	 * Compares the <code>ReviewPOJO</code> to another <code>ReviewPOJO</code>.
	 * 
	 * @param review <code>ReviewPOJO</code> to be compared to.
	 * @return whether or not the two <code>Reviews</code> are the same.
	 */
	public boolean equals(ReviewPOJO review) {
		return (getOpinion().equals(review.getOpinion()) &&
				getRating() == review.getRating());
	}
}
