package service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import dto.Special;
import dto.Vehicle;

public interface SpecialService {
	//add special to the vehicles that match it, return the number of vehicles match this adding special
	int addSpecial(Special special, List<Vehicle> vehicles);
	
	//delete special to the vehicles that match it, return the number of vehicles match this deleting special
	int deleteSpecial(Special special, List<Vehicle> vehicles);
	
	//search the special by id in database
	Special getSpecialBySpecialID(String id);
	
	//associate a list of Specials to a list of vehicles, and then return assigned vehicles
	List<Vehicle> associateSpecials(List<Vehicle> vehicles, List<Special> specials) throws ParseException;
	
	//get the whole specials, according to the dealerID to query specials that match to the dealerID
	List<Special> querySpecials(List<Special> specials, String dealerID);
	
	List<Special> getAllSpecialsByDealerID(String dealerID) throws SQLException;
}
