/**
 * SNAKE
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.util.LinkedList;
import java.util.Random;

// Enumerated type representing the snake's direction
enum Direction {
    RIGHT, LEFT, UP, DOWN;
}

public class Snake extends Canvas implements KeyListener {
    // Game objects
    private LinkedList<Rectangle> snake;
    private Rectangle pellet;
    private Direction direction;

    private Random rand; //Wird für Aufgabe 2 - Place Pallet benötigt

    // Constants
    private final int WIDTH = 400;
    private final int HEIGHT = 400;
    private final int SIZE = 10; // Side length of squares

    /**
     * Constructor - set initial positions of snake and pellet
     */
    public Snake() {
        // Set up game window and listen for keyboard
        setSize(WIDTH, HEIGHT);
        addKeyListener(this);
        setFocusable(true);

        // Starting locations
        this.snake = new LinkedList<Rectangle>();
        this.snake.add(new Rectangle(90, 90, SIZE, SIZE));
        this.snake.add(new Rectangle(90 - SIZE, 90, SIZE, SIZE));
        this.pellet = new Rectangle(300, 240, SIZE, SIZE);

        // Initial direction
        this.direction = Direction.RIGHT;

        //Konstruktor angepasst für Aufgabe 2
        this.rand = new Random();
    }

    /**
     * Listen for keyboard input
     */
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //Anpassungen für Aufgabe 1
        if (code == KeyEvent.VK_RIGHT && this.direction != Direction.LEFT) {
            this.direction = Direction.RIGHT;
        }

        if (code == KeyEvent.VK_LEFT && this.direction != Direction.RIGHT) {
            this.direction = Direction.LEFT;
        }

        if (code == KeyEvent.VK_UP && this.direction != Direction.DOWN) {
            this.direction = Direction.UP;
        }

        if (code == KeyEvent.VK_DOWN && this.direction != Direction.UP) {
            this.direction = Direction.DOWN;
        }
    }

    public void keyTyped(KeyEvent e) { /* Empty */ }
    public void keyReleased(KeyEvent e) { /* Empty */ }

    /**
     * Draw each object to the screen
     */
    public void paint(Graphics g) {
        // Clear the screen
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Draw the snake's body segments
        g.setColor(Color.BLACK);
        for (Rectangle segment : this.snake) {
            g.fillRect(segment.x, segment.y, SIZE, SIZE);
        }

        // Draw the pellet
        g.setColor(Color.RED);
        g.fillRect(pellet.x, pellet.y, SIZE, SIZE);
    }

    //Aufgabe 2
    private void placePellet() {
        // Berechnet die maximale Anzahl von Positionen in X- und Y-Richtung
        int gridWidth = WIDTH / SIZE;
        int gridHeight = HEIGHT / SIZE;

        // Wählt eine zufällige X- und Y-Position im Raster
        int newX = rand.nextInt(gridWidth) * SIZE;
        int newY = rand.nextInt(gridHeight) * SIZE;

        // Erstellt das Pellet-Objekt neu
        this.pellet = new Rectangle(newX, newY, SIZE, SIZE);
    }

    /**
     * The main game loop
     */
    public void run() {

        boolean running = true;
        while (running) {

            // Sleep for a moment
            try {
                Thread.sleep(250);
            } catch(Exception e) {
                e.printStackTrace();
            }

            // Get the current head segment
            Rectangle head = this.snake.getFirst();
            int newX = head.x;
            int newY = head.y;

            // Use the current head and direction to find
            // the coordinates of the new head segment
            if (this.direction == Direction.RIGHT) {
                newX += SIZE; // Move one square right
            } else if (this.direction == Direction.LEFT) {
                newX -= SIZE; // Move one square left
            } else if (this.direction == Direction.UP) {
                newY -= SIZE; // Move one square up
            } else if (this.direction == Direction.DOWN) {
                newY += SIZE; // Move one square down
            }

            //Aufgabe 3 - Wandkollision
            if (newX < 0 | newX >= WIDTH || newY < 0 || newY >= HEIGHT) {
                running = false;
                continue;
            }

            // Put the new head segment on the front of the list
            Rectangle newHead = new Rectangle(newX, newY, SIZE, SIZE);
            this.snake.addFirst(newHead);

            // If the snake does not eat the pellet, remove the tail
            if (!head.intersects(pellet)) {
                this.snake.removeLast();
            } else {
                placePellet();
            }

            // Check for self-collision
            for (Rectangle segment : this.snake) {
                if (segment != head && head.intersects(segment)) {
                    running = false;
                }
            }

            // Draw
            repaint();
        }
    }

    /**
     * Main
     */
    public static void main(String[] args) {
        // Construct the driver object
        Snake game = new Snake();

        // Create a JFrame and attach the driver to it
        JFrame frame = new JFrame();
        frame.add(game);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Call the run method -- this is the main game loop
        game.run();
    }
}