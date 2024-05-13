import database.DatabaseManager;
import entity.OrderLine;

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

    private JButton cancelButton = new JButton("Annuleren");

    public updateOrderDialog(JFrame frame, boolean modal, int orderID, ArrayList<OrderLine> orderLines, DatabaseManager databaseManager){
        super(frame, modal);
        this.orderID = orderID;
        this.orderLines = orderLines;
        this.databaseManager = databaseManager;
        setTitle("Order aanpassen: " + orderID);
        setSize(500,300);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        removeLabel.setBounds(25, 15, 200, 25);
        add(removeLabel);

        scrollFrame.setLayout(new GridLayout(0, 2));

        for(OrderLine orderLine : orderLines){
            scrollFrame.add(new JLabel("Product " + orderLine.getStockItem().getStockItemID()));
            JButton button = new JButton("Verwijder");
            buttonArray.add(button);
            scrollFrame.add(button);
            button.addActionListener(this);
        }

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

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < buttonArray.size(); i++) {
            if(e.getSource().equals(buttonArray.get(i))){
                databaseManager.removeOrderLine(orderID, orderLines.get(i).getOrderID());
            }
        }
    }
}