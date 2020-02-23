package com.techelevator;

import java.math.BigDecimal;

public class Campground {
	
	private long campgroundId;
	private long parkId;
	private String name;
	private int openFrom;
	private int openTo;
	private BigDecimal dailyFee;

	public Long getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}

	public Long getParkId() {
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
		return name;
	}

}
