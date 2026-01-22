import java.util.Arrays;

public class InsertionSort {

    public static void insertionSort(int[] a) {
        // Äußere Schleife: Geht das Array von links nach rechts durch.
        // Wir starten bei Index 1, da ein einzelnes Element (Index 0)
        // bereits als sortiert gilt
        for (int i = 1; i < a.length; i++) {

            int j = i;

            // Innere Schleife: Das aktuelle Element a[j] wird so lange
            // nach links "getauscht", bis es an der richtigen Stelle ist.
            // Bedingung: Solange der linke Nachbar größer ist als das aktuelle Element.
            while (j > 0 && a[j - 1] > a[j]) {
                // Tauschvorgang (Swap) [cite: 143-149]
                int temp = a[j - 1];
                a[j - 1] = a[j];
                a[j] = temp;

                // Wir wandern mit dem Element weiter nach links
                j--;
            }
        }
    }

    public static void main(String[] args) {
        int[] zahlen = {3, 9, 1, 2, 5}; // Beispiel von Folie 9
        System.out.println("Vorher:  " + Arrays.toString(zahlen));

        insertionSort(zahlen);

        System.out.println("Nachher: " + Arrays.toString(zahlen));
    }
}