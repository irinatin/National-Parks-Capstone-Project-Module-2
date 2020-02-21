package com.techelevator;
import java.util.List;
//import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;

public class ParksCLI {
	
	private static final String MAIN_MENU_OPTION_EXIT = "Quit";

	private static final String MENU_OPTION_RETURN_TO_MAIN = "Return to main menu";

	private static final String PARK_MENU_ALL_PARKS = "Select a Park further details:";
	private static final String[] PARK_MENU_OPTIONS = new String[] { PARK_MENU_ALL_PARKS,
																	 MAIN_MENU_OPTION_EXIT};
	
	private static final String PARK_MENU_LIST_PARK_INFO = "Park Information Screen";
	private static final String PARK_MENU_OPTION_SEARCH_BY_SITE = "campground search by site";

//	private static final String PARK_MENU_OPTION_ALL_CAMPGROUNDS = "Show all campgrounds";
	private static final String PARK_MENU_PARK_INFO = "ca";
//	private static final String PARK_MENU_OPTION_SEARCH_BY_SITE = "campground search by site";
//	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] { PARK_MENU_OPTION_ALL_CAMPGROUNDS,
//																		   PARK_MENU_PARK_INFO,
//																		   MENU_OPTION_RETURN_TO_MAIN};
//	private static final String PARK_MENU_OPTION_ALL_CAMPGROUNDS = "Show all campgrounds";
//	private static final String PARK_MENU_OPTION_SEARCH_BY_DATE = "campground search by date";
//	private static final String PARK_MENU_OPTION_SEARCH_BY_SITE = "campground search by site";
//	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] { PARK_MENU_OPTION_ALL_CAMPGROUNDS,
//																		   PARK_MENU_OPTION_SEARCH_BY_DATE,
//																		   PARK_MENU_OPTION_SEARCH_BY_SITE,
//																		   MENU_OPTION_RETURN_TO_MAIN};
//	
	
	private Menu menu;
	private ParksDAO parksDAO;
	private CampgroundsDAO campgroundsDAO;
	private SiteTablesDAO siteTablesDAO;
	private ReservationsDAO reservationsDAO;
	
	public static void main(String[] args) {
		ParksCLI application = new ParksCLI();
		application.run();
	}
	
	public ParksCLI() {
		this.menu = new Menu(System.in, System.out);
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		parksDAO = new JDBCParksDAO(dataSource);
		campgroundsDAO = new JDBCCampgroundsDAO(dataSource);
		siteTablesDAO = new JDBCSiteTablesDAO(dataSource);
		reservationsDAO = new JDBCReservationsDAO(dataSource);
	}

	private void run() {
		displayParksBanner();	
		while(true) {
			printHeading("View Parks Interface");
			handleParks();
			String choice = (String)menu.getChoiceFromOptions(PARK_MENU_OPTIONS);
	
			
			
			if(choice.equals(PARK_MENU_ALL_PARKS)) {
				if(choice.equals(PARK_MENU_PARK_INFO)) {
					handleParks();
			} else if(choice.equals(MAIN_MENU_OPTION_EXIT)) {
				printHeading("So Long, and Thnx 4 4ll da Fish.");
				System.exit(0);
			}
			}
		}
		}

	private void handleParks() {
		handleListAllParks();
		
//		String choice = (String)menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS);
//		if(choice.equals(PARK_MENU_OPTION_ALL_CAMPGROUNDS)) {
//		} else if (choice.equals(PARK_MENU_OPTION_SEARCH_BY_DATE)) {
//			handleListAllParksByDate();
		} 
//	}	

	private void handleListAllParks() {
		printHeading("Select a Park for further details:");
		List<Parks> allParks = parksDAO.findAllParks();
		listParks(allParks);
	}

	private void handleListAllParksByDate() {
		printHeading("All Parks");
		List<Parks> allParks = parksDAO.findAllParks();
		listParks(allParks);
	}
	private void listParks(List<Parks> parks) {
		System.out.println();
		if(parks.size() > 0) {
			int i = 1;
			for(Parks park : parks) {
				System.out.println(  i +  ") " +  park.getName());
				i++;
			}
		} else {
			System.out.println("\n*** No results ***");
		}
	}
	
	private void printHeading(String headingText) {
		System.out.println("\n"+headingText);
		for(int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
//	@SuppressWarnings("resource")
//	private String getUserInput(String prompt) {
//		System.out.print(prompt + " >>> ");
//		return new Scanner(System.in).nextLine();
//	}

	private void displayParksBanner() {
		System.out.println(" ___  __       _   _                    _   ____            _  		          	");
		System.out.println("|  \\ | |      | | (_)                  | | |  _ \\          | |  __ ___      	");
		System.out.println("| \\ |  | ___ _| |_ __ ___  _  _   ____ | | | (_) |____ ____| | / /// \\\\     	");
		System.out.println("| |\\|  |/ _ `|_  _/ |/ _ \\| |\\ \\ / _ `|| | | ___/  _ `| ___/ '/_/ \\\\_     ");
		System.out.println("| | \\  | |_| || | | | (_) | | | | |_| || | | |  | |_| | |  | |\\ \\    \\\\    ");
		System.out.println("|_|  \\_|____^||_| |_|\\___/|_| |_|____^||_| |_|  |____^|_|  |_| \\_\\\\\\_//   ");
		System.out.println();
	}
}
