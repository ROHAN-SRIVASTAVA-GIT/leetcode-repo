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

    // ===== YEH HELPER METHOD HAI — array ke sirf PEHLE "k" elements print karta hai =====
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
        int[] nums1 = {3, 2, 2, 3};
        int k1 = sol.removeElement(nums1, 3);
        System.out.println("Test 1 Output: k=" + k1 + ", array=" + arrayPrefixToString(nums1, k1));
        // Expected: k=2, array=[2, 2]

        // Test case 2
        int[] nums2 = {0, 1, 2, 2, 3, 0, 4, 2};
        int k2 = sol.removeElement(nums2, 2);
        System.out.println("Test 2 Output: k=" + k2 + ", array=" + arrayPrefixToString(nums2, k2));
        // Expected: k=5, array=[0, 1, 3, 0, 4] (order thoda alag ho sakta hai, count 5 hona chahiye)

        // Test case 3 — koi bhi element "val" nahi hai
        int[] nums3 = {1, 2, 3};
        int k3 = sol.removeElement(nums3, 5);
        System.out.println("Test 3 Output: k=" + k3 + ", array=" + arrayPrefixToString(nums3, k3));
        // Expected: k=3, array=[1, 2, 3]
    }
}
