package es.ucm.fdi.business.ticketmanagement;

import java.util.NoSuchElementException;

import es.ucm.fdi.business.util.EmailSenderHelper;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Class implementing the TicketManager interface.
 */
public class TicketManagerSAImp implements TicketManagerSA {
	private UserDAO users;
	private ClubDAO clubs;

	/**
	 * Class constructor.
	 * 
	 * @param users users
	 * @param clubs clubs
	 */
	public TicketManagerSAImp(ClubDAO clubs, UserDAO users) {
		this.users = users;
		this.clubs = clubs;
	}

	/**
	 * {@inheritDoc}
	 */
	public void buyTicket(String clubID, String userID)
			throws NoSuchElementException {
		ClubPOJO club = clubs.getClub(clubID);
		UserPOJO user = users.getUser(userID);

		if (club == null || user == null)
			throw new NoSuchElementException();

		float price = club.getPrice();
		String email = user.getEmail();
		String psw = user.getPassword();

		/*
		 *  Payment code
		 *  Stripe seems the best API to do it easily, it is not for free, 
		 *  it takes a 1.4% for each transaction + 0.25�
		 */
		boolean isSuccessful = performTransaction(price);
		
		EmailSenderHelper.send("enjoymad@gmail.com", email, psw, 
				(isSuccessful ? "Se" : "No se") + " ha completado la compra de una entrada de " + clubID, "Tramite EnjoyMad");
	}

	public boolean performTransaction(double price) {
		return true;
	}
}
