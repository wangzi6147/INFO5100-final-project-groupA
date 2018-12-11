package ui;

import dto.Dealer;
import ui.Setting;
import ui.button.BeautifulButton;
import ui.dealer.panel.SearchDealerPanel;
import ui.menu.MenuBarPanel;
import ui.vehicle.panel.SearchVehiclePanel;
import ui.vehicle.panel.SingleVehicleRecordPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

public class VehicleSearchMainView {

    public static JPanel mainEastPanel;
    public static SearchDealerPanel searchDealerPanel;
    public static SearchVehiclePanel searchVehiclePanel;
    public static SingleVehicleRecordPanel singleVehicleRecordPanel;
    private JFrame mainFrame;
    private JPanel mainViewPanel;
    private MenuBarPanel mainMenuBarPanel;
    private int x, y;

    public VehicleSearchMainView(Dealer dealer) {
        initializeMainView(dealer);
    }

    private void initializeMainView(Dealer dealer) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        mainFrame = new JFrame();
        mainFrame.setUndecorated(false);//调用JFrame的最小化
        //AWTUtilities.setWindowOpacity(mainFrame,0.9F);
        //AWTUtilities.setWindowOpaque(mainFrame, false);
        mainFrame.setBounds(Setting.MAIN_FRAM_X, Setting.MAIN_FRAM_Y, Setting.MAIN_FRAM_WIDTH, Setting.MAIN_FRAM_HEIGHT);
        mainFrame.setTitle("Vehicle Search System Based On Dealer");
        mainFrame.setBackground(Setting.MAIN_FRAM_COLOR);

        mainMenuBarPanel = new MenuBarPanel();
        mainEastPanel = new JPanel();
        //AWTUtilities.setWindowOpacity(mainEastPanel, 1);
        mainViewPanel = new JPanel();

        mainViewPanel.setLayout(new BorderLayout());
        mainViewPanel.setBackground(Setting.MAIN_VIEW_PANEL_COLOR);
        mainViewPanel.add(mainMenuBarPanel, BorderLayout.WEST);
        mainViewPanel.add(mainEastPanel, BorderLayout.CENTER);

        searchVehiclePanel = new SearchVehiclePanel();
        searchDealerPanel = new SearchDealerPanel();
        mainFrame.add(mainViewPanel);

        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setProperty();
        mainFrame.addMouseListener(new PressMouseAdapter());
        mainFrame.addMouseMotionListener(new MoveMouseMotionAdapter());
    }

    public void setProperty() {
        Enumeration<Object> sets = UIManager.getDefaults().keys();
        while (sets.hasMoreElements()) {
            Object obj = sets.nextElement();
            if (obj instanceof String) {
                if (((String) obj).endsWith(".background")) {
                    UIManager.put(obj, Color.white);
                }
            }
        }
    }

    public class PressMouseAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }
    }

    public class MoveMouseMotionAdapter extends MouseAdapter {
        public void mouseDragged(MouseEvent e) {
            int left = mainFrame.getLocation().x;
            int top = mainFrame.getLocation().y;
            mainFrame.setLocation(left + e.getX() - x, top + e.getY() - y);
        }
    }
}
