package groep4.MagazijnApplicatie;

import groep4.MagazijnApplicatie.entity.OrderLine;
import groep4.MagazijnApplicatie.entity.StockItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TSPBruteForce {
    public TSPBruteForce() {
    }

    static double calculateDistance(StockItem c1, StockItem c2) {
        return Math.sqrt(Math.pow((double)(c1.getX() - c2.getX()), 2.0) + Math.pow((double)(c1.getY() - c2.getY()), 2.0));
    }

    static double calculateTotalDistance(ArrayList<OrderLine> coords, ArrayList<Integer> route) {
        double totalDistance = 0.0;

        for(int i = 0; i < route.size() - 1; ++i) {
            totalDistance += calculateDistance((StockItem) coords.get((Integer)route.get(i)).getStockItem(), (StockItem) coords.get((Integer)route.get(i + 1)).getStockItem());
        }

        totalDistance += calculateDistance((StockItem) coords.get((Integer)route.get(route.size() - 1)).getStockItem(), (StockItem) coords.get((Integer)route.get(0)).getStockItem());
        return totalDistance;
    }

    static Pair<Double, ArrayList<Integer>> tspBruteForce(ArrayList<OrderLine> orderLines) {
        int numProducts = orderLines.size();
        ArrayList<Integer> products = new ArrayList();

        for(int i = 0; i < numProducts; ++i) {
            products.add(i);
        }

        double minDistance = Double.POSITIVE_INFINITY;
        ArrayList<Integer> bestRoute = new ArrayList();

        do {
            double distance = calculateTotalDistance(orderLines, products);
            if (distance < minDistance) {
                minDistance = distance;
                bestRoute = new ArrayList(products);
            }
        } while(nextPermutation(products.subList(1, products.size())));

        bestRoute.add((Integer)bestRoute.get(0));
        return new Pair(minDistance, bestRoute);
    }

    static boolean nextPermutation(List<Integer> array) {
        int i;
        for(i = array.size() - 1; i > 0 && (Integer)array.get(i - 1) >= (Integer)array.get(i); --i) {
        }

        if (i <= 0) {
            return false;
        } else {
            int j;
            for(j = array.size() - 1; (Integer)array.get(j) <= (Integer)array.get(i - 1); --j) {
            }

            int temp = (Integer)array.get(i - 1);
            array.set(i - 1, (Integer)array.get(j));
            array.set(j, temp);

            for(j = array.size() - 1; i < j; --j) {
                temp = (Integer)array.get(i);
                array.set(i, (Integer)array.get(j));
                array.set(j, temp);
                ++i;
            }

            return true;
        }
    }

    public static String getRoute(ArrayList<OrderLine> orderLines) {
        StockItem baseItem = new StockItem(-1, 0, 0, -1);
        baseItem.setX(0);
        baseItem.setY(0);
        OrderLine baseLine = new OrderLine();
        baseLine.setStockItem(baseItem);
        orderLines.addFirst(baseLine);
        Pair<Double, ArrayList<Integer>> result = tspBruteForce(orderLines);
        Iterator var3 = ((ArrayList)result.getSecond()).iterator();

        String route = "o";

        while(var3.hasNext()) {
            int product = (Integer)var3.next();
            StockItem stockItem = orderLines.get(product).getStockItem();
            if (var3.hasNext()) {
                route = String.format("%s%s.%s,", route, stockItem.getX(), stockItem.getY()) ;
            } else {
                route = String.format("%s%s.%s", route, stockItem.getX(), stockItem.getY());
            }
        }
        orderLines.removeFirst();

        return route;
    }

    static class Pair<A, B> {
        private final A first;
        private final B second;

        public Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }

        public A getFirst() {
            return this.first;
        }

        public B getSecond() {
            return this.second;
        }
    }
}
