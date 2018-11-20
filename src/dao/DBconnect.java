package dao;

import java.sql.*;

public class DBconnect {
    //119.23.73.176
    private static String url = "jdbc:mysql://119.23.73.176:3306/car?useSSL=false";
    private static String userName="root";
    private static String password="123456";
    private static Connection connection;
    public static Connection connectDB(){
        if (connection != null)  return connection;
        try {
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Connect to DB successful! ");
            return connection;
        } catch (SQLException e) {
            System.out.println("Failed connecting to DB ! ");
            e.printStackTrace();
            return null;
        }
    }
}
