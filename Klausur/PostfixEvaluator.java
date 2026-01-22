import java.util.Stack;
import java.util.Scanner;

public class PostfixEvaluator {

    /**
     * Wertet einen Postfix-Ausdruck aus und gibt das Ergebnis zurück.
     * Basierend auf der Implementierung von Folie 23.
     */
    public static int evaluate(String expr) {
        // Initialisierung des Stacks für Integers [cite: 1235]
        Stack<Integer> stack = new Stack<Integer>();
        // Scanner zum Zerlegen des Strings in einzelne Tokens (Zahlen/Operatoren) [cite: 1235]
        Scanner scan = new Scanner(expr);

        System.out.println("Auswertung von: " + expr);
        System.out.println("--------------------------------");

        while (scan.hasNext()) {
            String token = scan.next();

            // Falls der Token ein Operator ist [cite: 1238]
            if (isOperator(token)) {
                // Pop der obersten zwei Operanden [cite: 1239-1240, 1172]
                // Wichtig: Das zweite gepoppte Element ist der linke Operand!
                int op2 = stack.pop();
                int op1 = stack.pop();
                int result = applyOperator(token, op1, op2);

                // Ergebnis zurück auf den Stack legen
                stack.push(result);

                System.out.printf("Operator '%s' gefunden: %d %s %d = %d%n",
                        token, op1, token, op2, result);
                System.out.println("Aktueller Stack: " + stack);
            }
            // Falls der Token eine Zahl ist [cite: 1251]
            else {
                int value = Integer.parseInt(token); // [cite: 1255]
                stack.push(value); // [cite: 1257, 1171]
                System.out.println("Zahl gepusht: " + value);
                System.out.println("Aktueller Stack: " + stack);
            }
        }

        // Das letzte Element auf dem Stack ist das finale Ergebnis
        return stack.pop();
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private static int applyOperator(String operator, int op1, int op2) {
        switch (operator) {
            case "+": return op1 + op2;
            case "-": return op1 - op2; //
            case "*": return op1 * op2;
            case "/": return op1 / op2;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        // Beispiel aus den Folien: 50 20 40 + 10 - + [cite: 1177]
        String expression = "50 20 40 + 10 - +";
        int result = evaluate(expression);

        System.out.println("--------------------------------");
        System.out.println("Finales Ergebnis: " + result); // Sollte 100 sein laut Folie 22
    }
}