package ui.SpecificDealer;

import dto.Dealer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InventoryView {
    private JFrame jFrame;
    private JTable jTable;
    private JPanel searchPane;
    private JScrollPane resultPane;
    private InventoryTableModel inventoryTableModel;
    private JButton searchButton;
    private SpringLayout springLayout;
    private String[] searchFields;
    private JComboBox[] jComboBoxes;
    private Dealer dealer;

    // Constructor
    public InventoryView(Dealer dealer) {
        this.dealer = dealer;
        String[] temp = {"Category:", "Year:", "Make:", "Model:", "Trim:", "Type:", "Price:"};
        this.searchFields = temp;
        this.jComboBoxes = new JComboBox[9];
        this.createComponents();
        this.setLayout();
        this.addComponents();
        this.addBehaviors();
        this.display();
    }

    private void createComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.jFrame = new JFrame();
        this.searchButton = new JButton("Search");
        this.inventoryTableModel = new InventoryTableModel();
        this.jTable = new JTable(this.inventoryTableModel);
        this.resultPane = new JScrollPane(this.jTable);
        this.searchPane = new JPanel();
        this.springLayout = new SpringLayout();
    }

    private void setLayout() {
        this.searchPane.setLayout(this.springLayout);
    }

    private void addComponents() {
        Container frameContainer = this.jFrame.getContentPane();
        frameContainer.add(this.resultPane, BorderLayout.EAST);
        frameContainer.add(this.searchPane);

        /////

        /////

        for (int i = 0; i < searchFields.length; i++) {
            JLabel jLabel = new JLabel(searchFields[i]);
            JTextField jTextField = new JTextField("", 15);
            this.searchPane.add(jLabel);
            ComboBoxModel comboBoxModel = new DefaultComboBoxModel(searchFields);
            ((DefaultComboBoxModel) comboBoxModel).insertElementAt("All", 0);
            jComboBoxes[i] = new JComboBox(comboBoxModel);

            this.searchPane.add(jComboBoxes[i]);
            this.springLayout.putConstraint(SpringLayout.WEST, jLabel, 50, SpringLayout.WEST, searchPane);
            this.springLayout.putConstraint(SpringLayout.NORTH, jLabel, 25 * (i + 1), SpringLayout.NORTH, searchPane);
            this.springLayout.putConstraint(SpringLayout.WEST, jComboBoxes[i], 150, SpringLayout.WEST, searchPane);
            this.springLayout.putConstraint(SpringLayout.NORTH, jComboBoxes[i], 25 * (i + 1), SpringLayout.NORTH, searchPane);
        }
        this.searchPane.add(searchButton);
        this.springLayout.putConstraint(SpringLayout.WEST, searchButton, 150, SpringLayout.WEST, searchPane);
        this.springLayout.putConstraint(SpringLayout.NORTH, searchButton, 200, SpringLayout.NORTH, searchPane);
    }

    private void addBehaviors() {
        jTable.addMouseListener(new DoubleClick());
    }

    private void display() {
        this.jFrame.setBounds(600, 200, 1000, 600);
        this.jFrame.setVisible(true);
        this.jTable.setPreferredScrollableViewportSize(jTable.getPreferredSize());
        this.jTable.setRowHeight(120);
        this.jTable.setSize(600, 600);
    }

    public JLabel getIcon() {
        ImageIcon icon = new ImageIcon("/Users/petter/test/Cadillac.jpg");
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(400, 300, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(icon);
        return jLabel;
    }

    public void addItemsToComboBox(JComboBox jComboBox) {

    }

    class DoubleClick extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                JFrame detailFrame = new JFrame();
                Container detailFramePane = detailFrame.getContentPane();
                SpringLayout springLayout = new SpringLayout();
                detailFramePane.add(getIcon());

                for (int i = 0; i < searchFields.length; i++) {
                    JLabel title = new JLabel(searchFields[i]);
                    JLabel text = new JLabel("Car");
                    detailFramePane.add(title);
                    detailFramePane.add(text);
                    springLayout.putConstraint(SpringLayout.WEST, title, 50, SpringLayout.WEST, detailFramePane);
                    springLayout.putConstraint(SpringLayout.NORTH, title, 25 * (i + 15), SpringLayout.NORTH, detailFramePane);
                    springLayout.putConstraint(SpringLayout.WEST, text, 150, SpringLayout.WEST, detailFramePane);
                    springLayout.putConstraint(SpringLayout.NORTH, text, 25 * (i + 15), SpringLayout.NORTH, detailFramePane);
                }

                detailFrame.setLayout(springLayout);
                detailFrame.setVisible(true);
                detailFrame.setBounds(800, 200, 500, 900);
            }
        }
    }
}
