public class Fakultät {
    public static int fakultät(int n) {
        // 1. Die Abbruchbedingung (Base Case)
        // Wenn wir unten angekommen sind, geben wir 1 zurück.
        if (n == 0) {
            return 1;
        }

        // 2. Der rekursive Aufruf (Recursive Step)
        // Wir rufen die Methode selbst mit einem kleineren Wert auf.
        return n * fakultät(n - 1);
    }

    // --- Die Main-Methode zum Testen ---
    public static void main(String[] args) {

        // 1. Einfacher Testfall: 5! (Erwartet: 120)
        int eingabe = 5;
        int ergebnis = fakultät(eingabe);

        System.out.println("Berechnung für " + eingabe + "!");
        System.out.println("Ergebnis: " + ergebnis);
        System.out.println("-------------------------");

        // 2. Test der Abbruchbedingung (Basisfälle)
        System.out.println("Fakultät von 1: " + fakultät(1)); // Sollte 1 sein
        System.out.println("Fakultät von 0: " + fakultät(0)); // Sollte 1 sein
        System.out.println("-------------------------");

        // 3. Ein größerer Wert (Wachstum demonstrieren)
        // 10! = 3.628.800
        System.out.println("Fakultät von 10: " + fakultät(10));

        System.out.println("Fakultät von 0: " + fakultät(0));
        System.out.println("Fakultät von 1: " + fakultät(1));
        System.out.println("Fakultät von 2: " + fakultät(2));
        System.out.println("Fakultät von 3: " + fakultät(3));
        System.out.println("Fakultät von 4: " + fakultät(4));
        System.out.println("Fakultät von 5: " + fakultät(5));

    }
}
