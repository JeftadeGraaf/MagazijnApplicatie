package entity;

import java.net.SocketTimeoutException;

public class StockItem {

    private int stockItemID;
    private int x, y;
    private int weight;

    public StockItem(int stockItemID, int x, int y, int weight){
        this.stockItemID = stockItemID;
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    public int getStockItemID() {
        return stockItemID;
    }

    public void setStockItemID(int stockItemID) {
        this.stockItemID = stockItemID;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWeight(){
        return weight;
    }
}
