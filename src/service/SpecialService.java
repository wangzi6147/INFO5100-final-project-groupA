package service;

import java.util.List;

import dto.Special;
import dto.Vehicle;

public interface SpecialService {
	List<Vehicle> addSpecial(Special special, List<Vehicle> vehicles);
}
