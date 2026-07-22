// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "String" = yeh method ek text (String) return karega — common prefix
    // "longestCommonPrefix" = method ka naam
    // "String[] strs" = parameter — words ka array jo humein input mein milega
    public String longestCommonPrefix(String[] strs) {

        // ===== EDGE CASE CHECK (agar array khaali hai) =====
        // agar array mein koi word hi nahi hai, toh empty string return kar do
        if (strs == null || strs.length == 0) {
            return "";
        }

        // "prefix" hamara "current best guess" hai — shuru mein poora PEHLA word hi le lete hain
        // strs[0] = array ka pehla element
        String prefix = strs[0];

        // ===== YEH LOOP HAI (for loop) =====
        // "i" counter hai, 1 se shuru kiya (0 nahi, kyunki index 0 already prefix hai)
        // baaki sab words ke saath compare karenge
        for (int i = 1; i < strs.length; i++) {

            // ===== YEH INNER LOOP HAI (while loop) =====
            // jab tak current word (strs[i]) hamare prefix se SHURU (startsWith) nahi hota,
            // tab tak prefix ko CHHOTA karte jao
            // strs[i].startsWith(prefix) = check karta hai ki strs[i], prefix se shuru hota hai ya nahi
            while (!strs[i].startsWith(prefix)) {

                // prefix ka aakhri letter kaat do (chhota kar do)
                // substring(0, prefix.length() - 1) = shuru se lekar last letter se pehle tak ka text
                prefix = prefix.substring(0, prefix.length() - 1);

                // ===== SAFETY CHECK =====
                // agar prefix bilkul empty ho gaya (matlab koi bhi common letter nahi mila),
                // toh seedha empty string return kar do, aage check karne ka koi fayda nahi
                if (prefix.isEmpty()) {
                    return "";
                }
            }
            // jaise hi inner while loop ruk jaata hai, matlab prefix ab is word (strs[i])
            // ke saath match ho raha hai. Ab outer loop agle word (strs[i+1]) ki taraf jaayega
        }

        // saare words ke saath match ho chuka prefix return kar do
        return prefix;
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        // Solution class ka object bana rahe hain
        Solution sol = new Solution();

        // Test case 1
        String[] words1 = {"flower", "flow", "flight"};
        System.out.println("Test 1 Output: \"" + sol.longestCommonPrefix(words1) + "\""); // Expected: "fl"

        // Test case 2 — koi common prefix nahi hai
        String[] words2 = {"dog", "racecar", "car"};
        System.out.println("Test 2 Output: \"" + sol.longestCommonPrefix(words2) + "\""); // Expected: ""

        // Test case 3 — sab same words
        String[] words3 = {"interspecies", "interstellar", "interstate"};
        System.out.println("Test 3 Output: \"" + sol.longestCommonPrefix(words3) + "\""); // Expected: "inters"
    }
}
