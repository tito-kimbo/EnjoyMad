package es.ucm.fdi.business.profilemanagement.tools;

import java.time.LocalDate;
import java.util.zip.DataFormatException;

import org.mindrot.jbcrypt.BCrypt;

import es.ucm.fdi.business.util.ParsingToolHelper;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Enum class representing the attributes of a UserPOJO.
 * 
 * @version 08.05.2018
 */
public enum UserModifierHelper {
    NICKNAME("NICKNAME") {
        @Override
        public void modify(UserPOJO userToManage, Object newData) 
                throws IllegalArgumentException, DataFormatException {
            
            // Instance of...
            if ( ! ( newData instanceof String ) ) {
                throw new IllegalArgumentException(
                    "In NICKNAME modification: " + 
                    "not a String type argument."
                );
            }

            // Valid?
            String newNickname = (String) newData;

            if ( ! ParsingToolHelper.parseUsername(newNickname) ) {
                throw new DataFormatException(
                    "In NICKNAME modification: " +
                    "not a valid username format -> " + 
                    newNickname
                );
            }

            // Modification.
            userToManage.setNickname(newNickname);
        }
    },
    
    PASSWORD("PASSWORD") {
        @Override
        public void modify(UserPOJO userToManage, Object newData)
                throws IllegalArgumentException, DataFormatException {
            
            // Instance of...
            if ( ! ( newData instanceof String ) ) {
                throw new IllegalArgumentException(
                    "In PASSWORD modification: " + 
                    "not a String type argument."
                );
            }

            // Valid?
            String newPassword = (String) newData;

            if ( ! ParsingToolHelper.parsePassword(newPassword) ) {
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
        public void modify(UserPOJO userToManage, Object newData) 
                throws IllegalArgumentException, DataFormatException {
            
            // Instance of...
            if ( ! ( newData instanceof String ) ) {
                throw new IllegalArgumentException(
                    "In EMAIL modification: " + 
                    "not a String type argument."
                );
            }

            // Valid?
            String newEmail = (String) newData;

            if ( ! ParsingToolHelper.parseEmail(newEmail) ) {
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
        public void modify(UserPOJO userToManage, Object newData) 
                throws IllegalArgumentException, DataFormatException {
            
            // Instance of...             
            if ( ! ( newData instanceof String ) ) {
                throw new IllegalArgumentException(
                    "In NAME modification: " +
                    "not a String type argument."
                );
            }

            // Valid?
            String newName = (String) newData;

            if ( ! ParsingToolHelper.parseName(newName) ) {
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
        public void modify(UserPOJO userToManage, Object newData) 
                throws IllegalArgumentException, DataFormatException {
            
            // Instance of...
            if ( ! ( newData instanceof LocalDate ) ) {
                throw new IllegalArgumentException(
                    "In BIRTHDAY modification: " + 
                    "not a LocalDate type argument."
                );
            }

            // Valid?
            LocalDate newBirthday = (LocalDate) newData;

            if ( ! ParsingToolHelper.parseBirthday(newBirthday) ) {
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

    private UserModifierHelper(String type) {
        this.type = type;
    }

    /**
     * Returns the enum type in {@code String} form.
     * 
     * @return type as {@code String}
     */
    @Override
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
    public abstract void modify(UserPOJO userToManage, Object newData) 
            throws IllegalArgumentException, DataFormatException;
}