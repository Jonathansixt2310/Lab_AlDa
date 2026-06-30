import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalVisualizer extends JFrame {

    // Definition einer Kante
    static class Edge implements Comparable<Edge> {
        int u, v, weight;
        String status = "UNDECIDED"; // "ACCEPTED", "REJECTED", "UNDECIDED"

        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    // Knoten-Koordinaten für die visuelle Anordnung analog zur Vorlage
    static class Vertex {
        int id;
        int x, y;
        Vertex(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

    private List<Edge> edges = new ArrayList<>();
    private Vertex[] vertices = new Vertex[6];
    private int currentEdgeIndex = 0;
    private int iterationCounter = 1;
    private int[] parent;

    private GraphPanel graphPanel;
    private JLabel infoLabel;
    private JButton nextButton;

    public KruskalVisualizer() {
        setTitle("Kruskal-Algorithmus Visualisierung");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Knoten initialisieren (X, Y Layout passend zur Vorlage)
        vertices[1] = new Vertex(1, 100, 100);
        vertices[4] = new Vertex(4, 450, 100);
        vertices[0] = new Vertex(0, 100, 300);
        vertices[2] = new Vertex(2, 275, 300);
        vertices[3] = new Vertex(3, 100, 500);
        vertices[5] = new Vertex(5, 450, 500);

        // 2. Kanten initialisieren (gemäß Tabelle aus der Aufgabe)
        edges.add(new Edge(0, 2, 4));
        edges.add(new Edge(2, 4, 7));
        edges.add(new Edge(1, 4, 9));
        edges.add(new Edge(2, 3, 11));
        edges.add(new Edge(1, 2, 13));
        edges.add(new Edge(0, 1, 15));
        edges.add(new Edge(4, 5, 17));
        edges.add(new Edge(2, 5, 20));
        edges.add(new Edge(0, 3, 21));
        edges.add(new Edge(3, 5, 22));

        // Kanten aufsteigend nach Gewicht sortieren (Kruskal-Prinzip)
        Collections.sort(edges);

        // --- TERMINAL-AUSGABE: Startzustand ---
        System.out.println("=========================================================");
        System.out.println("          KRUSKAL-ALGORITHMUS - STARTZUSTAND             ");
        System.out.println("=========================================================");
        System.out.println("Sortierte Kantenliste (Priorisierung nach Gewicht):");
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            System.out.printf("%2d. Kante (%d-%d) | Gewicht: %2d\n", (i + 1), e.u, e.v, e.weight);
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("Klicke in der GUI auf 'Nächster Schritt' für die Iterationen...\n");

        // Union-Find Datenstruktur vorbereiten
        parent = new int[6];
        for (int i = 0; i < 6; i++) {
            parent[i] = i;
        }

        // GUI Komponenten
        graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new BorderLayout());
        infoLabel = new JLabel("Kruskal gestartet. Kanten sortiert. Bereit für Iteration 1.", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nextButton = new JButton("Nächster Schritt");
        nextButton.setFont(new Font("Arial", Font.PLAIN, 14));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stepKruskal();
            }
        });

        controlPanel.add(infoLabel, BorderLayout.NORTH);
        controlPanel.add(nextButton, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Union-Find: Find-Operation mit Pfadkompression
    private int find(int i) {
        if (parent[i] == i)
            return i;
        return parent[i] = find(parent[i]);
    }

    // Union-Find: Union-Operation
    private void union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);
        parent[rootI] = rootJ;
    }

    // Ein Einzelschritt des Algorithmus mit Terminal-Logging
    private void stepKruskal() {
        if (currentEdgeIndex >= edges.size()) {
            infoLabel.setText("Algorithmus beendet!");
            nextButton.setEnabled(false);
            return;
        }

        Edge edge = edges.get(currentEdgeIndex);
        int rootU = find(edge.u);
        int rootV = find(edge.v);

        // Ausführliches Terminal-Protokoll vorbereiten
        System.out.println(">>> ITERATION " + iterationCounter + " <<<");
        System.out.println("Untersuche Kante: (" + edge.u + " - " + edge.v + ") mit Gewicht: " + edge.weight);
        System.out.println("-> Komponente von Knoten " + edge.u + " hat Repräsentant: " + rootU);
        System.out.println("-> Komponente von Knoten " + edge.v + " hat Repräsentant: " + rootV);

        if (rootU != rootV) {
            // Kein Kreis -> Kante wird aufgenommen
            edge.status = "ACCEPTED";
            union(rootU, rootV);

            System.out.println("ERGEBNIS: [AKZEPTIERT]");
            System.out.println("Grund: Die Knoten gehören zu verschiedenen Komponenten (kein Kreis).");
            System.out.println("Aktion: Komponenten werden vereinigt. " + rootU + " verweist nun auf " + find(rootV) + ".");

            infoLabel.setText("Schritt " + iterationCounter + ": Kante (" + edge.u + "-" + edge.v + ") mit Gewicht " + edge.weight + " hinzugefügt.");
        } else {
            // Kreis erkannt -> Kante wird abgelehnt
            edge.status = "REJECTED";

            System.out.println("ERGEBNIS: [ABGELEHNT]");
            System.out.println("Grund: Beide Knoten haben bereits denselben Repräsentanten (" + rootU + ").");
            System.out.println("Aktion: Kante wird übersprungen, da sie einen geschlossenen Kreis bilden würde.");

            infoLabel.setText("Schritt " + iterationCounter + ": Kante (" + edge.u + "-" + edge.v + ") mit Gewicht " + edge.weight + " abgelehnt.");
        }

        System.out.println("Aktuelle Union-Find Struktur (Eltern-Array):");
        System.out.print("Knoten: ");
        for(int i=0; i<6; i++) System.out.print(i + " ");
        System.out.print("\nParent: ");
        for(int i=0; i<6; i++) System.out.print(parent[i] + " ");
        System.out.println("\n---------------------------------------------------------\n");

        currentEdgeIndex++;
        iterationCounter++;
        graphPanel.repaint();

        if (currentEdgeIndex >= edges.size()) {
            System.out.println("=========================================================");
            System.out.println("     ALGORITHMUS BEENDET - MINIMALER SPANNBAUM FERTIG    ");
            System.out.println("=========================================================");
            infoLabel.setText("Algorithmus beendet! Der minimale Spannbaum ist komplett.");
            nextButton.setEnabled(false);
        }
    }

    // Panel zum Zeichnen des Graphen
    private class GraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 1. Alle Kanten zeichnen
            for (Edge edge : edges) {
                Vertex u = vertices[edge.u];
                Vertex v = vertices[edge.v];

                g2.setStroke(new BasicStroke(2));
                g2.setColor(Color.LIGHT_GRAY);

                if (edge.status.equals("ACCEPTED")) {
                    g2.setColor(new Color(46, 204, 113)); // Grün
                    g2.setStroke(new BasicStroke(5));
                } else if (edge.status.equals("REJECTED")) {
                    g2.setColor(new Color(231, 76, 60)); // Rot
                    g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{9}, 0)); // Gestrichelt
                }

                g2.drawLine(u.x, u.y, v.x, v.y);

                int midX = (u.x + v.x) / 2;
                int midY = (u.y + v.y) / 2;
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                g2.drawString(String.valueOf(edge.weight), midX - 5, midY - 5);
            }

            // 2. Alle Knoten zeichnen
            int radius = 25;
            for (Vertex vertex : vertices) {
                if (vertex == null) continue;

                g2.setColor(Color.WHITE);
                g2.fillOval(vertex.x - radius, vertex.y - radius, 2 * radius, 2 * radius);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(3));
                g2.drawOval(vertex.x - radius, vertex.y - radius, 2 * radius, 2 * radius);

                g2.setFont(new Font("Arial", Font.BOLD, 16));
                String idStr = String.valueOf(vertex.id);
                FontMetrics fm = g2.getFontMetrics();
                int txtX = vertex.x - fm.stringWidth(idStr) / 2;
                int txtY = vertex.y + fm.getAscent() / 2 - 2;
                g2.drawString(idStr, txtX, txtY);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KruskalVisualizer().setVisible(true);
            }
        });
    }
}