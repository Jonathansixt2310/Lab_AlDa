import java.util.*;

public class StringRadixSort {

    public static void sort(String[] a) {
        String[] aux = new String[a.length];
        sort(a, 0, a.length - 1, 0, aux);
    }

    // Hilfsmethode zur Bestimmung des Zeichens an Index d
    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1; // Ende des Strings erreicht
    }

    private static void sort(String[] a, int lo, int hi, int d, String[] aux) {
        // Basisfall: Nur ein Element oder keine Zeichen mehr
        if (hi <= lo) return;

        // Bins für 256 ASCII-Zeichen + 1 für das String-Ende (-1)
        int R = 256;
        int[] count = new int[R + 2];

        // 1. Frequenzen zählen (Häufigkeit der Buchstaben an Position d)
        for (int i = lo; i <= hi; i++) {
            count[charAt(a[i], d) + 2]++;
        }

        // 2. Kumulierte Häufigkeiten berechnen (Startpositionen der Bins)
        for (int r = 0; r < R + 1; r++) {
            count[r + 1] += count[r];
        }

        // 3. In Hilfs-Array (aux) verteilen
        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }

        // 4. Zurückkopieren ins Original-Array
        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }

        // 5. Rekursiv für jeden Buchstaben-Bin weitermachen (nächste Stelle d+1)
        for (int r = 0; r < R; r++) {
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
        }
    }

    public static void main(String[] args) {
        // Beispiel aus Folie 46 [cite: 756-766]
        String[] animals = {
                "alligator", "zebra", "aardvark", "echidna",
                "elephant", "squid", "armadillo", "snake",
                "snail", "squirrel", "alpaca"
        };

        sort(animals);

        for (String s : animals) {
            System.out.println(s);
        }
    }
}