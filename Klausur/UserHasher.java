import java.util.Scanner;

public class UserHasher {
    private final String username;

    public UserHasher(String username) {
        this.username = username;
    }

    public int getHash() {
        if (this.username == null) return 0;
        return this.username.hashCode();
    }

    // Neue Methode, die den mathematischen Rechenweg als Text generiert
    public String getCalculationFormula() {
        if (this.username == null || this.username.isEmpty()) {
            return "0 (Leerer String)";
        }

        StringBuilder formula = new StringBuilder();
        int n = this.username.length();

        for (int i = 0; i < n; i++) {
            char buchstabe = this.username.charAt(i);
            int unicode = (int) buchstabe; // Holt den numerischen Unicode/ASCII-Wert
            int exponent = n - 1 - i;

            // Den Teil-Ausdruck für diesen Buchstaben bauen (z.B. 72 * 31^1)
            formula.append(unicode);
            if (exponent > 0) {
                formula.append(" * 31^").append(exponent);
            }

            // Pluszeichen hinzufügen, wenn es nicht der letzte Buchstabe ist
            if (i < n - 1) {
                formula.append(" + ");
            }
        }

        return formula.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Bitte gib einen Benutzernamen ein: ");
        String inputUsername = scanner.nextLine();

        UserHasher user = new UserHasher(inputUsername);

        System.out.println("\n--- Analyse für: '" + inputUsername + "' ---");

        // Ausgabe der mathematischen Formel
        System.out.println("Mathematischer Ausdruck:");
        System.out.println(user.getCalculationFormula());

        // Ausgabe des finalen Werts
        System.out.println("\nBerechneter Hashwert: " + user.getHash());
        System.out.println("---------------------------------");

        // Ausgabe für Bucket in Vorlesung
        System.out.println("\nBerechneter Mudolo: " + user.getHash() % 5);
        System.out.println("---------------------------------");

        scanner.close();
    }
}