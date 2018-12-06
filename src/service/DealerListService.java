package service;

import dto.DealerQueryResponse;

import java.sql.SQLException;

public interface DealerListService {
    DealerQueryResponse getDealerList(String dealerName, String city, int pageNumber) throws SQLException;
}
