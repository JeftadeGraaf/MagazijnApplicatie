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
                int itemID = rs.getInt("StockItemID");
                int[] itemCoordinates = getItemRackLocation(itemID);
                StockItem stockItem = new StockItem(rs.getInt("StockItemID"), itemCoordinates[0], itemCoordinates[1]);
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM orderlines WHERE OrderID = ? AND StockItemID = ?");
            statement.setInt(1, orderId);
            statement.setInt(2, orderLineID);
            statement.executeUpdate();
            System.out.println("Removed " + orderId + orderLineID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProductToOrder(int orderID, int stockItemID){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO orderlines (OrderID, StockItemID, Description, PackageTypeID, Quantity, UnitPrice, TaxRate, PickedQuantity, PickingCompletedWhen, LastEditedBy, lastEditedWhen) VALUES(?, ?, \"test\", 7, 1, 1, 1, 1, now(), 4, now())");
            statement.setInt(1, orderID);
            statement.setInt(2, stockItemID);
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int[] getItemRackLocation(int itemID){
        int x = 0;
        int y = 0;
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM warehouse_rack\n" +
                    "WHERE itemID = ?;");
            statement.setInt(1, itemID);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                x = rs.getInt("locationX");
                y = rs.getInt("locationY");
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return new int[]{x, y};
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
