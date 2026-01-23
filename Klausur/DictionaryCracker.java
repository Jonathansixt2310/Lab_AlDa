import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class DictionaryCracker {

    /**
     * Berechnet den kryptografischen Hash eines Wortes (Folie 16).
     */
    public static byte[] hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Führt den Wörterbuch-Angriff aus (orientiert an Folie 16).
     * @param target Der Ziel-Hash, der geknackt werden soll.
     * @param dictionaryFile Pfad zur Datei mit Passwort-Kandidaten.
     */
    public static void crack(byte[] target, String dictionaryFile) {
        try {
            Scanner s = new Scanner(new File(dictionaryFile));

            // Schleife durch die Wörterbuch-Passwörter (Folie 16, Zeile 2)
            while (s.hasNext()) {
                String candidate = s.next(); // Nächstes Wort (Folie 16, Zeile 3)

                // Hinweis: Hier könnten Mangling-Regeln angewendet werden (Folie 16, Zeile 5)

                // Teste den Kandidaten (Folie 16, Zeile 8)
                byte[] candidateHash = hash(candidate);

                // Vergleich (Folie 16, Zeile 10)
                if (Arrays.equals(candidateHash, target)) {
                    System.out.println("Cracked: " + candidate); // (Folie 16, Zeile 10)
                    s.close();
                    System.exit(0); // (Folie 16, Zeile 11)
                }
            }
            s.close();
            System.out.println("Passwort nicht im Wörterbuch gefunden.");
        } catch (FileNotFoundException e) {
            System.err.println("Wörterbuch-Datei nicht gefunden.");
        }
    }

    public static void main(String[] args) {
        // Beispiel: Wir haben den Hash von "password123" gestohlen
        byte[] stolenHash = hash("password123");

        // Starte Angriff (benötigt eine Datei 'dictionary.txt')
        System.out.println("Starte Dictionary Attack...");
        crack(stolenHash, "Klausur/dictionary.txt");
    }
}