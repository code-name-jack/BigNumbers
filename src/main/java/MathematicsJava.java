

/*
The program takes input from stdin. Each line is an arithmetic expression
with numbers (in decimal), +, -, *, ^ (exponentiation), and parentheses.
Operator precedence: ^, *, {+,-} (same as in programming languages).
Your program should read each line and calculate the value of the expression
and print it to stdout. A line with "0" means the program should stop.
*/

/*
Sample input:
48-24
24-48
(2*3+4)*(4*3+2)
((2*3
2^100
0

Sample output:
24
Negative numbers are not supported.
140
syntax error
1267650600228229401496703205376
*/


import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Stack;

public class MathematicsJava {
    final static String Operators = "^*+-/";
    double result;

    public static void main(String[] args) {
        System.out.println("Please input your Expressions.");
        Scanner ConsoleInput = new Scanner(System.in);
        LinkedList<String> Expressions = new LinkedList<>();
        /*..........................Taking Input...............................................*/
        do {
            Expressions.add(ConsoleInput.nextLine());
        } while (!Expressions.peekLast().contentEquals("0"));
        Expressions.removeLast();
        for (String Expression : Expressions) {
            if (ValidateExpression(Expression)) {
                String Solution;
                Solution = SolveExpression(Expression);
                System.out.println("Result is: " + Solution);
            } else System.out.println("Syntax Error");
        }
    }
    /*This function checks for any syntax error*/

    static boolean ValidateExpression(String Expression) {
        char[] Validater = Expression.toCharArray();
        short Count = 0;
        for (char c : Validater) {
            if (c == '(') Count++;
            else if (c == ')') Count--;

            if (Character.isAlphabetic(c)) return false;
            if (Count < 0) return false;
        }
        return Count == 0;
    }

    /*This is where expresseions are solved step by step*/

    public static String SolveExpression(String Expression) {
        Expression = InfixToPostfix(Expression);

        ArrayList<String> Tokens = new ArrayList<>();
        String Result = "";
        try {
            for (String s : Expression.split("\\s+")) {
                String Temp1, Temp2;

                if (s.matches("\\d+") || s.substring(1).matches("\\d+")) {

                    Tokens.add(s);
                } else {

                    Temp2 = Tokens.remove(Tokens.size() - 1);
                    Temp1 = Tokens.remove(Tokens.size() - 1);

                    switch (s) {
                        case "+":

                            if (Temp1.startsWith("-") && Temp2.startsWith("-"))
                                Result = "-" + MathematicalOperations.AddHuge(Temp1.substring(1), Temp2.substring(1));
                            else if (Temp1.startsWith("-"))
                                Result = MathematicalOperations.SubtractHuge(Temp2, Temp1.substring(1));
                            else if (Temp2.startsWith("-"))
                                Result = MathematicalOperations.SubtractHuge(Temp1, Temp2.substring(1));
                            else Result = MathematicalOperations.AddHuge(Temp1, Temp2);

                            break;
                        case "-":

                            if (Temp1.startsWith("-") && Temp2.startsWith("-"))
                                Result = "-" + MathematicalOperations.SubtractHuge(Temp1.substring(1), Temp2.substring(1));
                            else if (Temp1.startsWith("-"))
                                Result = "-" + MathematicalOperations.AddHuge(Temp1.substring(1), Temp2);
                            else if (Temp2.startsWith("-"))
                                Result = MathematicalOperations.AddHuge(Temp1, Temp2.substring(1));
                            else {

                                Result = MathematicalOperations.SubtractHuge(Temp1, Temp2);

                            }

                            if (Result.startsWith("--")) Result = Result.substring(2);

                            break;
                        case "*":
                            if (Temp1.startsWith("-") && Temp2.startsWith("-"))
                                Result = MathematicalOperations.MultiplyHuge2(Temp1.substring(1), Temp2.substring(1));
                            else if (Temp1.startsWith("-"))
                                Result = "-" + MathematicalOperations.MultiplyHuge2(Temp1.substring(1), Temp2);
                            else if (Temp2.startsWith("-"))
                                Result = "-" + MathematicalOperations.MultiplyHuge2(Temp1, Temp2.substring(1));
                            else Result = MathematicalOperations.MultiplyHuge2(Temp1, Temp2);
                            break;
                        case "/":
                            if (Temp1.startsWith("-") && Temp2.startsWith("-"))
                                Result = MathematicalOperations.DivideHuge(Temp1.substring(1), Temp2.substring(1));
                            else if (Temp1.startsWith("-"))
                                Result = "-" + MathematicalOperations.DivideHuge(Temp1.substring(1), Temp2);
                            else if (Temp2.startsWith("-"))
                                Result = "-" + MathematicalOperations.DivideHuge(Temp1, Temp2.substring(1));
                            else Result = MathematicalOperations.DivideHuge(Temp1, Temp2);

                            break;
                        case "^":
                            Result = MathematicalOperations.PowerHuge(Temp1, Temp2);

                            break;
                    }
                    Tokens.add(Result);
                }
            }
        } catch (Exception e) {
            return "Either incorrect format or negative no. has been used" + e;
        }
        return Tokens.remove(0);
    }


