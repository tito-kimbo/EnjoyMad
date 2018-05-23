// package es.ucm.fdi.business.profilemanagement;

// import static org.junit.Assert.fail;

// import java.util.Arrays;
// import java.util.HashSet;
// import java.util.List;
// import java.util.NoSuchElementException;
// import java.util.Set;
// import java.util.zip.DataFormatException;

// import org.junit.Before;
// import org.junit.Test;

// import es.ucm.fdi.business.data.TagPOJO;
// import es.ucm.fdi.business.profilemanagement.tools.ClubModifierHelper;
// import es.ucm.fdi.integration.ClubDAO;
// import es.ucm.fdi.integration.ClubDAOImp;
// import es.ucm.fdi.integration.UserDAO;
// import es.ucm.fdi.integration.UserDAOImp;
// import es.ucm.fdi.integration.data.ClubPOJO;
// import es.ucm.fdi.integration.data.Location;

// /**
//  * Tests the correct modification of {@code ClubPOJO}s in the 
//  * app corresponding {@code ClubDAO}.
//  */
// public class ModifyClubTest {

//     ProfileManagerSA profileManager;
//     ClubDAO clubDAO;
//     UserDAO userDAO;

//     public class ModificationInfo {
//         String clubID;
//         ClubModifierHelper type;
//         Object data;

//         ModificationInfo(String id, ClubModifierHelper t, Object d) {
//             clubID = id;
//             type = t;
//             data = d;
//         }

//         @Override
//         public String toString() {
//             return  "Club: " + clubID + 
//                     " Modification: " + type.toString() +
//                     " Data: " + data.toString();
//         }
//     }

//     @Before
//     public void setUp() {
//         clubDAO = new ClubDAOImp();
//         userDAO = new UserDAOImp();

//         profileManager = new ProfileManagerSAImp(clubDAO, userDAO);

//         // Initially active tags
//         TagPOJO[] tags = {
//             new TagPOJO("dance"), new TagPOJO("edm"), new TagPOJO("latino"),
//             new TagPOJO("reguetton"), new TagPOJO("indie"), new TagPOJO("after"),
//             new TagPOJO("barato"), new TagPOJO("salsa"), new TagPOJO("chill")
//         };

//         // Initially existing clubs
//         List<ClubPOJO> existingClubs = Arrays.asList(
//             new ClubPOJO("club01", "Astra", "C/ Estrella, 55", 0f, 
//                 new HashSet<TagPOJO>(Arrays.asList(tags[0], tags[1], tags[2]))),
//             new ClubPOJO("club02", "Bowi", "C/ Galileo, 26", 12.50f,
//                 new HashSet<TagPOJO>(Arrays.asList(tags[3], tags[4], tags[5]))),
//             new ClubPOJO("club03", "Copérnico", "C/ Fernández, 67", 13.00f,
//                 new HashSet<TagPOJO>(Arrays.asList(tags[6], tags[7], tags[8])))
//         );

//         try {
//             for (ClubPOJO club : existingClubs) {
//                profileManager.addNewClub(club);
//             }
//         } catch (IllegalArgumentException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (DataFormatException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         }
//     }

//     @Test
//     public void validCommercialNameTest() {
//         List<ModificationInfo> modifications = Arrays.asList(
//             new ModificationInfo("club01", ClubModifierHelper.COMMERCIAL_NAME, "Aspera"),
//             new ModificationInfo("club02", ClubModifierHelper.COMMERCIAL_NAME, "Zetta"),
//             new ModificationInfo("club03", ClubModifierHelper.COMMERCIAL_NAME, "Stars")
//         );

//         // Valid modification
//         try {
//             for (ModificationInfo mod : modifications) {
//                 profileManager.modifyClubData(mod.clubID, mod.type, mod.data);

//                 // must reach
//             }
//         } catch (IllegalArgumentException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (DataFormatException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (NoSuchElementException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         }

//         // Check
//         for (ModificationInfo mod : modifications) {
//             String clubCommercialName = clubDAO.getClub(mod.clubID).getCommercialName();
//             String newCommercialName = (String) mod.data;

//             if (!clubCommercialName.equals(newCommercialName)) { // unreachable
//                 fail("Expected equal: " + clubCommercialName + " <-> " + newCommercialName);
//             }
//         }
//     }

