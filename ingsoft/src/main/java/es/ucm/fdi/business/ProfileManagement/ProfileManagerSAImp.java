package es.ucm.fdi.business.ProfileManagement;

import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;

import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Class to be used as the Profile Manager of the application.
 */
public class ProfileManagerSAImp extends ProfileManagerSA {
    private static ClubDAOImp clubDAO = new ClubDAOImp(); //¿Singleton?
    private static UserDAOImp userDAO = new UserDAOImp();

    /**
     * List representing the attributes of a ClubPOJO.
     * Used to modify a particular attribute.
     */
    private static ArrayList<String> clubData = {
            "ID",
            "LOCATION",
            "PRICE",
            "RATING"
    };

    /**
     * List representing the attributes of a UserPOJO.
     * Used to modify a particular attribute.
     */
    private static ArrayList<String> userData = {
            "ID",
            "PASSWORD",
            "EMAIL",
            "NAME",
            "BIRTHDAY"
    };

    public ProfileManagerSAImp(ClubDAOImp clubs, UserDAOImp users) {
        clubDAO = clubs;
        userDAO = users;
    }

    @Override
    public void addNewClub(String id, String location, float price, List<String> tags) {
        // Is already registered?
        if (clubDAO.exist(id)) {
            // throw AlreadyExistingClub exception
        }
        
        // Valid location? [Qué formato vamos a usar]

        // Valid price? [Sólo 2 decimales]

        // Valid tags? [¿Usaremos tags preestblecidos (TagsDAO)?]

        ClubPOJO newClub = new ClubPOJO(id, location, price, tags);
        clubDAO.insert(newClub);
    }

    @Override
    public void addNewUser(String id, String password, String email, String name, Date birthday) {
        // Is already registered?
        if (userDAO.exist(id)) {
            // throw AlreadyExistingUser exception
        }

        // Valid password? [¿sólo contraseñas 'seguras'?]

        // Valid email?

        // Valid name?

        // Valid birthday? [¿filtro de edad?]

        UserPOJO newUser = new UserPOJO(id, password, email, name, birthday);
        userDAO.insert(newUser);
    }

    @Override
    public void modifyClubData(String id, String dataType, Object newData) {
        ClubPOJO club = clubDAO.getClub(id);

        if (club == null) {
            // throw NonExistingClub excepction
        }

        // try...catch (for wrong newData)
        clubManager.modify(dataType, newData);
    }
    
    @Override
    public void modifyUserData(String id, String dataType, Object newData) {
        UserPOJO user = userDAO.getClub(id);

        if (user == null) {
            // throw NonExistingUser exception
        }

        // try...catch (for wrong newData)
        userManager.modify(dataType, newData);
    }

    @Override 
	public void removeClub(String id) {
        if ( ! clubDao.exist(id)) {
            //throw NonExistingProfile exception
        }

        clubDAO.removeClub(id);
    }

    @Override
    public void removeUser(String id) {
        if ( ! userDAO.exist(id)) {
            // throw NonExistingProfile exception
        }

        userDAO.removeUser(id);
    }
}