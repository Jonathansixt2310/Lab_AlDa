import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class RekursiverBaum extends JPanel {

    // --- Turtle-Zustand ---
    private double x, y;       // Aktuelle Position
    private double heading;    // Blickrichtung in Grad (-90 ist oben)

    public RekursiverBaum() {
        this.x = 400;
        this.y = 550;
        this.heading = -90;
    }

    private void tree(Graphics2D g, int count, double length, double angle, double scale) {

        // Base Case
        if (count == 0) {
            return;
        }

        // Draw the trunk
        move(g, length);

        // Left subtree (Drehen)
        turnLeft(angle);

        // Rekursiver Aufruf Links
        tree(g, count - 1, length * scale, angle, scale);

        // Turn back to center -> Right subtree
        // zwei Schritte: erst zur Mitte, dann nach rechts
        turnRight(angle); // Zurück zur Mitte
        turnRight(angle); // Nach rechts drehen

        // Rekursiver Aufruf Rechts
        tree(g, count - 1, length * scale, angle, scale);

        // Return to the base of the tree
        turnLeft(angle);   // Zurück zur Ausrichtung des Stammes

        // Der physische Rückweg (Backtracking)
        turnLeft(180);     // Umdrehen (Blick nach unten)
        move(g, length);   // Den Stamm zurücklaufen
        turnLeft(180);     // Wieder umdrehen (Blick nach oben, wie am Anfang)
    }
    // =============================================================


    // --- Hilfsmethoden (Simulation der Turtle-Befehle) ---
    private void move(Graphics2D g, double length) {
        double rad = Math.toRadians(heading);
        double x2 = x + Math.cos(rad) * length;
        double y2 = y + Math.sin(rad) * length;

        // Wir zeichnen nur, wenn wir "vorwärts" bauen.
        // Beim Rückweg (Backtracking) zeichnen wir technisch gesehen drüber,
        // was optisch egal ist, aber die Logik der Folie erfordert den move()-Aufruf.
        g.draw(new Line2D.Double(x, y, x2, y2));

        this.x = x2;
        this.y = y2;
    }

    private void turnLeft(double angle) {
        heading -= angle;
    }

    private void turnRight(double angle) {
        heading += angle;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.setColor(Color.BLACK);

        // Reset für jeden Paint-Zyklus
        this.x = 400;
        this.y = 550;
        this.heading = -90;

        // Aufruf mit Beispielwerten
        tree(g2d, 3, 100, 30, 0.8);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rekursiver Baum");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new RekursiverBaum());
        frame.setVisible(true);
    }
}