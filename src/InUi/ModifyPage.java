
package InUi;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.sql.SQLException;

import javax.swing.JFrame;

/**
 * Created by LynnTeng on 2018/12/7.
 */
public class ModifyPage extends BasePage {

    @Override
    public void setViewOrModifyPageLayout (String vehicleId)  {

        try {
			super.initViewOrModifyComponents(vehicleId);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        super.setLabelConfig();
        super.setButtonConfig();
        super.setLayoutConfig();
        
        setModifyPageConfig();
        super.addListeners();
    }

    public void setModifyPageConfig() {

        GridBagConstraints c = new GridBagConstraints();

        c.fill= GridBagConstraints.HORIZONTAL;
        c.gridx = 8;
        c.gridy = 11;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 2;
        //c.gridheight = 1;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets(10,10,10,10);
        super.container.add(modifyButton,c);
    }

    public static void main(String args[]) {
        ModifyPage modifyPage = new ModifyPage();
        String vehicleId = "791179263";
        modifyPage.setViewOrModifyPageLayout(vehicleId);
    }

	@Override
	public void setAddPageLayout(String dealerID) {
		// TODO Auto-generated method stub
		
	}
}