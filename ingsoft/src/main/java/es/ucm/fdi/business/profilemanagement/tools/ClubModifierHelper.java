package es.ucm.fdi.business.profilemanagement.tools;

import java.util.zip.DataFormatException;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.business.util.ParsingToolHelper;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.Location;

/**
 * Enum class representing the attributes of a ClubPOJO.
 * 
 * @version 08.05.2018
 */
public enum ClubModifierHelper { 

    COMMERCIAL_NAME("COMMERCIAL_NAME") {
        @Override
        public void modify(ClubPOJO clubToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {
            
            // Instance of...
            if ( ! ( newData instanceof String ) ) {
                throw new IllegalArgumentException(
                    "In COMMERCIAL NAME modification: " + 
                    "not a String type argument."
                );
            }

            // Valid?
            String newCommercialName = (String) newData;

            if ( ! ParsingToolHelper.parseCommercialName(newCommercialName) ) {
                throw new DataFormatException(
                    "In COMMERCIAL NAME modification: " + 
                    "not a valid commercial name format -> " + 
                    newCommercialName
                );
            }

            // Modification.
            clubToManage.setCommercialName(newCommercialName);
        }
    }, 

    LOCATION("LOCATION") {
        @Override
        public void modify(ClubPOJO clubToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {

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
    },

    ADDRESS("ADDRESS") {
        @Override
        public void modify(ClubPOJO clubToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {

            // Instance of...
            if ( ! ( newData instanceof String ) ) {
                throw new IllegalArgumentException(
                    "In ADDRESS modification: " + 
                    "not a String type argument."
                );
            }

            // Valid?
            String newAddress = (String) newData;

            if ( ! ParsingToolHelper.parseAddress(newAddress) ) {
                throw new DataFormatException(
                    "In ADDRESS modification: " + 
                    "not a valid address format -> " + 
                    newAddress
                );
            }

            // Modification.
            clubToManage.setAddress(newAddress);
        }
    },

    PRICE("PRICE") {
        @Override
        public void modify(ClubPOJO clubToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {
            
            if ( ! (newData instanceof Float ) ) {
            throw new IllegalArgumentException(
                    "In PRICE modification: " + 
                    "not a Float type argument."
                );
            }

            // Valid?
            float newPrice = (Float) newData;

            if ( ! ParsingToolHelper.parsePrice(newPrice) ) {
                throw new DataFormatException(
                    "In PRICE modification: " + 
                    "not a valid price -> " + 
                    newPrice
                );
            }

            // Modification.
            clubToManage.setPrice(newPrice);
        }
    },

    RATING("RATING") {
        @Override
        public void modify(ClubPOJO clubToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {
            
            // Instance of
            if ( ! ( newData instanceof Float ) ) {
                throw new IllegalArgumentException(
                    "In RATING modification: " + 
                    "not a Float type argument."
                );
            }

            // Valid?
            float newRating = (Float) newData;

            if ( ! ParsingToolHelper.parseRating(newRating) ) {
                throw new DataFormatException(
                    "In RATING modification: " + 
                    "not a valid rating -> " + 
                    newRating
                );
            }

            // Modification.
            clubToManage.setRating(newRating);
        }
    },

    ADD_TAG("ADD_TAG") {
        @Override
        public void modify(ClubPOJO clubToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {
            
            // Instance of...
            if ( ! ( newData instanceof TagPOJO ) ) {
                throw new IllegalArgumentException(
                    "In TAG adding: " + 
                    "not a TagPOJO type argument."
                );
            }

            // Valid?
            TagPOJO newTag = (TagPOJO) newData;

            /*
            if (!ParsingToolHelper.tagChecker.matcher(newTag).matches()) {
                throw new DataFormatException("In TAG adding: not a valid tag format -> " + newTag);
            }
            */

            // Adding.
            clubToManage.addTag(newTag);
        }
    },

    REMOVE_TAG("REMOVE_TAG") {
        @Override
        public void modify(ClubPOJO clubToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {
            
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
    },

    CLEAR_TAGS("CLEAR_TAGS") {
        @Override
        public void modify(ClubPOJO clubToManage, Object newData) throws
                IllegalArgumentException, DataFormatException {
            
            clubToManage.clearTags();
        }
    };


	
	
    private String type;

    private ClubModifierHelper(String type) {
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
     * {@code ClubModifierBO} itself.
     * 
     * @param clubToManage - club to be modified
     * @param newData      - club new data
     * 
     * @throws IllegalArgumentException if {@code newData} is not an instance 
     *                                  of the correct class
     * @throws DataFormatException      if {@code newData} parsing failed
     *                                  
     */
    public abstract void modify(ClubPOJO clubToManage, Object newData) throws
            IllegalArgumentException, DataFormatException;
}