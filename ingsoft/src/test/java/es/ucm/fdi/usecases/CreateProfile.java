package es.ucm.fdi.usecases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.ProductionConfig;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.requesthandling.tools.RequestType;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.TagPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/*
 * The use case executes as follows
 * 
 * 1.   request() method from FrontController is called with respective
 *      requests of Club and User registration.
 * 		    * FrontController handles request internally on separate thread.
 * 2.   While there is no answer, polling (calls poll()) at fixed time intervals.
 * 3.   Valid answer is given at some point.
 */
public class CreateProfile {
    public static final int POLLING_DELAY = 100;
    private static String customClubID = "idRegister Club240520189229";
    private static String customUserID = "idRegister User245920780023";
    private static String hashedPassword = BCrypt.hashpw("pass1234", BCrypt.gensalt());
    
    public static TagPOJO[] tags = {
            new TagPOJO("pop"), new TagPOJO("edm"), new TagPOJO("latino"),
            new TagPOJO("reggaeton"), new TagPOJO("indie"), new TagPOJO("after"),
            new TagPOJO("barato"), new TagPOJO("salsa"), new TagPOJO("chill")
        };

    public static ClubPOJO newClub = 
            new ClubPOJO(
                    "club08", "Barceló", "C/ Barceló, 11", 14.00f,
                    new HashSet<TagPOJO>(Arrays.asList(tags[7], tags[8], tags[0]))
                );

    public static UserPOJO newUser = 
            new UserPOJO(
                    "user02", "alfonrom2", hashedPassword, 
                    "alf.rom15@mail.es", "Alfonso Romo", 
                    LocalDate.parse("1997-05-14")
            );

    FrontController fc;


    @Before
    public void setUp() {
        // INITIALIZATION
        ProductionConfig.init(false);

        // Active tags.
        for (TagPOJO tag : tags) {
            ProductionConfig
                .getFrontController()
                .getTagManagerSA()
                .newTag(tag);
        }

        ProductionConfig.getFrontController().getCustomDataSA().updateValues();

        // Initialize FrontController
        fc = ProductionConfig.getFrontController();
    }

    @After
    public void tearDown() {
        // remove?
        // ProductionConfig.getFrontController().getProfileManagerSA().removeUser("id");
    }    

    // Uses customID
    private RequestPOJO buildOneClubRP(ClubPOJO club) {
        List<Object> l = new ArrayList<Object>();
        l.add(club);
        return new RequestPOJO(customClubID, new RequestPOJO("", RequestType.REGISTER_CLUB, l));
    }

    // Uses customID
    private RequestPOJO buildOneUserRP(UserPOJO user) {
        List<Object> l = new ArrayList<Object>();
        l.add(user);
        return new RequestPOJO(customUserID, new RequestPOJO("", RequestType.REGISTER_USER, l));
    }

    @Test
    public void createUserProfileTest() {
        // Build Request
        AnswerPOJO ans;        
        RequestPOJO rp = buildOneUserRP(newUser);
        
        // Do request to sv
        String id = fc.request(rp);
        ans = null;
		try {
			// Until answer is valid (not null)
			while (ans == null) {
				Thread.sleep(POLLING_DELAY);
				ans = fc.poll(id);
			}
		} catch (InterruptedException ie) {
			fail("Unknown interruption to main thread: " + ie.getMessage());
		}

		// Check operation
		assertTrue(
            "OK -> Valid operation unsucessful.", 
            (Boolean) ans.getAnswer().get(0)
        );

        // Check integrity
        UserPOJO registeredUser = fc.getProfileManagerSA().getUser(newClub.getID());
        assertNotNull(
            "OK -> User found in DAO.", 
            registeredUser
        );
        assertEquals(
            "OK -> User attributes conserved.", 
            registeredUser, newUser
        );
    }

    public void createClubProfileTest() {
        // Build Request
        AnswerPOJO ans;        
        RequestPOJO rp = buildOneClubRP(newClub);
        
        // Do request to sv
        String id = fc.request(rp);
        ans = null;
		try {
			// Until answer is valid (not null)
			while (ans == null) {
				Thread.sleep(POLLING_DELAY);
				ans = fc.poll(id);
			}
		} catch (InterruptedException ie) { // should not reach
			fail("Unknown interruption to main thread: " + ie.getMessage());
		}

		// Check operation
		assertTrue(
            "OK -> Valid operation unsucessful.", 
            (Boolean) ans.getAnswer().get(0)
        );

        // Check integrity
        ClubPOJO registeredClub = fc.getProfileManagerSA().getClub(newClub.getID());
        assertNotNull(
            "OK -> Club found in DAO.", 
            registeredClub
        );
        assertEquals(
            "OK -> Club attributes conserved.", 
            registeredClub, newClub
        );
    }
}