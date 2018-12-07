package main;

import dto.*;
import service.VehicleServiceImpl;

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

//
//        ms.addSpecial(s);
//        System.out.println(s.getId());
//
//
//        maintainDealer md = new maintainDealer();
//        Dealer d = new Dealer("Brother ");
//        Address add = new Address("Terry 401","NW","Seattle","WA","98107");
//        d.setAddress(add);
//
//        md.addDealer(d);

//        ms.addSpecial(s);
//        System.out.println(s.getId());

//
//        dealerQuery dq = new dealerQuery();
//        List<Dealer> list = dq.findDealersByPostCode(98107);
//
//        for(Dealer d : list){
//            System.out.println(d.getName()+"\t"+d.getId()+"\t"+d.getAddress1());
//        }


//        new InventoryView(new Dealer("zane"));
        VehicleFilterSelected p = new VehicleFilterSelected("10142");
//        List<String> years = new ArrayList<>();
//        years.add("2010-2014");
//        years.add("2000-2009");
//        years.add("2015");
//        years.add("Before 2000");
////        List<BodyType> bodyTypes = new ArrayList<>();
////        bodyTypes.add(BodyType.CAR);
//        p.setYears(years);
//        p.setBodyType(bodyTypes);

//        List<String > prices = new ArrayList<>();
//        prices.add("Negotiable");
//        prices.add("1000-5000");
//        prices.add("10000-50000");
//        p.setPrice(prices);


        VehicleServiceImpl vlp = new VehicleServiceImpl();
        vlp.Query(p);
        VehicleFilterContent Content = vlp.getFilterContent();

        System.out.println(Content);
        System.out.println(Content.getYears());
        System.out.println(Content.getBrand());
        System.out.println(Content.getDealerID());
        System.out.println(Content.getMiles());
        System.out.println(Content.getPrice());
        System.out.println(vlp.getPageCount());

        List<Vehicle> res = vlp.getVehicleList();
        for (Vehicle v : res) {
            System.out.println(v.getImages());
        }


        System.out.println("Hello project!");


    }
}
