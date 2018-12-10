
package InUi;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by LynnTeng on 2018/12/7.
 */
public class ViewPage extends BasePage {

    @Override
    public void setSpecificPageLayout() {

        super.initComponents();
        setViewPageConfig();
        super.setLabelConfig();
        super.setButtonConfig();
        super.setModel();
        super.setLayoutConfig();
        super.addListeners();
    }

    public void setViewPageConfig(){
        makeTextfield.setEditable(false);
        vehicleIdTextfield.setEditable(false);
        vehicleTypeTextfield.setEditable(false);
        yearTextfield.setEditable(false);
        milesTextfield.setEditable(false);
        priceTextfield.setEditable(false);
        modelTextfield.setEditable(false);
        extColorTextfield.setEditable(false);
        intColorTextfield.setEditable(false);
    }
    public static void main(String args[]) {
        ViewPage viewPage = new ViewPage();
        viewPage.setSpecificPageLayout();
    }
}