// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "void" = yeh method kuch return nahi karta, seedha "nums" array ko IN-PLACE modify karta hai
    // "nextPermutation" = method ka naam
    // "int[] nums" = current arrangement, jise next permutation mein badalna hai
    public void nextPermutation(int[] nums) {

        int n = nums.length;

        // ===== STEP 1: RIGHT SE LEFT CHALKE, PEHLA "BREAK POINT" DHUNDHNA =====
        // "i" us index ko dhundega jaha "nums[i] < nums[i+1]" hai
        // (matlab yaha se ek CHHOTA element hai jise hum badal sakte hain)
        int i = n - 2; // n-2 se shuru kiya kyunki humein "nums[i+1]" bhi chahiye compare karne ke liye

        // ===== YEH LOOP HAI (while loop) =====
        // jab tak i valid hai (0 se kam nahi hua) AUR current pair "descending" hai (badhna nahi ho raha)
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--; // left ki taraf badhte jao
        }

        // ===== STEP 2: AGAR BREAK POINT MILA (i >= 0), TOH USSE SWAP KARNE WALA ELEMENT DHUNDHO =====
        if (i >= 0) {
            // "j" ko array ke AAKHRI index se shuru karte hain
            int j = n - 1;

            // ===== YEH LOOP HAI (while loop) — right se left chalke pehla element dhundo jo nums[i] se BADA hai =====
            while (nums[j] <= nums[i]) {
                j--;
            }

            // ===== STEP 3: nums[i] AUR nums[j] KO SWAP KARNA =====
            swap(nums, i, j);
        }

        // ===== STEP 4: i KE BAAD WALA POORA HISSA (i+1 se end tak) REVERSE KARNA =====
        // agar "i == -1" tha (poora array descending tha), toh yeh POORE array ko reverse kar dega
        // (jo sahi hai — descending array ka next permutation, sabse chhota ascending array hai)
        reverse(nums, i + 1, n - 1);
    }

    // ===== HELPER METHOD — do indices ke values ko swap (aapas mein badalna) karta hai =====
    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    // ===== HELPER METHOD — array ke "left" se "right" tak ke hisse ko reverse karta hai =====
    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    // ===== YEH HELPER METHOD HAI — array ko print karne layak String banata hai =====
    private static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
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
        int[] nums1 = {1, 2, 3};
        sol.nextPermutation(nums1);
        System.out.println("Test 1 Output: " + arrayToString(nums1)); // Expected: [1, 3, 2]

        // Test case 2 — sabse bada arrangement, wrap around hoga
        int[] nums2 = {3, 2, 1};
        sol.nextPermutation(nums2);
        System.out.println("Test 2 Output: " + arrayToString(nums2)); // Expected: [1, 2, 3]

        // Test case 3 — tricky case
        int[] nums3 = {1, 1, 5};
        sol.nextPermutation(nums3);
        System.out.println("Test 3 Output: " + arrayToString(nums3)); // Expected: [1, 5, 1]
    }
}
