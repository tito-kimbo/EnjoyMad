package es.ucm.fdi.business.profilemanagement.tools;

/**
 * Enum class representing the attributes of a ClubPOJO.
 */
public enum ClubDataType {
    ID("ID"), 
    COMMERCIAL_NAME("COMMERCIAL_NAME"), 
    LOCATION("LOCATION"),
    ADDRESS("ADDRESS"), 
    PRICE("PRICE"), 
    ADD_TAG("ADD_TAG"),
    REMOVE_TAG("REMOVE_TAG"), 
    CLEAR_TAGS("CLEAR_TAGS"), 
    RATING("RATING");

    private String type;

    private ClubDataType(String type) {
        this.type = type;
    }

    /**
     * Returns the enum type in <code>String</code> form.
     * 
     * @return type as <code>String</code>
     */
    public String toString() {
        return type;
    }
}