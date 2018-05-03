package es.ucm.fdi.business.profilemanagement.tools;

import java.time.LocalDate;
import java.util.zip.DataFormatException;

import org.mindrot.jbcrypt.BCrypt;

import es.ucm.fdi.business.util.ParsingToolBO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Class to be used by the ProfileManagerSAImp to modify particular attributes
 * of a UserPOJO instance.
 */
public class UserManageToolBO {
    private UserPOJO userToManage;
    private UserDAOImp userDAO;

    /**
     * Constructs a <code>UserManageTool</code> to be used in
     * <code>ProfileManager</code> methods. Receives the <code>UserDAO</code>
     * database to rearrange it in case of id modification.
     * 
     * @param user    user to be managed
     * @param userDAO users database
     */
    public UserManageToolBO(UserPOJO user, UserDAOImp userDAO) {
        userToManage = user;
        this.userDAO = userDAO;
    }

    /**
     * Given a new data and its type, it modifies the <code>User</code> attribute,
     * provided the new data corresponds with the instance type.
     * 
     * @param dataID  id of the attribute to be modified
     * @param newData new data of the attribute
     * @throws IlegalArgumentException if caught or if dataID is not contemplated
     * @throws DataFormatException     if caught, because of parsing failure
     */
    public void modify(UserDataType dataID, Object newData) throws DataFormatException {
        try {
            switch (dataID) {
            case ID: // implies DAO-map change
                modifyID(newData);
                break;
            case USERNAME:
                modifyUsername(newData);
                break;
            case PASSWORD:
                modifyPassWord(newData);
                break;
            case EMAIL:
                modifyEmail(newData);
                break;
            case NAME:
                modifyName(newData);
                break;
            case BIRTHDAY:
                modifyBirthday(newData);
                break;
            default:
                throw new IllegalArgumentException(
                        "In Club modification: not an implemented club data modification -> " + dataID.toString());
            }
        } catch (IllegalArgumentException ilegArg) {
            throw ilegArg;
        } catch (DataFormatException dataForm) {
            throw dataForm;
        }
    }




    /**
     * Modifies the user {@code ID} if the data is correct.
     * 
     * @param newData       - new user {@code ID}
     * 
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code String}
     * @throws DataFormatException          if id parsing failed
     */
    private void modifyID(Object newData) 
            throws DataFormatException {
        
        // Instance of...
        if ( ! (newData instanceof String) ) {
            throw new IllegalArgumentException(
                "In ID modification: " + 
                "not a String type argument."
            );
        }

        // Checking
        String newID = (String) newData;

        if ( ! ParsingToolBO.parseID(newID) ) {
            throw new DataFormatException(
                "In ID modification: " + 
                " not a valid ID format -> " + newID
            );
        }

        // Map change.
        userDAO.removeUser(userToManage.getID());
        userToManage.setID(newID);
        userDAO.addUser(userToManage);
    }

    /**
     * Modifies the user {@code Username} if the data 
     * is correct.
     * 
     * @param newData       - new user {@code Username}
     * 
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code String}
     * @throws DataFormatException          if {@code ID} parsing failed
     */
    private void modifyUsername(Object newData) 
            throws DataFormatException {
        
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

    /**
     * Modifies the user {@code PasswordHash} if the data 
     * is correct.
     * 
     * @param newData       - new user {@code Password}
     * 
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code String}
     * @throws DataFormatException          if {@code newPassword} 
     *                                      parsing failed
     */
    private void modifyPassWord(Object newData) 
            throws DataFormatException {

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

    /**
     * Modifies the user {@code Email} if the data is correct.
     * 
     * @param newData       - new user {@code Email}
     * 
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code String}
     * @throws DataFormatException          if {@code newEmail} parsing 
     *                                      failed
     */
    private void modifyEmail(Object newData) 
            throws DataFormatException {

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

    /**
     * Modifies the user {@code Name} if the data is correct.
     * 
     * @param newData       - new user {@code name}
     * 
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code String}
     * @throws DataFormatException          if {@code newName} parsing 
     *                                      failed
     */
    private void modifyName(Object newData) 
            throws DataFormatException {

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

    /**
     * Modifies the user {@code Birthday} if the data is correct.
     * 
     * @param newData       - new user {@code Birthday}
     * 
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code LocalDate}
     * @throws DataFormatException          if {@code newBirthday} parsing 
     *                                      failed
     */
    private void modifyBirthday(Object newData)
            throws DataFormatException {

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
}