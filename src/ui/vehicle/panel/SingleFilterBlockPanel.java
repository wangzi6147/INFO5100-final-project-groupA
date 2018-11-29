package ui.vehicle.panel;

import dto.BodyType;
import ui.Setting;
import ui.checkBox.TitledJCheckBox;
import ui.label.BeautifulLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class SingleFilterBlockPanel extends JPanel {

    public static int totalHeight;
    private static Map<String, Set> totalRecord;
    private Set<String> result;
    private String title;

    public SingleFilterBlockPanel(String title, List<String> list) {
        init(title, list);
    }

    public static void refresh() {
        if (totalRecord == null) {
            totalRecord = new HashMap<String, Set>();
        } else {
            totalRecord.clear();
        }
    }

    private static List<String> set2list(Set<String> set) {
        List<String> result = new ArrayList<>();
        result.addAll(set);
        if (result.size() == 0) {
            return null;
        } else {
            return result;
        }
    }

    private static List<String> getFilterList(String name) {
        Set set = totalRecord.get(name);
        return set2list(set);
    }

    private void init(String title, List<String> list) {
        this.title = title;
        if (list == null)
            list = new ArrayList<String>();
        this.result = new HashSet<>();
        int num = list.size();
        int rows = 0;
        if (num % 2 == 0) {
            rows = num / 2;
        } else {
            rows = num / 2 + 1;
        }
        Dimension scrollPanelSize = null;
        int maxHeight = Math.min((int) Math.ceil(300 * rows / 6.0), 300);
        int minHeight = 50;
        if (num == 0) {
            scrollPanelSize = new Dimension(Setting.VEHICLE_SEARCH_BUTTON_PANEL_WIDTH, minHeight);
            totalHeight += minHeight;
        } else {

            scrollPanelSize = new Dimension(Setting.VEHICLE_SEARCH_BUTTON_PANEL_WIDTH, maxHeight);
            totalHeight += maxHeight;
        }
        //scrollPanelSize = new Dimension(Setting.VEHICLE_SEARCH_BUTTON_PANEL_WIDTH, 50);

        JPanel jPanel = null;
        if ("Model".equals(title) || "Price".equals(title)) {
            jPanel = new JPanel(new GridLayout(num, 1));

        } else {
            jPanel = new JPanel(new GridLayout(rows, 2));
        }
        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setBorder(null);
        jScrollPane.setPreferredSize(scrollPanelSize);

        for (int i = 0; i < list.size(); i++) {
            TitledJCheckBox jCheckBox = new TitledJCheckBox(list.get(i));
            if (totalRecord.get(title) != null && totalRecord.get(title).contains(list.get(i))) {
                jCheckBox.setSelected(true);
                result.add(list.get(i));
            }
            jCheckBox.addActionListener(new CheckBoxStateChangedActionListener());
            jPanel.add(jCheckBox);
        }
        this.setLayout(new BorderLayout());
        this.add(new BeautifulLabel(title), BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.SOUTH);
        //this.setPreferredSize(scrollPanelSize);
        totalRecord.put(title, result);
        System.out.println(title + ": " + result);
    }

    private List<Boolean> string2Boolean(Set<String> set) {
        List<Boolean> result = new ArrayList<>();
        for (String item : set) {
            if ("New".equals(item)) {
                result.add(true);
            } else {
                result.add(false);
            }
        }
        if (result.size() == 0) {
            return null;
        } else {
            return result;
        }
    }

    private List<BodyType> string2BodyType(Set<String> set) {
        List<BodyType> result = new ArrayList<>();
        for (String item : set) {
            if (BodyType.CAR.toString().equals(item)) {
                result.add(BodyType.CAR);
            } else if (BodyType.SUV.toString().equals(item)) {
                result.add(BodyType.SUV);
            } else if (BodyType.TRUCK.toString().equals(item)) {
                result.add(BodyType.TRUCK);
            } else {
                result.add(BodyType.VAN);
            }
        }
        if (result.size() == 0) {
            return null;
        } else {
            return result;
        }
    }

    class CheckBoxStateChangedActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            TitledJCheckBox source = (TitledJCheckBox) e.getSource();
            boolean selected = source.getModel().isSelected();

            if (selected) {
                result.add(source.getTitle());
            } else {
                result.remove(source.getTitle());
            }

            if (totalRecord.get(title) == result)
                System.out.println(true);

            if ("Category".equals(title)) {
                SearchVehicleButtonPanel.vehicleFilterSelected.setIsNew(set2list(result));
            } else if ("Make".equals(title)) {
                SearchVehicleButtonPanel.vehicleFilterSelected.setBrand(set2list(result));
            } else if ("Model".equals(title)) {
                SearchVehicleButtonPanel.vehicleFilterSelected.setModel(set2list(result));
            } else if ("Price".equals(title)) {
                SearchVehicleButtonPanel.vehicleFilterSelected.setPrice(set2list(result));
            } else if ("Year".equals(title)) {
                SearchVehicleButtonPanel.vehicleFilterSelected.setYears(set2list(result));
            } else if ("Body Type".equals(title)) {
                SearchVehicleButtonPanel.vehicleFilterSelected.setBodyType(set2list(result));
            } else if ("Exterior".equals(title)) {
                SearchVehicleButtonPanel.vehicleFilterSelected.setExteriorColor(set2list(result));
            } else if ("Interior".equals(title)) {
                SearchVehicleButtonPanel.vehicleFilterSelected.setInteriorColor(set2list(result));
            }

            SearchVehiclePanel.buttonPanel.refresh(title);
            //SearchVehiclePanel.refreshPageInfoPanel();
        }
    }
}
