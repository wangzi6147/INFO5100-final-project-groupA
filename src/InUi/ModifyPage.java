package InUi;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * Created by LynnTeng on 2018/12/7.
 */
public class ModifyPage extends BasePage {

    @Override
    public void setSpecificPageLayout () {

        super.initComponents();
        super.setLabelConfig();
        super.setButtonConfig();
        
        super.setModel();
        super.setLayoutConfig();
        super.addListeners();
        setModifyPageConfig();
    }

    public void setModifyPageConfig() {
        //super.buttonPanel.add(modifyButton);
    	//LayoutManager layout = super.container.getLayout();
        GridBagConstraints c = new GridBagConstraints();

        c.fill= GridBagConstraints.HORIZONTAL;
        c.gridx = 8;
        c.gridy = 11;
        c.weightx = 0.3;
        //c.weighty = 0.3;
        c.gridwidth = 2;
        //c.gridheight = 1;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets(10,10,10,10);
        super.container.add(modifyButton,c);
    }

    public static void main(String args[]) {
        ModifyPage modifyPage = new ModifyPage();
        modifyPage.setSpecificPageLayout();
    }
}
