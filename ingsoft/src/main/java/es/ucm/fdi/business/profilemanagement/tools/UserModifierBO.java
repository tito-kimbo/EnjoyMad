package es.ucm.fdi.business.profilemanagement.tools;

import java.time.LocalDate;
import java.util.zip.DataFormatException;

import org.mindrot.jbcrypt.BCrypt;

import es.ucm.fdi.business.util.ParsingToolBO;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Enum class representing the attributes of a UserPOJO.
 */
public enum UserModifierBO {
    USERNAME("USERNAME") {
        @Override
        public void modify(UserPOJO userToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {
            
            // Instance of...
            if ( ! ( newData instanceof String ) ) {
                throw new IllegalArgumentException(
                    "In USERNAME modification: " + 
                    "not a String type argument."
                );
            }

            // Valid?
            String newUsername = (String) newData;

            if ( ! ParsingToolBO.parseUsername(newUsername) ) {
                throw new DataFormatException(
                    "In USERNAME modification: " +
                    "not a valid username format -> " + 
                    newUsername
                );
            }

            // Modification.
            userToManage.setUsername(newUsername);
        }
    },
    
    PASSWORD("PASSWORD") {
        @Override
        public void modify(UserPOJO userToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {
            
            // Instance of...
            if ( ! ( newData instanceof String ) ) {
                throw new IllegalArgumentException(
                    "In PASSWORD modification: " + 
                    "not a String type argument."
                );
            }

            // Valid?
            String newPassword = (String) newData;

            if ( ! ParsingToolBO.parsePassword(newPassword) ) {
                throw new DataFormatException(
                    "In PASSWORD modification: " +
                    "not a valid password format -> " + 
                    newPassword
                );
            }

            // Password protection.
            String hashPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            // Modification.
            userToManage.setPassword(hashPassword);
        }
    },
    
    EMAIL("EMAIL") {
        @Override
        public void modify(UserPOJO userToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {
            
            // Instance of...
            if ( ! ( newData instanceof String ) ) {
                throw new IllegalArgumentException(
                    "In EMAIL modification: " + 
                    "not a String type argument."
                );
            }

            // Valid?
            String newEmail = (String) newData;

            if ( ! ParsingToolBO.parseEmail(newEmail) ) {
                throw new DataFormatException(
                    "In EMAIL modification: " + 
                    "not a valid email format -> " + 
                    newEmail
                );
            }

            // Modification.
            userToManage.setEmail(newEmail);
        }
    },
    
    NAME("NAME") {
        @Override
        public void modify(UserPOJO userToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {
            
            // Instance of...             
            if ( ! ( newData instanceof String ) ) {
                throw new IllegalArgumentException(
                    "In NAME modification: " +
                    "not a String type argument."
                );
            }

            // Valid?
            String newName = (String) newData;

            if ( ! ParsingToolBO.parseName(newName) ) {
                throw new DataFormatException(
                    "In NAME modification: " + 
                    "not a valid name format -> " + 
                    newName
                );
            }

            // Modification.
            userToManage.setName(newName);
        }
    },
    
    BIRTHDAY("BIRHTDAY") {
        @Override
        public void modify(UserPOJO userToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {
            
            // Instance of...
            if ( ! ( newData instanceof LocalDate ) ) {
                throw new IllegalArgumentException(
                    "In BIRTHDAY modification: " + 
                    "not a LocalDate type argument."
                );
            }

            // Valid?
            LocalDate newBirthday = (LocalDate) newData;

            if ( ! ParsingToolBO.parseBirthday(newBirthday) ) {
                throw new DataFormatException(
                    "In BIRTHDAY modification: " + 
                    "not a valid birth date -> " + 
                    newBirthday
                );
            }

            // Modification.
            userToManage.setBirthday(newBirthday);
        }
    };

    
    
    
    
    
    private String type;

    private UserModifierBO(String type) {
        this.type = type;
    }

    /**
     * Returns the enum type in {@code String} form.
     * 
     * @return type as {@code String}
     */
    public String toString() {
        return type;
    }

    /**
     * Tries to carry out the modification indicated by the 
     * {@code UserModifierBO} itself.
     * 
     * @param userToManage - user to be modified
     * @param newData      - user new data
     * 
     * @throws IllegalArgumentException if {@code newData} is not an instance 
     *                                  of the correct class
     * @throws DataFormatException      if {@code newData} parsing failed
     *                                  
     */
    public abstract void modify(UserPOJO userToManage, Object newData) throws
            IllegalArgumentException, DataFormatException;
}