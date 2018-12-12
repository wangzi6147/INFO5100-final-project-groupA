package InUi;

import service.VehicleService;

import service.VehicleServiceImpl;
import dto.Vehicle;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VehiclesSystem{

	
	
    public JTable vehicleTable;
    private JButton add, view, modify, search;
    private JTextField enterDealerID;
    private JLabel vehicleDetails;
    private String selectedID = "-1";
    private String theDealerID  ;
    private JLabel toEnterDealerID;
    public Vehicles vehicles;
    public VTableModel model;
    // private JTextPane detail;
    private Font font;
    private Font font_text;
    private VehicleService vehicleService;
    public VehiclesSystem(Vehicles vehicles){
        vehicleService = new VehicleServiceImpl();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        model = new VTableModel(vehicles);
        this.vehicles = vehicles;
        vehicleTable = new JTable(model);
        vehicleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int idx = vehicleTable.getSelectedRow();
                selectedID = vehicleTable.getValueAt(idx,1).toString();
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
                
                	 AddPage addPage = new AddPage();
                	 addPage.setAddPageLayout(theDealerID);
               

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
//                }else if (theDealerID == "-1") {
//                	JOptionPane.showMessageDialog(frame,"input dealerID first");
                }else{
                	ViewPage viewPage = new ViewPage();
                	viewPage.setViewOrModifyPageLayout(selectedID);
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
//                }else if (theDealerID == "-1") {
//                	JOptionPane.showMessageDialog(frame,"input dealerID first");
                }
                else{
                	ModifyPage modifyPage = new ModifyPage();
                	modifyPage.setViewOrModifyPageLayout(selectedID);

                }

            }
        });

        search = new JButton("search");
        search.setPreferredSize(new DimensionUIResource(125,40));
        search.setFont(font);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theDealerID = enterDealerID.getText();
//                VehicleService vehicleService = new VehicleServiceImpl();
                List<Vehicle> vehicleslist = null;
                try {
                    System.out.println("executed");
                    vehicleslist = vehicleService.findAllVehiclesByDealerId(theDealerID);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
//                Vehicles v = new Vehicles();
//                v.vehicles = vehicleslist;
                VehiclesSystem.this.vehicles.vehicles = vehicleslist;
//                VehiclesSystem.this.model
                VehiclesSystem.this.vehicleTable.validate();
                VehiclesSystem.this.vehicleTable.updateUI();
//                VehiclesSystem.this.vehicle
//                v.vehicles =  vehicles;

//                theDealerID.

            }
        });

        enterDealerID = new JTextField("");
        enterDealerID.setPreferredSize(new DimensionUIResource(125,40));
        enterDealerID.setFont(font);

        toEnterDealerID = new JLabel("Enter Dealer ID:");
        toEnterDealerID.setPreferredSize(new DimensionUIResource(150,40));
        toEnterDealerID.setFont(font);

        Container con = frame.getContentPane();

        JPanel panel = new JPanel();
        panel.add(vehicleDetails);
        con.add(panel, BorderLayout.PAGE_START);

        JScrollPane jsp = new JScrollPane(vehicleTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        con.add(jsp, BorderLayout.CENTER);

        JPanel p = new JPanel();
        p.add(toEnterDealerID);
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

    public static void main(String[] args) throws SQLException {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            throw new RuntimeException("Test Failed. MetalLookAndFeel not set "
                    + "for frame");
        }
    	
    	Vehicles v = new Vehicles();
//        v.addVehicle(new Vehicle(001, "KE", "BMW", "1"));
//        v.addVehicle(new Vehicle(002, "AE", "VM", "1"));
//        v.addVehicle(new Vehicle(003, "GE", "TOYOTA", "1"));
//        v.addVehicle(new Vehicle(001, "KE", "BMW", "1"));
//        v.addVehicle(new Vehicle(001, "KE", "BMW", "1"));
//        v.addVehicle(new Vehicle(001, "KE", "BMW", "1"));

        new VehiclesSystem(v);


    }
    

}

class Vehicles {

    public  Collection<Vehicle> vehicles = new ArrayList<Vehicle>();
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

// class Vehicle{
//    int id;
//    String name;
//    String type;
//    String available;
//
//    public Vehicle(int id, String name, String type,  String available){
//        this.id = id;
//        this.name = name;
//        this.type = type;
//        this.available = available;
//    }
//}


class VTableModel implements TableModel {

    private Vehicles vehicles;
    public static final String[] columnName = {"Dealer ID", "Vehicle ID","Car Inventory","Year", "Brand", "Model", "Is New", "Price", "Final Price",
    "Exterior Color", "Interior Color","Miles"};

    public VTableModel(Vehicles vehicles){
        this.vehicles = vehicles;
    }

    @Override
    public int getRowCount() {
        return vehicles.getNumberOfVehicles();
    }

    @Override
    public int getColumnCount() {
        return 11;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnName[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 6){
            return Boolean.class;
        }
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
                    return vehicle.getDealerID() + "";
                }
                if (columnIndex == 1) {
                    return vehicle.getId();
                }
                if(columnIndex == 2){
                    return vehicles.getNumberOfVehicles();
                }
                if (columnIndex == 3) {
                    return vehicle.getYear();
                }
                if (columnIndex == 4) {
                    return vehicle.getBrand();
                }
                if(columnIndex == 5){
                    return vehicle.getModel() + "";
                }
                if (columnIndex == 6) {
                    return vehicle.getIsNew();
                }
                if (columnIndex == 7) {
                    return vehicle.getPrice();
                }
                if (columnIndex == 8) {
                    return vehicle.getFinalPrice();
                }
                if (columnIndex == 9) {
                    return vehicle.getExteriorColor();
                }
                if (columnIndex == 10) {
                    return vehicle.getInteriorColor();
                }
                if (columnIndex == 11) {
                    return vehicle.getMiles();
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