// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "String" = yeh method ek text (String) return karega, jo Roman numeral hoga
    // "intToRoman" = method ka naam
    // "int num" = parameter — jo number humein Roman mein convert karna hai
    public String intToRoman(int num) {

        // ===== YEH DO ARRAYS EK "LOOKUP TABLE" HAIN =====
        // values[] mein saari possible values hain, SABSE BADI se SABSE CHHOTI order mein
        // isme normal symbols (1000, 500, 100...) aur subtraction cases (900, 400, 90...) dono hain
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        // symbols[] mein har value ke corresponding Roman symbol hai
        // values[0]="1000" ka symbol symbols[0]="M" hai, aur aise hi aage
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        // StringBuilder ek "text banane wala box" hai — String se fast hai jab baar-baar
        // text jodna (append) ho, kyunki normal String har baar naya banata hai
        StringBuilder result = new StringBuilder();

        // ===== YEH LOOP HAI (for loop) =====
        // "i" ek counter hai jo 0 se shuru hoga aur values.length-1 tak jaayega
        // yani hum values[] array ke har element ko ek-ek karke check karenge,
        // SABSE BADI value se shuru karke
        for (int i = 0; i < values.length; i++) {

            // ===== YEH INNER LOOP HAI (while loop) =====
            // jab tak humara "num" is current value se BADA ya BARABAR hai,
            // tab tak hum baar-baar isi value ko use karte rahenge
            // (jaise ek hi denomination ki multiple "notes" use karna)
            while (num >= values[i]) {

                // num mein se current value ko subtract (ghata) do
                num -= values[i];

                // result text mein corresponding symbol jod (append) do
                result.append(symbols[i]);
            }
            // jab num is value se chhota ho jaata hai, inner loop khatam ho jaata hai
            // aur outer loop agli (chhoti) value pe chala jaata hai
        }

        // poora banaya hua Roman numeral text return kar do
        // .toString() StringBuilder ko normal String mein badalta hai
        return result.toString();
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        // Solution class ka object bana rahe hain
        Solution sol = new Solution();

        // Test case 1
        System.out.println("Test 1 Output: " + sol.intToRoman(3749)); // Expected: MMMDCCXLIX

        // Test case 2
        System.out.println("Test 2 Output: " + sol.intToRoman(58));   // Expected: LVIII

        // Test case 3 — subtraction case wala tricky example
        System.out.println("Test 3 Output: " + sol.intToRoman(1994)); // Expected: MCMXCIV
    }
}
