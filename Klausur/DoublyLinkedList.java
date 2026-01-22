public class DoublyLinkedList<T> {

    // --- Innere Klasse (Der Knoten) ---
    private class ListNode<T> {
        T value;
        ListNode<T> next; // Zeiger auf Nachfolger
        ListNode<T> prev; // Zeiger auf Vorgänger

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

    // --- METHODEN ---

    // 1. Hinzufügen (Am Ende)
    public void add(T item) {
        ListNode<T> newNode = new ListNode<>(item);

        if (head == null) {
            // Liste ist noch leer
            head = newNode;
            tail = newNode; // Head und Tail sind das gleiche Element
        } else {
            // Liste ist nicht leer -> Wir hängen an Tail an
            tail.next = newNode; // Der alte Letzte zeigt auf den Neuen
            newNode.prev = tail; // Der Neue zeigt zurück auf den Alten
            tail = newNode;      // Der Neue ist jetzt der offizielle Tail
        }
        size++;
    }

    // 2. Einfügen an bestimmtem Index
    public void add(int index, T item) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();

        if (index == size) {
            add(item); // Einfach hinten anhängen
            return;
        }

        ListNode<T> newNode = new ListNode<>(item);

        if (index == 0) {
            // Vorne einfügen
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) tail = newNode; // Falls Liste vorher leer war
        } else {
            // In der Mitte einfügen
            ListNode<T> current = getNode(index); // Der Knoten, der jetzt an 'index' sitzt
            ListNode<T> predecessor = current.prev; // Der Knoten davor

            // "Vier Zeiger umbiegen"
            newNode.next = current;
            newNode.prev = predecessor;

            predecessor.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    // 3. Get mit Turbo (Bidirektionale Suche)
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    // 4. Set (Überschreiben)
    public T set(int index, T value) {
        checkIndex(index);
        ListNode<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    // 5. Remove (Löschen)
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

        // Aufräumen (optional, hilft dem Garbage Collector)
        toRemove.next = null;
        toRemove.prev = null;

        size--;
        return toRemove.value;
    }

    // --- HILFSMETHODEN ---

    // Findet den Knoten
    private ListNode<T> getNode(int index) {
        ListNode<T> current;

        // TRICK: Wenn der Index in der ersten Hälfte liegt -> starte bei Head
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        // Wenn der Index in der zweiten Hälfte liegt -> starte bei Tail (rückwärts)
        else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    public int size() {
        return size;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // --- TEST MAIN ---
    public static void printList(DoublyLinkedList<?> list) {
        System.out.print("Inhalt: [");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
            if (i < list.size() - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        DoublyLinkedList<String> dll = new DoublyLinkedList<>();

        System.out.println("--- 1. Add ---");
        dll.add("A");
        dll.add("B");
        dll.add("C");
        printList(dll); // [A, B, C]

        System.out.println("\n--- 2. Add an Index 1 ---");
        dll.add(1, "X");
        printList(dll); // [A, X, B, C]

        System.out.println("\n--- 3. Set an Index 2 ---");
        dll.set(2, "Y"); // B wird Y
        printList(dll); // [A, X, Y, C]

        System.out.println("\n--- 4. Remove Index 0 (Head) ---");
        dll.remove(0);
        printList(dll); // [X, Y, C]

        System.out.println("\n--- 5. Remove Index 2 (Tail) ---");
        dll.remove(2);
        printList(dll); // [X, Y]

        // Beweis, dass Tail korrekt sitzt (wir können von hinten zugreifen)
        System.out.println("\n--- Check: Get(1) nutzt Rückwärtssuche ---");
        System.out.println("Letztes Element: " + dll.get(1)); // Y
    }
}