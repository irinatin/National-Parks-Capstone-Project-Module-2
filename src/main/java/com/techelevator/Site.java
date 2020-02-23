package com.techelevator;

import java.math.BigDecimal;

public class Site {
	
	private long siteId;
	private long campgroundId;
	private int siteNumber;
	private int maxOccupancy;
	private boolean isAccessible;
	private int maxRvLength;
	private boolean hasUtilities;
	private long campgroundId2;
	private long parkId;
	private String name;
	private int openFrom;
	private int openTo;
	private BigDecimal dailyFee;

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public long getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}

	public int getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}

	public int getMax() {
		return maxOccupancy;
	}

	public void setMax(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	public boolean isAccessible() {
		return isAccessible;
	}

	public void setAccessible(boolean isAccessible) {
		this.isAccessible = isAccessible;
	}

	public int getMaxRvLength() {
		return maxRvLength;
	}

	public void setMaxRvLength(int maxRvLength) {
		this.maxRvLength = maxRvLength;
	}

	public boolean isHasUtilities() {
		return hasUtilities;
	}

	public void setHasUtilities(boolean hasUtilities) {
		this.hasUtilities = hasUtilities;
	}

	public long getCampgroundId2() {
		return campgroundId2;
	}

	public void setCampgroundId2(long campgroundId2) {
		this.campgroundId2 = campgroundId2;
	}

	public long getParkId() {
		return parkId;
	}

	public void setParkId(long parkId) {
		this.parkId = parkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOpenFrom() {
		return openFrom;
	}

	public void setOpenFrom(int openFrom) {
		this.openFrom = openFrom;
	}

	public int getOpenTo() {
		return openTo;
	}

	public void setOpenTo(int openTo) {
		this.openTo = openTo;
	}

	public BigDecimal getDailyFee() {
		return dailyFee;
	}

	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}

	public String toString() {
		return "this is a site";
	}

}
