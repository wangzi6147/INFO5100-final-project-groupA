package dto;

import java.util.ArrayList;

public class Special {
    private String id;
    private String startDate;
    private String endDate;
    private String title;
    private String description;
    private String disclaimer;
    private String value;

    public Special(String id, String startDate, String endDate, String title, String description, String disclaimer, String value) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
        this.disclaimer = disclaimer;
        this.value = value;
    }

    public String getId() {
        return id;
    }

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

    // ------------ VehicleCriterion Class ----------------------------------------
    public static class VehicleCriterion {
        private String make;
        private String model;
        private String year;
        private String minPrice;
        private String maxPrice;
        private ArrayList<String> vehicleIds;

        public VehicleCriterion(String make, String model, String year, String minPrice, String maxPrice, ArrayList<String> vehicleIds) {
            this.make = make;
            this.model = model;
            this.year = year;
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
            this.vehicleIds = vehicleIds;
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

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(String minPrice) {
            this.minPrice = minPrice;
        }

        public String getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(String maxPrice) {
            this.maxPrice = maxPrice;
        }

        public ArrayList<String> getVehicleIds() {
            return vehicleIds;
        }

        public void setVehicleIds(ArrayList<String> vehicleIds) {
            this.vehicleIds = vehicleIds;
        }
    }
}
