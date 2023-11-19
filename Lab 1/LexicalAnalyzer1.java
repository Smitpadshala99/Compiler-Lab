import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LexicalAnalyzer1 {
    
    public static boolean isKeyword(String input) {
        String[] keywords = {"break", "case", "char", "continue", "default",
                "do", "double", "else", "enum", "extern", "float", "for", "if", "int", "long", 
                "return", "static", "switch", "void", "while"};
        for (String keyword : keywords) {
            if (keyword.equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        char ch;
        char[] buffer = new char[15];
        char[] operators = {'+', '-', '*', '/', '%'};
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("input.java"));
            int i, j = 0;

            while ((ch = (char) br.read()) != (char) -1) {
                for (i = 0; i < operators.length; ++i) {
                    if (ch == operators[i])
                        System.out.println(ch + " is operator");
                }

                if (Character.isLetterOrDigit(ch)) {
                    buffer[j++] = ch;
                } else if ((ch == ' ' || ch == '\n') && (j != 0)) {
                    String identifier = new String(buffer, 0, j);
                    j = 0;

                    if (isKeyword(identifier)) {
                        System.out.println(identifier + " is keyword");
                    } else {
                        System.out.println(identifier + " is identifier");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file");
            e.printStackTrace();
        }
    }
}
