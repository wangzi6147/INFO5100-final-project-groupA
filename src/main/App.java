package main;
import dao.*;
import dto.*;
public class App {
    public static void main(String[] args) {


        maintainDealer m = new maintainDealer();


        Dealer a = new Dealer("dandan");
        Dealer b = new Dealer("brother");
        a.setAddress1("fremont");
        a.setCity("seattle");


        b.setAddress1("NEU");
        b.setCity("seattle");

        m.addDealer(a);


        System.out.println(a.getName());
        System.out.println(a.getId());


        System.out.println("Hello project!");


    }
}
