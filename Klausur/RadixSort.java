import java.util.*;

public class RadixSort {

    public static void radixSort(int[] a) {
        if (a.length == 0) return;

        // Finde die maximale Zahl, um die Anzahl der Stellen (d) zu bestimmen
        int max = a[0];
        for (int val : a) {
            if (val > max) max = val;
        }

        // Führe für jede Stelle (Einer, Zehner, Hunderter...) einen Pass aus
        // Entspricht O(d * n)
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(a, exp);
        }
    }

    private static void countingSortByDigit(int[] a, int exp) {
        int n = a.length;
        int[] output = new int[n];
        int[] count = new int[10]; // Bins für Ziffern 0-9 [cite: 749, 750]

        // Speichere die Häufigkeit der Ziffern im aktuellen Bin
        for (int i = 0; i < n; i++) {
            count[(a[i] / exp) % 10]++;
        }

        // Ändere count[i], sodass es die tatsächliche Position im output anzeigt
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Baue das Output-Array (stabil von hinten nach vorne)
        for (int i = n - 1; i >= 0; i--) {
            int digit = (a[i] / exp) % 10;
            output[count[digit] - 1] = a[i];
            count[digit]--;
        }

        // Kopiere das Ergebnis zurück in das Original-Array
        System.arraycopy(output, 0, a, 0, n);
    }

    public static void main(String[] args) {
        int[] data = {170, 45, 75, 90, 802, 24, 2, 66};
        radixSort(data);
        System.out.println(Arrays.toString(data));
    }
}