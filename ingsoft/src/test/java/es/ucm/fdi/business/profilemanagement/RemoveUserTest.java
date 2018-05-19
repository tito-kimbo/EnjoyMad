package es.ucm.fdi.business.profilemanagement;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Tests the correct removal of {@code UserPOJO}s from the app
 * corresponding {@code UserDAO}.
 */
public class RemoveUserTest {

    ProfileManagerSA profileManager;
    ClubDAO clubDAO;
    UserDAO userDAO;

    @Before
    public void setUp() {
        clubDAO = new ClubDAOImp();
        userDAO = new UserDAOImp();

        profileManager = new ProfileManagerSAImp(clubDAO, userDAO);

        // Initially existing users
        List<UserPOJO> existingUsers = Arrays.asList(
            new UserPOJO("user01", "julesp786", "sdfa56,.4", "julesp@mail.com", "Julia Espín-García", LocalDate.parse("1998-03-27")),
            new UserPOJO("user02", "alfonrom2", "456asdf", "alf.rom@mail.com", "Alfonso Romo", LocalDate.parse("1997-05-14")),
            new UserPOJO("user03", "hugo_alsin", ".ñ5bh*y", "halsina@mail.com", "Hugo Alsina", LocalDate.parse("1999-02-05")),
            new UserPOJO("user04", "yolimed_23", "!*yi89", "yolmedina@mail.org.es", "Yolanda Medina", LocalDate.parse("1996-11-19")),
            new UserPOJO("user05", "90sofiyust3", "45%ghp", "soyuste@mail.com", "Sofía Yuste", LocalDate.parse("2000-04-16")),
            new UserPOJO("user06", "n3r3a_samp", "fg414$·s", "neresamp@mail.com", "Nerea Samper", LocalDate.parse("1999-08-25")),
            new UserPOJO("user07", "tonis4lg_do", "hju5/&as", "antosalg@mail.com", "Antonio Salgado", LocalDate.parse("1993-07-13")),
            new UserPOJO("user08", "ana_duq", "^*bgui596$", "anaduque@mail.com", "Ana Duque", LocalDate.parse("1998-12-07"))
        );

        try {
            for (UserPOJO user : existingUsers) {
               profileManager.addNewUser(user);
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (DataFormatException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }
    }

    /**
     * A series of existing {@code UserPOJO}s are removed from the
     * {@code userDAO} via ProfileManager. No exception must be caught.
     * Non-removed users must remain in DAO.
     */
    @Test
    public void validRemovalsTest() {
        List<String> remainID = Arrays.asList(
            "user01", "user04", "user05", "user06"
        );

        List<String> removedID = Arrays.asList(
            "user02", "user03", "user07", "user08"
        );

        // Removal
        try {
            for (String userID : removedID) {
                profileManager.removeUser(userID);
            }
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Checks
        for (String id : remainID) {
            if (!userDAO.exists(id)) { // unreacheable
                fail("Expected existance of: " + id);
            }
        }

        for (String id : removedID) {
            if (userDAO.exists(id)) { // unreachable
                fail("Expected not to exist: " + id);
            }
        }
    }

    /**
     * Attemp to remove a series of non-existing {@code UserPOJO}s
     * from the {@code userDAO} via ProfileManager. A exception must 
     * be caught for each invalid removal. Non-removed users must 
     * remain in DAO.
     */
    @Test
    public void invalidRemovalsTest() {
        List<String> remainID = Arrays.asList(
            "user01", "user02", "user03", "user04", 
            "user05", "user06", "user07", "user08"
        );

        List<String> invalidID = Arrays.asList(
            "user00", "user13", "user_9", "id_USER879"
        );

        // Invalid removal
        for (String userID : invalidID) {
            try {
                profileManager.removeUser(userID);

                // unreachable
                fail("Expected error did not take place: " + userID);
            } catch (NoSuchElementException e) {
                // must reach
            }
        }

        // Check
        for (String id : remainID) {
            if (!userDAO.exists(id)) { // unreacheable
                fail("Expected existance of: " + id);
            }
        }
    }
}
