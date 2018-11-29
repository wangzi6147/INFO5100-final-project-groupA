package ui.vehicle.panel;

import dto.BodyType;
import dto.VehicleFilterContent;
import dto.VehicleFilterSelected;
import service.VehicleListPage;
import ui.Setting;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchVehicleButtonPanel extends JPanel {

    private static VehicleListPage vehicleListPageService;
    public static VehicleFilterSelected vehicleFilterSelected;
    private int indexFlag = 0;

    // for new SearchVehicleButtonPanel, this method will clear all contains.
    public SearchVehicleButtonPanel(String dealerId) {

        vehicleFilterSelected = new VehicleFilterSelected(dealerId);
        vehicleListPageService = new VehicleListPage();
        //this.vehicleListPageService.Query(vehicleFilterSelected);
        SingleFilterBlockPanel.refresh();
        initialize("All");
    }

    // for JCheckBox ActionListener, this method will refresh part of contains.
    public void refresh(String name) {
        //this.vehicleListPageService.Query(vehicleFilterSelected);
        initialize(name);
    }

    private void initialize(String name) {
        vehicleListPageService.Query(vehicleFilterSelected);
        VehicleFilterContent vehicleFilterContent = vehicleListPageService.getFilterContent();
        SearchVehiclePanel.totalPageNumber = vehicleListPageService.getPageCount();
        SingleFilterBlockPanel.totalHeight = 0;

        int refreshMethod = 1;
        if (refreshMethod == 1) {
            // refresh content after giving name.
            switch (name) {
                case "All":
                    this.removeAll();
                    List<String> isNew = vehicleFilterContent.getIsNew();
                    SingleFilterBlockPanel category = new SingleFilterBlockPanel("Category", isNew);
                    this.add(category);
                case "Category":
                    List<String> makeList = vehicleFilterContent.getBrand();
                    SingleFilterBlockPanel make = new SingleFilterBlockPanel("Make", makeList);
                    clear(name, 1);//1
                    this.add(make);
                case "Make":
                    List<String> modelList = vehicleFilterContent.getModel();
                    SingleFilterBlockPanel model = new SingleFilterBlockPanel("Model", modelList);
                    clear(name, 2);
                    this.add(model);
                case "Model":
                    List<String> priceList = vehicleFilterContent.getPrice();
                    SingleFilterBlockPanel price = new SingleFilterBlockPanel("Price", priceList);
                    clear(name, 3);
                    this.add(price);
                case "Price":
                    List<String> yearList = vehicleFilterContent.getYears();
                    SingleFilterBlockPanel year = new SingleFilterBlockPanel("Year", yearList);
                    clear(name, 4);
                    this.add(year);
                case "Year":
                    List<String> bodyTypeList = vehicleFilterContent.getBodyType();
                    SingleFilterBlockPanel bodyType = new SingleFilterBlockPanel("Body Type", bodyTypeList);
                    clear(name, 5);
                    this.add(bodyType);
                case "Body Type":
                    List<String> exteriorColorList = vehicleFilterContent.getExteriorColor();
                    SingleFilterBlockPanel exteriorColor = new SingleFilterBlockPanel("Exterior", exteriorColorList);
                    clear(name, 6);
                    this.add(exteriorColor);
                case "Exterior":
                    List<String> interiorColorList = vehicleFilterContent.getInteriorColor();
                    SingleFilterBlockPanel interiorColor = new SingleFilterBlockPanel("Interior", interiorColorList);
                    clear(name, 7);
                    this.add(interiorColor);
                default:
                    indexFlag = 0;
                    reload(name);
            }
        } else if (refreshMethod == 2) {
            if (!"Category".equals(name)) {
                List<String> isNew = vehicleFilterContent.getIsNew();
                SingleFilterBlockPanel category = new SingleFilterBlockPanel("Category", isNew);
                this.remove(0);
                this.add(category, 0);
            }

            List<String> makeList = vehicleFilterContent.getBrand();
            SingleFilterBlockPanel make = new SingleFilterBlockPanel("Make", makeList);
            List<String> modelList = vehicleFilterContent.getModel();
            SingleFilterBlockPanel model = new SingleFilterBlockPanel("Model", modelList);
            List<String> priceList = vehicleFilterContent.getPrice();
            SingleFilterBlockPanel price = new SingleFilterBlockPanel("Price", priceList);
            List<String> yearList = vehicleFilterContent.getYears();
            SingleFilterBlockPanel year = new SingleFilterBlockPanel("Year", yearList);
            List<String> bodyTypeList = vehicleFilterContent.getBodyType();
            SingleFilterBlockPanel bodyType = new SingleFilterBlockPanel("Body Type", bodyTypeList);
            List<String> exteriorColorList = vehicleFilterContent.getExteriorColor();
            SingleFilterBlockPanel exteriorColor = new SingleFilterBlockPanel("Exterior", exteriorColorList);
            List<String> interiorColorList = vehicleFilterContent.getInteriorColor();
            SingleFilterBlockPanel interiorColor = new SingleFilterBlockPanel("Interior", interiorColorList);


            this.add(make);
            this.add(model);
            this.add(price);
            this.add(year);
            this.add(bodyType);
            this.add(exteriorColor);
            this.add(interiorColor);
        }

        //this.setLayout(new GridLayout(8, 1));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(Setting.VEHICLE_SEARCH_BUTTON_PANEL_WIDTH, Math.max(SingleFilterBlockPanel.totalHeight, Setting.VEHICLE_SEARCH_BUTTON_PANEL_HEIGHT)));


    }

    private void clear(String name, int index) {
        if (indexFlag == 0)
            indexFlag = index;
        else
            index = indexFlag;
        if (!"All".equals(name)) {
            this.remove(index);
        }
    }

    private void reload(String name) {
        if (!"All".equals(name)) {
            this.revalidate();
            this.repaint();
            this.updateUI();
        } else {
            this.revalidate();
            this.repaint();
            this.updateUI();
        }
    }
}
