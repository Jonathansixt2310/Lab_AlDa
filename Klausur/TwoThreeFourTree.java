public class TwoThreeFourTree {

    private Node root = new Node(); // Der Baum startet mit einer leeren Wurzel

    // Ein Knoten kann bis zu 3 Schlüssel und 4 Kinder haben
    private static class Node {
        private static final int MAX_KEYS = 3;
        private int numKeys = 0;
        private long[] keys = new long[MAX_KEYS];
        private Node[] children = new Node[MAX_KEYS + 1];

        public boolean isLeaf() { return children[0] == null; }
        public boolean isFull() { return numKeys == MAX_KEYS; }

        // Hilfsmethode zum Einfügen eines Schlüssels in einen nicht-vollen Knoten
        public void insertKey(long key) {
            int i = numKeys - 1;
            while (i >= 0 && keys[i] > key) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = key;
            numKeys++;
        }
    }

    public void insert(long key) {
        // Preemptive Split: Wenn die Wurzel voll ist, spalte sie sofort
        if (root.isFull()) {
            Node newRoot = new Node();
            newRoot.children[0] = root;
            splitChild(newRoot, 0);
            root = newRoot;
        }
        insertNotFull(root, key);
    }

    private void insertNotFull(Node node, long key) {
        if (node.isLeaf()) {
            node.insertKey(key);
        } else {
            int i = node.numKeys - 1;
            while (i >= 0 && key < node.keys[i]) i--;
            i++;

            // Wenn das Zielkind voll ist, spalte es vor dem Abstieg
            if (node.children[i].isFull()) {
                splitChild(node, i);
                if (key > node.keys[i]) i++;
            }
            insertNotFull(node.children[i], key);
        }
    }

    /**
     * Spaltet einen vollen 4-Knoten in zwei 2-Knoten auf.
     * Das mittlere Element wandert nach oben in den Elternknoten.
     */
    private void splitChild(Node parent, int index) {
        Node fullNode = parent.children[index];
        Node newNode = new Node();

        // Das mittlere Element (Index 1) wandert hoch
        long midKey = fullNode.keys[1];

        // Das rechte Element wandert in den neuen Knoten
        newNode.keys[0] = fullNode.keys[2];
        newNode.numKeys = 1;

        // Kinder umverteilen, falls es kein Blatt ist
        if (!fullNode.isLeaf()) {
            newNode.children[0] = fullNode.children[2];
            newNode.children[1] = fullNode.children[3];
            fullNode.children[2] = null;
            fullNode.children[3] = null;
        }

        fullNode.numKeys = 1; // fullNode behält nur das linke Element

        // Den Elternknoten aktualisieren
        for (int j = parent.numKeys; j > index; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[index + 1] = newNode;

        for (int j = parent.numKeys - 1; j >= index; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }
        parent.keys[index] = midKey;
        parent.numKeys++;
    }
}