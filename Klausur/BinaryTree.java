public class BinaryTree {
    // Interne Klasse für die Knotenstruktur
    private class TreeNode {
        int data;
        TreeNode left;  // Verweis auf den linken Teilbaum
        TreeNode right; // Verweis auf den rechten Teilbaum

        TreeNode(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private TreeNode root; // Die Wurzel des Baums

    public BinaryTree() {
        root = null;
    }
}