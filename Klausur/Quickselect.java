public class Quickselect {

    public static int quickselect(int[] a, int left, int right, int k) {
        // Basisfall für ein einzelnes Element
        if (left == right) {
            return a[left];
        }

        // Partitionierungsschritt
        // (Verwendet dieselbe partition-Methode wie Quicksort)
        int pivotIndex = partition(a, left, right);

        // Zeile 11: Rekursiv nur eine Seite durchsuchen [cite: 847]
        if (pivotIndex == k) {
            // Pivot ist genau an der gesuchten Stelle k
            return a[pivotIndex];
        } else if (pivotIndex > k) {
            // Suche im linken Teil weiter
            return quickselect(a, left, pivotIndex - 1, k); // [cite: 855-857]
        } else {
            // Suche im rechten Teil weiter
            return quickselect(a, pivotIndex + 1, right, k); // [cite: 859]
        }
    }

    // Partitionierungs-Logik
    public static int partition(int[] a, int left, int right) {
        int pivot = a[right];
        int swap = left;

        for (int i = left; i < right; i++) {
            if (a[i] < pivot) {
                exchange(a, i, swap);
                swap++;
            }
        }
        exchange(a, swap, right);
        return swap;
    }

    private static void exchange(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        int[] data = {7, 4, 1, 8, 5, 3, 2, 6};
        int k = 3; // Wir suchen das 4. kleinste Element (Index 3)

        int result = quickselect(data, 0, data.length - 1, k);
        System.out.println("Das Element an Index " + k + " ist: " + result);
    }
}