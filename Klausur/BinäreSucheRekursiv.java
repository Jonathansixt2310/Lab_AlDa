public class BinäreSucheRekursiv {

    // --- 1. Die öffentliche Methode (Wrapper) ---
    // Damit der Nutzer nicht '0' und 'length-1' selbst angeben muss.
    public static int suche(int[] array, int gesucht) {
        return sucheRekursiv(array, gesucht, 0, array.length - 1);
    }

    // --- 2. Die rekursive Logik ---
    private static int sucheRekursiv(int[] array, int gesucht, int links, int rechts) {

        // ABBRUCHBEDINGUNG 1: Nicht gefunden
        // Wenn der linke Rand am rechten vorbeigezogen ist, gibt es keinen Suchbereich mehr.
        if (links > rechts) {
            return -1; // -1 signalisiert "Nicht gefunden"
        }

        // MITTE BERECHNEN
        // (links + rechts) / 2 würde auch gehen, aber diese Formel verhindert
        // einen Integer-Overflow bei sehr großen Arrays.
        int mitte = links + (rechts - links) / 2;

        // ABBRUCHBEDINGUNG 2: Gefunden!
        if (array[mitte] == gesucht) {
            return mitte; // Wir geben den Index zurück
        }

        // REKURSIVER SCHRITT
        if (array[mitte] > gesucht) {
            // Der gesuchte Wert ist KLEINER als die Mitte -> Wir suchen LINKS weiter
            // Wichtig: 'rechts' ist jetzt 'mitte - 1' (Mitte selbst haben wir ja schon geprüft)
            return sucheRekursiv(array, gesucht, links, mitte - 1);
        } else {
            // Der gesuchte Wert ist GRÖSSER als die Mitte -> Wir suchen RECHTS weiter
            // Wichtig: 'links' ist jetzt 'mitte + 1'
            return sucheRekursiv(array, gesucht, mitte + 1, rechts);
        }
    }

    // --- Main zum Testen ---
    public static void main(String[] args) {
        // WICHTIG: Das Array muss sortiert sein!
        int[] zahlen = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        int gesucht = 23;

        int ergebnis = suche(zahlen, gesucht);

        if (ergebnis != -1) {
            System.out.println("Gefunden an Index: " + ergebnis); // Sollte 5 sein
        } else {
            System.out.println("Nicht gefunden.");
        }
    }
}