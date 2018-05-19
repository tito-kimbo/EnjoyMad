package es.ucm.fdi.business.profilemanagement;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.business.profilemanagement.tools.ClubModifierHelper;
import es.ucm.fdi.business.profilemanagement.tools.UserModifierHelper;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.Location;
import es.ucm.fdi.integration.data.ReviewPOJO;
import es.ucm.fdi.integration.data.UserPOJO;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mindrot.jbcrypt.BCrypt;

public class ProfileManagerSAImpTest {

    ProfileManagerSA profileManager;

    public ProfileManagerSAImpTest() {
    }

    @Before
    public void setUp() {
        profileManager
                = new ProfileManagerSAImp(new ClubDAOImp(), new UserDAOImp());
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addNewClub method, of class ProfileManagerSAImp. The test of the
     * parse components are tested in their respective test classes. The focus
     * of this test is to check the performs of managing a ProfileManagerSAImp.
     */
    @Test
    public void testAddNewClub() {
        
        // We add a valid new club, no exception should be thrown
        try {
            profileManager.addNewClub(
                new ClubPOJO(
                    "c2", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The club was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The club was correctly set up but exception was thrown: "
                    + e.getMessage());
        }

        // We add a non valid club, an exception should be thrown
        try {
            profileManager.addNewClub(
                new ClubPOJO(
                    " ", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
            fail("The club was not correctly set up, you should not get here");
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // You should get here, expected exception caused by incorrect id
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The club was correctly set up but not the expected exception"
                    + " was thrown: " + e.getMessage());
        }
    }

    /**
     * Test of addNewUser method, of class ProfileManagerSAImp.
     */
    @Test
    public void testAddNewUser() {
        // We add a valid new user, no exception should be thrown
        try {
            profileManager.addNewUser(
                new UserPOJO(
                    "u1", 
                    "abc98", 
                    "Password1",
                    "hola@gmail.com", 
                    "Alba Rosa", 
                    LocalDate.now()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        }

        // We add a non valid user, an exception should be thrown
        try {
            profileManager.addNewUser(
                new UserPOJO(
                    "#", 
                    "dhg11", 
                    "Password1",
                    "adios@gmail.com", 
                    "Dani Ramiro", 
                    LocalDate.now()
                )
            );
            fail("The user was not correctly set up, you should not get here");
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // You should get here, expected exception caused by incorrect id
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The user was correctly set up but not the expected exception"
                    + " was thrown: " + e.getMessage());
        }
    }

    /**
     * Test of modifyClubCommercialName method, of class ProfileManagerSAImp.
     */
    @Test
    public void testModifyClubCommercialName() {
        // We change the club commercial name, no exception should be thrown
        try {
            profileManager.addNewClub(
                new ClubPOJO(
                    "c2", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
        } catch (IllegalArgumentException ex) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + ex.getMessage());
        }

        try {
            profileManager.modifyClubData(
                "c2", 
                ClubModifierHelper.COMMERCIAL_NAME, 
                "Teatro Barcelo"
            );
        } catch (NoSuchElementException ex) {
            // Should not enter here
            fail("The club commercial name was correctly changed but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The club commercial name was correctly changed but exception was thrown: "
                    + ex.getMessage());
        }
        assertTrue(
            "The commercial name of the club has changed",
            "Teatro Barcelo".equals(profileManager.getCommercialName("c2"))
        );
    }

    /**
     * Test of modifyClubAddress method, of class ProfileManagerSAImp.
     */
    @Test
    public void testModifyClubAddress() {
        try {
            // We change the club club adress, no exception should be thrown
            profileManager.addNewClub(
                new ClubPOJO(
                    "c2", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
        } catch (IllegalArgumentException ex) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + ex.getMessage());
        }

        try {
            profileManager.modifyClubData(
                "c2",
                ClubModifierHelper.ADDRESS, 
                "C/Gran Via 24"
            );
        } catch (NoSuchElementException ex) {
            // Should not enter here
            fail("The club adress was correctly changed but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The club adress was correctly changed but exception was thrown: "
                    + ex.getMessage());
        }
        assertTrue(
            "The adress of the club has changed",
            "C/Gran Via 24".equals(profileManager.getClubAdress("c2"))
        );
    }

    /**
     * Test of modifyClubPrice method, of class ProfileManagerSAImp.
     */
    @Test
    public void testModifyClubPrice() {
        try {
            // We change the club club price, no exception should be thrown
           profileManager.addNewClub(
               new ClubPOJO(
                    "c2", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
        } catch (IllegalArgumentException ex) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + ex.getMessage());
        }

        try {
            profileManager.modifyClubData(
                "c2",
                ClubModifierHelper.PRICE, 
                25.0F
            );
        } catch (NoSuchElementException ex) {
            // Should not enter here
            fail("The club price was correctly changed but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The club price was correctly changed but exception was thrown: "
                    + ex.getMessage());
        }
        assertTrue(
            "The price of the club has changed",
            25.0F == profileManager.getClubPrice("c2")
        );

    }

    /**
     * Test of modifyClublocation method, of class ProfileManagerSAImp.
     */
    @Test
    public void testModifyClublocation() {
        try {
            // We change the club club location, no exception should be thrown
            profileManager.addNewClub(
                new ClubPOJO(
                    "c2", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
        } catch (IllegalArgumentException ex) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + ex.getMessage());
        }

        Location newLoc = new Location(35.4, -5.61);
        try {
            profileManager.modifyClubData(
                "c2",
                ClubModifierHelper.LOCATION, 
                new Location(35.4, -5.61)
            );
        } catch (IllegalArgumentException ex) {
            // Should not enter here
            fail("The club location was correctly changed but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The club location was correctly changed but exception was thrown: "
                    + ex.getMessage());
        }
        assertTrue(
            "The location of the club has changed",
            newLoc.equals(profileManager.getClubLocation("c2")));
    }

    /**
     * Test of modifyClubRating method, of class ProfileManagerSAImp.
     */
    @Test
    public void testModifyClubRating() {
        try {
            // We change the club club rating, no exception should be thrown
            profileManager.addNewClub(
                new ClubPOJO(
                    "c2", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
        } catch (IllegalArgumentException ex) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + ex.getMessage());
        }

        try {
            profileManager.modifyClubData(
                "c2", 
                ClubModifierHelper.RATING, 
                8.3F
            );
        } catch (NoSuchElementException ex) {
            // Should not enter here
            fail("The club rating was correctly changed but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The club rating was correctly changed but exception was thrown: "
                    + ex.getMessage());
        }

        assertTrue(
            "The rating of the club has changed",
            8.3F == profileManager.getClubRating("c2")
        );
    }

    /**
     * Test of addClubTag method, of class ProfileManagerSAImp.
     */
    @Test
    public void testAddClubTag() {
        // We add a valid new club, no exception should be thrown
        try {
            profileManager.addNewClub(
                new ClubPOJO(
                    "c2", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The club was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The club was correctly set up but exception was thrown: "
                    + e.getMessage());
        }
        
        // We add a valid new club tag, no exception should be thrown
        try {
            profileManager.modifyClubData(
                "c2",
                ClubModifierHelper.ADD_TAG, 
                new TagPOJO("barato")
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The club tag was correctly added but exception was thrown: "
                    + e.getMessage());
        } catch (NoSuchElementException e) {
            // Should not enter here
            fail("The club tag was correctly added but exception was thrown: "
                    + e.getMessage());
        }
    }

    /**
     * Test of removeClubTag method, of class ProfileManagerSAImp.
     */
    @Test
    public void testRemoveClubTag() {

        // XXX ¿Por qué "was correctly added"? Si salta excepcion no se añade nada.
        
        // We add a valid new club, no exception should be thrown.
        try {
            profileManager.addNewClub(
                new ClubPOJO(
                    "c2", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
            // Not catching multiple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The club was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The club was correctly set up but exception was thrown: "
                    + e.getMessage());
        }

        // We add a valid new club tag, no exception should be thrown
        try {
            profileManager.modifyClubData(
                "c2",
                ClubModifierHelper.ADD_TAG, 
                new TagPOJO("barato")
            );
            // Not catching multiple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The club tag was correctly added but exception was thrown: "
                    + e.getMessage());
        } catch (NoSuchElementException e) {
            // Should not enter here
            fail("The club tag was correctly added but exception was thrown: "
                    + e.getMessage());
        }

        // We remove a the added tag, no exception should be thrown
        try {
            profileManager.modifyClubData(
                "c2", 
                ClubModifierHelper.REMOVE_TAG, 
                new TagPOJO("barato")
            );
        } catch (NoSuchElementException ex) {
            // Should not enter here
            fail("The club tag was correctly removed but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The club tag was correctly removed but exception was thrown: "
                    + ex.getMessage());
        }

        assertFalse(
            "The tag was previously removed",
            profileManager
                .getClubTags("c2")
                .contains( new TagPOJO("barato") )
        );
    }

    /**
     * Test of clearClubTags method, of class ProfileManagerSAImp.
     */
    @Test
    public void testClearClubTags() {
        
        // We add a valid new club, no exception should be thrown
        try {
            profileManager.addNewClub(
                new ClubPOJO(
                    "c2", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The club was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The club was correctly set up but exception was thrown: "
                    + e.getMessage());
        }
        
        // We add a valid new club tag, no exception should be thrown
        try {
            profileManager.modifyClubData(
                "c2", 
                ClubModifierHelper.ADD_TAG, 
                new TagPOJO("barato")
            );
            profileManager.modifyClubData(
                "c2", 
                ClubModifierHelper.ADD_TAG, 
                new TagPOJO("bueno")
            );
            profileManager.modifyClubData(
                "c2", 
                ClubModifierHelper.ADD_TAG, 
                new TagPOJO("famoso")
            );
            profileManager.modifyClubData(
                "c2", 
                ClubModifierHelper.ADD_TAG, 
                new TagPOJO("lleno")
            );


            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The club tag was correctly added but exception was thrown: "
                    + e.getMessage());
        } catch (NoSuchElementException e) {
            // Should not enter here
            fail("The club tag was correctly added but exception was thrown: "
                    + e.getMessage());
        }
        
        try {
            profileManager.modifyClubData(
                "c2",
                ClubModifierHelper.CLEAR_TAGS, 
                null
            );
        } catch (IllegalArgumentException ex) {
           // Should not enter here
            fail("The club tags were corregtly cleared but an unexpected "
                    + "exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The club tags were corregtly cleared but an unexpected "
                    + "exception was thrown: "
                    + ex.getMessage());
        }
  
        assertTrue(
            "The tag was previously removed",
            profileManager.getClubTags("c2").isEmpty()
        );
    }

    /**
     * Test of modifyUserNickname method, of class ProfileManagerSAImp.
     */
    @Test
    public void testModifyUserNickname() {
        
        // We add a valid new user, no exception should be thrown
        try {
            profileManager.addNewUser(
                new UserPOJO(
                    "u1", 
                    "abc98", 
                    "Password1",
                    "hola@gmail.com", 
                    "Alba Pecas", 
                    LocalDate.now()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        }

        try {
            profileManager.modifyUserData(
                "u1", 
                UserModifierHelper.NICKNAME,
                "morenito19"
            );
        } catch (NoSuchElementException ex) {
            // Should not enter here
            fail("The user nickname was correctly changed but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The user nickname was correctly changed but exception was thrown: "
                    + ex.getMessage());
        }

        assertTrue(
            "The nickname of the user has changed",
            "morenito19".equals(profileManager.getUserNickname("u1"))
        );
    }

    /**
     * Test of modifyUserPassWord method, of class ProfileManagerSAImp.
     */
    @Test
    public void testModifyUserPassWord() {
        // We add a valid new user, no exception should be thrown
        try {
            profileManager.addNewUser(
                new UserPOJO(
                    "u1", 
                    "abc98", 
                    "Password1",
                    "hola@gmail.com", 
                    "Alba Bosque", 
                    LocalDate.now()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        }

        try {
            profileManager.modifyUserData(
                "u1", 
                UserModifierHelper.PASSWORD,
                "fghjkllkjJKfd9"
            );
        } catch (NoSuchElementException ex) {
            // Should not enter here
            fail("The user password was correctly changed but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The user password was correctly changed but exception was thrown: "
                    + ex.getMessage());
        }

        assertTrue(
            "The password of the user has changed",
            BCrypt.checkpw(
                "fghjkllkjJKfd9", 
                profileManager.getUserPassword("u1")
            )
        );
    }

    /**
     * Test of modifyUserEmail method, of class ProfileManagerSAImp.
     */
    @Test
    public void testModifyUserEmail() {
        
        // We add a valid new user, no exception should be thrown
        try {
            profileManager.addNewUser(
                new UserPOJO(
                    "u1", 
                    "abc98", 
                    "Password1",
                    "hola@gmail.com", 
                    "Alba Boreal", 
                    LocalDate.now()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        }
        try {
            profileManager.modifyUserData(
                "u1", 
                UserModifierHelper.EMAIL,
                "qtal@gmail.com"
            );
        } catch (NoSuchElementException ex) {
            // Should not enter here
            fail("The user email was correctly changed but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The user email was correctly changed but exception was thrown: "
                    + ex.getMessage());
        }

        assertTrue(
            "The email of the user has changed",
            "qtal@gmail.com".equals(profileManager.getUserEmail("u1"))
        );
    }

    /**
     * Test of modifyUserName method, of class ProfileManagerSAImp.
     */
    @Test
    public void testModifyUserName() {
        // We add a valid new user, no exception should be thrown
        try {
            profileManager.addNewUser(
                new UserPOJO(
                    "u1", 
                    "abc98", 
                    "Password1",
                    "hola@gmail.com", 
                    "Alba Haca", 
                    LocalDate.now()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        }

        try {
            profileManager.modifyUserData(
                "u1", 
                UserModifierHelper.NAME,
                "John Bolton"
            );
        } catch (NoSuchElementException ex) {
            // Should not enter here
            fail("The user name was correctly changed but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The user name was correctly changed but exception was thrown: "
                    + ex.getMessage());
        }

        assertTrue(
            "The name of the user has changed",
            "John Bolton".equals(profileManager.getUserName("u1"))
        );
    }

    /**
     * Test of modifyUserBirthday method, of class ProfileManagerSAImp.
     */
    @Test
    public void testModifyUserBirthday() {
        // We add a valid new user, no exception should be thrown
        try {
            profileManager.addNewUser(
                new UserPOJO(
                    "u1", 
                    "abc98", 
                    "Password1",
                    "hola@gmail.com", 
                    "Alba Piso", 
                    LocalDate.now()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        }
        
        try {
            profileManager.modifyUserData(
                "u1", 
                UserModifierHelper.BIRTHDAY,
                LocalDate.parse("1998-04-02")
            );
        } catch (NoSuchElementException ex) {
            // Should not enter here
            fail("The user birthday was correctly changed but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The user birthday was correctly changed but exception was thrown: "
                    + ex.getMessage());
        }

        assertTrue(
            "The birthday of the user has changed",
            LocalDate.parse("1998-04-02").equals(profileManager.getUserBirthday("u1"))
        );
    }

    /**
     * Test of removeClub method, of class ProfileManagerSAImp.
     */
    @Test
    public void testRemoveClub() {
        try {
            // We change the club club location, no exception should be thrown
            profileManager.addNewClub(
                new ClubPOJO(
                    "c2", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
        } catch (IllegalArgumentException ex) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + ex.getMessage());
        } catch (DataFormatException ex) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + ex.getMessage());
        }

        try {
            profileManager.removeClub("c2");
        } catch (NoSuchElementException ex) {
            // Should not enter here
            fail("The club was correctly removed but exception was thrown: "
                    + ex.getMessage());
        }

        try {
            profileManager.getClubPrice("c2");
            fail("Getter method called over non-existing club");
        } catch (NoSuchElementException ex) {
            // Should enter here
        }
    }

    /**
     * Test of removeUser method, of class ProfileManagerSAImp.
     */
    @Test
    public void testRemoveUser() {
        // We add a valid new user, no exception should be thrown
        try {
            profileManager.addNewUser(
                new UserPOJO(
                    "u1", 
                    "abc98", 
                    "Password1",
                    "hola@gmail.com", 
                    "Alba Rosa", 
                    LocalDate.now()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        }
        
        try {
            profileManager.removeUser("u1");
        } catch (NoSuchElementException ex) {
            // Should not enter here
            fail("The usesr was correctly removed but exception was thrown: "
                    + ex.getMessage());
        }
        
        try {
            profileManager.getUserName("u1");
            fail("Getter method called over non-existing user");
        } catch (NoSuchElementException ex) {
            // Should enter here
        }
    }

    /**
     * Test of addReview method, of class ProfileManagerSAImp.
     */
    @Test
    public void testAddReview() {
        // We add a valid new user, no exception should be thrown
        try {
            profileManager.addNewUser(
                new UserPOJO(
                    "u1", 
                    "abc98", 
                    "Password1",
                    "hola@gmail.com", 
                    "Alba Rosa", 
                    LocalDate.now()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        }
        // We add a valid new club, no exception should be thrown
        try {
            profileManager.addNewClub(
                new ClubPOJO(
                    "c2", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The club was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The club was correctly set up but exception was thrown: "
                    + e.getMessage());
        }
        try {
            // We add a valid new review, no exception should be thrown
            profileManager.addReview(
                "c2", 
                new ReviewPOJO(
                    "Es un club con muy buen ambiente, buena musica y buenas copas.",
                    8
                ), 
                "u1"
            );
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The review was correctly added but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The review was correctly added but exception was thrown: "
                    + e.getMessage());
        }
        
        try {
            // We add a non valid new review, exception should be thrown
            profileManager.addReview(
                "c2", 
                new ReviewPOJO(
                    "#hfdj09$", 
                    8.9F
                ), 
                "u1"
            );
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The review was correctly added but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should enter here
        }
    }

    /**
     * Test of removeReview method, of class ProfileManagerSAImp.
     */
    @Test
    public void testRemoveReview() {
        // We add a valid new user, no exception should be thrown
        try {
            profileManager.addNewUser(
                new UserPOJO(
                    "u1", 
                    "abc98", 
                    "Password1",
                    "hola@gmail.com", 
                    "Alba Rosa", 
                    LocalDate.now()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The user was correctly set up but exception was thrown: "
                    + e.getMessage());
        }
        // We add a valid new club, no exception should be thrown
        try {
            profileManager.addNewClub(
                new ClubPOJO(
                    "c2", 
                    "Pacha", 
                    "C/ Barcelo 11", 
                    20, 
                    new HashSet<TagPOJO>()
                )
            );
            // Not catching multitple exception available (version 1.5)
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The club was correctly set up but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The club was correctly set up but exception was thrown: "
                    + e.getMessage());
        }
        
        try {
            // We add a valid new review, no exception should be thrown
            profileManager.addReview(
                "c2", 
                new ReviewPOJO(
                    "Es un club con muy buen ambiente, buena musica y buenas copas.",
                    8.0
                ), 
                "u1"
            );
        } catch (DataFormatException e) {
            // Should not enter here
            fail("The review was correctly added but exception was thrown: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The review was correctly added but exception was thrown: "
                    + e.getMessage());
        }
        
        try {
            // We remove a review, no exception should be thrown
            profileManager.removeReview("c2", "u1");
        } catch (IllegalArgumentException e) {
            // Should not enter here
            fail("The review was correctly added but exception was thrown: "
                    + e.getMessage());
        }
        
        // Test if the user has the review in their inner review list
        assertTrue(
            "The review of the user has been removed",
            profileManager.getClubsReviewed("u1").isEmpty());

        // Test if the club has the review in its inner review list
        assertTrue(
            "The review of the club has been removed",
            profileManager.getReviewsFromClub("c2").isEmpty()
        );
    }
}
