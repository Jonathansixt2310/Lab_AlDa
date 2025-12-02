/**
 * HTML Viewer
 */

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.Stack;
import java.awt.BorderLayout;
import java.io.IOException;

public class TinyBrowser extends JFrame {

    final int WIDTH = 640;
    final int HEIGHT = 480;

    // Declare all of the components
    JScrollPane window;
    JEditorPane pane;
    JTextField urlField;
    JButton backButton;
    JButton forwardButton; //Aufgabe - Neuer Button für Forwärts
    JToolBar bar;

    // Manage the history of visited pages with a stack
    Stack<String> history;
    Stack<String> forward; //Aufgabe - Neuer Stack für
    String currentUrl;

    /**
     * Constructor: sets up the window and loads the start page
     *
     * @param start The URL of the starting page
     */
    public TinyBrowser(String start) {
        // Set up the primary viewing window
        this.pane = new JEditorPane();
        this.pane.setEditable(false); // required to view HTML
        this.pane.addHyperlinkListener(new LinkListener());

        // JScrollPane automatically adds scroll bars if
        // the content is too large for the window
        JScrollPane scroller = new JScrollPane(this.pane);

        // Text box to display and set the page URL
        this.urlField = new JTextField();
        urlField.setText(start);
        this.urlField.addActionListener(new UrlFieldListener());

        // Back button
        this.backButton = new JButton();
        backButton.setText("Back");
        backButton.addActionListener(new BackButtonListener());

        //Aufgabe - Forward button
        this.forwardButton = new JButton();
        forwardButton.setText("Forward");
        forwardButton.addActionListener(new ForwardButtonListener());

        // The back button uses a stack of URLs
        this.history = new Stack<String>();
        //Aufgabe - Initialisierung des Forward-Stack
        this.forward = new Stack<String>();

        // Load the starting page
        goToPage(start, true);

        // Put the button and text field in a JTooLBar container
        this.bar = new JToolBar();
        this.bar.add(backButton);
        this.bar.add(forwardButton); //Aufgabe - Hinzufügen von forward Button zur Toolbar
        this.bar.add(urlField);

        // Add the tool bar and editor pane to the window
        JPanel container = (JPanel) this.getContentPane();
        container.add(this.bar, BorderLayout.NORTH);
        container.add(scroller, BorderLayout.CENTER);
    }

    public void goToPage(String newUrl, boolean clearForward) {
        try {
            this.pane.setPage(newUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        history.push(urlField.getText()); // Save previous URL
        urlField.setText(newUrl); // Display new URL

        if (clearForward) {
            forward.clear();
        }
    }

    /**
     * Main -- create the window and make it visible
     */
    public static void main(String[] args) {
        TinyBrowser app = new TinyBrowser(
                "http://info.cern.ch/hypertext/WWW/TheProject.html");
        app.setSize(app.WIDTH, app.HEIGHT);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    /**
     * Three helper event listener classes
     *
     * These will be filled in with their related components
     */
    private class LinkListener implements HyperlinkListener {
        public void hyperlinkUpdate(HyperlinkEvent event) {
            // The ACTIVATED event is triggered on a link click
            if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                String newUrl = event.getURL().toString();
                goToPage(newUrl, true); //Klicken auf Link soll den Forward Stack leeren
            }
        }
    }

    private class UrlFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String newUrl = urlField.getText();
            goToPage(newUrl, true); //manueller Eingabe soll den Forward Stack leeren
        }
    }

    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String newUrl = history.pop();
            //Aufgabe - Aktuellen Link in forward Stack aufnehmen
            forward.push(urlField.getText());
            goToPage(newUrl, false);
            history.pop(); // Löschen des hinzugefügten Links vom stapel - sonst wäre es vorwärts
        }
    }

    //Aufgabe - ForwardListener
    private class ForwardButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //URL von forwärts Stack aufrufen
            String newUrl = forward.pop();
            //Aktuelle URL auf History Stack
            history.push(urlField.getText());
            //Neue Seite Laden
            goToPage(newUrl, false);
        }
    }
}