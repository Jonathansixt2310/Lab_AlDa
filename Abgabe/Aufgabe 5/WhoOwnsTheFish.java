/**
 * Who Owns the Fish?
 */

import java.util.Arrays;

public class WhoOwnsTheFish {

    // Constants for each possible value
    final int NONE = -1;
    final int NATIONALITY = 0;
    final int COLOR = 1;
    final int DRINKS = 2;
    final int SMOKES = 3;
    final int PET = 4;

    final int NORWEGIAN = 0;
    final int DANE = 1;
    final int BRIT = 2;
    final int GERMAN = 3;
    final int SWEDE = 4;

    // And so forth for the other types
    // Werte für Farben
    final int RED = 0;
    final int GREEN = 1;
    final int WHITE = 2;
    final int YELLOW = 3;
    final int BLUE = 4;

    // Werte für Getränke
    final int TEA = 0;
    final int COFFEE = 1;
    final int MILK = 2;
    final int BEER = 3;
    final int WATER = 4;

    // Werte für Rauchen
    final int PALLMALL = 0;
    final int DUNHILL = 1;
    final int BLENDS = 2;
    final int BLUEMASTER = 3;
    final int PRINCE = 4;

    // Werte für Haustiere
    final int DOG = 0;
    final int BIRDS = 1;
    final int CATS = 2;
    final int HORSE = 3;
    final int FISH = 4;
    //*** We'll complete these methods ***//

    /**
     * Check if assigning value to houses[h][a] is valid
     */
    public boolean valid(int[][] houses, int h, int a, int value) {
        // Schritt 1: Eindeutigkeit prüfen (Spalte a darf value nur einmal enthalten)
        for (int i = 0; i < 5; i++) {
            // Überspringe das aktuelle Haus h, prüfe alle anderen
            if (i != h && houses[i][a] == value) {
                return false;
            }
        }

        // Schritt 2: Logische Regeln prüfen
        // Setze den Wert provisorisch
        houses[h][a] = value;

        // Überprüfe alle Regeln
        boolean rulesOk = checkRules(houses);

        //Wert wieder zurücksetzen, damit der Backtracking-State sauber bleibt
        houses[h][a] = NONE;

        return rulesOk;
    }

    //Regeln prüfen
    private boolean checkRules(int[][] houses) {
        // Wir iterieren durch alle Häuser, um direkte Zuordnungen zu prüfen
        for (int i = 0; i < 5; i++) {
            int n = houses[i][NATIONALITY];
            int c = houses[i][COLOR];
            int d = houses[i][DRINKS];
            int s = houses[i][SMOKES];
            int p = houses[i][PET];

            // 1. Der Brite lebt im roten Haus.
            if (n == BRIT && c != NONE && c != RED) return false;
            if (c == RED && n != NONE && n != BRIT) return false;

            // 2. Der Schwede hält Hunde. [cite: 25]
            if (n == SWEDE && p != NONE && p != DOG) return false;
            if (p == DOG && n != NONE && n != SWEDE) return false;

            // 3. Der Däne trinkt Tee. [cite: 26]
            if (n == DANE && d != NONE && d != TEA) return false;
            if (d == TEA && n != NONE && n != DANE) return false;

            // 5. Der Besitzer des grünen Hauses trinkt Kaffee. [cite: 28]
            if (c == GREEN && d != NONE && d != COFFEE) return false;
            if (d == COFFEE && c != NONE && c != GREEN) return false;

            // 6. Die Person, die Pall Mall raucht, hält Vögel. [cite: 29]
            if (s == PALLMALL && p != NONE && p != BIRDS) return false;
            if (p == BIRDS && s != NONE && s != PALLMALL) return false;

            // 7. Der Besitzer des gelben Hauses raucht Dunhill. [cite: 30]
            if (c == YELLOW && s != NONE && s != DUNHILL) return false;
            if (s == DUNHILL && c != NONE && c != YELLOW) return false;

            // 10. Der Mann, der Blue Master raucht, trinkt Bier. [cite: 33]
            if (s == BLUEMASTER && d != NONE && d != BEER) return false;
            if (d == BEER && s != NONE && s != BLUEMASTER) return false;

            // 11. Der Deutsche raucht Prince.
            if (n == GERMAN && s != NONE && s != PRINCE) return false;
            if (s == PRINCE && n != NONE && n != GERMAN) return false;
        }

        // 4. Das grüne Haus steht links vom weißen Haus.
        int greenIndex = findIndex(houses, COLOR, GREEN);
        int whiteIndex = findIndex(houses, COLOR, WHITE);
        if (greenIndex != -1 && whiteIndex != -1) {
            if (greenIndex != whiteIndex - 1) return false;
        }

        // 8. Der Mann, der Blends raucht, lebt neben dem, der Katzen hält. [cite: 31]
        if (!checkNeighborRule(houses, SMOKES, BLENDS, PET, CATS)) return false;

        // 9. Der Mann, der Pferde hält, lebt neben dem, der Dunhill raucht. [cite: 32]
        if (!checkNeighborRule(houses, PET, HORSE, SMOKES, DUNHILL)) return false;

        // 12. Der Norweger lebt neben dem blauen Haus. [cite: 35]
        if (!checkNeighborRule(houses, NATIONALITY, NORWEGIAN, COLOR, BLUE)) return false;

        // 13. Der Mann, der Blends raucht, hat einen Nachbarn, der Wasser trinkt. [cite: 36]
        if (!checkNeighborRule(houses, SMOKES, BLENDS, DRINKS, WATER)) return false;

        return true;
    }

