package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteTablesDAO implements SiteTablesDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCSiteTablesDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<SiteTables> findAllSiteTablesByCampgroundId (Long id) {
		ArrayList<SiteTables> allSitesChosen = new ArrayList<>();
		String sqlFindSiteTablesById = 
						  " SELECT * " 
						+ " FROM SiteTable " 
						+ " WHERE campground_id = ? "
						+ " ORDER BY site_number ";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindSiteTablesById, id);
		while (results.next()) {
			SiteTables theSiteTablesPerCampground = mapRowToSiteTables(results);
			allSitesChosen.add(theSiteTablesPerCampground);
		}

		return allSitesChosen;
	}
	
	public List<SiteTables> findAllSiteTablesByDate(LocalDate from_date) {
		ArrayList<SiteTables> allSitesChosen = new ArrayList<>();
		String sqlFindSiteTablesById = 
						  " SELECT * " 
						+ " FROM SiteTable " 
						+ " WHERE campground_id = ? "
						+ " ORDER BY site_number ";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindSiteTablesById, from_date);
		while (results.next()) {
			SiteTables theSiteTablesPerCampground = mapRowToSiteTables(results);
			allSitesChosen.add(theSiteTablesPerCampground);
		}

		return allSitesChosen;
	}
	
	public List<SiteTables> findAllSiteTablesBySite(int site_number) {
		ArrayList<SiteTables> allSitesChosen = new ArrayList<>();
		String sqlFindSiteTablesById = 
						  " SELECT * " 
						+ " FROM SiteTable " 
						+ " WHERE campground_id = ? "
						+ " ORDER BY site_number ";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindSiteTablesById, site_number);
		while (results.next()) {
			SiteTables theSiteTablesPerCampground = mapRowToSiteTables(results);
			allSitesChosen.add(theSiteTablesPerCampground);
		}

		return allSitesChosen;
	}
	

	private SiteTables mapRowToSiteTables(SqlRowSet results) {
		SiteTables theSiteTable;
		theSiteTable = new SiteTables();
		theSiteTable.setId(results.getLong("site_id"));
		theSiteTable.setCampgroundId(results.getLong("campground_id"));
		theSiteTable.setNumber(results.getInt("site_number"));
		theSiteTable.setMaxOccupancy(results.getInt("max_occupancy"));			
		theSiteTable.isIfHandicapAccessible();
		theSiteTable.setMaxRvLength(results.getInt("max_rv_length"));
		theSiteTable.isIfUtilitiesAccess();

		return theSiteTable;
	}

	@Override
	public List<SiteTables> findAllSiteTablesOfThisCampground() {
		// TODO Auto-generated method stub
		return null;
	}
}
