package dao;
import dto.*;
import java.sql.*;
import java.util.*;

public class VehicleQuery {



    public List<Vehicle> getVehiclesByFilter(VehicleFilterSelected parameter){

        //@todo select * from vechile where XXX =XXXX order by XXX ,XXX limit XXX,XXX;

    }

    public VehicleFilterContent getVehicleFilerContent(){
        //@todo select distinct XXXX from vehicle.
    }



//    public Vehicle getVehicleByID(String id){
//
//    }
//    public List<String> getVehicleIDsByDealerID(String dealerID){
//
//    }
//    public List<String> getVehicleIDsByCity(String city){
//
//    }
//    public List<String> getVehicleIDsByType(BodyType type){
//
//    }

}
