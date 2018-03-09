package es.ucm.fdi.datos;

/**
 * Basic data container.
 * 
 * @version 09.03.2018
 */
public abstract class DataPOJO {
	String id;
	
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
	public String getId() {
		return id;
	}

	/**
	 * Modifies the ID of this <code>DataPOJO</code>.
	 * 
	 * @param id new ID
	 */
	public void setId(String id) {
		this.id = id;
	}
}
