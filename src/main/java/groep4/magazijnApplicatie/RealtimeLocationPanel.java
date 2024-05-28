package groep4.magazijnApplicatie;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RealtimeLocationPanel extends JPanel {

    private int xCoordinate;
    private int yCoordinate;

    private final ArrayList<int[]> retrievedProducts = new ArrayList<>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.blue);
        drawRobot(xCoordinate, yCoordinate, g);
        drawRetrievedProducts(g);
    }

    public void setCoordinates(int x, int y){
        xCoordinate = x;
        yCoordinate = y;
        repaint();
    }

    public void drawRobot(int x, int y, Graphics g){
        if(xCoordinate < 1207){
            g.fillOval( 378, 330, 20,20);
            return;
        }
        if(yCoordinate < 60){
            yCoordinate = 60;
        }
        int xCoordinate = mapRange(x, 4757, 1207, 30, 355);
        int yCoordinate = mapRange(y, 2500, 60, 25, 350);
        g.setColor(Color.blue);
        g.fillOval(xCoordinate - 10, yCoordinate - 10, 20,20);
    }

    private void drawRetrievedProducts(Graphics g){
        for (int[] coordinates : retrievedProducts){
            drawPackageIcon(g, Color.decode("#00b80f"), coordinates[0], coordinates[1]);
        }
    }

    private void drawPackageIcon(Graphics g, Color color, int x, int y){
        g.setColor(color);
        g.fillRect(x, y, 10, 10);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g.drawLine(x, y, x + 2, y - 3);
        g.drawLine(x + 10, y, x + 8, y - 3);
    }

    private static int mapRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        double ratio = (value - fromLow) / (fromHigh - fromLow);
        return (int) (toLow + (ratio * (toHigh - toLow)));
    }

    public void addRetrievedProduct(int x, int y){
        int xIcon = 340 - ((x - 1) * 65);
        int yIcon = 295 - ((y - 1) * 65);
        retrievedProducts.add(new int[] {xIcon, yIcon});
    }

    public void clearRetrievedProducts(){
        retrievedProducts.clear();
    }
}
