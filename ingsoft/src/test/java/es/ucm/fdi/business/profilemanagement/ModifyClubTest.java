package es.ucm.fdi.business.profilemanagement;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.business.profilemanagement.tools.ClubModifierHelper;
import es.ucm.fdi.business.tagmanagement.TagManagerSA;
import es.ucm.fdi.business.tagmanagement.TagManagerSAImp;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.Location;
import es.ucm.fdi.integration.data.TagPOJO;

/**
 * Tests the correct modification of {@code ClubPOJO}s in the 
 * app corresponding {@code ClubDAO}.
 */
public class ModifyClubTest {

    final TagPOJO[] tags = {
            new TagPOJO("dance"), new TagPOJO("edm"), new TagPOJO("latino"),
            new TagPOJO("reguetton"), new TagPOJO("indie"), new TagPOJO("after"),
            new TagPOJO("barato"), new TagPOJO("salsa"), new TagPOJO("chill")
        };

    ProfileManagerSA profileManager;
    ClubDAO clubDAO;
    UserDAO userDAO;

    public class ModificationInfo {
        String clubID;
        ClubModifierHelper type;
        Object data;

        ModificationInfo(String id, ClubModifierHelper t, Object d) {
            clubID = id;
            type = t;
            data = d;
        }

        @Override
        public String toString() {
            return  "Club: " + clubID + 
                    " Modification: " + type.toString() +
                    " Data: " + data.toString();
        }
    }

