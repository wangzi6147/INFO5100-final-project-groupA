
package InUi;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Image.SCALE_SMOOTH;

public abstract class BasePage extends JFrame{

    private static final int TEXT_FIELD_COLUMNS = 20;

    JLabel modelLabel;
    JLabel intColorLabel;
    //JLabel specialLabel;
    JLabel bodyTypeLabel;
    JLabel extColorLabel;
    JLabel makeLabel;
    JLabel milesLabel;
    JLabel pictureLabel;
    JLabel priceLabel;
    JLabel featureLabel;
    JLabel vehicleIdLabel;
    JLabel vehicleTypeLabel;
    JLabel yearLabel;

    JTextField makeTextfield;
    JTextField extColorTextfield;
    JTextField milesTextfield;
    JTextField intColorTextfield;
    JTextField modelTextfield;
    JTextField priceTextfield;
    JTextField yearTextfield;
    JTextField vehicleIdTextfield;
    JTextField vehicleTypeTextfield;


    JComboBox<String> bodyTypeCombobox;
    JComboBox<String> vehicleCombobox;

    //JScrollPane specialJSP;
    JScrollPane featureJSP;

    JButton addButton;
    JButton modifyButton;
    JButton viewButton;
    
    // adding feature list 
	JList featureList;
    DefaultListModel listModel;
    JButton addFeature,deleteFeature;
    JTextField featureTextfield;

    Container container = getContentPane();

    JFrame frame = new JFrame("Dealer System");

