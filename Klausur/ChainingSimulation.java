import java.util.Arrays;
import java.util.LinkedList;

public class ChainingSimulation {

    public static void main(String[] args) {
        // Basis-Setup laut Aufgabe
        int m = 6;

        // Die Hash-Tabelle ist ein Array von verketteten Listen (LinkedList<String>)
        @SuppressWarnings("unchecked")
        LinkedList<String>[] hashTable = new LinkedList[m];

        // Jedes Bucket mit einer leeren Liste initialisieren
        for (int i = 0; i < m; i++) {
            hashTable[i] = new LinkedList<>();
        }

        // Wir nutzen dieselben Beispiel-Schlüssel aus deiner vorherigen Aufgabe
        String[] keysToInsert = {"jo211six", "fi791ede", "Ju351koz", "fe792ede", "Ti351mue", "le871rei", "sa791tra"};

        System.out.println("=== Simulation: Separate Chaining ===");
        System.out.println("Tabellengröße m = " + m);
        System.out.println("Einzufügende Schlüssel: " + Arrays.toString(keysToInsert));
        System.out.println("------------------------------------------------");

        int elementsInserted = 0;
        boolean firstCollisionFound = false;

        // Simulation des Einfügens mit Verkettung
        for (String k : keysToInsert) {
            int hashValue = k.hashCode();
            int bucketIndex = (hashValue & Integer.MAX_VALUE) % m; // h(k) = k mod 6

            System.out.println("Füge Schlüssel \"" + k + "\" ein (Ziel-Bucket: " + bucketIndex + "):");

            // Kollisionserkennung: Wenn die Liste an diesem Index nicht leer ist, kollidiert das Element
            if (!hashTable[bucketIndex].isEmpty()) {
                if (!firstCollisionFound) {
                    System.out.println("  => ERSTE KOLLISION ERKANNT!");
                    System.out.println("     Wann: Beim Einfügen von Schlüssel k = \"" + k + "\"");
                    System.out.println("     Wo:   Im Bucket " + bucketIndex + " (enthält bereits " + hashTable[bucketIndex] + ")");
                    firstCollisionFound = true;
                } else {
                    System.out.println("  (Kollision im Bucket " + bucketIndex + ")");
                }
            }

            // Element einfach an die verkettete Liste anhängen (kein Suchen nach freien Plätzen nötig!)
            hashTable[bucketIndex].add(k);
            elementsInserted++;
            System.out.println("  -> \"" + k + "\" an Liste im Bucket " + bucketIndex + " angehängt.");

            // Aktuellen Zustand der Tabelle ausgeben
            printTableState(hashTable);
            System.out.println();
        }

        // Analyse und Diskussion
        System.out.println("------------------------------------------------");
        System.out.println("=== Analyse und Diskussion ===");

        // 1. Ladefaktor berechnen
        // Bei Chaining darf alpha problemlos größer als 1.0 werden!
        double alpha = (double) elementsInserted / m;
        System.out.printf("Ladefaktor (alpha) = %d / %d = %.2f%n", elementsInserted, m, alpha);

        // 2. Warum ist die Suche im Worst Case hier immer noch schnell?
        System.out.println("\nDiskussion:");
        System.out.println("> Warum ist die Suche im Worst Case hier immer noch schnell?");
        System.out.println("  Streng theoretisch beträgt die Worst-Case-Laufzeit O(N), falls alle Elemente ");
        System.out.println("  in dasselbe Bucket hashen und eine einzige lange Liste bilden.");
        System.out.println("  Da die Gesamtanzahl der Elemente (N = 7) bei dieser Aufgabe jedoch extrem klein ist,");
        System.out.println("  ist selbst die längste denkbare Liste winzig. Eine sequenzielle Suche benötigt maximal ");
        System.out.println("  7 Schritte, was in der Praxis absolut vernachlässigbar und blitzschnell ist.");

        // 3. Welche Zahl garantiert eine weitere Kollision?
        System.out.println("\nZusatzfrage:");
        System.out.println("> Welche Zahl würde als nächstes Element garantiert eine weitere Kollision verursachen?");
        System.out.println("  Antwort: JEDE beliebige Zahl!");
        System.out.println("  Da die Tabelle m = 6 Buckets besitzt und wir bereits 7 Elemente eingefügt haben,");
        System.out.println("  besagt das Schubfachprinzip (Pigeonhole Principle), dass mindestens ein Bucket ");
        System.out.println("  ohnehin schon mehr als ein Element enthalten muss (bzw. kein Bucket mehr unberührt ist).");
        System.out.println("  Egal welchen Hashwert ein neues Element liefert (0 bis 5) – es trifft garantiert ");
        System.out.println("  auf ein Bucket, in dem bereits mindestens ein Element liegt. Jedes neue Element ");
        System.out.println("  verursacht ab jetzt also unweigerlich eine Kollision.");
    }

    // Hilfsmethode zur sauberen Formatierung der Listen-Tabelle
    private static void printTableState(LinkedList<String>[] hashTable) {
        System.out.print("  Aktuelle Tabelle: [");
        for (int i = 0; i < hashTable.length; i++) {
            System.out.print(i + ":" + hashTable[i]);
            if (i < hashTable.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}