    @Before
    public void setUp() {
        clubDAO = new ClubDAOImp();
        userDAO = new UserDAOImp();

        TagManagerSA tagManager = new TagManagerSAImp(Arrays.asList(tags));

        profileManager = new ProfileManagerSAImp(clubDAO, userDAO, tagManager);

        // Initially existing clubs
        List<ClubPOJO> existingClubs = Arrays.asList(
            new ClubPOJO("club01", "Astra", "C/ Estrella, 55", 0f, 
                new HashSet<TagPOJO>(Arrays.asList(tags[0], tags[1], tags[2]))),
            new ClubPOJO("club02", "Bowi", "C/ Galileo, 26", 12.50f,
                new HashSet<TagPOJO>(Arrays.asList(tags[3], tags[4], tags[5]))),
            new ClubPOJO("club03", "Copérnico", "C/ Fernández, 67", 13.00f,
                new HashSet<TagPOJO>(Arrays.asList(tags[6], tags[7], tags[8])))
        );

        try {
            for (ClubPOJO club : existingClubs) {
               profileManager.addNewClub(club);
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }
    }

    @Test
    public void validCommercialNameTest() {
        List<ClubPOJO> modifications = Arrays.asList(
            new ClubPOJO("club01", "Aspera", null, ClubPOJO.PRICE_NULL, null, null, null),
            new ClubPOJO("club02", "Zetta", null, ClubPOJO.PRICE_NULL, null, null, null),
            new ClubPOJO("club03", "Stars", null, ClubPOJO.PRICE_NULL, null, null, null)            
        );

        // Valid modification
        try {
            for (ClubPOJO clubChanges : modifications) {
                profileManager.modifyClubData(clubChanges.getID(), clubChanges);

                // must reach
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Check
        for (ClubPOJO clubChanges : modifications) {
            String clubCommercialName = clubDAO.getClub(clubChanges.getID()).getCommercialName();
            String newCommercialName = clubChanges.getCommercialName();

            if (!clubCommercialName.equals(newCommercialName)) { // unreachable
                fail("Expected equal: " + clubCommercialName + " <-> " + newCommercialName);
            }
        }
    }

    @Test
    public void validAddressTest() {
        List<ClubPOJO> modifications = Arrays.asList(
            new ClubPOJO("club01", null, "C/ Jerónimo, 76", ClubPOJO.PRICE_NULL, null, null, null),
            new ClubPOJO("club02", null, "C/ Tajo, 2", ClubPOJO.PRICE_NULL, null, null, null),
            new ClubPOJO("club03", null, "Avda. de las Rosas, 234", ClubPOJO.PRICE_NULL, null, null, null)
        );

        // Valid modification
        try {
            for (ClubPOJO clubChanges : modifications) {
                profileManager.modifyClubData(clubChanges.getID(), clubChanges);

                // must reach
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Check
        for (ClubPOJO clubChanges : modifications) {
            String clubAddress = clubDAO.getClub(clubChanges.getID()).getAddress();
            String newAddress = clubChanges.getAddress();

            if (!clubAddress.equals(newAddress)) { // unreachable
                fail("Expected equal: " + clubAddress + " <-> " + newAddress);
            }
        }
    }

    @Test
    public void validPriceTest() {
        List<ClubPOJO> modifications = Arrays.asList(
            new ClubPOJO("club01", null, null, 0.0f, null, null, null),
            new ClubPOJO("club02", null, null, 18.50f, null, null, null),
            new ClubPOJO("club03", null, null, 11.00f, null, null, null)
        );

        // Valid modification
        try {
            for (ClubPOJO clubChanges : modifications) {
                profileManager.modifyClubData(clubChanges.getID(), clubChanges);

                // must reach
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Check
        for (ClubPOJO clubChanges : modifications) {
            Float clubPrice = clubDAO.getClub(clubChanges.getID()).getPrice();
            Float newPrice = clubChanges.getPrice();

            if (!clubPrice.equals(newPrice)) { // unreachable
                fail("Expected equal: " + clubPrice + " <-> " + newPrice);
            }
        }
    }

    @Test
    public void validLocationTest() {
        List<ClubPOJO> modifications = Arrays.asList(
            new ClubPOJO("club01", null, null, ClubPOJO.PRICE_NULL, new Location(0.313, 12.456), null, null),
            new ClubPOJO("club02", null, null, ClubPOJO.PRICE_NULL, new Location(-20.153, 32.876), null, null),
            new ClubPOJO("club03", null, null, ClubPOJO.PRICE_NULL, new Location(31.293, -89.784), null, null)
        );

        // Valid modification
        try {
            for (ClubPOJO clubChanges : modifications) {
                profileManager.modifyClubData(clubChanges.getID(), clubChanges);

                // must reach
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Check
        for (ClubPOJO clubChanges : modifications) {
            Location clubLocation = clubDAO.getClub(clubChanges.getID()).getLocation();
            Location newLocation = clubChanges.getLocation();

            if (!clubLocation.equals(newLocation)) { // unreachable
                fail("Expected equal: " + clubLocation + " <-> " + newLocation);
            }
        }
    }

    @Test
    public void validTagsTest() {
        Set<TagPOJO> tagsSet1 = new HashSet<TagPOJO>();
        Set<TagPOJO> tagsSet2 = new HashSet<TagPOJO>(
            Arrays.asList(tags[0], tags[4], tags[7]));
        Set<TagPOJO> tagsSet3 = new HashSet<TagPOJO>(
            Arrays.asList(tags[5], tags[2]));

        List<ClubPOJO> modifications = Arrays.asList(
            new ClubPOJO("club01", null, null, ClubPOJO.PRICE_NULL, null, tagsSet1, null),
            new ClubPOJO("club02", null, null, ClubPOJO.PRICE_NULL, null, tagsSet2, null),
            new ClubPOJO("club03", null, null, ClubPOJO.PRICE_NULL, null, tagsSet3, null)
        );

        // Valid modification
        try {
            for (ClubPOJO clubChanges : modifications) {
                profileManager.modifyClubData(clubChanges.getID(), clubChanges);

                // must reach
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Check
        for (ClubPOJO clubChanges : modifications) {
            Set<TagPOJO> clubTags = clubDAO.getClub(clubChanges.getID()).getTags();
            Set<TagPOJO> newTags = clubChanges.getTags();

            if (!clubTags.equals(newTags)) { // unreachable
                fail("Club does not contain the expected tags: ");
            }
        }
    }

    /**
     * TODO Comentar
     */
    @Test
    public void invalidClubTest() {
        List<ClubPOJO> modifications = Arrays.asList(
            new ClubPOJO("club_13", "Existance", null, ClubPOJO.PRICE_NULL, null, null, null), // commercial name
            new ClubPOJO("club00", null, "C/ Piruleta, 12", ClubPOJO.PRICE_NULL, null, null, null), // address
            new ClubPOJO("id_CLUB76", null, null, 123.45f, null, null, null), // price
            new ClubPOJO("clmad87", null, null, ClubPOJO.PRICE_NULL, new Location(23.2, 5.4), null, null), // location
            new ClubPOJO("madnor2", null, null, ClubPOJO.PRICE_NULL, null, new HashSet<TagPOJO>(), null) // tags
        );
        
        for (ClubPOJO clubChanges : modifications) {
            try {
                profileManager.modifyClubData(clubChanges.getID(), clubChanges);

                // unreachable
                fail("Expected error with modification.");
            } catch (IllegalArgumentException e) { // unreachable
                fail("Not expected error: " + e.getMessage());
            } catch (NoSuchElementException e) {
                // must reach
            }
        }
    }

    @Test
    public void invalidDataTest() {
        Set<TagPOJO> invalidTags1 = new HashSet<TagPOJO>( 
            Arrays.asList(tags[5], new TagPOJO("escuela"), tags[3]));
        Set<TagPOJO> invalidTags2 = new HashSet<TagPOJO>( 
            Arrays.asList(tags[5], null, tags[3]));

        List<ClubPOJO> modifications = Arrays.asList(
            // COMMERCIAL NAME -> All valid except for \n
            new ClubPOJO("club01", "Exist\nance", null, ClubPOJO.PRICE_NULL, null, null, null),
            new ClubPOJO("club01", "Extasis\n", null, ClubPOJO.PRICE_NULL, null, null, null),
            new ClubPOJO("club01", "\nIndependance", "C/ Arlaban, 7", 5.00f, new Location(23.2, 5.4),
                    new HashSet<TagPOJO>(Arrays.asList(tags[6])), null),

            // ADDRESS -> All valid except for \n
            new ClubPOJO("club02", null, "C/ Piru\nleta, 12", ClubPOJO.PRICE_NULL, null, null, null),
            new ClubPOJO("club02", null, "C/ Ancha, 12\n", ClubPOJO.PRICE_NULL, null, null, null),
            new ClubPOJO("club02", "Independance", "\nC/ Arlaban, 7", 5.00f, new Location(23.2, 5.4),
                    new HashSet<TagPOJO>(Arrays.asList(tags[6])), null),

            // PRICE -> Non-negative float. !Beware value of CLubPOJO.PRICE_NULL
            new ClubPOJO("club03", null, null, -20.50f, null, null, null),
            new ClubPOJO("club03", null, null, -0.01f, null, null, null),
            new ClubPOJO("club03", "Independance", "C/ Arlaban, 7", -25.00f, new Location(23.2, 5.4),
                    new HashSet<TagPOJO>(Arrays.asList(tags[6])), null),

            // LOCATION -> No conditions.

            // TAGS -> Need to be active in tagManager.
            new ClubPOJO("club01", null, null, ClubPOJO.PRICE_NULL, null, invalidTags1, null),
            new ClubPOJO("club01", null, null, ClubPOJO.PRICE_NULL, null, invalidTags2, null),
            new ClubPOJO("club03", "Independance", "C/ Arlaban, 7", 5.00f, new Location(23.2, 5.4),
                    invalidTags1, null)
        );

        // Invalid modification
        try {
            for (ClubPOJO clubChanges : modifications) {
                profileManager.modifyClubData(clubChanges.getID(), clubChanges);

                // unreachable
                fail("Expected error in the modification.");
            }
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // must reach
        }

        // Check integrity
        for (ClubPOJO clubChanges : modifications) {
            ClubPOJO unchanged = clubDAO.getClub(clubChanges.getID());

            if (unchanged.getCommercialName().equals(clubChanges.getCommercialName())
                    || unchanged.getAddress().equals(clubChanges.getAddress())
                    || unchanged.getPrice() == clubChanges.getPrice()
                    || unchanged.getLocation().equals(clubChanges.getLocation())
                    || unchanged.getTags().equals(clubChanges.getTags())) {
                        fail("Club integrity fail.");
                    }
        }
    }


}