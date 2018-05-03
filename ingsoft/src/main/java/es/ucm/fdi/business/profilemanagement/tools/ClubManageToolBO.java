package es.ucm.fdi.business.profilemanagement.tools;

import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.Location;
import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.business.util.ParsingToolBO;
import es.ucm.fdi.integration.ClubDAOImp;

import java.util.zip.DataFormatException;

/**
 * Class to be used by the {@code ProfileManagerSAImp} 
 * to modify particular attributes of a 
 * {@code ClubPOJO} instance.
 */
public class ClubManageToolBO {
    private ClubPOJO clubToManage;
    private ClubDAOImp clubDAO;

    /**
     * Constructs a {@link ClubManageToolBO} to be used by
     * the {@code ProfileManagerSAImp} methods. It receives 
     * the {@code ClubDAO} database to rearrange it in case 
     * of id modification.
     * 
     * @param club          - club to be managed
     * @param clubDAO       - clubs database
     */
    public ClubManageToolBO(ClubPOJO club, ClubDAOImp clubDAO) {
        clubToManage = club;
        this.clubDAO = clubDAO;
    }




    /**
     * Given a new data and its type, it modifies the 
     * {@code clubToManage} corresponding attribute,
     * provided the new data is correct.
     * 
     * @param dataID        - {@code DataType} of the 
     *                      attribute to be modified
     * @param newData       - new value of the 
     *                      attribute
     * 
     * @throws IlegalArgumentException      if caught or if {@code dataID}
     *                                      is not contemplated
     * @throws DataFormatException          if caught, because of parsing 
     *                                      failure
     */
    public void modify(ClubDataType dataID, Object newData) throws DataFormatException {
        try {
            switch (dataID) {
            case ID: // implies DAO-map change
                modifyID(newData);
                break;
            case COMMERCIAL_NAME:
                modifyCommercialName(newData);
                break;
            case LOCATION:
                modifyLocation(newData);
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
                    "In Club modification: " + 
                    "not an implemented club data modification -> " + 
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
     * Modifies the club {@code ID} if the data is correct.
     * 
     * @param newData       - new club {@code ID}
     *
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code String}
     * @throws DataFormatException          if {@code newID} parsing 
     *                                      failed
     */
    private void modifyID(Object newData) 
            throws DataFormatException {
        
        // Instance of...
        if ( ! ( newData instanceof String ) ) {
            throw new IllegalArgumentException(
                "In ID modification: " + 
                "not a String type argument."
            );
        }

        // Valid?
        String newID = (String) newData;

        if ( ! ParsingToolBO.parseID(newID) ) {
            throw new DataFormatException(
                "In ID modification: " + 
                "not a valid ID format -> " + 
                newID
            );
        }

        // Map change
        clubDAO.removeClub(clubToManage.getID());
        clubToManage.setID(newID);
        clubDAO.addClub(clubToManage);
    }

    /**
     * Modifies the club {@code CommercialName} if the data 
     * is correct.
     * 
     * @param newData       - new club {@code CommercialName}
     * 
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code String}
     * @throws DataFormatException          if {@code newCommercialName} 
     *                                      parsing failed
     */
    private void modifyCommercialName(Object newData) 
            throws DataFormatException {
        
        // Instance of...
        if ( ! ( newData instanceof String ) ) {
            throw new IllegalArgumentException(
                "In COMMERCIAL NAME modification: " + 
                "not a String type argument."
            );
        }

        // Valid?
        String newCommercialName = (String) newData;

        if ( ! ParsingToolBO.parseCommercialName(newCommercialName) ) {
            throw new DataFormatException(
                "In COMMERCIAL NAME modification: " + 
                "not a valid commercial name format -> " + 
                newCommercialName
            );
        }

        // Modification.
        clubToManage.setCommercialName(newCommercialName);
    }

    /**
     * Modifies the club's location if the data is correct.
     * 
     */
     private void modifyLocation(Object newData) { 
        if ( ! (newData instanceof Location ) ) { 
            throw new IllegalArgumentException(
                "In LOCATION modification: " +
                "not a Location type argument."
            ); 
        } 
        
        // Valid?
        Location newLocation = (Location) newData; 
        
        clubToManage.setLocation(newLocation);
    }
     

    /**
     * Modifies the club {@code Address} if the data is correct.
     * 
     * @param newData       - new club {@code Address}
     * 
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code String}
     * @throws DataFormatException          if {@code newAddress} parsing 
     *                                      failed
     */
    private void modifyAddress(Object newData) 
            throws DataFormatException {
        
        // Instance of...
        if ( ! ( newData instanceof String ) ) {
            throw new IllegalArgumentException(
                "In ADDRESS modification: " + 
                "not a String type argument."
            );
        }

        // Valid?
        String newAddress = (String) newData;

        if ( ! ParsingToolBO.parseAddress(newAddress) ) {
            throw new DataFormatException(
                "In ADDRESS modification: " + 
                "not a valid address format -> " + 
                newAddress
            );
        }

        // Modification.
        clubToManage.setAddress(newAddress);
    }

    /**
     * Modifies the club {@code TicketPrice} if the 
     * data is correct.
     * 
     * @param newData       - new club {@code TicketPrice}
     * 
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code Float}
     * @throws DataFormatException          if {@code newPrice} parsing 
     *                                      failed
     */
    private void modifyPrice(Object newData) 
            throws DataFormatException {


        if ( ! (newData instanceof Float ) ) {
            throw new IllegalArgumentException(
                "In PRICE modification: " + 
                "not a Float type argument."
            );
        }

        // Valid?
        float newPrice = (Float) newData;

        if ( ! ParsingToolBO.parsePrice(newPrice) ) {
            throw new DataFormatException(
                "In PRICE modification: " + 
                "not a valid price -> " + 
                newPrice
            );
        }

        // Modification.
        clubToManage.setPrice(newPrice);
    }

    /**
     * <p>
     * ATTENTION: This method is not thought to be used 
     * initially in the general implementation, as it would
     * unpredictably alter the rating mean.
     * </p>
     * <p>
     * Modifies the club {@code Rating} if the data is correct.
     * </p>
     * 
     * @param newData       - new club {@code rating}
     * 
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code Float}
     * @throws DataFormatException          if {@code newRating} parsing 
     *                                      failed
     */
    private void modifyRating(Object newData) 
            throws DataFormatException {

        // Instance of
        if ( ! ( newData instanceof Float ) ) {
            throw new IllegalArgumentException(
                "In RATING modification: " + 
                "not a Float type argument."
            );
        }

        // Valid?
        float newRating = (Float) newData;

        if ( ! ParsingToolBO.parseRating(newRating) ) {
            throw new DataFormatException(
                "In RATING modification: " + 
                "not a valid rating -> " + 
                newRating
            );
        }

        // Modification.
        clubToManage.setRating(newRating);
    }

    /**
     * Adds a new {@code TagPOJO} to the club {@code TagList} 
     * if the data is correct.
     * 
     * @param newData       - new club {@code TagPOJO}
     * 
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code TagPOJO}
     * @throws DataFormatException          if {@code newTag} parsing 
     *                                      failed
     */
    private void addTag(Object newData) 
            throws DataFormatException {

        // Instance of...
        if ( ! ( newData instanceof String ) ) {
            throw new IllegalArgumentException(
                "In TAG adding: " + 
                "not a String type argument."
            );
        }

        // Valid?
        TagPOJO newTag = (TagPOJO) newData;

        /*
        if (!ParsingToolBO.tagChecker.matcher(newTag).matches()) {
            throw new DataFormatException("In TAG adding: not a valid tag format -> " + newTag);
        }
        */

        // Adding.
        clubToManage.addTag(newTag);
    }

    /**
     * Removes a {@code TagPOJO} from the club {@code TagList}
     * if the data is correct.
     * 
     * @param newData       - club {@code TagPOJO} to remove
     * 
     * @throws IllegalArgumentException     if {@code newData} is not 
     *                                      instance of {@code TagPOJO}
     */
    private void removeTag(Object newData) 
            throws DataFormatException {

        // Instance of...
        if ( ! ( newData instanceof String ) ) {
            throw new IllegalArgumentException(
                "In TAG removal: " + 
                "not a String type argument."
            );
        }

        TagPOJO tagToRemove = (TagPOJO) newData;

        // Removal
        clubToManage.removeTag(tagToRemove);
    }

    /**
     * Clears the club {@code TagList}.
     */
    private void clearTags() {
        clubToManage.clearTags();
    }
}