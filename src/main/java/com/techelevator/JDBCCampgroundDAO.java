package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO{
	
private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	//@Override
	public List<Campground> getCampgroundByParkId(long id) {
		List<Campground> campgrounds = new ArrayList<Campground>();
		String sqlGetCampgroundsByParkId = "SELECT * FROM campground WHERE park_id = ?";
		Campground theCampground;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCampgroundsByParkId, id);
		while(results.next()) {
			theCampground = mapRowToCampground(results);
			campgrounds.add(theCampground);
		}
		return campgrounds;
	}
	
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground;
		theCampground = new Campground();
		theCampground.setCampgroundId(results.getLong("campground_id"));
		theCampground.setParkId(results.getLong("park_id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpenFrom(results.getInt("open_from_mm"));
		theCampground.setOpenTo(results.getInt("open_to_mm"));
		theCampground.setDailyFee(results.getBigDecimal("daily_fee"));
		return theCampground;
	}

}
