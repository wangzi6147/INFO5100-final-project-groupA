package dao;

import dto.DealerQueryResponse;

import java.sql.SQLException;

/**
 * @author Arya Soman
 */
public interface DealerManager {
    DealerQueryResponse searchDealers(int postCode, int lines) throws SQLException;

    DealerQueryResponse searchDealers(String dealerName, String city, int pageNumber) throws SQLException;
}
