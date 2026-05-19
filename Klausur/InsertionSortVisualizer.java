public class InsertionSortVisualizer {

    public static void main(String[] args) {
        // Ein Array mit 8 Elementen
        int[] arr = {7, 3, 5, 1, 9, 2, 4, 6};

        System.out.println("Start-Array:");
        printArray(arr, 0, -1, -1);
        System.out.println("--------------------------------------------------");

        // Insertion Sort Algorithmus
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            System.out.println("\nSchritt " + i + ": Nehme Element [" + key + "] und suche die richtige Position.");

            // Elemente verschieben, die größer als der key sind
            while (j >= 0 && arr[j] > key) {
                System.out.println("  -> " + arr[j] + " ist größer als " + key + ", verschiebe " + arr[j] + " nach rechts.");
                arr[j + 1] = arr[j];
                printArray(arr, i, j + 1, j); // Zeige das Array während der Verschiebung
                j = j - 1;
            }

            // Key an der gefundenen Position einfügen
            arr[j + 1] = key;
            System.out.println("  -> Setze [" + key + "] an Index " + (j + 1) + " ein.");
            printArray(arr, i, j + 1, -1);
            System.out.println("--------------------------------------------------");
        }

        System.out.println("\nSortiertes Array (Fertig!):");
        printArray(arr, arr.length - 1, -1, -1);
    }

    /**
     * Hilfsmethode zur formatierten Ausgabe des Arrays.
     * @param arr Das Array
     * @param sortedBoundary Der Index bis zu dem das Array aktuell als "sortiert" gilt
     * @param highlightIndex1 Index des ersten hervorzuhebenden Elements
     * @param highlightIndex2 Index des zweiten hervorzuhebenden Elements
     */
    private static void printArray(int[] arr, int sortedBoundary, int highlightIndex1, int highlightIndex2) {
        for (int i = 0; i < arr.length; i++) {
            // Hervorhebung für bewegte Elemente
            if (i == highlightIndex1 || i == highlightIndex2) {
                System.out.print("[" + arr[i] + "] ");
            } else {
                System.out.print(" " + arr[i] + "  ");
            }

            // Trennlinie für den sortierten Bereich
            if (i == sortedBoundary && i < arr.length - 1) {
                System.out.print("| ");
            }
        }
        System.out.println();
    }
}