package ui.vehicle.panel;

import dto.Vehicle;
import ui.Setting;
import ui.label.BeautifulLabel;
import ui.vehicle.ViewUtil;

import javax.swing.*;
import java.awt.*;

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


    public SingleVehicleRecordPanel(Vehicle vehicle) {
        this.imageIcon = ViewUtil.getIcon("/Users/petter/test/Cadillac.jpg", 400, 300);

        this.imageLabel = new BeautifulLabel();
        this.imageLabel.setIcon(this.imageIcon);

        if(vehicle.getIsNew()){
            this.categoryLabel = new BeautifulLabel(ViewUtil.getIcon("src/ui/resources/new_stamp.png", 70, 70));

        }else{
            this.categoryLabel = new BeautifulLabel(ViewUtil.getIcon("src/ui/resources/used_stamp.png", 70, 70));
        }

        this.priceLabel = new BeautifulLabel("$ " + vehicle.getPrice());
        this.priceLabel.setFont(Setting.FONT_PRICE);

        this.milesLabel = new BeautifulLabel(vehicle.getMiles() + " Miles");

        this.titleLabel = new BeautifulLabel(vehicle.getYear() + " " + vehicle.getBrand() + " " + vehicle.getModel());


        this.colorLabel = new BeautifulLabel("Exterior: " + firstToUpper(vehicle.getExteriorColor()) + " Interior: " + firstToUpper(vehicle.getInteriorColor()));

        this.imagePanel = new JPanel();
        this.imagePanel.add(this.imageLabel);

        this.categoryPanel = new JPanel();
        this.categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.categoryPanel.add(categoryLabel);

        this.priceAndMilesPanel = new JPanel();
        this.priceAndMilesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        this.priceAndMilesPanel.add(this.priceLabel);
        this.priceAndMilesPanel.add(this.milesLabel);

        this.infoPanel = new JPanel();
        this.infoPanel.setLayout(new GridLayout(4, 1));
        this.infoPanel.add(this.categoryPanel);
        this.infoPanel.add(this.priceAndMilesPanel);
        this.infoPanel.add(titleLabel);
        this.infoPanel.add(colorLabel);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(20);
        this.setLayout(borderLayout);
        //this.setBackground(Color.white);
        this.setOpaque(true);
        Dimension size = new Dimension(1100, 310);
        this.setPreferredSize(size);
        this.add(imagePanel, BorderLayout.WEST);
        this.add(infoPanel, BorderLayout.CENTER);
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
}
