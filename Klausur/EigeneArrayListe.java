public class EigeneArrayListe <T> {
    // The 1-D backing array
    private T[] array;

    // Number of elements added to the list
    private int size;

    public EigeneArrayListe() {
        // Wir starten mit einer Standardkapazität (z.B. 10)
        // Man muss (T[]) new Object[...] schreiben, da new T[...] nicht geht.
        this.array = (T[]) new Object[10];
        this.size = 0;
    }

    public T getItem(int index) {

        // 1. Sicherheitscheck: Ist der Index überhaupt gültig?
        // Wir dürfen nicht unter 0 gehen und nicht über size (die Anzahl der echten Elemente).
        // WICHTIG: Wir prüfen gegen 'size', nicht gegen 'array.length'!
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        // 2. Das Element zurückgeben
        return array[index];
    }

    public void add(T item) {
        // 1. Prüfen, ob das Array voll ist
        if (size == array.length) {
            // Wenn voll: Neues Array mit doppelter Größe erstellen
            T[] newArray = (T[]) new Object[array.length * 2];

            // Alte Daten kopieren
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }

            // Referenz auf das neue Array setzen
            array = newArray;
        }

        // 2. Element an der nächsten freien Stelle einfügen
        array[size] = item;

        // 3. Size erhöhen
        size++;
    }

}
