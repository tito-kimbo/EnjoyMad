package es.ucm.fdi.business.TicketManagement;
/**
 * Interface containing the methods necessary in a TicketManager.
 * @author Fco Borja
 * @author Carlijn
 */
public interface TicketManagerSA {
	/**
	 * Buys a ticket.
	 * @param club_id club identification
	 * @param user_id user identification
	 */
	public abstract void buyTicker(String club_id, String user_id);
}
