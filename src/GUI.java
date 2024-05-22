import database.DatabaseManager;
import entity.OrderLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JButton orderInladen;
    private JButton orderAanmaken;
    private JButton orderAanpassen;
    private JButton orderVerwerken;

    private JButton voorraadBeheer;
    private JLabel status;


    private int loadedOrderID = 0;
    private int robotXCoordinate = 0;
    private int robotYCoordinate = 0;
    private Color statusColor = Color.red;


    OrderBijhouderPanel orderBijhouder;
    RobotLocatieGUI robotLocatie;
    private DatabaseManager databaseManager;
    private SerialManager serialManager;
    private ArrayList<OrderLine> orderLines = new ArrayList<>();

    public GUI(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        //serialManager = new SerialManager(this);
        setLayout(null);
        setSize(1000, 650);
        setTitle("HMI Applicatie");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        orderAanpassen = new JButton("Order aanpassen");
        orderAanmaken = new JButton("Order aanmaken");
        orderInladen= new JButton("Order inladen");
        orderVerwerken = new JButton("Order verwerken");
        voorraadBeheer = new JButton("Voorraadbeheer");
        status = new JLabel("STATUS: Handmatig");
        orderBijhouder = new OrderBijhouderPanel(this, databaseManager);

        orderInladen.setBounds(500,25,225,25);
        orderBijhouder.setBounds(500,60,475,400);
        orderAanmaken.setBounds(750,25,225,25);
        orderAanpassen.setBounds(500,470,225,25);
        orderVerwerken.setBounds(750,470,225,25);
        voorraadBeheer.setBounds(750, 525, 225, 25);
        robotLocatie = new RobotLocatieGUI(this);
        robotLocatie.setBounds(25,25,450,550);
        add(orderInladen);
        add(orderAanmaken);
        add(status);
        add(orderBijhouder);
        add(orderAanpassen);
        add(voorraadBeheer);
        orderAanpassen.setEnabled(false);
        add(orderVerwerken);
        orderVerwerken.setEnabled(false);
        add(robotLocatie);
        status.setBounds(500,525,200,25);
        status.setFont(new Font("Calibri", Font.BOLD, 20));
        orderInladen.addActionListener(this::clickedOrderLoad);
        orderAanpassen.addActionListener(this::clickedOrderChange);
        orderAanmaken.addActionListener(this::clickedOrderAdded);
        voorraadBeheer.addActionListener(this::clickedManageStock);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }


    public void clickedOrderLoad(ActionEvent e){
        OrderIDInvullenDialog order = new OrderIDInvullenDialog(this, true);
        loadedOrderID = order.getOrderID();
        orderLines = databaseManager.getOrderLines(loadedOrderID);
        if (orderLines.isEmpty()) {
            OrderLine or = new OrderLine();
            or.setOrderID(-1);
            orderLines.clear();
            orderLines.add(or);
            orderAanpassen.setEnabled(false);
            orderVerwerken.setEnabled(false);
        } else {
            orderVerwerken.setEnabled(true);
            orderAanpassen.setEnabled(true);
        }
        robotLocatie.repaint();
        orderBijhouder.repaint();
    }

    public void clickedOrderChange(ActionEvent e){
        if(loadedOrderID != 0){
            orderLines = databaseManager.getOrderLines(loadedOrderID);
            new updateOrderDialog(this, true, loadedOrderID, orderLines, databaseManager);
        }
        robotLocatie.repaint();
        orderBijhouder.repaint();
    }

    public void clickedOrderAdded(ActionEvent e){
        OrderAddDialog addDialog = new OrderAddDialog(this, true, databaseManager);
        loadedOrderID = addDialog.getOrderId();
        orderLines = databaseManager.getOrderLines(loadedOrderID);
        robotLocatie.repaint();
        orderBijhouder.repaint();
    }

    public void clickedManageStock(ActionEvent e){
        StockUpdateDialog stockUpdateDialog = new StockUpdateDialog(this, true, databaseManager);
        orderLines = databaseManager.getOrderLines(loadedOrderID);
        robotLocatie.repaint();
        orderBijhouder.repaint();
    }

    public void changeStatus(String newStatus, Color color){
        status.setText("Status: " + newStatus);
        statusColor = color;
        orderBijhouder.repaint();
    }

    public void setRobotXCoordinate(int xCoordinate){
        robotXCoordinate = xCoordinate;
        robotLocatie.repaint();
    }

    public void setRobotYCoordinate(int yCoordinate){
        robotYCoordinate = yCoordinate;
        robotLocatie.repaint();
    }

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }
}