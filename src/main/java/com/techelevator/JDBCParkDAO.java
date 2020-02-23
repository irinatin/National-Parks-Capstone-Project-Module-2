package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO{

	
private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	//gets each park from database and stores them in the ArrayList by name
	@Override
	public List<Park> getAllParks() {
		List<Park> parks = new ArrayList<Park>();
		String sqlGetAllParks = "SELECT * FROM park ORDER BY name";
		Park thePark;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		while(results.next()) {
			thePark = mapRowToPark(results);
			parks.add(thePark);
		}
		return parks;
	}

	//gets each park from database and stores them in the ArrayList by id
	@Override
	public List<Park> getParkById(long id) {
		List<Park> parks = new ArrayList<Park>();
		String sqlGetAllParks = "SELECT * FROM park WHERE park_id = ?";
		Park thePark;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks, id);
		while(results.next()) {
			thePark = mapRowToPark(results);
			parks.add(thePark);
		}
		return parks;
	}
	
	
	private Park mapRowToPark(SqlRowSet results) {
		Park thePark = new Park();
		//thePark = new Park();
		thePark.setParkId(results.getLong("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date").toLocalDate());
		thePark.setArea(results.getLong("area"));
		thePark.setVisitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));
		return thePark;
	}
}
