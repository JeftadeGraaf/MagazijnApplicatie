package database;

import com.mysql.cj.protocol.Resultset;
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

    public String getProductName(int productID){
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT StockItemName FROM stockitems WHERE StockItemID = ?;");
            statement.setInt(1, productID);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getString("StockItemName");
            } else {
                return "Leeg";
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

    }


    public void closeConnection() throws SQLException {
        connection.close();
    }

    public int addNewOrder(int CustomerID){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO orders (CustomerID, SalespersonPersonID, PickedByPersonID, ContactPersonID, BackorderOrderID, OrderDate, ExpectedDeliveryDate, CustomerPurchaseOrderNumber, IsUndersupplyBackordered, PickingCompletedWhen, LastEditedBy, LastEditedWhen) VALUES(?, 2, 3, 3032, 45, now(), now(), 12126, 1, now(), 7, now())", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, CustomerID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0){
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if(generatedKeys.next()){
                    return generatedKeys.getInt(1);
                }
            }
            return 0;

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<StockItem> retrieveWarehouseStock(){
        try {
            // Language=MySQL
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `warehouse_rack`");
            ResultSet rs = statement.executeQuery();
            ArrayList<StockItem> itemList = new ArrayList<>();
            for (int i = 0; rs.next(); i++) {
                int itemID = rs.getInt("itemID");
                int xCoord = rs.getInt("locationX");
                int yCoord = rs.getInt("locationY");
                itemList.add(new StockItem(itemID, xCoord, yCoord));
            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateRackCell(int itemID, int x, int y){
        try {
            // Language=MySQL
            PreparedStatement statement = connection.prepareStatement("UPDATE `warehouse_rack` SET `itemID` = ? WHERE locationX = ? AND locationY = ?");
            statement.setInt(1, itemID);
            statement.setInt(2, x);
            statement.setInt(3, y);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean removeItemFromStock(int itemID){
        try {
            // Language=MySQL
            PreparedStatement statement = connection.prepareStatement("UPDATE `warehouse_rack` SET `itemID` = null WHERE `itemID` = ?");
            statement.setInt(1, itemID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}