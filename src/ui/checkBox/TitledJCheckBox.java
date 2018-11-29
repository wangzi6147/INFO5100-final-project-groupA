package ui.checkBox;

import javax.swing.*;

public class TitledJCheckBox extends JCheckBox {

    private String title;

    public String getTitle(){
        return this.title;
    }

    public TitledJCheckBox(){
        super();
    }

    public TitledJCheckBox(String title){
        super(title);
        this.title = title;
    }
}
