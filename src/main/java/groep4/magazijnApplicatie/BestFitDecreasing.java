package groep4.magazijnApplicatie;

import groep4.magazijnApplicatie.entity.Box;
import groep4.magazijnApplicatie.entity.StockItem;

import java.util.ArrayList;
import java.util.Comparator;

public class BestFitDecreasing {

    public static ArrayList<Box> calculateBPP(ArrayList<StockItem> productList, int capacity){

        // Sort items in decreasing order of their weights
        productList.sort(Comparator.comparingInt(StockItem::weight).reversed());

        ArrayList<Box> boxList = new ArrayList<>();

        for (StockItem item : productList) {
            // Find the best bin for this item
            Box bestBox = null;
            int minRemainingCapacity = Integer.MAX_VALUE;
            for (Box box : boxList) {
                int remainingCapacity = box.getRemainingCapacity();
                if (remainingCapacity >= item.weight() && remainingCapacity < minRemainingCapacity) {
                    bestBox = box;
                    minRemainingCapacity = remainingCapacity;
                }
            }

            // If no suitable bin found, create a new bin
            if (bestBox == null) {
                bestBox = new Box(new ArrayList<>(), capacity);
                boxList.add(bestBox);
            }

            // Add the item to the best bin
            bestBox.addItem(item);
        }
        return boxList;
    }
}
