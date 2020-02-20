package com.techelevator;

import java.util.List;


public interface ParksDAO {
	
	public List<Parks> findAllParks();
	
	public List<Parks> listParkInfoById(Long id);
	
	

}
