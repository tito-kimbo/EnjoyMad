package es.ucm.fdi.business.ticketmanagement;

/**
 * Interface containing the methods necessary in a TicketManager.
 */
public interface TicketManagerSA {
	/**
	 * Buys a ticket.
	 * 
	 * @param club_id
	 *            club identification
	 * @param user_id
	 *            user identification
	 */
	public abstract void buyTicket(String clubID, String userID);
}
