package dao;

import dto.*;
import java.sql.*;
import java.util.*;

public class DealerQuery {

    Connection conn;

    public DealerQuery() {
        conn = new DBconnect().connectDB();
    }


    public List<String> getCityList(){
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT DISTINCT city FROM dealer");

            List<String> cities = new ArrayList<>();
            while(rs.next()){
                cities.add(rs.getString(1));
            }
            rs.close();
            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Dealer findDealerByID(String id) {
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM dealer WHERE id=" + id);

            if (rs.first()) {
                return createDealerFromRS(rs);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countDealersByCity(String city) {

        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT COUNT(city) FROM dealer WHERE city='" + city + "'");

            if (rs.first()) {
                return rs.getInt(1);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public List<Dealer> findDealersByCity(String city) {
        try {

            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM dealer WHERE city='" + city + "'");

            List<Dealer> res = new ArrayList<>();
            while (rs.next()) {
                res.add(createDealerFromRS(rs));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Dealer> findDealersByPostCode(int postCode){

        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM dealer WHERE zip='" + postCode + "'");

            List<Dealer> res = new ArrayList<>();
            while (rs.next()) {
                res.add(createDealerFromRS(rs));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


    private Dealer createDealerFromRS(ResultSet rs) throws SQLException {

        Address add = new Address(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
        Dealer d = new Dealer(rs.getString(2));
        d.setAddress(add);
        d.setId(rs.getString(1));
        return d;

    }


}
