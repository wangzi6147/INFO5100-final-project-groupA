package ui;

import java.awt.*;

public class Setting {

    public static final String[] SEARCH_VEHICLE_FILTER = {"Category", "Make", "Model", "Price", "Year", "Body Type", "Exterior Color", "Interior Color"};
    // main frame
    public static final Color MAIN_FRAM_COLOR = Color.white;
    public static final int MAIN_FRAM_X = 50;
    public static final int MAIN_FRAM_Y = 50;
    public static final int MAIN_FRAM_WIDTH = 1600;
    public static final int MAIN_FRAM_HEIGHT = 900;

    // main view panel
    public static final Color MAIN_VIEW_PANEL_COLOR = Color.white;

    // button
    public static final Color CHECK_INVENTORY_BUTTON_COLOR_PRESS = new Color(114, 206, 154);
    public static final Color CHECK_INVENTORY_BUTTON_COLOR_BEFORE = new Color(73,160,174);
//    public static final Color CHECK_INVENTORY_BUTTON_COLOR_PRESS = new Color(114, 206, 154);
    public static final int BUTTON_ICON_SIZE = 60;

    // menu bar
    public static final int MENU_BAR_WIDTH = 100;
    public static final Color MENU_BAR_COLOR = Color.DARK_GRAY; //new Color(200, 76, 72)

    public static final int SEARCH_VEHICLE_PANEL_WIDTH = MAIN_FRAM_WIDTH - MENU_BAR_WIDTH;
    public static final int SEARCH_VEHICLE_PANEL_HEIGHT = MAIN_FRAM_HEIGHT;
    public static final int SEARCH_DEALER_PANEL_WIDTH = MAIN_FRAM_WIDTH - MENU_BAR_WIDTH;
    public static final int SEARCH_DEALER_PANEL_HEIGHT = MAIN_FRAM_HEIGHT;

    // vehicle search button panel
    public static final int VEHICLE_SEARCH_BUTTON_PANEL_WIDTH = 300;
    public static final int VEHICLE_SEARCH_BUTTON_PANEL_HEIGHT = SEARCH_DEALER_PANEL_HEIGHT;

    // vehicle search result page navigator bar
    public static final int VEHICLE_SEARCH_RESULT_NAVIGATOR_BAR_HEIGHT = BUTTON_ICON_SIZE + 40;
    public static final int VEHICLE_SEARCH_RESULT_NAVIGATOR_BAR_WIDTH = SEARCH_VEHICLE_PANEL_WIDTH - VEHICLE_SEARCH_BUTTON_PANEL_WIDTH;

    // vehicle panel
    public static final int VEHICLE_TABLE_WIDTH = SEARCH_VEHICLE_PANEL_WIDTH - VEHICLE_SEARCH_BUTTON_PANEL_WIDTH;
    public static final int VEHICLE_TABLE_HEIGHT = MAIN_FRAM_HEIGHT - VEHICLE_SEARCH_RESULT_NAVIGATOR_BAR_HEIGHT;

    public static final int VEHICLE_SEARCH_BUTTON_PANEL_SEARCH_BUTTON_HEIGHT = 40;
    public static final int VEHICLE_SEARCH_BUTTON_PANEL_SEARCH_BUTTON_WIDTH = 280;

    public static final int VEHICLE_SEARCH_BUTTON_PANEL_FILTER_SCROLL_HEIGHT = MAIN_FRAM_HEIGHT - VEHICLE_SEARCH_BUTTON_PANEL_SEARCH_BUTTON_HEIGHT-50;
    public static final int VEHICLE_SEARCH_BUTTON_PANEL_FILTER_SCROLL_WIDTH = VEHICLE_SEARCH_BUTTON_PANEL_WIDTH;



    // font
    public static final Font FONT_CATEGORY = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    public static final Font FONT_PRICE = new Font(Font.SANS_SERIF, Font.BOLD, 27);
    public static final Font SEARCH_DEALER_BUTTON_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    public static final Font PAGE_INFO_LABEL_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 20);




    public static final int BUTTON_V_GAP = 50;
    public static final int BUTTON_H_GAP = 0;

//    public static final String SEARCH_VEHICLE_BUTTON_NAME = "Search Vehicle";
//    public static final String SEARCH_DEALER_BUTTON_NAME = "Search Dealer";

    // Search dealer button label size
    public static final int SEARCH_DEALER_LABEL_WIDTH = 60;
    public static final int SEARCH_DEALER_LABEL_HEIGHT = 30;

    public static final int SEARCH_DEALER_INPUT_WIDTH = 150;
    public static final int SEARCH_DEALER_INPUT_HEIGHT = SEARCH_DEALER_LABEL_HEIGHT;

    public static final int DEALER_SEARCH_BUTTON_PANEL_WIDTH = 250;
    public static final int DEALER_SEARCH_BUTTON_PANEL_HEIGHT = SEARCH_DEALER_PANEL_HEIGHT;
    // dealer records
    public static final Color DEALER_RECORD_BACKGROUND_COLOR = new Color(243, 243, 243);

    // image
//    public static final String PRE_BUTTON_IMAGE_PATH = "src/ui/resources/prev_button";
//    public static final String NEXT_BUTTON_IMAGE_PATH = "src/ui/resources/next_button";


}
