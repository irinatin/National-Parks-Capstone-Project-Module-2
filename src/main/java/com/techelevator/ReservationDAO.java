package com.techelevator;

import java.time.LocalDate;

public interface ReservationDAO {
	
	public long makeReservation (long site_id, String name, LocalDate startDate, LocalDate endDate);

}
