package InUi;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class VehiclesSystem{

    private JTable vehicleTable;
    private JButton add, view, modify, search;
    private JTextField enterDealerID;
    private JLabel vehicleDetails;
    private String selectedID = "-1";
    // private JTextPane detail;
    private Font font;
    private Font font_text;

    public VehiclesSystem(Vehicles vehicles){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        VTableModel model = new VTableModel(vehicles);
        vehicleTable = new JTable(model);
        vehicleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int idx = vehicleTable.getSelectedRow();
                selectedID = vehicleTable.getValueAt(idx,0).toString();
                System.out.println(idx);
            }
        });
        //vehicleTable.setPreferredScrollableViewportSize(new DimensionUIResource(500,900));


        font = new Font("Helvetica", Font.PLAIN, 20);
        font_text = new Font("Helvetica", Font.PLAIN,15);

        vehicleDetails = new JLabel("vehicle details");
        vehicleDetails.setPreferredSize(new DimensionUIResource(125,50));
        vehicleDetails.setFont(font);

        add = new JButton("add");
        add.setPreferredSize(new DimensionUIResource(125,40));
        add.setFont(font);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedID == "-1"){
                    JOptionPane.showMessageDialog(frame,"Select a Row" + selectedID);
                }
                else{
                    JOptionPane.showMessageDialog(frame,"You can move to next page" + selectedID);

                }

            }
        });


        view = new JButton("view");
        view.setPreferredSize(new DimensionUIResource(125,40));
        view.setFont(font);
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedID == "-1"){
                    JOptionPane.showMessageDialog(frame,"Select a Row" + selectedID);
                }
                else{
                    JOptionPane.showMessageDialog(frame,"You can move to next page" + selectedID);

                }

            }
        });

        modify = new JButton("modify");
        modify.setPreferredSize(new DimensionUIResource(125,40));
        modify.setFont(font);
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedID == "-1"){
                    JOptionPane.showMessageDialog(frame,"Select a Row" + selectedID);
                }
                else{
                    JOptionPane.showMessageDialog(frame,"You can move to next page" + selectedID);

                }

            }
        });

        search = new JButton("search");
        search.setPreferredSize(new DimensionUIResource(125,40));
        search.setFont(font);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        enterDealerID = new JTextField("100");
        enterDealerID.setPreferredSize(new DimensionUIResource(150,40));
        enterDealerID.setFont(font_text);


        Container con = frame.getContentPane();

        JPanel panel = new JPanel();
        panel.add(vehicleDetails);
        con.add(panel, BorderLayout.PAGE_START);

        JScrollPane jsp = new JScrollPane(vehicleTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        con.add(jsp, BorderLayout.CENTER);

        JPanel p = new JPanel();
        p.add(enterDealerID);
        p.add(search);
        p.add(add);
        p.add(view);
        p.add(modify);
        con.add(p,BorderLayout.PAGE_END);

        frame.setContentPane(con);
        frame.setSize(1000,600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Vehicles v = new Vehicles();
        v.addVehicle(new Vehicle(001, "KE", "BMW", "1"));
        v.addVehicle(new Vehicle(002, "AE", "VM", "1"));
        v.addVehicle(new Vehicle(003, "GE", "TOYOTA", "1"));
        v.addVehicle(new Vehicle(001, "KE", "BMW", "1"));
        v.addVehicle(new Vehicle(001, "KE", "BMW", "1"));
        v.addVehicle(new Vehicle(001, "KE", "BMW", "1"));

        new VehiclesSystem(v);


    }
}

class Vehicles{
    private Collection<Vehicle> vehicles = new ArrayList<Vehicle>();
    public Collection<Vehicle> getVehicles() {
        return vehicles;
    }
    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }
    public int getNumberOfVehicles() {
        return getVehicles().size();
    }
}

class Vehicle{
    int id;
    String name;
    String type;
    String available;

    public Vehicle(int id, String name, String type,  String available){
        this.id = id;
        this.name = name;
        this.type = type;
        this.available = available;
    }
}

class VTableModel implements TableModel {

    private Vehicles vehicles;

    public VTableModel(Vehicles vehicles){
        this.vehicles = vehicles;
    }

    @Override
    public int getRowCount() {
        return vehicles.getNumberOfVehicles();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if(columnIndex == 0){
            return "ID";
        }
        if(columnIndex == 1){
            return "NAME";
        }
        if(columnIndex == 2){
            return "TYPE";
        }
        if(columnIndex == 3){
            return "available";
        }
        return null;
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
        Collection<Vehicle> v = vehicles.getVehicles();
        int row = -1;
        for(Vehicle vehicle: v){

            row ++;
            if(row == rowIndex){
                if(columnIndex == 0){
                    return vehicle.id + "";
                }
                if (columnIndex == 1) {
                    return vehicle.name;
                }
                if (columnIndex == 2) {
                    return vehicle.type;
                }
                if (columnIndex == 3) {
                    return vehicle.available;
                }
            }
        }
        return null;
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