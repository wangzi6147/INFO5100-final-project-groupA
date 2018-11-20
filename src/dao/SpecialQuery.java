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

    public List<Special> getSpecialsByDealerID(String dealerID) throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM special WHERE dealerID=" + dealerID);
        List<Special> res = new ArrayList<>();
        while(rs.next()){
            Special sp = createSpecialFromRS(rs);
            res.add(sp);
        }
        return res;
    }


    private Special createSpecialFromRS(ResultSet rs) throws SQLException {

        Special s = new Special(rs.getString(2),rs.getString(4),rs.getString(5),rs.getString(8),SpecialScope.valueOf(rs.getString(9)),rs.getString(10));
        s.setId(rs.getString(1));
        s.setStartDate(rs.getString(3));
        s.setDescription(rs.getString(6));
        s.setDisclaimer(rs.getString(7));
        return s;
    }
}
