package dao;
import dto.*;
import java.sql.*;
import java.util.*;

public class MaintainVehicle {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/car";
    private static final String USER = "user";
    private static final String PASSWORD = "123456";

    Connection conn;

    public MaintainVehicle(){
        conn = DBconnect.connectDB();
    }

    public void addVehicle(Vehicle v){
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO vehicle (id, year, brand, model, price, exColor, inColor, type, miles, images, dealerID, isNew, features ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1,  v.getId());
            ps.setString(2,  v.getYear());
            ps.setString(3,  v.getBrand());
            ps.setString(4,  v.getModel());
            ps.setString(5,  v.getPrice());
            ps.setString(6,  v.getExteriorColor());
            ps.setString(7,  v.getInteriorColor());
            ps.setString(8,  v.getBodyType()==null? null : v.getBodyType().toString());
            ps.setString(9,  v.getMiles());


            if(v.getImages() != null &&  !v.getImages().isEmpty()){
                StringBuilder sb = new StringBuilder();
                for(String s : v.getImages()) {
                    sb.append(s + "\n");
                }
                ps.setString(10,  sb.toString()); // all images url compressed to one string.
            }else{
                ps.setString(10,  null);
            }

            ps.setString(11,  v.getDealerID());
            ps.setBoolean(12,  v.getIsNew());

            if(v.getFeatures() != null &&  !v.getFeatures().isEmpty()){
                StringBuilder sb = new StringBuilder();
                for(String s : v.getFeatures()){
                    sb.append(s + "\n");
                }
                ps.setString(13,  sb.toString());
            }else{
                ps.setString(13,  null);
            }

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void deleteVehicle(Vehicle v){

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM vehicle WHERE id=" + v.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteVehicle(String id){

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM vehicle WHERE id=" + id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void modifyVehicle(Vehicle oldVehicle, Vehicle newVehicle){

        deleteVehicle(oldVehicle);
        addVehicle(newVehicle);

    }

    public void updateFinalPriceAndDiscount(List<Vehicle> vehicles) throws SQLException{

        Statement stmt = conn.createStatement();
        StringBuffer sql = new StringBuffer("UPDATE vehicle SET finalPrice = CASE id ");
        for(Vehicle v : vehicles){
            sql.append(" when "+ v.getId() + " then " + v.getFinalPrice());
        }
        sql.append(" END, discountRate = CASE id");
        for(Vehicle v : vehicles){
            sql.append(" when "+ v.getId() + " then " + v.getDiscountRate());
        }
        sql.append( " END where id in (");
        for(Vehicle v :vehicles){
            sql.append(v.getId() + ",");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(")");
        System.out.println(sql);
        stmt.executeUpdate(sql.toString());
        stmt.close();
    }

}
