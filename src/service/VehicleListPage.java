package service;
import dto.*;

import javax.swing.*;
import java.util.*;
public class VehicleListPage {

    List<Vehicle> vehicles;
    VehicleFilterSelected parameter;


    public void setParameter(VehicleFilterSelected parameter){
        this.parameter = parameter;
    }

    public List<ImageIcon> getImages(){

        //@todo from vehicles to get images url.
    }


    public VehicleFilterContent getFilterContent(){

        VehicleFilterContent obj = new VehicleFilterContent();

        //赋值、init
        //@todo
        return obj;
    }

    public List<Vehicle> getVehicleList(){


        //@todo

        return vehicles;

    }

}
