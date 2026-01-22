import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecursiveReverse {

    public static List<Integer> reverse(List<Integer> a) {

        // Base case (Abbruchbedingung)
        // Wenn die Liste leer ist oder nur 1 Element hat, ist sie bereits umgedreht.
        if (a.size() <= 1) {
            return a;
        }

        // Get first element
        // Wir merken uns das erste Element (den "Kopf").
        int first = a.get(0);

        // Extract sublist
        // Wir nehmen den Rest der Liste (ab Index 1 bis zum Ende).
        // WICHTIG: 'new ArrayList' erstellt eine echte Kopie, damit wir sie verändern können.
        List<Integer> rest = new ArrayList<>(a.subList(1, a.size()));

        // Recursive operation
        // Wir vertrauen darauf, dass der rekursive Aufruf den Rest für uns umdreht.
        rest = reverse(rest);

        // Append first element to reversed sublist
        // Wir hängen das gemerkte erste Element ganz ans Ende der umgedrehten Rest-Liste.
        rest.add(first);

        // Zeile 18: Return result
        return rest;
    }

    // Main-Methode zum Testen
    public static void main(String[] args) {
        // Erstellen einer veränderbaren Liste
        List<Integer> meineListe = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        System.out.println("Vorher:  " + meineListe);

        List<Integer> ergebnis = reverse(meineListe);

        System.out.println("Nachher: " + ergebnis);
    }
}