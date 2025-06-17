import java.util.*;
public class SmartCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder expression = new StringBuilder();
        System.out.print("How many numbers do you want to perform operations with? (Minimum 2): ");
        int count = sc.nextInt();
        sc.nextLine();
        expression.append(getDouble(sc, "Enter first number: "));
        int numCount = 1;
        boolean expectNumber = false;
        while (numCount < count) {
            if (expectNumber) {
                expression.append(getDouble(sc, "Enter next number: "));
                numCount++;
                expectNumber = false;
            } else {
                System.out.print("Enter operator (+, -, *, /, (, )) or 'n' to finish early: ");
                String op = sc.next();
                if (op.equalsIgnoreCase("n")) break;
                if ("+-*/()".contains(op)) {
                    expression.append(" ").append(op).append(" ");
                    expectNumber = !op.equals(")");
                } else {
                    System.out.println("Invalid operator. Try again.");
                }
            }
        }
        System.out.println("\nExpression so far: " + expression);
        String fixedExpr = fixUnmatchedParentheses(expression.toString(), sc);
        try {
            double result = evaluateExpression(fixedExpr);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println(" Evaluation Error: " + e.getMessage());
        }
        sc.close();
    }
    static double getDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) return sc.nextDouble();
            else {
                System.out.println("Invalid input. Enter a valid number.");
                sc.next();
            }
        }
    }
    static String fixUnmatchedParentheses(String expr, Scanner sc) {
        int open = 0, close = 0;
        for (char c : expr.toCharArray()) {
            if (c == '(') open++;
            else if (c == ')') close++;
        }
        if (open == close) return expr;
        System.out.println(" Parenthesis mismatch detected.");
        boolean missingRight = open > close;
        boolean missingLeft = close > open;
        List<String> elements = new ArrayList<>();
        List<Integer> positions = new ArrayList<>();
        String[] split = expr.split(" ");
        int offset = 0;
        for (String part : split) {
            try {
                Double.parseDouble(part);
                elements.add(part);
                positions.add(offset);
            } catch (NumberFormatException ignored) {}
            offset += part.length() + 1;
        }
        if (missingLeft) {
            System.out.println("You missed a left parenthesis.");
            System.out.println("Where should I insert '('?");
            for (int i = 0; i < elements.size(); i++) {
                System.out.println("[" + i + "] before " + elements.get(i));
            }
            System.out.print("Select index to insert '(': ");
            int idx = sc.nextInt();
            sc.nextLine();
            int pos = positions.get(Math.min(idx, positions.size() - 1));
            expr = expr.substring(0, pos) + "( " + expr.substring(pos);
        } else if (missingRight) {
            System.out.println("You missed a right parenthesis.");
            System.out.println("Where should I insert ')'?");
            for (int i = 0; i < elements.size(); i++) {
                System.out.println("[" + i + "] after " + elements.get(i));
            }
            System.out.print("Select index to insert ')': ");
            int idx = sc.nextInt();
            sc.nextLine();
            int pos = positions.get(Math.min(idx, positions.size() - 1)) + elements.get(idx).length();
            expr = expr.substring(0, pos) + " )" + expr.substring(pos);
        }
        System.out.println(" New expression: " + expr);
        return expr;
    }
    static double evaluateExpression(String expr) throws Exception {
        expr = expr.replaceAll("\\s+", "");
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i++));
                }
                values.push(Double.parseDouble(sb.toString()));
                continue;
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    applyOp(values, ops.pop());
                }
                if (ops.isEmpty()) throw new Exception("Mismatched parentheses.");
                ops.pop();
            } else if ("+-*/".indexOf(c) != -1) {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                    applyOp(values, ops.pop());
                }
                ops.push(c);
            } else {
                throw new Exception("Invalid character: " + c);
            }
            i++;
        }
        while (!ops.isEmpty()) {
            if (ops.peek() == '(') throw new Exception("Mismatched parentheses.");
            applyOp(values, ops.pop());
        }
        if (values.size() != 1) throw new Exception("Invalid expression.");
        return values.pop();
    }
    static void applyOp(Stack<Double> values, char op) throws Exception {
        if (values.size() < 2) throw new Exception("Not enough operands.");
        double b = values.pop(), a = values.pop();
        switch (op) {
            case '+': values.push(a + b); break;
            case '-': values.push(a - b); break;
            case '*': values.push(a * b); break;
            case '/':
                if (b == 0) throw new Exception("Division by zero.");
                values.push(a / b); break;
        }
    }
    static int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : (op == '*' || op == '/') ? 2 : 0;
    }
}
