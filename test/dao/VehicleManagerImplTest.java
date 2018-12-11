package dao;

import dto.BodyType;
import dto.Vehicle;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class VehicleManagerImplTest {
    VehicleManagerImpl VMITest =new VehicleManagerImpl();
    @Test
    public void findVehicleById() throws SQLException {
        Vehicle v = VMITest.findVehicleById(415756673);
        assertEquals("ID get from database should match input ID.",415756673,Integer.parseInt(v.getId()));
    }

    @Test
    public void findAllVehiclesByDealerId() throws SQLException{
        List<Vehicle> vlist =VMITest.findAllVehiclesByDealerId(String.valueOf(10153));
        for(Vehicle v : vlist){
            assertEquals("Vehicle's dealerID should be 10153",String.valueOf(10153),v.getDealerID());
        }
    }

    
    @Test
    public void maintainVehicles() throws SQLException {
        //save test
        Vehicle v =new Vehicle("10153","2018","Benz","CTS Sedan",false,"50000","White","White", BodyType.CAR,"8000");
        String addedID=VMITest.maintainVehicle(v);
        assertEquals("ID should be same as added ID.",true,addedID!=null);
        //update test
        Vehicle v1= VMITest.findVehicleById(415756673);
        v1.setPrice("48000.0");
        VMITest.maintainVehicle(v1);
        assertEquals("After update, the price should be 48000.", "48000.0",VMITest.findVehicleById(415756673).getPrice());
    }



    @Test
    public void deleteVehicle() throws SQLException{
       VMITest.deleteVehicle(String.valueOf(228104413));
       Vehicle v= VMITest.findVehicleById(228104413);
       assertEquals("return value should be -1",v.getId(),"-1");
    }
    

}