public class search {
    //Aufgabe Teil 1a
    public static int linearSearch(int[] a, int val) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == val) {
                return i;
            }
        }
        return -1;
    }
}
