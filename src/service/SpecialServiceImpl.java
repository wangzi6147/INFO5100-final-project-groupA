package service;

import dao.*;
import dto.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class SpecialServiceImpl implements SpecialService {
	
/*
 * 
 * The following methods used memory to manage specials
 * 
 */
	
	@Override
	public List<Vehicle> addSpecial(Special special, List<Vehicle> vehicles) {
		
	}
	
	private boolean isMatch(Vehicle v, Special s) {
		
	}
	
	private boolean isYearMatch(String VYear, String SYear) {
		VYear = VYear.trim();
		if(VYear.equals(SYear)) return true;
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
            List<String> dealerIDs = new DealerManagerImpl().getAllDealerIDs();
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

            HashSet<Special> appSpecials = new HashSet<>();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date =  new Date();
            for(Vehicle v: vehicles){
		        for(Special s : specials){
		        	Date sDate = df.parse(s.getStartDate());
		            Date eDate = df.parse(s.getEndDate());
		            //if the special is Mutex, which is initialized as true
		            //only consider one UNIQUEONE
		            if(s.getScope().toString().equalsIgnoreCase("UNIQUEONE")) {
		            	s.setMutex(false);
		            }
		            if(date.before(sDate)|| date.after(eDate)) 
		            	continue;
		            if(s.getScope().toString().equalsIgnoreCase("ALL"))
		                appSpecials.add(s);
		            if(s.getScope().toString().equalsIgnoreCase("BRAND"))
		            	if(v.getBrand().equalsIgnoreCase(s.getScopeParameter()))
		            		appSpecials.add(s);
		            if(s.getScope().toString().equalsIgnoreCase("YEAR"))
		                if(v.getYear().equalsIgnoreCase(s.getScopeParameter()))
		                	appSpecials.add(s);
		            if(s.getScope().toString().equalsIgnoreCase("NEWORUSED"))
		                if((v.getIsNew() == true && s.getScopeParameter() == "NEW")||
		                   (v.getIsNew() == false && s.getScopeParameter() == "USED"))
		                	appSpecials.add(s);
		            if(s.getScope().toString().equalsIgnoreCase("BODYTYPE"))
		                if(v.getBodyType().toString().equalsIgnoreCase(s.getScopeParameter()))
		                	appSpecials.add(s);
		        }
		        v.setSpecialIDs(null);
		        Double max = Double.MIN_VALUE;
		        for(Special s:appSpecials) {
		        	double temp = Double.valueOf(s.getValue());
		        	max = max > temp ? max : temp; 
		        	v.getSpecialIDs().add(s.getId());
		        }
		        v.setDiscountRate(String.valueOf(max));
		        //@todo inside different vehicle, calculate the minimum value, and remove those 'useless' specials.
		      
            }

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


    public List<Special> getSpecialsByDealerID(String id){
        SpecialManagerImpl sq = new SpecialManagerImpl();
        List<Special> result = new ArrayList<>();
        try {
            result = sq.getAllSpecialsByDealerID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

*/

	
}
