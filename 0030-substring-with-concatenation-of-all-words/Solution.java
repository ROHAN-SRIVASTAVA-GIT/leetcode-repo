// Yeh imports List/ArrayList/Map/HashMap use karne ke liye zaroori hain
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "List<Integer>" = yeh method saari valid starting indices ki list return karega
    // "findSubstring" = method ka naam
    // "String s" = bada string, "String[] words" = jinko jodke dhundhna hai
    public List<Integer> findSubstring(String s, String[] words) {

        // yeh final answer store karega
        List<Integer> result = new ArrayList<>();

        // ===== EDGE CASE CHECK =====
        if (s == null || words == null || words.length == 0) {
            return result;
        }

        // "wordLen" = har word ki length (saare words ki length SAME hoti hai)
        int wordLen = words[0].length();
        // "numWords" = kitne words hain
        int numWords = words.length;
        // "windowLen" = poori window ki size (jitna bada substring dhundhna hai)
        int windowLen = wordLen * numWords;
        // "n" = poore string "s" ki length
        int n = s.length();

        // agar s hi itna chhota hai ki poori window fit nahi hogi, toh khaali return karo
        if (n < windowLen) {
            return result;
        }

        // ===== EXPECTED FREQUENCY MAP BANANA =====
        // "words" mein har word kitni baar aana chahiye, yeh HashMap mein store kar rahe hain
        Map<String, Integer> wordCount = new HashMap<>();
        for (String w : words) {
            wordCount.merge(w, 1, Integer::sum); // merge: agar pehle se hai toh +1, warna 1 se shuru
        }

        // ===== YEH OUTER LOOP HAI (for loop) — DIFFERENT "OFFSETS" TRY KAR RAHE HAIN =====
        // words hamesha wordLen ke multiples pe start ho sakte hain, lekin humein pata
        // nahi ki window kaha se shuru hogi — isliye 0 se wordLen-1 tak har starting
        // offset try karte hain (isse saari possibilities cover ho jaati hain)
        for (int offset = 0; offset < wordLen; offset++) {

            // "left" is offset ke liye current window ka SHURU hai
            int left = offset;
            // "count" batata hai ki window ke andar ABHI kitne VALID words hain
            int count = 0;
            // is offset ke liye window ke andar ke words ki ACTUAL frequency
            Map<String, Integer> windowCount = new HashMap<>();

            // ===== YEH INNER LOOP HAI — WINDOW KO "wordLen" KE STEPS MEIN SLIDE KARTA HAI =====
            for (int right = offset; right + wordLen <= n; right += wordLen) {

                // current chunk (window ke naye "right" end wala word) nikal rahe hain
                String word = s.substring(right, right + wordLen);

                // ===== CONDITION: kya yeh word "words" list mein maujood hai? =====
                if (wordCount.containsKey(word)) {

                    // is word ki count window mein badha do
                    windowCount.merge(word, 1, Integer::sum);
                    count++;

                    // ===== AGAR YEH WORD "ZAROORAT SE ZYADA" AA GAYA (duplicate se zyada) =====
                    // toh window ko LEFT se chhota karte jao (extra copies hatao) jab tak
                    // is word ki frequency, expected frequency se zyada na ho
                    while (windowCount.get(word) > wordCount.get(word)) {
                        String leftWord = s.substring(left, left + wordLen);
                        windowCount.merge(leftWord, -1, Integer::sum);
                        count--;
                        left += wordLen; // window ka left end aage khisak gaya
                    }

                    // ===== CHECK: kya window mein SAARE numWords words aa chuke hain? =====
                    if (count == numWords) {
                        // "left" hi is valid window ka starting index hai
                        result.add(left);

                        // window ko AAGE badhane ke liye, left wala word hata do
                        // (taaki agla naya word window mein aa sake)
                        String leftWord = s.substring(left, left + wordLen);
                        windowCount.merge(leftWord, -1, Integer::sum);
                        count--;
                        left += wordLen;
                    }

                } else {
                    // ===== word "words" list mein hai hi nahi — window "toot" gayi =====
                    // poori window reset kar do, kyunki yeh invalid word beech mein aa gaya
                    windowCount.clear();
                    count = 0;
                    left = right + wordLen; // naya window is invalid word ke BAAD se shuru hoga
                }
            }
        }

        // saari mili hui starting indices return kar do
        return result;
    }
}
