package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO{
	
private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	

	@Override
	public List<Site> getAvailableSitesByReservationDate(long campgroundId, LocalDate startDate, LocalDate endDate) {
		List<Site> availableSites = new ArrayList<Site>();
		String sqlFindTopFiveAvailableSites = "SELECT distinct * FROM site " + 
				"JOIN campground on site.campground_id = campground.campground_id " + 
				"WHERE site.campground_id = ? " + 
				"AND site_id NOT IN " + 
				"(SELECT site.site_id FROM site " + 
				"JOIN reservation ON reservation.site_id = site.site_id " + 
				"WHERE ? > reservation.from_date AND ? < reservation.to_date) " + 
				"ORDER BY daily_fee " + 
				"LIMIT 5";
		Site theSite;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindTopFiveAvailableSites, campgroundId, startDate, endDate);
		while(results.next()) {
			theSite = mapRowToSite(results);
			availableSites.add(theSite);
		}
		return  availableSites;
	}
	
	
	
	private Site mapRowToSite(SqlRowSet results) {
		Site theSite;
		theSite = new Site();
		theSite.setSiteId(results.getLong("site_id"));
		theSite.setCampgroundId(results.getLong("campground_id"));
		theSite.setSiteNumber(results.getInt("site_number"));
		theSite.setMax(results.getInt("max_occupancy"));
		theSite.setAccessible(results.getBoolean("accessible"));
		theSite.setMaxRvLength(results.getInt("max_rv_length"));
		theSite.setHasUtilities(results.getBoolean("utilities"));
		theSite.setCampgroundId(results.getLong("campground_id"));
		theSite.setParkId(results.getLong("park_id"));
		theSite.setName(results.getString("name"));
		theSite.setOpenFrom(results.getInt("open_from_mm"));
		theSite.setOpenTo(results.getInt("open_to_mm"));
		theSite.setDailyFee(results.getBigDecimal("daily_fee"));
		return theSite;
	}

}