//     @Test
//     public void validAddressTest() {
//         List<ModificationInfo> modifications = Arrays.asList(
//             new ModificationInfo("club01", ClubModifierHelper.ADDRESS, "C/ Jerónimo, 76"),
//             new ModificationInfo("club02", ClubModifierHelper.ADDRESS, "Avda. de las Rosas, 234"),
//             new ModificationInfo("club03", ClubModifierHelper.ADDRESS, "C/ Tajo, 2")
//         );

//         // Valid modification
//         try {
//             for (ModificationInfo mod : modifications) {
//                 profileManager.modifyClubData(mod.clubID, mod.type, mod.data);

//                 // must reach
//             }
//         } catch (IllegalArgumentException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (DataFormatException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (NoSuchElementException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         }

//         // Check
//         for (ModificationInfo mod : modifications) {
//             String clubAddress = clubDAO.getClub(mod.clubID).getAddress();
//             String newAddress = (String) mod.data;

//             if (!clubAddress.equals(newAddress)) { // unreachable
//                 fail("Expected equal: " + clubAddress + " <-> " + newAddress);
//             }
//         }
//     }

//     @Test
//     public void validPriceTest() {
//         List<ModificationInfo> modifications = Arrays.asList(
//             new ModificationInfo("club01", ClubModifierHelper.PRICE, 0.0f),
//             new ModificationInfo("club02", ClubModifierHelper.PRICE, 18.50f),
//             new ModificationInfo("club03", ClubModifierHelper.PRICE, 11.00f)
//         );

//         // Valid modification
//         try {
//             for (ModificationInfo mod : modifications) {
//                 profileManager.modifyClubData(mod.clubID, mod.type, mod.data);

//                 // must reach
//             }
//         } catch (IllegalArgumentException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (DataFormatException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (NoSuchElementException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         }

//         // Check
//         for (ModificationInfo mod : modifications) {
//             Float clubPrice = clubDAO.getClub(mod.clubID).getPrice();
//             Float newPrice = (Float) mod.data;

//             if (!clubPrice.equals(newPrice)) { // unreachable
//                 fail("Expected equal: " + clubPrice + " <-> " + newPrice);
//             }
//         }
//     }

//     @Test
//     public void validLocationTest() {
//         List<ModificationInfo> modifications = Arrays.asList(
//             new ModificationInfo("club01", ClubModifierHelper.PRICE, new Location(0.313, 12.456)),
//             new ModificationInfo("club02", ClubModifierHelper.PRICE, new Location(-20.153, 32.876)),
//             new ModificationInfo("club03", ClubModifierHelper.PRICE, new Location(31.293, -89.784))
//         );

//         // Valid modification
//         try {
//             for (ModificationInfo mod : modifications) {
//                 profileManager.modifyClubData(mod.clubID, mod.type, mod.data);

//                 // must reach
//             }
//         } catch (IllegalArgumentException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (DataFormatException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (NoSuchElementException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         }

//         // Check
//         for (ModificationInfo mod : modifications) {
//             Location clubLocation = clubDAO.getClub(mod.clubID).getLocation();
//             Location newLocation = (Location) mod.data;

//             if (!clubLocation.equals(newLocation)) { // unreachable
//                 fail("Expected equal: " + clubLocation + " <-> " + newLocation);
//             }
//         }
//     }

//     @Test
//     public void validTagAddingTest() {
//         List<ModificationInfo> modifications = Arrays.asList(
//             new ModificationInfo("club01", ClubModifierHelper.ADD_TAG, new TagPOJO("eurobeat")),
//             new ModificationInfo("club02", ClubModifierHelper.ADD_TAG, new TagPOJO("cumbia")),
//             new ModificationInfo("club03", ClubModifierHelper.ADD_TAG, new TagPOJO("alterative"))
//         );

//         // Valid modification
//         try {
//             for (ModificationInfo mod : modifications) {
//                 profileManager.modifyClubData(mod.clubID, mod.type, mod.data);

//                 // must reach
//             }
//         } catch (IllegalArgumentException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (DataFormatException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (NoSuchElementException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         }

//         // Check
//         for (ModificationInfo mod : modifications) {
//             Set<TagPOJO> clubTags = clubDAO.getClub(mod.clubID).getTags();
//             TagPOJO newTag = (TagPOJO) mod.data;

