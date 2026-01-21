public class EigeneLinkedList<T> {

    // --- Die Variablen der Liste (exakt wie im Bild) ---
    // Maintain a reference to the head node
    private ListNode<T> head;
    private int size;

    // --- Constructor (exakt wie im Bild) ---
    public EigeneLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // --- METHODEN (Angepasst an ListNode und value) ---

    // 1. Hinzufügen (Add)
    public void add(T item) {

        ListNode<T> newNode = new ListNode<>(item);

        if (head == null) {
            head = newNode;
        } else {
            ListNode<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // 2. Element holen (Get)
    public T get(int index) {
        checkIndex(index);

        ListNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        // WICHTIG: Hier greifen wir auf .value zu (nicht mehr .data)
        return current.value;
    }

    // 3. Entfernen (Remove)
    public T remove(int index) {
        checkIndex(index);

        ListNode<T> removedNode;

        //Kopf löschen
        if (index == 0) {
            removedNode = head;
            head = head.next;
        } else {
            ListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            removedNode = current.next; // zeigt auf den zu löschenen Index
            current.next = removedNode.next; //removeNode.next = der Nachfolge des gelöschten; der aktuell zeigt jetzt auf den Nachfolger des gelöschten
        }

        size--;
        // Auch hier: .value statt .data
        return removedNode.value;
    }

    // 4. Size Getter
    public int size() {
        return this.size;
    }

    // Hilfsmethode
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // --- Private "inner" node class (Exakt wie im Bild) ---
    // Sie steht hier am Ende, genau wie auf deinem Screenshot
    private class ListNode<T> {
        private T value;             // Heißt jetzt 'value'
        private ListNode<T> next;    // Typ ist ListNode<T>

        public ListNode(T newValue) {
            this.value = newValue;   // Zuweisung an 'value'
            this.next = null;
        }
    }
}