public class LinkedQueue<T> {

    // Interne Klasse für die doppelt verketteten Knoten
    private class Node {
        T data;
        Node next;
        Node prev;

        Node(T data) {
            this.data = data;
        }
    }

    private Node head; // Zeigt auf das vorderste Element
    private Node tail; // Zeigt auf das hinterste Element
    private int size;

    public LinkedQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * offer: Fügt ein Element am Ende der Queue hinzu.
     * Entspricht dem Hinzufügen am Tail (O(1)).
     */
    public void offer(T item) {
        Node newNode = new Node(item);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * poll: Entfernt das vorderste Element und gibt es zurück.
     * Entspricht dem Entfernen am Head (O(1)).
     */
    public T poll() {
        if (isEmpty()) {
            return null;
        }

        T data = head.data;
        head = head.next;

        if (head == null) {
            tail = null; // Queue ist jetzt leer
        } else {
            head.prev = null;
        }

        size--;
        return data;
    }

    /**
     * peek: Gibt das vorderste Element zurück, ohne es zu entfernen.
     */
    public T peek() {
        return isEmpty() ? null : head.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}