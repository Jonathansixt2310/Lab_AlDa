public class LinkedDeque<T> {

    private class Node {
        T data;
        Node next;
        Node prev;

        Node(T data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public LinkedDeque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // --- Methoden für das ENDE (Standard Queue: offer/poll) ---

    public void offer(T item) {
        offerLast(item);
    }

    public void offerLast(T item) { // [cite: 795, 801]
        Node newNode = new Node(item);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public T poll() {
        return pollFirst();
    }

    public T pollLast() { // [cite: 795]
        if (isEmpty()) return null;
        T data = tail.data;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        size--;
        return data;
    }

    // --- Methoden für den ANFANG ---

    public void offerFirst(T item) { // [cite: 795]
        Node newNode = new Node(item);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public T pollFirst() { // [cite: 795, 802]
        if (isEmpty()) return null;
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;
        return data;
    }

    // --- Hilfsmethoden ---

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}