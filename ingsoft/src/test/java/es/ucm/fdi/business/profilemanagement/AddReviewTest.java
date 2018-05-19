package es.ucm.fdi.business.profilemanagement;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import es.ucm.fdi.integration.data.ReviewPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

public class NewReviewTest {
    
    ProfileManagerSA profileManager;
    ClubDAO clubDAO;
    UserDAO userDAO;

    public class ReviewInfo {
        public String userID, clubID;
        public ReviewPOJO review;

        public ReviewInfo(String u, String c, ReviewPOJO r) {
            userID = u;
            clubID = c;
            review = r;
        }
    }

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


    @Test
    public void validReviewsTest() {
        // Review requests
        List<ReviewInfo> reviewRequests = Arrays.asList(
            new ReviewInfo(
                "user01", "club02",
                new ReviewPOJO("La gente que trabaja allí nos atendió bastante mal, llegando incluso a insultarnos en...", 2.5)
            ),
            new ReviewInfo(
                "user01", "club03",
                new ReviewPOJO("Gran discoteca con buena música y distintas salas donde se reproducen distintos estilos.", 4.5)             
            ),
            new ReviewInfo(
                "user03", "club01",
                new ReviewPOJO("El sitio es enorme y de calidad con un excelente sonido, y efectos de luces...", 4.0)
            ),
            new ReviewInfo(
                "user03", "club07",
                new ReviewPOJO("Impresionante, principalmente la planta de electrónica. Las demás más paradas. Precios...", 4.0)
            ),
            new ReviewInfo(
                "user04", "club04",
                new ReviewPOJO("Es una discoteca pésima. Estuvimos en una despedida de soltera, habíamos cogido...", 1.0)
            ),
            new ReviewInfo(
                "user05", "club03",
                new ReviewPOJO("La peor experiencia que he tenido jamás. A parte de que está lleno de niñatos, colas...", 0.0)
            ),
            new ReviewInfo(
                "user05", "club04",
                new ReviewPOJO("Amor - odio con esta discoteca. Es mi favorita en Madrid, pero hay cosas que no se pueden...", 3.5)
            ),
            new ReviewInfo(
                "user06", "club05",
                new ReviewPOJO("Está bien. Diferentes ambientes. Pero mucho turista y cara. 25€ de entrada con...", 3.5)
            ),
            new ReviewInfo(
                "user06", "club08",
                new ReviewPOJO("Me gustaba mucho ir a esta discoteca hace años estaba bien pero hoy en día... ", 3.0)
            ),
            new ReviewInfo(
                "user07", "club02",
                new ReviewPOJO("¡Es una pasada! Una discoteca de 7 plantas y con varias salas independientes, como...", 5.0)
            )
        );

        // Review adding
        try {
            for (ReviewInfo req : reviewRequests) {
                profileManager.addReview(req.clubID, req.review, req.userID);
            }
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (DataFormatException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Users/Clubs knowledge & integrity of review
        for (ReviewInfo req : reviewRequests) {
            Collection<String> reviewedClubs = userDAO.getUser(req.userID).getReviewedClubs();
            Collection<String> reviewers = clubDAO.getClub(req.clubID).getReviewers();
            Map<String, ReviewPOJO> reviews = clubDAO.getClub(req.clubID).getReviews();

            if (! reviewedClubs.contains(req.clubID)) { // unreachable
                fail("User (" + req.userID + ") does not know reviewed club (" + req.clubID + ").");
            }

            if (! reviewers.contains(req.userID)) { // unreachable
                fail("Club (" + req.clubID + ") does not know reviewer (" + req.userID + ").");
            }

            if (! ( req.review ).equals( reviews.get(req.userID) ) ) { // unreachable
                fail("Review of user (" + req.userID + ") to club (" + req.clubID + ") did not match.");
            }
        }
    }

    @Test
    public void invalidReviewsTest() {
        // Invalid requests 1
        List<ReviewInfo> invalidNoSuchUser = Arrays.asList(
            new ReviewInfo(
                "user00", "club02",
                new ReviewPOJO("La gente que trabaja allí nos atendió bastante mal, llegando incluso a insultarnos en...", 2.5)
            ),
            new ReviewInfo(
                "user13", "club03",
                new ReviewPOJO("Gran discoteca con buena música y distintas salas donde se reproducen distintos estilos.", 4.5)             
            ),
            new ReviewInfo(
                "user_9", "club01",
                new ReviewPOJO("El sitio es enorme y de calidad con un excelente sonido, y efectos de luces...", 4.0)
            ),
            new ReviewInfo(
                "id_USER879", "club07",
                new ReviewPOJO("Impresionante, principalmente la planta de electrónica. Las demás más paradas. Precios...", 4.0)
            )
        );

        // Invalid request 2
        List<ReviewInfo> invalidNoSuchClub = Arrays.asList(
            new ReviewInfo(
                "user04", "club00",
                new ReviewPOJO("Es una discoteca pésima. Estuvimos en una despedida de soltera, habíamos cogido...", 1.0)
            ),
            new ReviewInfo(
                "user05", "club13",
                new ReviewPOJO("La peor experiencia que he tenido jamás. A parte de que está lleno de niñatos, colas...", 0.0)
            ),
            new ReviewInfo(
                "user05", "club_9",
                new ReviewPOJO("Amor - odio con esta discoteca. Es mi favorita en Madrid, pero hay cosas que no se pueden...", 3.5)
            ),
            new ReviewInfo(
                "user06", "id_CLUB475",
                new ReviewPOJO("Está bien. Diferentes ambientes. Pero mucho turista y cara. 25€ de entrada con...", 3.5)
            )
        );

        // Invalid requests 3
        List<ReviewInfo> invalidData = Arrays.asList(
            new ReviewInfo(
                "user06", "club08",
                new ReviewPOJO("Me gustaba mucho ir a esta discoteca hace años estaba bien pero hoy en día... ", -1.2)
            ),
            new ReviewInfo(
                "user07", "club02",
                new ReviewPOJO("¡Es una pasada! Una discoteca de 7 plantas y con varias salas independientes, como...", 5.3)
            )
        );

        // Invalid addings
        for (ReviewInfo req : invalidNoSuchUser) { 
            try {
                profileManager.addReview(req.clubID, req.review, req.userID);

                // unreachable
                fail("Expected error did not take place. User: " + req.userID + ". Club: " + req.clubID);
            } catch (DataFormatException e) { // unreachable
                fail("Not expected error: " + e.getMessage());
            } catch (NoSuchElementException e) {
                // must reach
            }
        }

        for (ReviewInfo req : invalidNoSuchClub) { 
            try {
                profileManager.addReview(req.clubID, req.review, req.userID);

                // unreachable
                fail("Expected error did not take place. User: " + req.userID + ". Club: " + req.clubID);
            } catch (DataFormatException e) { // unreachable
                fail("Not expected error: " + e.getMessage());
            } catch (NoSuchElementException e) {
                // must reach
            }
        }

        for (ReviewInfo req : invalidData) {
            try {
                profileManager.addReview(req.clubID, req.review, req.userID);

                // unreachable
                fail("Expected error did not take place. User: " + req.userID + ". Club: " + req.clubID);
            } catch (NoSuchElementException e) { // unreachable
                fail("Not expected error: " + e.getMessage());
            } catch (DataFormatException e) {
                // must reach
            }
        }
    }

    @Test
    public void validReplacementTest() {
        // Initially existing reviews
        validReviewsTest();

        // Valid replacements
        List<ReviewInfo> validReplace = Arrays.asList(
            new ReviewInfo(
                "user01", "club02",
                new ReviewPOJO("Pasado un tiempo, en una segunda visita, parece que la actitud hacia los clientes ha cambiado...", 4.0)
            ),
            new ReviewInfo(
                "user03", "club07",
                new ReviewPOJO("Muy decepcionante. Han movido la sala de electrónica a un cuchitril para sustituirla por una de reguetton...", 2.5)
            )
        );

        // Valid review replacement
        try {
            for (ReviewInfo req : validReplace) {
                profileManager.addReview(req.clubID, req.review, req.userID);
            }
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (DataFormatException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Users/Clubs knowledge & integrity of review
        for (ReviewInfo req : validReplace) {
            Collection<String> reviewedClubs = userDAO.getUser(req.userID).getReviewedClubs();
            Collection<String> reviewers = clubDAO.getClub(req.clubID).getReviewers();
            Map<String, ReviewPOJO> reviews = clubDAO.getClub(req.clubID).getReviews();

            if (! reviewedClubs.contains(req.clubID)) { // unreachable
                fail("User (" + req.userID + ") does not know reviewed club (" + req.clubID + ").");
            }

            if (! reviewers.contains(req.userID)) { // unreachable
                fail("Club (" + req.clubID + ") does not know reviewer (" + req.userID + ").");
            }

            if (! ( req.review ).equals( reviews.get(req.userID) ) ) { // unreachable
                fail("Review of user (" + req.userID + ") to club (" + req.clubID + ") did not match.");
            }
        }
    }

    @Test
    public void invalidReplacementTest() {
        // Initially existing reviews
        validReviewsTest();

        // Invalid replacement
        ReviewInfo previous = new ReviewInfo(
                "user06", "club08",
                new ReviewPOJO("Me gustaba mucho ir a esta discoteca hace años estaba bien pero hoy en día... ", 3.0)
        );        
        ReviewInfo invalid = new ReviewInfo(
                "user06", "club08",
                new ReviewPOJO("Me gustaba mucho ir a esta discoteca hace años estaba bien pero hoy en día... ", -1.2)
        );

        // Invalid review replacement
        try {
            profileManager.addReview(invalid.clubID, invalid.review, invalid.userID);

            // unreachable
            fail("Expected error did not take place. User: " + previous.userID + ". Club: " + previous.clubID);
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (DataFormatException e) {
            // must reach
        }

        // Check previous review integrity
        Collection<String> reviewedClubs = userDAO.getUser(previous.userID).getReviewedClubs();
        Collection<String> reviewers = clubDAO.getClub(previous.clubID).getReviewers();
        Map<String, ReviewPOJO> reviews = clubDAO.getClub(previous.clubID).getReviews();

        if (!reviewedClubs.contains(previous.clubID)) { // unreachable
            fail("User (" + previous.userID + ") does not know reviewed club (" + previous.clubID + ").");
        }

        if (!reviewers.contains(previous.userID)) { // unreachable
            fail("Club (" + previous.clubID + ") does not know reviewer (" + previous.userID + ").");
        }

        if (!(previous.review).equals(reviews.get(previous.userID))) { // unreachable
            fail("Review of user (" + previous.userID + ") to club (" + previous.clubID + ") did not match.");
        }
    }
}