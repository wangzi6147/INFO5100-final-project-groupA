package ui.vehicle.panel;

import dto.Vehicle;
import dto.VehicleFilterSelected;
import service.VehicleListPage;
import ui.Setting;
import ui.VehicleSearchMainView;
import ui.button.BeautifulButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SearchVehiclePanel extends JPanel {

    public static SearchVehicleButtonPanel buttonPanel;
    public static int totalPageNumber;
    public static JScrollPane centerPanel;
    private static int currentPageNumber;
    private static JPanel pageInfoPanel;
    private JPanel recordListPanel;
    private String dealerId;
    private VehicleFilterSelected vehicleFilterSelected;

    public SearchVehiclePanel() {

    }

    public static void refreshPageInfoPanel() {
        //pageInfoPanel = new JPanel();
        pageInfoPanel.removeAll();
        JLabel jLabel = new JLabel(currentPageNumber + " / " + totalPageNumber);
        jLabel.setFont(Setting.PAGE_INFO_LABEL_FONT);
        pageInfoPanel.add(jLabel);
        pageInfoPanel.repaint();
        pageInfoPanel.revalidate();
    }

    public void init(String dealerId) {
        currentPageNumber = 1;
        this.dealerId = dealerId;
        //this.vehicleTable = new JTable(this.inventoryTableModel);
        this.recordListPanel = new JPanel();
        centerPanel = new JScrollPane(recordListPanel);
        refreshRecordListPanel(getVehicleList("All", 1));
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        JButton prePageButton = new BeautifulButton(Setting.PRE_BUTTON_IMAGE_PATH);
        prePageButton.addActionListener(new PrePageButtonActionListener());
        JButton nextPageButton = new BeautifulButton(Setting.NEXT_BUTTON_IMAGE_PATH);
        nextPageButton.addActionListener(new NextPageButtonActionListener());

        JPanel southPanel = new JPanel();
        this.pageInfoPanel = new JPanel();
        /*JLabel jLabel = new JLabel(currentPageNumber + " / " + totalPageNumber);
        jLabel.setFont(Setting.PAGE_INFO_LABEL_FONT);
        pageInfoPanel.add(jLabel);*/
        refreshPageInfoPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 5));
        southPanel.add(prePageButton);
        southPanel.add(pageInfoPanel);
        southPanel.add(nextPageButton);
        southPanel.setPreferredSize(new Dimension(Setting.VEHICLE_SEARCH_RESULT_NAVIGATOR_BAR_WIDTH, Setting.VEHICLE_SEARCH_RESULT_NAVIGATOR_BAR_HEIGHT));

        //this.southPanel.setLayout(new ScrollPaneLayout());
        this.setLayout(new BorderLayout());

        Dimension size = new Dimension(Setting.SEARCH_VEHICLE_PANEL_WIDTH, Setting.SEARCH_VEHICLE_PANEL_HEIGHT);
        this.setPreferredSize(size);
        Dimension vehicleTableSize = new Dimension(Setting.VEHICLE_TABLE_WIDTH, Setting.VEHICLE_TABLE_HEIGHT);
        centerPanel.setBorder(null);
        //this.centerPanel.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);
        centerPanel.setSize(vehicleTableSize);

        resultPanel.add(centerPanel, BorderLayout.CENTER);
        resultPanel.add(southPanel, BorderLayout.SOUTH);
        buttonPanel = new SearchVehicleButtonPanel(dealerId);
        JScrollPane buttonScrollPanel = new JScrollPane(buttonPanel);
        buttonScrollPanel.setBorder(null);
        Dimension buttonPanelSize = new Dimension(Setting.VEHICLE_SEARCH_BUTTON_PANEL_FILTER_SCROLL_WIDTH, Setting.VEHICLE_SEARCH_BUTTON_PANEL_FILTER_SCROLL_HEIGHT);
        buttonScrollPanel.setPreferredSize(buttonPanelSize);

        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(Setting.VEHICLE_SEARCH_BUTTON_PANEL_WIDTH, Setting.SEARCH_VEHICLE_PANEL_HEIGHT));
        westPanel.add(buttonScrollPanel, BorderLayout.NORTH);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonActionListener());
        searchButton.setPreferredSize(new Dimension(Setting.VEHICLE_SEARCH_BUTTON_PANEL_SEARCH_BUTTON_WIDTH, Setting.VEHICLE_SEARCH_BUTTON_PANEL_SEARCH_BUTTON_HEIGHT));
        westPanel.add(searchButton, BorderLayout.CENTER);
        searchButton.setBackground(Setting.CHECK_INVENTORY_BUTTON_COLOR_BEFORE);
        searchButton.setBorderPainted(false);
        searchButton.setOpaque(true);
        this.add(resultPanel, BorderLayout.CENTER);
        this.add(westPanel, BorderLayout.WEST);

    }

    public String getDealerId() {
        return dealerId;
    }

    public void refreshRecordListPanel(List<Vehicle> list) {
        this.recordListPanel.removeAll();
        GridLayout gridLayout = new GridLayout(20 >= list.size() ? list.size() : 20, 1);
        gridLayout.setVgap(10);
        this.recordListPanel.setBackground(Setting.DEALER_RECORD_BACKGROUND_COLOR);
        this.recordListPanel.setOpaque(true);
        this.recordListPanel.setLayout(gridLayout);

        for (int i = 0; i < 20 && i < list.size(); i++) {
            SingleVehicleRecordPanel vehicleRecordPanel = new SingleVehicleRecordPanel(list.get(i));
            vehicleRecordPanel.addMouseListener(new ClickActionListener(list.get(i)));
            this.recordListPanel.add(vehicleRecordPanel);
        }
        centerPanel.getVerticalScrollBar().setValue(0);
    }

    public List<Vehicle> getVehicleList(String type, int pageNumber) {
        if ("All".equals(type)) {
            VehicleFilterSelected vehicleFilterSelected = new VehicleFilterSelected(dealerId);
            VehicleListPage vehicleListPageService = new VehicleListPage();
            vehicleListPageService.Query(vehicleFilterSelected);
            List<Vehicle> list = vehicleListPageService.getVehicleList();
            totalPageNumber = vehicleListPageService.getPageCount();
            return list;
        } else {
            this.vehicleFilterSelected.setPageNumber(pageNumber);
            VehicleListPage vehicleListPageService = new VehicleListPage();
            vehicleListPageService.Query(vehicleFilterSelected);
            List<Vehicle> list = vehicleListPageService.getVehicleList();
            totalPageNumber = vehicleListPageService.getPageCount();
            return list;
        }


    }

    class SearchButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            vehicleFilterSelected = SearchVehicleButtonPanel.vehicleFilterSelected;
            List<Vehicle> list = getVehicleList("Filter", 1);
            refreshRecordListPanel(list);
            recordListPanel.repaint();
            recordListPanel.revalidate();
            refreshPageInfoPanel();
            System.out.println("Search vehicle button: " + list.size() + ": " + list);
        }
    }

    class PrePageButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentPageNumber > 1) {
                List<Vehicle> list = getVehicleList("Filter", currentPageNumber - 1);
                currentPageNumber--;
                refreshRecordListPanel(list);
                recordListPanel.repaint();
                recordListPanel.revalidate();
                refreshPageInfoPanel();
            }
        }
    }

    class NextPageButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentPageNumber < totalPageNumber) {
                List<Vehicle> list = getVehicleList("Filter", currentPageNumber + 1);
                currentPageNumber++;
                refreshRecordListPanel(list);
                recordListPanel.repaint();
                recordListPanel.revalidate();
                refreshPageInfoPanel();
            }
        }
    }

    class ClickActionListener extends MouseAdapter {
        private Vehicle vehicle;

        public ClickActionListener (Vehicle vehicle) {
            this.vehicle = vehicle;
        }
        public void mouseClicked(MouseEvent me) {
            if (me.getClickCount() == 2) {
                VehicleSearchMainView.mainEastPanel.removeAll();
                SingleVehicleRecordPanel singleVehicleRecordPanel = new SingleVehicleRecordPanel(vehicle);
                singleVehicleRecordPanel.addReturnButton();
                singleVehicleRecordPanel.resetLayout();
                VehicleSearchMainView.singleVehicleRecordPanel = singleVehicleRecordPanel;
                VehicleSearchMainView.mainEastPanel.add(VehicleSearchMainView.singleVehicleRecordPanel, BorderLayout.EAST);
                VehicleSearchMainView.mainEastPanel.updateUI();
            }
        }
    }


}
