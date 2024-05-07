import entity.OrderLine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OrderBijhouderPanel extends JPanel{


    private GUI gui;

    public OrderBijhouderPanel(GUI gui){
        this.gui = gui;
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
        for (OrderLine orderLine : orderLines) {
            g.drawString("OrderID: " + orderLine.getOrderID() + "; ItemID: " + orderLine.getStockItem().getStockItemID(), x, y);
            y += 20;
        }
    }
}
