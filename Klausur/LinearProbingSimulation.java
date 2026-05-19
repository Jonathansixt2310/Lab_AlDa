import java.util.Arrays;

public class LinearProbingSimulation {

    private static int m = 5;
    private static String[] hashTable = new String[m];
    private static int elementsInserted = 0;
    private static boolean firstCollisionFound = false;

    // Der Schwellenwert für das Resizing (75% Auslastung)
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    public static void main(String[] args) {
        Arrays.fill(hashTable, null);

        // Deine Beispiel-Schlüssel
        String[] keysToInsert = {"jo211six", "fi791ede", "Ju351koz", "fe792ede", "Ti351mue", "le871rei", "sa791tra"};

        System.out.println("=== Simulation: Linear Probing mit Load Factor Threshold (0.75) ===");
        System.out.println("Start-Tabellengröße m = " + m);
        System.out.println("Einzufügende Schlüssel: " + Arrays.toString(keysToInsert));
        System.out.println("------------------------------------------------");

        // Simulation des Einfügens
        for (String k : keysToInsert) {
            // Prüfung: Würde das neue Element die 75%-Grenze überschreiten?
            // Formel: (Aktuelle Elemente + 1) / Tabellengröße > 0.75
            double predictedLoadFactor = (double) (elementsInserted + 1) / m;

            if (predictedLoadFactor > LOAD_FACTOR_THRESHOLD) {
                System.out.printf("!!! SCHWELLENWERT ERREICHT !!! Ladefaktor würde %.2f betragen (> %.2f)%n",
                        predictedLoadFactor, LOAD_FACTOR_THRESHOLD);
                System.out.println("-> Starte Verdopplung auf m = " + (m * 2));
                resizeAndRehash();
            }

            insert(k);
            System.out.println("  Aktuelle Tabelle: " + Arrays.toString(hashTable));
            System.out.println();
        }

        // Analyse und Diskussion
        System.out.println("------------------------------------------------");
        System.out.println("=== Analyse und Diskussion ===");
        double alpha = (double) elementsInserted / m;
        System.out.println("Finale Tabellengröße m = " + m);
        System.out.println("Eingefügte Elemente = " + elementsInserted);
        System.out.printf("Finaler Ladefaktor (alpha) = %.2f%n", alpha);
    }

    // Methode zum Einfügen eines einzelnen Schlüssels
    private static void insert(String k) {
        int hashValue = k.hashCode();
        int originalHash = (hashValue & Integer.MAX_VALUE) % m;

        int currentPos = originalHash;
        System.out.println("Füge Schlüssel \"" + k + "\" ein (bevorzugter Bucket: " + originalHash + "):");

        // Solange das Bucket besetzt ist, liegt eine Kollision vor
        while (hashTable[currentPos] != null) {
            if (!firstCollisionFound) {
                System.out.println("  => ERSTE KOLLISION ERKANNT!");
                System.out.println("     Wann: Beim Einfügen von Schlüssel k = \"" + k + "\"");
                System.out.println("     Wo:   Im Bucket " + currentPos);
                firstCollisionFound = true;
            }

            // Lineares Sondieren
            currentPos = (currentPos + 1) % m;
        }

        // Freier Platz gefunden
        hashTable[currentPos] = k;
        elementsInserted++;
        System.out.println("  -> Schlüssel \"" + k + "\" erfolgreich in Bucket " + currentPos + " platziert.");
    }

    // Methode zur Verdopplung und zum Rehashing
    private static void resizeAndRehash() {
        String[] oldTable = hashTable;
        int oldM = m;

        // Größe verdoppeln
        m = oldM * 2;
        hashTable = new String[m];
        Arrays.fill(hashTable, null);

        // Zähler zurücksetzen, da insert() ihn wieder hochzählt
        elementsInserted = 0;

        System.out.println("  [Resize] Rehashing der alten Elemente in die neue Tabelle...");

        // Alle alten Elemente neu hashen
        for (int i = 0; i < oldM; i++) {
            if (oldTable[i] != null) {
                insert(oldTable[i]);
            }
        }
        System.out.println("  [Resize] Rehashing abgeschlossen.\n");
    }
}