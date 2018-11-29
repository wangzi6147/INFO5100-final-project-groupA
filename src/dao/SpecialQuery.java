package dao;
import dto.*;
import java.sql.*;
import java.util.*;


public class SpecialQuery {

    Connection conn;

    public SpecialQuery(){
        conn = DBconnect.connectDB();
    }

    public Special getSpecialByID(String id) throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM special WHERE id=" + id);
        Special sp = null;
        if(rs.first()){
            sp = createSpecialFromRS(rs);
        }
        return sp;
    }

    public List<Special> getValidSpecialsByDealerID(String dealerID) throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM special WHERE dealerID=" + dealerID +" AND to_days(endDate)>=to_days(now())");
        List<Special> res = new ArrayList<>();
        while(rs.next()){
            Special sp = createSpecialFromRS(rs);
            res.add(sp);
        }
        return res;
    }


    public List<Special> getAllSpecialsByDealerID(String dealerID) throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM special WHERE dealerID=" + dealerID );
        List<Special> res = new ArrayList<>();
        while(rs.next()){
            Special sp = createSpecialFromRS(rs);
            res.add(sp);
        }
        return res;
    }


//    public List<Special> getMutexValidSpecialsByDealerID(String dealerID) throws SQLException{
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT * FROM special WHERE dealerID=" + dealerID + " AND isMutex=1 AND to_days(endDate)>=to_days(now())");
//        List<Special> res = new ArrayList<>();
//        while(rs.next()){
//            Special sp = createSpecialFromRS(rs);
//            res.add(sp);
//        }
//        return res;
//    }
//
//
//    public List<Special> getNonMutexValidSpecialsByDealerID(String dealerID) throws SQLException{
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT * FROM special WHERE dealerID=" + dealerID +" AND isMutex=0 AND to_days(endDate)>=to_days(now())");
//        List<Special> res = new ArrayList<>();
//        while(rs.next()){
//            Special sp = createSpecialFromRS(rs);
//            res.add(sp);
//        }
//        return res;
//    }


    public List<Special> getAllSpecials() throws SQLException{

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM special");
        List<Special> res = new ArrayList<>();
        while(rs.next()){
            Special s = createSpecialFromRS(rs);
            res.add(s);
        }
        return res;
    }



    private Special createSpecialFromRS(ResultSet rs) throws SQLException {

        Special s = new Special(rs.getString("dealerID"),rs.getString("endDate"),rs.getString("title"),
                SpecialScope.valueOf(rs.getString("scope")),rs.getString("scopeParameter"),
                rs.getBoolean("isMutex"), rs.getString("value"),ValueType.valueOf(rs.getString("valueType")));
        s.setId(rs.getString("id"));
        s.setStartDate(rs.getString("startDate"));
        s.setDescription(rs.getString("description"));
        s.setDisclaimer(rs.getString("disclaimer"));
        return s;
    }
}
