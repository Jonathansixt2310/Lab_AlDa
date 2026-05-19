import java.util.*;

public class NotationConverter {

    public static void main(String[] args) {
        String expression = "((10 + 2) + 3) * (5 - (3 - 1))";

        System.out.println(">>> ANALYSE DES AUSDRUCKS: " + expression + " <<<\n");

        // Erst Präfix (A)
        System.out.println("### AUFGABE A) UMWAHLDUNG IN PRÄFIX ###");
        List<String> prefix = convertToPrefixWithSteps(expression);
        System.out.println("\nENDERGEBNIS PRÄFIX: " + String.join(" ", prefix));

        System.out.println("\n" + "=".repeat(70) + "\n");

        // Dann Postfix (B)
        System.out.println("### AUFGABE B) UMWANDLUNG IN POSTFIX ###");
        List<String> postfix = convertToPostfixWithSteps(expression);
        System.out.println("\nENDERGEBNIS POSTFIX: " + String.join(" ", postfix));
    }

    private static int getPrecedence(String op) {
        if (op.equals("+") || op.equals("-")) return 1;
        if (op.equals("*") || op.equals("/")) return 2;
        return 0;
    }

    // --- LOGIK FÜR POSTFIX (Standard Shunting-Yard) ---
    public static List<String> convertToPostfixWithSteps(String expression) {
        List<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        String[] tokens = tokenize(expression);

        printHeader();
        for (String token : tokens) {
            processToken(token, stack, output, false);
            printStep(token, stack, output);
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
            printStep("Ende", stack, output);
        }
        return output;
    }

    // --- LOGIK FÜR PRÄFIX (Reversed Shunting-Yard) ---
    public static List<String> convertToPrefixWithSteps(String expression) {
        // Schritt 1: Ausdruck umkehren
        String[] tokens = tokenize(expression);
        List<String> tokenList = Arrays.asList(tokens);
        Collections.reverse(tokenList);

        System.out.println("1. Schritt: Ausdruck umkehren & Klammern spiegeln");
        List<String> processedTokens = new ArrayList<>();
        for (String t : tokenList) {
            if (t.equals("(")) processedTokens.add(")");
            else if (t.equals(")")) processedTokens.add("(");
            else processedTokens.add(t);
        }
        System.out.println("Interne Arbeitsform: " + String.join(" ", processedTokens) + "\n");

        // Schritt 2: Shunting-Yard auf den umgekehrten Ausdruck
        System.out.println("2. Schritt: Shunting-Yard auf Arbeitsform anwenden");
        List<String> intermediateOutput = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        printHeader();
        for (String token : processedTokens) {
            // Bei Präfix: Wenn Priorität gleich ist, bleibt der Operator auf dem Stack (Rechtsassoziativität simuliert)
            processToken(token, stack, intermediateOutput, true);
            printStep(token, stack, intermediateOutput);
        }
        while (!stack.isEmpty()) {
            intermediateOutput.add(stack.pop());
            printStep("Ende", stack, intermediateOutput);
        }

        // Schritt 3: Ergebnis erneut umkehren
        Collections.reverse(intermediateOutput);
        System.out.println("\n3. Schritt: Ergebnis für Präfix-Notation final umkehren.");
        return intermediateOutput;
    }

    // Hilfsmethode zur Tokenisierung
    private static String[] tokenize(String expr) {
        return expr.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").trim().split("\\s+");
    }

    // Zentralisierte Token-Verarbeitung
    private static void processToken(String token, Stack<String> stack, List<String> output, boolean isPrefixMode) {
        if (token.matches("\\d+")) {
            output.add(token);
        } else if (token.equals("(")) {
            stack.push(token);
        } else if (token.equals(")")) {
            while (!stack.isEmpty() && !stack.peek().equals("(")) {
                output.add(stack.pop());
            }
            stack.pop();
        } else {
            if (isPrefixMode) {
                while (!stack.isEmpty() && getPrecedence(stack.peek()) > getPrecedence(token)) {
                    output.add(stack.pop());
                }
            } else {
                while (!stack.isEmpty() && getPrecedence(stack.peek()) >= getPrecedence(token)) {
                    output.add(stack.pop());
                }
            }
            stack.push(token);
        }
    }

    private static void printHeader() {
        System.out.printf("%-10s | %-20s | %s%n", "Token", "Operator-Stack", "Output-Liste");
        System.out.println("-".repeat(70));
    }

    private static void printStep(String token, Stack<String> stack, List<String> output) {
        System.out.printf("%-10s | %-20s | %s%n", token, stack.toString(), String.join(" ", output));
    }
}