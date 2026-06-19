public class LongestCommonSubsequence {

    public static void main(String[] args) {

        String A = "GENETIC";
        String B = "GENOMIC";

        int m = A.length();
        int n = B.length();

        // DP table
        int[][] dp = new int[m + 1][n + 1];

        // Fill DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Print DP Table
        System.out.println("Dynamic Programming Table:");

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }

        // LCS Length
        System.out.println("\nLength of LCS = " + dp[m][n]);

        // Backtracking to find LCS
        StringBuilder lcs = new StringBuilder();

        int i = m;
        int j = n;

        while (i > 0 && j > 0) {

            if (A.charAt(i - 1) == B.charAt(j - 1)) {
                lcs.append(A.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] >= dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        System.out.println("Longest Common Subsequence = " + lcs.reverse().toString());

        // Complexity
        System.out.println("\nTime Complexity: O(m × n)");
        System.out.println("Space Complexity: O(m × n)");
    }
}