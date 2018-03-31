package es.ucm.fdi.business.ProfileManagement.ManagementTools;

import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.ClubDataID;

import es.ucm.fdi.integration.ClubDAOImp;

/**
 * Class to be used by the ProfileManagerSAImp to modify
 * particular attributes of a ClubPOJO instance.
 */
public class ClubManageTool {
    ClubPOJO clubToManage;
    ClubDAOImp clubDAO;

    public ClubManageTool(ClubPOJO club, ClubDAOImp clubDAO) {
        clubToManage = club;
        this.clubDAO = clubDAO;
    }

    /**
     * Given a new data and its type, it modifies the club's attribute,
     * provided the new data corresponds with the instance type.
     * @param dataID id of the attribute to be modified
     * @param newData new data of the attribute
     */
    public void modify(ClubDataID dataID, Object newData) {
        switch(dataID) {
            case ID: // implies DAO-map change
                modifyID(newData);
                break;
            case ADDRESS:
                modifyAddress(newData);
                break;
            case PRICE:
                modifyPrice(newData);
                break;
            case RATING:
                modifyRating(newData);
                break;
            case ADD_TAG:
                addTag(newData);
                break;
            case REMOVE_TAG:
                removeTag(newData);
                break;
            case CLEAR_TAGS:
                clearTags();
                break;
            default:
                //throw NonValidClubData
                break;
        }
    }

    /**
     * Adds a new tag to the club tags list if
     * the new tag data is correct.
     */
    private void addTag(Object newData)
    {
        if ( ! (newData instanceof String)) {
            // throw NotValidModification exception
        }

        // ¿Parse?
        String newTag = (String) newData;

        clubToManage.addTag(newTag);
    }

    /**
     * Removes a tag from the club tags list if
     * the new tag data is correct.
     */
    private void removeTag(Object newData)
    {
        if ( ! (newData instanceof String)) {
            // throw NotValidModification exception
        }

        // ¿Parse?
        String tagToRemove = (String) newData;

        clubToManage.removeTag(tagToRemove);
    }

    /**
     * Removes a tag from the club tags list if
     * the new tag data is correct.
     */
    private void clearTags()
    {
        clubToManage.clearTags();
    }

    /**
     * Modifies the club's id if the data is correct.
     * @param newData new data (String)
     */
    private void modifyID(Object newData) {
        if ( ! (newData instanceof String)) {
            // throw NotValidModification exception
        }

        // ¿Parse?
        String newID = (String) newData;

        // Map change
        clubDAO.removeClub(clubToManage.getID());        
        clubToManage.setID(newID);
        clubDAO.addClub(clubToManage);
    }

    /**
     * Modifies the club's address if the data is correct.
     * @param newData new data (String)
     */
    private void modifyAddress(Object newData) {
        if ( ! (newData instanceof String)) {
            // throw NotValidModification exception
        }

        // ¿Parse?
        String newAddress = (String) newData;

        clubToManage.setAddress(newAddress);
    }

    /**
     * Modifies the club's location if the data is correct.
     * @param newData new data (String)
     */
    /*private void modifyCoordinates(Object newData) {
        if ( ! (newData instanceof Location)) {
            // throw NotValidModification exception
        }

        // Parse?
        Location newCoordinates = (Location) newData;

        clubToManage.set
    }*/

    /**
     * Modifies the club's ticket price if the data is correct.
     * @param newData new data (String)
     */
    private void modifyPrice(Object newData) {
        if ( ! (newData instanceof Float)) {
            // throw NotValidModification exception
        }

        // Parse?
        float newPrice = (Float) newData;

        clubToManage.setPrice(newPrice);
    }

    /**
     * Modifies the club's rating if the data is correct.
     * @param newData new data (String)
     */
    private void modifyRating(Object newData) {
        if ( ! (newData instanceof Float)) {
            // throw NotValidModification exception
        }

        // Parse?
        float newRating = (Float) newData;

        clubToManage.setRating(newRating);
    }   
}