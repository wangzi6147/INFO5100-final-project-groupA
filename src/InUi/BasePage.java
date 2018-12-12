
package InUi;

import javax.swing.*;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
	private static final int LABEL_WITDTH = 200;
	private static final int LABEL_HEIGHT = 50;

	JLabel modelLabel;
	JLabel intColorLabel;
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
	JLabel dealerIdLabel;

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

	JButton addButton;
	JButton modifyButton;
	JButton viewButton;
	JButton deleteButton;

	// adding feature list
	JList featureList;
	DefaultListModel listModel;
	JButton addFeature, deleteFeature;
	JTextField featureTextfield;

	Container container = getContentPane();

	JFrame frame = new JFrame("Dealer System");

	JPanel infoPanel = new JPanel(new GridBagLayout());
	JPanel featurePanel = new JPanel(new BorderLayout());
	JPanel picturePanel = new JPanel(new GridLayout(1, 2));
	JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
	JPanel addAnddeletePanel = new JPanel();

	Vehicle vehicle;
	String vehicleId;
	VehicleService vehicleService = new VehicleServiceImpl();

	// initialize base page component based on existing vehicleID and its
	// information
	public void initViewOrModifyComponents(String vehicleId) throws NumberFormatException, SQLException {
		vehicle = vehicleService.findVehicleById(Integer.parseInt(vehicleId));
		initialLabel();
		initialTextfield();
		setTextfield();
		initialComboBox();
		setComboBox();
		initialButton();
		initialFeatureList();
		setFeaturelist();

		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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

		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	// add action listeners to buttons
	public void addListeners() {
		addFeatureListBehaviors();
		ModifyAndADDVehicleBehaviors();
		deleteVehicleBehaviors();

	}

	public void setLayoutConfig() {
		container.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.5;
		c.weighty = 1;
		c.gridwidth = 5;
		c.gridheight = 5;
		c.insets = new Insets(10, 10, 10, 10);
		container.add(picturePanel, c);

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

		// TODO: Modify the sample url to specific url iterate from UrlList according to
		// search result.
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
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(container);

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
		// JPanel addAnddeletePanel = new JPanel();

		addAnddeletePanel.setLayout(new BoxLayout(addAnddeletePanel, BoxLayout.LINE_AXIS));
		addAnddeletePanel.add(deleteFeature);
		addAnddeletePanel.add(Box.createHorizontalStrut(5));
		addAnddeletePanel.add(new JSeparator(SwingConstants.VERTICAL));
		addAnddeletePanel.add(Box.createHorizontalStrut(5));
		addAnddeletePanel.add(featureTextfield);
		addAnddeletePanel.add(addFeature);
		addAnddeletePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		featurePanel.add(featureLabel, BorderLayout.PAGE_START);
		featurePanel.add(featureJSP, BorderLayout.CENTER);
		featurePanel.add(addAnddeletePanel, BorderLayout.PAGE_END);
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

	}

	// Add button text and size.
	public void setButtonConfig() {
		addButton.setText("Add");
		viewButton.setText("View");
		modifyButton.setText("Modify");
		deleteButton.setText("Delete");
	}

	// Publish image from url
	public ImageIcon publishImage(int w, int h, URL url) {
		// TODO: Set the specific URL from URL list, according to the backend search
		// result.
		ImageIcon imageIcon = new ImageIcon(url);
		Image originImage = imageIcon.getImage();
		Image resizedImage = originImage.getScaledInstance(w, h, SCALE_SMOOTH);
		imageIcon = new ImageIcon(resizedImage);
		pictureLabel.setIcon(imageIcon);
		return imageIcon;
	}

	public abstract void setViewOrModifyPageLayout(String vehicleId);

	public abstract void setAddPageLayout(String dealerID);

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
		priceLabel = new JLabel();
		milesLabel = new JLabel();
		intColorLabel = new JLabel();
		extColorLabel = new JLabel();
		vehicleTypeLabel = new JLabel();
		bodyTypeLabel = new JLabel();
		pictureLabel = new JLabel();
		featureLabel = new JLabel();
		dealerIdLabel = new JLabel();
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

	private void initialComboBox() {
		vehicleCombobox = new JComboBox<>();
		vehicleCombobox.setModel(new DefaultComboBoxModel<>(new String[] { "--Select Vehicle Type--", "Used", "New" }));

		bodyTypeCombobox = new JComboBox<>();
		bodyTypeCombobox.setModel(
				new DefaultComboBoxModel<>(new String[] { "--Select Body Type--", "VAN", "SUV", "CAR", "TRUCK" }));

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

	private void initialButton() {
		addButton = new JButton();
		viewButton = new JButton();
		modifyButton = new JButton();
		deleteButton = new JButton();
	}

	private void initialFeatureList() {
		listModel = new DefaultListModel();
//        List<String> featureListString = vehicle.getFeatures();
//        
//        for(String s : featureListString) {
//        	listModel.addElement(s);
//
//        }

		featureList = new JList(listModel);
		addFeature = new JButton("Add Feature");
		deleteFeature = new JButton("Delete Feature");
		featureTextfield = new JTextField(TEXT_FIELD_COLUMNS);

		featureJSP = new JScrollPane(featureList);
	}

	private void setFeaturelist() {
		List<String> featureListString = vehicle.getFeatures();

		for (String s : featureListString) {
			listModel.addElement(s);

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

	class DeleteFeatureListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = featureList.getSelectedIndex();
			System.out.println(index);
			listModel.remove(index);

			int size = listModel.getSize();

			if (size == 0) {
				deleteFeature.setEnabled(false);
			} else {
				if (index == listModel.getSize()) {
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

			listModel.addElement(featureTextfield.getText());

			featureTextfield.requestFocusInWindow();
			featureTextfield.setText("");

			featureList.setSelectedIndex(index);
			featureList.ensureIndexIsVisible(index);
		}

		protected boolean alreadyInList(String name) {
			return listModel.contains(name);
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

			List<String> newFeatureList = Arrays
					.asList((Arrays.copyOf(listModel.toArray(), listModel.toArray().length, String[].class)));
			vehicle.setFeatures(newFeatureList);

			System.out.println("id = " + vehicle.getId());
			System.out.println("year = " + vehicle.getYear());
			System.out.println("brand = " + vehicle.getBrand());
			System.out.println("model = " + vehicle.getModel());
			System.out.println("isNew = " + vehicle.getIsNew());
			System.out.println("price = " + vehicle.getPrice());
			System.out.println("extColor = " + vehicle.getExteriorColor());
			System.out.println("intColor = " + vehicle.getInteriorColor());
			System.out.println("featurelist = " + Arrays.toString(vehicle.getFeatures().toArray()));
			System.out.println("miles = " + vehicle.getMiles());
			System.out.println("bodytype = " + vehicle.getBodyType());
			System.out.println("dealerID = " + vehicle.getDealerID());



				try {
					String result = vehicleService.maintainVehicle(vehicle);
					if (!result.equals(null)&& e.getSource() == addButton) {
						JOptionPane.showMessageDialog(frame,"You have successfully add this vehicle, this vehicle id is " + result);
						frame.dispose();
					}else if ( !result.equals(null)&& e.getSource() == modifyButton) {
						JOptionPane.showMessageDialog(frame,"You have successfully modify this vehicle");
						frame.dispose();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		}

	}

	class DeleteButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				Boolean isDeleted = vehicleService.deleteVehicleByVehicleId(vehicleId);
				if (isDeleted) {
					JOptionPane.showMessageDialog(frame,"You have successfully delete this vehicle!");
					frame.dispose();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}

	}
}
