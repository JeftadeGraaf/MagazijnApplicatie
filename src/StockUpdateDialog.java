import database.DatabaseManager;
import entity.StockItem;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StockUpdateDialog extends JDialog implements ActionListener {
    private DatabaseManager databaseManager;

    private ArrayList<JButton> buttonArray = new ArrayList<>();
    private ArrayList<StockItem> stockList = new ArrayList<>();
    private ArrayList<StockItem> removeableItems = new ArrayList<>();
    private JLabel removeLabel = new JLabel("Producten verwijderen uit stelling");
    private JLabel addLabel = new JLabel("Vak aanvullen/wijzigen");
    private JLabel addTextLabel = new JLabel("Product ID:");
    private JTextField addText = new JTextField("");
    private JLabel addXLabel = new JLabel("X-coördinaat:");
    private JTextField addXText = new JTextField("");
    private JLabel addYLabel = new JLabel("Y-coördinaat:");
    private JTextField addYText = new JTextField("");

    private JLabel errorText = new JLabel("");
    private JButton addButton = new JButton("Aanpassen");
    private JPanel scrollFrame = new JPanel();
    private JScrollPane scrollPane;

    private JButton cancelButton = new JButton("Sluiten");

    public StockUpdateDialog(JFrame frame, boolean modal, DatabaseManager databaseManager) {
        super(frame, modal);
        this.databaseManager = databaseManager;
        setTitle("Voorraadbeheer");
        setSize(1250, 475);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        removeLabel.setBounds(25, 15, 200, 25);
        add(removeLabel);

        scrollFrame.setLayout(new GridBagLayout());

        refreshStockItems();

        scrollPane = new JScrollPane(scrollFrame);
        scrollPane.setBounds(25, 50, 950, 350);
        add(scrollPane);
        addLabel.setBounds(1000, 15, 200, 25);
        add(addLabel);
        addTextLabel.setBounds(1000, 50, 90, 25);
        add(addTextLabel);
        addText.setBounds(1085, 50, 115, 25);
        add(addText);
        addButton.setBounds(1000, 185, 200, 25);
        add(addButton);
        addXLabel.setBounds(1000, 100, 90, 25);
        add(addXLabel);
        addXText.setBounds(1085, 100, 115, 25);
        add(addXText);
        addYLabel.setBounds(1000, 150, 125, 25);
        add(addYLabel);
        addYText.setBounds(1085, 150, 115, 25);
        add(addYText);
        errorText.setBounds(1000, 225, 250, 25);
        errorText.setForeground(Color.red);
        add(errorText);
        cancelButton.setBounds(1100, 375, 100, 25);
        add(cancelButton);

        cancelButton.addActionListener(this);
        addButton.addActionListener(this);
        setVisible(true);
    }

    private void refreshStockItems() {
        scrollFrame.removeAll();
        buttonArray.clear();
        removeableItems.clear();
        stockList = databaseManager.retrieveWarehouseStock();
        int rowHeight = 0;
        GridBagConstraints gbc = new GridBagConstraints();
        for (StockItem item : stockList) {
            gbc.ipady = 20;
            gbc.gridx = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.gridy = rowHeight;
            gbc.gridwidth = 8;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0, 5, 0, 5);

            String itemName = databaseManager.getProductName(item.getStockItemID());
            JLabel label;
            if(item.getStockItemID() == 0){
                label = new JLabel("Vak (" + item.getX() + "," + item.getY() + ") | LEEG");
            } else {
                label = new JLabel("Vak (" + item.getX() + "," + item.getY() + ") - Product " + item.getStockItemID() + " | " + itemName);
                removeableItems.add(item);
            }
            label.setHorizontalAlignment(SwingConstants.LEFT);
            scrollFrame.add(label, gbc);
            gbc.anchor = GridBagConstraints.EAST;
            if(item.getStockItemID() != 0){
                JButton button = new JButton("Verwijder");
                button.setHorizontalAlignment(SwingConstants.RIGHT);
                buttonArray.add(button);
                gbc.gridx = 8;
                gbc.gridwidth = 1;
                scrollFrame.add(button, gbc);
                button.addActionListener(this);
            }
            rowHeight++;
        }
        scrollFrame.revalidate();
        scrollFrame.repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cancelButton)) {
            dispose();
        } else if (e.getSource().equals(addButton)) {
            try {
                int itemID = Integer.parseInt(addText.getText());
                int xCoord = Integer.parseInt(addXText.getText());
                int yCoord = Integer.parseInt(addYText.getText());
                if(xCoord > 0 && xCoord <= 5 && yCoord > 0 && yCoord <= 5){
                    databaseManager.updateRackCell(itemID, xCoord, yCoord);
                    stockList = databaseManager.retrieveWarehouseStock();
                    errorText.setForeground(new Color(42,140,0));
                    errorText.setText("Succesvol aangepast!");
                    addText.setText("");
                    addXText.setText("");
                    addYText.setText("");
                    refreshStockItems();
                } else {
                    errorText.setForeground(Color.red);
                    errorText.setText("Coördinaten liggen niet tussen 0 en 5!");
                }
            } catch (NumberFormatException numberFormatException) {
                errorText.setForeground(Color.red);
                errorText.setText("Ongeldige ingave, probeer opnieuw");
            }
        } else {
            for (int i = 0; i < buttonArray.size(); i++) {
                if (e.getSource().equals(buttonArray.get(i))) {
                    StockItem item = removeableItems.get(i);
                    databaseManager.removeItemFromStock(item.getStockItemID());
                    refreshStockItems();
                    break;
                }
            }
        }
    }
}