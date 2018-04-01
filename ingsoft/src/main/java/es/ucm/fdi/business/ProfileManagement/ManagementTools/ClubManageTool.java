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
     * Given a new data and its type, it modifies the <code>Club</code> 
     * attribute, provided the new data corresponds with the instance type.
     * 
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
     * Modifies the club's id if the data is correct.
     * 
     * @param newData new id
     */
    private void modifyID(Object newData) {
        if ( ! (newData instanceof String)) {
            // throw NotValidModification exception
        }

        // Valid?
        String newID = (String) newData;

        if ( ! ParsingTool.parseID(newID) ) {
            
        }

        // Map change
        clubDAO.removeClub(clubToManage.getID());        
        clubToManage.setID(newID);
        clubDAO.addClub(clubToManage);
    }

    /**
     * Modifies the club's address if the data is correct.
     * 
     * @param newData new adress
     */
    private void modifyAddress(Object newData) {
        if ( ! (newData instanceof String)) {
            // throw NotValidModification exception
        }

        // Valid?
        String newAddress = (String) newData;

        if ( ! ParsingTool.parseAddress(newAddress) ) {
            
        }

        clubToManage.setAddress(newAddress);
    }

    /**
     * Modifies the club's location if the data is correct.
     * 
     * @param newData new location
     */
    /*private void modifyCoordinates(Object newData) {
        if ( ! (newData instanceof Location)) {
            // throw NotValidModification exception
        }

        // Valid?
        Location newCoordinates = (Location) newData;

        clubToManage.set
    }*/

    /**
     * Modifies the club's ticket price if the data is correct.
     * 
     * @param newData new ticket price
     */
    private void modifyPrice(Object newData) {
        if ( ! (newData instanceof Float)) {
            // throw NotValidModification exception
        }

        // Valid?
        float newPrice = (Float) newData;

        if ( ! ParsingTool.parsePrice(newPrice) ) {
            
        }

        clubToManage.setPrice(newPrice);
    }

    /**
     * Modifies the club's rating if the data is correct.
     * ATTENTION: This method is not thought to be used
     * initially in the general implementation.
     * 
     * @param newData new rating
     */
    private void modifyRating(Object newData) {
        if ( ! (newData instanceof Float)) {
            // throw NotValidModification exception
        }

        // Valid?
        float newRating = (Float) newData;

        clubToManage.setRating(newRating);
    }   

    /**
     * Adds a new tag to the club tags list if
     * the new tag data is correct.
     * 
     * @param newData new tag
     */
    private void addTag(Object newData)
    {
        if ( ! (newData instanceof String)) {
            // throw NotValidModification exception
        }

        // Valid?
        String newTag = (String) newData;

        if ( ! ParsingTool.tagChecker.matcher(newTag).matches() ) {

        }

        clubToManage.addTag(newTag);
    }

    /**
     * Removes a tag from the club tags list if
     * the new tag data is correct.
     * 
     * @param newData tag to remove
     */
    private void removeTag(Object newData)
    {
        if ( ! (newData instanceof String)) {
            // throw NotValidModification exception
        }

        // Valid?
        String tagToRemove = (String) newData;

        if ( ! ParsingTool.tagChecker.matcher(tagToRemove).matches() ) {
            
        }

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
}