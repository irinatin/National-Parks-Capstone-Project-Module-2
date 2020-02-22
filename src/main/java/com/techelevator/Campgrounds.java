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
	





	public String opentFromToString (int openFromMonth) {
		String openFromMonthString = "";
		if(openFromMonth == 1) {
			openFromMonthString = "January";
		}
		if(openFromMonth == 2) {
			openFromMonthString = "February";
		}
		if(openFromMonth == 3) {
			openFromMonthString = "March";
		}
		if(openFromMonth == 4) {
			openFromMonthString = "April";
		}
		if(openFromMonth == 5) {
			openFromMonthString = "May";
		}
		if(openFromMonth == 6) {
			openFromMonthString = "June";
		}
		if(openFromMonth == 7) {
			openFromMonthString = "July";
		}
		if(openFromMonth == 8) {
			openFromMonthString = "August";
		}
		if(openFromMonth == 9) {
			openFromMonthString = "September";
		}
		if(openFromMonth == 10) {
			openFromMonthString = "October";
		}
		if(openFromMonth == 11) {
			openFromMonthString = "November";
		}
		if(openFromMonth == 12) {
			openFromMonthString = "December";
		}
		return openFromMonthString;
	}
	
	public String closeToString(int openToMonth) {
		String closeMonthString = "";
		if(openFromMonth == 1) {
			closeMonthString = "January";
		}
		if(openFromMonth == 2) {
			closeMonthString = "February";
		}
		if(openFromMonth == 3) {
			closeMonthString = "March";
		}
		if(openFromMonth == 4) {
			closeMonthString = "April";
		}
		if(openFromMonth == 5) {
			closeMonthString = "May";
		}
		if(openFromMonth == 6) {
			closeMonthString = "June";
		}
		if(openFromMonth == 7) {
			closeMonthString = "July";
		}
		if(openFromMonth == 8) {
			closeMonthString = "August";
		}
		if(openFromMonth == 9) {
			closeMonthString = "September";
		}
		if(openFromMonth == 10) {
			closeMonthString = "October";
		}
		if(openFromMonth == 11) {
			closeMonthString = "November";
		}
		if(openFromMonth == 12) {
			closeMonthString = "December";
		}
		return closeMonthString;
	}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	