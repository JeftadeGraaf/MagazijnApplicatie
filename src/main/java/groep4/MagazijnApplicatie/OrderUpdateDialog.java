package groep4.MagazijnApplicatie;

import groep4.MagazijnApplicatie.database.DatabaseManager;
import groep4.MagazijnApplicatie.entity.OrderLine;
import groep4.MagazijnApplicatie.entity.StockItem;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderUpdateDialog extends JDialog implements ActionListener {

    private final int orderID;
    private final ArrayList<OrderLine> orderLines;
    private final DatabaseManager databaseManager;
    private final ArrayList<JButton> buttonArray = new ArrayList<>();
    private final JLabel removeLabel = new JLabel("Product verwijderen");
    private final JLabel addLabel = new JLabel("Product toevoegen");
    private final JLabel addTextLabel = new JLabel("Product ID:");
    private final JTextField addText = new JTextField("");
    private final JButton addButton = new JButton("Toevoegen aan order");
    private final JPanel scrollFrame = new JPanel();
    private Border blackLine = BorderFactory.createLineBorder(Color.black);
    private JScrollPane scrollPane;

    private final JButton cancelButton = new JButton("Ok");

    public OrderUpdateDialog(JFrame frame, boolean modal, int orderID, ArrayList<OrderLine> orderLines, DatabaseManager databaseManager) {
        super(frame, modal);
        this.orderID = orderID;
        this.orderLines = orderLines;
        this.databaseManager = databaseManager;
        setTitle("Order aanpassen: " + orderID);
        setSize(800, 300);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Initialize components
        initializeComponents();
        // Populate initial order lines
        initializeOrderLines();

        setVisible(true);
    }

    private void initializeComponents() {
        removeLabel.setBounds(250, 15, 200, 25);
        add(removeLabel);

        scrollFrame.setLayout(new GridLayout(0, 1));

        scrollPane = new JScrollPane(scrollFrame);
        scrollPane.setBounds(250, 50, 500, 175);
        add(scrollPane);

        addLabel.setBounds(25, 15, 200, 25);
        add(addLabel);
        addTextLabel.setBounds(25, 50, 90, 25);
        add(addTextLabel);
        addText.setBounds(100, 50, 125, 25);
        add(addText);
        addButton.setBounds(25, 85, 200, 25);
        add(addButton);
        cancelButton.setBounds(650, 230, 100, 25);
        add(cancelButton);

        cancelButton.addActionListener(this);
        addButton.addActionListener(this);

        // Clear any pre-filled text
        addText.setText("");
    }

    private void initializeOrderLines() {
        for (OrderLine orderLine : orderLines) {
            int itemID = orderLine.getStockItem().getStockItemID();
            JPanel productPanel = new JPanel();
            productPanel.setLayout(new BorderLayout());
            scrollFrame.add(productPanel);
            productPanel.setBorder(blackLine);
            scrollFrame.add(productPanel);

            String itemName = databaseManager.getProductName(itemID);
            JLabel productLabel = new JLabel(itemID + " :  " + itemName);
            productPanel.add(productLabel);

            JButton trashCanButton = new JButton("\uD83D\uDDD1");
            trashCanButton.setBounds(200, 0, 30, 30);
            trashCanButton.setFont(new Font("monospace", Font.PLAIN, 25));
            trashCanButton.addActionListener(this);
            productPanel.add(trashCanButton, BorderLayout.EAST);
            buttonArray.add(trashCanButton);



        }
        scrollFrame.repaint();
        scrollFrame.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cancelButton)) {
            dispose();
        } else if (e.getSource().equals(addButton)) {
            try {
                int itemID = Integer.parseInt(addText.getText().trim());
                databaseManager.addProductToOrder(orderID, itemID);
                OrderLine orderLine = new OrderLine();
                orderLine.setOrderID(orderID);
                int[] itemCoordinates = databaseManager.getItemRackLocation(itemID);
                orderLine.setStockItem(new StockItem(itemID, itemCoordinates[0], itemCoordinates[1], databaseManager.getItemWeight(itemID)));
                orderLines.add(orderLine);

                /*

                JLabel itemLabel = new JLabel("Product " + itemID);
                scrollFrame.add(itemLabel);
                JButton removeButton = new JButton("Verwijder");
                scrollFrame.add(removeButton);
                buttonArray.add(removeButton);

                removeButton.addActionListener(this);

                scrollFrame.revalidate();
                scrollFrame.repaint();
                               */

                JPanel productPanel = new JPanel();
                productPanel.setLayout(new BorderLayout());
                scrollFrame.add(productPanel);
                productPanel.setBorder(blackLine);
                scrollFrame.add(productPanel);

                String itemName = databaseManager.getProductName(itemID);
                JLabel productLabel = new JLabel(itemID + " :  " + itemName);
                productPanel.add(productLabel);

                JButton trashCanButton = new JButton("\uD83D\uDDD1");
                trashCanButton.setBounds(200, 0, 30, 30);
                trashCanButton.setFont(new Font("monospace", Font.PLAIN, 25));
                trashCanButton.addActionListener(this);
                productPanel.add(trashCanButton, BorderLayout.EAST);
                buttonArray.add(trashCanButton);


                scrollFrame.repaint();
                scrollFrame.revalidate();

                // Clear the text field after adding the product
                addText.setText("");
            } catch (NumberFormatException numberFormatException) {
                addText.setText("Ongeldige ingave!");
            }
        } else {
            for (int i = 0; i < buttonArray.size(); i++) {
                if (e.getSource().equals(buttonArray.get(i))) {
                    databaseManager.removeOrderLine(orderID, orderLines.get(i).getStockItem().stockItemID());
                    // Updating the orderLines list
                    orderLines.remove(i);
                    // Removing the corresponding button and label
                    scrollFrame.remove(i); // Remove panel
                    // Removing the button from buttonArray
                    buttonArray.remove(i);
                    // Refreshing the panel
                    scrollFrame.revalidate();
                    scrollFrame.repaint();
                    break;
                }
            }
        }
    }
}
