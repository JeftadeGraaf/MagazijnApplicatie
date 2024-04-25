import database.DatabaseManager;
import database.OrderLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        DatabaseManager databaseManager = new DatabaseManager();
        GUI gui = new GUI(databaseManager);
//        System.out.println("Input order id:");
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(System.in));
//
//        // Reading data using readLine
//        String orderIdString = reader.readLine();
//
//        int orderId = Integer.parseInt(orderIdString);
//        ArrayList<OrderLine> orderLines = databaseManager.getOrderLines(orderId);
//
//        for (OrderLine orderLine : orderLines) {
//            System.out.printf("id: %s, item: %s\n", orderLine.getOrderID(), orderLine.getStockItemID());
//        }

    }

}
