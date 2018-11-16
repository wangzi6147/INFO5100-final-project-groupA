package dto;

import java.io.Serializable;
import java.util.ArrayList;

public class Vehicle implements Serializable {
    private String id;
    private String dealerName;
    private String year;
    private String make;
    private String model;
    private boolean type;   // true for new , false for used
    private String price;
    private String exteriorColor;
    private String interiorColor;
    private BodyType bodyType;
    private ArrayList<String> features;
    private String miles;
//    private ArrayList<VehicleImage> images;
//    private ArrayList<Special> specials;

    public Vehicle(String id, String dealerName, String year, String make, String model, boolean type, String price, String exteriorColor, String interiorColor, BodyType bodyType, ArrayList<String> features, String miles) {
        this.id = id;
        this.dealerName = dealerName;
        this.year = year;
        this.make = make;
        this.model = model;
        this.type = type;
        this.price = price;
        this.exteriorColor = exteriorColor;
        this.interiorColor = interiorColor;
        this.bodyType = bodyType;
        this.features = features;
        this.miles = miles;
//        this.images = images;
//        this.specials = specials;
    }

    public String getId() {
        return id;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExteriorColor() {
        return exteriorColor;
    }

    public void setExteriorColor(String exteriorColor) {
        this.exteriorColor = exteriorColor;
    }

    public String getInteriorColor() {
        return interiorColor;
    }

    public void setInteriorColor(String interiorColor) {
        this.interiorColor = interiorColor;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public ArrayList<String> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = features;
    }

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    // ---------- Body Type Class -----------
    public enum BodyType {
        VAN, SUV, Jeep
    }

    public static class VehicleImage implements Serializable {
        private String id;
        private String url;

        public VehicleImage(String id, String url) {
            this.id = id;
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
