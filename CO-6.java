import java.util.*;

public class FloydWarshallDabbawalla {

    static final int INF = 999999;

    public static void main(String[] args) {

        String[] hubs = {
                "CHC", "DDR", "KRL", "BYC", "CST", "AND"
        };

        int V = hubs.length;

        int[][] dist = {
                {0, 4, 2, INF, INF, INF},
                {4, 0, 1, 5, INF, INF},
                {2, 1, 0, 8, 10, INF},
                {INF, 5, 8, 0, 2, 6},
                {INF, INF, 10, 2, 0, 3},
                {INF, INF, INF, 6, 3, 0}
        };

        int[][] next = new int[V][V];

        // Initialize next matrix
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (dist[i][j] != INF && i != j)
                    next[i][j] = j;
                else
                    next[i][j] = -1;
            }
        }

        // Floyd-Warshall Algorithm
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {

                    if (dist[i][k] == INF || dist[k][j] == INF)
                        continue;

                    if (dist[i][k] + dist[k][j] < dist[i][j]) {

                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];

                    }
                }
            }
        }

        // Print shortest distance matrix
        System.out.println("Shortest Distance Matrix:\n");

        System.out.print("\t");
        for (String hub : hubs)
            System.out.print(hub + "\t");

        System.out.println();

        for (int i = 0; i < V; i++) {

            System.out.print(hubs[i] + "\t");

            for (int j = 0; j < V; j++) {

                if (dist[i][j] == INF)
                    System.out.print("INF\t");
                else
                    System.out.print(dist[i][j] + "\t");
            }

            System.out.println();
        }

        // Example Path
        System.out.println("\nShortest Path from CHC to AND:");

        printPath(0, 5, next, hubs);

        System.out.println("\nTotal Travel Time = " + dist[0][5] + " minutes");
    }

    static void printPath(int u, int v, int[][] next, String[] hubs) {

        if (next[u][v] == -1) {
            System.out.println("No Path Exists");
            return;
        }

        System.out.print(hubs[u]);

        while (u != v) {
            u = next[u][v];
            System.out.print(" -> " + hubs[u]);
        }

        System.out.println();
    }
}