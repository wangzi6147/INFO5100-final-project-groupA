package dto;

public class Special {
    private String id; //auto generated
    private String dealerID;  // who is giving such special offer.
    private String startDate; // by default it's today
    private String endDate;
    private String title;
    private String description;
    private String disclaimer;
    private String value;
    private SpecialScope scope;
    private String scopeParameter;


    public Special(String dealerID, String endDate, String title, String value, SpecialScope scope, String scopeParameter) {

        this.dealerID = dealerID;
        this.endDate = endDate;
        this.title = title;
        this.value = value;
        this.scopeParameter = scopeParameter;
        this.scope = scope;
    }

    public void setId(String id){ this.id = id; }

    public String getId() {
        return id;
    }

    public String getDealerID() {
        return dealerID;
    }

    public void setDealerID(String dealerID) { this.dealerID = dealerID; }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SpecialScope getScope() { return scope; }

    public void setScope(SpecialScope scope){ this.scope = scope; }

    public String getScopeParameter(){ return scopeParameter;    }

    public void setScopeParameter(String scopeParameter){ this.scopeParameter = scopeParameter;}

}
