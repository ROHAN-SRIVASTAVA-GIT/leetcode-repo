// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "int" = yeh method starting index (ya -1 agar nahi mila) return karega
    // "strStr" = method ka naam (LeetCode ka diya hua naam)
    // "String haystack" = bada string, "String needle" = jo dhundhna hai
    public int strStr(String haystack, String needle) {

        // "n" = haystack ki length, "m" = needle ki length
        int n = haystack.length();
        int m = needle.length();

        // ===== YEH OUTER LOOP HAI (for loop) — WINDOW KI HAR STARTING POSITION TRY KAR RAHE HAIN =====
        // "i" window ka starting index hai. "n - m" tak isliye jaate hain kyunki
        // agar window yaha se aage shuru hui, toh needle jitni length fit hi nahi hogi
        for (int i = 0; i <= n - m; i++) {

            // ===== YEH INNER LOOP HAI (for loop) — WINDOW KE ANDAR CHARACTER-BY-CHARACTER COMPARE =====
            int j = 0;
            while (j < m && haystack.charAt(i + j) == needle.charAt(j)) {
                // agar match hua, "j" ko aage badhao (agla character check karne)
                j++;
            }

            // ===== CHECK KARO: kya POORA needle match ho gaya? =====
            // agar j, needle ki poori length (m) tak pahunch gaya, matlab SAB characters match hue
            if (j == m) {
                // yeh window ka starting index (i) hi humara answer hai
                return i;
            }
            // agar poora match nahi hua, "i" (outer loop) apne aap agli position try karega
        }

        // ===== YAHAN TAK POHONCHE, MATLAB KAHI BHI MATCH NAHI MILA =====
        return -1;
    }
}
