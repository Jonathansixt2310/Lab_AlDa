import java.util.*;

/**
 * Starter-Klasse für das Pixie-Empfehlungssystem.
 * Basierend auf dem Modell eines bipartiten Graphen
 */
public class PixieStarter {

    // Adjazenzliste: Speichert Verbindungen zwischen Items (Songs) und Listen (Playlists)
    private HashMap<String, ArrayList<String>> graph;
    private final double RESET_PROB = 0.5; // Abbruchwahrscheinlichkeit p

    public PixieStarter() {
        this.graph = new HashMap<>();
        initExampleData();
    }

    private void initExampleData() {
        addEdge("Despacito", "Latin Party");
        addEdge("Despacito", "Summer Jams");
        addEdge("Dynamite", "Summer Jams");
        addEdge("Dynamite", "Boy Bands");
        addEdge("Mambo #5", "90's");
        addEdge("Mambo #5", "Summer Jams");
    }

    /**
     * Aufgabe 1a: Implementieren Sie das Hinzufügen einer ungerichteten Kante.
     * Denken Sie daran, dass beide Richtungen in der Map gespeichert werden müssen.
     */
    public void addEdge(String item, String list) {
        // NEU: Implementierung für ungerichteten Graphen
        // Kante von Item zu Liste
        this.graph.computeIfAbsent(item, k -> new ArrayList<>()).add(list);
        // Kante von Liste zu Item (Rückrichtung)
        this.graph.computeIfAbsent(list, k -> new ArrayList<>()).add(item);
    }

    /**
     * Aufgabe 1b: Prüfen Sie mittels BFS (Breitensuche), ob der Graph bipartit (2-färbbar) ist.
     * Ein Graph ist nicht bipartit, wenn er Zyklen ungerader Länge enthält.
     */
    public boolean isBipartite() {
        // Tipp: Nutzen Sie eine Map<String, Integer> für die Farben (z.B. 0 und 1).

        // NEU: Implementierung Bipartit-Test mittels BFS und 2-Färbung
        HashMap<String, Integer> colors = new HashMap<>();

        // Iteration über alle Knoten, falls der Graph aus mehreren nicht verbundenen Komponenten besteht
        for (String startNode : graph.keySet()) {
            if (colors.containsKey(startNode)) continue; // Bereits besucht

            // Startknoten färben (Farbe 0)
            colors.put(startNode, 0);
            Queue<String> queue = new LinkedList<>();
            queue.add(startNode);

            while (!queue.isEmpty()) {
                String current = queue.poll();
                int currentColor = colors.get(current);
                int neighborColor = 1 - currentColor; // Das Gegenteil von 0 ist 1, und umgekehrt

                // Nachbarn prüfen
                if (graph.get(current) != null) {
                    for (String neighbor : graph.get(current)) {
                        if (!colors.containsKey(neighbor)) {
                            // Noch nicht gefärbt: Färben und in die Queue
                            colors.put(neighbor, neighborColor);
                            queue.add(neighbor);
                        } else if (colors.get(neighbor) == currentColor) {
                            // Konflikt: Nachbar hat dieselbe Farbe -> Nicht bipartit
                            return false;
                        }
                    }
                }
            }
        }
        return true; // Keine Konflikte gefunden
    }

    /**
     * Aufgabe 2: Implementieren Sie den Pixie Random Walk.
     * 1. Starten Sie beim 'start' Knoten.
     * 2. Springen Sie zu einer zufälligen Liste und dann zu einem zufälligen Item.
     * 3. Erhöhen Sie den Besuchs-Zähler für das gefundene Item.
     * 4. Nutzen Sie RESET_PROB für den Teleport zurück zum Start.
     */
    public List<String> recommend(String start, int maxSteps, int k) {
        HashMap<String, Integer> counts = new HashMap<>();

        // NEU: Implementierung des Random Walks
        Random random = new Random();
        String currentItem = start;

        // Prüfen, ob Startknoten überhaupt existiert
        if (!graph.containsKey(start)) return new ArrayList<>();

        for (int i = 0; i < maxSteps; i++) {

            // Schritt 1: Abbruchwahrscheinlichkeit (Teleport zum Start)
            // p = 0.5 (RESET_PROB)
            if (random.nextDouble() < RESET_PROB) {
                currentItem = start;
                // Nach einem Reset zählt der Schritt meist nicht als "Besuch" eines neuen relevanten Items,
                // sondern setzt den Pfad zurück. Wir fahren mit dem nächsten Loop-Durchlauf fort.
                continue;
            }

            // Schritt 2: Random Walk Logik (Item -> Liste -> Item)

            // a) Wähle zufällige Liste (Nachbar von currentItem)
            ArrayList<String> connectedLists = graph.get(currentItem);
            if (connectedLists == null || connectedLists.isEmpty()) {
                currentItem = start; // Sackgasse, zurück zum Start
                continue;
            }
            String randomList = connectedLists.get(random.nextInt(connectedLists.size()));

            // b) Wähle zufälliges Item aus dieser Liste (Nachbar von randomList)
            ArrayList<String> itemsInList = graph.get(randomList);
            if (itemsInList == null || itemsInList.isEmpty()) {
                currentItem = start;
                continue;
            }
            String nextItem = itemsInList.get(random.nextInt(itemsInList.size()));

            // Schritt 3: Häufigkeit tracken
            // Wir zählen das erreichte Item.
            currentItem = nextItem;

            // Optional: Start-Item selbst oft aus den Empfehlungen ausschließen,
            // aber laut Aufgabe sollen wir einfach Häufigkeiten tracken.
            counts.put(currentItem, counts.getOrDefault(currentItem, 0) + 1);
        }

        return getTopK(counts, k);
    }

    private List<String> getTopK(HashMap<String, Integer> counts, int k) {
        // Hilfsmethode: Extrahiert die k Items mit den höchsten Zählerwerten.
        List<String> results = new ArrayList<>(counts.keySet());
        // Sortiere absteigend nach Häufigkeit
        results.sort((a, b) -> counts.get(b) - counts.get(a));
        return results.subList(0, Math.min(k, results.size()));
    }

    public static void main(String[] args) {
        PixieStarter pixie = new PixieStarter();

        System.out.println("Graph ist bipartit: " + pixie.isBipartite());

        // Beispielaufruf
        List<String> recommendations = pixie.recommend("Despacito", 1000, 2);
        System.out.println("Empfehlungen für 'Despacito': " + recommendations);
    }
}