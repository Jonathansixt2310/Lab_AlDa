public class HeapsortDemo {

    /**
     * Hauptmethode für Heapsort (Folie 23)
     */
    public static void heapsort(int[] a) {
        // 1. Das Array in einen Heap verwandeln (Heapify)
        heapify(a);

        // 2. Anzahl der verbleibenden Elemente im Heap-Bereich
        int size = a.length;

        // 3. Wiederholt das größte Element (Wurzel) entfernen
        for (int i = a.length - 1; i > 0; i--) {
            // Tausche die Wurzel (Maximum) an die Position i
            swap(a, 0, i);

            // Verkleinere den Heap-Bereich
            size--;

            // Downward pass an der Wurzel, um Heap-Eigenschaft wiederherzustellen
            downwardPass(a, 0, size);
        }
    }

    /**
     * Verwandelt das Array in einen Max-Heap (Folie 22: Bottom-up Technik)
     */
    private static void heapify(int[] a) {
        // Starte beim untersten Knoten, der Kinder hat, und arbeite dich hoch
        for (int i = (a.length / 2) - 1; i >= 0; i--) {
            downwardPass(a, i, a.length);
        }
    }

    /**
     * Max-Heap Downward Pass (angepasst an Heapsort-Logik)
     */
    private static void downwardPass(int[] a, int k, int size) {
        int left = 2 * k + 1; // Linkes Kind (Folie 17)

        while (left < size) {
            int larger = left;
            int right = 2 * k + 2; // Rechtes Kind

            // Bestimme das größere der beiden Kinder
            if (right < size && a[right] > a[left]) {
                larger = right;
            }

            // Wenn das Kind größer als der Elternknoten ist: tauschen
            if (a[larger] > a[k]) {
                swap(a, k, larger);
                k = larger; // Eine Ebene tiefer gehen
                left = 2 * k + 1;
            } else {
                break; // Korrekte Position gefunden
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        int[] data = {15, 12, 8, 9, 7, 4, 5};
        heapsort(data);

        System.out.println("Sortiertes Array:");
        for (int val : data) System.out.print(val + " ");
    }
}