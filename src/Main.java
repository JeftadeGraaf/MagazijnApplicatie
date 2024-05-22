import database.DatabaseManager;
import entity.StockItem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        DatabaseManager databaseManager = new DatabaseManager();
        GUI gui = new GUI(databaseManager);
    }
}