public class CircularBuffer<T> {
    private T[] buffer;
    private int front;
    private int end;
    private int size;

    public CircularBuffer(int capacity) {
        this.buffer = (T[]) new Object[capacity];
        this.front = 0;
        this.end = 0;
        this.size = 0;
    }

    /**
     * offer: Fügt ein neues Element am Ende hinzu. [cite: 877]
     * @return false, wenn der Buffer voll ist. [cite: 880, 884]
     */
    public boolean offer(T value) {
        // Prüfen, ob das Array voll ist
        if (this.size == buffer.length) {
            return false;
        }

        this.buffer[this.end] = value; // Wert an Position 'end' speichern
        this.size++; // Größe inkrementieren

        // 'end' vorrücken mit Wrap-Around Logik
        this.end = (this.end + 1) % buffer.length;

        return true;
    }

    /**
     * poll: Entfernt das vorderste Element und gibt es zurück. [cite: 900]
     * @return null, wenn die Queue leer ist. [cite: 903, 905]
     */
    public T poll() {
        // Rückgabe von null bei leerer Queue
        if (this.size == 0) {
            return null;
        }

        T next = this.buffer[this.front]; // Element an Position 'front' holen
        this.size--; // Größe dekrementieren

        // 'front' vorrücken mit Wrap-Around Logik
        this.front = (this.front + 1) % buffer.length;

        return next;
    }
}