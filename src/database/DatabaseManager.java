package database;

import entity.OrderLine;
import entity.StockItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class DatabaseManager {

    private String url = "jdbc:mysql://localhost/nerdygadgets";
    private String username = "root", password = "";

    private Connection connection;

    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(this.url, this.username, this.password);

    }

    public ArrayList<OrderLine> getOrderLines(int orderId) {
        try {
            // Language=MySQL
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `orderlines` where OrderID = ?");
            statement.setInt(1, orderId);
            ArrayList<OrderLine> orderlines = new ArrayList<>();
            ResultSet rs = statement.executeQuery();

            for (int i = 0; rs.next(); i++) {
                StockItem stockItem = new StockItem();
                stockItem.setX(getRandomNumberInRange(1, 5));
                stockItem.setY(getRandomNumberInRange(1, 5));
                stockItem.setStockItemID(rs.getInt("StockItemID"));
                OrderLine orderLine = new OrderLine();
                orderLine.setStockItem(stockItem);
                orderLine.setOrderID(rs.getInt("OrderID"));
                orderlines.add(orderLine);
            }

            return orderlines;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeOrderLine(int orderId, int orderLineID){
        try {
            // Language=MySQL
            PreparedStatement statement = connection.prepareStatement("DELETE FROM orderlines WHERE OrderID = ? AND OrderLineID = ?");
            statement.setInt(1, orderId);
            statement.setInt(2, orderLineID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

}
