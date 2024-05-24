package groep4.magazijnApplicatie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderIDInvullenDialog extends JDialog implements ActionListener {
    private final JButton okButton;
    private final JButton annuleren;
    private final JTextField OrderIDTekstveld;

    private int orderID = -1;

    public OrderIDInvullenDialog(JFrame frame, boolean modaal){
        super(frame,modaal);
        setSize(310,135);
        setResizable(false);
        setTitle("Order");
        setLayout(null);

        OrderIDTekstveld = new JTextField("", 8);
        JLabel labelOrderID = new JLabel("Vul in orderID:");
        annuleren = new JButton("Annuleren");
        okButton = new JButton("OK");

        add(labelOrderID);
        add(OrderIDTekstveld);
        add(annuleren);
        add(okButton);

        labelOrderID.setBounds(25, 20, 120, 25);
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
            orderID = -1;
            dispose();
        }
    }

    public int getOrderID() {
        return orderID;
    }
}
