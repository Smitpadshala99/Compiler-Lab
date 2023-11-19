// Smit Padshala
// 21BCP187
import java.io.*;
import java.util.*;

public class lexical_analysis {

    public static void main(String[] args) {
        String fileName = "input.java";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            ArrayList<String> keywordList = new ArrayList<>();
            ArrayList<String> identifierList = new ArrayList<>();
            ArrayList<String> stringLiteralList = new ArrayList<>();
            ArrayList<String> numberList = new ArrayList<>();
            ArrayList<String> otherList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                analyzeLine(line, keywordList, identifierList, stringLiteralList, numberList, otherList);
            }

            reader.close();

            System.out.println("Keywords: " + keywordList);
            System.out.println("Identifiers: " + identifierList);
            System.out.println("String Literals: " + stringLiteralList);
            System.out.println("Numbers: " + numberList);
            System.out.println("Others: " + otherList);

        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public static void analyzeLine(String line, ArrayList<String> keywordList,
                                   ArrayList<String> identifierList, ArrayList<String> stringLiteralList,
                                   ArrayList<String> numberList, ArrayList<String> otherList) {
        

        StringBuilder currentToken = new StringBuilder();
        boolean insideStringLiteral = false;

        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);

            if (currentChar == '\"') {
                if (insideStringLiteral) {
                    currentToken.append(currentChar);
                    stringLiteralList.add(currentToken.toString());
                    System.out.println("String Literal: " + currentToken.toString());
                    currentToken.setLength(0);
                }
                insideStringLiteral = !insideStringLiteral;
            } else if (insideStringLiteral) {
                currentToken.append(currentChar);
            } else if (Character.isWhitespace(currentChar)) {
                processToken(currentToken.toString(), keywordList, identifierList, stringLiteralList, numberList, otherList);
                currentToken.setLength(0);
            } else {
                currentToken.append(currentChar);
            }
        }

        processToken(currentToken.toString(), keywordList, identifierList, stringLiteralList, numberList, otherList);
    }

    public static void processToken(String token, ArrayList<String> keywordList,
                                    ArrayList<String> identifierList, ArrayList<String> stringLiteralList,
                                    ArrayList<String> numberList, ArrayList<String> otherList) {
        token = token.trim();
        

        String[] keywords = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", 
            "const", "continue", "default", "do", "double", "else", "enum", "exports", "extends", "final", 
            "finally", "float", "for", "if", "implements", "import", "instanceof", "int", "interface", "long", 
            "module", "native", "new", "open", "opens", "package", "private", "protected", "provides", "public", 
            "requires", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", 
            "throws", "transient", "transitive", "try", "var", "void", "volatile", "while", "with"};
        
        if (token.isEmpty()) {
            return;
        }

        if (Arrays.asList(keywords).contains(token)) {
            keywordList.add(token);
            System.out.println(token + " : Keyword");
        } else if (isValidIdentifier(token)) {
            identifierList.add(token);
            System.out.println(token + " : Identifiers");
        } else if (isStringLiteral(token)) {
            stringLiteralList.add(token);
            System.out.println(token + " : String Literals");
        } else if (isNumber(token)) {
            numberList.add(token);
            System.out.println(token + " : Numbers");
        } else {
            otherList.add(token);
            System.out.println(token + " : Others");
        }
    }


    public static boolean isValidIdentifier(String word) {
        char firstChar = word.charAt(0);
        if (!Character.isLetter(firstChar) && firstChar != '_') {
            return false;
        } else{
            for (int i = 1; i < word.length(); i++) {
                char currentChar = word.charAt(i);
                if (!Character.isLetterOrDigit(currentChar) && currentChar != '_') {
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean isStringLiteral(String token) {
        return token.startsWith("\"") && token.endsWith("\"");
    }

    public static boolean isNumber(String word) {
        try {
            Double.parseDouble(word);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
