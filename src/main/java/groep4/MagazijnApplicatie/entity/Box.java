package groep4.MagazijnApplicatie.entity;

import java.util.ArrayList;

public class Box {
    private ArrayList<StockItem> productlist;

    private int capacity;
    private int orderNumber;
    private int customerId;

    public Box(ArrayList<StockItem> productlist, int capacity){
        setCustomerId(customerId);
        setProductlist(productlist);
        setOrderNumber(orderNumber);
        this.capacity = capacity;
    }

    public int getRemainingCapacity(){
        int totalWeight = 0;
        for(StockItem item : productlist){
            totalWeight += item.getWeight();
        }
        return capacity - totalWeight;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setProductlist(ArrayList<StockItem> productlist) {
        this.productlist = productlist;
    }

    public ArrayList<StockItem> getProductlist() {
        return productlist;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void addItem(StockItem item) {
        if (item.getWeight() <= getRemainingCapacity()) {
            productlist.add(item);
        }
    }
}
