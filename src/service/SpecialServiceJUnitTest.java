package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


import dto.BodyType;
import dto.Special;
import dto.Vehicle;

public class SpecialServiceJUnitTest {
	
	@Test
	public void TestaddSpecial() {
		SpecialServiceImpl test = new SpecialServiceImpl();
		List<Vehicle> vehicles = new ArrayList<>(); 
		Vehicle v1 = new Vehicle("1","2018","BMW","",true,"","","",BodyType.SUV,"");
		Vehicle v2 = new Vehicle("2","2017","BMW","",false,"","","",BodyType.CAR,"");
		Vehicle v3 = new Vehicle("3","2018","BenZ","",true,"","","",BodyType.VAN,"");
		vehicles.add(v1);
		vehicles.add(v2);
		vehicles.add(v3);
		Special s1 = new Special("1","1","","","","BMW","2018",true,null,"",null);
		test.addSpecial(s1, vehicles);
		String testId = vehicles.get(0).getSpecialIDs().get(0);
		assertEquals(s1.getId(),testId);
	}
	
	@Test
	public void TestdeleteSpecial() {
		
	}
	
	@Test
	public void TestassociateSpecials() {
		
	}
}
