import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimVisualizer extends JFrame {

    // Struktur für eine Kante
    static class Edge {
        int u, v, weight;
        boolean isInMST = false;

        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }

    // Knoten-Koordinaten passend zur Netzstruktur
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

    // Prim-spezifische Variablen
    private int[] D = new int[6];       // Distanz-Array D(i)
    private int[] parent = new int[6];  // Vorgänger im Baum
    private boolean[] S = new boolean[6]; // Menge S: Knoten im MST
    private int stepCounter = 0;
    private final int START_NODE = 0;

    private GraphPanel graphPanel;
    private JLabel infoLabel;
    private JButton nextButton;

    public PrimVisualizer() {
        setTitle("Prim-Algorithmus Visualisierung");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Knoten initialisieren (X, Y Layout analog zur Vorlage)
        vertices[1] = new Vertex(1, 100, 100);
        vertices[4] = new Vertex(4, 450, 100);
        vertices[0] = new Vertex(0, 100, 300);
        vertices[2] = new Vertex(2, 275, 300);
        vertices[3] = new Vertex(3, 100, 500);
        vertices[5] = new Vertex(5, 450, 500);

        // 2. Kanten des Graphen definieren
        edges.add(new Edge(0, 1, 15));
        edges.add(new Edge(0, 2, 4));
        edges.add(new Edge(0, 3, 21));
        edges.add(new Edge(1, 2, 13));
        edges.add(new Edge(1, 4, 9));
        edges.add(new Edge(2, 3, 11));
        edges.add(new Edge(2, 4, 7));
        edges.add(new Edge(2, 5, 20));
        edges.add(new Edge(3, 5, 22));
        edges.add(new Edge(4, 5, 17));

        // Initialisierung der Arrays für den Prim-Algorithmus
        Arrays.fill(D, Integer.MAX_VALUE); // Unendlich
        Arrays.fill(parent, -1);
        D[START_NODE] = 0;                 // Startknoten D(0) = 0

        // --- TERMINAL-AUSGABE: Startzustand ---
        System.out.println("=========================================================");
        System.out.println("          PRIM-ALGORITHMUS - INITIALISIERUNG             ");
        System.out.println("=========================================================");
        System.out.println("Gegebener Startknoten: " + START_NODE);
        printTableState();
        System.out.println("---------------------------------------------------------");
        System.out.println("Klicke in der GUI auf 'Nächster Schritt'...\n");

        // GUI-Komponenten aufbauen
        graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new BorderLayout());
        infoLabel = new JLabel("Initialisiert. Startknoten ist " + START_NODE + ". Bereit für Iteration 1.", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nextButton = new JButton("Nächster Schritt");
        nextButton.setFont(new Font("Arial", Font.PLAIN, 14));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stepPrim();
            }
        });

        controlPanel.add(infoLabel, BorderLayout.NORTH);
        controlPanel.add(nextButton, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Führt eine Iterationsstufe von Prim aus
    private void stepPrim() {
        // Finden des Knotens u aus Q (nicht in S) mit minimalem D(u)
        int u = -1;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < 6; i++) {
            if (!S[i] && D[i] < minDistance) {
                minDistance = D[i];
                u = i;
            }
        }

        // Falls kein Knoten gefunden wurde oder alle erreichbaren abgearbeitet sind
        if (u == -1) {
            infoLabel.setText("Algorithmus beendet! Der minimale Spannbaum ist komplett.");
            nextButton.setEnabled(false);
            return;
        }

        // Knoten u zu S hinzufügen
        S[u] = true;
        stepCounter++;

        // Markiere die Kante, die u in den MST holt
        if (parent[u] != -1) {
            for (Edge e : edges) {
                if ((e.u == u && e.v == parent[u]) || (e.v == u && e.u == parent[u])) {
                    e.isInMST = true;
                    break;
                }
            }
        }

        // --- Terminal-Ausgabe: Aktueller Schritt ---
        System.out.println(">>> ITERATION " + stepCounter + " <<<");
        System.out.println("Wähle Knoten mit minimalem D(i) aus Q: Knoten [" + u + "] (D=" + minDistance + ")");

        if (parent[u] != -1) {
            System.out.println("-> Kante (" + parent[u] + " - " + u + ") wird fest in den MST aufgenommen.");
        } else {
            System.out.println("-> Startknoten ohne Vorgänger-Kante aufgenommen.");
        }

        // Update der Distanzen D(v) für alle Nachbarn v von u, die noch in Q sind
        System.out.println("Aktualisiere Nachbarknoten von " + u + ":");
        for (Edge e : edges) {
            int v = -1;
            if (e.u == u) v = e.v;
            else if (e.v == u) v = e.u;

            if (v != -1 && !S[v]) { // Nachbar v ist noch in Q
                if (e.weight < D[v]) {
                    System.out.printf("   Knoten %d: Altes D=%s -> Neues D=%d (über Kante mit Gewicht %d)\n",
                            v, (D[v] == Integer.MAX_VALUE ? "∞" : String.valueOf(D[v])), e.weight, e.weight);
                    D[v] = e.weight;
                    parent[v] = u;
                } else {
                    System.out.printf("   Knoten %d: Behält D=%d (Kante mit Gewicht %d bietet keine Verbesserung)\n", v, D[v], e.weight);
                }
            }
        }

        printTableState();
        System.out.println("---------------------------------------------------------\n");

        // GUI updaten
        infoLabel.setText("Iteration " + stepCounter + ": Knoten " + u + " hinzugefügt. Tabellen-Werte wurden aktualisiert.");
        graphPanel.repaint();

        // Prüfen, ob alle Knoten im MST sind
        boolean allInS = true;
        for (boolean inS : S) {
            if (!inS) {
                allInS = false;
                break;
            }
        }

        if (allInS) {
            System.out.println("=========================================================");
            System.out.println("     ALGORITHMUS BEENDET - MINIMALER SPANNBAUM FERTIG    ");
            System.out.println("=========================================================");
            infoLabel.setText("Algorithmus beendet! Alle Knoten wurden erfolgreich verbunden.");
            nextButton.setEnabled(false);
        }
    }

    // Hilfsmethode zur formatierten Darstellung der Tabelle im Terminal
    private void printTableState() {
        System.out.print("Menge S (im MST):  { ");
        for (int i = 0; i < 6; i++) if (S[i]) System.out.print(i + " ");
        System.out.println("}");

        System.out.print("Menge Q (noch frei): { ");
        for (int i = 0; i < 6; i++) if (!S[i]) System.out.print(i + " ");
        System.out.println("}");

        System.out.println("Aktuelle Tabelle D(i):");
        System.out.println("+------+---+---+---+---+---+---+");
        System.out.println("|  i   | 0 | 1 | 2 | 3 | 4 | 5 |");
        System.out.println("+------+---+---+---+---+---+---+");
        System.out.print("| D(i) |");
        for (int i = 0; i < 6; i++) {
            if (D[i] == Integer.MAX_VALUE) {
                System.out.print(" ∞ |");
            } else {
                System.out.printf("%2d |", D[i]);
            }
        }
        System.out.println("\n+------+---+---+---+---+---+---+");
    }

    // GUI-Zeichenfläche
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

                if (edge.isInMST) {
                    g2.setColor(new Color(46, 204, 113)); // Kräftiges Grün für MST
                    g2.setStroke(new BasicStroke(5));
                } else {
                    g2.setColor(Color.LIGHT_GRAY); // Standardverbindung
                    g2.setStroke(new BasicStroke(2));
                }

                g2.drawLine(u.x, u.y, v.x, v.y);

                // Kantengewicht zentrieren
                int midX = (u.x + v.x) / 2;
                int midY = (u.y + v.y) / 2;
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                g2.drawString(String.valueOf(edge.weight), midX - 5, midY - 5);
            }

            // 2. Knoten zeichnen
            int radius = 25;
            for (Vertex vertex : vertices) {
                if (vertex == null) continue;

                // Rot umrandet oder dicker gezeichnet, wenn er Teil des MST (S) ist
                if (S[vertex.id]) {
                    g2.setColor(new Color(254, 237, 222)); // Heller Farbton für aktivierte Knoten
                    g2.fillOval(vertex.x - radius, vertex.y - radius, 2 * radius, 2 * radius);
                    g2.setColor(new Color(231, 76, 60));   // Rote Umrandung analog zu den PDF-Folien
                    g2.setStroke(new BasicStroke(4));
                } else {
                    g2.setColor(Color.WHITE);
                    g2.fillOval(vertex.x - radius, vertex.y - radius, 2 * radius, 2 * radius);
                    g2.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(2));
                }

                g2.drawOval(vertex.x - radius, vertex.y - radius, 2 * radius, 2 * radius);

                // Knotennummer hineinschreiben
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 16));
                String idStr = String.valueOf(vertex.id);
                FontMetrics fm = g2.getFontMetrics();
                int txtX = vertex.x - fm.stringWidth(idStr) / 2;
                int txtY = vertex.y + fm.getAscent() / 2 - 2;
                g2.drawString(idStr, txtX, txtY);

                // Aktuellen D(i) Wert klein über dem Knoten einblenden
                g2.setFont(new Font("Arial", Font.ITALIC, 13));
                g2.setColor(Color.GRAY);
                String dVal = D[vertex.id] == Integer.MAX_VALUE ? "∞" : String.valueOf(D[vertex.id]);
                g2.drawString("D=" + dVal, vertex.x - 18, vertex.y - radius - 5);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PrimVisualizer().setVisible(true);
            }
        });
    }
}