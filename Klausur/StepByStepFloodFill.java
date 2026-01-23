import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class StepByStepFloodFill extends JPanel {
        private final int ROWS = 10, COLS = 10, CELL_SIZE = 50;
        private int[][] grid = new int[ROWS][COLS]; // 0: Weiß, 1: Schwarz (Wand), 2: Blau (Gefüllt)

        public StepByStepFloodFill() {
            // Erzeuge eine geschlossene Grenze (eine Box von Index 1 bis 8)
            for (int i = 1; i < 9; i++) {
                grid[1][i] = 1; // Obere Wand
                grid[8][i] = 1; // Untere Wand
                grid[i][1] = 1; // Linke Wand
                grid[i][8] = 1; // Rechte Wand
            }

            // Starte den Algorithmus im Zentrum der Box (5,5)
            new Thread(() -> runStepByStep(5, 5, 2)).start();
        }

        public void runStepByStep(int r, int c, int fill) {
            Scanner scanner = new Scanner(System.in);
            Queue<int[]> q = new LinkedList<>();
            boolean[][] visited = new boolean[ROWS][COLS];

            int initialValue = grid[r][c]; // Merkt sich die Farbe des Startfelds (0 / Weiß)
            q.offer(new int[]{r, c});
            visited[r][c] = true;

            while (!q.isEmpty()) {
                // 1. Anzeige der aktuellen Queue in der Konsole [cite: 426-434]
                System.out.println("\n--- Aktuelle Queue (Warteschlange) ---");
                for (int[] pos : q) {
                    System.out.print("(" + pos[0] + "," + pos[1] + ") ");
                }
                System.out.println();

                // 2. Interaktive Pause
                System.out.print("Eingabe '1' für den nächsten Schritt: ");
                while (!scanner.hasNext("1")) {
                    scanner.next(); // Ungültige Eingabe verwerfen
                }
                scanner.next(); // Die '1' konsumieren

                // 3. Element entnehmen und färben (FIFO-Prinzip) [cite: 261, 458]
                int[] next = q.poll();
                int row = next[0];
                int col = next[1];
                grid[row][col] = fill;
                repaint();

                // 4. Acht Nachbarn prüfen
                for (int nr = row - 1; nr <= row + 1; nr++) {
                    for (int nc = col - 1; nc <= col + 1; nc++) {
                        // Prüfe: Innerhalb des Arrays? [cite: 480]
                        if (nr >= 0 && nr < ROWS && nc >= 0 && nc < COLS) {
                            // Bedingung: Nur Felder mit der Startfarbe (Weiß) und unbesucht
                            // Schwarze Felder (1) erfüllen 'grid[nr][nc] == initialValue' NICHT
                            if (grid[nr][nc] == initialValue && !visited[nr][nc]) {
                                visited[nr][nc] = true;
                                q.offer(new int[]{nr, nc}); // Hinten anstellen [cite: 262]
                            }
                        }
                    }
                }
            }
            System.out.println("\nFüllung beendet. Alle erreichbaren Felder innerhalb der Grenze sind blau.");
            scanner.close();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    if (grid[r][c] == 0) g.setColor(Color.WHITE);
                    else if (grid[r][c] == 1) g.setColor(Color.BLACK);
                    else g.setColor(Color.BLUE);

                    g.fillRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        public static void main(String[] args) {
            JFrame frame = new JFrame("Interaktiver Bounded Flood-Fill");
            frame.add(new StepByStepFloodFill());
            frame.setSize(520, 540);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            System.out.println("Interaktions-Modus: Bitte die Konsole für Eingaben nutzen!");
        }
}