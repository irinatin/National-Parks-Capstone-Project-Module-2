package com.techelevator;

import java.math.BigDecimal;

public class Campgrounds {

	private Long id;
	private String name;
	private String location;
	private int openFromMonth;
	private int openToMonth;
	private BigDecimal dailyFee;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getOpenFromMonth() {
		return openFromMonth;
	}
	public void setOpenFromMonth(int openFromMonth) {
		this.openFromMonth = openFromMonth;
	}
	public int getOpenToMonth() {
		return openToMonth;
	}
	public void setOpenToMonth(int openToMonth) {
		this.openToMonth = openToMonth;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	



}
