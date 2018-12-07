package dao;

import dto.Dealer;
import dto.DealerQueryResponse;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Arya Soman
 *
 */
public interface DealerManager  
{
	/**
	 * Read methods
	 */
	
	List<String> getCityList() throws SQLException;
	Dealer findDealerByID(String id) throws SQLException;
	List<Dealer> findDealersByName(String name) throws SQLException;
	List<String> getAllDealerIDs() throws SQLException;
	int countDealersByCity(String city);
	List<Dealer> findDealersByCity(String city);
	List<Dealer> findDealersByPostCode(int postCode);
	 //public DealerQueryResponse findDealersByPostCodeWithinLines(int postCode,int lines);
	DealerQueryResponse searchDealers(int postCode, int lines) throws SQLException;
	 //public DealerQueryResponse findDealersByNameAndCityWithPageNumber(String dealerName, String city, int pageNumber);
	DealerQueryResponse searchDealers(String dealerName, String city, int pageNumber) throws SQLException;
	 
	 /**
	 * Write methods
	 */
	 void addDealer(Dealer dealer);
	 void deleteDealer(Dealer dealer);
	 void deleteDealer(String dealerID);
	 void modifyDealer(Dealer oldDealer, Dealer newDealer);
}
