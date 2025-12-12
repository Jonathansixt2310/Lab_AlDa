import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class BruteForceCrackerStarter {

    private MessageDigest md;

    //Anpassung 1 - ALPHABET erweitern
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzäöüß";
    //private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzäöüßABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜ";

    //Variable für die Zeitmessung
    private static long startTime;

    public BruteForceCrackerStarter() {
        // Initialize this.md
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public byte[] hash(String data) {
        return this.md.digest(data.getBytes());
    }

    /**
     * Recursively generate candidate passwords
     *
     * @param target the target password hash in byte[] format
     * @param k password length
     * @param candidate the in-progress candidate password
     */
    public void generate(byte[] target, int k, String candidate) {

        // Base case: the candidate string has length k
        // Test if it's a match for the target
        if (candidate.length() == k) {
            byte[] candidateHash = hash(candidate);

            if (Arrays.equals(candidateHash, target)) {
                //Anpassung 4 - Zeitmessung
                long endTime = System.currentTimeMillis();
                long dauer = endTime - startTime;
                System.out.println("Cracked: " + candidate);
                System.out.printf("Benötigte Zeit: %d ms", dauer);
                System.exit(0);
            }
            return;
        }

        // Recursive case: extend candidate by each alphabet character
        for (int i = 0; i < ALPHABET.length(); i++) {
            char next = ALPHABET.charAt(i);
            generate(target, k, candidate + next);
        }
    }

    /**
     * Entry function to generate candidate passwords
     */
    public void crack(byte[] target, int k) {
        generate(target, k, "");
    }
    public static void main(String[] args){
        //Anpassung 2 - Passwort festlegen
        Scanner scanner = new Scanner(System.in);
        System.out.println("Passwort zum Testen: ");
        String targetPw = scanner.nextLine();
        int length = targetPw.length();
        System.out.println();
        System.out.printf("Brute-Force für das Passwort %s wird gestartet", targetPw);
        System.out.println();

        //Anpassung 3 - Ziel-Hash erzeugen
        BruteForceCrackerStarter cracker = new BruteForceCrackerStarter(); //erzeugen der Instanz
        byte[] targetHash = cracker.hash(targetPw);

        //Anpassung 4 - Zeitmessung
        startTime = System.currentTimeMillis();

        //Anpassung 5 - Cracker starten
        cracker.crack(targetHash, length);
    }
}