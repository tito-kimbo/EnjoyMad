package es.ucm.fdi.business.TicketManagement;

import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

abstract class TicketManagerSAImp implements TicketManagerSA {
	public void buyTicket(String club_name, String user_name) {
		ClubDAOImp clubs = new ClubDAOImp();
		ClubPOJO club = clubs.getClub(club_name);
		UserDAOImp users = new UserDAOImp();
		UserPOJO user = users.getUser(user_name);
		
		float price = club.getPrice();
		String email = user.getEmail();
		
		/*
		 *  Payment code
		 *  Stripe seems the best API to do it easily, it is not for free, it takes a 1.4% for each transaction + 0.25�
		 */
		
		EmailSender.send(email, "Se ha completado la compra de una entrada de " + club_name, "Tr�mite EnjoyMad");
	}
}

