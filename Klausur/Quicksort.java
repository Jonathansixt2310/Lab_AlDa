public class Quicksort {

    // Hauptmethode zum Starten des Sortiervorgangs
    public static void quicksort(int[] a, int left, int right) {
        // Base case: Wenn der Bereich nur ein Element oder kein Element hat
        if (left >= right) {
            return;
        }

        // Partitionierung des Bereichs und Erhalt des Pivot-Index
        int pivotIndex = partition(a, left, right);

        // Rekursives Sortieren der linken Seite (vor dem Pivot)
        quicksort(a, left, pivotIndex - 1);

        // Rekursives Sortieren der rechten Seite (nach dem Pivot)
        quicksort(a, pivotIndex + 1, right);
    }

    // Die Partitionierungs-Logik
    public static int partition(int[] a, int left, int right) {
        // Das letzte Element wird standardmäßig als Pivot gewählt
        int pivot = a[right];

        // Der 'swap'-Index verfolgt, wo das nächste kleinere Element hin soll
        int swap = left;

        // Scannen des Bereichs von links nach rechts
        for (int i = left; i < right; i++) {
            // Wenn das aktuelle Element kleiner als das Pivot ist
            if (a[i] < pivot) {
                // Tausche es an die vordere Position
                exchange(a, i, swap);
                // Erhöhe den Zielindex für den nächsten Tausch
                swap++;
            }
        }

        // Am Ende das Pivot-Element an seine endgültige Position tauschen
        exchange(a, swap, right);

        // Den Index des Pivots zurückgeben [cite: 276]
        return swap;
    }

    // Hilfsmethode zum Vertauschen zweier Elemente im Array
    private static void exchange(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        int[] data = {7, 4, 1, 8, 5, 3, 2, 6}; // Beispiel aus Folie 19 [cite: 287-294]
        quicksort(data, 0, data.length - 1);

        // Ausgabe des sortierten Arrays
        for (int n : data) {
            System.out.print(n + " ");
        }
    }
}