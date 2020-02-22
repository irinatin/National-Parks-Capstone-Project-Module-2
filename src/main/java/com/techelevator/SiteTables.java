package com.techelevator;

public class SiteTables {
	
	private Long id;
	private Long campground_id;
	private int number;
	private int maxOccupancy;
	private boolean ifHandicapAccessible;
	private int maxRvLength;
	private boolean ifUtilitiesAccess;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCampgroundId() {
		return campground_id;
	}

	public void setCampgroundId(Long campground_id) {
		this.campground_id = campground_id;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	
	public boolean isIfHandicapAccessible() {
		return ifHandicapAccessible;
	}
	
	public void setIfHandicapAccessible(boolean ifHandicapAccessible) {
		this.ifHandicapAccessible = ifHandicapAccessible;
	}
	
	public int getMaxRvLength() {
		return maxRvLength;
	}
	
	public void setMaxRvLength(int maxRvLength) {
		this.maxRvLength = maxRvLength;
	}
	
	public boolean isIfUtilitiesAccess() {
		return ifUtilitiesAccess;
	}
	
	public void setIfUtilitiesAccess(boolean ifUtilitiesAccess) {
		this.ifUtilitiesAccess = ifUtilitiesAccess;
	}

}
