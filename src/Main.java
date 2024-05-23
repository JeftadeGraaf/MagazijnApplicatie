import database.DatabaseManager;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        DatabaseManager databaseManager = new DatabaseManager();
        GUI gui = new GUI(databaseManager);
    }
}