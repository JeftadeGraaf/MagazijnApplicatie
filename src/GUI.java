import database.DatabaseManager;
import entity.OrderLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener {
    private JButton orderInladen;
    private JButton orderAanmaken;
    private JButton orderAanpassen;
    private JButton orderVerwerken;
    private JLabel status;

    private int loadedOrderID = 0;

    JLabel testerVoorStatusVerwijderLater;
    OrderBijhouderPanel orderBijhouder;
    RobotLocatieGUI robotLocatie;
    private DatabaseManager databaseManager;
    private ArrayList<OrderLine> orderLines = new ArrayList<>();

    public GUI(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        setLayout(null);
        setSize(1000, 650);
        setTitle("HMI Applicatie");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        orderAanpassen = new JButton("Order aanpassen");
        orderAanmaken = new JButton("Order aanmaken");
        orderInladen= new JButton("Order inladen");
        orderVerwerken = new JButton("Order verwerken");
        status = new JLabel("STATUS: Handmatig");
        orderBijhouder = new OrderBijhouderPanel(this);
        orderInladen.setBounds(500,25,225,25);
        orderBijhouder.setBounds(500,60,475,400);
        orderAanmaken.setBounds(750,25,225,25);
        orderAanpassen.setBounds(500,470,225,25);
        orderVerwerken.setBounds(750,470,225,25);
        robotLocatie = new RobotLocatieGUI();
        robotLocatie.setBounds(25,25,450,550);
        add(orderInladen);
        add(orderAanmaken);
        add(status);
        add(orderBijhouder);
        add(orderAanpassen);
        orderAanpassen.setEnabled(false);
        add(orderVerwerken);
        orderVerwerken.setEnabled(false);
        add(robotLocatie);
        status.setBounds(500,525,200,25);
        status.setFont(new Font("Calibri", Font.BOLD, 20));
        orderInladen.addActionListener(this);
        orderAanpassen.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(orderInladen)){
            OrderIDInvullenDialog order = new OrderIDInvullenDialog(this, true);
            loadedOrderID = order.getOrderID();
            if(loadedOrderID != -1){
                orderVerwerken.setEnabled(true);
                orderAanpassen.setEnabled(true);
            }
            orderLines = databaseManager.getOrderLines(loadedOrderID);
            if (orderLines.isEmpty()) {
                OrderLine or = new OrderLine();
                or.setOrderID(-1);
                orderLines.clear();
                orderLines.add(or);
            }
        }
        if(e.getSource().equals(orderAanpassen)){
            if(loadedOrderID != 0){
                new updateOrderDialog(this, true, loadedOrderID, orderLines, databaseManager);
                orderLines = databaseManager.getOrderLines(loadedOrderID);
            }
        }
        orderBijhouder.repaint();
    }

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }
}