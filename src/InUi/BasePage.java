
package InUi;

import javax.swing.*;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//import InUi.PictureList.PitureListRenderer;
import dto.BodyType;
import dto.Vehicle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import service.*;

import static java.awt.Image.SCALE_SMOOTH;

public abstract class BasePage extends JFrame {

	private static final int TEXT_FIELD_COLUMNS = 20;
	private static final int LABEL_WITDTH = 20;
	private static final int LABEL_HEIGHT = 10;

	JLabel modelLabel;
	JLabel intColorLabel;
	JLabel bodyTypeLabel;
	JLabel extColorLabel;
	JLabel makeLabel;
	JLabel milesLabel;
	JLabel pictureLabel;
	JLabel imageLabel;
	JLabel featureLabel;
	JLabel vehicleIdLabel;
	JLabel vehicleTypeLabel;
	JLabel yearLabel;
	JLabel dealerIdLabel;
	JLabel priceLabel;

	JTextField makeTextfield;
	JTextField extColorTextfield;
	JTextField milesTextfield;
	JTextField intColorTextfield;
	JTextField modelTextfield;
	JTextField priceTextfield;
	JTextField yearTextfield;
	JTextField vehicleIdTextfield;
	JTextField dealerIdTextfield;

	JComboBox<String> bodyTypeCombobox;
	JComboBox<String> vehicleCombobox;

	JScrollPane featureJSP;
	JScrollPane imageJSP;

	JButton addButton;
	JButton modifyButton;
	JButton viewButton;
	JButton deleteButton;

	// adding feature list
	JList featureList;
	DefaultListModel featureListModel;
	JButton addFeature, deleteFeature;
	JTextField featureTextfield;

	// adding feature list
	JList imageList;
	DefaultListModel imageListModel;
	JButton addImage, deleteImage;
	JTextField imageTextfield;

	Container container = getContentPane();

	JFrame frame = new JFrame("Dealer System");

	JPanel infoPanel = new JPanel(new GridBagLayout());
	JPanel featurePanel = new JPanel(new BorderLayout());
	JPanel imagePanel = new JPanel(new BorderLayout());
	JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
	JPanel addAndDeleteButtonFeaturePanel = new JPanel();
	JPanel addAndDeleteButtonImagePanel = new JPanel();

	Vehicle vehicle;
	String vehicleId;
	VehicleService vehicleService = new VehicleServiceImpl();

	public abstract void setViewOrModifyPageLayout(String vehicleId);

	public abstract void setAddPageLayout(String dealerID);
	
	// initialize base page component based on existing vehicleID and its
	// information
	public void initViewOrModifyComponents(String vehicleId) throws NumberFormatException, SQLException {
		
		
		vehicle = vehicleService.findVehicleById(Integer.parseInt(vehicleId));
		this.vehicleId = vehicleId;
		initialLabel();
		initialTextfield();
		setTextfield();
		initialComboBox();
		setComboBox();
		initialButton();
		initialFeatureList();
		setFeaturelist();
		initialImageList();
		setImageList();

		
	}


	// Initialize base page component with new vehicle
	public void initAddComponents(String dealerID) {

		
		vehicle = new Vehicle();
		vehicle.setDealerID(dealerID);
		initialLabel();
		initialTextfield();

		initialComboBox();
		initialButton();
		initialFeatureList();
		initialImageList();




	}



