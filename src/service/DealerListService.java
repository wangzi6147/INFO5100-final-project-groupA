package service;

import dto.DealerQueryResponse;

public interface DealerListService {
    DealerQueryResponse getDealerList(String dealerName, String city, int pageNumber);
}
