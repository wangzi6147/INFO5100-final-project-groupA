package dao;
import dto.*;
import java.sql.*;


public class MaintainDealer {

    Connection conn;

    public MaintainDealer(){
        conn = DBconnect.connectDB();
    }

    //Basic infos modification.
    public void addDealer(Dealer dealer){
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dealer (name, add1, add2, city, state, zip) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1,  dealer.getName());
            ps.setString(2,  dealer.getAddress1());
            ps.setString(3,  dealer.getAddress2());
            ps.setString(4,  dealer.getCity());
            ps.setString(5,  dealer.getState());
            ps.setString(6,  dealer.getZip());
            ps.executeUpdate();
            ps.close();

            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select last_insert_id()");

            if(rs.first()){
                dealer.setId(rs.getString(1));
                //@todo here we need to come up a solution that can solve the sql query exception or null result.
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDealer(Dealer dealer){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM dealer WHERE id=" + dealer.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void deleteDealer(String dealerID){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM dealer WHERE id="+dealerID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void modifyDealer(Dealer oldDealer, Dealer newDealer){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE dealer SET name=?, add1=?, add2=?, city=?, state=?, zip=? WHERE id="+oldDealer.getId());
            preparedStatement.setString(1,  newDealer.getName());
            preparedStatement.setString(2,  newDealer.getAddress1());
            preparedStatement.setString(3,  newDealer.getAddress2());
            preparedStatement.setString(4,  newDealer.getCity());
            preparedStatement.setString(5,  newDealer.getState());
            preparedStatement.setString(6,  newDealer.getZip());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
