package service;

import dto.DealerQueryResponse;

import java.sql.SQLException;

public interface DealerService {
    DealerQueryResponse getDealerList(String dealerName, String city, int pageNumber) throws SQLException;
}
