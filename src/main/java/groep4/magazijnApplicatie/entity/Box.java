package groep4.magazijnApplicatie.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Box(List<StockItem> productlist, int capacity) {

    public Box(List<StockItem> productlist, int capacity) {
        this.productlist = new ArrayList<>(productlist); // Ensure the list is mutable and local
        this.capacity = capacity;
    }

    public List<StockItem> getProductlist() {
        return Collections.unmodifiableList(productlist);
    }

    public int getRemainingCapacity() {
        int totalWeight = productlist.stream()
                .mapToInt(StockItem::weight)
                .sum();
        return capacity - totalWeight;
    }

    public void addItem(StockItem item) {
        if (item.weight() <= getRemainingCapacity()) {
            productlist.add(item);
        }
    }
}
