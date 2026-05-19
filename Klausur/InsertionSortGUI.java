import javax.swing.*;
import java.awt.*;

public class InsertionSortGUI extends JPanel {

    // Unser Start-Array
    private int[] arr = {8, 7, 6, 5, 4, 3, 2, 1};

    // Variablen zur farblichen Hervorhebung
    private int currentIndex = -1;   // Der aktuelle "Key", der einsortiert wird
    private int comparingIndex = -1; // Das Element, mit dem gerade verglichen wird
    private int sortedBoundary = 0;  // Bis wohin das Array bereits sortiert ist

    public InsertionSortGUI() {
        // Größe des Fensters festlegen
        setPreferredSize(new Dimension(800, 500));
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Zeicheneinstellungen für Kästchen
        int boxSize = 60; // Größe eines Kästchens (Breite und Höhe)
        int spacing = 10; // Abstand zwischen den Kästchen
        int totalWidth = arr.length * boxSize + (arr.length - 1) * spacing;

        // Start-X-Position berechnen, damit die Reihe zentriert ist
        int startX = (getWidth() - totalWidth) / 2;
        // Y-Position berechnen, damit die Reihe vertikal zentriert ist
        int y = (getHeight() - boxSize) / 2;

        // Schleife zum Zeichnen der einzelnen Kästchen
        for (int i = 0; i < arr.length; i++) {
            int x = startX + i * (boxSize + spacing);

            // Farben festlegen basierend auf dem aktuellen Status
            if (i == currentIndex) {
                g.setColor(new Color(255, 100, 100)); // Rot für den Key
            } else if (i == comparingIndex) {
                g.setColor(new Color(255, 200, 0));   // Gelb für den Vergleichspartner
            } else if (i <= sortedBoundary) {
                g.setColor(new Color(100, 200, 100)); // Grün für bereits sortierte Elemente
            } else {
                g.setColor(new Color(100, 150, 255)); // Blau für noch unsortierte Elemente
            }

            // Gefülltes Kästchen zeichnen
            g.fillRect(x, y, boxSize, boxSize);

            // Rahmen um das Kästchen zeichnen (für bessere Abgrenzung)
            g.setColor(Color.WHITE);
            g.drawRect(x, y, boxSize, boxSize);

            // Zahl zentriert IM Kästchen zeichnen
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics metrics = g.getFontMetrics();
            int textWidth = metrics.stringWidth(String.valueOf(arr[i]));
            int textHeight = metrics.getHeight();

            int textX = x + (boxSize - textWidth) / 2;
            int textY = y + ((boxSize - textHeight) / 2) + metrics.getAscent();

            g.drawString(String.valueOf(arr[i]), textX, textY);
        }
    }

    // Die Sortier-Methode, die in einem separaten Thread läuft
    public void startSorting() {
        new Thread(() -> {
            sleep(1000); // Kurze Pause vor dem Start

            for (int i = 1; i < arr.length; i++) {
                int key = arr[i];
                currentIndex = i;
                int j = i - 1;

                sortedBoundary = i - 1;
                repaint(); // Bildschirm aktualisieren
                sleep(800); // Animations-Pause

                // Vergleichen und verschieben
                while (j >= 0 && arr[j] > key) {
                    comparingIndex = j;
                    repaint();
                    sleep(600);

                    arr[j + 1] = arr[j];
                    j = j - 1;

                    repaint();
                    sleep(600);
                }

                // Key einfügen
                arr[j + 1] = key;
                comparingIndex = -1;
                repaint();
                sleep(800);
            }

            // Fertig! Alles als sortiert markieren
            sortedBoundary = arr.length - 1;
            currentIndex = -1;
            comparingIndex = -1;
            repaint();

        }).start();
    }

    // Hilfsmethode für die Animations-Pausen
    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Das Fenster (JFrame) erstellen
        JFrame frame = new JFrame("Insertion Sort Animation");
        InsertionSortGUI panel = new InsertionSortGUI();

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Zentriert das Fenster auf dem Bildschirm
        frame.setVisible(true);

        // Sortierung starten
        panel.startSorting();
    }
}