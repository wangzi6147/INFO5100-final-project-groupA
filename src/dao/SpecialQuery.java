package dao;
import dto.*;
import java.sql.*;
import java.util.*;


public class SpecialQuery {

    Connection conn;

//    public SpecialQuery(){
//        conn = new DBconnect().connectDB();
//    }
//
//    public Special getSpecialByID(String id){
//
//    }
//    public List<Special> getSpecialsByDealerID(String dealerID){
//
//    }
//    public List<Special> getSpecialsByScope(SpecialScope scope){
//
//    }
//    public List<Special> getSpecialsByDate(String date){
//
//    }



    private Special createSpecialFromRS(ResultSet rs) throws SQLException {

        Special s = new Special(rs.getString(2),rs.getString(4),rs.getString(5),rs.getString(8),SpecialScope.valueOf(rs.getString(9)),rs.getString(10));
        s.setId(rs.getString(1));
        s.setStartDate(rs.getString(3));
        s.setDescription(rs.getString(6));
        s.setDisclaimer(rs.getString(7));
        return s;
    }
}
