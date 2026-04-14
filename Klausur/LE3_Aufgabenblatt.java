public class LE3_Aufgabenblatt {


    /**
     * Aufgabe 1: Berechnet die Summe der Quadrate von 1 bis n.
     * Formel: n^2 + (n-1)^2 + ... + 1^2
     */
    public static int stones(int n) {
        // Base case
        if (n == 1) {
            return 1;
        }

        // Recursive case
        return n * n + stones(n - 1);
    }

    /**
     * Aufgabe 2: Erhöht den Wert rekursiv um 5, bis er > 25 ist,
     * und gibt dann die Differenz zu 25 zurück.
     */
    public static int rec(int a) {
        if (a > 25) {
            return a - 25;
        }

        return rec(a + 5);
    }

    // Aufgabe 4
    public static int power(int a, int b) {
        if (b == 0) {
            return 1;
        }

        return a * power(a,b-1);
    }

    public static void main(String[] args) {
        // Test für Aufgabe 1
        int ergebnisStones = stones(8);
        System.out.println("Ergebnis von stones(8): " + ergebnisStones);

        // Test für Aufgabe 2
        int ergebnisRec = rec(24);
        System.out.println("Ergebnis von rec(24): " + ergebnisRec);

        // Test für Aufgabe 3
        System.out.println(power(2,2));
        System.out.println(power(10,20));
        System.out.println(power(2,5));

    }
}
