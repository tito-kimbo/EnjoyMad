package es.ucm.fdi.business.profilemanagement;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * Tests the correct removal of {@code ClubPOJO}s from the app
 * corresponding {@code ClubDAO}.
 */
public class RemoveClubTest {

    ProfileManagerSA profileManager;
    ClubDAO clubDAO;
    UserDAO userDAO;

    @Before
    public void setUp() {
        clubDAO = new ClubDAOImp();
        userDAO = new UserDAOImp();

        profileManager = new ProfileManagerSAImp(clubDAO, userDAO);

        // Initially existing clubs
        List<ClubPOJO> existingClubs = Arrays.asList(
            new ClubPOJO("club01", "Astra", "C/ Estrella, 55", 0f, new HashSet<TagPOJO>()),
            new ClubPOJO("club02", "Bowi", "C/ Galileo, 26", 12.50f, new HashSet<TagPOJO>()),
            new ClubPOJO("club03", "Copérnico", "C/ Fernández, 67", 13.00f, new HashSet<TagPOJO>()),
            new ClubPOJO("club04", "Kapital", "C/ Atocha, 125", 18.00f, new HashSet<TagPOJO>()),
            new ClubPOJO("club05", "Mitty", "C/ Julián, 4", 11.00f, new HashSet<TagPOJO>()),
            new ClubPOJO("club06", "Gabana", "C/ Velázquez, 6", 15.00f, new HashSet<TagPOJO>()),
            new ClubPOJO("club07", "Independance", "C/ Arlaban, 7", 5.00f, new HashSet<TagPOJO>()),
            new ClubPOJO("club08", "Barceló", "C/ Barceló, 11", 14.00f, new HashSet<TagPOJO>()),
            new ClubPOJO("club09", "Joy Eslava", "C/ Arenal, 11", 12.50f, new HashSet<TagPOJO>())
        );

        try {
            for (ClubPOJO club : existingClubs) {
               profileManager.addNewClub(club);
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (DataFormatException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }
    }

    /**
     * A series of existing {@code ClubPOJO}s are removed from the
     * {@code clubDAO} via ProfileManager. No exception must be caught.
     * Non-removed clubs must remain in DAO.
     */
    @Test
    public void validRemovalsTest() {
        List<String> remainID = Arrays.asList(
            "club01", "club04", "club05", "club06", "club09"
        );

        List<String> removedID = Arrays.asList(
            "club02", "club03", "club07", "club08"
        );

        // Removal
        try {
            for (String clubID : removedID) {
                profileManager.removeClub(clubID);
            }
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Checks
        for (String id : remainID) {
            if (!clubDAO.exists(id)) { // unreacheable
                fail("Expected existance of: " + id);
            }
        }

        for (String id : removedID) {
            if (clubDAO.exists(id)) { // unreachable
                fail("Expected not to exist: " + id);
            }
        }
    }

    /**
     * Attemp to remove a series of non-existing {@code ClubPOJO}s
     * from the {@code clubDAO} via ProfileManager. A exception must 
     * be caught for each invalid removal. Non-removed clubs must 
     * remain in DAO.
     */
    @Test
    public void invalidRemovalsTest() {
        List<String> remainID = Arrays.asList(
            "club01", "club02", "club03", "club04", "club05", 
            "club06", "club07", "club08", "club09"
        );

        List<String> invalidID = Arrays.asList(
            "club00", "club13", "club_9", "id_CLUB475"
        );

        // Invalid removal
        for (String clubID : invalidID) {
            try {
                profileManager.removeClub(clubID);

                // unreachable
                fail("Expected error did not take place: " + clubID);
            } catch (NoSuchElementException e) {
                // must reach
            }
        }

        // Check
        for (String id : remainID) {
            if (!clubDAO.exists(id)) { // unreacheable
                fail("Expected existance of: " + id);
            }
        }
    }
}
