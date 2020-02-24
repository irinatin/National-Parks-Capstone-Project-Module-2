package com.techelevator;

import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;


import org.apache.commons.dbcp2.BasicDataSource;


public class CampgroundCLI {
	
	//create the static final stuff here e.g. private DepartmentDAO departmentDAO
	//private static final String PARK_MENU_DISPLAY_PARKS = "Select a park for further details: ";
	//private static final String[] PARK_MENU_OPTIONS = new String[] { PARK_MENU_DISPLAY_PARKS };
	
	private static final String MAIN_MENU_OPTIONS_PARKS = "View Parks Interface";
	private static final String MAIN_MENU_OPTIONS_QUIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] {MAIN_MENU_OPTIONS_PARKS, MAIN_MENU_OPTIONS_QUIT};
	
	private static final String CAMP_MENU_OPTION_ALL_CAMPGROUNDS = "View Campgrounds";
	private static final String CAMP_MENU_SEARCH_AVAILABLE_RESERVATIONS = "Search for Reservation";
	private static final String CAMP_MENU_RETURN_TO_PREVIOUS = "Return to Previous Screen";
	private static final String[] CAMP_MENU_OPTIONS = new String[] {CAMP_MENU_OPTION_ALL_CAMPGROUNDS, CAMP_MENU_SEARCH_AVAILABLE_RESERVATIONS,CAMP_MENU_RETURN_TO_PREVIOUS};
	
	private static final String RESERVATION_MENU_SEARCH_AVAILABLE = "Search for Available Reservation";
	private static final String RESERVATION_MENU_RETURN_TO_PREVIOUS = "Return to Previous Screen";
	private static final String[] RESERVATION_MENU_OPTIONS = new String[] {RESERVATION_MENU_SEARCH_AVAILABLE,RESERVATION_MENU_RETURN_TO_PREVIOUS};
	
	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservationDAO reservationDAO;
	
	private long numChoice;
	private Park selectedPark;
	
	private List<Campground> campgroundsByParkId;
	private long selectedCampgroundId = 0;
	
	private long selectedSiteId = 0;
	private LocalDate arrival;
	private LocalDate departure;
	private List<Site> availableSites = null;

  	public static void main(String[] args) {
  		
  		CampgroundCLI application = new CampgroundCLI();
		application.run();

	}

  	
  	
	public CampgroundCLI() {
		
		this.menu = new Menu(System.in, System.out);
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
	}
	



	private void run() {
		displayParksBanner();
		
		while(true) {
		
			printHeading("National Park Campsite Reservation");
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(choice.equals(MAIN_MENU_OPTIONS_PARKS)) {
				handleParks();
			} else if (choice.equals(MAIN_MENU_OPTIONS_QUIT)) {
				break;
			}
			
		}
	}
	
	private void handleParks() {
		System.out.println("\nSelect a Park for Further Details");
		List<Park> results = parkDAO.getAllParks();
		int count = 1;
		
		for (Park park : results) {
			System.out.println(count++ + ") " + park.getName());
		}
			System.out.println("Q) " + "Quit");
	
		System.out.print("\nPlease choose an option >>> ");
		
		String preChoice = (String) menu.getUserInput();
		numChoice = handleParkChoice(preChoice, results);
		
		for (Park park : results) {
			if (numChoice == park.getParkId()) {
				selectedPark = park;
			} 
		}
		displayParkInfo(numChoice, selectedPark);
		
		switchToCampgroundMenu();
	}
	
	private void switchToCampgroundMenu () {
		String choice = (String) menu.getChoiceFromOptions(CAMP_MENU_OPTIONS);
		if(choice.equals(CAMP_MENU_OPTION_ALL_CAMPGROUNDS)) {
			System.out.println("\n\n" + selectedPark.getName() + " National Park Campgrounds\n");
			viewCampgroundsByParkId(numChoice, selectedPark);
			handleReservations();
		} else if (choice.equals(CAMP_MENU_SEARCH_AVAILABLE_RESERVATIONS)) {
			System.out.println("\nSearch for Campground Reservation\n");
			viewCampgroundsByParkId(numChoice, selectedPark);
			catchAllDateExceptionsBeforeUsingThemForSearch();
		} else if (choice.equals(CAMP_MENU_RETURN_TO_PREVIOUS)) {
			handleParks();
		}
	}
	
	private void handleReservations() {
		System.out.println("Select a Command");
		
		String choice = (String)menu.getChoiceFromOptions(RESERVATION_MENU_OPTIONS);
		if(choice.equals(RESERVATION_MENU_SEARCH_AVAILABLE)) {
			catchAllDateExceptionsBeforeUsingThemForSearch();
		} else if (choice.equals(RESERVATION_MENU_RETURN_TO_PREVIOUS)) {
			switchToCampgroundMenu();
		}
	}
	
	private void catchAllDateExceptionsBeforeUsingThemForSearch() {
		
		//handle exceptions for campground
		System.out.print("Which Campground (enter 0 to cancel)? >>> ");
		String stringCampId = (String) menu.getUserInput();
		selectedCampgroundId = handleCampgroundExceptions (stringCampId);
		
		//handle exceptions for arrival date
		System.out.print("What is the arrival date? (format: MM/DD/YYY) >>> ");
		String strArrival = menu.getUserInput();
		arrival = handleDateExceptions(strArrival);
		
		//handle exceptions for departure date
		System.out.print("What is the departure date? (format: MM/DD/YYY) >>> ");
		String strDeparture = menu.getUserInput();
		departure = handleDateExceptions(strDeparture);
		
		if (departure.compareTo(arrival) <= 0) {
			System.out.println("***Something is wrong with your arrival and departure dates. Sorry!.***\n");
			catchAllDateExceptionsBeforeUsingThemForSearch();
		}
		
		handleGetAvailableSites();
	}
	
	private LocalDate handleDateExceptions(String dateInput) {
		LocalDate resultDate = null;
		
		if (dateInput.length() != 10) {
			System.out.println("***Invalid date format, please try again!***");
			catchAllDateExceptionsBeforeUsingThemForSearch();
			
		} else {
			
			String[] dateArray = dateInput.split("/");
			
			for (String s : dateArray) {
				try {
					Integer.parseInt(s);
				} catch (NumberFormatException e) {
					System.out.println("***Invalid date format, please try again.***");
					catchAllDateExceptionsBeforeUsingThemForSearch();
				}
			}
			if (dateArray.length != 3) {
				System.out.println("***Invalid date format, please try again.***");
				catchAllDateExceptionsBeforeUsingThemForSearch();
			}
			
			int Month = Integer.parseInt(dateArray[0]);
			int Day = Integer.parseInt(dateArray[1]);
			int Year = Integer.parseInt(dateArray[2]);
			
			if (Month < 1 || Month > 12) {
				System.out.println("***Invalid Month, please try again.***");
				catchAllDateExceptionsBeforeUsingThemForSearch();
			}
			if (Day < 1 || Day > 31) {
				System.out.println("***Invalid Day, please try again.***");
				catchAllDateExceptionsBeforeUsingThemForSearch();
			}
			if (Day > 30 && (Month == 2 || Month == 4 || Month == 6 || Month == 9 || Month == 11)) {
				System.out.println("***Invalid Day! Please try again!***");
				catchAllDateExceptionsBeforeUsingThemForSearch();
			} else if (Day > 28 && (Month == 2)) {
				if (!(Day == 29 && (Month == 2) && (Year % 4 == 0))) {
				System.out.println("***Invalid Day! Please try again!***");
				catchAllDateExceptionsBeforeUsingThemForSearch();
				}
			}
			if (Year != 2020) {
				System.out.println("***Reservations can only be made for 2020!***");
				catchAllDateExceptionsBeforeUsingThemForSearch();
			}
			
			resultDate = LocalDate.of(Year, Month, Day);
		}
			return resultDate;
	}
	
	private long handleCampgroundExceptions (String stringCampgroundId) {
		int selectedCampground = 0;
		long result = 0;
		
		try {
			selectedCampground = Integer.parseInt(stringCampgroundId);
			
		} catch (NumberFormatException e) {
			System.out.println("***Invalid entry, please try again.***\n");
			catchAllDateExceptionsBeforeUsingThemForSearch();
		}
		if (selectedCampground == 0) {
			System.out.println("***Operation cancelled. Returning to Campground Menu***\n");
			switchToCampgroundMenu();
		} 
		
		selectedCampground--; //campground has to be decremented in order to get the correct index
		
		if (selectedCampground < 0 || selectedCampground > campgroundsByParkId.size() - 1) {
			System.out.println("***Please select a different value***\n");
			catchAllDateExceptionsBeforeUsingThemForSearch();
		} else { //if it passes through all the exception cases, we'll get the ACTUAL campgroundID from our List and assign it
			result = campgroundsByParkId.get(selectedCampground).getCampgroundId();
		}
		return result;
	}
	
	private long handleSiteExceptionsAndGetSelectedSiteId (String stringSiteNumber) {
		int selectedSite = 0;
		long result = 0;
		
		try {
			selectedSite = Integer.parseInt(stringSiteNumber);
		} catch (NumberFormatException e) {
			System.out.println("***Invalid entry, please try again.***\n");
			handleMakeReservation();
		}
		
		if (selectedSite == 0) {
			System.out.println("***Cancelling operation, returning to Campground Menu***\n");
			switchToCampgroundMenu();
		} 
		
		selectedSite--; //site has to be decremented in order to get the correct index
		if (selectedSite < 0 || selectedSite > availableSites.size() - 1) {
			System.out.println("***Please select a different value***\n");
			handleMakeReservation();
			
		} else { //if it passes through all the exception cases, we'll get the ACTUAL siteID from our List and assign it
			result = availableSites.get(selectedSite).getSiteId();
		}
		return result;
	}
	
	private void handleGetAvailableSites() {
		System.out.println("\nResults Matching Your Search Criteria");
		availableSites = siteDAO.getAvailableSitesByReservationDate(selectedCampgroundId, arrival, departure);
		
		BigDecimal days = new BigDecimal((int)ChronoUnit.DAYS.between(arrival,departure));
		
		System.out.println(String.format("%-12s%-14s%-12s%15s%14s%10s", "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost"));
		
		String trueOrFalse = "";
		String rvLength = "";
		String utility = "";
		String sumCost = "";
		int count = 1;
		
		for (Site site : availableSites) {
			BigDecimal sumFee = site.getDailyFee().multiply(days);
			if (site.isAccessible()) {
				trueOrFalse = "Yes";
			} else {
				trueOrFalse = "No";
			}
			
			if (site.getMaxRvLength() == 0) {
				rvLength = "N/A";
			} else {
				rvLength = Integer.toString(site.getMaxRvLength());
			}
			if (site.isHasUtilities()) {
				utility = "Yes";
			} else {
				utility = "N/A";
			}
			sumCost = "$" + Double.toString(sumFee.doubleValue()) + "0";
					
			System.out.println(String.format("%-12d%-14s%-14s%-20s%-13s%1s",
					count++, site.getMax(), trueOrFalse, 
					rvLength, utility, sumCost));
		}
		if (availableSites.size() == 0) {
			handleEnterAlternateDateRange();
		}
		handleMakeReservation();
	}
	
	private void handleEnterAlternateDateRange() {
		System.out.println("***Sorry! No sites available for this timeframe!***\n Would you like to try different dates? (Y)es, (N)o");
		String yesOrNo = menu.getUserInput();
		
		if (yesOrNo.toUpperCase().equals("Y")) {
			catchAllDateExceptionsBeforeUsingThemForSearch();
			
		} else if (yesOrNo.toUpperCase().equals("N")) {
			System.out.println("***Cancelling operation, returning to Camp Menu***\n");
			switchToCampgroundMenu();
			
		} else {
			System.out.println("***Invalid entry, please try again.***\n");
			handleEnterAlternateDateRange();
		}
	}
	
	private void handleMakeReservation() {
		System.out.print("\nWhich site you'd like to make a reservation for (enter 0 to cancel)? >>> ");
		String stringSiteNumber = menu.getUserInput();
		
		selectedSiteId = handleSiteExceptionsAndGetSelectedSiteId (stringSiteNumber);
		
		System.out.print("What name should the reservation be made under? >>> ");
		String reservationName = menu.getUserInput();
		
		long customerReservationId = reservationDAO.makeReservation(selectedSiteId, reservationName, arrival, departure);
		
		System.out.println("\nThe reservation has been made and the confirmation id is: " + customerReservationId + "\n\n");
		run();
	}
	
	private void viewCampgroundsByParkId(long park_id, Park selectedPark) {
		
		campgroundsByParkId = campgroundDAO.getCampgroundByParkId(park_id);
		System.out.print("     ");
		System.out.println(String.format("%-35s%-9s%10s%20s", "Name", "Open", "Close", "Daily Fee"));
		
		int count = 1;
		
		for (Campground campground : campgroundsByParkId) {
			String price = "$" + String.format("%.2f", campground.getDailyFee());
			String counttoString = "#" + count++;
			System.out.println(String.format("%-5s%-35s%-12s%10s%15s",
					counttoString, campground.getName(), getMonth(campground.getOpenFrom()), 
					getMonth(campground.getOpenTo()), price));
		}
		System.out.println("");
		
	}
	
	public String getMonth (int month) {
		return new DateFormatSymbols().getMonths()[month-1];
	}
	
	private long handleParkChoice(String preChoice, List<Park> results) {
		int numChoice = 0;
		String strChoice = "";
		long parkInfoSearch = 0;
		
		try {
			numChoice = Integer.parseInt(preChoice);
			
			for (Park park : results) {
				if ((long)numChoice == park.getParkId()) {
					parkInfoSearch = park.getParkId();
				} 
			}
			
			if (parkInfoSearch == 0) {
				System.out.println("\n*** " + preChoice + " is not a valid option ***");
				handleParks();
			}
			
		} catch (NumberFormatException e) {
			strChoice = preChoice.toUpperCase();
			
			if (strChoice.equals("Q")) {
				System.exit(0);
			} else {
				System.out.println("\n*** " + strChoice + " is not a valid option ***");
				handleParks();
			}
		}
		return numChoice;
	}
	
	private void displayParkInfo(long park_id, Park thePark) {
		
		System.out.println("\nPark Information Screen");
		System.out.println(thePark.getName() + " National Park");
		System.out.println(String.format("%-10s%12s", "Location:", thePark.getLocation()));
		System.out.println(String.format("%-10s%15s", "Established:", thePark.getEstablishDate()));
		System.out.println(String.format("%-10s%12d", "Area:", thePark.getArea()) + " sq km");
		System.out.println(String.format("%-10s%8d", "Annual Visitors:", thePark.getVisitors()));
		System.out.println("");
		

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



//package com.techelevator;
//
//import java.awt.Menu;
//
//import javax.sql.DataSource;
//
//import org.apache.commons.dbcp2.BasicDataSource;
//
//public class CampgroundCLI {
//	
//	
//	private static final String MAIN_MENU_OPTION_PARKS = "Parks Interface";
//	
//	private static final String[] MAIN_MENU_OPTIONS_MENU = new String[] { MAIN_MENU_OPTION_PARKS};
//	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
//	private Menu menu;
//	private ParksDAO parksDAO;
//	private CampgroundsDAO campgroundsDAO;
//	private SiteTablesDAO siteTablesDAO;
//	private ReservationsDAO reservationsDAO;
//
//	public static void main(String[] args) {
//		
//		CampgroundCLI application = new CampgroundCLI();
//		application.run();
//		
//	}
//
//	public CampgroundCLI() {
//		
//		
////		private static final String[] MAIN_MENU_OPTIONS = new String[] {  
////				 MAIN_MENU_OPTION_EXIT };
////	
//		
//		//this.menu = new Menu(System.in, System.out);
//		
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("postgres1");
//		
//		parksDAO = new JDBCParksDAO(dataSource);
//		campgroundsDAO = new JDBCCampgroundsDAO(dataSource);
//		siteTablesDAO = new JDBCSiteTablesDAO(dataSource);
//		reservationsDAO = new JDBCReservationsDAO(dataSource);
//		
//	}
//
//	public void run() {
//		displayApplicationBanner();	
//		while(true) {
//			System.out.println("Main Menu");
//			String choice = (String)menu.getChoiceFromOptions();
//		
//	}
//}
