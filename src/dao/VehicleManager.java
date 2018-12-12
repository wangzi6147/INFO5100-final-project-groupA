package dao;

import dto.Vehicle;

import java.sql.SQLException;

public interface VehicleManager {
    String maintainVehicle(Vehicle vehicle) throws SQLException;
    boolean deleteVehicle(String vehicleId) throws SQLException;
}
