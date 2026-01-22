import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack; // Nutzt die Java-eigene Stack-Klasse

public class TagValidator {

    public static boolean validate(ArrayList<String> tags) {
        // 1. Initialisiere den Stack [cite: 1024]
        Stack<String> stack = new Stack<String>();

        // 2. Schleife durch die Tag-Sequenz [cite: 1039]
        for (String tag : tags) {

            // Prüfe, ob es ein schließendes Tag ist (beginnt mit '/') [cite: 1032]
            if (tag.startsWith("/")) {

                // Falls ein schließendes Tag kommt, der Stack aber leer ist -> Fehler
                if (stack.isEmpty()) return false;

                // Extrahiere den Namen nach dem '/'
                String name = tag.substring(1);

                // Vergleiche mit dem obersten Element des Stacks [cite: 1044]
                // pop() entfernt das Tag und gibt es zurück
                if (!stack.pop().equals(name)) {
                    return false; // Tags passen nicht zusammen [cite: 1045]
                }

            } else {
                // Öffnende Tags werden auf den Stack gelegt [cite: 1051, 1053]
                stack.push(tag);
            }
        }

        // Wenn der Stack am Ende leer ist, war alles korrekt gepaart
        return stack.empty();
    }

    public static void main(String[] args) {
        // Beispiel aus den Folien: <html> <head> </head> </html> [cite: 939-956]
        ArrayList<String> validTags = new ArrayList<>(Arrays.asList(
                "html", "head", "/head", "body", "/body", "/html"
        ));

        ArrayList<String> invalidTags = new ArrayList<>(Arrays.asList(
                "html", "head", "/html", "/head" // Falsche Reihenfolge!
        ));

        System.out.println("Valid Tags korrekt? " + validate(validTags));   // true
        System.out.println("Invalid Tags korrekt? " + validate(invalidTags)); // false
    }
}