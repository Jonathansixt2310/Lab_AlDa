import java.util.ArrayDeque;

// Die Klasse BinaryTree verwaltet den Baum und hält eine Referenz auf die Wurzel
public class BinaryTree<T> {

    private TreeNode<T> root; // Der Wurzelknoten des Baums

    // Interne Klasse für die einzelnen Knoten
    private class TreeNode<T> {
        TreeNode<T> left;  // Referenz auf das linke Kind
        TreeNode<T> right; // Referenz auf das rechte Kind
        T key;             // Der gespeicherte Wert (Schlüssel)

        // Konstruktor für einen neuen, unverbundenen Knoten
        TreeNode(T newKey) {
            this.key = newKey;   //
            this.left = null;    //
            this.right = null;   //
        }
    }

    // Beispielmethode: Finden des am weitesten links liegenden Knotens
    public T findLeftmost() {
        if (root == null) return null;

        TreeNode<T> node = root; // Starte an der Wurzel
        while (node.left != null) { // Gehe so weit wie möglich nach links
            node = node.left; //
        }
        return node.key;
    }

    // 1. Die rekursive Methode (Das Herzstück der Tiefensuche)
    public void traverse(TreeNode node) {
        // Abbruch: Wenn wir über ein Blatt hinausgeschossen sind
        if (node == null) {
            return;
        }

        // AKTION: Wir "besuchen" den aktuellen Knoten (z.B. Drucken)
        System.out.println("Besuche Knoten: " + node.key);

        // ZUERST: Geh so tief wie möglich in den LINKEN Zweig
        traverse(node.left);

        // DANACH: Wenn links alles fertig ist, geh in den RECHTEN Zweig
        traverse(node.right);
    }

    // 2. Die Starter-Methode
    public void depthFirstTraversal() {
        // Wir starten die Reise bei der Wurzel
        traverse(this.root);
    }

    //Breitensuche
    public void levelOrderTraversal() {
        // 1. Falls der Baum leer ist, gibt es nichts zu tun
        if (this.root == null) {
            return;
        }

        // 2. Erstelle eine Queue für die Knoten (FIFO-Prinzip)
        ArrayDeque<TreeNode> q = new ArrayDeque<>();

        // 3. Starte mit der Wurzel
        q.add(this.root);

        // 4. Solange noch Knoten in der Warteschlange sind
        while (!q.isEmpty()) {
            // 5. Hole den nächsten Knoten aus der Queue (vorne)
            TreeNode next = q.remove();

            // 6. Führe die Aktion aus (Besuchen des Knotens)
            System.out.println(next.key);

            // 7. Füge die Kinder zur Queue hinzu (hinten)
            // Zuerst das linke Kind
            if (next.left != null) {
                q.add(next.left);
            }
            // Dann das rechte Kind
            if (next.right != null) {
                q.add(next.right);
            }
        }
    }
}