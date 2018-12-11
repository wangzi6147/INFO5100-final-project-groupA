package dao;
import dto.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialManagerImpl implements SpecialManager {

    Connection conn;

    public SpecialManagerImpl(){
        conn = DBconnect.connectDB();
    }

    /**
     * Special Query
     */
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



    public Special createSpecialFromRS(ResultSet rs) throws SQLException {

    	Special s = new Special(rs.getString("id"), rs.getString("dealerID"), rs.getString("startDate"), rs.getString("endDate"), 
    			    rs.getString("title"), rs.getString("brand"), rs.getString("year"), rs.getBoolean("isNew"), 
    			    BodyType.valueOf(rs.getString("type")), rs.getString("value"), ValueType.valueOf(rs.getString("valueType")));

        s.setDescription(rs.getString("description"));
        s.setDisclaimer(rs.getString("disclaimer"));
        return s;
    }

    /**
     * Maintain Special
     */
    public void addSpecial(Special s){
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO special (dealerID, startDate, endDate, title, description, disclaimer, value, brand, isNew, year, type, isMutex, valueType ) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1,  s.getDealerID());
            ps.setString(2,  s.getStartDate());
            ps.setString(3,  s.getEndDate());
            ps.setString(4,  s.getTitle());
            ps.setString(5,  s.getDescription());
            ps.setString(6,  s.getDisclaimer());
            ps.setString(7,  s.getValue());
            ps.setString(8,s.getBrand());
            ps.setBoolean(9,s.getIsNew());
            ps.setString(10,s.getYear());
            ps.setString(11,s.getBodyType().toString());
            ps.setBoolean(12,  s.isMutex());
            ps.setString(13,  s.getValueType().toString());
            ps.executeUpdate();
            ps.close();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select last_insert_id()");
            if(rs.first()){
                s.setId(rs.getString(1));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeSpecial(Special s){

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM special WHERE id=" + s.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeSpecial(String id){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM special WHERE id=" + id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
