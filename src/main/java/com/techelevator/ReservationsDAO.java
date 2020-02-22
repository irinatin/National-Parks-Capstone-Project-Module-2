package com.techelevator;

import java.util.List;


public interface ReservationsDAO {

	public List<Reservations> listAllReservations();

	public List<Reservations> listAllReservationsByCampground();
	
	public List<Reservations> listReservationsByDate();
	public List<Reservations> listReservationsBySite();
	
	public void addNewReservation(Reservations newReservation);
	
}
