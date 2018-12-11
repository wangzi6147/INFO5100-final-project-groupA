package dao;

import dto.*;

import java.sql.*;
import java.util.*;

public class DealerManagerImpl implements DealerManager {

    Connection conn;

    public DealerManagerImpl() {
        conn = DBconnect.connectDB();
    }

    @Override
    public DealerQueryResponse searchDealers(int postCode, int lines) throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet rs = null;
        List<Dealer> dealerList = new ArrayList<>();
        DealerQueryResponse response = null;
        try {
            rs = stm.executeQuery("SELECT SQL_CALC_FOUND_ROWS * FROM dealer WHERE zip=" + postCode + " LIMIT " + lines);

            while (rs.next()) {
                dealerList.add(createDealerFromRS(rs));
            }
            rs = stm.executeQuery("SELECT FOUND_ROWS()");
            rs.next();
            response = new DealerQueryResponse(dealerList, pages(rs.getInt(1)));
        } catch (SQLException e) {
            e.printStackTrace();
            response = new DealerQueryResponse(new ArrayList<>(), 0);
        } finally {
            rs.close();
        }
        return response;
    }

    /**
     * find Dealers By Name And City With PageNumber
     * method overloading
     * parameters = dealerName , city, pageNumber
     *
     * @throws SQLException
     */

    @Override
    public DealerQueryResponse searchDealers(String dealerName, String city, int pageNumber) throws SQLException {
        List<Dealer> dealerList = new ArrayList<>();
        Statement stm = conn.createStatement();
        ResultSet rs = null;
        DealerQueryResponse response = null;

        try {
            String sql = querySql("SELECT SQL_CALC_FOUND_ROWS * FROM dealer", dealerName, city, pageNumber);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                dealerList.add(createDealerFromRS(rs));
            }
            rs = stm.executeQuery("SELECT FOUND_ROWS()");
            rs.next();
            response = new DealerQueryResponse(dealerList, pages(rs.getInt(1)));
        } catch (SQLException e) {
            e.printStackTrace();
            response = new DealerQueryResponse(new ArrayList<>(), 0);
        } finally {
            rs.close();
        }
        return response;
    }

    private int pages(int total) {
        return (total + 19) / 20;
    }

    private String querySql(String base, String dealerName, String city, int pageNumber) {
        StringBuilder sql = new StringBuilder(base);
        if (!dealerName.equals("") || !city.equals("")) {
            if (!dealerName.equals(""))
                sql.append(" WHERE ").append("name like '%").append(dealerName).append("%'");
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

    private String pageSql(int pageNumber) {
        int pageSplit_start = (pageNumber - 1) * 20;
        return " limit " + pageSplit_start + " , " + 20;
    }

    private Dealer createDealerFromRS(ResultSet rs) throws SQLException {
        Address add = new Address(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
        Dealer d = new Dealer(rs.getString(2));
        d.setAddress(add);
        d.setId(rs.getString(1));
        return d;
    }

}
