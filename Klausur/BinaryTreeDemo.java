import java.util.LinkedList;
import java.util.Queue;

/**
 * Vollständige Implementierung eines Binären Suchbaums basierend auf Kapitel 17.
 */
public class BinaryTreeDemo {

    // --- Interne Knotenstruktur (Folie 11) ---
    private static class TreeNode {
        int data;
        TreeNode left;  // Referenz auf den linken Teilbaum
        TreeNode right; // Referenz auf den rechten Teilbaum

        TreeNode(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private TreeNode root; // Die Wurzel des Baums

    public BinaryTreeDemo() {
        this.root = null;
    }

    // --- Einfügen (Folie 24-26) ---
    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private TreeNode insertRecursive(TreeNode current, int value) {
        if (current == null) {
            return new TreeNode(value); // [cite: 1013, 1076]
        }

        if (value < current.data) {
            current.left = insertRecursive(current.left, value);
        } else if (value > current.data) {
            current.right = insertRecursive(current.right, value);
        }
        return current;
    }

    // --- 1. Pre-Order Traversierung: Wurzel -> Links -> Rechts (Folie 14-15) ---
    public void printPreOrder(TreeNode node) {
        if (node == null) return;
        System.out.print(node.data + " ");
        printPreOrder(node.left);
        printPreOrder(node.right);
    }

    // --- 2. In-Order Traversierung: Links -> Wurzel -> Rechts (Folie 16) ---
    public void printInOrder(TreeNode node) {
        if (node == null) return;
        printInOrder(node.left);
        System.out.print(node.data + " "); // Wurzel in der Mitte
        printInOrder(node.right);
    }

    // --- 3. Post-Order Traversierung: Links -> Rechts -> Wurzel (Folie 17) ---
    public void printPostOrder(TreeNode node) {
        if (node == null) return;
        printPostOrder(node.left);
        printPostOrder(node.right);
        System.out.print(node.data + " ");
    }

    // --- 4. Level-Order Traversierung: Ebene für Ebene (Folie 18) ---
    public void printLevelOrder() {
        if (root == null) return;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root); // [cite: 841]

        while (!queue.isEmpty()) {
            TreeNode next = queue.remove();
            System.out.print(next.data + " ");

            if (next.left != null) {
                queue.add(next.left);
            }
            if (next.right != null) {
                queue.add(next.right);
            }
        }
    }

    // --- Hilfsmethode für den Zugriff auf die Wurzel in der Main ---
    public TreeNode getRoot() {
        return root;
    }

    public static void main(String[] args) {
        BinaryTreeDemo tree = new BinaryTreeDemo();

        /* Beispielbaum Aufbau:
               5
              / \
             3   8
            / \
           1   4
        */
        tree.insert(5);
        tree.insert(3);
        tree.insert(8);
        tree.insert(1);
        tree.insert(4);

        System.out.println("In-Order (sortiert):");
        tree.printInOrder(tree.getRoot()); // [cite: 915]

        System.out.println("\n\nPre-Order (Struktur-Kopie):");
        tree.printPreOrder(tree.getRoot());

        System.out.println("\n\nPost-Order (Lösch-Reihenfolge):");
        tree.printPostOrder(tree.getRoot());

        System.out.println("\n\nLevel-Order (Breitensuche):");
        tree.printLevelOrder();
    }
}