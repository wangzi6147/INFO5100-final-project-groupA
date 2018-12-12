package ui.special;
import dto.Special;
import service.SpecialServiceImpl;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.List;




public class SpecialTableModel implements TableModel{

    public static SpecialServiceImpl specialServiceImpl;


    public static final String[] ColumnName = {"Title", "Description", "Disclaimer", "Value", "Start Date", "End Date"};
    private List<Special> list;

    public SpecialTableModel(String dealerId) {
        specialServiceImpl = new SpecialServiceImpl();
        try {
            this.list = specialServiceImpl.getAllSpecialsByDealerID(dealerId);
        }
        catch(SQLException se){
            se.printStackTrace();
        }

    }

    @Override
    public int getRowCount() {
        return list.size();
        //return 10;
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


        //List<Special> list = new ArrayList<>();

        Special record = list.get(rowIndex);
        if (columnIndex == 0) {
            return record.getTitle();
        } else if (columnIndex == 1) {
            return record.getDescription();
        } else if (columnIndex == 2) {
            return record.getDisclaimer();
        } else if (columnIndex == 3) {
            return record.getValue();
        } else if (columnIndex == 4) {
            return record.getStartDate();
        } else{
            return record.getEndDate();
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
