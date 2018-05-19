package es.ucm.fdi.business.profilemanagement;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.zip.DataFormatException;

import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Tests the correct addition of new {@code ClubPOJO}s to the app
 * corresponding {@code ClubDAO}.
 */
public class NewUserTest {

    ProfileManagerSA profileManager;
    ClubDAO clubDAO;
    UserDAO userDAO;

    @Before
    public void setUp() {
        clubDAO = new ClubDAOImp();
        userDAO = new UserDAOImp();

        profileManager = new ProfileManagerSAImp(clubDAO, userDAO);
    }

    /**
     * A series of valid {@code UserPOJO}s are added to the
     * {@code userDAO} via the Profile Manager. No exception must
     * be caught.
     */
    @Test
    public void validClubsTest() {

        // * The password argument is not the password used by the user to log in,
        // but an encrypted hash from that log in password.
        List<UserPOJO> newUsers = Arrays.asList(
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
            for (UserPOJO user : newUsers) {
                profileManager.addNewUser(user);
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not the expected eror: " + e.getMessage());
        } catch (DataFormatException e) { // unreachable
            fail("Not the expected eror: " + e.getMessage());
        }
    }

    /**
     * A series of invalid {@code UserPOJO}s are tried to be added to the
     * {@code userDAO} via the Profile Manager, unsuccessfully. 
     * An exception must be caught for each user.
     */
    @Test
    public void invalidUsersTest() {
        
        List<UserPOJO> newUsers = Arrays.asList(
            // Invalid IDs
            new UserPOJO("", "julesp786", "sdfa56,.4", "julesp@mail.com", "Julia Espín-García", LocalDate.parse("1998-03-27")),
            new UserPOJO("    ", "alfonrom2", "456asdf", "alf.rom@mail.com", "Alfonso Romo", LocalDate.parse("1997-05-14")),
            new UserPOJO("user 03", "hugo_alsin", ".ñ5bh*y", "halsina@mail.org.es", "Hugo Alsina", LocalDate.parse("1999-02-05")),
            new UserPOJO("&user#04", "yolimed_23", "!*yi89", "yolmedina@mail.com", "Yolanda Medina", LocalDate.parse("1996-11-19")),

            // Invalid Usernames
            new UserPOJO("user05", "90.sofiyt3", "45%ghp", "soyuste@mail.com", "Sofía Yuste", LocalDate.parse("2000-04-16")),
            new UserPOJO("user06", "n3r&a#samp", "fg414$·s", "neresamp@mail.com", "Nerea Samper", LocalDate.parse("1999-08-25")),

            // Invalid Emails
            new UserPOJO("user07", "tonis4lg_do", "hju5/&as", "antosalgmail.com", "Antonio Salgado", LocalDate.parse("1993-07-13")),
            new UserPOJO("user08", "ana_duq", "^*bgui596$", "anad@uque@mail.com", "Ana Duque", LocalDate.parse("1998-12-07")),

            // Invalid Names
            new UserPOJO("user09", "julesp786", "sdfa56,.4", "julesp@mail.com", "J", LocalDate.parse("1998-03-27")),
            new UserPOJO("user10", "alfonrom2", "456asdf", "alf.rom@mail.com", "Jo Chan Ki Ros", LocalDate.parse("1997-05-14")),
            new UserPOJO("user11", "hugo_alsin", ".ñ5bh*y", "halsina@mail.com", "C4rl0s M4l3scr1t", LocalDate.parse("1999-02-05")),
            new UserPOJO("user12", "yolimed_23", "!*yi89", "yolmedina@mail.org.es", "Pablo Err#r", LocalDate.parse("1996-11-19")),

            // Invalid Birthdates
            new UserPOJO("user13", "90sofiyust3", "45%ghp", "soyuste@mail.com", "Sofía Yuste", LocalDate.parse("2005-04-16")),
            new UserPOJO("user14", "n3r3a_samp", "fg414$·s", "neresamp@mail.com", "Nerea Samper", LocalDate.parse("2013-08-25"))
        );

        for (UserPOJO user : newUsers) {
            try {
                profileManager.addNewUser(user);

                fail("No errors occurred."); // unreachable
            } catch (IllegalArgumentException  e) { // unreachable
                fail("Not the expected error: " + e.getMessage());
            } catch (DataFormatException  e) {
                // must reach
            }
        }
    }

    /**
     * A series of valid {@code UserPOJO}s are added to the
     * {@code userDAO} via the Profile Manager. Next, a series of valid
     * {@code UserPOJO}s with already existing IDs are tried to be added
     * unsuccessfully. No exception must be caught for new IDs. An exception
     * must be caught for each user with an already existing ID.
     */
    @Test
    public void conflictUsersTest() {
        List<UserPOJO> newUsers = Arrays.asList(
            new UserPOJO("user01", "julesp786", "sdfa56,.4", "julesp@mail.com", "Julia Espín-García", LocalDate.parse("1998-03-27")),
            new UserPOJO("user02", "alfonrom2", "456asdf", "alf.rom@mail.com", "Alfonso Romo", LocalDate.parse("1997-05-14")),
            new UserPOJO("user03", "hugo_alsin", ".ñ5bh*y", "halsina@mail.com", "Hugo Alsina", LocalDate.parse("1999-02-05")),
            new UserPOJO("user04", "yolimed_23", "!*yi89", "yolmedina@mail.org.es", "Yolanda Medina", LocalDate.parse("1996-11-19")),
            new UserPOJO("user05", "90sofiyust3", "45%ghp", "soyuste@mail.com", "Sofía Yuste", LocalDate.parse("2000-04-16")),
            new UserPOJO("user06", "n3r3a_samp", "fg414$·s", "neresamp@mail.com", "Nerea Samper", LocalDate.parse("1999-08-25")),
            new UserPOJO("user07", "tonis4lg_do", "hju5/&as", "antosalg@mail.com", "Antonio Salgado", LocalDate.parse("1993-07-13")),
            new UserPOJO("user08", "ana_duq", "^*bgui596$", "anaduque@mail.com", "Ana Duque", LocalDate.parse("1998-12-07"))
        );

        List<UserPOJO> conflictUsers = Arrays.asList(
            new UserPOJO("user01", "687pseluj", "sdfa56,.4", "pseluj@mail.com", "aícraG-nípsE ailuJ", LocalDate.parse("1998-03-27")),
            new UserPOJO("user03", "nisla_oguh", ".ñ5bh*y", "anislah@mail.com", "anislA oguH", LocalDate.parse("1999-02-05")),
            new UserPOJO("user04", "32_demiloy", "!*yi89", "anidemloy@mail.org.es", "anideM adnaloY", LocalDate.parse("1996-11-19")),
            new UserPOJO("user06", "pmas_a3r3n", "fg414$·s", "pmaseren@mail.com", "repmaS aereN", LocalDate.parse("1999-08-25"))
        );        

        // Valid users
        try {
            for (UserPOJO user : newUsers) {
                profileManager.addNewUser(user);
            }
        } catch (IllegalArgumentException e) { // unreachable
            System.err.println(e.getMessage());
        } catch (DataFormatException e) { // unreachable
            System.err.println(e.getMessage());
        }

        // Conflictive users
        for (UserPOJO user : conflictUsers) {
            try {
                profileManager.addNewUser(user);

                fail("No errors occurred."); // unreachable
            } catch (DataFormatException e) { // unreachable
                fail("Not the expected error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                // must reach
            }
        }
    }
}