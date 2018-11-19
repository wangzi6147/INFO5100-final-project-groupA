package dto;
import java.util.*;

public class VehicleFilterSelected {

    private String dealerID; // must have
    private List<String> years;
    private List<String> brand;
    private List<String> model;
    private List<Boolean> isNew; // {true} {false} {}
    private List<String> price;
    private List<String> exteriorColor;
    private List<String>  interiorColor;
    private List<BodyType> bodyType;
    private List<String>  miles;

    private int pageNumber; // must have
    private SortType sortType;

    public VehicleFilterSelected(String dealerID){
    	this.dealerID=dealerID;
    	this.pageNumber=1;
    }


    public String getDealerID() {
        return dealerID;
    }

    public void setDealerID(String dealerID) {
        this.dealerID = dealerID;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public List<String> getBrand() {
        return brand;
    }

    public void setBrand(List<String> brand) {
        this.brand = brand;
    }

    public List<String> getModel() {
        return model;
    }

    public void setModel(List<String> model) {
        this.model = model;
    }

    public List<Boolean> getIsNew() {
        return isNew;
    }

    public void setIsNew(List<Boolean> isNew) {
        this.isNew = isNew;
    }

    public List<String> getPrice() {
        return price;
    }

    public void setPrice(List<String> price) {
        this.price = price;
    }

    public List<String> getExteriorColor() {
        return exteriorColor;
    }

    public void setExteriorColor(List<String> exteriorColor) {
        this.exteriorColor = exteriorColor;
    }

    public List<String> getInteriorColor() {
        return interiorColor;
    }

    public void setInteriorColor(List<String> interiorColor) {
        this.interiorColor = interiorColor;
    }

    public List<BodyType> getBodyType() {
        return bodyType;
    }

    public void setBodyType(List<BodyType> bodyType) {
        this.bodyType = bodyType;
    }

    public List<String> getMiles() {
        return miles;
    }

    public void setMiles(List<String> miles) {
        this.miles = miles;
    }
}
