package InUi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

//public class FeatureList extends JPanel implements ListSelectionListener{
public class FeatureList extends JPanel {
    private static final int TEXT_FIELD_COLUMNS = 20;

	private JList featureList;
    private DefaultListModel listModel;
    
    private JButton addFeature,deleteFeature;
    private JTextField featureTextfield;
    
    
    public FeatureList (DefaultListModel listModel) {
    	super(new BorderLayout());
    	
    	createComponents(listModel);
		addComponents();
		addBehaviors();

    }
    


	private void addBehaviors() {
		FeatureListSelectionListener featureListSelectionListener= new FeatureListSelectionListener();
		featureList.addListSelectionListener(featureListSelectionListener);
		featureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	featureList.setSelectedIndex(0);
    	featureList.setVisibleRowCount(10);
    	
    	deleteFeature.setEnabled(false);
		
		DeleteFeatureListener deleteFeatureListener = new DeleteFeatureListener();
		deleteFeature.addActionListener(deleteFeatureListener);
		
		AddFeatureListener addFeatureListener = new AddFeatureListener(addFeature);
		addFeature.addActionListener(addFeatureListener);
		addFeature.setEnabled(false);
		
		featureTextfield.addActionListener(addFeatureListener);
		featureTextfield.getDocument().addDocumentListener(addFeatureListener);

		
	}


	private void addComponents() {
		JPanel buttonPane = new JPanel(); 
	    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
	    buttonPane.add(deleteFeature);
	    buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(featureTextfield);
        buttonPane.add(addFeature);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        add(new JScrollPane(featureList),BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
	}


	private void createComponents(DefaultListModel listModel) {
    	this.listModel = listModel;
    	featureList = new JList(listModel);
    	addFeature = new JButton("Add Feature");
    	deleteFeature = new JButton("Delete Feature");
    	featureTextfield = new JTextField(TEXT_FIELD_COLUMNS);
    	
    	

	}

    


	
	
	public static void main(String[] args) {
		 JFrame frame = new JFrame("ListDemo");
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	      //Create and set up the content pane.
	      JComponent newContentPane = new FeatureList(new DefaultListModel());
	      newContentPane.setOpaque(true); //content panes must be opaque
	      frame.setContentPane(newContentPane);
	      frame.setSize(600,600);
	      frame.setVisible(true);
		
	}
	
    class DeleteFeatureListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = featureList.getSelectedIndex();
			listModel.remove(index);
			
			int size = listModel.getSize();

            if (size == 0) { 
            	deleteFeature.setEnabled(false);
            }else {
            	if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }
            	
            	 featureList.setSelectedIndex(index);
                 featureList.ensureIndexIsVisible(index);
            }
		}

    }
    
    class AddFeatureListener implements ActionListener,DocumentListener{
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

	            //User didn't type in a unique name...
	            if (name.equals("") || alreadyInList(name)) {
	                Toolkit.getDefaultToolkit().beep();
	                featureTextfield.requestFocusInWindow();
	                featureTextfield.selectAll();
	                return;
	            }

	            int index = featureList.getSelectedIndex(); //get selected index
	            if (index == -1) { //no selection, so insert at beginning
	                index = 0;
	            } else {           //add after the selected item
	                index++;
	            }

	            listModel.addElement(featureTextfield.getText());

	            //Reset the text field.
	            featureTextfield.requestFocusInWindow();
	            featureTextfield.setText("");

	            //Select the new item and make it visible.
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


	
	 class FeatureListSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			 if (e.getValueIsAdjusting() == false) {

			        if (featureList.getSelectedIndex() == -1) {
			        //No selection, disable fire button.
			            deleteFeature.setEnabled(false);

			        } else {
			        //Selection, enable the fire button.
			        	deleteFeature.setEnabled(true);
			        }
			    }
		}
		
	}
}
