package dao;

import main.Components.Dealer;
import java.sql.*;

public class maintainDealer {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/car";
    private static final String USER = "user";
    private static final String PASSWORD = "123456";


    public maintainDealer(){


    }

    //Basic infos modification.
    public void addDealer(Dealer d){

    }

    public void delteDealer(Dealer d){

    }
    public void delteDealer(String dealerID){

    }

    public void modifyDealer(Dealer oldInfo, Dealer newInfo){

    }






//    public static void main(String[] args) {
//        try {
//
//            Class.forName(DRIVER);
//            Connection con = DriverManager.getConnection(DBURL, USER, PASSWORD);
//
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("select * from dealer");
//            while (rs.next())
//                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
//            con.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//
//    }
}
