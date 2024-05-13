import javax.swing.*;
import java.awt.*;

public class RobotLocatieGUI extends JPanel{

    private JLabel toFetchLabel;
    private JLabel fetchedLabel;
    private JLabel robotLabel;

    public RobotLocatieGUI(){
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
        g.setColor(Color.lightGray);
        g.fillRect(0,400,450,150);
        drawPackageIconLarge(g, Color.red, 40, 440);
        drawPackageIconLarge(g, Color.green, 40, 500);
        drawRackGrid(g);
    }

    public void drawRackGrid(Graphics g){
        g.setColor(Color.black);
        int cellSize = 70;
        int x = 50;
        int y = 25;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                g.drawRect(x, y, cellSize, cellSize);
                y += cellSize;
            }
            x += cellSize;
            y = 25;
        }
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
