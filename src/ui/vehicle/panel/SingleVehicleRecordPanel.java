package ui.vehicle.panel;

import dto.Vehicle;
import ui.Setting;
import ui.VehicleSearchMainView;
import ui.label.BeautifulLabel;
import ui.vehicle.ViewUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingleVehicleRecordPanel extends JPanel {
    private ImageIcon imageIcon;
    private BeautifulLabel imageLabel;
    private BeautifulLabel categoryLabel;
    private BeautifulLabel priceLabel;
    private BeautifulLabel milesLabel;
    private BeautifulLabel titleLabel;
    private BeautifulLabel colorLabel;
    private JPanel categoryPanel;
    private JPanel imagePanel;
    private JPanel infoPanel;
    private JPanel priceAndMilesPanel;
    private JPanel titlePanel;
    private JPanel checkVehicleDetailPanel;
    private JButton vehicleDetailReturnButton;


    public SingleVehicleRecordPanel(Vehicle vehicle) {
        createComponent(vehicle);
        setLayOut();
    }

    public String firstToUpper(String str) {
        if (str == null || str.length() == 0) {
            return str;
        } else {
            char[] arr = str.toCharArray();
            arr[0] -= 32;
            return String.valueOf(arr);
        }
    }

    private void setLayOut() {
        this.priceLabel.setFont(Setting.FONT_PRICE);
        this.imagePanel = new JPanel();
        this.imagePanel.add(this.imageLabel);
//        this.imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 20));
        this.imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 30));


        this.categoryPanel = new JPanel();
        this.categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 0));
        this.categoryPanel.add(categoryLabel);


        this.priceAndMilesPanel = new JPanel();
        this.priceAndMilesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 5));
        this.priceAndMilesPanel.add(this.priceLabel);
        this.priceAndMilesPanel.add(this.milesLabel);

        this.titlePanel = new JPanel();
        this.titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 5));
        this.titlePanel.add(titleLabel);
        this.titlePanel.add(colorLabel);


        this.infoPanel = new JPanel();
        this.infoPanel.setLayout(new GridLayout(4, 1));
        this.infoPanel.add(this.categoryPanel);
        this.infoPanel.add(this.priceAndMilesPanel);
        this.infoPanel.add(this.titlePanel);
        //this.infoPanel.add(this.colorLabel);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(20);
        this.setLayout(borderLayout);
        //this.setBackground(Color.white);
        this.setOpaque(true);
        Dimension size = new Dimension(550, 240);
        this.setPreferredSize(size);
        this.add(imagePanel, BorderLayout.WEST);
        this.add(infoPanel, BorderLayout.CENTER);
    }

    private void createComponent(Vehicle vehicle) {
        // create image
        this.imageIcon = ViewUtil.getIcon("src/ui/resources/Cadillac.jpeg", 250, 180);
        this.imageLabel = new BeautifulLabel();
        this.imageLabel.setIcon(this.imageIcon);

        // create category
        if(vehicle.getIsNew()){
            this.categoryLabel = new BeautifulLabel(ViewUtil.getIcon("src/ui/resources/new_stamp.png", 70, 70));

        }else{
            this.categoryLabel = new BeautifulLabel(ViewUtil.getIcon("src/ui/resources/used_stamp.png", 70, 70));
        }

        // create price, mile, title, color label
        this.priceLabel = new BeautifulLabel("$ " + vehicle.getPrice());
        this.milesLabel = new BeautifulLabel(vehicle.getMiles() + " Miles");
        this.titleLabel = new BeautifulLabel(vehicle.getYear() + " " + vehicle.getBrand() + " " + vehicle.getModel());
        this.colorLabel = new BeautifulLabel("Exterior: " + firstToUpper(vehicle.getExteriorColor()) + " Interior: " + firstToUpper(vehicle.getInteriorColor()));

    }

    public void addReturnButton() {
        vehicleDetailReturnButton = new JButton("Return to Vehicle List");
        vehicleDetailReturnButton.setBackground(Setting.CHECK_INVENTORY_BUTTON_COLOR_BEFORE);
        vehicleDetailReturnButton.setBorderPainted(false);
        vehicleDetailReturnButton.setOpaque(true);
        vehicleDetailReturnButton.addActionListener(new ReturnButtonActionListener());

        this.checkVehicleDetailPanel = new JPanel();

        this.checkVehicleDetailPanel.add(vehicleDetailReturnButton, BorderLayout.EAST);
        this.add(checkVehicleDetailPanel, BorderLayout.SOUTH);
    }


    public void resetLayout() {
        Dimension size = new Dimension(850, 612);
        this.setPreferredSize(size);
        this.imagePanel.setLayout(new GridLayout(5, 1));

        String imagePath = "src/ui/resources/Cadillac";
        for (int i = 1; i < 5; i++) {
            this.imageIcon = ViewUtil.getIcon(imagePath + i + ".png", 250, 180);
            imageLabel = new BeautifulLabel();
            imageLabel.setIcon(this.imageIcon);
            imagePanel.add(imageLabel);
        }
    }

    class ReturnButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            VehicleSearchMainView.mainEastPanel.removeAll();
            VehicleSearchMainView.singleVehicleRecordPanel.removeAll();
            VehicleSearchMainView.mainEastPanel.add(VehicleSearchMainView.searchVehiclePanel, BorderLayout.CENTER);
            VehicleSearchMainView.searchVehiclePanel.refreshPageInfoPanel();
            VehicleSearchMainView.mainEastPanel.updateUI();
        }
    }
}
