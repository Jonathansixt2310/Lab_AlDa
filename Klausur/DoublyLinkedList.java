public class DoublyLinkedList {
    public class DoublyLinkedList<T> {

        // --- Innere Klasse (Der Knoten) ---
        private class ListNode<T> {
            T value;
            ListNode<T> next; // Zeiger auf Nachfolger
            ListNode<T> prev; // NEU: Zeiger auf Vorgänger

            public ListNode(T value) {
                this.value = value;
                this.next = null;
                this.prev = null;
            }
        }

        // --- Variablen der Liste ---
        private ListNode<T> head; // Anfang
        private ListNode<T> tail; // Ende (für schnelles Einfügen hinten)
        private int size;

        public DoublyLinkedList() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        // 1. Hinzufügen (Am Ende)
        public void add(T item) {
            ListNode<T> newNode = new ListNode<>(item);

            if (head == null) {
                // Liste ist leer: Der Neue ist Head UND Tail
                head = newNode;
                tail = newNode;
            } else {
                // Liste ist nicht leer: Wir hängen an Tail an
                tail.next = newNode; // Der alte Letzte zeigt auf den Neuen
                newNode.prev = tail; // Der Neue zeigt zurück auf den Alten
                tail = newNode;      // Der Neue ist jetzt der offizielle Tail
            }
            size++;
        }

        // 2. Get mit Turbo (Bidirektionale Suche)
        public T get(int index) {
            checkIndex(index);
            return getNode(index).value;
        }

        // Hilfsmethode: Findet den Knoten (optimiert)
        private ListNode<T> getNode(int index) {
            ListNode<T> current;

            // TRICK: Wenn der Index in der zweiten Hälfte liegt,
            // starten wir von hinten (tail) und laufen rückwärts!
            if (index < size / 2) {
                current = head;
                for (int i = 0; i < index; i++) {
                    current = current.next;
                }
            } else {
                current = tail;
                for (int i = size - 1; i > index; i--) {
                    current = current.prev;
                }
            }
            return current;
        }

        // 3. Remove (Löschen)
        public T remove(int index) {
            checkIndex(index);
            ListNode<T> toRemove = getNode(index); // Knoten suchen

            // A. Verkettung zum Vorgänger lösen
            if (toRemove.prev != null) {
                toRemove.prev.next = toRemove.next;
            } else {
                // Wenn es keinen Vorgänger gab, war es der Head
                head = toRemove.next;
            }

            // B. Verkettung zum Nachfolger lösen
            if (toRemove.next != null) {
                toRemove.next.prev = toRemove.prev;
            } else {
                // Wenn es keinen Nachfolger gab, war es der Tail
                tail = toRemove.prev;
            }

            size--;
            return toRemove.value;
        }

        // 4. Insert (Einfügen an Index)
        public void insert(int index, T item) {
            if (index < 0 || index > size) throw new IndexOutOfBoundsException();

            if (index == size) {
                add(item); // Einfach anhängen
                return;
            }

            ListNode<T> newNode = new ListNode<>(item);
            ListNode<T> current = getNode(index); // Der Knoten, der jetzt an 'index' sitzt
            ListNode<T> predecessor = current.prev; // Der Knoten davor

            // Neue Verbindungen setzen
            newNode.next = current;
            newNode.prev = predecessor;

            // Alte Verbindungen anpassen
            current.prev = newNode;

            if (predecessor != null) {
                predecessor.next = newNode;
            } else {
                head = newNode; // Wenn wir an Index 0 einfügen
            }

            size++;
        }

        public int size() {
            return size;
        }

        private void checkIndex(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
        }

        // --- Test Main ---
        public static void main(String[] args) {
            DoublyLinkedList<String> dll = new DoublyLinkedList<>();
            dll.add("A");
            dll.add("B");
            dll.add("C");

            System.out.println("Get 1: " + dll.get(1)); // B

            System.out.println("Insert X at 1...");
            dll.insert(1, "X"); // A, X, B, C

            System.out.println("Remove Index 2 (B)...");
            dll.remove(2); // A, X, C

            System.out.println("Head: " + dll.get(0));       // A
            System.out.println("Tail: " + dll.get(dll.size-1)); // C
        }
    }
}
