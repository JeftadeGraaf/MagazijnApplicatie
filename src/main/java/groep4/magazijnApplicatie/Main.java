package groep4.magazijnApplicatie;

import groep4.magazijnApplicatie.database.DatabaseManager;

import javax.swing.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            new GUI(databaseManager);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Geen verbinding met database mogelijk", "Fout", JOptionPane.ERROR_MESSAGE);
        }
    }
}