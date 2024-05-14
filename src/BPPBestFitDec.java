
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BPPBestFitDec {
    static int bestFit(Integer[] weight, int n, int c) { // c= capacity of bin, n= numbers of items
        int res = 0;
        int[] bin_rem = new int[n]; // keeps track of restcapacity of the bins
        List<List<Integer>> bins = new ArrayList<>();

        for (int i = 0; i < n; i++){
            int minimumCapacity = c + 1;
            int binIndex = 0; // index for best container

            for (int j = 0; j < res; j++){
                if (bin_rem[j] >= weight[i] && bin_rem[j] - weight[i] < minimumCapacity){
                    binIndex = j;
                    minimumCapacity = bin_rem[j] - weight[i];
                }
            }
            if (minimumCapacity == c + 1) {
                List<Integer> newBin = new ArrayList<>();
                newBin.add(weight[i]);
                bins.add(newBin);
                bin_rem[res] = c - weight[i];
                res++;
            }
            else {
                bin_rem[binIndex] -= weight[i];
                bins.get(binIndex).add(weight[i]);

            }
        }
        System.out.println("Weights in each bin: ");
        for(int i =0; i < bins.size();i++){
            System.out.print("Bin " + (i + 1) + ": ");
            for(int j =0; j < bins.get(i).size(); j++){
                System.out.println(bins.get(i).get(j) + " ");
            }
        }
        return res;
    }
    static int bestFitDec(Integer[] weight, int n, int c) {
        Arrays.sort(weight, Collections.reverseOrder());
        return bestFit(weight, n, c);
    }

    public static void main(String[] args) {
        Integer[] weight = { 1,2,3};
        int c = 10;
        int n = weight.length;
        System.out.print("Number of bins required in Best Fit Decreasing : " + bestFitDec(weight, n, c));
    }
}