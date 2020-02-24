package com.techelevator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;
import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;

public class ParksCLI {

	Scanner userInput = new Scanner(System.in);
	String siteTableInput = "";
	LocalDate arrivalDateInput;
	LocalDate departureDateInput;
	
	private static final String MENU_OPTION_EXIT = "Quit";
	private static final String MENU_OPTION_RETURN_TO_PREVIOUS = "Return to previous screen";
	
	private static final String CAMPGROUNDS_IN_PARK = "View Campgrounds";
	private static final String SEARCH_FOR_RESERVATION = "Search for Reservation";
	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] { CAMPGROUNDS_IN_PARK,
																		   SEARCH_FOR_RESERVATION,
																		   MENU_OPTION_RETURN_TO_PREVIOUS};
	
	private static final String CAMPSITES_IN_CAMPGROUND = "Show all campsites";
	private static final String CAMPSITE_SEARCH_BY_CAMPGROUND = "campsite search by campground";
//	private static final String PARK_MENU_OPTION_SEARCH_BY_DATE = "campground search by date";
//	private static final String PARK_MENU_OPTION_SEARCH_BY_SITE = "campground search by site";
	private static final String[] CAMPSITE_MENU_OPTIONS = new String[] { CAMPSITES_IN_CAMPGROUND,
																		 CAMPSITE_SEARCH_BY_CAMPGROUND,
//																		 PARK_MENU_OPTION_SEARCH_BY_DATE,
//																		 PARK_MENU_OPTION_SEARCH_BY_SITE,
																	   	 MENU_OPTION_RETURN_TO_PREVIOUS};
	
	
	
	
