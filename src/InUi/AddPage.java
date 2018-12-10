
package InUi;

import javax.swing.*;

import static javax.swing.SwingUtilities.invokeLater;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Created by LynnTeng on 2018/12/7.
 */
public class AddPage extends BasePage {


    @Override
    public void setSpecificPageLayout() {

        super.initComponents();
        super.setLabelConfig();
        super.setButtonConfig();
        
        super.setModel();
        super.setLayoutConfig();
        super.addListeners();
        
        setAddPageConfig();
    }

    public void setAddPageConfig() {
        //super.buttonPanel.add(addButton);
        GridBagConstraints c = new GridBagConstraints();
 
    	c.fill= GridBagConstraints.HORIZONTAL;
        c.gridx = 8;
        c.gridy = 11;
        //c.weightx = 1;
        //c.weighty = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets(5,5,5,5);
        super.container.add(addButton,c);
    }

    public static void main(String args[]) {
        AddPage addPage = new AddPage();
        addPage.setSpecificPageLayout();
    }
}