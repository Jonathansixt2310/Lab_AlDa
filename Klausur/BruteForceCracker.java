import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class BruteForceCracker {

    /**
     * Berechnet den Hash eines Kandidaten (orientiert an Folie 16)[cite: 451].
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
     * Simuliert einen Brute-Force-Angriff für Passwörter bis zu einer max. Länge.
     */
    public static void bruteForce(byte[] targetHash, int maxLength) {
        char[] charset = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        generate(targetHash, "", charset, maxLength);
    }

    private static void generate(byte[] target, String current, char[] charset, int length) {
        if (current.length() > length) return;

        // Teste den aktuellen Kandidaten (orientiert an Folie 16)
        if (!current.isEmpty()) {
            byte[] candidateHash = hash(current);
            if (Arrays.equals(candidateHash, target)) {
                System.out.println("Geknackt: " + current); // [cite: 455]
                System.exit(0); // [cite: 458]
            }
        }

        if (current.length() < length) {
            for (char c : charset) {
                generate(target, current + c, charset, length);
            }
        }
    }

    public static void main(String[] args) {
        // Beispiel: Wir suchen das Passwort "abc"
        String secret = "abc";
        byte[] target = hash(secret);

        System.out.println("Suche Passwort für Hash von: " + secret);

        // Brute-Force-Angriff starten (max. 3 Zeichen)
        bruteForce(target, 3);
    }
}