//	
	
	private Menu menu;
	private ParksDAO parksDAO;
	private CampgroundsDAO campgroundsDAO;
	private SiteTablesDAO siteTablesDAO;
	private ReservationsDAO reservationsDAO;

	private List<String> parkList = new ArrayList<String>();
	private List<String> campgroundList = new ArrayList<String>();
	private List<Integer> siteTableList = new ArrayList<Integer>();
	
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

	public void run() {
		displayParksBanner();	

		handleListAllParks();
		String[] parkArray = new String[parkList.size() + 1]; 
		parkList.toArray(parkArray);
		parkArray[parkArray.length -1] = MENU_OPTION_EXIT;
		String choiceP = new String();
		String choiceC = new String();
		String choiceS = new String();
		long parkId = 0;
		long campgroundId = 0;
				
		int i = 0;
		while(!(choiceP.equals(MENU_OPTION_EXIT))) {
/*pick park*/choiceP = (String)menu.getChoiceFromOptions(parkArray);  // pick park
			for (i = 0; i < parkArray.length-1; i++) {
				if(choiceP.equals(parkArray[i])) {
					parkId = handleGetAllParkInfoByName(parkArray[i]);
							
/*pick campground*/	choiceC = (String)(menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS));  //pick campground
				
					while(!(choiceC.equals(MENU_OPTION_RETURN_TO_PREVIOUS))) {
					
						if(choiceC.equals(CAMPGROUNDS_IN_PARK)) {
							handleGetAllCampgroundInfo(parkId);
							String[] campgroundArray = new String[campgroundList.size()]; 
							campgroundList.toArray(campgroundArray);

							choiceS = (String)menu.getChoiceFromOptions(campgroundArray);
							
							if(choiceS.equals(CAMPSITES_IN_CAMPGROUND)) {
								
								handleListAllSiteTables(campgroundId);
								Integer[] siteTableArray = new Integer[siteTableList.size()];
								siteTableList.toArray(siteTableArray);
								if(choiceS.contentEquals(CAMPSITE_SEARCH_BY_CAMPGROUND)) {
									System.out.println("Which campground (enter 0 to cancel)? __ ");
									siteTableInput = userInput.nextLine();
									System.out.println("What is the arrival Date? __/__/____");
									arrivalDateInput = LocalDate.parse(userInput.nextLine());
									System.out.println("What is the departure Date? __/__/____");								}
									departureDateInput = LocalDate.parse(userInput.nextLine());

						} else if(choiceC.equals(MENU_OPTION_RETURN_TO_PREVIOUS)) {
							break;
						}
					}
				} 	if (choiceC.equals(MENU_OPTION_RETURN_TO_PREVIOUS)) {
					break;
				}

			} if(choiceP.equals(MENU_OPTION_EXIT)) {
					printHeading("So Long, and Thnx 4 4ll da Fish.");
//					System.exit(0);
			}	
			}
//			else if(choiceP.equals(MENU_OPTION_RETURN_TO_PREVIOUS)) {
		}
	}
	
	
	public void handleListAllSiteTables(long campgroundId) {
		printHeading("Select a SiteTable for further details:");
		List<SiteTables> allSiteTables = siteTablesDAO.findAllSiteTablesOfThisCampground(campgroundId);
		if(allSiteTables.size() > 0) {
			int i = 0;
			for(SiteTables siteTable : allSiteTables) {
				 siteTableList.add(siteTable.getNumber());
				i++;
			}
		}
	}
	
	public void handleAllSiteTablesByCampgroundId(Long campgroundId, BigDecimal dailyFee ) {
		printHeading("-- sitetables by campground id --");
		List<SiteTables> allSitetablesByCampgroundId = siteTablesDAO.findAllSiteTablesOfThisCampground(campgroundId);
		if(allSitetablesByCampgroundId.size() > 0) {
			for(SiteTables sitetable : allSitetablesByCampgroundId) {
				if (campgroundId.equals(sitetable.getId())) {
					System.out.println(sitetable.getNumber());
					System.out.println(sitetable.getMaxOccupancy());
					System.out.println(sitetable.isIfHandicapAccessible());
					System.out.println(sitetable.isIfUtilitiesAccess());
					System.out.println(sitetable.getMaxRvLength());
					System.out.println(dailyFee);
							
				}
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

	public long handleGetAllParkInfoByName(String name) {
		printHeading("-- parks by id --");
		List<Parks> allParks = parksDAO.listParkInfoByName(name);
		long parkId = 0;
		if(allParks.size() > 0) {
			for(Parks park : allParks) {
				if (name.equals(park.getName())) {
					System.out.println(park.getName() + "National Park");
					System.out.println("Location" + "\t\t" + park.getLocation());
	//				System.out.println(park.establishDate().toLocalDate());
					System.out.println("Area:" + "\t\t" + park.getArea().toString());
	//				System.out.println(park.getVisitors().toString());
					System.out.println(park.getDescription());
					parkId = park.getId();
				}
			}
		}
		return parkId;
		
	}
	
//	public void handleListAllCampground(long parkId) {
//		printHeading("Select a Campground for further details:");
//		List<Campgrounds> allCampgrounds = campgroundsDAO.findAllCampgroundsByParkId(parkId);
//		if(allCampgrounds.size() > 0) {
//			int i = 0;
//			for(Campgrounds campground : allCampgrounds) {
//				campgroundList.add(campground.getName());
//				i++;
//			}
//		}
//	}

	public long handleGetAllCampgroundInfo(Long id) {
		printHeading("-- campground by park_name(?) --");
		List<Campgrounds> allCampgrounds = campgroundsDAO.findAllCampgroundsByParkId(id);
		long campgroundId = 0;
		if(allCampgrounds.size() > 0) {
			int i = 1;
			for(Campgrounds campground : allCampgrounds) {
				if (id.equals(campground.getId())) {
					System.out.println(i + ") " + campground.getName() + "\t\t" + 
					campground.getOpenFromMonth() + "\t\t" + 
					campground.getOpenToMonth() + "\t\t" +
					campground.getDailyFee());
					campgroundId = campground.getId();
					
					campgroundList.add(campground.getName());
					
//					campgroundList.add(campground.getOpenFromMonth());
//					campgroundList.add(campground.getOpenToMonth());
//					campgroundList.add(campground.getDailyFee());
					
					i++;
				}
			}
		}
		return campgroundId;
	}
	

	public void listParkInfo(List<Parks> parkList, String name) {
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
	
	public void printHeading(String headingText) {
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