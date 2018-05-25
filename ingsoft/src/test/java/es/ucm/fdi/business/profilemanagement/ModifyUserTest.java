package es.ucm.fdi.business.profilemanagement;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.business.tagmanagement.TagManagerSA;
import es.ucm.fdi.business.tagmanagement.TagManagerSAImp;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.TagPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Tests the correct modification of {@code UserPOJO}s in the 
 * app corresponding {@code UserDAO}.
 */
public class ModifyUserTest {

    ProfileManagerSA profileManager;
    ClubDAO clubDAO;
    UserDAO userDAO;


    @Before
    public void setUp() {
        clubDAO = new ClubDAOImp();
        userDAO = new UserDAOImp();

        TagManagerSA tagManager = new TagManagerSAImp(new ArrayList<TagPOJO>());
        
        profileManager = new ProfileManagerSAImp(clubDAO, userDAO, tagManager);

        // Initially existing users
        List<UserPOJO> existingUsers = Arrays.asList(
            new UserPOJO("user01", "julesp786", "sdfa56,.4", "jul_esp@mail.com", "Julia Espín-García", LocalDate.parse("1998-03-27")),
            new UserPOJO("user02", "alfonrom2", "456asdf", "alf.rom15@mail.es", "Alfonso Romo", LocalDate.parse("1997-05-14")),
            new UserPOJO("user03", "hugo_alsin", ".ñ5bh*y", "h+alsina@mail.gob", "Hugo Alsina", LocalDate.parse("1999-02-05"))
        );

        try {
            for (UserPOJO user : existingUsers) {
               profileManager.addNewUser(user);
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }
    }

    @Test
    public void validMultipleTest() {
        List<UserPOJO> modifications = Arrays.asList(
            new UserPOJO("user01", null, null, null, null, null),
            new UserPOJO("user02", "anduque82", null, "nuevo23correo@mail.com", null, null),
            new UserPOJO("user03", "serapis67", "11ar23fda581gfs32134", "cambio_radical@mail.com", "Nuevo Nombre Bien", LocalDate.parse("1997-02-12"))
        );

        // Valid modification
        try {
            for (UserPOJO userChanges : modifications) {
                profileManager.modifyUserData(userChanges.getID(), userChanges);

                // must reach
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Check integrity
        for (UserPOJO userChanges : modifications) {
            UserPOJO unchanged = userDAO.getUser(userChanges.getID());

            if (userChanges.getUsername() != null) {
                if (!unchanged.getUsername().equals(userChanges.getUsername())) {
                    fail("Expected equal");
                }
            }

            if (userChanges.getHashedPassword() != null) {
                if (!unchanged.getHashedPassword().equals(userChanges.getHashedPassword())) {
                    fail("Expected equal");
                }
            }

            if (userChanges.getEmail() != null) {
                if (!unchanged.getEmail().equals(userChanges.getEmail())) {
                    fail("Expected equal");
                }
            }

            if (userChanges.getName() != null) {
                if (!unchanged.getName().equals(userChanges.getName())) {
                    fail("Expected equal");
                }
            }

            if (userChanges.getBirthday() != null) {
                if (!unchanged.getBirthday().equals(userChanges.getBirthday())) {
                    fail("Expected equal");
                }
            }
        }
    }

    @Test
    public void validUsernameTest() {
        List<UserPOJO> modifications = Arrays.asList(
            new UserPOJO("user01", "petazetas", null, null, null, null),
            new UserPOJO("user02", "under_score", null, null, null, null),
            new UserPOJO("user03", "hastatreintaydoscaracteres", null, null, null, null)
        );

        // Valid modification
        try {
            for (UserPOJO userChanges : modifications) {
                profileManager.modifyUserData(userChanges.getID(), userChanges);

                // must reach
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Check
        for (UserPOJO userChanges : modifications) {
            String userUsername = userDAO.getUser(userChanges.getID()).getUsername();
            String newUsername = userChanges.getUsername();

            if (!userUsername.equals(newUsername)) { // unreachable
                fail("Expected equal: " + userUsername + " <-> " + newUsername);
            }
        }
    }

    @Test
    public void validHashedPasswordTest() {
        // * Receives already hashed password.
        List<UserPOJO> modifications = Arrays.asList(
            new UserPOJO("user01", null, "jkk39fasd89hqlf0sdfuasdf0", null, null, null),
            new UserPOJO("user02", null, "7u90ilfah90023rhfq340awAE", null, null, null),
            new UserPOJO("user03", null, "fjasdfhklñ23r890uf0823u40", null, null, null)
        );

        // Valid modification
        try {
            for (UserPOJO userChanges : modifications) {
                profileManager.modifyUserData(userChanges.getID(), userChanges);

                // must reach
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Check
        for (UserPOJO userChanges : modifications) {
            String userHashedPassword = userDAO.getUser(userChanges.getID()).getHashedPassword();
            String newHashedPassword = userChanges.getHashedPassword();

            if (!userHashedPassword.equals(newHashedPassword)) { // unreachable
                fail("Expected equal: " + userHashedPassword + " <-> " + newHashedPassword);
            }
        }
    }

    @Test
    public void validEmailTest() {
        List<UserPOJO> modifications = Arrays.asList(
            new UserPOJO("user01", null, null, "nuevo+email.jeje@arroba.algo", null, null),
            new UserPOJO("user02", null, null, "fiestas.celebs@ritmo.de.la.noche", null, null),
            new UserPOJO("user03", null, null, "email@valido.com", null, null)
        );

        // Valid modification
        try {
            for (UserPOJO userChanges : modifications) {
                profileManager.modifyUserData(userChanges.getID(), userChanges);

                // must reach
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Check
        for (UserPOJO userChanges : modifications) {
            String userEmail = userDAO.getUser(userChanges.getID()).getEmail();
            String newEmail = userChanges.getEmail();

            if (!userEmail.equals(newEmail)) { // unreachable
                fail("Expected equal: " + userEmail + " <-> " + newEmail);
            }
        }
    }

    @Test
    public void validNameTest() {
        List<UserPOJO> modifications = Arrays.asList(
            new UserPOJO("user01", null, null, null, "Fred Waterford Krüger", null),
            new UserPOJO("user02", null, null, null, "Woh Chang'Yo Kiu", null),
            new UserPOJO("user03", null, null, null, "Manuel Martín-Forero García", null)
        );

        // Valid modification
        try {
            for (UserPOJO userChanges : modifications) {
                profileManager.modifyUserData(userChanges.getID(), userChanges);

                // must reach
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Check
        for (UserPOJO userChanges : modifications) {
            String userName = userDAO.getUser(userChanges.getID()).getName();
            String newName = userChanges.getName();

            if (!userName.equals(newName)) { // unreachable
                fail("Expected equal: " + userName + " <-> " + newName);
            }
        }
    }

    @Test
    public void validBirthdayTest() {
        List<UserPOJO> modifications = Arrays.asList(
            new UserPOJO("user01", null, null, null, null, LocalDate.parse("1957-06-24")),
            new UserPOJO("user02", null, null, null, null, LocalDate.parse("1938-02-18")),
            new UserPOJO("user03", null, null, null, null, LocalDate.parse("2000-04-23"))
        );

        // Valid modification
        try {
            for (UserPOJO userChanges : modifications) {
                profileManager.modifyUserData(userChanges.getID(), userChanges);

                // must reach
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Check
        for (UserPOJO userChanges : modifications) {
            LocalDate userBirthday = userDAO.getUser(userChanges.getID()).getBirthday();
            LocalDate newBirthday = userChanges.getBirthday();

            if (!userBirthday.equals(newBirthday)) { // unreachable
                fail("Expected equal: " + userBirthday + " <-> " + newBirthday);
            }
        }
    }

    @Test
    public void invalidUserTest() {
        List<UserPOJO> modifications = Arrays.asList(
            new UserPOJO("user_85", "aresmarte", null, null, null, null),
            new UserPOJO("user00", null, "fdajkñf432r8foaf9", null, null, null),
            new UserPOJO("id_USER789", null, null, "emailbien@gmail.com", null, null),
            new UserPOJO("cimbrod34", null, null, null, "Nombre de Alguien", null),
            new UserPOJO("sombrr1", null, null, null, null, LocalDate.parse("1957-06-24"))
        );
        
        // Valid modification
        try {
            for (UserPOJO userChanges : modifications) {
                profileManager.modifyUserData(userChanges.getID(), userChanges);

                // unreachable
                fail("Expected error in the modification.");
            }
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (NoSuchElementException e) {
            // must reach
        }
    }

    @Test
    public void invalidDataTest() {
        List<UserPOJO> modifications = Arrays.asList(
            // USERNAME -> Just alphanumeric and underscore (4 - 32)
            new UserPOJO("user01", "pet!az-etas", null, null, null, null),
            new UserPOJO("user01", "hastatreintaydoscaracteresymasalla", null, null, null, null),
            new UserPOJO("user01", "low", "^*bgui596$", "anad@uque@mail.com", "Ana Duque", LocalDate.parse("1998-12-07")),

            // HASHED PASSWORD -> No conditions, just a string.

            // EMAIL -> Email-like format   
            new UserPOJO("user02", null, null, "malporquenohay.arroba", null, null),
            new UserPOJO("user02", null, null, "demas@iadasarro@bas.com", null, null),
            new UserPOJO("user02", "anduque82", "^*bgui596$", "es$p%eci!?al3s@mal.es", "Ana Duque", LocalDate.parse("1998-12-07")),

            // NAME -> Name + 2 surnames (second one is optional)
            new UserPOJO("user03", null, null, null, "Nombremal", null),
            new UserPOJO("user03", null, null, null, "Nombre con muchos apellidos", null),
            new UserPOJO("user03", "anduque82", "^*bgui596$", "es$p%eci!?al3s@mal.es", "mal!&%todo", LocalDate.parse("1998-12-07")),

            // BIRTHDAY -> actual age over 18 yo
            new UserPOJO("user01", null, null, null, null, LocalDate.parse("2006-01-11")),
            new UserPOJO("user01", null, null, null, "Nombre con muchos apellidos", LocalDate.parse("2019-05-09")),
            new UserPOJO("user01", "anduque82", "^*bgui596$", "es$p%eci!?al3s@mal.es", "mal!&%todo", LocalDate.parse("2013-12-07"))
        );

        // Valid modification
        try {
            for (UserPOJO userChanges : modifications) {
                profileManager.modifyUserData(userChanges.getID(), userChanges);

                // unreachable
                fail("Expected error in the modification.");
            }
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // must reach
        }

        // Check integrity
        for (UserPOJO userChanges : modifications) {
            UserPOJO unchanged = userDAO.getUser(userChanges.getID());

            if (unchanged.getUsername().equals(userChanges.getUsername())
                    || unchanged.getHashedPassword().equals(userChanges.getHashedPassword())
                    || unchanged.getEmail().equals(userChanges.getEmail())
                    || unchanged.getName().equals(userChanges.getName())
                    || unchanged.getBirthday().equals(userChanges.getBirthday())) {
                        fail("User integrity fail.");
                    }
        }
    }


}