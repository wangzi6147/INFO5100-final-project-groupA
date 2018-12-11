package ui.dealer.panel;

import ui.Setting;
import ui.label.BeautifulLabel;
import ui.vehicle.panel.SearchVehiclePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchDealerButtonPanel extends JPanel {

    public static String dealerName;
    public static String city;
    public BeautifulLabel name;
    public BeautifulLabel location;
    public BeautifulLabel zipCode;
    public BeautifulLabel miles;
    public JTextField inputName;
    public JTextField inputLocation;


    public SearchDealerButtonPanel() {
        dealerName = "";
        city = "";
        initialize();
    }

    public void initialize() {
        Dimension size = new Dimension(Setting.DEALER_SEARCH_BUTTON_PANEL_WIDTH, Setting.DEALER_SEARCH_BUTTON_PANEL_HEIGHT);
        Dimension labelSize = new Dimension(Setting.SEARCH_DEALER_LABEL_WIDTH, Setting.SEARCH_DEALER_LABEL_HEIGHT);
        Dimension inputSize = new Dimension(Setting.SEARCH_DEALER_INPUT_WIDTH, Setting.SEARCH_DEALER_INPUT_HEIGHT);

        this.name = new BeautifulLabel("Dealer");
        name.setPreferredSize(labelSize);
        name.setFont(Setting.SEARCH_DEALER_BUTTON_FONT);

        inputName = new JTextField();
        inputName.setPreferredSize(inputSize);
        inputName.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);

        this.location = new BeautifulLabel("Location");
        location.setPreferredSize(labelSize);
        location.setFont(Setting.SEARCH_DEALER_BUTTON_FONT);

        this.zipCode = new BeautifulLabel("Zip Code");
        zipCode.setPreferredSize(labelSize);
        zipCode.setFont(Setting.SEARCH_DEALER_BUTTON_FONT);

        this.miles = new BeautifulLabel("Miles");
        miles.setPreferredSize(labelSize);
        miles.setFont(Setting.SEARCH_DEALER_BUTTON_FONT);

        inputLocation = new JTextField();
        inputLocation.setPreferredSize(inputSize);
        inputLocation.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);

        JTextField inputZipCode = new JTextField();
        inputZipCode.setPreferredSize(inputSize);
        inputZipCode.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);

        JComboBox<String> selectMiles = new JComboBox<>();
        selectMiles.setPreferredSize(inputSize);
        selectMiles.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(name);
        namePanel.add(inputName);

        JPanel locationPanel = new JPanel();
        locationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        locationPanel.add(location);
        locationPanel.add(inputLocation);

        JPanel zipCodePanel = new JPanel();
        zipCodePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        zipCodePanel.add(zipCode);
        zipCodePanel.add(inputZipCode);

        JPanel milesPanel = new JPanel();
        milesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        milesPanel.add(miles);
        milesPanel.add(selectMiles);


        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(Setting.VEHICLE_SEARCH_BUTTON_PANEL_SEARCH_BUTTON_WIDTH, Setting.VEHICLE_SEARCH_BUTTON_PANEL_SEARCH_BUTTON_HEIGHT));
        JPanel serchButtonPanel = new JPanel();
        serchButtonPanel.add(searchButton);
        searchButton.setBackground(Setting.CHECK_INVENTORY_BUTTON_COLOR_BEFORE);
        searchButton.setBorderPainted(false);
        searchButton.setOpaque(true);
        searchButton.addActionListener(new SearchDealerButtonActionListener());


        this.setPreferredSize(size);
        this.setLayout(new GridLayout(10, 1));
        this.add(namePanel);
        this.add(locationPanel);
        this.add(serchButtonPanel);

    }

    class SearchDealerButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dealerName = inputName.getText();
            city = inputLocation.getText();
            SearchDealerPanel.refreshDealerRecordsPanel(dealerName, city, 1);

        }
    }


}
