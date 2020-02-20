package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationsDAO implements ReservationsDAO{

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationsDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Reservations> listAllReservations() {
		ArrayList<Reservations> allReservations = new ArrayList<>();
		String sqlFindAllReservations = "SELECT * "
									  + "  from Reservations "
									  + "  order by reservation_id ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindAllReservations);
		while (results.next()) {
			Reservations theReservation = mapRowToReservation(results);
			allReservations.add(theReservation);
		}
		return allReservations;
	}


	public List<Reservations> listReservationsByDate(LocalDate fromDate, LocalDate toDate) {
		ArrayList<Reservations> allReservations = new ArrayList<>();
		String sqlFindAllReservations = "SELECT * "
									  + "  from Reservations "
									  + " WHERE ( ? not between start_date and end_date) "
									  + "   AND ( ? not between start_date and end_date) "
									  + "  order by reservation_id ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindAllReservations, fromDate, toDate);
		while (results.next()) {
			Reservations theReservation = mapRowToReservation(results);
			allReservations.add(theReservation);
		}
		return allReservations;
	}

	public List<Reservations> listReservationsBySite(Long siteId) {
		ArrayList<Reservations> allReservations = new ArrayList<>();
		String sqlFindAllReservations = "SELECT * "
									  + "  from Reservations "
									  + " WHERE  ? = site_id " ;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindAllReservations, siteId);
		while (results.next()) {
			Reservations theReservation = mapRowToReservation(results);
			allReservations.add(theReservation);
		}
		return allReservations;
	}

	@Override
	public void addNewReservation(Reservations newReservation) {
		String sqlInsertReservation = 
				"INSERT INTO reservation(reservation_id, site_id, name, start_date, end_date, create_date) " 
			  + "VALUES (?, ?, ?, ?, CURRENT_DATE) ";
		newReservation.setId(getNextReservationId());
			jdbcTemplate.update(sqlInsertReservation, 
								newReservation.getId(),
								newReservation.getSiteId(),
								newReservation.getStartDate(),
								newReservation.getEndDate());
	}

	private long getNextReservationId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet(
				 "SELECT nextval('reservation_reservation_id_seq')");
		if(nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("DANGER!! BAD REQUEST to get an id for the new reservation");
		}
	}

	private Reservations mapRowToReservation(SqlRowSet results) {
		Reservations theReservation;
		theReservation = new Reservations();
		theReservation.setId(results.getLong("id"));
		theReservation.setId(results.getLong("site_Id"));
		theReservation.setName(results.getString("name"));
		theReservation.setStartDate(results.getDate("from_date").toLocalDate());
		theReservation.setEndDate(results.getDate("to_date").toLocalDate());
		theReservation.setCreateDate(results.getDate("create_date").toLocalDate());
		
		return theReservation;
	}


	@Override
	public List<Reservations> listReservationsByDate() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Reservations> listReservationsBySite() {
		// TODO Auto-generated method stub
		return null;
	}
}
