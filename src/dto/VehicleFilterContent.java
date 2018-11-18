package dto;
import java.util.List;

public class VehicleFilterContent {


    private String dealerID;
    private List<String> years;
    private List<String> brand;
    private List<String> model;
    private List<Boolean> isNew;   // {true} {false} {true, false} {}
    private List<String> price;
    private List<String> exteriorColor;
    private List<String>  interiorColor;
    private List<BodyType> bodyType;
    private List<String>  miles;

    public String getDealerID() {
        return dealerID;
    }

    public List<String> getYears() {
        return years;
    }

    public List<String> getBrand() {
        return brand;
    }

    public List<String> getModel() {
        return model;
    }

    public List<Boolean> getIsNew() {
        return isNew;
    }

    public List<String> getPrice() {
        return price;
    }

    public List<String> getExteriorColor() {
        return exteriorColor;
    }

    public List<String> getInteriorColor() {
        return interiorColor;
    }

    public List<BodyType> getBodyType() {
        return bodyType;
    }

    public List<String> getMiles() {
        return miles;
    }

    public void setDealerID(String dealerID) {
        this.dealerID = dealerID;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public void setBrand(List<String> brand) {
        this.brand = brand;
    }

    public void setModel(List<String> model) {
        this.model = model;
    }

    public void setIsNew(List<Boolean> isNew) {
        this.isNew = isNew;
    }

    public void setPrice(List<String> price) {
        this.price = price;
    }

    public void setExteriorColor(List<String> exteriorColor) {
        this.exteriorColor = exteriorColor;
    }

    public void setInteriorColor(List<String> interiorColor) {
        this.interiorColor = interiorColor;
    }

    public void setBodyType(List<BodyType> bodyType) {
        this.bodyType = bodyType;
    }

    public void setMiles(List<String> miles) {
        this.miles = miles;
    }

}
