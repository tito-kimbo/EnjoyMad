package es.ucm.fdi.business.profilemanagement.tools;

/**
 * Enum class representing the attributes of a UserPOJO.
 */
public enum UserDataType {
    ID("ID"), USERNAME("USERNAME"), PASSWORD("PASSWORD"), EMAIL("EMAIL"), NAME("NAME"), BIRTHDAY("BIRHTDAY");

    private String type;

    private UserDataType(String type) {
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