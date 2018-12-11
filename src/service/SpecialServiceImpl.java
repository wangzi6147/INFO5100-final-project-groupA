package service;

import dto.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import dao.DBconnect;
import dao.SpecialManagerImpl;



public class SpecialServiceImpl implements SpecialService {
	
	Connection conn;
	
	public SpecialServiceImpl() {
		conn = DBconnect.connectDB();
	}
/*
 * 
 * The following methods used memory to manage specials
 * 
 */
	
	@Override
	public int addSpecial(Special special, List<Vehicle> vehicles) {
		int addNumber = 0;
		try {
			for(Vehicle v:vehicles) {
				if(isMatch(v,special)) {
					++addNumber;
					v.getSpecialIDs().add(special.getId());
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return addNumber;
	}
	

	@Override
	public int deleteSpecial(Special special, List<Vehicle> vehicles) {
		int deleteNumber = 0;
		try {
			for(Vehicle v:vehicles) {
				if(isMatch(v,special)) {
					++deleteNumber;
					String id = special.getId();
					v.getSpecialIDs().remove(id);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return deleteNumber;
	}
	
    @Override
    public List<Special> querySpecials(List<Special> specials, String dealerID) {
		List<Special> res = new ArrayList<>();
		try {
			for(Special s:specials) {
				if(s.getDealerID().equalsIgnoreCase(dealerID.trim()))
					res.add(s);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}

	
	@Override
	public List<Vehicle> associateSpecials(List<Vehicle> vehicles, List<Special> specials) throws ParseException {
		if(specials.size() == 0 || specials == null) return vehicles;
		for(Vehicle v:vehicles) {
			HashSet<Special> matchSpecials = new HashSet<>();
			for(Special s:specials) {
				if(isMatch(v,s)) matchSpecials.add(s);
			}
			List<String> SpecialIds = new ArrayList<>();
			Double MaxDiscount = Double.MIN_VALUE;
			for(Special s:matchSpecials) {
				SpecialIds.add(s.getId());
				if(!s.getValue().equals("")) {
					Double value = Double.valueOf(s.getValue());
					MaxDiscount = MaxDiscount > value ? MaxDiscount : value;
				}
			}
			v.setSpecialIDs(SpecialIds);
			v.setDiscountRate(String.valueOf(MaxDiscount));
			Double finalPrice = 0.0;
			if(!v.getPrice().equals(""))
				finalPrice = MaxDiscount * Double.valueOf(v.getPrice());
			v.setFinalPrice(String.valueOf(finalPrice));
		}
		
		return vehicles;
	}
    
	private boolean isMatch(Vehicle v, Special s) {
		return isYearMatch(v.getYear(),s.getYear()) && isBrandMatch(v.getBrand(),s.getBrand())
			&& isNewMatch(v.getIsNew(),s.getIsNew()) && isBodyTypeMatch(v.getBodyType(),s.getBodyType());
	}
	
	private boolean isYearMatch(String VYear, String SYear) {
		VYear = VYear.trim();
		SYear = SYear.trim();
		if(VYear.equals(SYear) || SYear.equals("")) return true;
		return false;
	}
	
	private boolean isBrandMatch(String VBrand, String SBrand) {
		SBrand = SBrand.trim();
		VBrand = VBrand.trim();
		if(SBrand.equals("") || SBrand.equals(VBrand))
			return true;
		return false;
	}
	
	private boolean isNewMatch (boolean VIsNew, boolean SIsNew) {
		if(VIsNew == SIsNew)
			return true;
		return false;
	}
	
	private boolean isBodyTypeMatch(BodyType VBodyType, BodyType SBodyType) {
		if(VBodyType == SBodyType || SBodyType == null) return true;
		return false;
	}


	//the following method searching in database
    public Special getSpecialBySpecialID(String id){
        SpecialManagerImpl sq = new SpecialManagerImpl();
        Special result = null;
        try {
             result = sq.getSpecialByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
    
    public Special createSpecialFromRS(ResultSet rs) throws SQLException {

    	Special s = new Special(rs.getString("id"), rs.getString("dealerID"), rs.getString("startDate"), rs.getString("endDate"), 
    			    rs.getString("title"), rs.getString("brand"), rs.getString("year"), rs.getBoolean("isNew"), 
    			    BodyType.valueOf(rs.getString("type")), rs.getString("value"), ValueType.valueOf(rs.getString("valueType")));

        s.setDescription(rs.getString("description"));
        s.setDisclaimer(rs.getString("disclaimer"));
        return s;
    }
    
	
	
/*
 * 
 * The following methods used database to manage specials
 * 
 */
    // Suggest one operation daily.
	
/*
    public void updateAll() {

        try {
            List<String> dealerIDs = new DealerManagerImplementation().getAllDealerIDs();
            if (dealerIDs == null || dealerIDs.isEmpty()) {
                return;
            }

            for (String id : dealerIDs) {
                update(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Suggest every time after one rule applied.
    public void update(String dealerID) {

        try {
            List<Special> specials = new SpecialManagerImpl().getValidSpecialsByDealerID(dealerID);
            if (specials == null || specials.isEmpty()) {
                return;
            }

            VehicleFilterSelected p = new VehicleFilterSelected(dealerID);
            List<Vehicle> vehicles = new VehicleManagerImpl().getAllVehiclesByFilter(p);

          
            new VehicleManagerImpl().updateFinalPriceAndDiscount(vehicles);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void storeSpecial(Special special) {
        SpecialManagerImpl ms = new SpecialManagerImpl();
        ms.addSpecial(special);
    }


    public void deleteSpecial(Special special){
        SpecialManagerImpl ms = new SpecialManagerImpl();
        ms.removeSpecial(special);
    }

*/

	
}
