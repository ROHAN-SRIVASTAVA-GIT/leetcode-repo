// Yeh imports List/ArrayList use karne ke liye zaroori hain
import java.util.ArrayList;
import java.util.List;

// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "List<String>" = yeh method saari valid combinations ki list return karega
    // "generateParenthesis" = method ka naam
    // "int n" = kitne pairs of parentheses banane hain
    public List<String> generateParenthesis(int n) {

        // yeh final answer store karega
        List<String> result = new ArrayList<>();

        // ===== BACKTRACKING SHURU KARNA =====
        // StringBuilder ek "current combination banane wala box" hai
        // helper method ko shuru mein 0 open aur 0 close brackets ke saath call kar rahe hain
        backtrack(result, new StringBuilder(), 0, 0, n);

        // saari banayi hui valid combinations return kar do
        return result;
    }

    // ===== YEH "HELPER METHOD" HAI JO KHUD KO BAAR-BAAR CALL KARTA HAI (RECURSION) =====
    // Parameters:
    //   result = final answer list
    //   current = ab tak jo string bani hai (StringBuilder mein)
    //   openCount = ab tak kitne '(' use kiye hain
    //   closeCount = ab tak kitne ')' use kiye hain
    //   n = total kitne pairs chahiye
    private void backtrack(List<String> result, StringBuilder current, int openCount, int closeCount, int n) {

        // ===== BASE CASE (recursion kab RUKEGA) =====
        // current string ki length "2*n" ho gayi, matlab saare brackets use ho gaye
        // (n opening + n closing = 2n total characters) — ek COMPLETE combination ban gayi
        if (current.length() == 2 * n) {
            // current.toString() se String banake result list mein add kar do
            result.add(current.toString());
            return; // yahin se wapas chale jao
        }

        // ===== CHOICE 1: OPENING BRACKET '(' DAALNA =====
        // ===== PRUNING CONDITION: sirf tabhi daalo agar abhi tak ke openCount, n se KAM hai =====
        // (warna hum n se zyada '(' daal denge, jo galat hoga)
        if (openCount < n) {
            // CHOOSE: '(' current string mein jodo
            current.append('(');

            // EXPLORE: recursion se aage badho, openCount ko 1 se badha ke
            backtrack(result, current, openCount + 1, closeCount, n);

            // UN-CHOOSE (backtrack): abhi jo '(' jodha tha use hata do,
            // taaki agla option (closing bracket) try kar sakein
            current.deleteCharAt(current.length() - 1);
        }

        // ===== CHOICE 2: CLOSING BRACKET ')' DAALNA =====
        // ===== PRUNING CONDITION: sirf tabhi daalo agar abhi tak ke closeCount, openCount se KAM hai =====
        // (warna koi ')' bina matching '(' ke aa jaayega, jaise invalid string ")(")
        if (closeCount < openCount) {
            // CHOOSE: ')' current string mein jodo
            current.append(')');

            // EXPLORE: recursion se aage badho, closeCount ko 1 se badha ke
            backtrack(result, current, openCount, closeCount + 1, n);

            // UN-CHOOSE (backtrack): abhi jo ')' jodha tha use hata do
            current.deleteCharAt(current.length() - 1);
        }
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test case 1
        System.out.println("Test 1 Output: " + sol.generateParenthesis(3));
        // Expected: [((())), (()()), (())(), ()(()), ()()()]

        // Test case 2 — sirf 1 pair
        System.out.println("Test 2 Output: " + sol.generateParenthesis(1));
        // Expected: [()]
    }
}
