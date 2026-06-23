import java.util.*;

public class DijkstraVisualizer {

    static class Edge {
        char target;
        int weight;

        Edge(char target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    static class NodeState {
        char node;
        boolean visited;
        int distance;
        Character predecessor;

        NodeState(char node) {
            this.node = node;
            this.visited = false;
            this.distance = Integer.MAX_VALUE;
            this.predecessor = null;
        }
    }

    static class FrontierNode implements Comparable<FrontierNode> {
        char node;
        int distance;

        FrontierNode(char node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(FrontierNode o) {
            return Integer.compare(this.distance, o.distance);
        }

        @Override
        public String toString() {
            return node + "(" + (distance == Integer.MAX_VALUE ? "∞" : distance) + ")";
        }
    }

    public static void dijkstra(Map<Character, List<Edge>> graph, char startNode, char targetNode) {
        Map<Character, NodeState> states = new LinkedHashMap<>();
        for (char node : graph.keySet()) {
            states.put(node, new NodeState(node));
        }

        states.get(startNode).distance = 0;

        PriorityQueue<FrontierNode> frontier = new PriorityQueue<>();
        frontier.add(new FrontierNode(startNode, 0));

        int iteration = 1;

        System.out.println("=== START DES DIJKSTRA-ALGORITHMUS ===");
        printTable(states, "Initialzustand");
        printFrontier(frontier);

        while (!frontier.isEmpty()) {
            FrontierNode current = frontier.poll();
            char u = current.node;

            if (states.get(u).visited) {
                continue;
            }

            System.out.println("\n------------------------------------------------------------------");
            System.out.println("ITERATION " + iteration + ": Wähle Knoten '" + u + "' mit Distanz " + current.distance);
            System.out.println("------------------------------------------------------------------");

            states.get(u).visited = true;

            System.out.println("Distanzberechnungen für die Nachbarn von " + u + ":");
            boolean neighborUpdated = false;

            for (Edge edge : graph.getOrDefault(u, new ArrayList<>())) {
                char v = edge.target;
                NodeState neighborState = states.get(v);

                if (!neighborState.visited) {
                    int currentDistU = states.get(u).distance;
                    int weightUV = edge.weight;
                    int oldDistV = neighborState.distance;
                    int newDistV = currentDistU + weightUV;

                    String oldDistStr = oldDistV == Integer.MAX_VALUE ? "∞" : String.valueOf(oldDistV);
                    System.out.print("  -> Nachbar " + v + ": " + oldDistStr + " vs. (" + currentDistU + " + " + weightUV + " = " + newDistV + ")");

                    if (newDistV < oldDistV) {
                        neighborState.distance = newDistV;
                        neighborState.predecessor = u;
                        frontier.add(new FrontierNode(v, newDistV));
                        System.out.println(" => KÜRZER! Update auf " + newDistV + ", Vorgänger: " + u);
                        neighborUpdated = true;
                    } else {
                        System.out.println(" => Keine Änderung.");
                    }
                }
            }

            if (!neighborUpdated) {
                System.out.println("  (Keine Aktualisierungen der Nachbarn nötig oder möglich)");
            }

            printTable(states, "Zustand nach Iteration " + iteration);
            printFrontier(frontier);

            iteration++;
        }

        System.out.println("\n=== ALGORITHMUS BEENDET ===");

        // Kürzesten Pfad ausgeben
        printShortestPath(states, startNode, targetNode);
    }

    private static void printShortestPath(Map<Character, NodeState> states, char start, char target) {
        System.out.println("\n=======================================================");
        System.out.println("ERGEBNIS: KÜRZESTER PFAD VON KNOTEN '" + start + "' ZU KNOTEN '" + target + "'");
        System.out.println("=======================================================");

        NodeState targetState = states.get(target);
        if (targetState == null || targetState.distance == Integer.MAX_VALUE) {
            System.out.println("Es existiert kein gültiger Pfad von " + start + " nach " + target + ".");
            return;
        }

        // Rückwärts den Pfad über die Vorgänger einsammeln
        List<Character> path = new ArrayList<>();
        Character current = target;
        while (current != null) {
            path.add(current);
            current = states.get(current).predecessor;
        }

        // Da wir von hinten nach vorne gelaufen sind, Liste umdrehen
        Collections.reverse(path);

        // Pfad schön formatiert ausgeben
        System.out.print("Pfad: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        System.out.println("Gesamtdistanz (Kosten): " + targetState.distance);
        System.out.println("=======================================================");
    }

    private static void printTable(Map<Character, NodeState> states, String title) {
        System.out.println("\n[ " + title + " ]");
        System.out.printf("%-8s | %-8s | %-8s | %-9s%n", "Knoten", "Besucht", "Distanz", "Vorgänger");
        System.out.println("----------------------------------------------");
        for (NodeState s : states.values()) {
            String distStr = s.distance == Integer.MAX_VALUE ? "∞" : String.valueOf(s.distance);
            String predStr = s.predecessor == null ? "-" : String.valueOf(s.predecessor);
            System.out.printf("%-8s | %-8b | %-8s | %-9s%n", s.node, s.visited, distStr, predStr);
        }
    }

    private static void printFrontier(PriorityQueue<FrontierNode> frontier) {
        System.out.print("Aktuelle Frontier (Warteschlange): [");
        List<FrontierNode> list = new ArrayList<>();
        for (FrontierNode fn : frontier) {
            list.add(fn);
        }
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
            if (i < list.size() - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        Map<Character, List<Edge>> graph = new LinkedHashMap<>();
        graph.put('1', Arrays.asList(new Edge('2', 5), new Edge('3', 4)));
        graph.put('2', Arrays.asList(new Edge('3', 3), new Edge('5', 3), new Edge('4', 6)));
        graph.put('3', Arrays.asList(new Edge('5', 3)));
        graph.put('4', Arrays.asList(new Edge('6', 1)));
        graph.put('5', Arrays.asList(new Edge('4', 2), new Edge('6', 4)));
        graph.put('6', new ArrayList<>());

        // Dijkstra startet bei '1' und sucht den Weg zu Zielknoten '6'
        dijkstra(graph, '1', '6');
    }
}