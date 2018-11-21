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
            List<Special> specials = new SpecialQuery().getSpecialsByDealerID(dealerID);
            if(specials == null || specials.isEmpty()){
                return;
            }

            VehicleFilterSelected p = new VehicleFilterSelected(dealerID);
            List<Vehicle> vehicles = new VehicleQuery().getAllVehiclesByFilter(p);


            //@todo must not use those expired ones.
            //@todo RULES

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
