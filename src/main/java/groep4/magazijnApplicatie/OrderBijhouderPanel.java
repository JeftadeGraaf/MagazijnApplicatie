package groep4.magazijnApplicatie;

import groep4.magazijnApplicatie.database.DatabaseManager;
import groep4.magazijnApplicatie.entity.OrderLine;
import groep4.magazijnApplicatie.entity.Box;
import groep4.magazijnApplicatie.entity.StockItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderBijhouderPanel extends JPanel {


    private final GUI gui;

    private final DatabaseManager databaseManager;

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
        Font boldFont = new Font("arial", Font.BOLD, 16);
        Font italicFont = new Font("arial", Font.ITALIC, 14);
        Font defaultFont = new Font("arial", Font.PLAIN, 12);
        ArrayList<OrderLine> orderLines = gui.getOrderLines();
        System.out.println("PANEL:");
        for (OrderLine orderLine : orderLines){
            System.out.println(orderLine.stockItem().stockItemID());
        }
        if (orderLines.isEmpty()) {
            return;
        }
        int x = 20;
        int y = 20;
        if (orderLines.getFirst().orderID() == -1) {
            g.drawString("Momenteel geen ingeladen order gevonden.", x, y);
            return;
        }
        g.setFont(boldFont);
        int orderID = gui.getLoadedOrderID();
        g.drawString("Order-ID: " + orderID, x, y);
        y += 20;

        ArrayList<StockItem> itemsInOrder = new ArrayList<>();

        for (OrderLine orderLine :orderLines){
            itemsInOrder.add(orderLine.stockItem());
        }

        ArrayList<Box> boxList = BestFitDecreasing.calculateBPP(itemsInOrder, 6);

        for (int i = 0; i < boxList.size(); i++) {
            g.setFont(italicFont);
            g.drawString("Doos " + (i+1), x, y);
            y+= 20;
            g.setFont(defaultFont);
            List<StockItem> itemsInBox = boxList.get(i).getProductlist();
            for (StockItem inBox : itemsInBox) {
                String itemName = databaseManager.getProductName(inBox.stockItemID());
                if (inBox.x() == 0) {
                    g.setColor(Color.red);
                    g.drawString(" ID " + inBox.stockItemID() + " | " + itemName + " | NIET IN STELLING", x, y);
                } else {
                    g.setColor(Color.black);
                    g.drawString(" ID " + inBox.stockItemID() + " | " + itemName + " | Vak (" + inBox.x() + "," + inBox.y() + ")", x, y);
                }
                g.setColor(Color.black);
                y += 20;
            }
            y += 10;
        }
        String tspRoute = TSPBruteForce.getRoute(orderLines);
        gui.setTSPRoute(tspRoute);
        g.drawString(tspRoute, x, y);
    }
}