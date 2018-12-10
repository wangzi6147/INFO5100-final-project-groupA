package service;
import dao.DealerManagerImplementation;
import dto.DealerQueryResponse;

import java.sql.SQLException;

public class DealerServiceImpl implements DealerService {

    private DealerManagerImplementation dealerManagerImplementation;

    public DealerServiceImpl() {
        this.dealerManagerImplementation = new DealerManagerImplementation();
    }

    public DealerQueryResponse getDealerList(String dealerName, String city, int pageNumber) throws SQLException {
        return dealerManagerImplementation.searchDealers(dealerName, city, pageNumber);
    }


}
