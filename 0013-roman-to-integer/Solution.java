// Yeh imports hain — HashMap use karne ke liye zaroori hai
import java.util.HashMap;
import java.util.Map;

// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "int" = yeh method ek number (integer) return karega
    // "romanToInt" = method ka naam
    // "String s" = parameter — Roman numeral text jo humein input mein milega
    public int romanToInt(String s) {

        // ===== YEH EK "LOOKUP TABLE" HAI (HashMap) =====
        // HashMap ek "key-value" box hai — yaha "key" symbol (Character) hai
        // aur "value" uski numeric value (Integer) hai
        // isse humein baar-baar if-else likhne ki zaroorat nahi padegi,
        // seedha map.get('X') karke value nikaal lenge
        Map<Character, Integer> values = new HashMap<>();
        values.put('I', 1);
        values.put('V', 5);
        values.put('X', 10);
        values.put('L', 50);
        values.put('C', 100);
        values.put('D', 500);
        values.put('M', 1000);

        // yeh variable final answer (total) store karega, shuru mein 0
        int total = 0;

        // ===== YEH LOOP HAI (for loop) =====
        // "i" ek counter hai, 0 se lekar string ke last character tak jaayega
        // s.length() = string mein kitne characters hain
        for (int i = 0; i < s.length(); i++) {

            // current character ki value nikal rahe hain map se
            // s.charAt(i) = string ke index "i" pe jo character hai wo deta hai
            int currentValue = values.get(s.charAt(i));

            // ===== YEH CONDITION HAI (if-else) — subtraction case check kar rahe hain =====
            // check kar rahe hain: kya current character, LAST character nahi hai (i+1 < length)
            // AUR agle character ki value, current se BADI hai?
            // (jaise "IV" mein I=1 hai aur agla V=5 hai, 1 < 5, toh yeh subtraction case hai)
            if (i + 1 < s.length() && currentValue < values.get(s.charAt(i + 1))) {
                // subtraction case hai, toh current value ko total mein se GHATA do
                total -= currentValue;
            } else {
                // normal case hai, toh current value ko total mein JOD do
                total += currentValue;
            }
        }

        // poora calculate hua total (final number) return kar do
        return total;
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        // Solution class ka object bana rahe hain
        Solution sol = new Solution();

        // Test case 1
        System.out.println("Test 1 Output: " + sol.romanToInt("III"));      // Expected: 3

        // Test case 2 — subtraction case
        System.out.println("Test 2 Output: " + sol.romanToInt("LVIII"));    // Expected: 58

        // Test case 3 — multiple subtraction cases
        System.out.println("Test 3 Output: " + sol.romanToInt("MCMXCIV"));  // Expected: 1994
    }
}
