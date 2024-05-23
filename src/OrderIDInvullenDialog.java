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

    private int orderID = -1; // Initialize to a default invalid value
    private GUI gui;

    public OrderIDInvullenDialog(JFrame frame, boolean modaal, GUI gui){
        super(frame, modaal);
        this.gui = gui;
        setSize(310, 135);
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
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ongeldig order-ID opgegeven, numerieke waarde vereist", "Fout", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == annuleren){
            orderID = -1; // Use an invalid value to indicate cancellation
            dispose();
        }
    }

    public int getOrderID() {
        return orderID;
    }
}
