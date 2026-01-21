public class LineareSuche {
    // Die Methode für die lineare Suche
    // Rückgabewert: Der Index des gefundenen Elements oder -1, wenn es fehlt.
    public static int lineare_suche(int[] daten, int gesuchterWert) {

        // Wir gehen jeden Index von 0 bis zum Ende durch
        for (int i = 0; i < daten.length; i++) {

            // Prüfung: Ist das aktuelle Element das gesuchte?
            if (daten[i] == gesuchterWert) {
                return i; // Gefunden! Wir geben die Position zurück.
            }
        }

        // Wenn die Schleife durchläuft, ohne etwas zu finden:
        return -1; // Standard-Code für "Nicht gefunden"
    }

    public static void main(String[] args) {
        // Beispiel-Daten (müssen NICHT sortiert sein)
        int[] zahlen = {10, 50, 20, 70, 80, 30};
        int ziel = 30;

        int ergebnis = lineare_suche(zahlen, ziel);

        if (ergebnis != -1) {
            System.out.println("Element gefunden an Index: " + ergebnis);
        } else {
            System.out.println("Element nicht in der Liste.");
        }
    }
}
