package ui.button;

import ui.Setting;
import javax.swing.*;
import java.awt.*;

public class BeautifulButton extends JButton {

    private ImageIcon imageIcon0;
    private ImageIcon imageIcon1;

    public BeautifulButton(String imagePath) {

        init(imagePath, Setting.BUTTON_ICON_SIZE, Setting.BUTTON_ICON_SIZE);
    }
/*
    public BeautifulButton(String imagePath, int width, int height) {

        init(imagePath, width, height);
    }
*/
    public void init(String imagePath, int width, int height) {
        imageIcon0 = getIcon(imagePath + "0.png", width, height);
        imageIcon1 = getIcon(imagePath + "1.png", width, height);
        //init("", imageIcon1);


//        this.setDisabledIcon(imageIcon0);
    }

    public void setNormalIcon() {
        this.setIcon(imageIcon0);
        this.setOpaque(false);// foreground invisible
        this.setBorder(null);
        this.setRolloverIcon(imageIcon1);
        this.setPressedIcon(imageIcon1);
    }

    public void InitChangeIcon() {

        this.setIcon(imageIcon0);
    }

    public void AftChangeIcon() {

        this.setIcon(imageIcon1);
    }

    public ImageIcon getIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        return icon;
    }

}
