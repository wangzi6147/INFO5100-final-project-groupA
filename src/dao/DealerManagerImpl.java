package dao;

import dto.*;

import java.sql.*;
import java.util.*;

public class DealerManagerImpl implements DealerManager {

    Connection conn;

    public DealerManagerImpl() {
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
            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // rs.close();

        }
        // when exception
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

    public List<Dealer> findDealersByName(String name) {
        try {

            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM dealer WHERE name='" + name + "'");

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

    public DealerQueryResponse searchDealers(int postCode,int lines) throws SQLException
    {
        Statement stm = conn.createStatement();
        ResultSet rs = null;
        List<Dealer> dealerList = new ArrayList<>();
        DealerQueryResponse response = null;
        try
        {
            rs = stm.executeQuery("SELECT SQL_CALC_FOUND_ROWS * FROM dealer WHERE zip="+ postCode +" LIMIT "+lines );

            while (rs.next())
            {
                dealerList.add(createDealerFromRS(rs));
            }
            rs = stm.executeQuery("SELECT FOUND_ROWS()");
            rs.next();
            response = new DealerQueryResponse(dealerList, pages(rs.getInt(1)));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            response = new DealerQueryResponse(new ArrayList<>(), 0);
        }
        finally
        {
            rs.close();
        }
        return response;
    }

    /**
     * findDealersByNameAndCityWithPageNumber
     * method overloading
     * parameters = dealerName , city, pageNumber
     * @throws SQLException
     */

    public DealerQueryResponse searchDealers(String dealerName, String city, int pageNumber) throws SQLException
    {
        List<Dealer> dealerList = new ArrayList<>();
        Statement stm = conn.createStatement();
        ResultSet rs = null;
        DealerQueryResponse response = null;

        try
        {
            String sql = querySql("SELECT SQL_CALC_FOUND_ROWS * FROM dealer", dealerName, city, pageNumber);
            rs = stm.executeQuery(sql);

            while (rs.next())
            {
                dealerList.add(createDealerFromRS(rs));
            }
            rs = stm.executeQuery("SELECT FOUND_ROWS()");
            rs.next();
            response = new DealerQueryResponse(dealerList, pages(rs.getInt(1)));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            response = new DealerQueryResponse(new ArrayList<>(), 0);
        }
        finally
        {
            rs.close();
        }
        return response;
    }


//    public DealerQueryResponse findDealersByPostCodeWithinLines(int postCode,int lines) {
//       try {
//           Statement stm = conn.createStatement();
//           ResultSet rs = stm.executeQuery("SELECT SQL_CALC_FOUND_ROWS * FROM dealer WHERE zip="+ postCode +" LIMIT "+lines );
//           List<Dealer> res = new ArrayList<>();
//           while (rs.next()) {
//               res.add(createDealerFromRS(rs));
//           }
//           rs = stm.executeQuery("SELECT FOUND_ROWS()");
//           rs.next();
//           DealerQueryResponse response = new DealerQueryResponse(res, pages(rs.getInt(1)));
//           rs.close();
//           return response;
//       } catch (SQLException e) {
//           e.printStackTrace();
//       }
//       return null;
//   }
    
//    public DealerQueryResponse findDealersByNameAndCityWithPageNumber(String dealerName, String city, int pageNumber) {
//        try {
//            Statement stm = conn.createStatement();
//            String sql = querySql("SELECT SQL_CALC_FOUND_ROWS * FROM dealer", dealerName, city, pageNumber);
//            System.out.println(sql);
//            ResultSet rs = stm.executeQuery(sql);
//            List<Dealer> res = new ArrayList<>();
//            while (rs.next()) {
//                res.add(createDealerFromRS(rs));
//            }
//            rs = stm.executeQuery("SELECT FOUND_ROWS()");
//            rs.next();
//            DealerQueryResponse response = new DealerQueryResponse(res, pages(rs.getInt(1)));
//            rs.close();
//            return response;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//
//        }
//        return null;
//    }

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
