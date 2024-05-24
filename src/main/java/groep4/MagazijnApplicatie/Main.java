package groep4.MagazijnApplicatie;

import groep4.MagazijnApplicatie.database.DatabaseManager;

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