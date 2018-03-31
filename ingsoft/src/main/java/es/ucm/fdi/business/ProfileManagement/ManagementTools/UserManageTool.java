package es.ucm.fdi.business.ProfileManagement.ManagementTools;

import java.time.LocalDate;

import es.ucm.fdi.integration.data.UserPOJO;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.UserDataID;

import es.ucm.fdi.integration.UserDAOImp;



/**
 * Class to be used by the ProfileManagerSAImp to modify
 * particular attributes of a UserPOJO instance.
 */
public class UserManageTool {
    private UserPOJO userToManage;
    private UserDAOImp userDAO;

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
     */
    public void modify(UserDataID dataID, Object newData) {
        switch(dataID) {
            case ID: // implies DAO-map change
                modifyID(newData);
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
                //throw NonValidUserData
                break;
        }
    }

    /**
     * Modifies the user's id if the data is correct.
     * 
     * @param newData new id
     */
    private void modifyID(Object newData) {
        if ( ! (newData instanceof String)) {
            // throw NotValidModification exception
        }

        // ¿Parse?
        String newID = (String) newData;
        
        // Map change
        userDAO.removeUser(userToManage.getID());        
        userToManage.setID(newID);
        userDAO.addUser(userToManage);
    }

    /**
     * Modifies the user's password if the data is correct.
     * 
     * @param newData new password
     */
    private void modifyPassWord(Object newData) {
        if ( ! (newData instanceof String)) {
            // throw NotValidModification exception
        }

        // ¿Parse?
        String newPassword = (String) newData;

        userToManage.setPassword(newPassword);
    }

    /**
     * Modifies the user's email if the data is correct.
     * 
     * @param newData new email
     */
    private void modifyEmail(Object newData) {
        if ( ! (newData instanceof String)) {
            // throw NotValidModification exception
        }

        // Parse?
        String newEmail = (String) newData;

        userToManage.setEmail(newEmail);
    }

    /**
     * Modifies the user's name if the data is correct.
     * 
     * @param newData new name
     */
    private void modifyName(Object newData) {
        if ( ! (newData instanceof String)) {
            // throw NotValidModification exception
        }

        // Parse?
        String newName = (String) newData;

        userToManage.setName(newName);
    }

    /**
     * Modifies the user's birthday if the data is correct.
     * @param newData new birthday
     */
    private void modifyBirthday(Object newData) {
        if ( ! (newData instanceof LocalDate)) {
            // throw ...
        }

        LocalDate newBirthday = (LocalDate) newData;

        userToManage.setBirthday(newBirthday);
    }
}