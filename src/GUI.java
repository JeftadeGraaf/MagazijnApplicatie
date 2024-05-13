import database.DatabaseManager;
import entity.OrderLine;

import javax.swing.*;
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
        setSize(854, 580);
        setTitle("HMI Applicatie");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        orderAanpassen = new JButton("Order aanpassen");
        orderAanmaken = new JButton("Order aannmaken");
        orderInladen= new JButton("Order inladen");
        orderVerwerken = new JButton("Order verwerken");
        testerVoorStatusVerwijderLater = new JLabel("handmatig");
        status = new JLabel("Status:");
        orderBijhouder = new OrderBijhouderPanel(this);
        robotLocatie = new RobotLocatieGUI();
        orderInladen.setBounds(400,25,135,25);
        orderBijhouder.setBounds(400,60,400,300);
        orderAanmaken.setBounds(550,25,135,25);
        orderAanpassen.setBounds(400,370,135,25);
        orderVerwerken.setBounds(550,370,135,25);
        robotLocatie.setBounds(10,20,380,450);
        add(orderInladen);
        add(orderAanmaken);
        add(status);
        add(orderBijhouder);
        add(orderAanpassen);
        orderAanpassen.setEnabled(false);
        add(orderVerwerken);
        orderVerwerken.setEnabled(false);
        add(testerVoorStatusVerwijderLater);
        add(robotLocatie);
        status.setBounds(700,-13,100,100);
        testerVoorStatusVerwijderLater.setBounds(742,-13,100,100);
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