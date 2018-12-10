package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
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
		SpecialServiceImpl test = new SpecialServiceImpl();
		List<Vehicle> vehicles = new ArrayList<>(); 
		Vehicle v1 = new Vehicle("1","2018","BMW","",true,"","","",BodyType.SUV,"");
		vehicles.add(v1);
		Special s1 = new Special("1098","1","","","","BMW","2018",true,null,"",null);
		Special s2 = new Special("2065","1","","","","BenZ","2018",true,null,"",null);
		v1.getSpecialIDs().add(s1.getId());
		assertEquals(v1.getSpecialIDs().size(),1);
		test.deleteSpecial(s2, vehicles);
		assertEquals(v1.getSpecialIDs().size(),1);
		test.deleteSpecial(s1, vehicles);
		assertEquals(v1.getSpecialIDs().size(),0);
	}
	
	@Test
	public void TestassociateSpecials() throws ParseException {
		SpecialServiceImpl test = new SpecialServiceImpl();
		List<Vehicle> vehicles = new ArrayList<>(); 
		Vehicle v1 = new Vehicle("1","2018","BMW","",true,"","","",BodyType.SUV,"");
		Vehicle v2 = new Vehicle("2","2017","BMW","",false,"","","",BodyType.CAR,"");
		Vehicle v3 = new Vehicle("3","2018","BenZ","",true,"","","",BodyType.VAN,"");
		Vehicle v4 = new Vehicle("4","2000","Tesla","",true,"","","",BodyType.SUV,"");
		Vehicle v5 = new Vehicle("5","2008","Toyota","",false,"","","",BodyType.TRUCK,"");
		Vehicle v6 = new Vehicle("6","2012","Tesla","",true,"","","",BodyType.CAR,"");
		vehicles.add(v1);
		vehicles.add(v2);
		vehicles.add(v3);
		vehicles.add(v4);
		vehicles.add(v5);
		vehicles.add(v6);
		List<Special> specials = new ArrayList<>(); 
		Special s1 = new Special("1098","1","","","","BMW","2018",true,null,"",null);
		Special s2 = new Special("2065","2","","","","","2018",true,null,"",null);
		Special s3 = new Special("3698","6","","","","","",true,null,"",null);
		Special s4 = new Special("2465","4","","","","BenZ","2018",true,null,"",null);
		Special s5 = new Special("0865","5","","","","Toyota","2008",false,BodyType.TRUCK,"",null);
		Special s6 = new Special("0002","2","","","","Tesla","",true,BodyType.SUV,"",null);
		//Special s7 = new Special("0002","2","","","","","",true,null,"",null);
		specials.add(s1);
		specials.add(s2);
		specials.add(s3);
		specials.add(s4);
		specials.add(s5);
		specials.add(s6);
		//specials.add(s7);
		test.associateSpecials(vehicles, specials);
		assertEquals(v1.getSpecialIDs().size(),3);
		assertEquals(v2.getSpecialIDs().size(),0);
		assertEquals(v3.getSpecialIDs().size(),3);
		assertEquals(v4.getSpecialIDs().size(),2);
		assertEquals(v5.getSpecialIDs().size(),1);
		assertEquals(v6.getSpecialIDs().size(),1);
	}
}
