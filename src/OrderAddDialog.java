import database.DatabaseManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class OrderAddDialog extends JDialog {
    private ArrayList<Number> products = new ArrayList<>();
    private ArrayList<JButton> productButtons = new ArrayList<JButton>();
    private JTextField stockItemIdText = new JTextField();
    private Border blackLine = BorderFactory.createLineBorder(Color.black);
    private JPanel productsPanel = new JPanel();
    private JScrollPane productsScrollPane = new JScrollPane(productsPanel);

    private DatabaseManager databaseManager;

    public OrderAddDialog(JFrame jframe, boolean modal, DatabaseManager databaseManager){
        super(jframe, modal);
        this.databaseManager = databaseManager;
        setSize(600, 200);
        setTitle("Order toevoegen");
        setLayout(null);
        setResizable(true);

        JPanel customerId = new JPanel(null);
        customerId.setBounds(0, 0, 150, 50);
        customerId.setBorder(blackLine);
        add(customerId);

        JLabel customerIdLabel = new JLabel("Klant ID:");
        customerIdLabel.setBounds(10, 10, 50, 20);
        customerId.add(customerIdLabel);

        JTextField customerIdText = new JTextField();
        customerIdText.setBounds(60, 10, 50, 20);
        customerId.add(customerIdText);

        JPanel stockItemId = new JPanel();
        stockItemId.setBounds(0, 50, 150, 80);
        stockItemId.setBorder(blackLine);
        stockItemId.setLayout(null);
        add(stockItemId);

        JLabel stockItemIdLabel = new JLabel("Stock Item ID:");
        stockItemIdLabel.setBounds(10, 10, 90, 20);
        stockItemId.add(stockItemIdLabel);

        stockItemIdText.setBounds(90, 10, 50, 20);
        stockItemId.add(stockItemIdText);

        JButton stockItemIdButton = new JButton("Toevoegen");
        stockItemIdButton.setBounds(25, 50, 100, 20);
        stockItemIdButton.addActionListener(this::stockItemIdClicked);
        stockItemId.add(stockItemIdButton);

        productsPanel.setBorder(blackLine);
        productsPanel.setLayout(new GridLayout(0, 1));

        productsScrollPane.setBounds(198, 0, 390, 132);
        add(productsScrollPane);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(170, 135, 90, 20);
        cancelButton.addActionListener(this::cancelClicked);
        add(cancelButton);

        JButton okButton = new JButton("Ok");
        okButton.setBounds(270, 135, 90, 20);
        okButton.addActionListener(this::okClicked);
        add(okButton);




        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    public void cancelClicked(ActionEvent e) {
        dispose();
    }

    public void okClicked(ActionEvent e){

    }

    public void trashCanClicked(ActionEvent e){

    }

    public void stockItemIdClicked(ActionEvent e){
        try{
            Integer id = Integer.valueOf(stockItemIdText.getText());
            int size = products.size();

            products.add(id);

            JPanel productPanel = new JPanel();
//            productPanel.setBounds(5, 60*size + 5, 228, 55);
            productPanel.setBorder(blackLine);
            productsPanel.add(productPanel);

            String itemName = databaseManager.getProductName(id);
            JLabel productLabel = new JLabel(id + " :  " + itemName);
            productPanel.add(productLabel);

            JButton trashCanButton = new JButton("\uD83D\uDDD1");
            trashCanButton.setBounds(200, 0, 30, 30);
            trashCanButton.setFont(new Font("monospace", Font.PLAIN, 25));
            trashCanButton.addActionListener(this::trashCanClicked);
            productPanel.add(trashCanButton);
            productButtons.add(trashCanButton);


            productsPanel.repaint();
            productsPanel.revalidate();
        }
        catch (NumberFormatException n){
            stockItemIdText.setText("alleen nummers toegestaan");
        }
    }
}
