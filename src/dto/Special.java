package dto;

public class Special {
    private String id; //auto generated
    private String dealerID;  //
    private String startDate;
    private String endDate;
    private String title;
    private String description;
    private String disclaimer;
    private SpecialScope scope;
    private String scopeParameter;
    private boolean isMutex;
    private String value;
    private ValueType valueType;


    public Special(String dealerID, String endDate, String title, SpecialScope scope, String scopeParameter,
                   boolean isMutex, String value, ValueType valueType) {

        this.dealerID = dealerID;
        this.endDate = endDate;
        this.title = title;
        this.scopeParameter = scopeParameter;
        this.scope = scope;
        this.isMutex = isMutex;
        this.value = value;
        this.valueType = valueType;
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

    public boolean isMutex() {
        return isMutex;
    }

    public void setMutex(boolean mutex) {
        isMutex = mutex;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }
}
