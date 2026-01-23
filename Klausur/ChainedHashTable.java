import java.util.LinkedList;

public class ChainedHashTable<K, V> {
    // Interne Klasse für die Schlüssel-Wert-Paare (Folie 13)
    private class HashNode {
        K key;
        V value;
        HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<HashNode>[] table;

    @SuppressWarnings("unchecked")
    public ChainedHashTable() {
        // Initialisierung mit 7 Buckets (Folie 13)
        this.table = new LinkedList[7];
        for (int i = 0; i < this.table.length; i++) {
            this.table[i] = new LinkedList<HashNode>();
        }
    }

    /**
     * get-Methode zum Nachschlagen eines Wertes (Folie 14)
     */
    public V get(K lookupKey) {
        // Bucket-Index berechnen mittels hashCode() und Modulo (Folie 14)
        int bucket = Math.abs(lookupKey.hashCode()) % this.table.length;

        // Die Kette (Chain) im Bucket nach dem Schlüssel durchsuchen
        for (HashNode node : this.table[bucket]) {
            if (node.key.equals(lookupKey)) {
                return node.value; // Treffer (Folie 14)
            }
        }
        return null; // Nicht gefunden (Folie 14)
    }

    /**
     * put-Methode zum Einfügen eines Paares (Folie 4/12)
     */
    public void put(K key, V value) {
        int bucket = Math.abs(key.hashCode()) % this.table.length;
        // Einfügen eines neuen Knotens in die Liste des Buckets (Folie 12)
        this.table[bucket].add(new HashNode(key, value));
    }
}