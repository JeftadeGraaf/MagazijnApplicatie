package groep4.MagazijnApplicatie;

import javax.swing.*;
import java.awt.*;

public class RealtimeLocationPanel extends JPanel {

    private int xCoordinate;
    private int yCoordinate;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRobot(xCoordinate, yCoordinate, g);
    }

    public void setCoordinates(int x, int y){
        xCoordinate = x;
        yCoordinate = y;
        repaint();
    }

    public void drawRobot(int x, int y, Graphics g){
        if(xCoordinate < 1207){
            g.fillOval( 375, 310, 20,20);
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

    public static int mapRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        double ratio = (value - fromLow) / (fromHigh - fromLow);
        return (int) (toLow + (ratio * (toHigh - toLow)));
    }
}
