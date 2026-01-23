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
}