    /*it Converts a no in string format to Linked list*/
    static LinkedList<Integer> StringToList(String Number, int FragmentSize) {
        LinkedList<Integer> intNumber = new LinkedList<>();

        while (Number.length() > FragmentSize) {

            int Size = Number.length();
            intNumber.add(Integer.parseInt(Number.substring(Size - FragmentSize, Size)));
            Number = Number.substring(0, Size - FragmentSize);

        }

        intNumber.add(Integer.parseInt(Number));

        return intNumber;
    }

    /* This function compares Operators for there precednce level */
    static int operatorToPrecedence(String op) {
        switch (op) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return 4;
        }
    }

    /*It Simply checks if a string is an operator or not*/
    static boolean isOperator(String s) {
        s = s.trim();
        if (s.length() != 1) return false;
        if ((s.equals("(") || s.equals(")"))) return true;
        else return Operators.contains(s);
    }

    /*It Simply checks if a string is a Number or not*/
    static boolean isNumber(String s) {
        String master = "-0123456789.";
        s = s.trim();
        for (int i = 0; i < s.length(); i++) {
            String lttr = s.substring(i, i + 1);
            if (!master.contains(lttr)) return false;
        }
        return true;
    }

    /*itConverts an arethmatic expressions into Postfix equivalent. Benefit is there is no need of
     * precdence order or paranthesis in     Postfix Form*/
    static String InfixToPostfix(String input) {
        ArrayList<String> output = new ArrayList<>();
        input = input.replaceAll("\\s+", "");
        Stack<String> OperatorStack = new Stack<>();
        boolean PreviousTokenNegative = true, UnaryNegativeOccured = false;
        for (int i = 0; i < input.length(); i++) {
            String currentToken = input.substring(i, i + 1);

            if (isOperator(currentToken)) {
                if (PreviousTokenNegative && currentToken.equals("-")) {
                    UnaryNegativeOccured = true;
                    continue;
                }
                //if(currentToken.equals("("))
                PreviousTokenNegative = true;
                if (OperatorStack.size() == 0) OperatorStack.push(currentToken);
                else if (currentToken.equals(")")) {

                    while (OperatorStack.size() > 0 && !OperatorStack.peek().equals("(")) {
                        output.add(OperatorStack.pop());
                    }
                    OperatorStack.pop();
                } else {

                    if ((currentToken.equals("(") && OperatorStack.peek().equals("(")) || (!currentToken.equals("(") && operatorToPrecedence(OperatorStack.peek()) >= operatorToPrecedence(currentToken))) {
                        while (OperatorStack.size() > 0 && !OperatorStack.peek().equals("(") && operatorToPrecedence(OperatorStack.peek()) >= operatorToPrecedence(currentToken)) {
                            output.add(OperatorStack.pop());
                        }
                        OperatorStack.push(currentToken);
                    } else if (operatorToPrecedence(OperatorStack.peek()) < operatorToPrecedence(currentToken)) {
                        OperatorStack.push(currentToken);
                    }
                }

            } else if (isNumber(currentToken)) {
                PreviousTokenNegative = false;
                // need a while loop to keep concatenating all numbers into one string that we end at end of while loop
                if (UnaryNegativeOccured) {
                    UnaryNegativeOccured = false;
                    currentToken = "-" + currentToken;
                }
                numberLoop:
                while (i + 1 < input.length()) {
                    String nxtLttr = input.substring(i + 1, i + 2);
                    if (nxtLttr.equals("-")) //then it's a subtraction sign not a unary negative sign
                        break numberLoop;
                    if (isNumber(nxtLttr)) {
                        currentToken += nxtLttr;
                        i++;
                    } else break numberLoop;
                }
                try {//in case it is only dots and or negative signs
                    output.add(currentToken);
                } catch (NumberFormatException e) {
                    System.out.println(currentToken + " is not a valid number");
                }
            }

        }
        while (OperatorStack.size() > 0) {
            output.add(OperatorStack.pop());
        }
        return output.toString().replace(", ", " ").replace("]", "").substring(1);
    }

    /*Checks if one string is greater than another*/
    static Boolean isGreater(String Larger, String Smaller) {
        if (Larger.length() > Smaller.length()) return true;
        if (Larger.length() < Smaller.length()) {

            return false;
        }
        for (int i = 0; i < Larger.length(); i++) {

            if (Integer.parseInt(Larger.substring(i, i + 1)) < Integer.parseInt(Smaller.substring(i, i + 1))) {

                return false;
            }
        }
        return true;
    }
}
