package com.techelevator;

	import java.util.ArrayList;
	import java.util.List;

	import javax.sql.DataSource;

	import org.springframework.jdbc.core.JdbcTemplate;
	import org.springframework.jdbc.support.rowset.SqlRowSet;

	public class JDBCCampgroundsDAO implements CampgroundsDAO {

		private JdbcTemplate jdbcTemplate;

		public JDBCCampgroundsDAO(DataSource dataSource) {
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}

// save for BONUS #3
//		@Override
//		public List<Campgrounds> findAllCampgrounds() {
//			ArrayList<Campgrounds> allCampgrounds = new ArrayList<>();
//			String sqlFindAllCampgrounds = "SELECT * " + "FROM Campground " + "ORDER BY name ";
//			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindAllCampgrounds);
//			while (results.next()) {
//				Campgrounds theCampground = mapRowToCampgrounds(results);
//				allCampgrounds.add(theCampground);
//			}
//
//			return allCampgrounds;
//		}


	public List<Campgrounds> findAllCampgroundsByParkId (Long id) {
ArrayList<Campgrounds> campgroundsChosen = new ArrayList<>();
		String sqlFindCampgroundsById = 
						  " SELECT c.* , p.name " 
						+ " FROM campground c " 
						+ "  JOIN park p USING (park_id) "
						+ " WHERE c.park_id = ? "
						+ " ORDER BY c.name ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindCampgroundsById, id);
		while (results.next()) {
			Campgrounds theCampgrounds = mapRowToCampgrounds(results);
			campgroundsChosen.add(theCampgrounds);
		}

		return campgroundsChosen;
	}

	private Campgrounds mapRowToCampgrounds(SqlRowSet results) {
		Campgrounds theCampground;
		theCampground = new Campgrounds();
		theCampground.setId(results.getLong("campground_id"));
		theCampground.setId(results.getLong("park_id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpenFromMonth(results.getInt("open_from_mm"));			
		theCampground.setOpenToMonth(results.getInt("open_to_mm"));
		theCampground.setDailyFee(results.getBigDecimal("daily_fee"));

		return theCampground;
	}
}
