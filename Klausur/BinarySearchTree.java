/**
 * Implementierung eines Binären Suchbaums (BST) [cite: 691-692]
 * Der Datentyp K muss 'Comparable' sein, um Vergleiche zu ermöglichen [cite: 718, 756]
 */
public class BinarySearchTree<K extends Comparable<K>> {

    private TreeNode<K> root; // Die Wurzel des Baums

    // Interne Klasse für die Knotenstruktur
    private class TreeNode<K> {
        K key;
        TreeNode<K> left;  //Linkes Kind: kleinere Werte
        TreeNode<K> right; // Rechtes Kind: größere/gleiche Werte

        TreeNode(K newKey) {
            this.key = newKey;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * Suchen nach einem Schlüssel [cite: 734, 761]
     * Iterative Umsetzung laut Folie 23 [cite: 766-788]
     */
    public boolean search(K searchKey) {
        TreeNode<K> current = this.root;

        while (current != null) {
            int comparison = searchKey.compareTo(current.key);

            if (comparison == 0) {
                return true; // Gefunden!
            } else if (comparison < 0) {
                current = current.left; // Suche im linken Teilbaum weiter
            } else {
                current = current.right; // Suche im rechten Teilbaum weiter
            }
        }
        return false; // Schlüssel nicht im Baum
    }

    /**
     * Einfügen eines neuen Schlüssels [cite: 806, 845]
     * Iterative Umsetzung laut Folie 26 [cite: 857-883]
     */
    public void insert(K key) {
        TreeNode<K> newNode = new TreeNode<>(key);
        TreeNode<K> current = this.root;
        TreeNode<K> parent = null;

        // Den Baum hinabsteigen, um die Einfügeposition zu finden
        while (current != null) {
            parent = current;
            int comparison = key.compareTo(current.key);

            if (comparison == 0) {
                return; // Key existiert bereits
            } else if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        // Den neuen Knoten an den 'parent' anhängen
        if(this.root == null) {
            this.root = newNode; //leerer Baum
        }
        else if (key.compareTo(parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    /**
     * Löschen eines Schlüssels (Logik nach Folie 27-33)
     */
    public void delete(K key) {
        root = deleteRecursive(root, key);
    }

    private TreeNode<K> deleteRecursive(TreeNode<K> current, K key) {
        if (current == null) return null;

        int comparison = key.compareTo(current.key);
        if (comparison < 0) {
            current.left = deleteRecursive(current.left, key);
        } else if (comparison > 0) {
            current.right = deleteRecursive(current.right, key);
        } else {
            // Fall 1 & 2: Ein Kind oder kein Kind (Folie 27, 28)
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            // Fall 3: Zwei Kinder (Folie 30-33)
            // Ersetze durch Nachfolger (kleinster Wert im rechten Teilbaum)
            current.key = findMin(current.right);
            current.right = deleteRecursive(current.right, current.key);
        }
        return current;
    }

    private K findMin(TreeNode<K> node) {
        K min = node.key;
        while (node.left != null) {
            min = node.left.key;
            node = node.left;
        }
        return min;
    }
}