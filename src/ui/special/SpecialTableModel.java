package ui.special;
import dto.Special;
import service.SpecialServiceImpl;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.List;




public class SpecialTableModel implements TableModel{

    public static SpecialServiceImpl specialServiceImpl;
    private List<Special> list;

    public static final String[] ColumnName = {"Title", "Start Date", "End Date","brand","discount Info", "Description",
            "Disclaimer"};


    public SpecialTableModel(String dealerId) {
        specialServiceImpl = new SpecialServiceImpl();
        try {
            this.list = specialServiceImpl.getAllSpecialsByDealerID(dealerId);
        }
        catch(SQLException se){
            se.printStackTrace();
        }

    }
    public int getlistsize(){
        return list.size();
    }
    @Override
    public int getRowCount() {
        return list.size();
        //return 4;
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
        /*
        if (rowIndex == 0) {
            if (columnIndex == 0) {
                return "Christmas Sale";
            } else if (columnIndex == 1) {
                return "2018-12-20";
            } else if (columnIndex == 2) {
                return "2018-12-30";
            } else if (columnIndex == 3) {
                return "BMW";
            } else if (columnIndex == 4) {
                return "15% off";
            } else if (columnIndex == 5) {
                return "Marry Christmas";
            } else {
                return "All rights reserved";
            }
        }
        if (rowIndex == 1) {
            if (columnIndex == 0) {
                return "Thanks Giving Sale";
            } else if (columnIndex == 1) {
                return "2018-11-20";
            } else if (columnIndex == 2) {
                return "2018-11-24";
            } else if (columnIndex == 3) {
                return "All";
            } else if (columnIndex == 4) {
                return "1000 off every 12000";
            } else if (columnIndex == 5) {
                return "Happy Thanks Giving";
            } else {
                return "All rights reserved";
            }
        }
        if (rowIndex == 2) {
            if (columnIndex == 0) {
                return "All year Sale";
            } else if (columnIndex == 1) {
                return "2018-01-01";
            } else if (columnIndex == 2) {
                return "2018-12-31";
            } else if (columnIndex == 3) {
                return "Ford";
            } else if (columnIndex == 4) {
                return "5% off";
            } else if (columnIndex == 5) {
                return "Ford year";
            } else {
                return "All rights reserved";
            }
        }
        if (rowIndex == 3) {
            if (columnIndex == 0) {
                return "Winter Sale";
            } else if (columnIndex == 1) {
                return "2018-11-01";
            } else if (columnIndex == 2) {
                return "2019-02-01";
            } else if (columnIndex == 3) {
                return "Toyota";
            } else if (columnIndex == 4) {
                return "1000 off every 10000";
            } else if (columnIndex == 5) {
                return "Stay warm";
            } else {
                return "All rights reserved";
            }
        }
       return null;
*/

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

