/**
 * Homage to Homage to the Square
 *
 * Recursively draw nested squares in the style of Josef Albers
 */

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;
import java.util.Random;

public class HomageStarter extends Canvas {

    private final int WIDTH = 400;
    private final int HEIGHT = 400;
    private Random random = new Random();

    /**
     * Constructor -- set the drawing surface's size
     */
    public HomageStarter() {
        setSize(WIDTH, HEIGHT);
    }

    /**
     * Recursively draw nested squares
     *
     * @param g The Graphics object for the drawing surface
     * @param count Number of squares to draw
     * @param x Starting x position
     * @param y Starting y position
     * @param size Starting square side length
     */
    public void drawSquares(Graphics g, int x, int y, int size, int
            count) {
        //Basisfall der die Rekursion bei Count 0 stopt
        if (count == 0) {
            return;
        }
        //Rekursiver Aufruf

            //Zufällige Füllfarbe wählen
            float r = random.nextFloat();
            float gr = random.nextFloat();
            float b = random.nextFloat();
            g.setColor(new Color(r, gr, b));

            //Aktuelles Quadrat zeichnen und befüllen
            g.fillRect(x,y, size, size);

            //Rekursiverfalls
            int nextSize = (int) (size * 0.75);

            //neue X Koordinate
            int nextX = x + size / 8;

            //neue y Koordinate
            int nextY = y + size / 6;

            drawSquares(g, nextX, nextY, nextSize, count -1);
    }

    /**
     * Paint the surface
     *
     * @param g Graphics object for the drawable surface
     */
    public void paint(Graphics g) {
        //Methode mit Rekursion aufrufen
        drawSquares(g, 50, 50, 300, 8);
    }

    /**
     * Main
     */
    public static void main(String[] args) {

        // Construct the driver object
        HomageStarter h = new HomageStarter();

        // Create a JFrame and attach the driver to it
        // These steps are required to make the window visible
        JFrame frame = new JFrame();
        frame.add(h);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        h.repaint();
    }
}