// Smit Padshala
// 21BCP187
import java.util.*;

public class first_and_follow_set {
    
    static char nonTerminals[], terminals[];
    static int numNonTerminals, numTerminals;
    static String grammar[][], first[], follow[];
    
    static String calculateFirst(int i) {
        StringBuilder temp = new StringBuilder();
        for (String production : grammar[i]) {
            int k = 0;
            while (k < production.length()) {
                char symbol = production.charAt(k);
                if (isTerminal(symbol)) {
                    temp.append(symbol);
                    break;
                } else if (isNonTerminal(symbol)) {
                    String subFirst = calculateFirst(getNonTerminalIndex(symbol));
                    if (!subFirst.contains("@")) {
                        temp.append(subFirst);
                        break;
                    } else {
                        temp.append(subFirst.replace("@", ""));
                        k++;
                    }
                } else {
                    temp.append(symbol);
                    break;
                }
            }
        }
        return temp.toString();
    }

    static String calculateFollow(int i) {
        StringBuilder temp = new StringBuilder();
        if (i == 0)
            temp.append("$");

        for (int j = 0; j < numNonTerminals; j++) {
            for (String production : grammar[j]) {
                for (int k = 0; k < production.length(); k++) {
                    char symbol = production.charAt(k);
                    if (symbol == nonTerminals[i]) {
                        if (k == production.length() - 1) {
                            if (j < i)
                                temp.append(follow[j]);
                        } else {
                            for (int m = k + 1; m < production.length(); m++) {
                                char nextSymbol = production.charAt(m);
                                if (isTerminal(nextSymbol)) {
                                    temp.append(nextSymbol);
                                    break;
                                } else if (isNonTerminal(nextSymbol)) {
                                    String subFirst = calculateFirst(getNonTerminalIndex(nextSymbol));
                                    if (!subFirst.contains("@")) {
                                        temp.append(subFirst);
                                        break;
                                    } else {
                                        temp.append(subFirst.replace("@", ""));
                                        if (m == production.length() - 1) {
                                            temp.append(calculateFollow(j));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return temp.toString();
    }

    static int getNonTerminalIndex(char symbol) {
        for (int i = 0; i < numNonTerminals; i++) {
            if (nonTerminals[i] == symbol)
                return i;
        }
        return -1;
    }

    static boolean isTerminal(char symbol) {
        return Arrays.binarySearch(terminals, symbol) >= 0;
    }

    static boolean isNonTerminal(char symbol) {
        return Arrays.binarySearch(nonTerminals, symbol) >= 0;
    }

    static String removeDuplicates(String str) {
        StringBuilder sb = new StringBuilder();
        boolean seen[] = new boolean[256];
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!seen[ch]) {
                seen[ch] = true;
                sb.append(ch+" ");
            }
        }
        return sb.toString();
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the non-terminals:");
        String nonTerminalsInput = scanner.nextLine();
        numNonTerminals = nonTerminalsInput.length();
        nonTerminals = nonTerminalsInput.toCharArray();

        System.out.println("Enter the terminals:");
        String terminalsInput = scanner.nextLine();
        numTerminals = terminalsInput.length();
        terminals = terminalsInput.toCharArray();

        System.out.println("Specify the grammar (Enter @ for epsilon production):");
        grammar = new String[numNonTerminals][];
        for (int i = 0; i < numNonTerminals; i++) {
            System.out.println("Enter the number of productions for " + nonTerminals[i]);
            int numProductions = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            grammar[i] = new String[numProductions];
            System.out.println("Enter the productions:");
            for (int j = 0; j < numProductions; j++)
                grammar[i][j] = scanner.nextLine();
        }

        first = new String[numNonTerminals];
        for (int i = 0; i < numNonTerminals; i++)
            first[i] = calculateFirst(i);

        System.out.println("First Set:");
        for (int i = 0; i < numNonTerminals; i++)
            System.out.println(nonTerminals[i] + " -> {" + removeDuplicates(first[i]) + "}");

        follow = new String[numNonTerminals];
        for (int i = 0; i < numNonTerminals; i++)
            follow[i] = calculateFollow(i);

        System.out.println("Follow Set:");
        for (int i = 0; i < numNonTerminals; i++)
            System.out.println(nonTerminals[i] + " -> {" + removeDuplicates(follow[i]) + "}");

        scanner.close();
    }

}
