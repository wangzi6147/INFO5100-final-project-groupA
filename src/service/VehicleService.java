package service;

import dto.Vehicle;

import java.sql.SQLException;

public interface VehicleService {

    Vehicle findVehicleById(int vehicleId) throws SQLException;
}