//             if (!clubTags.contains(newTag)) { // unreachable
//                 fail("Expected club to have tag: " + newTag.getTag());
//             }
//         }
//     }

//     @Test
//     public void validTagRemovalTest() {
//         List<ModificationInfo> modifications = Arrays.asList(
//             new ModificationInfo("club01", ClubModifierHelper.REMOVE_TAG, new TagPOJO("latino")),
//             new ModificationInfo("club02", ClubModifierHelper.REMOVE_TAG, new TagPOJO("indie")),
//             new ModificationInfo("club03", ClubModifierHelper.REMOVE_TAG, new TagPOJO("barato"))
//         );

//         // Valid modification
//         try {
//             for (ModificationInfo mod : modifications) {
//                 profileManager.modifyClubData(mod.clubID, mod.type, mod.data);

//                 // must reach
//             }
//         } catch (IllegalArgumentException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (DataFormatException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (NoSuchElementException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         }

//         // Check
//         for (ModificationInfo mod : modifications) {
//             Set<TagPOJO> clubTags = clubDAO.getClub(mod.clubID).getTags();
//             TagPOJO removedTag = (TagPOJO) mod.data;

//             if (clubTags.contains(removedTag)) { // unreachable
//                 fail("Expected club to have tag: " + removedTag.getTag());
//             }
//         }
//     }

//     @Test
//     public void validTagClearTest() {
//         List<ModificationInfo> modifications = Arrays.asList(
//             new ModificationInfo("club01", ClubModifierHelper.CLEAR_TAGS, null),
//             new ModificationInfo("club02", ClubModifierHelper.CLEAR_TAGS, null),
//             new ModificationInfo("club03", ClubModifierHelper.CLEAR_TAGS, null)
//         );

//         // Valid modification
//         try {
//             for (ModificationInfo mod : modifications) {
//                 profileManager.modifyClubData(mod.clubID, mod.type, mod.data);

//                 // must reach
//             }
//         } catch (IllegalArgumentException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (DataFormatException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         } catch (NoSuchElementException e) { // unreachable
//             fail("Not expected error: " + e.getMessage());
//         }

//         // Check
//         for (ModificationInfo mod : modifications) {
//             Set<TagPOJO> clubTags = clubDAO.getClub(mod.clubID).getTags();

//             if (!clubTags.isEmpty()) { // unreachable
//                 fail("Expected club to have no tags.");
//             }
//         }
//     }

//     /**
//      * TODO Comentar
//      */
//     @Test
//     public void invalidClubTest() {
//         List<ModificationInfo> modifications = Arrays.asList(
//             new ModificationInfo("club_13",     ClubModifierHelper.COMMERCIAL_NAME, "Existance"),
//             new ModificationInfo("club00",      ClubModifierHelper.ADDRESS, "C/ Piruleta, 12"),
//             new ModificationInfo("id_CLUB76",   ClubModifierHelper.PRICE, 123.45f),
//             new ModificationInfo("clmad87",     ClubModifierHelper.LOCATION, new Location(23.2, 5.4)),
//             new ModificationInfo("madnor2",     ClubModifierHelper.ADD_TAG, new TagPOJO("doesnotreach")),
//             new ModificationInfo("dance16",     ClubModifierHelper.REMOVE_TAG, new TagPOJO("doesnotreach")),
//             new ModificationInfo("",            ClubModifierHelper.CLEAR_TAGS, null)
//         );
        
//         for (ModificationInfo mod : modifications) {
//             try {
//                 profileManager.modifyClubData(mod.clubID, mod.type, mod.data);

//                 // unreachable
//                 fail("Expected error with modification. " + mod);
//             } catch (IllegalArgumentException e) { // unreachable
//                 fail("Not expected error: " + e.getMessage() + " -> with modification:" + mod);
//             } catch (DataFormatException e) { // unreachable
//                 fail("Not expected error: " + e.getMessage() + " -> with modification:" + mod);
//             } catch (NoSuchElementException e) {
//                 // must reach
//             }
//         }
//     }

