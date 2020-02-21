package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;

public class ParksCLI {
	
	private static final String MENU_OPTION_EXIT = "Quit";
	private static final String MENU_OPTION_RETURN_TO_PREVIOUS = "Return to previous screen";
	
	private static final String PARK_MENU_LIST_PARK_INFO = "Park Information Screen";

	private static final String CAMPGROUNDS_IN_PARK = "View Campgrounds";
	private static final String SEARCH_FOR_RESERVATION = "Search for Reservation";
	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] { CAMPGROUNDS_IN_PARK,
																		   SEARCH_FOR_RESERVATION,
																		   MENU_OPTION_RETURN_TO_PREVIOUS};
	
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

	private List<String> parkList = new ArrayList<String>();
	
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
		printHeading("View Parks Interface");
		handleListAllParks();
		String[] parkArray = new String[parkList.size()]; 
		parkList.toArray(parkArray);
		String choice = (String)menu.getChoiceFromOptions(parkArray);

		int i = 0;
		while(true) {
			if(choice.equals(parkArray[i])) {
				handleGetAllParkInfoByName(parkArray[i]);
//1			} else if(choice.equals(MENU_OPTION_RETURN_TO_PREVIOUS)) {
				choice.equals(CAMPGROUND_MENU_OPTIONS);
			} else if(choice.equals(MENU_OPTION_RETURN_TO_PREVIOUS)) {
				printHeading("So Long, and Thnx 4 4ll da Fish.");
				System.exit(0);
			}
		}
	}

	public void  handleListAllParks() {
		printHeading("Select a Park for further details:");
		List<Parks> allParks = parksDAO.findAllParks();
//		listParks(allParks);

		if(allParks.size() > 0) {
			int i = 0;
			for(Parks park : allParks) {
				parkList.add(park.getName());
				i++;
			}
		}
	}

	private void handleGetAllParkInfoByName(String name) {
		printHeading("-- parks by id --");
		List<Parks> allParks = parksDAO.listParkInfoByName(name);
		if(allParks.size() > 0) {
			for(Parks park : allParks) {
				if (name.equals(park.getName())) {
					System.out.println(park.getName() + "National Park");
					System.out.println("Location" + "\t\t" + park.getLocation());
	//				System.out.println(park.establishDate().toLocalDate());
					System.out.println("Area:" + "\t\t" + park.getArea().toString());
	//				System.out.println(park.getVisitors().toString());
					System.out.println(park.getDescription());
				}
			}
		}
	}

	private void listParkInfo(List<Parks> parkList, String name) {
		System.out.println();
		if(parkList.size() > 0) {
			for(Parks park : parkList) {
				System.out.println(park.getName()); 
				System.out.println(park.getLocation());
//				System.out.println(park.getEstablishedDate().toLocalDate);
				System.out.println(park.getArea());
				System.out.println(park.getVisitors());
				System.out.println(park.getDescription());
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
