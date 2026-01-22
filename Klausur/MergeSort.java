import java.util.Arrays;

public class MergeSort {

    public static int[] mergesort(int[] a) {
        // Base case: Ein einzelnes Element ist bereits sortiert
        if (a.length <= 1) {
            return a;
        }

        // Neue Arrays für die linke und rechte Hälfte erstellen
        int mid = a.length / 2;
        int[] left = Arrays.copyOfRange(a, 0, mid);
        int[] right = Arrays.copyOfRange(a, mid, a.length);

        // Rekursives Sortieren der Hälften
        left = mergesort(left);
        right = mergesort(right);

        // Zusammenführen (Merge) der sortierten Hälften
        return merge(left, right);
    }

    private static int[] merge(int[] left, int[] right) {
        int[] out = new int[left.length + right.length];
        int lPtr = 0, rPtr = 0, outPtr = 0;

        // Kopiere das jeweils kleinere Element in das Ergebnis-Array
        while (lPtr < left.length && rPtr < right.length) {
            if (left[lPtr] < right[rPtr]) {
                out[outPtr] = left[lPtr];
                lPtr++;
            } else {
                out[outPtr] = right[rPtr];
                rPtr++;
            }
            outPtr++;
        }

        // Kopiere verbleibende Elemente der linken Seite
        while (lPtr < left.length) {
            out[outPtr++] = left[lPtr++];
        }

        // Kopiere verbleibende Elemente der rechten Seite
        while (rPtr < right.length) {
            out[outPtr++] = right[rPtr++];
        }

        return out;
    }

    public static void main(String[] args) {
        int[] data = {6, 3, 4, 1, 7, 8, 5, 2};
        int[] sorted = mergesort(data);
        System.out.println(Arrays.toString(sorted));
    }
}