	public void setLayoutConfig() {

		container.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 5;
		c.gridheight = 5;
		c.insets = new Insets(10, 10, 10, 10);
		container.add(imagePanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 5;
		c.gridheight = 5;
		c.insets = new Insets(10, 10, 10, 10);
		container.add(infoPanel, c);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 6;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 10;
		c.gridheight = 5;
		c.insets = new Insets(10, 10, 10, 10);
		container.add(featurePanel, c);


		//frame.setDefaultLookAndFeelDecorated(true);
		frame.setSize(960, 800);
		frame.setVisible(true);
		frame.add(container);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


		// set infoPanel Layout
		setComponentGridBagLayOut(infoPanel, dealerIdLabel, 0, 0);
		setComponentGridBagLayOut(infoPanel, dealerIdTextfield, 0, 1);
		setComponentGridBagLayOut(infoPanel, vehicleIdLabel, 0, 2);
		setComponentGridBagLayOut(infoPanel, vehicleIdTextfield, 0, 3);
		setComponentGridBagLayOut(infoPanel, makeLabel, 0, 4);
		setComponentGridBagLayOut(infoPanel, makeTextfield, 0, 5);
		setComponentGridBagLayOut(infoPanel, modelLabel, 0, 6);
		setComponentGridBagLayOut(infoPanel, modelTextfield, 0, 7);
		setComponentGridBagLayOut(infoPanel, priceLabel, 0, 8);
		setComponentGridBagLayOut(infoPanel, priceTextfield, 0, 9);
		setComponentGridBagLayOut(infoPanel, vehicleTypeLabel, 0, 10);
		setComponentGridBagLayOut(infoPanel, vehicleCombobox, 0, 11);
		setComponentGridBagLayOut(infoPanel, intColorLabel, 1, 2);
		setComponentGridBagLayOut(infoPanel, intColorTextfield, 1, 3);
		setComponentGridBagLayOut(infoPanel, extColorLabel, 1, 4);
		setComponentGridBagLayOut(infoPanel, extColorTextfield, 1, 5);
		setComponentGridBagLayOut(infoPanel, yearLabel, 1, 6);
		setComponentGridBagLayOut(infoPanel, yearTextfield, 1, 7);
		setComponentGridBagLayOut(infoPanel, milesLabel, 1, 8);
		setComponentGridBagLayOut(infoPanel, milesTextfield, 1, 9);
		setComponentGridBagLayOut(infoPanel, bodyTypeLabel, 1, 10);
		setComponentGridBagLayOut(infoPanel, bodyTypeCombobox, 1, 11);

		// set feature panel Layout
		addAndDeleteButtonFeaturePanel.setLayout(new BoxLayout(addAndDeleteButtonFeaturePanel, BoxLayout.LINE_AXIS));
		addAndDeleteButtonFeaturePanel.add(deleteFeature);
		addAndDeleteButtonFeaturePanel.add(Box.createHorizontalStrut(5));
		addAndDeleteButtonFeaturePanel.add(new JSeparator(SwingConstants.VERTICAL));
		addAndDeleteButtonFeaturePanel.add(Box.createHorizontalStrut(5));
		addAndDeleteButtonFeaturePanel.add(featureTextfield);
		addAndDeleteButtonFeaturePanel.add(addFeature);
		addAndDeleteButtonFeaturePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		
		featurePanel.add(featureLabel, BorderLayout.PAGE_START);
		featurePanel.add(featureJSP, BorderLayout.CENTER);
		featurePanel.add(addAndDeleteButtonFeaturePanel, BorderLayout.PAGE_END);
		
		
		//set imagePanel Layout
		addAndDeleteButtonImagePanel.setLayout(new BoxLayout(addAndDeleteButtonImagePanel, BoxLayout.LINE_AXIS));
		addAndDeleteButtonImagePanel.add(deleteImage);
		addAndDeleteButtonImagePanel.add(Box.createHorizontalStrut(5));
		addAndDeleteButtonImagePanel.add(new JSeparator(SwingConstants.VERTICAL));
		addAndDeleteButtonImagePanel.add(Box.createHorizontalStrut(5));
		addAndDeleteButtonImagePanel.add(imageTextfield);
		addAndDeleteButtonImagePanel.add(addImage);
		addAndDeleteButtonImagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		imagePanel.add(imageLabel, BorderLayout.PAGE_START);
		imagePanel.add(imageJSP, BorderLayout.CENTER);
		imagePanel.add(addAndDeleteButtonImagePanel, BorderLayout.PAGE_END);
		
	}

	// Add Label text and size.
	public void setLabelConfig() {

		setLabelTextAndSize(vehicleIdLabel, "Vehicle ID", LABEL_WITDTH, LABEL_HEIGHT);
		setLabelTextAndSize(vehicleTypeLabel, "Vehicle Type", LABEL_WITDTH, LABEL_HEIGHT);
		setLabelTextAndSize(yearLabel, "Years", LABEL_WITDTH, LABEL_HEIGHT);
		setLabelTextAndSize(makeLabel, "Make", LABEL_WITDTH, LABEL_HEIGHT);
		setLabelTextAndSize(modelLabel, "Model", LABEL_WITDTH, LABEL_HEIGHT);
		setLabelTextAndSize(priceLabel, "Price", LABEL_WITDTH, LABEL_HEIGHT);
		setLabelTextAndSize(milesLabel, "Miles", LABEL_WITDTH, LABEL_HEIGHT);
		setLabelTextAndSize(intColorLabel, "Interior Color", LABEL_WITDTH, LABEL_HEIGHT);
		setLabelTextAndSize(extColorLabel, "Exterior Color", LABEL_WITDTH, LABEL_HEIGHT);
		setLabelTextAndSize(bodyTypeLabel, "Body Type", LABEL_WITDTH, LABEL_HEIGHT);
		setLabelTextAndSize(featureLabel, "Feature", LABEL_WITDTH, LABEL_HEIGHT);
		setLabelTextAndSize(dealerIdLabel, "Dealer ID", LABEL_WITDTH, LABEL_HEIGHT);
		setLabelTextAndSize(imageLabel, "Image", LABEL_WITDTH, LABEL_HEIGHT);

	}

	// Add button text and size.
	public void setButtonConfig() {

		addButton.setText("Add");
		viewButton.setText("View");
		modifyButton.setText("Modify");
		deleteButton.setText("Delete");
	}
	
	
	// add action listeners to buttons
	public void addListeners() {

		addFeatureListBehaviors();
		addImageListBehaviors();
		ModifyAndADDVehicleBehaviors();
		deleteVehicleBehaviors();

	}

	// Publish image from url
	public ImageIcon publishImage(int w, int h, URL url) {
		// TODO: Set the specific URL from URL list, according to the backend search
		// result.
		ImageIcon imageIcon = new ImageIcon(url);
		Image originImage = imageIcon.getImage();
		Image resizedImage = originImage.getScaledInstance(w, h, SCALE_SMOOTH);
		imageIcon = new ImageIcon(resizedImage);
		return imageIcon;
	}



	private void setComponentGridBagLayOut(JPanel panel, JComponent component, int gridx, int gridy) {

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		if (component.getClass().getName().equals("javax.swing.JLabel")) {
			gbc.insets = new Insets(5, 5, 0, 5);
		} else {
			gbc.insets = new Insets(0, 5, 5, 5);
		}

		panel.add(component, gbc);

	}

	private void setLabelTextAndSize(JLabel label, String text, int width, int height) {

		label.setText(text);
		label.setSize(width, height);
	}

	private void initialLabel() {

		vehicleIdLabel = new JLabel();
		yearLabel = new JLabel();
		makeLabel = new JLabel();
		modelLabel = new JLabel();
		imageLabel = new JLabel();
		milesLabel = new JLabel();
		intColorLabel = new JLabel();
		extColorLabel = new JLabel();
		vehicleTypeLabel = new JLabel();
		bodyTypeLabel = new JLabel();
		pictureLabel = new JLabel();
		featureLabel = new JLabel();
		dealerIdLabel = new JLabel();
		priceLabel = new JLabel();
	}

	private void initialTextfield() {


		dealerIdTextfield = new JTextField(TEXT_FIELD_COLUMNS);
		dealerIdTextfield.setEditable(false);
		dealerIdTextfield.setText((vehicle.getDealerID()));

		vehicleIdTextfield = new JTextField(TEXT_FIELD_COLUMNS);
		vehicleIdTextfield.setEditable(false);

		yearTextfield = new JTextField(TEXT_FIELD_COLUMNS);
		milesTextfield = new JTextField(TEXT_FIELD_COLUMNS);
		priceTextfield = new JTextField(TEXT_FIELD_COLUMNS);
		modelTextfield = new JTextField(TEXT_FIELD_COLUMNS);
		extColorTextfield = new JTextField(TEXT_FIELD_COLUMNS);
		intColorTextfield = new JTextField(TEXT_FIELD_COLUMNS);
		makeTextfield = new JTextField(TEXT_FIELD_COLUMNS);

	}
	
	private void initialComboBox() {

		vehicleCombobox = new JComboBox<>();
		vehicleCombobox.setModel(new DefaultComboBoxModel<>(new String[] { "--Select Vehicle Type--", "Used", "New" }));

		bodyTypeCombobox = new JComboBox<>();
		bodyTypeCombobox.setModel(
				new DefaultComboBoxModel<>(new String[] { "--Select Body Type--", "VAN", "SUV", "CAR", "TRUCK" }));

	}
	

	private void initialFeatureList() {

		featureListModel = new DefaultListModel();
		featureList = new JList(featureListModel);
		addFeature = new JButton("Add");
		deleteFeature = new JButton("Delete");
		featureTextfield = new JTextField(TEXT_FIELD_COLUMNS);

		featureJSP = new JScrollPane(featureList);
	}
	
	
	private void initialImageList() {

		imageListModel = new DefaultListModel();
		imageList = new JList(imageListModel);
		addImage = new JButton("Add");
		deleteImage = new JButton("Delete");
		imageTextfield = new JTextField(TEXT_FIELD_COLUMNS);
		imageJSP = new JScrollPane(imageList);
		
	}
	
	private void initialButton() {

		addButton = new JButton();
		viewButton = new JButton();
		modifyButton = new JButton();
		deleteButton = new JButton();
	}

	private void setTextfield() {

		makeTextfield.setText(vehicle.getBrand());
		vehicleIdTextfield.setText(vehicle.getId());
		yearTextfield.setText(vehicle.getYear());
		milesTextfield.setText(vehicle.getMiles());
		priceTextfield.setText(vehicle.getPrice());
		modelTextfield.setText(vehicle.getModel());
		extColorTextfield.setText(vehicle.getExteriorColor());
		intColorTextfield.setText(vehicle.getInteriorColor());

	}



	private void setComboBox() {

		boolean isNew = vehicle.getIsNew();
		if (isNew) {
			vehicleCombobox.setSelectedIndex(1);
		} else {
			vehicleCombobox.setSelectedIndex(2);
		}
		BodyType bodyType = vehicle.getBodyType();
		if (bodyType == BodyType.VAN) {
			bodyTypeCombobox.setSelectedIndex(1);
		} else if (bodyType == BodyType.SUV) {
			bodyTypeCombobox.setSelectedIndex(2);
		} else if (bodyType == BodyType.CAR) {
			bodyTypeCombobox.setSelectedIndex(3);
		} else if (bodyType == BodyType.TRUCK) {
			bodyTypeCombobox.setSelectedIndex(4);
		}

	}



	
	private void setFeaturelist() {
		List<String> featureListString = vehicle.getFeatures();

		for (String s : featureListString) {
			featureListModel.addElement(s);

		}
	}
	


	private void setImageList() {

		List<String> imageListString = vehicle.getImages();
		
		for(String s : imageListString) {
			imageListModel.addElement(s);
		}
		
	}
		

	private void deleteVehicleBehaviors() {

		DeleteButtonListener deleteButtonListener = new DeleteButtonListener();
		deleteButton.addActionListener(deleteButtonListener);
	}

	private void ModifyAndADDVehicleBehaviors() {

		ModifyAndADDButtonListener modifyAndADDButtonListener = new ModifyAndADDButtonListener();
		modifyButton.addActionListener(modifyAndADDButtonListener);
		addButton.addActionListener(modifyAndADDButtonListener);
	}

	//implement feature list behaviors
	private void addFeatureListBehaviors() {

		FeatureListSelectionListener featureListSelectionListener = new FeatureListSelectionListener();
		featureList.addListSelectionListener(featureListSelectionListener);
		featureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		featureList.setSelectedIndex(0);
		featureList.setVisibleRowCount(10);

		DeleteFeatureListener deleteFeatureListener = new DeleteFeatureListener();
		deleteFeature.addActionListener(deleteFeatureListener);

		AddFeatureListener addFeatureListener = new AddFeatureListener(addFeature);
		addFeature.addActionListener(addFeatureListener);
		addFeature.setEnabled(false);

		featureTextfield.addActionListener(addFeatureListener);
		featureTextfield.getDocument().addDocumentListener(addFeatureListener);
	}
	
	
	//implement image list behaviors
		private void addImageListBehaviors() {

			ImageListSelectionListener imageListSelectionListener = new ImageListSelectionListener();
			imageList.addListSelectionListener(imageListSelectionListener);
			imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			imageList.setSelectedIndex(0);
			imageList.setVisibleRowCount(5);
			imageList.setCellRenderer(new PitureListRenderer());

			DeleteImageListener deleteImageListener = new DeleteImageListener();
			deleteImage.addActionListener(deleteImageListener);

			AddImageListener addImageListener = new AddImageListener(addImage);
			addImage.addActionListener(addImageListener);
			addImage.setEnabled(false);

			imageTextfield.addActionListener(addImageListener);
			imageTextfield.getDocument().addDocumentListener(addImageListener);
			
		}
	
	

	class DeleteFeatureListener implements ActionListener {


		@Override
		public void actionPerformed(ActionEvent e) {

			int index = featureList.getSelectedIndex();
			System.out.println(index);
			featureListModel.remove(index);

			int size = featureListModel.getSize();

			if (size == 0) {
				deleteFeature.setEnabled(false);
			} else {
				if (index == featureListModel.getSize()) {
					index--;
				}

				featureList.setSelectedIndex(index);
				featureList.ensureIndexIsVisible(index);
			}
		}

	}
	
	class DeleteImageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = featureList.getSelectedIndex();
			imageListModel.remove(index);

			int size = imageListModel.getSize();

			if (size == 0) {
				deleteFeature.setEnabled(false);
			} else {
				if (index == imageListModel.getSize()) {
					// removed item in last position
					index--;
				}

				featureList.setSelectedIndex(index);
				featureList.ensureIndexIsVisible(index);
			}
		}

	}

	class FeatureListSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {

			if (e.getValueIsAdjusting() == false) {

				if (featureList.getSelectedIndex() == -1) {
					deleteFeature.setEnabled(false);

				} else {
					deleteFeature.setEnabled(true);
				}
			}

		}
	}
	
	class ImageListSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() == false) {

				if (imageList.getSelectedIndex() == -1) {
					// No selection, disable fire button.
					deleteImage.setEnabled(false);

				} else {
					// Selection, enable the fire button.
					deleteImage.setEnabled(true);
				}
			}
		}

	}
	

	//add behavior to add feature at feature list & add DocumentListener to feature list 
	class AddFeatureListener implements ActionListener, DocumentListener {

		private boolean alreadyEnabled = false;
		private JButton button;

		public AddFeatureListener(JButton button) {

			this.button = button;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {

			if (!handleEmptyTextField(e)) {
				enableButton();
			}
		}

		@Override
		public void insertUpdate(DocumentEvent e) {

			enableButton();

		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			handleEmptyTextField(e);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String name = featureTextfield.getText();

			// User didn't type in a unique name...
			if (name.equals("") || alreadyInList(name)) {
				Toolkit.getDefaultToolkit().beep();
				featureTextfield.requestFocusInWindow();
				featureTextfield.selectAll();
				return;
			}

			int index = featureList.getSelectedIndex();
			if (index == -1) {
				index = 0;
			} else {
				index++;
			}

			featureListModel.addElement(featureTextfield.getText());

			featureTextfield.requestFocusInWindow();
			featureTextfield.setText("");

			featureList.setSelectedIndex(index);
			featureList.ensureIndexIsVisible(index);
		}

		protected boolean alreadyInList(String name) {

			return featureListModel.contains(name);
		}

		private void enableButton() {

			if (!alreadyEnabled) {
				button.setEnabled(true);
			}
		}

		private boolean handleEmptyTextField(DocumentEvent e) {

			if (e.getDocument().getLength() <= 0) {
				button.setEnabled(false);
				alreadyEnabled = false;
				return true;
			}
			return false;
		}

	}
	
	


	class AddImageListener implements ActionListener, DocumentListener {
		private boolean alreadyEnabled = false;
		private JButton button;

		public AddImageListener(JButton button) {
			this.button = button;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			if (!handleEmptyTextField(e)) {
				enableButton();
			}
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			enableButton();

		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			handleEmptyTextField(e);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = imageTextfield.getText();

			// User didn't type in a unique name...
			if (name.equals("") || alreadyInList(name)) {
				Toolkit.getDefaultToolkit().beep();
				imageTextfield.requestFocusInWindow();
				imageTextfield.selectAll();
				return;
			}

			int index = imageList.getSelectedIndex(); 
			if (index == -1) { 
				index = 0;
			} else { 
				index++;
			}

			imageListModel.addElement(imageTextfield.getText());

			// Reset the text field.
			imageTextfield.requestFocusInWindow();
			imageTextfield.setText("");

			// Select the new item and make it visible.
			imageList.setSelectedIndex(index);
			imageList.ensureIndexIsVisible(index);
		}

		protected boolean alreadyInList(String name) {
			return imageListModel.contains(name);
		}

		private void enableButton() {
			if (!alreadyEnabled) {
				button.setEnabled(true);
			}
		}

		private boolean handleEmptyTextField(DocumentEvent e) {
			if (e.getDocument().getLength() <= 0) {
				button.setEnabled(false);
				alreadyEnabled = false;
				return true;
			}
			return false;
		}

	}

	
	

	//modify & add vehicle action Listener
	class ModifyAndADDButtonListener implements ActionListener {



		@Override
		public void actionPerformed(ActionEvent e) {

			vehicle.setInteriorColor(intColorTextfield.getText());
			vehicle.setBrand(makeTextfield.getText());
			vehicle.setExteriorColor(extColorTextfield.getText());
			vehicle.setYear(yearTextfield.getText());
			vehicle.setModel(modelTextfield.getText());
			vehicle.setPrice(priceTextfield.getText());
			vehicle.setMiles(milesTextfield.getText());

			if (vehicleCombobox.getSelectedItem().equals("Used")) {
				vehicle.setNewOrUsed(false);
			} else if (vehicleCombobox.getSelectedItem().equals("New")) {
				vehicle.setNewOrUsed(true);
			}

			if (bodyTypeCombobox.getSelectedItem().equals("VAN")) {
				vehicle.setBodyType(BodyType.VAN);
			} else if (bodyTypeCombobox.getSelectedItem().equals("SUV")) {
				vehicle.setBodyType(BodyType.SUV);
			} else if (bodyTypeCombobox.getSelectedItem().equals("CAR")) {
				vehicle.setBodyType(BodyType.CAR);
			} else if (bodyTypeCombobox.getSelectedItem().equals("TRUCK")) {
				vehicle.setBodyType(BodyType.TRUCK);
			}

			List<String> newFeatureList = Arrays.asList(
					(Arrays.copyOf(featureListModel.toArray(), featureListModel.toArray().length, String[].class)));
			vehicle.setFeatures(newFeatureList);
			
			List<String> newImageList = Arrays.asList(
					(Arrays.copyOf(imageListModel.toArray(), imageListModel.toArray().length, String[].class)));
			vehicle.setImages(newImageList);

//			System.out.println("id = " + vehicle.getId());
//			System.out.println("year = " + vehicle.getYear());
//			System.out.println("brand = " + vehicle.getBrand());
//			System.out.println("model = " + vehicle.getModel());
//			System.out.println("isNew = " + vehicle.getIsNew());
//			System.out.println("price = " + vehicle.getPrice());
//			System.out.println("extColor = " + vehicle.getExteriorColor());
//			System.out.println("intColor = " + vehicle.getInteriorColor());
//			System.out.println("featurelist = " + Arrays.toString(vehicle.getFeatures().toArray()));
//			System.out.println("miles = " + vehicle.getMiles());
//			System.out.println("bodytype = " + vehicle.getBodyType());
//			System.out.println("dealerID = " + vehicle.getDealerID());

			try {
				String result = vehicleService.maintainVehicle(vehicle);
				if (!result.equals(null) && e.getSource() == addButton) {
					JOptionPane.showMessageDialog(frame,
							"You have successfully add this vehicle, this vehicle id is " + result);
					frame.dispose();
				} else if (!result.equals(null) && e.getSource() == modifyButton) {
					JOptionPane.showMessageDialog(frame, "You have successfully modify this vehicle");
					frame.dispose();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	//delete vehicle action Listener 
	class DeleteButtonListener implements ActionListener {


		@Override
		public void actionPerformed(ActionEvent arg0) {


			try {
				Boolean isDeleted = vehicleService.deleteVehicleByVehicleId(vehicleId);
				if (isDeleted) {
					JOptionPane.showMessageDialog(frame, "You have successfully delete this vehicle!");
					frame.dispose();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	//set custom render for imageList 
	class PitureListRenderer extends DefaultListCellRenderer {
		 
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

	
			String urlString = (String) imageListModel.toArray()[index];

			try {
				URL url = new URL(urlString);
				ImageIcon icon = publishImage(200,150,url);
				
				if (icon == null||icon.getIconHeight()!= 150) {
//					setText(" (no image available)");
					label.setText("(no image available!)");
				}else {
					label.setText("Image" + index);
					label.setIcon(icon);
				}
				

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			label.setOpaque(true);
			return label;
		}

	}
	

}
