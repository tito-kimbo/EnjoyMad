package es.ucm.fdi.usecases;

import static org.junit.Assert.assertNull;
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

import es.ucm.fdi.business.FrontController;
import es.ucm.fdi.business.ProductionConfig;
import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;
import es.ucm.fdi.business.profilemanagement.ProfileManagerSA;
import es.ucm.fdi.business.requesthandling.tools.RequestType;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.TagPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/*
 * The use case executes as follows
 * 
 * 1.   request() method from FrontController is called with respective
 *      requests of Club and User removal.
 * 		    * FrontController handles request internally on separate thread.
 * 2.   While there is no answer, polling (calls poll()) at fixed time intervals.
 * 3.   Valid answer is given at some point.
 */
public class RemoveProfile {
    public static final int POLLING_DELAY = 100;
    private static String customRemoveID = "idRemove 2477530189229";
    
    public static TagPOJO[] tags = {
            new TagPOJO("pop"), new TagPOJO("edm"), new TagPOJO("latino"),
            new TagPOJO("reggaeton"), new TagPOJO("indie"), new TagPOJO("after"),
            new TagPOJO("barato"), new TagPOJO("salsa"), new TagPOJO("chill")
        };

    public static ClubPOJO existingClub = 
            new ClubPOJO(
                    "club08", "Barceló", "C/ Barceló, 11", 14.00f,
                    new HashSet<TagPOJO>(Arrays.asList(tags[7], tags[8], tags[0]))
                );

    public static UserPOJO existingUser = 
            new UserPOJO(
                    "user02", "alfonrom2", "dfsd653;^34;dqwe", 
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
            ProductionConfig.getFrontController()
                .getTagManagerSA()
                .newTag(tag);
        }

        // Existing profiles
        ProductionConfig.getFrontController()
            .getProfileManagerSA().addNewClub(existingClub);
        ProductionConfig.getFrontController()
            .getProfileManagerSA().addNewUser(existingUser);

        // Initialize FrontController
        fc = ProductionConfig.getFrontController();
    }

    @After
    public void tearDown() {
        ProfileManagerSA pmsa = ProductionConfig.getFrontController().getProfileManagerSA();
        if(pmsa.hasClub(existingClub.getID())) {
            pmsa.removeClub(existingClub.getID());
        }
        if (pmsa.hasUser(existingUser.getID())) {
            pmsa.removeUser(existingUser.getID());
        }
    }  

    // Uses customID
    private RequestPOJO buildDeleteRP(String id) {
        List<Object> l = new ArrayList<Object>();
        l.add(id);
        return new RequestPOJO(customRemoveID, new RequestPOJO("", RequestType.DELETE_ACCOUNT, l));
    }

    @Test
    public void removeUserProfileTest() {
        // Build Request
        AnswerPOJO ans;        
        RequestPOJO rp = buildDeleteRP(existingUser.getID());
        
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
            "ERROR -> Valid operation unsucessful.", 
            (Boolean) ans.getAnswer().get(0)
        );

        // Check integrity of changes
        UserPOJO modifiedUser = fc.getProfileManagerSA().getUser(existingUser.getID());
        assertNull(
            "ERROR -> User found in DAO.", 
            modifiedUser
        );
    }

    @Test
    public void removeClubProfileTest() {
        // Build Request
        AnswerPOJO ans;        
        RequestPOJO rp = buildDeleteRP(existingClub.getID());
        
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
            "ERROR -> Valid operation unsucessful.", 
            (Boolean) ans.getAnswer().get(0)
        );

        // Check integrity of changes
        ClubPOJO modifiedClub = fc.getProfileManagerSA().getClub(existingClub.getID());
        assertNull(
            "ERROR -> Club found in DAO.", 
            modifiedClub
        );
    }
}