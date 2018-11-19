package service;

import dto.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

import java.io.IOException;
import java.util.*;

public class VehicleListPage {

    List<Vehicle> vehicles;
    VehicleFilterSelected parameter;


    public void setParameter(VehicleFilterSelected parameter) {
        this.parameter = parameter;
    }

    public List<List<ImageIcon>> getImages() {

        List<List<ImageIcon>> res = new ArrayList<>();
        ImageIcon noIMG;
        try {
            noIMG = new ImageIcon(ImageIO.read( new File("~/src/dto/noImage.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
            return res;
        }

        for (Vehicle v : vehicles) {
            List<ImageIcon> list = new ArrayList<>();
            for (String url : v.getImages()) {
                ImageIcon icon;
                try {
                    icon = new ImageIcon(ImageIO.read(new File(url)));
                } catch (IOException e) {
                    icon = noIMG;
                }
                list.add(icon);
            }
            res.add(new ArrayList<>(list));
        }

        return res;
    }


    public VehicleFilterContent getFilterContent() {

        VehicleFilterContent obj = new VehicleFilterContent();

        //赋值、init
        //@todo
        return obj;
    }

    public List<Vehicle> getVehicleList() {


        //@todo

        return vehicles;

    }

}
