package groep4.MagazijnApplicatie;

import groep4.MagazijnApplicatie.database.DatabaseManager;
import groep4.MagazijnApplicatie.entity.OrderLine;
import groep4.MagazijnApplicatie.entity.Box;
import groep4.MagazijnApplicatie.entity.StockItem;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class OrderBijhouderPanel extends JPanel {


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
        Font boldFont = new Font("arial", Font.BOLD, 16);
        Font italicFont = new Font("arial", Font.ITALIC, 14);
        Font defaultFont = new Font("arial", Font.PLAIN, 12);
        ArrayList<OrderLine> orderLines = gui.getOrderLines();
        if (orderLines == null || orderLines.isEmpty()) {
            return;
        }
        int x = 20;
        int y = 20;
        if (orderLines.getFirst().getOrderID() == -1) {
            g.drawString("Momenteel geen ingeladen order gevonden.", x, y);
            return;
        }
        g.setFont(boldFont);
        int orderID = gui.getLoadedOrderID();
        g.drawString("Order-ID: " + orderID, x, y);
        y += 20;

        ArrayList<StockItem> itemsInOrder = new ArrayList<>();

        for (OrderLine orderLine :orderLines){
            itemsInOrder.add(orderLine.getStockItem());
        }

        ArrayList<Box> boxList = BestFitDecreasing.calculateBPP(itemsInOrder, 6);

        for (int i = 0; i < boxList.size(); i++) {
            g.setFont(italicFont);
            g.drawString("Doos " + (i+1), x, y);
            y+= 20;
            g.setFont(defaultFont);
            ArrayList<StockItem> itemsInBox = boxList.get(i).getProductlist();
            for (int j = 0; j < itemsInBox.size(); j++) {
                String itemName = databaseManager.getProductName(itemsInBox.get(j).getStockItemID());
                if(itemsInBox.get(j).getX() == 0){
                    g.setColor(Color.red);
                    g.drawString(" ID " + itemsInBox.get(j).getStockItemID() + " | " + itemName + " | NIET IN STELLING", x, y);
                } else {
                    g.setColor(Color.black);
                    g.drawString(" ID " + itemsInBox.get(j).getStockItemID() + " | " + itemName + " | Vak (" + itemsInBox.get(j).getX() + "," + itemsInBox.get(j).getY() + ")", x, y);
                }
                g.setColor(Color.black);
                y += 20;
            }
            y += 10;
        }
        g.drawString(TSPBruteForce.getRoute(orderLines), x, y);
    }
}