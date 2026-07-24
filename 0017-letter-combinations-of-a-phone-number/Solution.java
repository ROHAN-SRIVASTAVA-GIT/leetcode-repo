// Yeh imports List/ArrayList use karne ke liye zaroori hain
import java.util.ArrayList;
import java.util.List;

// Yeh poora file ek "class" hai
public class Solution {

    // ===== YEH EK "LOOKUP TABLE" HAI =====
    // "private" = sirf isi class ke andar use ho sakta hai
    // "final" = ek baar set hone ke baad yeh badlega nahi
    // "static" = is array ki sirf ek hi copy banegi (object banate waqt baar-baar nahi banegi)
    // index 0 aur 1 khaali isliye hain kyunki phone keypad pe 0 aur 1 pe letters nahi hote
    // index 2 = "abc", index 3 = "def", waghera (jaise real phone keypad)
    private static final String[] KEYPAD = {
        "",     // 0
        "",     // 1
        "abc",  // 2
        "def",  // 3
        "ghi",  // 4
        "jkl",  // 5
        "mno",  // 6
        "pqrs", // 7
        "tuv",  // 8
        "wxyz"  // 9
    };

    // "public" = bahar se call ho sakta hai
    // "List<String>" = yeh method saari combinations ki ek list return karega
    // "letterCombinations" = method ka naam
    // "String digits" = input — jaise "23"
    public List<String> letterCombinations(String digits) {

        // yeh final answer store karega — saari combinations isme jaayengi
        List<String> result = new ArrayList<>();

        // ===== EDGE CASE CHECK =====
        // agar digits khaali hai, toh koi combination nahi banti — khaali list return karo
        if (digits == null || digits.isEmpty()) {
            return result;
        }

        // ===== BACKTRACKING SHURU KARNA =====
        // StringBuilder ek "current word banane wala box" hai jo hum step-by-step bharenge
        // helper method ko call kar rahe hain, jo asli kaam (recursion) karega
        backtrack(digits, 0, new StringBuilder(), result);

        // saari banayi hui combinations return kar do
        return result;
    }

    // ===== YEH "HELPER METHOD" HAI JO KHUD KO BAAR-BAAR CALL KARTA HAI (RECURSION) =====
    // "private" = sirf is class ke andar use hota hai
    // "void" = yeh kuch return nahi karta, seedha "result" list mein add karta jaata hai
    // Parameters:
    //   digits = original input jaise "23"
    //   index = ABHI hum KAUNSE digit pe kaam kar rahe hain (0 = pehla digit)
    //   current = ab tak jo word bana hai (StringBuilder mein)
    //   result = final answer list, jisme complete words jaate hain
    private void backtrack(String digits, int index, StringBuilder current, List<String> result) {

        // ===== BASE CASE (recursion kab RUKEGA) =====
        // agar index, digits ki length ke barabar ho gaya, matlab hum saare digits
        // process kar chuke hain — current word COMPLETE ho gaya hai
        if (index == digits.length()) {
            // current.toString() StringBuilder ko normal String mein badalta hai
            // usko result list mein add kar do
            result.add(current.toString());
            return; // yahin se wapas chale jao, aage kuch mat karo
        }

        // ===== CURRENT DIGIT KE LETTERS NIKALNA =====
        // digits.charAt(index) = current digit character nikalna, jaise '2'
        // '2' - '0' = 2 (character ko number mein convert karne ka trick,
        //   kyunki characters ke ASCII values order mein hote hain)
        int digit = digits.charAt(index) - '0';

        // is digit ke corresponding letters nikal liye, jaise digit=2 hai toh letters="abc"
        String letters = KEYPAD[digit];

        // ===== YEH LOOP HAI (for loop) — har possible letter try kar rahe hain =====
        // "i" ek counter hai jo letters string ke har character pe jaayega
        for (int i = 0; i < letters.length(); i++) {

            // ===== CHOICE LENA (choose) =====
            // current word ke aakhir mein yeh letter jod do
            current.append(letters.charAt(i));

            // ===== RECURSION (explore) =====
            // agle digit (index+1) ke liye yehi process phir se karo
            // yeh method KHUD KO CALL kar raha hai — isi ko "recursion" kehte hain
            backtrack(digits, index + 1, current, result);

            // ===== BACKTRACK KARNA (un-choose / undo) =====
            // jo letter abhi jodha tha, use HATA do (delete)
            // taaki agla letter try karne ke liye current word wapas SAHI state mein aa jaaye
            // deleteCharAt(length-1) = last character ko hata deta hai
            current.deleteCharAt(current.length() - 1);
        }
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        // Solution class ka object bana rahe hain
        Solution sol = new Solution();

        // Test case 1
        System.out.println("Test 1 Output: " + sol.letterCombinations("23"));
        // Expected: [ad, ae, af, bd, be, bf, cd, ce, cf]

        // Test case 2 — empty input
        System.out.println("Test 2 Output: " + sol.letterCombinations(""));
        // Expected: []

        // Test case 3 — sirf ek digit
        System.out.println("Test 3 Output: " + sol.letterCombinations("2"));
        // Expected: [a, b, c]
    }
}
