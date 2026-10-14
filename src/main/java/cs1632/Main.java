package cs1632;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

public class Main {
    private static Boolean endsSequence(Token tok) {
        if (tok == null) return true;
        if (tok instanceof OpToken) {
            if (((OpToken) tok).getOperator() == ';') return true;
            if (((OpToken) tok).getOperator() == '.') return true;
        }
        return false;
    }

    private static ArrayList<Token> getNextSequence(Tokenizer toknizr) throws IOException {
        ArrayList<Token> tokens = new ArrayList<>();
        Token tok;
        do {
            tok = toknizr.nextToken();
            if (tok != null) tokens.add(tok);
        } while (!endsSequence(tok));
        if (tok == null) return null;
        if (tok instanceof OpToken) {
            if (((OpToken) tok).getOperator() == '.') {
                return null;
            }
        }
        return tokens;
    }

    public static void main(String[] args) {
        try {
            Tokenizer myScanner = new Tokenizer(System.in);
            ArrayList<Token> tokenSequence;
            do {
                System.out.print("Equation: ");
                tokenSequence = getNextSequence(myScanner);
                if (tokenSequence != null) {
                    int sum = evaluateExpression(tokenSequence);

                    System.out.print("Result: ");
                    System.out.println(sum);
                }
            } while (tokenSequence != null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static int evaluateExpression(ArrayList<Token> tokenSequence) {
        Stack<Token> tokStack = new Stack<>();

        // Iterate backwards from the end of the sequence (ignoring the trailing semicolon)
        for (int i = tokenSequence.size() - 2; i >= 0; i--) {
            Token tok = tokenSequence.get(i);
            if (tok.getTokenString().equals(";")) {
                continue;
            }
            if (tok.getTokenType().equals("op")) {
                OpToken opToken = (OpToken) tok;

                // Pop the two operands
                NumToken tok1 = (NumToken) tokStack.pop();
                NumToken tok2 = (NumToken) tokStack.pop();

                int result = 0;

                switch (opToken.getOperator()) {
                    case '*':
                        result = tok1.value * tok2.value;
                        break;
                    case '/':
                        // tok1 is evaluated first, so it's the numerator in prefix order
                        result = tok1.value / tok2.value;
                        break;
                    case '+':
                        result = tok1.value + tok2.value;
                        break;
                    case '-':
                        // tok1 is the left operand, tok2 is the right operand (tok1 - tok2)
                        result = tok1.value - tok2.value;
                        break;
                }

                tok = new NumToken(String.valueOf(result));
            }

            tokStack.add(tok);
        }

        // A valid prefix expression should leave exactly one NumToken on the stack
        if (!tokStack.isEmpty() && tokStack.peek() instanceof NumToken) {
            return ((NumToken) tokStack.pop()).value;
        }

        return 0;
    }
}