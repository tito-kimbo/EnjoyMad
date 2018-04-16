package es.ucm.fdi.business.ProfileManagement.ManagementTools;

import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.ClubDataID;

import es.ucm.fdi.integration.ClubDAOImp;

import java.util.zip.DataFormatException;

/**
 * Class to be used by the ProfileManagerSAImp to modify
 * particular attributes of a ClubPOJO instance.
 */
public class ClubManageToolBO {
    private ClubPOJO clubToManage;
    private ClubDAOImp clubDAO;

    /**
     * Constructs a <code>ClubManageTool</code> to be used in <code>ProfileManager</code>
     * methods. Receives the <code>ClubDAO</code> database to rearrange it in case of
     * id modification.
     * 
     * @param club club to be managed
     * @param clubDAO clubs database
     */
    public ClubManageToolBO(ClubPOJO club, ClubDAOImp clubDAO) {
        clubToManage = club;
        this.clubDAO = clubDAO;
    }

    /**
     * Given a new data and its type, it modifies the <code>Club</code> 
     * attribute, provided the new data corresponds with the instance type.
     * 
     * @param dataID id of the attribute to be modified
     * @param newData new data of the attribute
     * @throws IlegalArgumentException if caught or if dataID is not contemplated
     * @throws DataFormatException if caught, because of parsing failure
     */
    public void modify(ClubDataID dataID, Object newData) throws DataFormatException {
        try {
            switch(dataID) {
                case ID: // implies DAO-map change
                    modifyID(newData);
                    break;
                case COMMERCIAL_NAME:
                    modifyCommercialName(newData);
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
     * Modifies the club's id if the data is correct.
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

        if ( ! ParsingToolBO.parseID(newID) ) {
            throw new DataFormatException(
                "In ID modification: not a valid ID format -> " + newID
            );
        }

        // Map change
        clubDAO.removeClub(clubToManage.getID());        
        clubToManage.setID(newID);
        clubDAO.addClub(clubToManage);
    }

    /**
     * Modifies the club's commercial name if the data is correct.
     * 
     * @param newData new commercial name
     * @throws IllegalArgumentException if newData is not instance of <code>String</code>
     * @throws DataFormatException if name parsing failed
     */
    private void modifyCommercialName(Object newData) throws DataFormatException {
        if ( ! (newData instanceof String)) {
            throw new IllegalArgumentException(
                "In COMMERCIAL NAME modification: not a String type argument."
            );        
        }

        // Valid?
        String newCommercialName = (String) newData;

        if ( ! ParsingToolBO.parseCommercialName(newCommercialName) ) {
            throw new DataFormatException(
                "In COMMERCIAL NAME modification: not a valid commercial name format -> " + newCommercialName
            );
        }

        clubToManage.setCommercialName(newCommercialName);
    }

    /**
     * Modifies the club's address if the data is correct.
     * 
     * @param newData new adress
     * @throws IllegalArgumentException if newData is not instance of <code>String</code>
     * @throws DataFormatException if address parsing failed
     */
    private void modifyAddress(Object newData) throws DataFormatException {
        if ( ! (newData instanceof String)) {
            throw new IllegalArgumentException(
                "In ADDRESS modification: not a String type argument."
            );
        }

        // Valid?
        String newAddress = (String) newData;

        if ( ! ParsingToolBO.parseAddress(newAddress) ) {
            throw new DataFormatException(
                "In ADDRESS modification: not a valid address format -> " + newAddress
            );
        }

        clubToManage.setAddress(newAddress);
    }

    /**
     * Modifies the club's ticket price if the data is correct.
     * 
     * @param newData new ticket price
     * @throws IllegalArgumentException if newData is not instance of <code>Float</code>
     * @throws DataFormatException if price parsing failed
     */
    private void modifyPrice(Object newData) throws DataFormatException {
        if ( ! (newData instanceof Float)) {
            throw new IllegalArgumentException(
                "In PRICE modification: not a Float type argument."
            );
        }

        // Valid?
        float newPrice = (Float) newData;

        if ( ! ParsingToolBO.parsePrice(newPrice) ) {
            throw new DataFormatException(
                "In PRICE modification: not a valid price -> " + newPrice
            );
        }

        clubToManage.setPrice(newPrice);
    }

    /**
     * Modifies the club's rating if the data is correct.
     * ATTENTION: This method is not thought to be used
     * initially in the general implementation.
     * 
     * @param newData new rating
     * @throws IllegalArgumentException if newData is not instance of <code>Float</code>
     * @throws DataFormatException if rating parsing failed
     */
    private void modifyRating(Object newData) throws DataFormatException {
        if ( ! (newData instanceof Float)) {
            throw new IllegalArgumentException(
                "In RATING modification: not a Float type argument."
            );
        }

        // Valid?
        float newRating = (Float) newData;

        if ( ! ParsingToolBO.parseRating(newRating) ) {
            throw new DataFormatException(
                "In RATING modification: not a valid rating -> " + newRating
            );
        }

        clubToManage.setRating(newRating);
    }   

    /**
     * Adds a new tag to the club tags list if
     * the new tag data is correct.
     * 
     * @param newData new tag
     * @throws IllegalArgumentException if newData is not instance of <code>String</code>
     * @throws DataFormatException if tag parsing failed
     */
    private void addTag(Object newData) throws DataFormatException
    {
        if ( ! (newData instanceof String)) {
            throw new IllegalArgumentException(
                "In TAG adding: not a String type argument."
            );
        }

        // Valid?
        String newTag = (String) newData;

        if ( ! ParsingToolBO.tagChecker.matcher(newTag).matches() ) {
            throw new DataFormatException(
                "In TAG adding: not a valid tag format -> " + newTag
            );
        }

        clubToManage.addTag(newTag);
    }

    /**
     * Removes a tag from the club tags list if
     * the new tag data is correct.
     * 
     * @param newData tag to remove
     * @throws IllegalArgumentException if newData is not instance of <code>String</code>
     * @throws DataFormatException if tag parsing failed
     */
    private void removeTag(Object newData) throws DataFormatException
    {
        if ( ! (newData instanceof String)) {
            throw new IllegalArgumentException(
                "In TAG removal: not a String type argument."
            );
        }

        // Valid?
        String tagToRemove = (String) newData;

        if ( ! ParsingToolBO.tagChecker.matcher(tagToRemove).matches() ) {
            throw new DataFormatException(
                "In TAG removal: not a valid tag format -> " + tagToRemove
            );
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





    /**
     * Modifies the club's location if the data is correct.
     * 
     */
    /*private void modifyCoordinates(Object newData) {
        if ( ! (newData instanceof Location)) {
            throw new IllegalArgumentException(
                "In LOCATION modification: not a Location type argument."
            );
        }

        // Valid?
        Location newCoordinates = (Location) newData;

        clubToManage.set
    }*/
}