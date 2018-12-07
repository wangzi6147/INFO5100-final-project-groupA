package ui.SpecificDealer;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

public class SearchComboBoxModel implements ComboBoxModel {
    private List<String> list;

    public SearchComboBoxModel() { // ?????
        this.list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
    }

    @Override
    public Object getSelectedItem() {
        System.out.println("getSelectedItem");
        return "1";
    }

    @Override
    public void setSelectedItem(Object anItem) {
        System.out.println("setSelectedItem");
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Object getElementAt(int index) {
        return list.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
