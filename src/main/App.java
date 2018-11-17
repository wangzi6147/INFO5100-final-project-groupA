package main;
import dao.*;
import dto.*;
public class App {
    public static void main(String[] args) {

//
//        maintainDealer md = new maintainDealer();
//        maintainVehicle mv = new maintainVehicle();

        maintainSpecial ms = new maintainSpecial();

        Special s = new Special("1322","2018-12-24","Big sale!!!", "60%", SpecialScope.ALL, "");

//        Vehicle v = new Vehicle("132","1234");
//        v.setBodyType(BodyType.SUV);
//
//
//
//        mv.addVehicle(v);

        ms.addSpecial(s);
        System.out.println(s.getId());


        System.out.println("Hello project!");


    }
}
