package groep4.magazijnApplicatie;

import groep4.magazijnApplicatie.database.DatabaseManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class OrderAddDialog extends JDialog implements ActionListener {
    private final ArrayList<JPanel> productPanels = new ArrayList<>();
    private final ArrayList<JButton> productButtons = new ArrayList<>();
    private final ArrayList<Integer> productIds = new ArrayList<>();
    private final JTextField stockItemIdText = new JTextField();
    private final Border blackLine = BorderFactory.createLineBorder(Color.black);
    private final JPanel productsPanel = new JPanel();
    private final JTextField customerIdText = new JTextField();
    private Integer orderId = 0;
    private final JLabel errorText = new JLabel("");
    private final DatabaseManager databaseManager;

    public OrderAddDialog(JFrame jframe, boolean modal, DatabaseManager databaseManager, int loadedOrderID){
        super(jframe, modal);
        this.databaseManager = databaseManager;
        setSize(620, 250);

        setTitle("Order toevoegen");
        setLayout(null);
        setResizable(true);

        JPanel customerId = new JPanel(null);
        customerId.setBounds(20, 20, 150, 50);
        customerId.setBorder(blackLine);
        add(customerId);

        JLabel customerIdLabel = new JLabel("Klant ID:");
        customerIdLabel.setBounds(10, 10, 50, 20);
        customerId.add(customerIdLabel);

        customerIdText.setBounds(60, 10, 50, 20);
        customerId.add(customerIdText);

        JPanel stockItemId = new JPanel();
        stockItemId.setBounds(20, 70, 150, 80);
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
        stockItemIdButton.addActionListener(e2 -> stockItemIdClicked());
        stockItemId.add(stockItemIdButton);

        productsPanel.setBorder(blackLine);
        productsPanel.setLayout(new GridLayout(0, 1));

        JScrollPane productsScrollPane = new JScrollPane(productsPanel);
        productsScrollPane.setBounds(198, 20, 390, 132);
        add(productsScrollPane);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(400, 165, 90, 20);
        cancelButton.addActionListener(e1 -> cancelClicked());
        add(cancelButton);

        JButton okButton = new JButton("Ok");
        okButton.setBounds(500, 165, 90, 20);
        okButton.addActionListener(e -> okClicked());
        add(okButton);

        errorText.setBounds(100, 150, 125, 25);

        add(errorText);

        if(orderId == 0){
            orderId = loadedOrderID;
        }
        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    public void cancelClicked() {
        dispose();
    }

    public void okClicked(){
        if(Objects.equals(customerIdText.getText(), "") || productButtons.isEmpty()){
            dispose();
        } else {
            try {
                int customerId = Integer.parseInt(stockItemIdText.getText());
                orderId = databaseManager.addNewOrder(customerId);
                for (Integer productId : productIds) {
                    databaseManager.addProductToOrder(orderId, productId);
                }
                dispose();
            } catch (NumberFormatException ex) {
                errorText.setText("alleen nummers toegestaan");
            }

        }
    }


    public void stockItemIdClicked(){
        try{
            Integer id = Integer.valueOf(stockItemIdText.getText());
            JPanel productPanel = new JPanel();
            productPanel.setLayout(new BorderLayout());
            productPanels.add(productPanel);
            productPanel.setBorder(blackLine);
            productsPanel.add(productPanel);

            String itemName = databaseManager.getProductName(id);
            JLabel productLabel = new JLabel(id + " :  " + itemName);
            productPanel.add(productLabel);

            JButton trashCanButton = new JButton("\uD83D\uDDD1");
            trashCanButton.setBounds(200, 0, 30, 30);
            trashCanButton.setFont(new Font("monospace", Font.PLAIN, 25));
            trashCanButton.addActionListener(this);
            productPanel.add(trashCanButton, BorderLayout.EAST);
            productButtons.add(trashCanButton);
            productIds.add(id);


            productsPanel.repaint();
            productsPanel.revalidate();
        }
        catch (NumberFormatException n){
            errorText.setText("alleen nummers toegestaan");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < productButtons.size(); i++) {
            if(e.getSource().equals(productButtons.get(i))){
                System.out.println(i);
                productsPanel.remove(productPanels.get(i));
                productsPanel.revalidate();
                productsPanel.repaint();
            }
        }
    }

    public int getOrderId(){
        return orderId;
    }
}
