package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
    private String url = "jdbc:mysql://119.23.73.176:3306/car?useSSL=false";
    private String userName="root";
    private String password="123456";
    private Connection connection;
    public Connection connectDB(){
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
