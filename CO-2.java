public class HDFCFenwickTree {

    static class FenwickTree {
        int[] bit;
        int n;

        // Constructor
        FenwickTree(int size) {
            n = size;
            bit = new int[n + 1];
        }

        // Point Update
        void update(int index, int value) {
            while (index <= n) {
                bit[index] += value;
                index += index & (-index);
            }
        }

        // Prefix Sum (Correct)
        int prefixSum(int index) {
            int sum = 0;
            while (index > 0) {
                sum += bit[index];
                index -= index & (-index);
            }
            return sum;
        }

        // Prefix Sum (Buggy Version)
        int buggyPrefixSum(int index) {
            int sum = 0;
            while (index > 0) {
                sum += bit[index];
                index -= (index & (-index)) + 1; // Incorrect
            }
            return sum;
        }

        // Range Sum
        int rangeSum(int left, int right) {
            return prefixSum(right) - prefixSum(left - 1);
        }

        // Print BIT
        void printBIT() {
            System.out.println("Fenwick Tree Array:");
            for (int i = 1; i <= n; i++) {
                System.out.println("bit[" + i + "] = " + bit[i]);
            }
        }
    }

    public static void main(String[] args) {

        // Daily spending (Jan 1 - Jan 12)
        int[] spend = {
                1200, 950, 850, 1100,
                1500, 1000, 900, 1250,
                1300, 950, 800, 1200
        };

        int n = spend.length;

        FenwickTree ft = new FenwickTree(n);

        // Build BIT
        for (int i = 0; i < n; i++) {
            ft.update(i + 1, spend[i]);
        }

        ft.printBIT();

        System.out.println();

        // Verify important BIT cells
        System.out.println("Verification of Key BIT Cells");
        System.out.println("bit[4]  = " + ft.bit[4]);
        System.out.println("bit[8]  = " + ft.bit[8]);
        System.out.println("bit[12] = " + ft.bit[12]);

        System.out.println();

        // Prefix sums
        int prefix12 = ft.prefixSum(12);
        int prefix4 = ft.prefixSum(4);

        System.out.println("PrefixSum(12) = " + prefix12);
        System.out.println("PrefixSum(4)  = " + prefix4);

        System.out.println();

        // Range Query (Jan 5 - Jan 12)
        int answer = ft.rangeSum(5, 12);

        System.out.println("Monthly Spend (Jan 5 - Jan 12) = ₹" + answer);

        System.out.println();

        // Demonstrate Bug
        System.out.println("Correct PrefixSum(12) = " + ft.prefixSum(12));
        System.out.println("Buggy PrefixSum(12)   = " + ft.buggyPrefixSum(12));

        System.out.println();
        System.out.println("Bug Fix:");
        System.out.println("Wrong : index -= (index & -index) + 1;");
        System.out.println("Correct: index -= (index & -index);");

        System.out.println();

        // Comparison
        System.out.println("Fenwick Tree vs Segment Tree");
        System.out.println("--------------------------------");
        System.out.println("Fenwick Tree:");
        System.out.println("Memory : O(n)");
        System.out.println("Point Update : O(log n)");
        System.out.println("Range Sum Query : O(log n)");
        System.out.println("Simple implementation and cache friendly.");

        System.out.println();

        System.out.println("Segment Tree:");
        System.out.println("Memory : O(4n)");
        System.out.println("Point Update : O(log n)");
        System.out.println("Range Query : O(log n)");
        System.out.println("Supports Range Updates, Min/Max Queries and other operations.");
    }
}