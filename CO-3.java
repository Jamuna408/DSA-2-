import java.util.*;

public class RazorpayBuildGraph {

    enum Color {
        WHITE, GREY, BLACK
    }

    static Map<String, List<String>> graph = new TreeMap<>();
    static Map<String, Color> color = new HashMap<>();
    static Map<String, String> parent = new HashMap<>();

    static List<String> topoOrder = new ArrayList<>();
    static List<String> cycle = new ArrayList<>();

    static boolean hasCycle = false;

    public static void main(String[] args) {

        // Build dependency graph
        addEdge("admin-ui", "payments");
        addEdge("auth", "ledger");
        addEdge("fraud", "notify");
        addEdge("gateway", "admin-ui");
        addEdge("ledger", "fraud");
        addEdge("notify", "gateway");
        addEdge("payments", "auth");

        // Initialize all vertices
        for (String node : graph.keySet()) {
            color.put(node, Color.WHITE);
            parent.put(node, null);
        }

        // DFS in alphabetical order
        for (String node : graph.keySet()) {
            if (color.get(node) == Color.WHITE) {
                dfs(node);
                if (hasCycle)
                    break;
            }
        }

        if (hasCycle) {
            System.out.println("Cycle Detected!");
            System.out.print("Cycle: ");

            for (int i = 0; i < cycle.size(); i++) {
                System.out.print(cycle.get(i));
                if (i != cycle.size() - 1)
                    System.out.print(" -> ");
            }

            System.out.println();
            System.out.println("\nTopological Sort Aborted.");
            System.out.println("\nSuggested Fix:");
            System.out.println("Remove dependency: auth -> ledger");
            System.out.println("This breaks the cycle with minimal downstream impact.");
        } else {

            Collections.reverse(topoOrder);

            System.out.println("Topological Build Order:");

            for (String node : topoOrder)
                System.out.print(node + " ");

            System.out.println();
        }
    }

    static void dfs(String u) {

        if (hasCycle)
            return;

        color.put(u, Color.GREY);

        for (String v : graph.get(u)) {

            if (color.get(v) == Color.WHITE) {

                parent.put(v, u);
                dfs(v);

            } else if (color.get(v) == Color.GREY) {

                hasCycle = true;

                List<String> temp = new ArrayList<>();

                temp.add(v);

                String cur = u;

                while (!cur.equals(v)) {
                    temp.add(cur);
                    cur = parent.get(cur);
                }

                temp.add(v);

                Collections.reverse(temp);

                cycle = temp;

                return;
            }
        }

        color.put(u, Color.BLACK);
        topoOrder.add(u);
    }

    static void addEdge(String from, String to) {

        graph.putIfAbsent(from, new ArrayList<>());
        graph.putIfAbsent(to, new ArrayList<>());

        graph.get(from).add(to);

        Collections.sort(graph.get(from));
    }
}