    //Hilfmethode zum prüfen der Nachbarschaften
    private boolean checkNeighborRule(int[][] houses, int attr1, int val1, int attr2, int val2) {
        int idx1 = findIndex(houses, attr1, val1);
        int idx2 = findIndex(houses, attr2, val2);

        if (idx1 != -1 && idx2 != -1) {
            int diff = Math.abs(idx1 - idx2);
            if (diff != 1) return false; // Sie sind nicht nebeneinander
        }
        return true;
    }

    //Sucht nach einem Index mit bestimmten Eigenschaften
    private int findIndex(int[][] houses, int attr, int value) {
        for (int i = 0; i < 5; i++) {
            if (houses[i][attr] == value) return i;
        }
        return -1;
    }

    /**
     * Find the next open position in the houses matrix
     */
    public int[] findNext(int[][] houses) {
        // Durchsuchen Sie das Array houses nach dem ersten Feld, das NONE (-1) enthält.
        for (int h = 0; h < 5; h++) {
            for (int a = 0; a < 5; a++) {
                if (houses[h][a] == NONE) {
                    // Rückgabe: Array mit {HausIndex, AttributIndex}
                    return new int[]{h, a};
                }
            }
        }
        // Wenn keine freien Felder mehr existieren, geben Sie null zurück.
        return null;
    }

    /**
     * Backtracking search
     */
    public void search(int[][] houses) {
        // Basisfall: Rufen Sie findNext auf.
        int[] next = findNext(houses);

        // Wenn das Ergebnis null ist, haben Sie eine Lösung gefunden!
        if (next == null) {
            print(houses);
            System.exit(0); // Beenden Sie das Programm
            return;
        }

        int h = next[0]; // Haus-Index
        int a = next[1]; // Attribut-Index

        // Iterieren Sie durch alle möglichen Werte von 0 bis 4
        for (int val = 0; val <= 4; val++) {
            // 1. Prüfen Sie mit valid(...), ob der Wert zulässig ist.
            if (valid(houses, h, a, val)) {

                // 2. Falls ja: Setzen Sie den Wert
                houses[h][a] = val;

                // 3. Rufen Sie search (houses) rekursiv auf
                search(houses);

                // 4. Backtracking: Setzen Sie das Feld zurück
                houses[h][a] = NONE;
            }
        }
    }

    /**
     * Start the solution process
     */
    public void solve() {
        // Initialisierung: 5 Häuser, 5 Attribute
        int[][] houses = new int[5][5];

        // Alle Werte auf NONE (-1) setzen
        for (int[] house : houses) {
            Arrays.fill(house, NONE);
        }

        // Suche starten
        search(houses);
    }

    public void print(int[][] houses) {
        int indexPerson = -1;
        for (int i = 0; i < 5; i++) {
            System.out.print("  " + (i + 1) + "  | ");
            for (int j = 0; j < 5; j++) {
                int val = houses[i][j];
                System.out.print(val + " ");
                if (j == 4 && val == 4) {
                    indexPerson = i;
                }
            }
            System.out.println();
        }
        if (indexPerson != -1) {
            int valNatio = houses[indexPerson][0];
            String nationalityName = "";
            switch (valNatio) {
                case 0:
                    nationalityName = "Norweger";
                    break;
                case 1:
                    nationalityName = "Däne";
                    break;
                case 2:
                    nationalityName = "Brite";
                    break;
                case 3:
                    nationalityName = "Deutscher";
                    break;
                case 4:
                    nationalityName = "Schwede";
                    break;
                default:
                    nationalityName = "Unbekannt";
                    break;
            }
            System.out.println("Der Besitzer des Fisches ist der: " + nationalityName);
        }
    }

    public static void main(String[] args) {
        WhoOwnsTheFish who = new WhoOwnsTheFish();
        who.solve();
    }
}