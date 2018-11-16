package DataBase;

import dto.Dealer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBase {
    private String url = "jdbc:mysql://119.23.73.176:3306/car?useSSL=false";
    private String userName="root";
    private String password="123456";
    private Connection connection;
    public void connectDB() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Success load connection");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Dealer dealer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO car.dealer (name, add1, add2, city, state, zip) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1,  dealer.getName());
            preparedStatement.setString(2,  dealer.getAddress1());
            preparedStatement.setString(3,  dealer.getAddress2());
            preparedStatement.setString(4,  dealer.getCity());
            preparedStatement.setString(5,  dealer.getState());
            preparedStatement.setString(6,  dealer.getZip());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataBase dataBase = new DataBase();
        dataBase.connectDB();
        dataBase.insert(new Dealer("Dylan", "add1","add2", "Seattle", "Washington", "98109"));
    }
}
