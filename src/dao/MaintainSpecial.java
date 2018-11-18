package dao;
import dto.*;
import java.sql.*;

public class MaintainSpecial {

    Connection conn;

    public MaintainSpecial(){
        conn = new DBconnect().connectDB();
    }


    public void addSpecial(Special s){
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO special (dealerID, startDate, endDate, title, description, disclaimer, value, scope, parameter) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1,  s.getDealerID());
            ps.setString(2,  s.getStartDate());
            ps.setString(3,  s.getEndDate());
            ps.setString(4,  s.getTitle());
            ps.setString(5,  s.getDescription());
            ps.setString(6,  s.getDisclaimer());
            ps.setString(7,  s.getValue());
            ps.setString(8,  s.getScope()==null? null :s.getScope().toString());
            ps.setString(9,  s.getScopeParameter());
            ps.executeUpdate();
            ps.close();

            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select last_insert_id()");

            if(rs.first()){
                s.setId(rs.getString(1));
                //@todo here we need to come up a solution that can solve the sql query exception or null result.
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeSpecial(Special s){

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM special WHERE id=" + s.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeSpecial(String id){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM special WHERE id=" + id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void modifySpecial(Special oldSpecial, Special newSpecial){

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dealer SET dealerID=?, startDate=?, endDate=?, title=?, description=?, disclaimer=?, value=?, scope=?, parameter=? WHERE id="+oldSpecial.getId());
            ps.setString(1,  newSpecial.getDealerID());
            ps.setString(2,  newSpecial.getStartDate());
            ps.setString(3,  newSpecial.getEndDate());
            ps.setString(4,  newSpecial.getTitle());
            ps.setString(5,  newSpecial.getDescription());
            ps.setString(6,  newSpecial.getDisclaimer());
            ps.setString(7,  newSpecial.getValue());
            ps.setString(8,  newSpecial.getScope()==null? null :newSpecial.getScope().toString());
            ps.setString(9,  newSpecial.getScopeParameter());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }


}
