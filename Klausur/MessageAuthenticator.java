import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MessageAuthenticator {

    /**
     * Erzeugt einen SHA-256 Hash für eine Nachricht
     */
    public static byte[] generateHash(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(message.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algorithmus nicht gefunden");
        }
    }

    /**
     * Verifiziert, ob die Nachricht zum gegebenen Hash passt.
     * Das ist das Kernprinzip der Authentifizierung (Folie 15).
     */
    public static boolean verifyMessage(String receivedMessage, byte[] originalHash) {
        byte[] currentHash = generateHash(receivedMessage);
        // Nutzt Arrays.equals für den Vergleich von Byte-Arrays
        return Arrays.equals(currentHash, originalHash);
    }

    public static void main(String[] args) {
        String originalMsg = "Überweise 100 Euro an Klaus.";

        // 1. Alice generiert den Hash (Fingerabdruck)
        byte[] aliceHash = generateHash(originalMsg);
        System.out.println("Alice sendet Nachricht und Hash...");

        // 2. Szenario: Nachricht wird NICHT manipuliert
        boolean isAuthentic = verifyMessage(originalMsg, aliceHash);
        System.out.println("Szenario 1 (Original): Authentisch? " + isAuthentic);

        // 3. Szenario: Ein Angreifer ändert die Nachricht (Folie 3: "even one bit changes")
        String manipulatedMsg = "Überweise 1000 Euro an Klaus.";
        boolean isManipulated = verifyMessage(manipulatedMsg, aliceHash);
        System.out.println("Szenario 2 (Manipuliert): Authentisch? " + isManipulated);
    }
}