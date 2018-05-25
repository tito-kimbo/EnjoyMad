package es.ucm.fdi.business.profilemanagement;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.business.tagmanagement.TagManagerSA;
import es.ucm.fdi.business.tagmanagement.TagManagerSAImp;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.ReviewPOJO;
import es.ucm.fdi.integration.data.TagPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

public class RemoveReviewTest {

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

        TagManagerSA tagManager = new TagManagerSAImp(new ArrayList<TagPOJO>());

        profileManager = new ProfileManagerSAImp(clubDAO, userDAO, tagManager);

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
        }

        // Initially existing users
        List<UserPOJO> existingUsers = Arrays.asList(
            new UserPOJO("user01", "julesp786", "sdfa56,.4", "julesp@mail.com", "Julia Espín-García", LocalDate.parse("1998-03-27")),
            new UserPOJO("user02", "alfonrom2", "456asdf", "alf.rom@mail.com", "Alfonso Romo", LocalDate.parse("1997-05-14")),
            new UserPOJO("user03", "hugo_alsin", ".ñ5bh*y", "halsina@mail.com", "Hugo Alsina", LocalDate.parse("1999-02-05")),
            new UserPOJO("user04", "yolimed_23", "!*yi89", "yolmedina@mail.org", "Yolanda Medina", LocalDate.parse("1996-11-19")),
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
        }

        // Initially existing reviews.
        // Review removuests
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
                new ReviewPOJO("La peor experiencia que he tenido jamás. A parte de que está lleno de niñatos, colas...", 1.5)
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
            for (ReviewInfo remov : reviewRequests) {
                profileManager.addReview(remov.clubID, remov.review, remov.userID);
            }
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        } catch (IllegalArgumentException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Users/Clubs knowledge & integrity of review
        for (ReviewInfo remov : reviewRequests) {
            Collection<String> reviewedClubs = userDAO.getUser(remov.userID).getReviewedClubs();
            Collection<String> reviewers = clubDAO.getClub(remov.clubID).getReviewers();
            Map<String, ReviewPOJO> reviews = clubDAO.getClub(remov.clubID).getReviews();

            if (! reviewedClubs.contains(remov.clubID)) { // unreachable
                fail("User (" + remov.userID + ") does not know reviewed club (" + remov.clubID + ").");
            }

            if (! reviewers.contains(remov.userID)) { // unreachable
                fail("Club (" + remov.clubID + ") does not know reviewer (" + remov.userID + ").");
            }

            if (! ( remov.review ).equals( reviews.get(remov.userID) ) ) { // unreachable
                fail("Review of user (" + remov.userID + ") to club (" + remov.clubID + ") did not match.");
            }
        }
    }

    @Test
    public void validRemovalsTest() {
        List<ReviewInfo> remainReviews = Arrays.asList(
            new ReviewInfo(
                "user01", "club03",
                new ReviewPOJO("Gran discoteca con buena música y distintas salas donde se reproducen distintos estilos.", 4.5)             
            ),
            new ReviewInfo(
                "user04", "club04",
                new ReviewPOJO("Es una discoteca pésima. Estuvimos en una despedida de soltera, habíamos cogido...", 1.0)
            ),
            new ReviewInfo(
                "user05", "club03",
                new ReviewPOJO("La peor experiencia que he tenido jamás. A parte de que está lleno de niñatos, colas...", 1.5)
            )
        );

        List<ReviewInfo> removedReviews = Arrays.asList(
            new ReviewInfo("user01", "club02", null),
            new ReviewInfo("user03", "club01", null),
            new ReviewInfo("user03", "club07", null),
            new ReviewInfo("user05", "club04", null),
            new ReviewInfo("user06", "club05", null),
            new ReviewInfo("user06", "club08", null),
            new ReviewInfo("user07", "club02", null)
        );

        // Removal
        try {
            for (ReviewInfo remov : removedReviews) {
                profileManager.removeReview(remov.clubID, remov.userID);
            }
        } catch (NoSuchElementException e) { // unreachable
            fail("Not expected error: " + e.getMessage());
        }

        // Integrity of remain reviews
        for (ReviewInfo remov : remainReviews) {
            Collection<String> reviewedClubs = userDAO.getUser(remov.userID).getReviewedClubs();
            Collection<String> reviewers = clubDAO.getClub(remov.clubID).getReviewers();
            Map<String, ReviewPOJO> reviews = clubDAO.getClub(remov.clubID).getReviews();

            if (!reviewedClubs.contains(remov.clubID)) { // unreachable
                fail("User (" + remov.userID + ") does not know reviewed club (" + remov.clubID + ").");
            }

            if (!reviewers.contains(remov.userID)) { // unreachable
                fail("Club (" + remov.clubID + ") does not know reviewer (" + remov.userID + ").");
            }

            if (!(remov.review).equals(reviews.get(remov.userID))) { // unreachable
                fail("Review of user (" + remov.userID + ") to club (" + remov.clubID + ") did not match.");
            }
        }

        // Removal check
        for (ReviewInfo remov : removedReviews) {
            Collection<String> reviewedClubs = userDAO.getUser(remov.userID).getReviewedClubs();
            Collection<String> reviewers = clubDAO.getClub(remov.clubID).getReviewers();
            Map<String, ReviewPOJO> reviews = clubDAO.getClub(remov.clubID).getReviews();

            if (reviewedClubs.contains(remov.clubID)) { // unreachable
                fail("User (" + remov.userID + ") knows removed review from club (" + remov.clubID + ").");
            }

            if (reviewers.contains(remov.userID)) { // unreachable
                fail("Club (" + remov.clubID + ") knows removed reviewer (" + remov.userID + ").");
            }

            if (reviews.containsKey(remov.userID)) { // unreachable
                fail("Removed review of user (" + remov.userID + ") to club (" + remov.clubID + ") still exists.");
            }
        }
    }

    @Test
    public void invalidRemovalsTest() {
        // Invalid removals "club00", "club13", "club_9", "id_CLUB475" "user00", "user13", "user_9", "id_USER879"
        List<ReviewInfo> invalid = Arrays.asList(
            new ReviewInfo("user00", "club02", null),
            new ReviewInfo("user_13", "club01", null),
            new ReviewInfo("id_USER789", "club07", null),
            new ReviewInfo("user05", "club00", null),
            new ReviewInfo("user06", "club_15", null),
            new ReviewInfo("user06", "id_CLUB475", null)
        );

        // Invalid removal
        for (ReviewInfo remov : invalid) {
            try {
                profileManager.removeReview(remov.clubID, remov.userID);

                // unreachable
                fail("Expected error did not take place. User: " + remov.userID + ". Club: " + remov.clubID);
            } catch (NoSuchElementException e) {
                // must reach
            }
        }
    }
}
