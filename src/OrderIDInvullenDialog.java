import database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderIDInvullenDialog extends JDialog implements ActionListener {
    private JButton okButton;
    private JButton annuleren;
    private JLabel LabelOrderID;
    private JTextField OrderIDTekstveld;

    private int orderID;

    public OrderIDInvullenDialog(JFrame frame, boolean modaal){
        super(frame,modaal);
        setSize(310,135);
        setResizable(false);
        setTitle("Order");
        setLayout(null);
        OrderIDTekstveld = new JTextField("", 8);
        LabelOrderID = new JLabel("Vul in orderID:");
        annuleren = new JButton("Annuleren");
        okButton = new JButton("OK");
        add(LabelOrderID);
        add(OrderIDTekstveld);
        add(annuleren);
        add(okButton);
        LabelOrderID.setBounds(25, 20, 120, 25);
        OrderIDTekstveld.setBounds(155, 20, 120, 25);
        annuleren.setBounds(25, 60, 120, 25);
        okButton.setBounds(155, 60, 120, 25);
        okButton.addActionListener(this);
        annuleren.addActionListener(this);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            try {
                orderID = Integer.parseInt(OrderIDTekstveld.getText());
            } catch (NumberFormatException ex) {
                orderID = -1;
            }
        }
        dispose();
    }

    public int getOrderID() {
        return orderID;
    }
}
