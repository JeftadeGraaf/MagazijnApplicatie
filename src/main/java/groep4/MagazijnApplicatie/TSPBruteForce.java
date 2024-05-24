package groep4.MagazijnApplicatie;

import groep4.MagazijnApplicatie.entity.OrderLine;
import groep4.MagazijnApplicatie.entity.StockItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class TSPBruteForce {
    public TSPBruteForce() {
    }

    static double calculateDistance(StockItem c1, StockItem c2) {
        return Math.hypot(c1.x() - c2.x(), c1.y() - c2.y());
    }

    static double calculateTotalDistance(List<OrderLine> coords, List<Integer> route) {
        double totalDistance = 0.0;
        int size = route.size();

        for (int i = 0; i < size - 1; i++) {
            totalDistance += calculateDistance(coords.get(route.get(i)).getStockItem(), coords.get(route.get(i + 1)).getStockItem());
        }

        totalDistance += calculateDistance(coords.get(route.get(size - 1)).getStockItem(), coords.get(route.getFirst()).getStockItem());
        return totalDistance;
    }

    static Pair<Double, List<Integer>> tspBruteForce(List<OrderLine> orderLines) {
        int numProducts = orderLines.size();
        List<Integer> products = new ArrayList<>();

        for (int i = 0; i < numProducts; i++) {
            products.add(i);
        }

        double minDistance = Double.POSITIVE_INFINITY;
        List<Integer> bestRoute = new ArrayList<>(products);

        do {
            double distance = calculateTotalDistance(orderLines, products);
            if (distance < minDistance) {
                minDistance = distance;
                bestRoute = new ArrayList<>(products);
            }
        } while (nextPermutation(products.subList(1, products.size())));

        bestRoute.add(bestRoute.getFirst());  // Close the loop
        return new Pair<>(minDistance, bestRoute);
    }

    static boolean nextPermutation(List<Integer> array) {
        int i = array.size() - 1;
        while (i > 0 && array.get(i - 1) >= array.get(i)) {
            i--;
        }

        if (i <= 0) {
            return false;
        }

        int j = array.size() - 1;
        while (array.get(j) <= array.get(i - 1)) {
            j--;
        }

        Collections.swap(array, i - 1, j);

        for (j = array.size() - 1; i < j; i++, j--) {
            Collections.swap(array, i, j);
        }

        return true;
    }

    public static String getRoute(List<OrderLine> orderLines) {
        StockItem baseItem = new StockItem(-1, 0, 0, -1);
        OrderLine baseLine = new OrderLine();
        baseLine.setStockItem(baseItem);
        orderLines.addFirst(baseLine);
        Pair<Double, List<Integer>> result = tspBruteForce(orderLines);

        StringJoiner route = new StringJoiner(",", "o", "");
        for (int product : result.second()) {
            StockItem stockItem = orderLines.get(product).getStockItem();
            route.add(stockItem.x() + "." + stockItem.y());
        }
        orderLines.removeFirst();

        return route.toString();
    }

    record Pair<A, B>(A first, B second) {
    }
}
