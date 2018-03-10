package es.ucm.fdi.TicketManagement;



abstract class TicketManager {
	public void buyTicket(String club_name, String user_name) {
		ClubDAO clubs = new ClubDAO();
		ClubPOJO club = clubs.getClub(club_name);
		UserDAO users = new UserDAO();
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

