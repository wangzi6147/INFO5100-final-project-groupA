package main;
import dao.*;
import dto.*;

import java.util.*;
public class App {
    public static void main(String[] args) {

//

//        maintainVehicle mv = new maintainVehicle();

//        maintainSpecial ms = new maintainSpecial();
//
//        Special s = new Special("1322","2018-12-24","Big sale!!!", "60%", SpecialScope.ALL, "");

//        Vehicle v = new Vehicle("132","1234");
//        v.setBodyType(BodyType.SUV);
//
//
//        mv.addVehicle(v);

//        ms.addSpecial(s);
//        System.out.println(s.getId());

//
//        dealerQuery dq = new dealerQuery();
//        List<Dealer> list = dq.findDealersByPostCode(98107);
//
//        for(Dealer d : list){
//            System.out.println(d.getName()+"\t"+d.getId()+"\t"+d.getAddress1());
//        }


        DealerQuery dq = new DealerQuery();
        List<String> cities = dq.getCityList();
        System.out.println(cities);

        System.out.println("Hello project!");


    }
}
