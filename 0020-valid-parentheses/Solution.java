// Yeh imports Stack aur HashMap use karne ke liye zaroori hain
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "boolean" = yeh method sirf true ya false return karega (valid hai ya nahi)
    // "isValid" = method ka naam
    // "String s" = parameter — brackets wali string jo humein check karni hai
    public boolean isValid(String s) {

        // ===== YEH EK "LOOKUP TABLE" HAI (HashMap) =====
        // key = closing bracket, value = uska matching opening bracket
        // isse humein baar-baar if-else likhne ki zaroorat nahi padegi
        Map<Character, Character> matchingPairs = new HashMap<>();
        matchingPairs.put(')', '(');
        matchingPairs.put('}', '{');
        matchingPairs.put(']', '[');

        // ===== STACK BANA RAHE HAIN =====
        // Stack ek "plates ka dhair" hai — sirf top se hi add (push) ya remove (pop) kar sakte hain
        // ismein hum har OPENING bracket ko daalte jaayenge
        Stack<Character> stack = new Stack<>();

        // ===== YEH LOOP HAI (for loop) =====
        // "c" har character hai jo string "s" mein ek-ek karke aayega
        for (char c : s.toCharArray()) {

            // ===== CONDITION: kya yeh ek OPENING bracket hai? =====
            // check kar rahe hain ki current character '(' ya '{' ya '[' hai
            if (c == '(' || c == '{' || c == '[') {
                // opening bracket hai, toh use stack ke UPAR rakh do (push)
                stack.push(c);

            } else {
                // ===== warna yeh ek CLOSING bracket hai (), }, ] =====

                // ===== SAFETY CHECK 1: stack khaali toh nahi hai? =====
                // agar stack khaali hai, matlab koi opening bracket hai hi nahi
                // is closing bracket ko match karne ke liye — toh turant INVALID
                if (stack.isEmpty()) {
                    return false;
                }

                // ===== TOP WALA OPENING BRACKET NIKALNA (pop) =====
                // stack.pop() sabse upar wala element nikaal ke deta hai, AUR use stack se hata deta hai
                char topOpening = stack.pop();

                // ===== SAFETY CHECK 2: kya yeh sahi bracket match karta hai? =====
                // matchingPairs.get(c) = current closing bracket ka SAHI matching opening bracket
                // agar stack se nikla hua opening bracket, is sahi wale se MATCH NAHI karta...
                if (topOpening != matchingPairs.get(c)) {
                    // ...toh INVALID hai (galat order mein band hua)
                    return false;
                }
                // agar match ho gaya, toh loop aage badhta hai (kuch return nahi karna)
            }
        }

        // ===== FINAL CHECK =====
        // saara string process ho gaya. Ab agar stack KHAALI hai, matlab saare
        // opening brackets ko unka matching closing bracket mil gaya — VALID hai
        // agar stack mein KUCH BACHA hai, matlab kuch opening bracket band hi nahi hua — INVALID
        return stack.isEmpty();
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        // Solution class ka object bana rahe hain
        Solution sol = new Solution();

        // Test case 1
        System.out.println("Test 1 Output: " + sol.isValid("()[]{}"));  // Expected: true

        // Test case 2 — galat matching (( se ] match nahi karta)
        System.out.println("Test 2 Output: " + sol.isValid("(]"));      // Expected: false

        // Test case 3 — galat order (( aur [ ek doosre ke andar-bahar ho gaye)
        System.out.println("Test 3 Output: " + sol.isValid("([)]"));    // Expected: false
    }
}
