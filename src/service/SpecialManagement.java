package service;
import dao.*;
import dto.*;
import java.sql.*;
import java.util.*;
public class SpecialManagement {


    // Suggest one operation daily.
    public void updateAll(){

        try {
            List<String> dealerIDs = new DealerQuery().getAllDealerIDs();
            if(dealerIDs == null || dealerIDs.isEmpty()){
                return;
            }

            for(String id : dealerIDs) {
                update(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //@todo will update given dealer's all vehicles immediately. Suggest every time after one rule applied.
    public void update(String dealerID){

        try {
            SpecialQuery sq = new SpecialQuery();
            List<Special> nonMutexSpecials = sq.getNonMutexValidSpecialsByDealerID(dealerID);
            List<Special> mutexSpecials = sq.getMutexValidSpecialsByDealerID(dealerID);
            if((nonMutexSpecials == null || nonMutexSpecials.isEmpty()) && (mutexSpecials == null || mutexSpecials.isEmpty())){
                return;
            }


            //@todo calculate nonmutex

            //@todo calculate mutex seperately.
            VehicleFilterSelected p = new VehicleFilterSelected(dealerID);
            List<Vehicle> vehicles = new VehicleQuery().getAllVehiclesByFilter(p);


            new MaintainVehicle().updateFinalPriceAndDiscount(vehicles);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void storeSpecial(Special special){
        MaintainSpecial ms = new MaintainSpecial();
        ms.addSpecial(special);
    }


}
