package es.ucm.fdi.business.ProfileManagement.ManagementTools;

/**
 * Enum class representing the attributes of a UserPOJO.
 */
public enum UserDataID {
    ID ("ID"),
    USERNAME ("USERNAME"),
    PASSWORD ("PASSWORD"),
    EMAIL ("EMAIL"),
    NAME ("NAME"),
    BIRTHDAY ("BIRHTDAY");

    private String type;
    private UserDataID(String type) {
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