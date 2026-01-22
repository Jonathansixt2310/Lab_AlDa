public class LinkedStack<T> {

    // Interne Hilfsklasse für die Knoten
    private class Node {
        T data;
        Node next;

        Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node head; // Zeigt immer auf das oberste Element (top)
    private int size;

    public LinkedStack() {
        this.head = null;
        this.size = 0;
    }

    // Push: Fügt ein Element am Kopf der Liste ein
    public void push(T newItem) {
        // Der neue Knoten wird zum neuen Head und zeigt auf den alten Head
        this.head = new Node(newItem, this.head);
        this.size++;
    }

    // Pop: Entfernt das Element am Kopf und gibt es zurück
    public T pop() {
        if (isEmpty()) {
            return null; // Oder Exception werfen
        }
        T topItem = this.head.data;  // Daten sichern
        this.head = this.head.next;  // Head auf das nächste Element verschieben
        this.size--;
        return topItem;
    }

    // Peek: Zeigt das oberste Element an, ohne es zu entfernen
    public T peek() {
        return (isEmpty()) ? null : this.head.data;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public int size() {
        return this.size;
    }
}