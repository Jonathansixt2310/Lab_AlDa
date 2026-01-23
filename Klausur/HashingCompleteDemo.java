import java.util.LinkedList;
import java.util.Objects;

/**
 * Gesamtdemonstration von Hashing und Hash-Tabellen.
 * Kombiniert die Konzepte aus Kapitel 14 & 15.
 */
public class HashingCompleteDemo {

    // ==========================================================
    // 1. Die Schlüssel-Klasse (Orientiert an Kapitel 14)
    // ==========================================================
    public static class Student {
        private final int id;
        private final String name;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * hashCode: Erzeugt einen "Fingerabdruck" des Objekts[cite: 8, 170].
         * Nutzt eine Polynom-Akkumulation wie in Folie 5 beschrieben[cite: 37].
         */
        @Override
        public int hashCode() {
            // Objects.hash nutzt intern die Primzahl 31 zur Berechnung[cite: 37].
            return Objects.hash(id, name);
        }

        /**
         * equals: Notwendig für den Vergleich innerhalb einer Chain (Kollision)[cite: 379].
         * Zwei Objekte mit gleichem Inhalt MÜSSEN den gleichen hashCode haben[cite: 28].
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Student other = (Student) obj;
            return id == other.id && Objects.equals(name, other.name);
        }

        @Override
        public String toString() {
            return "Student[ID=" + id + ", Name=" + name + "]";
        }
    }

    // ==========================================================
    // 2. Die Hash-Tabellen Implementierung
    // ==========================================================
    public static class ChainedHashTable<K, V> {

        // Interner Knoten zur Speicherung der Key-Value Paare [cite: 338-340]
        private class HashNode {
            K key;
            V value;
            HashNode(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private LinkedList<HashNode>[] table;
        private int size = 0; // Anzahl n der gespeicherten Elemente

        @SuppressWarnings("unchecked")
        public ChainedHashTable(int capacity) {
            // Erzeugt ein Array von verketteten Listen (Buckets) [cite: 339, 351]
            this.table = new LinkedList[capacity];
            for (int i = 0; i < capacity; i++) {
                this.table[i] = new LinkedList<>(); // [cite: 359]
            }
        }

        /**
         * Fügt ein Paar hinzu oder ersetzt den Wert bei doppeltem Key [cite: 212-213].
         */
        public void put(K key, V value) {
            int bucketIndex = getBucketIndex(key);

            // Suche nach existierendem Key zur Aktualisierung [cite: 213]
            for (HashNode node : table[bucketIndex]) {
                if (node.key.equals(key)) {
                    node.value = value;
                    return;
                }
            }

            // Ansonsten: Neues Element in die Kette einfügen [cite: 322]
            table[bucketIndex].add(new HashNode(key, value));
            size++;

            // Last-Management: Rebalancing bei Load Factor > 0.75 [cite: 432, 436]
            if ((double) size / table.length > 0.75) {
                rehash();
            }
        }

        /**
         * Sucht den Wert zu einem Schlüssel[cite: 214, 370].
         */
        public V get(K key) {
            int bucketIndex = getBucketIndex(key);
            // Kette am berechneten Index linear durchsuchen [cite: 375-377]
            for (HashNode node : table[bucketIndex]) {
                if (node.key.equals(key)) { // [cite: 379]
                    return node.value; // [cite: 381]
                }
            }
            return null; // Nicht gefunden [cite: 388]
        }

        private int getBucketIndex(K key) {
            // h(k) % m: Hash-Wert auf Tabellengröße m begrenzen 
            return Math.abs(key.hashCode()) % table.length;
        }

        @SuppressWarnings("unchecked")
        private void rehash() {
            // Tabelle vergrößern und alle Einträge neu verteilen [cite: 433-434]
            System.out.println("--- Rebalancing (Load Factor > 0.75) ---");
            LinkedList<HashNode>[] oldTable = table;
            table = new LinkedList[oldTable.length * 2];
            for (int i = 0; i < table.length; i++) table[i] = new LinkedList<>();

            size = 0; // Wird durch die put-Aufrufe neu gezählt
            for (LinkedList<HashNode> bucket : oldTable) {
                for (HashNode node : bucket) {
                    put(node.key, node.value);
                }
            }
        }
    }

    // ==========================================================
    // 3. Test-Logik (Main)
    // ==========================================================
    public static void main(String[] args) {
        // Erstellung einer Tabelle mit Startkapazität 4
        ChainedHashTable<Student, String> grades = new ChainedHashTable<>(4);

        // Studenten anlegen
        Student alice = new Student(101, "Alice");
        Student bob = new Student(102, "Bob");

        // Daten speichern (put)
        grades.put(alice, "1.0");
        grades.put(bob, "2.3");

        // Daten abrufen (get) mit einem neuen Objekt, das inhaltlich gleich ist
        Student lookup = new Student(101, "Alice");
        System.out.println("Suche nach: " + lookup);
        System.out.println("Gefundene Note: " + grades.get(lookup));

        // Demonstration des Rebalancing
        System.out.println("\nFüge weitere Studenten hinzu, um Rehash auszulösen...");
        grades.put(new Student(103, "Charlie"), "3.0");
        grades.put(new Student(104, "Doris"), "1.7");
    }
}