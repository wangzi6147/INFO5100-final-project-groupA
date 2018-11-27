package dao;

import dto.*;

import java.sql.*;
import java.util.*;

public class DealerQuery {

    Connection conn;

    public DealerQuery() {
        conn =  DBconnect.connectDB();
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


    public List<String> getAllDealerIDs() throws SQLException{
        Statement stm = conn.createStatement();
        ResultSet rs =stm.executeQuery("SELECT id FROM dealer");
        List<String> res = new ArrayList<>();
        while(rs.next()){
            res.add(rs.getString(1));
        }
        return res;
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

    public DealerQueryResponse findDealersByNameAndCityWithPageNumber(String dealerName, String city, int pageNumber) {
        try {
            Statement stm = conn.createStatement();
            String sql = querySql("SELECT SQL_CALC_FOUND_ROWS * FROM dealer", dealerName, city, pageNumber);
            System.out.println(sql);
            ResultSet rs = stm.executeQuery(sql);
            List<Dealer> res = new ArrayList<>();
            while (rs.next()) {
                res.add(createDealerFromRS(rs));
            }
            rs = stm.executeQuery("SELECT FOUND_ROWS()");
            rs.next();
            DealerQueryResponse response = new DealerQueryResponse(res, pages(rs.getInt(1)));
            rs.close();
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int pages(int total) {
        return (total + 19)/20;
    }

    private String querySql(String base, String dealerName, String city, int pageNumber) {
        StringBuilder sql = new StringBuilder(base);
        if (!dealerName.equals("") || !city.equals("")) {
            if (!dealerName.equals("")) sql.append(" WHERE ").append("name like '%").append(dealerName).append("%'");
            if (!city.equals("")) {
                if (sql.toString().contains("WHERE")) {
                    sql.append(" AND city='").append(city).append("'");
                } else {
                    sql.append(" WHERE ").append("city='").append(city).append("'");
                }
            }
        }
        sql.append(pageSql(pageNumber));
        return sql.toString();
    }
    
    private String pageSql(int pageNumber){
        StringBuilder sql = new StringBuilder("");
        int pageSplit_start = (pageNumber-1) * 20;
        sql.append(" limit ").append(pageSplit_start).append(" , ").append(20);
        return sql.toString();
    }

    private Dealer createDealerFromRS(ResultSet rs) throws SQLException {
        Address add = new Address(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
        Dealer d = new Dealer(rs.getString(2));
        d.setAddress(add);
        d.setId(rs.getString(1));
        return d;

    }


}
