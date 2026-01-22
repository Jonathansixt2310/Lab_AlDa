import java.util.Stack;
import java.util.Scanner;

public class InfixToPostfix {

    // Hilfsmethode zur Definition der Prioritäten
    private static int priority(String op) {
        if (op.equals("+") || op.equals("-")) return 1;
        if (op.equals("*") || op.equals("/")) return 2;
        return 0; // Für Klammern
    }

    public static String convert(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<String> stack = new Stack<>();
        Scanner scan = new Scanner(infix);

        while (scan.hasNext()) {
            String token = scan.next();

            // 1. Wenn Operand: Direkt zur Ausgabe
            if (token.matches("[a-zA-Z0-9]+")) {
                output.append(token).append(" ");
            }
            // 2. Wenn linke Klammer: Auf den Stack
            else if (token.equals("(")) {
                stack.push(token);
            }
            // 3. Wenn rechte Klammer: Pop bis zur linken Klammer
            else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.append(stack.pop()).append(" ");
                }
                stack.pop(); // Entferne "("
            }
            // 4. Wenn Operator [cite: 405-408]
            else {
                while (!stack.isEmpty() && priority(stack.peek()) >= priority(token)) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(token);
            }
        }

        // 5. Reste vom Stack leeren
        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(" ");
        }

        return output.toString().trim();
    }

    public static void main(String[] args) {
        // Beispiel aus Folie 26: a + b * c - d [cite: 415]
        String infix = "a + b * c - d";
        System.out.println("Infix:   " + infix);
        System.out.println("Postfix: " + convert(infix)); // Erwartet: a b c * + d - [cite: 398]
    }
}