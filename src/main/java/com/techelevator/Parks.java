package com.techelevator;

import java.time.LocalDate;
import java.util.List;

public class Parks implements ParksDAO{
	
	private Long id;
	private String name;
	private String location;
	private LocalDate establishDate;
	private Long area;
	private int visitors;
	private String description;
	
	@Override
	public List<Parks> findAllParks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parks> listParkInfoById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
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
	
	public LocalDate establishDate() {
		return establishDate;
	}
	
	public void setEstablishDate(LocalDate establishDate) {
		this.establishDate = establishDate;
	}
	
	public Long getArea() {
		return area;
	}
	
	public void setArea(Long area) {
		this.area = area;
	}
	
	public int getVisitors() {
		return visitors;
	}
	
	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public List<Parks> listParkInfoByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}


}
