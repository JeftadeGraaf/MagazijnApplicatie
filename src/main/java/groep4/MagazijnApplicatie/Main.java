package groep4.MagazijnApplicatie;

import groep4.MagazijnApplicatie.database.DatabaseManager;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        new GUI(databaseManager);
    }
}