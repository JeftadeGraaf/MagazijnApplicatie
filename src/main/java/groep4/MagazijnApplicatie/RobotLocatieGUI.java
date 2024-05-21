package groep4.MagazijnApplicatie;

import groep4.MagazijnApplicatie.entity.OrderLine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RobotLocatieGUI extends JPanel{

    private JLabel toFetchLabel;
    private JLabel fetchedLabel;
    private JLabel robotLabel;

    private GUI gui;

    public RobotLocatieGUI(GUI gui){
        this.gui = gui;
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black));
        fetchedLabel = new JLabel("Opgehaald product");
        toFetchLabel = new JLabel("Op te halen product");
        robotLabel = new JLabel("Locatie robot");
        fetchedLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        toFetchLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        robotLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        add(fetchedLabel);
        add(toFetchLabel);
        add(robotLabel);
        robotLabel.setBounds(300,440,200,25);
        toFetchLabel.setBounds(75, 500, 200, 25);
        fetchedLabel.setBounds(75, 440, 200, 25);
        setVisible(true);
}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0,400,450,150);
        g.setColor(Color.lightGray);
        g.fillRect(2,402,446,146);
        drawPackageIconLarge(g, Color.green, 40, 440);
        drawPackageIconLarge(g, Color.red, 40, 500);
        drawRackGrid(g);
        ArrayList<OrderLine> orderLines = gui.getOrderLines();
        if(orderLines.size() > 0 && orderLines.get(0).getOrderID() != -1){
            for (int i = 0; i < orderLines.size(); i++) {
                int x = orderLines.get(i).getStockItem().getX();
                int y = orderLines.get(i).getStockItem().getY();
                if(x > 0 && y > 0){
                    int xIcon = 340 - ((x-1) * 65);
                    int yIcon = 295 - ((y-1) * 65);
                    int xLabel = 295 - ((x-1) * 65);
                    int yLabel = 305 - ((y-1) * 65);
                    drawPackageIcon(g, Color.red, xIcon, yIcon);
                    g.setColor(Color.black);
                    g.setFont(new Font("Calibri", Font.PLAIN, 20));
                    g.drawString(String.valueOf(orderLines.get(i).getStockItem().getStockItemID()), xLabel,yLabel);
                }
            }
        }
    }

    public void drawRackGrid(Graphics g){
        g.setColor(Color.black);
        int cellSize = 65;
        int x = 30;
        int y = 25;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                g.drawRect(x, y, cellSize, cellSize);
                y += cellSize;
            }
            x += cellSize;
            y = 25;
        }
        g.drawRect(355, 285, cellSize, cellSize);
    }

    public void drawPackageIcon(Graphics g, Color color, int x, int y){
        g.setColor(color);
        g.fillRect(x, y, 10, 10);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g.drawLine(x, y, x + 2, y - 3);
        g.drawLine(x + 10, y, x + 8, y - 3);
    }

    public void drawPackageIconLarge(Graphics g, Color color, int x, int y){
        g.setColor(color);
        g.fillRect(x, y, 25, 25);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g.drawLine(x, y, x + 10, y - 12);
        g.drawLine(x + 25, y, x + 15, y - 12);
    }
}