package database;

public class OrderLine {

    private int OrderID;
    private int StockItemID;

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public void setStockItemID(int stockItemID) {
        StockItemID = stockItemID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public int getStockItemID() {
        return StockItemID;
    }

}
