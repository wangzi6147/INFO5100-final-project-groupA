package ui.special;



import dto.Dealer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SpecialView{
    private JFrame jFrame;
    private JTable jTable;
    private JPanel addPane;
    private JLabel note;
    private JScrollPane resultPane;
    private SpecialTableModel SpecialTableModel;
    private JButton addButton;
    private SpringLayout springLayout;
    private String[] ColumnName;

    private Dealer dealer;
    private String dealerId;
    // Constructor
    public SpecialView(String dealerId) {
        this.dealer = dealer;
        this.dealerId = dealerId;



        this.createComponents();
        this.setLayout();
        this.addComponents();
        this.addBehaviors();
        this.display();

        setupListeners();
    }

    private void setupListeners(){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //JOptionPane.showMessageDialog(null,dealerId);
                addSpecial c = new addSpecial(null,dealerId);

            }
        });
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
        this.jFrame = new JFrame("Special for DealerID: "+ dealerId);
        this.addButton = new JButton("Add new special");
        this.note = new JLabel("Double click on a special to modify or delete.");
        this.SpecialTableModel = new SpecialTableModel(dealerId);



        System.out.println(SpecialTableModel.getlistsize());


        this.jTable = new JTable(this.SpecialTableModel);
        this.resultPane = new JScrollPane(this.jTable);
        this.addPane = new JPanel();
        this.springLayout = new SpringLayout();
    }



    private void setLayout() {
        this.addPane.setLayout(this.springLayout);
    }

    private void addComponents() {
        Container frameContainer = this.jFrame.getContentPane();
        frameContainer.add(this.resultPane,BorderLayout.NORTH);
        frameContainer.add(this.addPane);


        this.addPane.add(addButton);
        this.addPane.add(note);
        this.springLayout.putConstraint(SpringLayout.WEST, addButton, 300, SpringLayout.WEST, addPane);
        this.springLayout.putConstraint(SpringLayout.NORTH, addButton, 8, SpringLayout.NORTH, addPane);

    }


    private void addBehaviors() {
        jTable.addMouseListener(new DoubleClick());
    }


    private void display() {
        this.jFrame.setBounds(300, 300, 900, 800);
        this.jFrame.setVisible(true);
        this.jTable.setPreferredScrollableViewportSize(new Dimension(900,700));

        this.jTable.setRowHeight(120);
        this.jTable.setSize(600, 600);
    }


    class DoubleClick extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                modifySpecial c = new modifySpecial(null,dealerId);
                /*

                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                JFrame detailFrame = new JFrame();
                Container detailFramePane = detailFrame.getContentPane();
                SpringLayout springLayout = new SpringLayout();

                for (int i = 0; i < ColumnName.length; i++) {
                    JLabel title = new JLabel(ColumnName[i]);
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


                */






            }
        }
    }



}
