import java.sql.*;

public class DatabaseManager {

    private String url = "jdbc:mysql://localhost/nerdygadgets";
    private String username = "root", password = "";

    private Connection connection;

    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(this.url, this.username, this.password);

    }

    public void getOrderLines(int orderId) throws SQLException {
        // Language=MySQL
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `orderlines` where OrderID = ?");
        statement.setInt(1, orderId);

        ResultSet rs = statement.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("OrderID");
            int itemId = rs.getInt("StockItemID");

            System.out.printf("id: %s, item: %s\n", id, itemId);
        }
        statement.close();
    }

}
