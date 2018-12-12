
package InUi;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by LynnTeng on 2018/12/7.
 */
public class ViewPage extends BasePage {

    @Override
    public void setViewOrModifyPageLayout(String vehicleId) {

        try {
			super.initViewOrModifyComponents(vehicleId);
		} catch (NumberFormatException e) {
//			System.out.println(vehicleId);
//			System.out.println(vehicleId.getClass());
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        super.setLabelConfig();
        super.setButtonConfig();
        super.setLayoutConfig();
        setViewPageConfig();
        super.addListeners();
        
    }

    public void setViewPageConfig(){
        makeTextfield.setEditable(false);
        vehicleIdTextfield.setEditable(false);
        yearTextfield.setEditable(false);
        milesTextfield.setEditable(false);
        priceTextfield.setEditable(false);
        modelTextfield.setEditable(false);
        extColorTextfield.setEditable(false);
        intColorTextfield.setEditable(false);
        bodyTypeCombobox.setEnabled(false);
        vehicleCombobox.setEnabled(false);
        addAndDeleteButtonFeaturePanel.setVisible(false);
        addAndDeleteButtonImagePanel.setVisible(false);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill= GridBagConstraints.HORIZONTAL;
        c.gridx = 8;
        c.gridy = 11;
        c.weightx = 0.0;
        //c.weighty = 0.3;
        c.gridwidth = 2;
        //c.gridheight = 1;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets(10,10,10,10);
        super.container.add(deleteButton,c);
        
    }
    public static void main(String args[]) {
        ViewPage viewPage = new ViewPage();
        String vehicleId = "976487683";
        viewPage.setViewOrModifyPageLayout(vehicleId);
    }

	@Override
	public void setAddPageLayout(String dealerID) {
		// TODO Auto-generated method stub
		
	}
}