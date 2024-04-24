import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
    private JButton orderInladen;
    private JButton orderAanmaken;
    private JButton orderAapassen;
    private JButton orderVerwerken;
    private JLabel status;


    JLabel testerVoorStatusVerwijderLater;
    OrderBijhouderPanel orderBijhouder;
    RobotLocatieGUI robotLocatie;
    public GUI() {
        setLayout(null);
        setSize(854, 580);
        setTitle("HMI Applicatie");
        orderAapassen = new JButton("Order aanpassen");
        orderAanmaken = new JButton("Order aannmaken");
        orderInladen= new JButton("Order inladen");
        orderVerwerken = new JButton("Order verwerken");
        testerVoorStatusVerwijderLater = new JLabel("handmatig");
        status = new JLabel("Status:");
        orderBijhouder = new OrderBijhouderPanel();
        robotLocatie = new RobotLocatieGUI();
        orderInladen.setBounds(400,25,135,25);
        orderBijhouder.setBounds(400,60,400,300);
        orderAanmaken.setBounds(550,25,135,25);
        orderAapassen.setBounds(400,370,135,25);
        orderVerwerken.setBounds(550,370,135,25);
        robotLocatie.setBounds(10,20,380,450);
        add(orderInladen);
        add(orderAanmaken);
        add(status);
        add(orderBijhouder);
        add(orderAapassen);
        add(orderVerwerken);
        add(testerVoorStatusVerwijderLater);
        add(robotLocatie);
        status.setBounds(700,-13,100,100);
        testerVoorStatusVerwijderLater.setBounds(742,-13,100,100);
        orderInladen.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        OrderIDInvullenDialog order = new OrderIDInvullenDialog(this, true);
    }
}
