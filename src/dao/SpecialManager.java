package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dto.Special;

public interface SpecialManager {
	
	Special getSpecialByID(String id) throws SQLException;
	
	List<Special> getValidSpecialsByDealerID(String dealerID) throws SQLException;
	
	List<Special> getAllSpecialsByDealerID(String dealerID) throws SQLException;
	
	List<Special> getAllSpecials() throws SQLException;
	
	Special createSpecialFromRS(ResultSet rs) throws SQLException;
	
	void addSpecial(Special s);
	
	void removeSpecial(Special s);
	
	void removeSpecial(String id);
}
