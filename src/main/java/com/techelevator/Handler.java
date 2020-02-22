package com.techelevator;

import java.math.BigDecimal;
import java.util.List;


import java.time.LocalDate;
import java.util.ArrayList;

//import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;

public class Handler {
	
	private Menu menu;
	private ParksDAO parksDAO;
	private CampgroundsDAO campgroundsDAO;
	private SiteTablesDAO siteTablesDAO;
	private ReservationsDAO reservationsDAO;

	private List<String> parkList = new ArrayList<String>();
	private List<String> campgroundList = new ArrayList<String>();
	private List<Integer> siteTableList = new ArrayList<Integer>();
	
	
	
	public void handleListAllSiteTables(long campgroundId) {
		printHeading("Select a SiteTable for further details:");
		List<SiteTables> allSiteTables =siteTablesDAO.findAllSiteTablesOfThisCampground(campgroundId);
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

}
