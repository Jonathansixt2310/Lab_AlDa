import java.util.Arrays;

public class SelectionSort {

    public static void selectionSort(int[] a) {

        // Äußere Schleife: Der Bereich a[0]...a[i-1] ist bereits sortiert.
        // Wir suchen nun den passenden Wert für Position i.
        // Die Schleife läuft bis a.length - 1[cite: 556].
        for (int i = 0; i < a.length - 1; i++) {

            // Wir nehmen an, das aktuelle Element sei das kleinste
            int minValue = a[i]; // aktuelles Element ist das kleinste
            int minIndex = i;    // Index des kleinsten Wertes

            // Innere Schleife: Suche das wirkliche Minimum im Rest des Arrays (ab i+1)
            for (int j = i + 1; j < a.length; j++) {

                // Wenn wir einen kleineren Wert finden
                if (a[j] < minValue) {
                    minValue = a[j];   // speichern des Wertes
                    minIndex = j;      // merken des Index
                }
            }

            // Tausch (Swap): Das gefundene Minimum kommt an die Position i
            // Wir tauschen a[i] mit a[minIndex]
            int temp = a[i];
            a[i] = a[minIndex]; // minIndex auf den gefundenen Index setzen
            a[minIndex] = temp; //
        }
    }

    // Main Methode zum Testen
    public static void main(String[] args) {
        int[] zahlen = {2, 3, 1, 7, 11, 5}; // Beispiel aus Folie 5 [cite: 543]

        System.out.println("Vorher:  " + Arrays.toString(zahlen));

        selectionSort(zahlen);

        System.out.println("Nachher: " + Arrays.toString(zahlen));
    }
}