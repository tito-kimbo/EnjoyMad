package es.ucm.fdi.business.profilemanagement;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.business.tagmanagement.TagManagerSA;
import es.ucm.fdi.business.tagmanagement.TagManagerSAImp;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.TagPOJO;

/**
 * Tests the correct addition of new {@code ClubPOJO}s to the app
 * corresponding {@code ClubDAO}.
 */
public class AddClubTest {

    final TagPOJO[] tags = {
            new TagPOJO("dance"), new TagPOJO("edm"), new TagPOJO("latino"),
            new TagPOJO("reguetton"), new TagPOJO("indie"), new TagPOJO("after"),
            new TagPOJO("barato"), new TagPOJO("salsa"), new TagPOJO("chill")
        };

    ProfileManagerSA profileManager;
    ClubDAO clubDAO;
    UserDAO userDAO;

    @Before
    public void setUp() {
        clubDAO = new ClubDAOImp();
        userDAO = new UserDAOImp();

        TagManagerSA tagManager = new TagManagerSAImp(Arrays.asList(tags));

        profileManager = new ProfileManagerSAImp(clubDAO, userDAO, tagManager);
    }

    /**
     * A series of valid {@code ClubPOJO}s are added to the
     * {@code clubDAO} via the Profile Manager. No exception must
     * be caught.
     */
    @Test
    public void validClubsTest() {
        List<ClubPOJO> newClubs = Arrays.asList(
            new ClubPOJO("club01", "Astra", "C/ Estrella, 55", 0f, 
                    new HashSet<TagPOJO>(Arrays.asList(tags[0]))),
            new ClubPOJO("club02", "Bowi", "C/ Galileo, 26", 12.50f,
                    new HashSet<TagPOJO>(Arrays.asList(tags[1]))),
            new ClubPOJO("club03", "Copérnico", "C/ Fernández, 67", 13.00f,
                    new HashSet<TagPOJO>(Arrays.asList(tags[2]))),
            new ClubPOJO("club04", "Kapital", "C/ Atocha, 125", 18.00f,
                    new HashSet<TagPOJO>(Arrays.asList(tags[3]))),
            new ClubPOJO("club05", "Mitty", "C/ Julián, 4", 11.00f,
                    new HashSet<TagPOJO>(Arrays.asList(tags[4]))),
            new ClubPOJO("club06", "Gabana", "C/ Velázquez, 6", 15.00f,
                    new HashSet<TagPOJO>(Arrays.asList(tags[5]))),
            new ClubPOJO("club07", "Independance", "C/ Arlaban, 7", 5.00f,
                    new HashSet<TagPOJO>(Arrays.asList(tags[6]))),
            new ClubPOJO("club08", "Barceló", "C/ Barceló, 11", 14.00f,
                    new HashSet<TagPOJO>(Arrays.asList(tags[7], tags[8], tags[0]))),
            new ClubPOJO("club09", "Joy Eslava", "C/ Arenal, 11", 12.50f,
                    new HashSet<TagPOJO>())
        );

        try {
            for (ClubPOJO club : newClubs) {
                profileManager.addNewClub(club);
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not the expected eror: " + e.getMessage());
        }
    }

    /**
     * Attemp to add a series of invalid {@code ClubPOJO}s to the
     * {@code clubDAO} via the Profile Manager, unsuccessfully. 
     * An exception must be caught for each club.
     */
    @Test
    public void invalidClubsTest() {
        Set<TagPOJO> invalidTags = new HashSet<TagPOJO>( Arrays.asList(
                tags[5], new TagPOJO("escuela"), tags[3]
        ));
        
        List<ClubPOJO> newClubs = Arrays.asList(
            // Invalid IDs
            new ClubPOJO("", "Astra", "C/ Estrella, 55", 0f, new HashSet<TagPOJO>()),
            new ClubPOJO("   ", "Bowi", "C/ Galileo, 26", 12.50f, new HashSet<TagPOJO>()),
            new ClubPOJO("club 03", "Copérnico", "C/ Fernández, 67", 13.00f, new HashSet<TagPOJO>()),
            new ClubPOJO("&club#04", "Kapital", "C/ Atocha, 125", 18.00f, new HashSet<TagPOJO>()),

            // Invalid Price
            new ClubPOJO("club06", "Gabana", "C/ Velázquez", -5.00f, new HashSet<TagPOJO>()),

            // Invalid Tags
            new ClubPOJO("club07", "Independance", "C/ Arlaban, 7", 5.00f, invalidTags)
        );

        for (ClubPOJO club : newClubs) {
            try {
                profileManager.addNewClub(club);

                fail("No errors occurred."); // unreachable
            } catch (IllegalArgumentException  e) { // unreachable
                // must reach
            }
        }
    }

    /**
     * A series of valid {@code ClubPOJO}s are added to the
     * {@code clubDAO} via the Profile Manager. Next, we attempt 
     * to add a series of valid {@code ClubPOJO}s with already 
     * existing IDs unsuccessfully. No exception must be caught 
     * for new IDs. An exception must be caught for each club 
     * with an already existing ID.
     */
    @Test
    public void conflictClubsTest() {
        List<ClubPOJO> newClubs = Arrays.asList(
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

        List<ClubPOJO> conflictClubs = Arrays.asList(
            new ClubPOJO("club01", "artsA", "C/ allertsE, 55", 0f, new HashSet<TagPOJO>()),
            new ClubPOJO("club03", "ocinrépoC", "C/ zednánreF, 76", 0.13f, new HashSet<TagPOJO>()),
            new ClubPOJO("club04", "latipaK", "C/ ahcotA, 521", 0.18f, new HashSet<TagPOJO>()),
            new ClubPOJO("club06", "anabaG", "C/ zeuqzáleV, 6", 0.15f, new HashSet<TagPOJO>()),
            new ClubPOJO("club08", "ólecraB", "C/ ólecraB, 11", 14.00f, new HashSet<TagPOJO>()),
            new ClubPOJO("club09", "avalsE yoJ", "C/ lanerA, 11", 5.12f, new HashSet<TagPOJO>())
        );

        // Valid clubs
        try {
            for (ClubPOJO club : newClubs) {
                profileManager.addNewClub(club);
            }
        } catch (IllegalArgumentException e) { // unreachable
            System.err.println(e.getMessage());
        }

        // Conflictive clubs
        for (ClubPOJO club : conflictClubs) {
            try {
                profileManager.addNewClub(club);

                fail("No errors occurred."); // unreachable
            } catch (IllegalArgumentException e) {
                // must reach
            }
        }
    }
}