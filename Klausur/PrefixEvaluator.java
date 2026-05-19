import java.util.Stack;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class PrefixEvaluator {

    /**
     * Wertet einen mathematischen Ausdruck in Präfix-Notation aus.
     * @param expression Der Ausdruck als String (Zahlen und Operatoren durch Leerzeichen getrennt)
     * @return Das Endergebnis der Berechnung
     */
    public static double evaluatePrefix(String expression) {
        // Trennt den String anhand von Leerzeichen in einzelne Tokens (Zeichen) auf
        String[] tokens = expression.trim().split("\\s+");
        Stack<Double> stack = new Stack<>();

        System.out.println("Start der Auswertung für Präfix-Ausdruck: " + expression);
        System.out.println("=========================================================");

        // Wir durchlaufen das Array von rechts nach links
        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];

            if (isOperator(token)) {
                // Wenn es ein Operator ist, nimm die beiden obersten Elemente vom Stack
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Ungültiger Ausdruck: Nicht genügend Operanden für Operator '" + token + "'");
                }

                double op1 = stack.pop();
                double op2 = stack.pop();

                // Berechne das Ergebnis
                double result = performOperation(token, op1, op2);

                System.out.printf("Aktion : Operator '%s' gefunden.%n", token);
                System.out.printf("Rechne : %s %s %s = %s%n", formatNumber(op1), token, formatNumber(op2), formatNumber(result));

                // Lege das Ergebnis wieder auf den Stack
                stack.push(result);

            } else {
                // Wenn es eine Zahl ist, parse sie und lege sie auf den Stack
                try {
                    double value = Double.parseDouble(token);
                    stack.push(value);
                    System.out.printf("Aktion : Lese Zahl '%s' und lege sie auf den Stack.%n", token);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Ungültiges Zeichen im Ausdruck gefunden: " + token);
                }
            }

            printStack(stack);
            System.out.println("---------------------------------------------------------");
        }

        // Am Ende sollte genau ein Element (das Endergebnis) auf dem Stack liegen
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Ungültiger Ausdruck: Der Stack enthält am Ende mehr als ein Element.");
        }

        return stack.pop();
    }

    // Hilfsmethode zur Prüfung, ob ein Token ein Operator ist (Modulo hinzugefügt)
    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%");
    }

    // Hilfsmethode zur Durchführung der eigentlichen Rechenoperation (Modulo hinzugefügt)
    private static double performOperation(String operator, double op1, double op2) {
        switch (operator) {
            case "+": return op1 + op2;
            case "-": return op1 - op2;
            case "*": return op1 * op2;
            case "/":
                if (op2 == 0) throw new ArithmeticException("Division durch Null!");
                return op1 / op2;
            case "%":
                if (op2 == 0) throw new ArithmeticException("Modulo durch Null!");
                return op1 % op2;
            default: throw new IllegalArgumentException("Unbekannter Operator: " + operator);
        }
    }

    // Hilfsmethode für eine schöne Konsolenausgabe des Stacks (zeigt die oberste Ebene links an)
    private static void printStack(Stack<Double> stack) {
        List<Double> list = new ArrayList<>(stack);
        Collections.reverse(list); // Umdrehen, damit das "Top" Element vorne steht

        System.out.print("Stack  : [");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(formatNumber(list.get(i)));
            if (i < list.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("] <- Top");
    }

    // Hilfsmethode, um ganze Zahlen ohne Nachkommastelle (.0) auszugeben
    private static String formatNumber(double number) {
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return String.format("%s", number);
        }
    }

    public static void main(String[] args) {
        // Hinweis: Dieser Ausdruck wirft gewollt einen Fehler, da 6 Zahlen aber nur 4 Operatoren existieren.
        // Ein gültiges Beispiel zum Testen wäre z.B.: "- 100 + 50 % 20 3"
        String expression = "- 100 + 50 % 20 3 / 5 2";

        try {
            double finalResult = evaluatePrefix(expression);
            System.out.println("=========================================================");
            System.out.println("Endergebnis: " + formatNumber(finalResult));
        } catch (Exception e) {
            System.err.println("Fehler bei der Auswertung: " + e.getMessage());
        }
    }
}