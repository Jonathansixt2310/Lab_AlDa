public class Fibonacci {
    public int fibonacci(int n) {
        // 1. Abbruchbedingung (Basisfälle)
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        // (Oder kurz: if (n <= 1) return n;)

        // 2. Rekursiver Schritt (Der Doppel-Aufruf)
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
