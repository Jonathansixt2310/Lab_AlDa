import java.util.ArrayList;

public class Stack<T> {
    // Interne Liste zum Speichern der Elemente
    private ArrayList<T> items;

    // Konstruktor: Initialisiert die Liste
    public Stack() {
        this.items = new ArrayList<T>();
    }

    // Push: Fügt ein Element oben (am Ende der Liste) hinzu
    public void push(T newItem) {
        this.items.add(newItem);
    }

    // Pop: Entfernt das oberste Element und gibt es zurück
    public T pop() {
        // Entfernt das Element am letzten Index (size - 1)
        return this.items.remove(this.items.size() - 1);
    }

    // Hilfsmethode: Prüfen ob der Stack leer ist [cite: 898]
    public boolean isEmpty() {
        return this.items.isEmpty();
    }
}