package es.ucm.fdi.datos;

import java.util.Map;

import es.ucm.fdi.datos.dataobjects.Ticket;

//OBSERVACTION: INDEPENDENT FROM DBMANAGER

//Specified in CRC
public class TicketManager {
private Map <String, Ticket> availableTickets;

void addTickets(/*params the ticket constructor needs*/){
	Ticket t = new Ticket(//Cosas de ticket);
	availableTickets.put(, t);
}
}
