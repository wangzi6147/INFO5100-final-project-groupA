package ui.SpecificDealer;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InventoryTableModel implements TableModel {
    public static final String[] searchFields = {"Picture", "Category", "Year", "Make", "Model", "Trim", "Type", "Price"};
    private List list;

    public InventoryTableModel() {
        this.list = new ArrayList();
    }

    @Override
    public int getRowCount() {
        //return list.size();
        return 10;
    }

    @Override
    public int getColumnCount() {
        return searchFields.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return searchFields[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0)
            return ImageIcon.class;
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if (columnIndex == 0) {
            return getIcon();
        } else if (columnIndex == 1) {
            return "New";
        } else if (columnIndex == 2) {
            return "2014";
        } else if (columnIndex == 3) {
            return "Cadillac";
        } else if (columnIndex == 4) {
            return "CTS Sedan";
        } else if (columnIndex == 5) {
            return "3.6L V6 AWD Luxury";
        } else if (columnIndex == 6) {
            return "CAR";
        } else {
            return "56000.00";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    public ImageIcon getIcon() {
        ImageIcon icon = new ImageIcon("/Users/petter/test/Cadillac.jpg");
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        return icon;
    }
}
