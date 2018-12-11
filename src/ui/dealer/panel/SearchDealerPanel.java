package ui.dealer.panel;

import dto.Address;
import dto.Dealer;
import dto.DealerQueryResponse;
import service.DealerServiceImpl;
import ui.Setting;
import ui.button.BeautifulButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class SearchDealerPanel extends JPanel {

    public static SearchDealerButtonPanel buttonPanel;
    public static JPanel resultPanel;
    public static JScrollPane dealerListPanel;
    public static int totalPageNumber;
    public static JPanel allDealerRecordsPanel;
    private static JPanel pageInfoPanel;
    private static int currentPageNumber;

    public SearchDealerPanel() {

    }

    public static void refreshPageInfoPanel() {
        //pageInfoPanel = new JPanel();
        pageInfoPanel.removeAll();
        int showTotalPageNumber = totalPageNumber == 0 ? 1 : totalPageNumber;
        JLabel jLabel = new JLabel(currentPageNumber + " / " + showTotalPageNumber);
        jLabel.setFont(Setting.PAGE_INFO_LABEL_FONT);
        pageInfoPanel.add(jLabel);
        pageInfoPanel.repaint();
        pageInfoPanel.revalidate();
    }

    public static void refreshDealerRecordsPanel(String dealerName, String city, int pageNum) {
        List<Dealer> list = queryDealRecords(dealerName, city, pageNum);
        allDealerRecordsPanel.removeAll();

        for (int i = 0; i < 20 && i < list.size(); i++) {
            allDealerRecordsPanel.add(new SingleDealerRecordPanel(list.get(i)));
        }
        allDealerRecordsPanel.repaint();
        allDealerRecordsPanel.revalidate();
        refreshPageInfoPanel();
    }

    public static List<Dealer> queryDealRecords(String dealerName, String city, int pageNum) {
        DealerServiceImpl dealerListServiceImplService = new DealerServiceImpl();
        DealerQueryResponse response = null;
        try {
            response = dealerListServiceImplService.getDealerList(dealerName, city, pageNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Dealer> list = response.getDealerList();
        totalPageNumber = response.getTotalPageNumber();
        return list;
    }

    public void init() {
        currentPageNumber = 1;
        this.buttonPanel = new SearchDealerButtonPanel();
        this.resultPanel = new JPanel();
        Dimension size = new Dimension(Setting.SEARCH_DEALER_PANEL_WIDTH, Setting.SEARCH_DEALER_PANEL_HEIGHT);
        this.setPreferredSize(size);
        this.setLayout(new BorderLayout());
        allDealerRecordsPanel = new JPanel();
        allDealerRecordsPanel.setLayout(new GridLayout(20, 1, 0, 10));


        BeautifulButton prePageButton = new BeautifulButton("src/ui/resources/prev_button");
        prePageButton.addActionListener(new PrePageButtonActionListener());
        prePageButton.setNormalIcon();
        BeautifulButton nextPageButton = new BeautifulButton("src/ui/resources/next_button");
        nextPageButton.addActionListener(new NextPageButtonActionListener());
        nextPageButton.setNormalIcon();

        JPanel southPanel = new JPanel();
        this.pageInfoPanel = new JPanel();
        refreshPageInfoPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 5));
        southPanel.add(prePageButton);
        southPanel.add(pageInfoPanel);
        southPanel.add(nextPageButton);
        southPanel.setPreferredSize(new Dimension(Setting.VEHICLE_SEARCH_RESULT_NAVIGATOR_BAR_WIDTH, Setting.VEHICLE_SEARCH_RESULT_NAVIGATOR_BAR_HEIGHT));


        Address address = new Address();
        address.setAddress1("225 Terry Ave N");
        address.setCity("Seattle");
        address.setState("WA");
        address.setZip("98001");
        Dealer dealer = new Dealer("XXX Ford Dealer", address);
        dealer.setId("10142");
        refreshDealerRecordsPanel("", "", currentPageNumber);
        Dimension dealerTableSize = new Dimension(Setting.VEHICLE_TABLE_WIDTH, Setting.VEHICLE_TABLE_HEIGHT);


        this.dealerListPanel = new JScrollPane(allDealerRecordsPanel);
        this.dealerListPanel.setBorder(null);
        dealerListPanel.setPreferredSize(dealerTableSize);
        resultPanel.setLayout(new BorderLayout());
        resultPanel.add(dealerListPanel, BorderLayout.CENTER);
        resultPanel.add(southPanel, BorderLayout.SOUTH);
        Dimension size1 = new Dimension(1100, Setting.SEARCH_DEALER_PANEL_HEIGHT);
        resultPanel.setSize(size1);
        this.add(buttonPanel, BorderLayout.WEST);
        this.add(resultPanel, BorderLayout.CENTER);
    }

    class PrePageButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentPageNumber > 1) {
                refreshDealerRecordsPanel(SearchDealerButtonPanel.dealerName, SearchDealerButtonPanel.city, --currentPageNumber);
            }
        }
    }

    class NextPageButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentPageNumber < totalPageNumber) {
                refreshDealerRecordsPanel(SearchDealerButtonPanel.dealerName, SearchDealerButtonPanel.city, ++currentPageNumber);
            }
        }
    }
}
