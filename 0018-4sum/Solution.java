// Yeh imports List/ArrayList/Arrays.sort use karne ke liye zaroori hain
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "List<List<Integer>>" = list of lists — har chhoti list ek valid "4 numbers ka group" hai
    // "fourSum" = method ka naam
    // "int[] nums" = numbers ka array, "int target" = jis sum tak pahunchna hai
    public List<List<Integer>> fourSum(int[] nums, int target) {

        // yeh final answer store karega
        List<List<Integer>> result = new ArrayList<>();

        // ===== STEP 1: ARRAY KO SORT KARO =====
        // Two Pointer aur duplicate-skip dono SIRF sorted array pe kaam karte hain
        Arrays.sort(nums);

        // "n" mein array ki length store kar li
        int n = nums.length;

        // ===== EDGE CASE CHECK =====
        // agar array mein 4 se kam numbers hain, toh koi bhi 4Sum ban hi nahi sakta
        if (n < 4) {
            return result;
        }

        // ===== YEH PEHLA OUTER LOOP HAI — PEHLA NUMBER "FIX" KAR RAHE HAIN =====
        // "i" har baar ek number ko "fixed number #1" banayega
        // n - 3 tak isliye jaate hain kyunki aage 3 aur numbers chahiye (j, left, right)
        for (int i = 0; i < n - 3; i++) {

            // ===== DUPLICATE SKIP (fixed number #1 ke liye) =====
            // agar current number, pichhle wale jaisa hi hai, toh SKIP kar do
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // ===== YEH DOOSRA OUTER LOOP HAI — DOOSRA NUMBER "FIX" KAR RAHE HAIN =====
            // "j" har baar ek number ko "fixed number #2" banayega, i ke turant baad se
            for (int j = i + 1; j < n - 2; j++) {

                // ===== DUPLICATE SKIP (fixed number #2 ke liye) =====
                // check karo: j apni hi shuruaat pe toh nahi (j > i+1),
                // AUR current number, pichhle wale jaisa hi hai kya
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                // ===== TWO POINTER SETUP (baaki 2 numbers dhundne ke liye) =====
                int left = j + 1;   // fixed #2 ke turant baad se shuru
                int right = n - 1;  // array ke bilkul end se shuru

                // ===== INNER WHILE LOOP — TWO POINTER CHALA RAHE HAIN =====
                while (left < right) {

                    // ===== SUM NIKALNA (long use kar rahe hain OVERFLOW se bachne ke liye) =====
                    // agar 4 bade numbers (jaise 10^9 wale) jodein, toh normal "int" mein
                    // fit nahi honge — isliye "long" (bada data type) use kiya
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];

                    // ===== CONDITION 1: sum bilkul target ke barabar hai (answer mil gaya!) =====
                    if (sum == target) {
                        // Arrays.asList banata hai ek chhoti list [i, j, left, right] wale numbers ki
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // ===== DUPLICATE SKIP (left pointer ke liye) =====
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        // ===== DUPLICATE SKIP (right pointer ke liye) =====
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }

                        // dono pointers ko ANDAR (beech) ki taraf move karo, agle NAYE
                        // combination ki taraf badhne ke liye
                        left++;
                        right--;

                    } else if (sum < target) {
                        // ===== CONDITION 2: sum chahiye se KAM hai =====
                        // sum badhane ke liye left ko aage badhao (sorted hai, bada number milega)
                        left++;

                    } else {
                        // ===== CONDITION 3: sum chahiye se ZYADA hai =====
                        // sum ghatane ke liye right ko peeche lao (chhota number milega)
                        right--;
                    }
                }
            }
        }

        // saari mili hui unique 4-number combinations ki list return kar do
        return result;
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        // Solution class ka object bana rahe hain
        Solution sol = new Solution();

        // Test case 1
        int[] nums1 = {1, 0, -1, 0, -2, 2};
        System.out.println("Test 1 Output: " + sol.fourSum(nums1, 0));
        // Expected: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]

        // Test case 2 — sab same numbers
        int[] nums2 = {2, 2, 2, 2, 2};
        System.out.println("Test 2 Output: " + sol.fourSum(nums2, 8));
        // Expected: [[2,2,2,2]]
    }
}
