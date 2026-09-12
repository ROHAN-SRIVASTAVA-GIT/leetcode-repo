// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "int" = yeh method bache hue (valid) elements ki GINTI return karega
    // "removeElement" = method ka naam
    // "int[] nums" = array, "int val" = jo value hatani hai
    public int removeElement(int[] nums, int val) {

        // ===== "SLOW" POINTER =====
        // yeh batata hai "agla VALID (val nahi wala) element kahan rakhna hai"
        // shuru mein 0 pe hai — abhi tak koi valid element confirm nahi hua
        int slow = 0;

        // ===== YEH LOOP HAI (for loop) — "FAST" POINTER poore array ko explore karta hai =====
        for (int fast = 0; fast < nums.length; fast++) {

            // ===== CONDITION: kya fast wala element, "val" SE ALAG hai? =====
            // agar alag hai, matlab yeh element RAKHNA hai (hatana nahi)
            if (nums[fast] != val) {

                // is valid element ko "slow" ki jagah copy kar do
                nums[slow] = nums[fast];

                // slow ko ek aage badhao (agli valid jagah ke liye)
                slow++;
            }
            // agar nums[fast] == val hai, matlab yeh HATANA hai — kuch mat karo,
            // bas fast loop se aage badh jaayega apne aap
        }

        // ===== FINAL ANSWER =====
        // "slow" hi valid elements ki GINTI hai (kyunki har baar valid mila, slow++ hua)
        return slow;
    }
}
