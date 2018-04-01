package es.ucm.fdi.business.ProfileManagement.ManagementTools;

import java.time.LocalDate;
import java.util.zip.DataFormatException;

import org.mindrot.jbcrypt.BCrypt;

import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.UserPOJO;



/**
 * Class to be used by the ProfileManagerSAImp to modify
 * particular attributes of a UserPOJO instance.
 */
public class UserManageTool {
    private UserPOJO userToManage;
    private UserDAOImp userDAO;

    /**
     * Constructs a <code>UserManageTool</code> to be used in <code>ProfileManager</code>
     * methods. Receives the <code>UserDAO</code> database to rearrange it in case of
     * id modification.
     * 
     * @param user user to be managed
     * @param userDAO users database
     */
    public UserManageTool(UserPOJO user, UserDAOImp userDAO) {
        userToManage = user;
        this.userDAO = userDAO;
    }

    /**
     * Given a new data and its type, it modifies the <code>User</code> attribute,
     * provided the new data corresponds with the instance type.
     * 
     * @param dataID id of the attribute to be modified
     * @param newData new data of the attribute
     * @throws IlegalArgumentException if caught or if dataID is not contemplated
     * @throws DataFormatException if caught, because of parsing failure
     */
    public void modify(UserDataID dataID, Object newData) throws DataFormatException {
        try {
            switch(dataID) {
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
                    "In Club modification: not an implemented club data modification -> " +
                    dataID.toString()
                );
            }
        } catch (IllegalArgumentException ilegArg) {
            throw ilegArg;
        } catch (DataFormatException dataForm) {
            throw dataForm;
        }
    }

    /**
     * Modifies the user's id if the data is correct.
     * 
     * @param newData new id
     * @throws IllegalArgumentException if newData is not instance of <code>String</code>
     * @throws DataFormatException if id parsing failed
     */
    private void modifyID(Object newData) throws DataFormatException {
        if ( ! (newData instanceof String)) {
            throw new IllegalArgumentException(
                "In ID modification: not a String type argument."
            );
        }

        // Valid?
        String newID = (String) newData;

        if ( ! ParsingTool.parseID(newID) ) {
            throw new DataFormatException(
                "In ID modification: not a valid ID format -> " + newID
            );
        }
        
        // Map change
        userDAO.removeUser(userToManage.getID());        
        userToManage.setID(newID);
        userDAO.addUser(userToManage);
    }

    /**
     * Modifies the user's username if the data is correct.
     * 
     * @param newData new username
     * @throws IllegalArgumentException if newData is not instance of <code>String</code>
     * @throws DataFormatException if username parsing failed
     */
    private void modifyUsername(Object newData) throws DataFormatException {
        if ( ! (newData instanceof String)) {
            throw new IllegalArgumentException(
                "In USERNAME modification: not a String type argument."
            );
        }

        // Valid?
        String newUsername = (String) newData;

        if ( ! ParsingTool.parseUsername(newUsername) ) {
            throw new DataFormatException(
                "In USERNAME modification: not a valid username format -> " + newUsername
            );
        }

        userToManage.setUsername(newUsername);
    }

    /**
     * Modifies the user's password if the data is correct.
     * 
     * @param newData new password
     * @throws IllegalArgumentException if newData is not instance of <code>String</code>
     * @throws DataFormatException if password parsing failed
     */
    private void modifyPassWord(Object newData) throws DataFormatException {
        if ( ! (newData instanceof String)) {
            throw new IllegalArgumentException(
                "In PASSWORD modification: not a String type argument."
            );
        }

        // Valid?
        String newPassword = (String) newData;

        if ( ! ParsingTool.parsePassword(newPassword) ) {
            throw new DataFormatException(
                "In PASSWORD modification: not a valid password format -> " + newPassword
            );
        }

        // Password protection.
        String hashPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        userToManage.setPassword(hashPassword);
    }

    /**
     * Modifies the user's email if the data is correct.
     * 
     * @param newData new email
     * @throws IllegalArgumentException if newData is not instance of <code>String</code>
     * @throws DataFormatException if email parsing failed
     */
    private void modifyEmail(Object newData) throws DataFormatException {
        if ( ! (newData instanceof String)) {
            throw new IllegalArgumentException(
                "In EMAIL modification: not a String type argument."
            );
        }

        // Valid?
        String newEmail = (String) newData;

        if ( ! ParsingTool.parseEmail(newEmail) ) {
            throw new DataFormatException(
                "In EMAIL modification: not a valid email format -> " + newEmail
            );
        }

        userToManage.setEmail(newEmail);
    }

    /**
     * Modifies the user's name if the data is correct.
     * 
     * @param newData new name
     * @throws IllegalArgumentException if newData is not instance of <code>String</code>
     * @throws DataFormatException if name parsing failed
     */
    private void modifyName(Object newData) throws DataFormatException {
        if ( ! (newData instanceof String)) {
            throw new IllegalArgumentException(
                "In NAME modification: not a String type argument."
            );
        }

        // Valid?
        String newName = (String) newData;

        if ( ! ParsingTool.parseName(newName) ) {
            throw new DataFormatException(
                "In NAME modification: not a valid name format -> " + newName
            );
        }

        userToManage.setName(newName);
    }

    /**
     * Modifies the user's birthday if the data is correct.
     * @param newData new birthday
     * @throws IllegalArgumentException if newData is not instance of <code>LocalDate</code>
     * @throws DataFormatException if birthday parsing failed
     */
    private void modifyBirthday(Object newData) throws DataFormatException {
        if ( ! (newData instanceof LocalDate)) {
            throw new IllegalArgumentException(
                "In BIRTHDAY modification: not a LocalDate type argument."
            );
        }

        // Valid?
        LocalDate newBirthday = (LocalDate) newData;
        
        if ( ! ParsingTool.parseBirthday(newBirthday) ) {
            throw new DataFormatException(
                "In BIRTHDAY modification: not a valid birth date -> " + newBirthday.toString()
            );
        }

        userToManage.setBirthday(newBirthday);
    }
}