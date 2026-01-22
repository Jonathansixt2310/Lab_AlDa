public class CircularLinkedList<T> {

    // --- Innere Klasse (Knoten) ---
    private class ListNode<T> {
        T value;
        ListNode<T> next;

        public ListNode(T value) {
            this.value = value;
            this.next = null;
        }
    }

    // --- Variablen ---
    private ListNode<T> head;
    private ListNode<T> tail; // Wichtig für schnellen Zugriff auf das Ende
    private int size;

    public CircularLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // 1. Hinzufügen (Am Ende)
    public void add(T item) {
        ListNode<T> newNode = new ListNode<>(item);

        if (head == null) {
            // Fall A: Liste ist leer
            head = newNode;
            tail = newNode;
            newNode.next = head; // DER KREIS: Zeigt auf sich selbst!
        } else {
            // Fall B: Liste hat Elemente
            tail.next = newNode; // Der alte letzte zeigt auf den Neuen
            tail = newNode;      // Der Neue ist jetzt der letzte
            tail.next = head;    // DER KREIS: Der Neue zeigt wieder zum Start
        }
        size++;
    }

    // 2. Entfernen (Remove)
    public T remove(int index) {
        checkIndex(index);
        ListNode<T> removedNode;

        // Spezialfall: Nur noch 1 Element da
        if (size == 1) {
            removedNode = head;
            head = null;
            tail = null;
            size--;
            return removedNode.value;
        }

        // Fall A: Kopf löschen (Index 0)
        if (index == 0) {
            removedNode = head;
            head = head.next; // Head weiterrücken
            tail.next = head; // WICHTIG: Das Ende muss den neuen Head kennen!
        }
        // Fall B: Mitte oder Ende löschen
        else {
            ListNode<T> current = head;
            // Wir laufen bis eins VOR das Ziel
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            removedNode = current.next;
            current.next = removedNode.next;

            // Falls wir das letzte Element gelöscht haben, müssen wir tail updaten
            if (index == size - 1) {
                tail = current;
            }
        }

        size--;
        return removedNode.value;
    }

    // 3. Get (Normale Suche)
    public T get(int index) {
        checkIndex(index);
        ListNode<T> current = head;
        // Da wir size kennen, nutzen wir einfach eine for-Schleife
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    // 4. Print (Spezial: Endlosschleife vermeiden!)
    public void printList() {
        if (head == null) {
            System.out.println("Leere Liste");
            return;
        }

        ListNode<T> current = head;
        System.out.print("Head -> ");

        // Wir nutzen do-while, damit wir mindestens einmal laufen,
        // auch wenn current am Anfang == head ist.
        do {
            System.out.print("[" + current.value + "] -> ");
            current = current.next;
        } while (current != head); // Stoppe, wenn wir wieder am Anfang sind

        System.out.println("(wieder Head)");
    }

    // Hilfsmethode
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // --- MAIN TEST ---
    public static void main(String[] args) {
        CircularLinkedList<String> cll = new CircularLinkedList<>();

        cll.add("A");
        cll.add("B");
        cll.add("C");

        System.out.println("--- Liste nach Insert ---");
        cll.printList();
        // Ausgabe: Head -> [A] -> [B] -> [C] -> (wieder Head)

        System.out.println("\n--- Remove Head (A) ---");
        cll.remove(0);
        cll.printList();
        // Ausgabe: Head -> [B] -> [C] -> (wieder Head)
        // Wichtig: C zeigt jetzt auf B!

        System.out.println("\n--- Remove Tail (C) ---");
        cll.remove(1); // Index 1 ist jetzt das letzte Element (C)
        cll.printList();
        // Ausgabe: Head -> [B] -> (wieder Head)
        // B zeigt jetzt auf sich selbst.
    }
}