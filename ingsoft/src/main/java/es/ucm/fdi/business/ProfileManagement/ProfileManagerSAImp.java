package es.ucm.fdi.business.ProfileManagement;

import java.util.Set;
import java.time.LocalDate;


import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.ClubDataID;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.ClubManageTool;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.UserPOJO;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.UserDataID;
import es.ucm.fdi.business.ProfileManagement.ManagementTools.UserManageTool;

/**
 * Class to be used as the Profile Manager of the application.
 */
public class ProfileManagerSAImp implements ProfileManagerSA {
    private static ClubDAOImp clubDAO = new ClubDAOImp(); //¿Singleton?
    private static UserDAOImp userDAO = new UserDAOImp();

    public ProfileManagerSAImp(ClubDAOImp clubs, UserDAOImp users) {
        clubDAO = clubs;
        userDAO = users;
    }

    public void addNewClub(String id, String location, float price, Set<String> tags) {
        // Is already registered?
        if (clubDAO.exist(id)) {
            // throw AlreadyExistingClub exception
        }
        
        // Valid location? [Qué formato vamos a usar]

        // Valid price? [Sólo 2 decimales]

        // Valid tags? [¿Usaremos tags preestblecidos (TagsDAO)?]

        ClubPOJO newClub = new ClubPOJO(id, location, price, tags);
        clubDAO.addClub(newClub);
    }

    public void addNewUser(String id, String password, String email, String name, LocalDate birthday) {
        // Is already registered?
        if (userDAO.exist(id)) {
            // throw AlreadyExistingUser exception
        }

        // Valid password? [¿sólo contraseñas 'seguras'?]

        // Valid email?

        // Valid name?

        // Valid birthday? [¿filtro de edad?]

        UserPOJO newUser = new UserPOJO(id, password, email, name, birthday);
        userDAO.addUser(newUser);
    }

	public void modifyClubData(String id, ClubDataID dataID, Object newData) {
        ClubPOJO club = clubDAO.getClub(id);

        if (club == null) {
            // throw NonExistingClub excepction
        }

        // try...catch (for wrong newData)
        ClubManageTool clubManager = new ClubManageTool(club, clubDAO);
        clubManager.modify(dataID, newData);
    }
    
    public void modifyUserData(String id, UserDataID dataID, Object newData) {
        UserPOJO user = userDAO.getUser(id);

        if (user == null) {
            // throw NonExistingUser exception //Better use an already existing exception
        }

        // try...catch (for wrong newData)
        UserManageTool userManager = new UserManageTool(user, userDAO);
        userManager.modify(dataID, newData);
    }

	public void removeClub(String id) {
        if ( ! clubDAO.exist(id)) {
            //throw NonExistingProfile exception
        }

        clubDAO.removeClub(id);
    }

    public void removeUser(String id) {
        if ( ! userDAO.exist(id)) {
            // throw NonExistingProfile exception
        }

        userDAO.removeUser(id);
    }

    public void addNewRate(String clubID, int rate, String userID) {
        ClubPOJO ratedClub = clubDAO.getClub(clubID);

        if (ratedClub == null) {
            // throw NonExistingClub excepction
        }

        // Parse? [De 0 a 10]

        ratedClub.addUserRate(userID, rate);
    }

    public void addNewOpinion(String clubID, String opinion, String userID) {
        ClubPOJO evaluatedClub = clubDAO.getClub(clubID);

        if (evaluatedClub == null) {
            // throw NonExistingClub exception
        }

        // Parse? [Número máximo de caracteres]

        evaluatedClub.addUserOpinion(userID, opinion);
    }
}