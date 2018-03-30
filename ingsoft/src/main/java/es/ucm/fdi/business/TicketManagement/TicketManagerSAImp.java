package es.ucm.fdi.business.TicketManagement;

import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.UserPOJO;
/**
 * Class implementing the TicketManager interface.
 * @author Fco Borja
 * @author Carlijn
 */
abstract class TicketManagerSAImp implements TicketManagerSA {
	/**
	 * @inheritDoc
	 */
	public void buyTicket(String club_id, String user_id) {
		UserDAOImp users = new UserDAOImp();
		UserPOJO user = users.getUser(user_id);
		
		String email = user.getEmail();
		String psw = user.getPassword();
		
		/*
		 *  Payment code
		 *  Stripe seems the best API to do it easily, it is not for free, it takes a 1.4% for each transaction + 0.25�
		 */
		
		EmailSender.send("enjoymad@gmail.com", email, psw, "Se ha completado la compra de una entrada de " + club_id, "Tramite EnjoyMad");
	}
}