    JPanel infoPanel = new JPanel(new GridBagLayout());
    JPanel featurePanel = new JPanel(new BorderLayout());
    JPanel picturePanel = new JPanel(new GridLayout(1,2));
    JPanel buttonPanel = new JPanel(new GridLayout(1,3));
    //initialize components
    public void initComponents() {

        vehicleIdLabel = new JLabel();
        yearLabel = new JLabel();
        makeLabel = new JLabel();
        modelLabel = new JLabel();
        priceLabel = new JLabel();
        milesLabel = new JLabel();
        intColorLabel = new JLabel();
        extColorLabel = new JLabel();
        vehicleTypeLabel = new JLabel();
        bodyTypeLabel = new JLabel();
        pictureLabel = new JLabel();
        featureLabel = new JLabel();
        //specialLabel = new JLabel();

        makeTextfield = new JTextField(TEXT_FIELD_COLUMNS);
        vehicleIdTextfield = new JTextField(TEXT_FIELD_COLUMNS);
        vehicleTypeTextfield = new JTextField(TEXT_FIELD_COLUMNS);
        yearTextfield = new JTextField(TEXT_FIELD_COLUMNS);
        milesTextfield = new JTextField(TEXT_FIELD_COLUMNS);
        priceTextfield = new JTextField(TEXT_FIELD_COLUMNS);
        modelTextfield = new JTextField(TEXT_FIELD_COLUMNS);
        extColorTextfield = new JTextField(TEXT_FIELD_COLUMNS);
        intColorTextfield = new JTextField(TEXT_FIELD_COLUMNS);

        vehicleCombobox = new JComboBox<>();
        bodyTypeCombobox = new JComboBox<>();

        

        addButton = new JButton();
        viewButton = new JButton();
        modifyButton = new JButton();
        
        
        // adding feature list 
        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("heated Seat");
        listModel.addElement("Sun Roof");
        listModel.addElement("GPS");
        
        featureList = new JList(listModel);
    	addFeature = new JButton("Add Feature");
    	deleteFeature = new JButton("Delete Feature");
    	featureTextfield = new JTextField(TEXT_FIELD_COLUMNS);
    	
    	featureJSP = new JScrollPane(featureList);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //add action listeners in textfield and button.
    public void addListeners() {
        //TODO: Add action listeners in textfield and button.
    }

    public void setLayoutConfig() {

        container.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        
        c.fill= GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        c.weighty = 1;
        c.gridwidth = 5;
        c.gridheight = 5;
        c.insets = new Insets(10,10,10,10);
        container.add(picturePanel,c);
        
        c.fill= GridBagConstraints.HORIZONTAL;
        c.gridx = 5;
        c.gridy = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 5;
        c.gridheight = 5;
        c.insets = new Insets(10,10,10,10);
        
        container.add(infoPanel,c);
        
        c.fill= GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 10;
        c.gridheight = 5;
        c.insets = new Insets(10,10,10,10);
        container.add(featurePanel,c);
        
        

        //TODO: Modify the sample url to specific url iterate from UrlList according to search result.
        String sampleUrlA = "https://www.gstatic.com/webp/gallery/2.jpg";
        String sampleUrlB = "https://www.gstatic.com/webp/gallery/2.jpg";
        String sampleUrlC = "https://www.gstatic.com/webp/gallery/2.jpg";
        List<String> urlList = new ArrayList<>();
        urlList.add(sampleUrlA);
        urlList.add(sampleUrlB);
        urlList.add(sampleUrlC);
        for (String stringUrl : urlList) {
            try {
                URL url = new URL(stringUrl);
                picturePanel.add(new JLabel(publishImage(150, 150, url)), FlowLayout.LEFT);
            } catch (MalformedURLException mex) {
                System.out.println("Url Unavailable");
            }
        }

        frame.setDefaultLookAndFeelDecorated(true);
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(container);

        

       //set infoPanel Layout
        setComponentGridBagLayOut(infoPanel, vehicleIdLabel, 0, 0);
        setComponentGridBagLayOut(infoPanel, vehicleIdTextfield, 0, 1);
        setComponentGridBagLayOut(infoPanel, makeLabel, 0, 2);
        setComponentGridBagLayOut(infoPanel, makeTextfield, 0, 3);
        setComponentGridBagLayOut(infoPanel, modelLabel, 0, 4);
        setComponentGridBagLayOut(infoPanel, modelTextfield, 0, 5);
        setComponentGridBagLayOut(infoPanel, priceLabel, 0, 6);
        setComponentGridBagLayOut(infoPanel, priceTextfield, 0, 7);
        setComponentGridBagLayOut(infoPanel, vehicleTypeLabel, 0, 8);
        setComponentGridBagLayOut(infoPanel, vehicleCombobox, 0, 9);
        setComponentGridBagLayOut(infoPanel, intColorLabel, 1, 0);
        setComponentGridBagLayOut(infoPanel, intColorTextfield, 1, 1);
        setComponentGridBagLayOut(infoPanel, extColorLabel, 1, 2);
        setComponentGridBagLayOut(infoPanel, extColorTextfield, 1, 3);
        setComponentGridBagLayOut(infoPanel, yearLabel, 1, 4);
        setComponentGridBagLayOut(infoPanel, yearTextfield, 1, 5);
        setComponentGridBagLayOut(infoPanel, milesLabel, 1, 6);
        setComponentGridBagLayOut(infoPanel, milesTextfield, 1, 7);
        setComponentGridBagLayOut(infoPanel, bodyTypeLabel, 1, 8);
        setComponentGridBagLayOut(infoPanel, bodyTypeCombobox, 1, 9);


//        infoPanel.add(featureLabel);
//        infoPanel.add(featureJSP);
//        infoPanel.add(specialLabel);
//        infoPanel.add(specialJSP);

        
        
        
//        specialPanel.add(specialLabel);
//        specialPanel.add(specialJSP);
        
        
        //set feature panel Layout
        JPanel addAnddeletePanel = new JPanel(); 
        addAnddeletePanel.setLayout(new BoxLayout(addAnddeletePanel, BoxLayout.LINE_AXIS));
        addAnddeletePanel.add(deleteFeature);
        addAnddeletePanel.add(Box.createHorizontalStrut(5));
        addAnddeletePanel.add(new JSeparator(SwingConstants.VERTICAL));
        addAnddeletePanel.add(Box.createHorizontalStrut(5));
        addAnddeletePanel.add(featureTextfield);
        addAnddeletePanel.add(addFeature);
        addAnddeletePanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        featurePanel.add(featureLabel,BorderLayout.PAGE_START);
        featurePanel.add(featureJSP,BorderLayout.CENTER);
        featurePanel.add(addAnddeletePanel, BorderLayout.PAGE_END);
    }

    //Add Label text and size.
    public void setLabelConfig() {
        vehicleIdLabel.setText("Vehicle ID");
        vehicleIdLabel.setSize(200, 50);

        vehicleTypeLabel.setText("Vehicle Type");
        vehicleTypeLabel.setSize(200, 50);

        yearLabel.setText("Years");
        yearLabel.setSize(200, 50);

        makeLabel.setText("Make");
        makeLabel.setSize(200, 50);

        modelLabel.setText("Model");
        modelLabel.setSize(200, 50);

        priceLabel.setText("Price");
        priceLabel.setSize(200, 50);

        milesLabel.setText("Miles");
        milesLabel.setSize(200, 50);

        intColorLabel.setText("Interior Color");
        intColorLabel.setSize(200, 50);

        extColorLabel.setText("Exterior Color");
        extColorLabel.setSize(200, 50);

        bodyTypeLabel.setText("Body Type");
        bodyTypeLabel.setSize(200, 50);

        featureLabel.setText("Feature");
        featureLabel.setSize(200, 50);

        //specialLabel.setText("Special");
        //specialLabel.setSize(200, 50);
        

    }

    //Add button text and size.
    public void setButtonConfig() {
        addButton.setText("Add");
        viewButton.setText("View");
        modifyButton.setText("Modify");
    }

    //Add ComboBox model configuration.
    public void setModel() {
        vehicleCombobox.setModel(new DefaultComboBoxModel<>(new String[]{"--Select Vehicle Type--", "Used", "New"}));
        bodyTypeCombobox.setModel(new DefaultComboBoxModel<>(new String[]{"--Select Body Type--", "VAN", "SUV", "CAR", "TRUCK"}));

        //featureJSP.setSize(500,500);
    }

    //Publish image from url
    public ImageIcon publishImage(int w, int h, URL url) {
        //TODO: Set the specific URL from URL list, according to the backend search result.
        ImageIcon imageIcon = new ImageIcon(url);
        Image originImage = imageIcon.getImage();
        Image resizedImage = originImage.getScaledInstance(w, h, SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedImage);
        pictureLabel.setIcon(imageIcon);
        return imageIcon;
    }

    public abstract void setSpecificPageLayout();
    
    
    private void setComponentGridBagLayOut(JPanel panel, JComponent component, int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill= GridBagConstraints.HORIZONTAL;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        if (component.getClass().getName().equals("javax.swing.JLabel") ) {
        	gbc.insets = new Insets(5, 5, 0, 5);
        }else {
        	 gbc.insets = new Insets(0, 5, 5, 5);
        }
        
        panel.add(component, gbc);
        
    }
    


}
