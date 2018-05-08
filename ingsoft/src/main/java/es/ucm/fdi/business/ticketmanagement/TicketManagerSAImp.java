package es.ucm.fdi.business.ticketmanagement;

import java.util.NoSuchElementException;

import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;
/**
 * Class implementing the TicketManager interface.
 * @author Fco Borja
 * @author Carlijn
 */
public abstract class TicketManagerSAImp implements TicketManagerSA {
	private UserDAOImp users;
	private ClubDAOImp clubs;
	/**
	 * Class constructor.
	 * @param users users
	 * @param clubs clubs
	 */
	TicketManagerSAImp(UserDAOImp users, ClubDAOImp clubs){
		this.users = users;
		this.clubs = clubs;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void buyTicket(String clubID, String userID) throws NoSuchElementException {
		ClubPOJO club = clubs.getClub(clubID);
		UserPOJO user = users.getUser(userID);
		
		if(club == null || user == null)
			throw new NoSuchElementException();
		
		float price = club.getPrice();
		String email = user.getEmail();
		String psw = user.getPassword();
		
		/*
		 *  Payment code
		 *  Stripe seems the best API to do it easily, it is not for free, it takes a 1.4% for each transaction + 0.25�
		 */
		
		EmailSenderHelper.send("enjoymad@gmail.com", email, psw, "Se ha completado la compra de una entrada de " + clubID, "Tramite EnjoyMad");
	}
	
}

