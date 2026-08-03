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

    // ===== YEH HELPER METHOD HAI — array ke sirf PEHLE "k" elements ko print karta hai =====
    // (testing ke liye, taaki dekh sakein ki array ka "unique" hissa kaisa dikhta hai)
    private static String arrayPrefixToString(int[] arr, int k) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < k; i++) {
            sb.append(arr[i]);
            if (i < k - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test case 1
        int[] nums1 = {1, 1, 2};
        int k1 = sol.removeDuplicates(nums1);
        System.out.println("Test 1 Output: k=" + k1 + ", array=" + arrayPrefixToString(nums1, k1));
        // Expected: k=2, array=[1, 2]

        // Test case 2
        int[] nums2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int k2 = sol.removeDuplicates(nums2);
        System.out.println("Test 2 Output: k=" + k2 + ", array=" + arrayPrefixToString(nums2, k2));
        // Expected: k=5, array=[0, 1, 2, 3, 4]

        // Test case 3 — koi duplicate hi nahi
        int[] nums3 = {1, 2, 3};
        int k3 = sol.removeDuplicates(nums3);
        System.out.println("Test 3 Output: k=" + k3 + ", array=" + arrayPrefixToString(nums3, k3));
        // Expected: k=3, array=[1, 2, 3]
    }
}
