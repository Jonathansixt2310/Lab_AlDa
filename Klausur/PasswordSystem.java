import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PasswordSystem {

    // Simuliert die "Shadow Password File" aus den Folien
    private Map<String, byte[]> shadowFile = new HashMap<>();

    /**
     * Berechnet den kryptografischen Hash (SHA-256) eines Passworts.
     * Nutzt die MessageDigest Klasse.
     */
    private byte[] hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 nicht verfügbar", e);
        }
    }

    /**
     * Registriert einen neuen Benutzer.
     * Speichert nur den Hash, niemals das Klartext-Passwort[cite: 402, 403].
     */
    public void register(String username, String password) {
        byte[] passwordHash = hashPassword(password);
        shadowFile.put(username, passwordHash);
        System.out.println("Benutzer " + username + " wurde mit Hash-Wert registriert.");
    }

    /**
     * Prüft die Login-Daten.
     * Vergleicht den neu berechneten Hash mit dem gespeicherten Hash[cite: 404, 405].
     */
    public boolean login(String username, String enteredPassword) {
        if (!shadowFile.containsKey(username)) {
            return false;
        }

        byte[] storedHash = shadowFile.get(username);
        byte[] enteredHash = hashPassword(enteredPassword);

        // Wenn die beiden Hashes übereinstimmen, ist das Passwort korrekt [cite: 405]
        return Arrays.equals(enteredHash, storedHash);
    }

    public static void main(String[] args) {
        PasswordSystem system = new PasswordSystem();

        // 1. Registrierung
        system.register("Alice", "meinGeheimnis123");

        // 2. Erfolgreicher Login
        boolean success = system.login("Alice", "meinGeheimnis123");
        System.out.println("Login Alice (korrekt): " + (success ? "Erfolgreich" : "Fehlgeschlagen"));

        // 3. Fehlgeschlagener Login (Falsches Passwort)
        boolean fail = system.login("Alice", "falschesPasswort");
        System.out.println("Login Alice (falsch): " + (fail ? "Erfolgreich" : "Fehlgeschlagen"));
    }
}