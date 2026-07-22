// Yeh imports hain — List/ArrayList aur Arrays.sort use karne ke liye zaroori hain
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "List<List<Integer>>" = yeh method ek "list of lists" return karega
    //   (matlab: bahar wali list mein, andar chhoti-chhoti lists hain, har ek triplet ke liye)
    // "threeSum" = method ka naam
    // "int[] nums" = parameter — numbers ka array jo humein input mein milega
    public List<List<Integer>> threeSum(int[] nums) {

        // yeh final answer store karega — saari valid triplets isme jaayengi
        List<List<Integer>> result = new ArrayList<>();

        // ===== STEP 1: ARRAY KO SORT KARO =====
        // Arrays.sort() array ko chhote se bade order mein laga deta hai
        // Two Pointer technique SIRF sorted array pe hi kaam karti hai
        Arrays.sort(nums);

        // "n" mein array ki length store kar li, baar-baar likhne se bachne ke liye
        int n = nums.length;

        // ===== YEH OUTER LOOP HAI (for loop) — pehla number "FIX" kar rahe hain =====
        // "i" har baar ek number ko "fixed" number banayega
        // n - 2 tak isliye jaate hain kyunki hume aage 2 aur numbers chahiye (left, right)
        for (int i = 0; i < n - 2; i++) {

            // ===== DUPLICATE SKIP KARNA (fixed number ke liye) =====
            // agar current number, pichhle wale jaisa hi hai, toh SKIP kar do
            // warna same triplet baar-baar ban jaayegi (duplicate answer)
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // "continue" matlab is iteration ko chhod ke seedha agli iteration pe jao
            }

            // ===== TWO POINTER SETUP (baaki 2 numbers dhundne ke liye) =====
            // "left" pointer fixed number ke turant baad se shuru
            int left = i + 1;

            // "right" pointer array ke bilkul end se shuru
            int right = n - 1;

            // ===== YEH INNER LOOP HAI (while loop) — Two Pointer chala rahe hain =====
            // jab tak left, right se chhota hai, tab tak dhundte raho
            while (left < right) {

                // teeno numbers ka sum nikal rahe hain: fixed + left wala + right wala
                int sum = nums[i] + nums[left] + nums[right];

                // ===== CONDITION 1: sum bilkul 0 hai (answer mil gaya!) =====
                if (sum == 0) {
                    // Arrays.asList banata hai ek chhoti list [fixed, left, right] ki
                    // aur usko result mein add kar dete hain
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // ===== DUPLICATE SKIP KARNA (left pointer ke liye) =====
                    // agar left ka number, uske agle wale jaisa hi hai, toh skip karo
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // ===== DUPLICATE SKIP KARNA (right pointer ke liye) =====
                    // agar right ka number, uske pichhle wale jaisa hi hai, toh skip karo
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    // ab dono pointers ko ANDAR (beech) ki taraf ek-ek step move karo,
                    // taaki agle NAYE (non-duplicate) combination ki taraf badhein
                    left++;
                    right--;

                } else if (sum < 0) {
                    // ===== CONDITION 2: sum chahiye se KAM hai =====
                    // sum badhane ke liye, left ko aage badhao (bada number milega, sorted hai)
                    left++;

                } else {
                    // ===== CONDITION 3: sum chahiye se ZYADA hai =====
                    // sum ghatane ke liye, right ko peeche lao (chhota number milega, sorted hai)
                    right--;
                }
            }
        }

        // saari mili hui unique triplets ki list return kar do
        return result;
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        // Solution class ka object bana rahe hain
        Solution sol = new Solution();

        // Test case 1
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        System.out.println("Test 1 Output: " + sol.threeSum(nums1)); // Expected: [[-1,-1,2],[-1,0,1]]

        // Test case 2 — koi bhi triplet zero nahi banata
        int[] nums2 = {0, 1, 1};
        System.out.println("Test 2 Output: " + sol.threeSum(nums2)); // Expected: []

        // Test case 3 — sab zero hain
        int[] nums3 = {0, 0, 0};
        System.out.println("Test 3 Output: " + sol.threeSum(nums3)); // Expected: [[0,0,0]]
    }
}
