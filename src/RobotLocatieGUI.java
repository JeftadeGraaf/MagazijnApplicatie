import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RobotLocatieGUI extends JPanel{

    private JLabel legendaCross;
    private JLabel legendaCheck;
    private JLabel legendaRobot;
    private void drawCheck(Graphics g, int locatieX, int locatieY){
        g.setColor(Color.GREEN);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g.drawLine(locatieX-20,locatieY+20,locatieX ,locatieY + 40); // teken een diagonale lijn
        g.drawLine(locatieX,locatieY+40,locatieX+30,locatieY);
        g2.setStroke(new BasicStroke(1));
        g.setColor(Color.black);
    }
    private void drawCross(Graphics g, int locatieX, int locatieY){
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.red);
        g2.setStroke(new BasicStroke(3));
        g.drawLine(locatieX,locatieY,locatieX+40 ,locatieY + 40); // teken een diagonale lijn
        g.drawLine(locatieX,locatieY+40,locatieX+40,locatieY);
        g2.setStroke(new BasicStroke(1));
        g.setColor(Color.black);
    }

    public RobotLocatieGUI(){
        setLayout(null);
        //setBorder(BorderFactory.createLineBorder(Color.red));
        //Activeer border alleen als je aan de JPanel werkt. Is alleen voor dev niet voor user
        legendaCheck = new JLabel("Opgehaald product");
        legendaCross = new JLabel("Op te halen product");
        legendaRobot = new JLabel("Locatie robot");
        add(legendaCheck);
        add(legendaCross);
        add(legendaRobot);
        legendaRobot.setBounds(270,300,150,100);
        legendaCross.setBounds(100, 300, 150, 100);
        legendaCheck.setBounds(100, 360, 150, 100);
        setVisible(true);
}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.lightGray);
        g.fillRect(30,320,330,120);
        g.setColor(Color.black);
        g.drawRect(30,320,330,120);
        int breedte = 60;
        int hoogte = 60;
        int x = 38;
        int y = 10;
        int reset_X = 0;
        reset_X = x;
        int teller;
        teller = 0;
        drawCheck(g,240, 80); //Een vakje naar links of rechts is de breede, naar boven naar beneden is hoogte. Beide zijn 60
        drawCross(g,50,20);
        drawCross(g,50,260);
        drawCross(g,50,330);
        drawCheck(g,65, 390);

        //Optimalizer later
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(3));
        g.setColor(Color.orange);
        g.fillRect(220,330,40,40);
        g.fillRect(258,110,40,40);
        g.drawRect(258,110,40,40);
        g.drawRect(220,330,40,40);
        g2.setStroke(new BasicStroke(1));
        g.setColor(Color.black);

        //Optimalizer later

        for (int i = 0; i < 25; i++) {
            g.drawRect(x,y,hoogte, breedte);
            x = x + breedte;
            teller++;
            if ( teller == 5){
                y = y + hoogte;
                x = reset_X;
                teller = 0;
            }
        }
    }
}
