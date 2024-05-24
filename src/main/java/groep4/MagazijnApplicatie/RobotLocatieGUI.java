package groep4.MagazijnApplicatie;

import groep4.MagazijnApplicatie.entity.OrderLine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RobotLocatieGUI extends JPanel{

    private final int cellSize;
    private final GUI gui;

    public RobotLocatieGUI(GUI gui){
        this.gui = gui;
        cellSize = 65;
        setBorder(BorderFactory.createLineBorder(Color.black));

        //Null layout for absolute item placement
        setLayout(null);

        //Label instantiating
        JLabel fetchedLabel = new JLabel("Opgehaald product");
        JLabel toFetchLabel = new JLabel("Op te halen product");
        JLabel robotLabel = new JLabel("Locatie robot");
        fetchedLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        toFetchLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        robotLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        //Adding items to frame and setting bounds
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

        if(!orderLines.isEmpty() && orderLines.getFirst().getOrderID() != -1){
            //Drawing the ID's of items in an order at the location in the warehouse rack
            for (OrderLine orderLine : orderLines) {
                int x = orderLine.getStockItem().x();
                int y = orderLine.getStockItem().y();
                if (x > 0 && y > 0) {
                    int xIcon = 340 - ((x - 1) * 65);
                    int yIcon = 295 - ((y - 1) * 65);
                    int xLabel = 295 - ((x - 1) * 65);
                    int yLabel = 305 - ((y - 1) * 65);
                    drawPackageIcon(g, Color.red, xIcon, yIcon);
                    g.setColor(Color.black);
                    g.setFont(new Font("Calibri", Font.PLAIN, 20));
                    g.drawString(String.valueOf(orderLine.getStockItem().stockItemID()), xLabel, yLabel);
                }
            }
            //Drawing the route the robot will take
            drawRobotRoute(g, orderLines);
        }
    }

    public void drawRackGrid(Graphics g){
        g.setColor(Color.black);
        int x = 30;
        int y = 25;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        //Drawing the rack grid
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                g.drawRect(x, y, cellSize, cellSize);
                y += cellSize;
            }
            x += cellSize;
            y = 25;
        }

        //Drawing labels for each column and row
        g.setFont(new Font("Calibri", Font.BOLD, 14));
        for (int i = 0; i < 5; i++) {
            g.drawString(String.valueOf(i+1), 10, (320-(i*cellSize)));
        }
        for (int i = 0; i < 5; i++) {
            g.drawString(String.valueOf(i+1), (320 - (i*cellSize)), 370);
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

    public void drawRobotRoute(Graphics g, ArrayList<OrderLine> orderLines){
        int yStart = 317;
        int xStart = 323;
        String route = TSPBruteForce.getRoute(orderLines);
        String[] coordinatesArray = route.substring(1).split(",");
        for (int i = 0; i < coordinatesArray.length-1; i++) {
            int x1 = xStart - ((Integer.parseInt(String.valueOf(coordinatesArray[i].charAt(0))) -1) * cellSize);
            int y1 = yStart - ((Integer.parseInt(String.valueOf(coordinatesArray[i].charAt(2))) -1) * cellSize);
            int x2 = xStart - ((Integer.parseInt(String.valueOf(coordinatesArray[i+1].charAt(0))) -1) * cellSize);
            int y2 = yStart - ((Integer.parseInt(String.valueOf(coordinatesArray[i+1].charAt(2))) -1) * cellSize);

            if(y1 > yStart){
                y1 = yStart;
            }
            if (y2 > yStart){
                y2 = yStart;
            }
            if(x1 != x2 || y1 != y2){
                g.fillOval(x1 - 5,y1 - 5, 10,10);
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }
}