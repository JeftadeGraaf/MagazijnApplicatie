package groep4.MagazijnApplicatie.entity;

import java.util.ArrayList;

public class Box {
    private ArrayList<StockItem> productlist;

    private final int capacity;

    public Box(ArrayList<StockItem> productlist, int capacity){
        setProductlist(productlist);
        this.capacity = capacity;
    }

    public int getRemainingCapacity(){
        int totalWeight = 0;
        for(StockItem item : productlist){
            totalWeight += item.weight();
        }
        return capacity - totalWeight;
    }

    public void setProductlist(ArrayList<StockItem> productlist) {
        this.productlist = productlist;
    }

    public ArrayList<StockItem> getProductlist() {
        return productlist;
    }

    public void addItem(StockItem item) {
        if (item.weight() <= getRemainingCapacity()) {
            productlist.add(item);
        }
    }
}
