/**
 * Implementierung eines Min-Heaps basierend auf Kapitel 19.
 * Alle Methoden entsprechen exakt der Vorlesung.
 */
public class MinHeap<K extends Comparable<K>> {

    private K[] heap;
    private int size;

    @SuppressWarnings("unchecked")
    public MinHeap(int capacity) {
        this.heap = (K[]) new Comparable[capacity];
        this.size = 0;
    }

    // --- Hilfsmethoden zur Indexberechnung (Folie 17) ---
    private int parent(int k) {
        return (k - 1) / 2; // p = floor((k-1)/2) [cite: 794, 795]
    }

    private int leftChild(int k) {
        return 2 * k + 1; // 2k + 1 [cite: 790, 791]
    }

    private int rightChild(int k) {
        return 2 * k + 2; // 2k + 2 [cite: 790, 791]
    }

    // --- Upward Pass Implementation (Folie 18) ---
    public void upwardPass(int k) {
        while (k > 0) {
            int parent = parent(k);

            // Compare to parent and swap upwards if necessary
            if (this.heap[k].compareTo(this.heap[parent]) < 0) {
                swap(k, parent);
                k = parent; // Move up
            } else {
                break; // k is in the correct position, end
            }
        }
    }

    // --- Downward Pass Implementation (Folie 19) ---
    public void downwardPass(int k) {
        int left = leftChild(k);

        while (left < this.size && k >= 0) {
            int smaller = left;

            // If there is a right child, determine which is smaller
            int right = rightChild(k);
            if (right < this.size
                    && this.heap[right].compareTo(this.heap[left]) < 0) {
                smaller = right;
            }

            // Compare the smaller item to item k and swap if necessary
            if (this.heap[smaller].compareTo(this.heap[k]) < 0) {
                swap(k, smaller);

                k = smaller; // Move down and continue
                left = leftChild(k);
            } else {
                break; // k is in correct position, end
            }
        }
    }

    // --- Offering and Polling (Folie 20) ---
    public void offer(K key) {
        // Add the new value at the next open position
        this.size++;
        this.heap[this.size - 1] = key;

        // Upward pass to put the new value into its correct position
        upwardPass(this.size - 1);
    }

    public K poll() {
        if (size == 0) return null;

        // Swap the last item into the root position
        swap(0, this.size - 1);

        // Remove the old root and reduce the heap size
        K result = this.heap[this.size - 1];
        this.size--;

        // Downward pass starting from the root
        downwardPass(0);

        return result;
    }

    // Hilfsmethode zum Tauschen von Elementen (wird in Folie 18, 19, 20 genutzt)
    private void swap(int i, int j) {
        K temp = this.heap[i];
        this.heap[i] = this.heap[j];
        this.heap[j] = temp;
    }
}