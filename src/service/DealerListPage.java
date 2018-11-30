package service;
import dao.DealerQuery;
import dto.DealerQueryResponse;

public class DealerListPage implements DealerListService {

    private DealerQuery dealerQuery;

    public DealerListPage() {
        this.dealerQuery = new DealerQuery();
    }

    public DealerQueryResponse getDealerList(String dealerName, String city, int pageNumber){
        return dealerQuery.findDealersByNameAndCityWithPageNumber(dealerName, city, pageNumber);
    }


}
