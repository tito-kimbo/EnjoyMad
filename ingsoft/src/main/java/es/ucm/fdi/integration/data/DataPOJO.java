package es.ucm.fdi.integration.data;

/**
 * Basic data container.
 * 
 * @version 09.03.2018
 */
public abstract class DataPOJO {
	private String id;
	
	/**
	 * Creates a <code>DataPOJO</code> with the given ID.
	 * 
	 * @param s Value for the ID
	 */
	public DataPOJO(String s){
		id = s;
	}

	/**
	 * Returns the ID of this <code>DataPOJO</code>.
	 * 
	 * @return ID
	 */
	public String getID() {
		return id;
	}

	/**
	 * Modifies the ID of this <code>DataPOJO</code>.
	 * 
	 * @param id new ID
	 */
	public void setID(String id) {
		this.id = id;
	}
	
	/**
	 * Compares two <code>DataPOJO</code>.
	 * 
	 * @param data <code>DataPOJO</code> to be compared to.
	 * @return  whether or not the two data are the same.
	 */
	public boolean equals(DataPOJO data) {
		return getID().equals(data.getID());
	}
}
