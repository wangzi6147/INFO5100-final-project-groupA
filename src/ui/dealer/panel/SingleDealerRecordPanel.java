package ui.dealer.panel;

import dto.Dealer;
import ui.Setting;
import ui.label.BeautifulLabel;
import ui.menu.MenuBarPanel;
import ui.VehicleSearchMainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingleDealerRecordPanel extends JPanel {

    public BeautifulLabel name;
    public BeautifulLabel address;
    public JButton checkInventory;
    //public BeautifulLabel workTime;
    //public BeautifulLabel phoneNumber;
    public String dealerId;

    public SingleDealerRecordPanel(Dealer dealer) {

        this.dealerId = dealer.getId();
        Dimension labelSize = new Dimension(60, 30);
        Dimension detailSize = new Dimension(300, 30);

        name = new BeautifulLabel("Name");
        name.setPreferredSize(labelSize);
        name.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);

        address = new BeautifulLabel("Address");
        address.setPreferredSize(labelSize);
        address.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);

        checkInventory = new JButton("Check Inventory");
        checkInventory.setBackground(Setting.CHECK_INVENTORY_BUTTON_COLOR_BEFORE);
        checkInventory.setBorderPainted(false);
        checkInventory.setOpaque(true);
        checkInventory.addActionListener(new ButtonPressActionListener());

        BeautifulLabel nameDetail = new BeautifulLabel(dealer.getName());
        nameDetail.setPreferredSize(detailSize);
        nameDetail.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);

        BeautifulLabel addressDetail = new BeautifulLabel(dealer.getAddress1());
        addressDetail.setPreferredSize(detailSize);
        addressDetail.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);

        JPanel namePanel = new JPanel(new GridLayout(1, 2));
        namePanel.add(name);
        namePanel.add(nameDetail);
        namePanel.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);

        JPanel addPanel = new JPanel(new GridLayout(1, 2));
        addPanel.add(address);
        addPanel.add(addressDetail);
        addPanel.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(checkInventory);
        buttonPanel.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);

        this.setLayout(new GridLayout(3, 1));
        this.add(namePanel);
        this.add(addPanel);
        this.add(buttonPanel);
        this.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);
    }

    class ButtonPressActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MenuBarPanel.searchVehicleButton.setEnableIcon();
            MenuBarPanel.searchDealerButton.setDisableIcon();
            VehicleSearchMainView.mainEastPanel.removeAll();
            VehicleSearchMainView.searchVehiclePanel.removeAll();
            VehicleSearchMainView.searchVehiclePanel.init(dealerId);
            VehicleSearchMainView.mainEastPanel.add(VehicleSearchMainView.searchVehiclePanel, BorderLayout.CENTER);
            VehicleSearchMainView.mainEastPanel.updateUI();
        }
    }


}
