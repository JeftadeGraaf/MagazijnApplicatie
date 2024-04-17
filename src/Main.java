import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        DatabaseManager databaseManager = new DatabaseManager();
        System.out.println("Input order id:");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        String orderIdString = reader.readLine();

        int orderId = Integer.parseInt(orderIdString);
        databaseManager.getOrderLines(orderId);
    }

}
