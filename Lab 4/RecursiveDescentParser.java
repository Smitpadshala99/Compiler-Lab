// 21BCP187
// Smit Padshala
import java.util.*;

public class RecursiveDescentParser {
    static int ptr;
    static char[] input;

    public static void main(String args[]) {
        System.out.println("Enter the input string:");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        input = s.toCharArray();
        if (input.length < 1) {
            System.out.println("The input string is invalid.");
            System.exit(0);
        }
        ptr = 0;
        boolean isValid = E();
        if ((isValid) & (ptr == input.length)) {
            System.out.println("The input string is valid.");
        } else {
            System.out.println("The input string is invalid.");
        }
    }

    static boolean E() {
        int fallback = ptr;
        if (T()) {
            if (EPrime()) {
                return true;
            }
        }
        ptr = fallback;
        return false;
    }

    static boolean EPrime() {
        int fallback = ptr;
        if (ptr < input.length && (input[ptr] == '+' || input[ptr] == '-')) {
            ptr++;
            if (T()) {
                if (EPrime()) {
                    return true;
                }
            }
            ptr = fallback;
            return false;
        }
        return true; 
    }
    
    static boolean T() {
        int fallback = ptr;
        if (F()) {
            if (TPrime()) {
                return true;
            }
        }
        ptr = fallback;
        return false;
    }

    static boolean TPrime() {
        int fallback = ptr;
        if (ptr < input.length && (input[ptr] == '*' || input[ptr] == '/')) {
            ptr++;
            if (F()) {
                if (TPrime()) {
                    return true;
                }
            }
            ptr = fallback;
            return false;
        }
        return true; 
    }
    
    static boolean F() {
        int fallback = ptr;
        if (P()) {
            if (FPrime()) {
                return true;
            }
        }
        ptr = fallback;
        return false;
    }

    static boolean FPrime() {
        int fallback = ptr;
        if (ptr < input.length && input[ptr] == '^') {
            ptr++;
            if (F()) {
                return true;
            }
            ptr = fallback;
            return false;
        }
        return true; 
    }    

    static boolean P() {
        int fallback = ptr;
        if (ptr < input.length && input[ptr] == '(') {
            ptr++;
            if (E()) {
                if (ptr < input.length && input[ptr] == ')') {
                    ptr++;
                    return true;
                }
            }
            ptr = fallback;
            return false;
        } else if (ptr < input.length && input[ptr] == 'i') {
            ptr++;
            return true;
        }
        return false;
    }    
}
