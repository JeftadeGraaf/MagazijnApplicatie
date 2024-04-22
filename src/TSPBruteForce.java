//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import entity.OrderLine;
import entity.StockItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TSPBruteForce {
    public TSPBruteForce() {
    }

    static double calculateDistance(StockItem c1, StockItem c2) {
        return Math.sqrt(Math.pow((double)(c1.getX() - c2.getX()), 2.0) + Math.pow((double)(c1.getY() - c2.getY()), 2.0));
    }

    static double calculateTotalDistance(ArrayList<StockItem> coords, ArrayList<Integer> route) {
        double totalDistance = 0.0;

        for(int i = 0; i < route.size() - 1; ++i) {
            totalDistance += calculateDistance((StockItem) coords.get((Integer)route.get(i)), (StockItem) coords.get((Integer)route.get(i + 1)));
        }

        totalDistance += calculateDistance((StockItem) coords.get((Integer)route.get(route.size() - 1)), (StockItem) coords.get((Integer)route.get(0)));
        return totalDistance;
    }

    static Pair<Double, ArrayList<Integer>> tspBruteForce(ArrayList<StockItem> coords) {
        int numProducts = coords.size();
        ArrayList<Integer> products = new ArrayList();

        for(int i = 0; i < numProducts; ++i) {
            products.add(i);
        }

        double minDistance = Double.POSITIVE_INFINITY;
        ArrayList<Integer> bestRoute = new ArrayList();

        do {
            double distance = calculateTotalDistance(coords, products);
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
        ArrayList<StockItem> coordinates = new ArrayList();
        StockItem base = new StockItem();
        base.setX(0);
        base.setY(0);
        coordinates.add(base);
        for (OrderLine orderLine : orderLines) {
            coordinates.add(orderLine.getStockItem());
            System.out.println(orderLine.getStockItem().getX() + ", " + orderLine.getStockItem().getY());
        }
        Pair<Double, ArrayList<Integer>> result = tspBruteForce(coordinates);
        System.out.println("Shortest Distance: " + String.valueOf(result.getFirst()));
        System.out.print("Best Path: ");
        Iterator var3 = ((ArrayList)result.getSecond()).iterator();

        String route = "optimal route:";

        while(var3.hasNext()) {
            int product = (Integer)var3.next();
            route = route + product + " ";
//            System.out.print("" + product + " ");
        }

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
