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

    // Gibt die Anzahl der Elemente zurück
    public int size() {
        return this.size;
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
        for (int i = index + 1; i < this.size; i++) {
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

    public static void main(String[] args) {
        System.out.println("=== START TEST: ArrayList ===");

        // 1. Erstellen der Liste (Generics Test mit String)
        EigeneArrayListe<String> liste = new EigeneArrayListe<>();
        printStatus(liste, "Liste initialisiert");

        // 2. Test: Einfaches Hinzufügen (add)
        liste.add("A");
        liste.add("B");
        liste.add("C");
        printStatus(liste, "Nach add(A, B, C)");

        // 3. Test: Einfügen zwischendrin (insert)
        // Wir fügen "X" zwischen A und B ein (Index 1)
        System.out.println("--> Füge 'X' an Index 1 ein...");
        liste.insert(1, "X");
        printStatus(liste, "Nach insert(1, 'X')");
        // Erwartung: [A, X, B, C]

        // 4. Test: Löschen (remove)
        // Wir löschen das "B". Es sollte an Index 2 liegen.
        System.out.println("--> Lösche Element an Index 2...");
        String geloescht = liste.remove(2);
        System.out.println("Gelöschtes Element war: " + geloescht);
        printStatus(liste, "Nach remove(2)");
        // Erwartung: [A, X, C] ("B" ist weg, "C" ist nachgerückt)

        // 5. Test: Automatische Vergrößerung (Resizing)
        System.out.println("--> Fülle Liste auf, um Resize zu erzwingen...");
        // Wir fügen viele Elemente hinzu, um über die Kapazität von 10 zu kommen
        for (int i = 0; i < 10; i++) {
            liste.add("Nummer " + i);
        }
        printStatus(liste, "Nach Massen-Add (Resize Test)");
        // Die Größe müsste jetzt 3 (von vorher) + 10 = 13 sein.

        // 6. Test: Fehlerbehandlung (Exception)
        System.out.println("--> Teste ungültigen Zugriff...");
        try {
            liste.get(100); // Index 100 gibt es nicht
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Erfolg! Fehler wurde korrekt abgefangen: " + e.getMessage());
        }

        System.out.println("=== TEST ENDE ===");
    }

    // --- Hilfsmethode zum schönen Ausgeben ---
    // Diese Methode zeigt uns den Inhalt der Liste wie [A, B, C] an
    public static void printStatus(EigeneArrayListe<?> list, String message) {
        System.out.println(message);
        System.out.print("Inhalt: [");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i)); // Nutzt unser get()
            if (i < list.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.println("Size: " + list.size());
        System.out.println("-------------------------");
    }
}
