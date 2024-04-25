import database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderIDInvullenDialog extends JDialog implements ActionListener {
    private JButton okButton;
    private JButton annuleren;
    private JLabel LabelOrderID;
    private JLabel placeholder2;
    private JTextField OrderIDTekstveld;

    private int orderID;

    //private JTextField placeholder2a;
    public OrderIDInvullenDialog(JFrame frame, boolean modaal){
        super(frame,modaal);
        setSize(300,200);
        setTitle("Order");
        setLayout(new FlowLayout(10,30,30));
        OrderIDTekstveld = new JTextField();
        LabelOrderID = new JLabel("Vul in orderID:");
        //placeholder2 = new JLabel("vul in placeholder2");
        //placeholder2a = new JTextField();
        OrderIDTekstveld.setColumns(8);
        //placeholder2a.setColumns(2);
        annuleren = new JButton("Annuleren");
        okButton = new JButton("OK");
        add(LabelOrderID);
        add(OrderIDTekstveld);
        //add(placeholder2);
        //add(placeholder2a);
        add(annuleren);
        add(okButton);
        okButton.addActionListener(this);
        annuleren.addActionListener(this);
        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            orderID = Integer.parseInt(OrderIDTekstveld.getText());
        }
        dispose();
    }

    public int getOrderID() {
        return orderID;
    }
}
