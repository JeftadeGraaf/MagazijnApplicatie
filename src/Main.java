import com.fazecast.jSerialComm.SerialPort;
import database.DatabaseManager;
import database.OrderLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        DatabaseManager databaseManager = new DatabaseManager();
        SerialPort comPort = SerialPort.getCommPorts()[0];
        SerialManager serialManager = new SerialManager(comPort);
        comPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        comPort.openPort();
        comPort.addDataListener(serialManager);

        Thread.sleep(2000);
        serialManager.sendMessage("o0.0,1.5,2.4,0.0");
        System.out.println("Input order id:");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        String orderIdString = reader.readLine();

        int orderId = Integer.parseInt(orderIdString);
        ArrayList<OrderLine> orderLines = databaseManager.getOrderLines(orderId);

        for (OrderLine orderLine : orderLines) {
            System.out.printf("id: %s, item: %s\n", orderLine.getOrderID(), orderLine.getStockItemID());
        }


        databaseManager.closeConnection();
    }

}
