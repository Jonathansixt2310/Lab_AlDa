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

    public T get(int index) {

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

    public void insert(int index, T item) {
        // 1. Sicherheitscheck:
        // WICHTIG: Hier ist "index == size" ERLAUBT!
        // Das bedeutet nämlich einfach: "Füge am Ende an".
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        // 2. Kapazität prüfen (identisch zur normalen add-Methode)
        if (size == array.length) {
            T[] newArray = (T[]) new Object[array.length * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }

        // 3. Platz schaffen (Shifting nach RECHTS)
        // Wir müssen RÜCKWÄRTS laufen, sonst überschreiben wir unsere eigenen Daten!
        // Wir starten am neuen freien Ende (size) und ziehen den Vorgänger (i-1) rüber.
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }

        // 4. Einfügen
        array[index] = item;
        size++;
    }

    public T remove(int index) {
        // 1. Sicherheitscheck: Ist der Index gültig?
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        T removedItem = this.array[index]; //speichern zu entfernenen Items

        // 2. Das Verschieben (Shifting)
        // Wir starten bei dem Index, der gelöscht werden soll.
        // Wir überschreiben ihn mit seinem rechten Nachbarn.
        // Das wiederholen wir bis zum vorletzten Element.
        for (int i = index + 1; 1 < this.size; i++) {
            this.array[i-1] = this.array[i];
        }

        // 3. Aufräumen (Memory Leak vermeiden)
        // Jetzt steht das letzte Element technisch gesehen doppelt im Array
        // (einmal an der neuen Position und einmal noch an der alten letzten Stelle).
        // Wir setzen die alte letzte Stelle auf null.
        this.array[this.size - 1] = null;

        // 4. Größe verringern
        size--;

        return removedItem;
    }
}
