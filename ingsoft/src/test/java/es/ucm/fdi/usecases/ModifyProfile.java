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
 *      requests of Club and User modification.
 * 		    * FrontController handles request internally on separate thread.
 * 2.   While there is no answer, polling (calls poll()) at fixed time intervals.
 * 3.   Valid answer is given at some point.
 */
public class ModifyProfile {
    public static final int POLLING_DELAY = 100;
    private static String customClubID = "idModify Club240520189229";
    private static String customUserID = "idModify User245920780023";
    private static String hashedPassword = BCrypt.hashpw("pass1234", BCrypt.gensalt());
    
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

    public static ClubPOJO clubChanges =
            new ClubPOJO(
                    "club08", "El fresquito", null, 7.50f,
                    new HashSet<TagPOJO>(Arrays.asList(tags[0], tags[2], tags[4]))
                );

    public static UserPOJO existingUser = 
            new UserPOJO(
                    "user02", "alfonrom2", "dfsd653;^34;dqwe", 
                    "alf.rom15@mail.es", "Alfonso Romo", 
                    LocalDate.parse("1997-05-14")
            );

    public static UserPOJO userChanges = 
            new UserPOJO(
                    "user02", "alfonbest3", hashedPassword, 
                    "alfonsoromo@gmail.com", "Alfonso Romo Hernán", 
                    null
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

        // Update cdsa
        ProductionConfig.getFrontController().getCustomDataSA().updateValues();

        // Initialize FrontController
        fc = ProductionConfig.getFrontController();
    }   

    // Uses customID
    private RequestPOJO buildOneClubRP(ClubPOJO club) {
        List<Object> l = new ArrayList<Object>();
        l.add(club);
        return new RequestPOJO(customClubID, new RequestPOJO("", RequestType.MODIFY_CLUB, l));
    }

    // Uses customID
    private RequestPOJO buildOneUserRP(UserPOJO user) {
        List<Object> l = new ArrayList<Object>();
        l.add(user);
        return new RequestPOJO(customUserID, new RequestPOJO("", RequestType.MODIFY_USER, l));
    }

    @Test
    public void modifyUserProfileTest() {
        // Build Request
        AnswerPOJO ans;        
        RequestPOJO rp = buildOneUserRP(userChanges);
        
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
        UserPOJO modifiedUser = fc.getProfileManagerSA().getUser(userChanges.getID());
        assertNotNull(
            "ERROR -> User not found in DAO.", 
            modifiedUser
        );

        if (userChanges.getUsername() != null) {
            assertEquals(
                "ERROR -> Unchanged username.",
                modifiedUser.getUsername(), userChanges.getUsername()
            );
        }
        if (userChanges.getHashedPassword() != null) {
            assertEquals(
                "ERROR -> Unchanged password.",
                modifiedUser.getHashedPassword(), userChanges.getHashedPassword()
            );
        }
        if (userChanges.getEmail() != null) {
            assertEquals(
                "ERROR -> Unchanged email.",
                modifiedUser.getEmail(), userChanges.getEmail()
            );
        }
        if (userChanges.getName() != null) {
            assertEquals(
                "ERROR -> Unchanged name.",
                modifiedUser.getName(), userChanges.getName()
            );
        }
        if (userChanges.getBirthday() != null) {
            assertEquals(
                "ERROR -> Unchanged birthday.",
                modifiedUser.getBirthday(), userChanges.getBirthday()
            );
        }
        ProductionConfig.getFrontController()
        .getProfileManagerSA().removeUser(existingUser.getID());
    }

    @Test
    public void modifyClubProfileTest() {
        // Build Request
        AnswerPOJO ans = null;        
        RequestPOJO rp = buildOneClubRP(clubChanges);
        
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
        ClubPOJO modifiedClub = fc.getProfileManagerSA().getClub(clubChanges.getID());
        assertNotNull(
            "ERROR -> Club not found in DAO.", 
            modifiedClub
        );

        if (clubChanges.getCommercialName() != null) {
            assertEquals(
                "ERROR -> Unchanged commercial name.",
                modifiedClub.getCommercialName(), clubChanges.getCommercialName()
            );
        }
        if (clubChanges.getAddress() != null) {
            assertEquals(
                "ERROR -> Unchanged address.",
                modifiedClub.getAddress(), clubChanges.getAddress()
            );
        }
        if (clubChanges.getPrice() != ClubPOJO.PRICE_NULL) {
            assertEquals(
                "ERROR -> Unchanged price.",
                modifiedClub.getPrice(), clubChanges.getPrice(),
                0.010f
            );
        }
        if (clubChanges.getTags() != null) {
            assertEquals(
                "ERROR -> Unchanged tags.",
                modifiedClub.getTags(), clubChanges.getTags()
            );
        }
        ProductionConfig.getFrontController()
        .getProfileManagerSA().removeClub(existingClub.getID());
    }
}