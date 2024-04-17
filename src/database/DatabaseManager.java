package database;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private String url = "jdbc:mysql://localhost/nerdygadgets";
    private String username = "root", password = "";

    private Connection connection;

    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(this.url, this.username, this.password);

    }

    public ArrayList<OrderLine> getOrderLines(int orderId) throws SQLException {
        // Language=MySQL
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `orderlines` where OrderID = ?");
        statement.setInt(1, orderId);
        ArrayList<OrderLine> orderlines = new ArrayList<>();
        ResultSet rs = statement.executeQuery();

        for (int i = 0; rs.next(); i++) {
            OrderLine orderLine = new OrderLine();
            orderLine.setOrderID(rs.getInt("OrderID"));
            orderLine.setStockItemID(rs.getInt("StockItemID"));
            orderlines.add(orderLine);
        }
        statement.close();

        return orderlines;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

}
