import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class IslandCounter extends JPanel {

    private static int ROWS = 10;
    private static int COLS = 12;
    private static int CELL_SIZE = 50;

    // 0: Wasser (Blau), 1: Land (Grün/Gelb), >1: Markierte Insel
    private int[][] grid;
    private Color[] islandColors = new Color[100]; // Speicher für verschiedene Inselfarben
    private int islandCount = 0;

    public IslandCounter() {
        grid = new int[ROWS][COLS];
        setupMap(); // Erstellt ein Test-Szenario mit mehreren Inseln

        // Startet die Logik in einem separaten Thread [cite: 507-509]
        new Thread(this::findAndFillIslands).start();
    }

    private void setupMap() {
        // Insel 1 (oben links)
        grid[1][1] = 1; grid[1][2] = 1; grid[2][1] = 1;
        // Insel 2 (Mitte)
        grid[4][4] = 1; grid[4][5] = 1; grid[5][4] = 1; grid[5][5] = 1; grid[3][5] = 1;
        // Insel 3 (unten rechts)
        grid[7][9] = 1; grid[8][9] = 1; grid[8][10] = 1; grid[7][10] = 1;
        // Einzelnes Landstück
        grid[1][10] = 1;
    }

    private void findAndFillIslands() {
        boolean[][] visited = new boolean[ROWS][COLS];
        Random rand = new Random();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                // Wenn wir unbesuchtes Land finden, haben wir eine neue Insel [cite: 508]
                if (grid[r][c] == 1 && !visited[r][c]) {
                    islandCount++;
                    // Weise der neuen Insel eine zufällige Farbe zu
                    islandColors[islandCount] = new Color(rand.nextInt(200), rand.nextInt(200) + 55, rand.nextInt(100));

                    // Starte Flood-Fill Markierung [cite: 416-418]
                    fillIsland(r, c, visited, islandCount);
                }
            }
        }
        System.out.println("Gefundene Inseln: " + islandCount);
    }

    private void fillIsland(int startR, int startC, boolean[][] visited, int id) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{startR, startC});
        visited[startR][startC] = true;

        while (!q.isEmpty()) {
            int[] pos = q.poll(); //
            int row = pos[0];
            int col = pos[1];

            grid[row][col] = id + 1; // Markiere Feld mit Insel-ID
            repaint(); // GUI aktualisieren

            try { Thread.sleep(300); } catch (InterruptedException e) {}

            // Prüfe alle 8 Nachbarn (Diagonalen eingeschlossen) [cite: 477-478]
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    int nr = row + dr;
                    int nc = col + dc;

                    if (nr >= 0 && nr < ROWS && nc >= 0 && nc < COLS) {
                        if (grid[nr][nc] == 1 && !visited[nr][nc]) {
                            visited[nr][nc] = true;
                            q.offer(new int[]{nr, nc}); //
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == 0) {
                    g.setColor(new Color(50, 150, 255)); // Wasser
                } else if (grid[r][c] == 1) {
                    g.setColor(new Color(34, 139, 34)); // Unentdecktes Land
                } else {
                    g.setColor(islandColors[grid[r][c] - 1]); // Entdeckte Insel
                }

                g.fillRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(new Color(0, 0, 0, 30));
                g.drawRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Island Counter Visualisierung");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new IslandCounter());
        frame.pack();
        frame.setSize(COLS * 50 + 20, ROWS * 50 + 40);
        frame.setVisible(true);
    }
}