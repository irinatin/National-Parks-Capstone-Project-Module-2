package com.techelevator;

import java.awt.Menu;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	
	
	private static final String MAIN_MENU_OPTION_PARKS = "Parks Interface";
	
	private static final String[] MAIN_MENU_OPTIONS_MENU = new String[] { MAIN_MENU_OPTION_PARKS};
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private Menu menu;
	private ParksDAO parksDAO;
	private CampgroundsDAO campgroundsDAO;
	private SiteTablesDAO siteTablesDAO;
	private ReservationsDAO reservationsDAO;

	public static void main(String[] args) {
		
		CampgroundCLI application = new CampgroundCLI();
		application.run();
		
	}

	public CampgroundCLI() {
		
		
//		private static final String[] MAIN_MENU_OPTIONS = new String[] {  
//				 MAIN_MENU_OPTION_EXIT };
//	
		
		//this.menu = new Menu(System.in, System.out);
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		parksDAO = new JDBCParksDAO(dataSource);
		campgroundsDAO = new JDBCCampgroundsDAO(dataSource);
		siteTablesDAO = new JDBCSiteTablesDAO(dataSource);
		reservationsDAO = new JDBCReservationsDAO(dataSource);
		
	}

	public void run() {
		displayApplicationBanner();	
		while(true) {
			System.out.println("Main Menu");
			String choice = (String)menu.getChoiceFromOptions();
		
	}
}
