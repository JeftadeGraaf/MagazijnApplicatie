package groep4.magazijnApplicatie.database;

import groep4.magazijnApplicatie.entity.OrderLine;
import groep4.magazijnApplicatie.entity.StockItem;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private final Connection connection;

    public DatabaseManager() throws SQLException {
        String url = "jdbc:mysql://localhost/nerdygadgets";
        String username = "root";
        String password = "";
        connection = DriverManager.getConnection(url, username, password);

    }

    public ArrayList<OrderLine> getOrderLines(int orderId) {
        try {
            // Language=MySQL
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `orderLines` where OrderID = ?");
            statement.setInt(1, orderId);
            ArrayList<OrderLine> orderLines = new ArrayList<>();
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int itemID = rs.getInt("StockItemID");
                int[] itemCoordinates = getItemRackLocation(itemID);
                StockItem stockItem = new StockItem(rs.getInt("StockItemID"), itemCoordinates[0], itemCoordinates[1], getItemWeight(itemID));
                OrderLine orderLine = new OrderLine(rs.getInt("OrderID"), stockItem);
                orderLines.add(orderLine);
            }

            return orderLines;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getItemWeight(int itemID){
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT `itemWeight` FROM stockitems WHERE StockItemID = ?");
            statement.setInt(1, itemID);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("itemWeight");
            } else {
                return -1;
            }
        } catch(SQLException e){
            throw  new RuntimeException(e);
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
                return "Error: geen productnaam gevonden";
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

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

    public String[] getPackageInfo(int orderId){
        String[] info = new String[5];
        try{
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT\s
                        c.CustomerID, c.CustomerName, CONCAT(c.DeliveryAddressLine1 ," ",  c.DeliveryAddressLine2) AS address, o.OrderDate, o.OrderID
                    FROM\s
                        orders o
                    JOIN\s
                        customers c
                    ON\s
                        o.CustomerID = c.CustomerID
                    WHERE\s
                        o.OrderID = ?;""");
            statement.setInt(1, orderId);

            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                info[0] = String.valueOf(rs.getInt("CustomerID"));
                info[1] = rs.getString("CustomerName");
                info[2] = rs.getString("address");
                info[3] = String.valueOf(rs.getDate("OrderDate"));
                info[4] = String.valueOf(rs.getInt("OrderID"));
                return info;
            }

        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return info;
    }

    public ArrayList<StockItem> retrieveWarehouseStock(){
        try {
            // Language=MySQL
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `warehouse_rack`");
            ResultSet rs = statement.executeQuery();
            ArrayList<StockItem> itemList = new ArrayList<>();
            while (rs.next()) {
                int itemID = rs.getInt("itemID");
                int xCoordinate = rs.getInt("locationX");
                int yCoordinate = rs.getInt("locationY");
                int itemWeight = getItemWeight(itemID);
                itemList.add(new StockItem(itemID, xCoordinate, yCoordinate, itemWeight));
            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRackCell(int itemID, int x, int y){
        try {
            // Language=MySQL
            PreparedStatement statement = connection.prepareStatement("UPDATE `warehouse_rack` SET `itemID` = ? WHERE locationX = ? AND locationY = ?");
            statement.setInt(1, itemID);
            statement.setInt(2, x);
            statement.setInt(3, y);
            statement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    public void removeItemFromStock(int itemID){
        try {
            // Language=MySQL
            PreparedStatement statement = connection.prepareStatement("UPDATE `warehouse_rack` SET `itemID` = null WHERE `itemID` = ?");
            statement.setInt(1, itemID);
            statement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    public void removeItemFromStock(int xCoord, int yCoord){
        try {
            // Language=MySQL
            PreparedStatement statement = connection.prepareStatement("UPDATE `warehouse_rack` SET `itemID` = null WHERE `locationX` = ? AND locationY = ?");
            statement.setInt(1, xCoord);
            statement.setInt(2, yCoord);
            statement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }
}
