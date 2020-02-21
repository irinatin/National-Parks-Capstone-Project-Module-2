package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParksDAO implements ParksDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParksDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Parks> findAllParks() {
		ArrayList<Parks> allParks = new ArrayList<>();
		String sqlFindAllParks = "SELECT * " + "FROM park " + "ORDER BY name ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindAllParks);
		while (results.next()) {
			Parks thePark = mapRowToParks(results);
			allParks.add(thePark);
		}

		return allParks;
	}

	@Override
	public List<Parks> listParkInfoById(Long id) {
		ArrayList<Parks> parkChosen = new ArrayList<>();
		String sqlFindParkById = "SELECT * " + "FROM park " + "WHERE park_id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindParkById, id);
		while (results.next()) {
			Parks thePark = mapRowToParks(results);
			parkChosen.add(thePark);
		}

		return parkChosen;
	}

	private Parks mapRowToParks(SqlRowSet results) {
		Parks thePark;
		thePark = new Parks();
		thePark.setId(results.getLong("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date").toLocalDate());
		thePark.setArea(results.getLong("area"));
		thePark.setVisitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));

		return thePark;
	}

}
