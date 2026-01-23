import java.util.LinkedList;

public class HashSearchExample {

    // Datenstruktur für ein Schlüssel-Wert-Paar [cite: 338-340]
    static class Entry {
        String key;
        String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Entry>[] table;
    private int capacity;

    @SuppressWarnings("unchecked")
    public HashSearchExample(int capacity) {
        this.capacity = capacity;
        this.table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            this.table[i] = new LinkedList<>();
        }
    }

    // Methode zum Einfügen [cite: 322]
    public void put(String key, String value) {
        int index = Math.abs(key.hashCode()) % capacity;
        table[index].add(new Entry(key, value));
    }

    /**
     * Die Such-Operation (Lookup) [cite: 370-389]
     */
    public String get(String lookupKey) {
        // 1. Schritt: Index berechnen (Hashing + Modulo)
        int index = Math.abs(lookupKey.hashCode()) % capacity;

        System.out.println("Suche nach '" + lookupKey + "' in Bucket " + index);

        // 2. Schritt: Den richtigen Bucket (LinkedList) anspringen [cite: 377]
        LinkedList<Entry> bucket = table[index];

        // 3. Schritt: Lineare Suche innerhalb der Kette (Chain) [cite: 377-379]
        int steps = 0;
        for (Entry entry : bucket) {
            steps++;
            if (entry.key.equals(lookupKey)) {
                System.out.println("  -> Gefunden nach " + steps + " Vergleich(en).");
                return entry.value; // Treffer! [cite: 381]
            }
        }

        System.out.println("  -> Nicht gefunden.");
        return null; // Misserfolg [cite: 388]
    }

    public static void main(String[] args) {
        // Wir erstellen eine kleine Tabelle, um absichtlich Kollisionen zu provozieren
        HashSearchExample myMap = new HashSearchExample(3);

        myMap.put("Alice", "Notenspiegel: 1.0");
        myMap.put("Bob", "Notenspiegel: 2.3");
        myMap.put("Charlie", "Notenspiegel: 1.7");

        System.out.println("--- Suche beginnt ---");
        myMap.get("Alice");
        myMap.get("Charlie");
        myMap.get("Unbekannt");
    }
}