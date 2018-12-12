package service;

import dto.Vehicle;

import java.sql.SQLException;
import java.util.List;

public interface VehicleService {

    Vehicle findVehicleById(int vehicleId) throws SQLException;
    List<Vehicle> findAllVehiclesByDealerId(String dealerId) throws SQLException;
    String maintainVehicle(Vehicle vehicle) throws SQLException;
    boolean deleteVehicleByVehicleId(String vehicleId) throws SQLException;
}
