// Yeh import Arrays.sort() use karne ke liye zaroori hai
import java.util.Arrays;

// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "int" = yeh method ek number return karega — jo sum target ke sabse paas hai
    // "threeSumClosest" = method ka naam
    // "int[] nums" = numbers ka array, "int target" = jis number ke paas jaana hai
    public int threeSumClosest(int[] nums, int target) {

        // ===== STEP 1: ARRAY KO SORT KARO =====
        // Two Pointer technique SIRF sorted array pe hi kaam karti hai
        Arrays.sort(nums);

        // "n" mein array ki length store kar li
        int n = nums.length;

        // ===== "closestSum" — ab tak ka BEST (target ke sabse paas wala) sum =====
        // shuru mein hum pehle 3 numbers ka sum le lete hain as a starting guess
        // (koi bhi valid starting value chalegi, kyunki array mein kam se kam 3 numbers hote hain)
        int closestSum = nums[0] + nums[1] + nums[2];

        // ===== YEH OUTER LOOP HAI (for loop) — pehla number "FIX" kar rahe hain =====
        // "i" har baar ek number ko "fixed" number banayega
        for (int i = 0; i < n - 2; i++) {

            // ===== TWO POINTER SETUP (baaki 2 numbers dhundne ke liye) =====
            int left = i + 1;   // fixed number ke turant baad se shuru
            int right = n - 1;  // array ke bilkul end se shuru

            // ===== YEH INNER LOOP HAI (while loop) — Two Pointer chala rahe hain =====
            while (left < right) {

                // teeno numbers ka current sum nikal rahe hain
                int currentSum = nums[i] + nums[left] + nums[right];

                // ===== COMPARE KARO: kya yeh sum, target ke ZYADA paas hai? =====
                // Math.abs() ek built-in function hai jo kisi number ki "positive distance"
                // deta hai (jaise Math.abs(-5) = 5, Math.abs(5) = 5)
                // agar currentSum, target ke closestSum se zyada paas hai...
                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    // ...toh closestSum ko update kar do
                    closestSum = currentSum;
                }

                // ===== AB DECIDE KARO KAUNSA POINTER MOVE KARNA HAI =====
                if (currentSum == target) {
                    // agar sum BILKUL target ke barabar hai, toh isse behtar ho hi nahi sakta
                    // seedha yehi return kar do, aage dhundne ki zaroorat nahi
                    return currentSum;
                } else if (currentSum < target) {
                    // sum badhana hai, toh left ko aage badhao (sorted hai, bada number milega)
                    left++;
                } else {
                    // sum ghatana hai, toh right ko peeche lao (chhota number milega)
                    right--;
                }
            }
        }

        // saare combinations check karne ke baad, jo best (closest) sum mila usko return karo
        return closestSum;
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        // Solution class ka object bana rahe hain
        Solution sol = new Solution();

        // Test case 1
        int[] nums1 = {-1, 2, 1, -4};
        System.out.println("Test 1 Output: " + sol.threeSumClosest(nums1, 1)); // Expected: 2

        // Test case 2 — sab zero, target bhi 0
        int[] nums2 = {0, 0, 0};
        System.out.println("Test 2 Output: " + sol.threeSumClosest(nums2, 1)); // Expected: 0

        // Test case 3
        int[] nums3 = {1, 1, 1, 0};
        System.out.println("Test 3 Output: " + sol.threeSumClosest(nums3, 100)); // Expected: 3
    }
}
