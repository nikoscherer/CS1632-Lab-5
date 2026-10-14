package cs1632;

import java.io.IOException;
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
    private static Stack<Token> getNextSequence(Tokenizer toknizr) throws IOException {
        Stack<Token> tokens = new Stack<>();
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
            Stack<Token> tokenSequence;
            ArrayList<Token> postfixSequence = null;
            do {
                System.out.print("Equation: ");
                tokenSequence = getNextSequence(myScanner);
                if (tokenSequence != null) {
                    // Replace the code starting here with code to evaluate the expression

                    int sum = 0;
                    for (int i = 0; i < tokenSequence.size(); i++) {

                    }


                    System.out.print("Read equation: ");
                    for (Token tok : tokenSequence) {
                        System.out.print(tok.getTokenString() + " ");
                    }
                    System.out.println();
                    // End where to replace
                }
            } while (tokenSequence != null);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}