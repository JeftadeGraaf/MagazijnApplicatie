package entity;

public class OrderLine {

    private int orderID;
    private StockItem stockItem;

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }

    public int getOrderID() {
        return orderID;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

}
