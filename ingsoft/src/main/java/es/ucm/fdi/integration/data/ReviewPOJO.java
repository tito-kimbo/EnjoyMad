package es.ucm.fdi.integration.data;

import java.io.Serializable;

/**
 * Class that represents an opinion left by a certain <code>User</code>.
 * 
 * @version 22.04.2018
 */
public class ReviewPOJO implements Serializable {
	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = 9017298055375753121L;
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
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return opinion.hashCode() * (int) (rate * 100);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if(! (o instanceof ReviewPOJO)) {
			return false;
		}

		ReviewPOJO reviewToCompare = (ReviewPOJO) o;

		return opinion.equals(reviewToCompare.opinion)
				&& rate == reviewToCompare.rate;
	}
}
