
package InUi;

import javax.swing.*;

import static javax.swing.SwingUtilities.invokeLater;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.SQLException;

/**
 * Created by LynnTeng on 2018/12/7.
 */
public class AddPage extends BasePage {


	
    @Override
    public void setViewOrModifyPageLayout(String vehicleId) {
    	// TODO Auto-generated method stub
    }

    public static void main(String args[]) {
        
        String vehicleId = "791179263";
        String dealerID = "10153";
        AddPage addPage = new AddPage();
        addPage.setAddPageLayout(dealerID);

    }

	@Override
	public void setAddPageLayout(String dealerID) {
		
		
			super.initAddComponents(dealerID);
	        super.setLabelConfig();
	        super.setButtonConfig();
	        super.setLayoutConfig();
	        super.addListeners();
	        
	        setAddPageConfig();
	    }

	    public void setAddPageConfig() {
	        GridBagConstraints c = new GridBagConstraints();
	 
	    	c.fill= GridBagConstraints.HORIZONTAL;
	        c.gridx = 8;
	        c.gridy = 11;
	        c.weightx = 0.0;
	        c.weighty = 0.0;
	        c.gridwidth = 2;
	        c.gridheight = 1;
	        c.anchor = GridBagConstraints.LAST_LINE_END;
	        c.insets = new Insets(5,5,5,5);
	        super.container.add(addButton,c);
		
	}
}