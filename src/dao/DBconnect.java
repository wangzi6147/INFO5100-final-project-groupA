package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Properties;

public class DBconnect {
    // in property file
    private static Connection connection;

    public static Connection connectDB(){
        if (connection == null) {
            synchronized (DBconnect.class) {
                if (connection == null) {
                    try {
                        // lock
                        Properties properties = new Properties();
                        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/app.properties");
                        properties.load(fis);
                        connection = DriverManager.getConnection(properties.getProperty("mysql.url"), properties.getProperty("mysql.username"), properties.getProperty("mysql.password"));
                        System.out.println("Connect to DB successful! ");
                    } catch (Exception e) {
                        System.out.println("Failed connecting to DB ! ");
                        e.printStackTrace();
                        return null;
                    } finally {
                        return connection;
                    }
                }
            }
        }
        return connection;
    }
}
