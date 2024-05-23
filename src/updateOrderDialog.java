import com.mysql.cj.x.protobuf.MysqlxCrud;
import database.DatabaseManager;
import entity.OrderLine;
import entity.StockItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class updateOrderDialog extends JDialog implements ActionListener {

    private int orderID;
    private ArrayList<OrderLine> orderLines;
    private DatabaseManager databaseManager;
    private ArrayList<JButton> buttonArray = new ArrayList<>();
    private JLabel removeLabel = new JLabel("Product verwijderen");
    private JLabel addLabel = new JLabel("Product toevoegen");
    private JLabel addTextLabel = new JLabel("Product ID:");
    private JTextField addText = new JTextField("");
    private JButton addButton = new JButton("Toevoegen aan order");
    private JPanel scrollFrame = new JPanel();
    private JScrollPane scrollPane;

    private JButton cancelButton = new JButton("Ok");

    public updateOrderDialog(JFrame frame, boolean modal, int orderID, ArrayList<OrderLine> orderLines, DatabaseManager databaseManager) {
        super(frame, modal);
        this.orderID = orderID;
        this.orderLines = orderLines;
        this.databaseManager = databaseManager;
        setTitle("Order aanpassen: " + orderID);
        setSize(500, 300);
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
        removeLabel.setBounds(25, 15, 200, 25);
        add(removeLabel);

        scrollFrame.setLayout(new GridLayout(0, 2));

        scrollPane = new JScrollPane(scrollFrame);
        scrollPane.setBounds(25, 50, 200, 175);
        add(scrollPane);

        addLabel.setBounds(250, 15, 200, 25);
        add(addLabel);
        addTextLabel.setBounds(250, 50, 90, 25);
        add(addTextLabel);
        addText.setBounds(325, 50, 125, 25);
        add(addText);
        addButton.setBounds(250, 85, 200, 25);
        add(addButton);
        cancelButton.setBounds(350, 200, 100, 25);
        add(cancelButton);

        cancelButton.addActionListener(this);
        addButton.addActionListener(this);

        // Clear any pre-filled text
        addText.setText("");
    }

    private void initializeOrderLines() {
        for (OrderLine orderLine : orderLines) {
            String itemName = databaseManager.getProductName(orderLine.getStockItem().getStockItemID());
            scrollFrame.add(new JLabel("Product " + orderLine.getStockItem().getStockItemID()));
            JButton button = new JButton("Verwijder");
            buttonArray.add(button);
            scrollFrame.add(button);
            button.addActionListener(this);
        }
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

                JLabel itemLabel = new JLabel("Product " + itemID);
                scrollFrame.add(itemLabel);
                JButton removeButton = new JButton("Verwijder");
                scrollFrame.add(removeButton);
                buttonArray.add(removeButton);

                removeButton.addActionListener(this);

                scrollFrame.revalidate();
                scrollFrame.repaint();

                // Clear the text field after adding the product
                addText.setText("");
            } catch (NumberFormatException numberFormatException) {
                addText.setText("Ongeldige ingave!");
            }
        } else {
            for (int i = 0; i < buttonArray.size(); i++) {
                if (e.getSource().equals(buttonArray.get(i))) {
                    databaseManager.removeOrderLine(orderID, orderLines.get(i).getStockItem().getStockItemID());
                    // Updating the orderLines list
                    orderLines.remove(i);
                    // Removing the corresponding button and label
                    scrollFrame.remove(i * 2); // Remove label
                    scrollFrame.remove(i * 2); // Remove button (after the label is removed, the button shifts to the label's position)
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
