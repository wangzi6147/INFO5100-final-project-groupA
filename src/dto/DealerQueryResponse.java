package dto;

import java.util.List;

public class DealerQueryResponse {
    private List<Dealer> dealerList;
    private int totalPageNumber;

    public DealerQueryResponse(List<Dealer> dealerList, int totalPageNumber) {
        this.dealerList = dealerList;
        this.totalPageNumber = totalPageNumber;
    }

    public List<Dealer> getDealerList() {
        return dealerList;
    }

    public int getTotalPageNumber() {
        return totalPageNumber;
    }
}
