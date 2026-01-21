public class BinäreSuche {
    // Die Methode für die Binäre Suche
    // Rückgabewert: Der Index des Elements oder -1.
    public static int suche(int[] daten, int gesuchterWert) {

        int links = 0;                  // Start des Suchbereichs
        int rechts = daten.length - 1;  // Ende des Suchbereichs

        // Solange der Bereich nicht "leer" ist (links darf rechts nicht überholen)
        while (links <= rechts) {

            // 1. Die Mitte berechnen
            int mitte = links + (rechts - links) / 2;

            // 2. Prüfen: Haben wir es gefunden?
            if (daten[mitte] == gesuchterWert) {
                return mitte; // Volltreffer!
            }

            // 3. Wenn nicht: In welcher Hälfte müssen wir weitersuchen?
            if (gesuchterWert < daten[mitte]) {
                // Der Wert ist kleiner als die Mitte -> Wir suchen links weiter.
                // Wir schieben die rechte Grenze nach unten.
                rechts = mitte - 1;
            } else {
                // Der Wert ist größer als die Mitte -> Wir suchen rechts weiter.
                // Wir schieben die linke Grenze nach oben.
                links = mitte + 1;
            }
        }

        // Wenn die Schleife durchläuft und sich 'links' und 'rechts' gekreuzt haben:
        return -1; // Nicht gefunden
    }

    public static void main(String[] args) {
        // ACHTUNG: Dieses Array MUSS sortiert sein!
        int[] sortierteZahlen = {10, 20, 30, 50, 70, 80};
        int ziel = 20;

        int ergebnis = suche(sortierteZahlen, ziel);

        if (ergebnis != -1) {
            System.out.println("Gefunden an Index: " + ergebnis);
        } else {
            System.out.println("Nicht gefunden.");
        }
    }
}