//     @Test
//     public void invalidArgumentTest() {
//         List<ModificationInfo> modifications = Arrays.asList(
//             new ModificationInfo("club01", ClubModifierHelper.COMMERCIAL_NAME, null),
//             new ModificationInfo("club02", ClubModifierHelper.COMMERCIAL_NAME, 4896),
//             new ModificationInfo("club03", ClubModifierHelper.COMMERCIAL_NAME, new TagPOJO("comm")),

//             new ModificationInfo("club01", ClubModifierHelper.ADDRESS, null),
//             new ModificationInfo("club02", ClubModifierHelper.ADDRESS, 7896),
//             new ModificationInfo("club03", ClubModifierHelper.ADDRESS, new TagPOJO("addr")),

//             new ModificationInfo("club01", ClubModifierHelper.PRICE, null),
//             new ModificationInfo("club02", ClubModifierHelper.PRICE, "20 euros"),
//             new ModificationInfo("club03", ClubModifierHelper.PRICE, new TagPOJO("pric")),

//             new ModificationInfo("club01", ClubModifierHelper.LOCATION, null),
//             new ModificationInfo("club02", ClubModifierHelper.LOCATION, "Madrid Norte"),
//             new ModificationInfo("club03", ClubModifierHelper.LOCATION, new TagPOJO("loca")),

//             new ModificationInfo("club01", ClubModifierHelper.ADD_TAG, null),
//             new ModificationInfo("club02", ClubModifierHelper.ADD_TAG, "dance"),
//             new ModificationInfo("club03", ClubModifierHelper.ADD_TAG, 4869),
            
//             new ModificationInfo("club01", ClubModifierHelper.REMOVE_TAG, null),
//             new ModificationInfo("club02", ClubModifierHelper.REMOVE_TAG, "latino"),
//             new ModificationInfo("club03", ClubModifierHelper.REMOVE_TAG, 0013)

//             // CLEAR_TAG can be called with any data, as it is not used.
//         );

//         for (ModificationInfo mod : modifications) {
//             try {
//                 profileManager.modifyClubData(mod.clubID, mod.type, mod.data);

//                 // unreachable
//                 fail("Expected error with modification. " + mod);
//             } catch (NoSuchElementException e) { // unreachable
//                 fail("Not expected error: " + e.getMessage() + " -> with modification:" + mod);
//             } catch (DataFormatException e) { // unreachable
//                 fail("Not expected error: " + e.getMessage() + " -> with modification:" + mod);
//             } catch (IllegalArgumentException e) {
//                 // must reach
//             }
//         }
//     }

//     @Test
//     public void invalidDataTest() {
//         List<ModificationInfo> modifications = Arrays.asList(
//             // XXX ¿Añadir parseos o dejarlos sin condiciones?
//             // COMMERCIAL_NAME -> No conditions on the String.
//             // ADDRESS -> No conditions on the String.

//             new ModificationInfo("club01", ClubModifierHelper.PRICE, -20.50f),
//             new ModificationInfo("club02", ClubModifierHelper.PRICE, -5.30f),
//             new ModificationInfo("club03", ClubModifierHelper.PRICE, -0.01f)

//             // LOCATION -> No conditions on the Location.

//             /*
//             XXX A la espera de implementar el parseo con TagManagerSA
//             new ModificationInfo("club01", ClubModifierHelper.ADD_TAG, new TagPOJO("pollo")),
//             new ModificationInfo("club02", ClubModifierHelper.ADD_TAG, new TagPOJO("inactive")),
//             new ModificationInfo("club03", ClubModifierHelper.ADD_TAG, new TagPOJO("()")),
//             */
            
//             // REMOVE_TAG -> If not a club tag, nothing happens.
//             // CLEAR_TAG can be called with any data, as it is not used.
//         );

//         for (ModificationInfo mod : modifications) {
//             try {
//                 profileManager.modifyClubData(mod.clubID, mod.type, mod.data);

//                 // unreachable
//                 fail("Expected error with modification. " + mod);
//             } catch (NoSuchElementException e) { // unreachable
//                 fail("Not expected error: " + e.getMessage() + " -> with modification:" + mod);
//             } catch (IllegalArgumentException e) { // unreachable
//                 fail("Not expected error: " + e.getMessage() + " -> with modification:" + mod);
//             } catch (DataFormatException e) {
//                 // must reach
//             }
//         }
//     }


// }