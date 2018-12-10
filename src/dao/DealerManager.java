package dao;

import java.sql.SQLException;
import dto.*;

/**
 * @author Arya Soman
 * DealerManager Interface with search Dealer methods
 *
 */
public interface DealerManager {

	public DealerQueryResponse searchDealers(int postCode,int lines) throws SQLException;
	public DealerQueryResponse searchDealers(String dealerName, String city, int pageNumber) throws SQLException;

}
