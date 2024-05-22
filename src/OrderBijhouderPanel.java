import database.DatabaseManager;
import entity.OrderLine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OrderBijhouderPanel extends JPanel{


    private GUI gui;

    private DatabaseManager databaseManager;

    public OrderBijhouderPanel(GUI gui, DatabaseManager databaseManager){
        this.gui = gui;
        this.databaseManager = databaseManager;
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ArrayList<OrderLine> orderLines = gui.getOrderLines();
        if (orderLines == null || orderLines.isEmpty()) {
            return;
        }
        int x = 20;
        int y = 20;
        if (orderLines.getFirst().getOrderID() == -1) {
            g.setColor(Color.RED);
            g.drawString("Ongeldige invoer", x, y);
            return;
        }
        g.drawString("Order ID: " + orderLines.get(0).getOrderID(), x, y);
        y += 20;
        for (OrderLine orderLine : orderLines) {
            String itemName = databaseManager.getProductName(orderLine.getStockItem().getStockItemID());
            if( orderLine.getStockItem().getX() == 0){
                g.setColor(Color.red);
                g.drawString(" ID " + orderLine.getStockItem().getStockItemID() + " | " + itemName + " (NIET IN STELLING)", x, y);
            } else {
                g.setColor(Color.black);
                g.drawString("Vak (" + orderLine.getStockItem().getX() + "," +  orderLine.getStockItem().getY() + ") ID " + orderLine.getStockItem().getStockItemID() + " | " + itemName, x, y);
            }
            y += 20;
        }
        g.setColor(Color.black);
        g.drawString(TSPBruteForce.getRoute(orderLines), x, y);
    }
}