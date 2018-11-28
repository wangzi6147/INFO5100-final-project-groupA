package ui.special;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SpecialTableModel implements TableModel{


    public static final String[] ColumnName = {"Title", "Description", "Disclaimer", "Scope", "Value", "Start Date", "End Date"};
    private List list;

    public SpecialTableModel() {
        this.list = new ArrayList();
    }

    @Override
    public int getRowCount() {
        //return list.size();
        return 10;
    }

    @Override
    public int getColumnCount() {
        return ColumnName.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return ColumnName[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if (columnIndex == 0) {
            return "Thanksgiving Sale";
        } else if (columnIndex == 1) {
            return "Happy Thanksgiving";
        } else if (columnIndex == 2) {
            return "All rights reserved";
        } else if (columnIndex == 3) {
            return "Voks Wagon";
        } else if (columnIndex == 4) {
            return "10% off";
        } else if (columnIndex == 5) {
            return "11-20-2018";
        } else{
            return "11-25-2018";
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

}
