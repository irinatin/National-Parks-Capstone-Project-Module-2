package com.techelevator;

import java.util.List;
import java.time.LocalDate;

public interface ReservationsDAO {

	public List<Reservations> listAllReservations();

	public List<Reservations> listAllReservationsByCampground(Long campgroundId);
	
	public List<Reservations> listReservationsByDate(Long id, LocalDate arrivalDate, LocalDate departureDate);
	public List<Reservations> listReservationsBySite();
	
	public void addNewReservation(Reservations newReservation);
	
}
