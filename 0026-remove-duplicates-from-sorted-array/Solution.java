// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "int" = yeh method unique elements ki GINTI return karega
    // "removeDuplicates" = method ka naam
    // "int[] nums" = sorted array jisme duplicates hain
    public int removeDuplicates(int[] nums) {

        // ===== EDGE CASE CHECK =====
        // agar array khaali hai, toh 0 unique elements hain
        if (nums.length == 0) {
            return 0;
        }

        // ===== "SLOW" POINTER =====
        // yeh batata hai "agla UNIQUE element kahan rakhna hai"
        // shuru mein 0 pe hai, kyunki nums[0] hamesha unique hota hai (koi pehle wala hi nahi)
        int slow = 0;

        // ===== YEH LOOP HAI (for loop) — "FAST" POINTER poore array ko explore karta hai =====
        // "fast" 1 se shuru hota hai kyunki index 0 ko humne already "unique" maan liya
        for (int fast = 1; fast < nums.length; fast++) {

            // ===== CONDITION: kya fast wala element, slow wale se ALAG hai? =====
            // array SORTED hai, isliye agar dono alag hain, matlab yeh EK NAYA unique number hai
            if (nums[fast] != nums[slow]) {

                // slow ko ek aage badhao (nayi jagah jahan unique element rakhna hai)
                slow++;

                // us nayi jagah pe, fast wala (naya unique) element COPY kar do
                nums[slow] = nums[fast];
            }
            // agar nums[fast] == nums[slow] hai, matlab yeh DUPLICATE hai — kuch mat karo,
            // bas fast loop se aage badh jaayega apne aap (agla element check karne)
        }

        // ===== FINAL ANSWER =====
        // "slow" hamesha AAKHRI unique element ke index pe hota hai
        // toh unique elements ki GINTI = slow + 1 (kyunki index 0 se shuru hota hai)
        return slow + 1;
    }
}
