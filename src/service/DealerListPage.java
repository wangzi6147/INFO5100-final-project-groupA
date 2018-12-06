package service;
import dao.DealerManagerImpl;
import dto.DealerQueryResponse;

import java.sql.SQLException;

public class DealerListPage implements DealerListService {

    private DealerManagerImpl dealerManagerImpl;

    public DealerListPage() {
        this.dealerManagerImpl = new DealerManagerImpl();
    }

    public DealerQueryResponse getDealerList(String dealerName, String city, int pageNumber) throws SQLException {
        return dealerManagerImpl.searchDealers(dealerName, city, pageNumber);
    }


}
