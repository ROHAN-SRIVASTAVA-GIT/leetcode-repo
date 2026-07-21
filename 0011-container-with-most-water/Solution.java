// Yeh poora file ek "class" hai — Java mein har code kisi class ke andar hi rehta hai
public class Solution {

    // "public" = yeh method bahar se call ho sakta hai
    // "int" = yeh method ek integer (whole number) return karega
    // "maxArea" = method ka naam (hum khud rakhte hain)
    // "int[] height" = parameter — array of heights jo humein input mein milega
    public int maxArea(int[] height) {

        // "int" variable "left" bana rahe hain, aur 0 se initialize kar rahe hain
        // yeh humara "left pointer" hai — array ke sabse pehle index (0) se shuru
        int left = 0;

        // "right" pointer — array ke sabse aakhri index se shuru
        // height.length = array mein kitne elements hain
        // -1 isliye kiya kyunki index hamesha 0 se shuru hota hai (last index = length - 1)
        int right = height.length - 1;

        // yeh variable ab tak ka "best / maximum" paani store karega
        // shuru mein 0 rakha hai kyunki abhi tak kuch calculate nahi kiya
        int maxWater = 0;

        // ===== YEH LOOP HAI (while loop) =====
        // jab tak left pointer, right pointer se chhota hai, tab tak loop chalega
        // jaise hi left aur right mil jaayenge ya cross ho jaayenge, loop ruk jaayega
        while (left < right) {

            // "width" = dono pointers ke beech ki doori (chaudai)
            int width = right - left;

            // "currentHeight" = dono poles mein se JO CHHOTI HAI, wahi lenge
            // Math.min(a, b) ek built-in function hai jo do numbers mein se chhota wala deta hai
            int currentHeight = Math.min(height[left], height[right]);

            // is pair (left, right) se jitna paani ban raha hai, wo calculate kiya
            int currentWater = width * currentHeight;

            // ===== YEH CONDITION (if) HAI =====
            // agar abhi wala paani, ab tak ke best paani se zyada hai...
            if (currentWater > maxWater) {
                // ...toh best paani ko update kar do
                maxWater = currentWater;
            }

            // ===== YEH DECISION LOGIC HAI (kaunsa pointer move karna hai) =====
            // jo pointer chhota hai, usko andar (beech) ki taraf move karo
            // kyunki chhoti height hi paani ka level rok rahi thi,
            // toh usko badalne se hi behtar answer milne ka chance hai
            if (height[left] < height[right]) {
                left++;   // left pointer ko ek step aage (right ki taraf) badhao
            } else {
                right--;  // right pointer ko ek step peeche (left ki taraf) badhao
            }
        }

        // loop khatam hone ke baad, jo bhi best paani mila usko wapas (return) kar do
        return maxWater;
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        // Solution class ka ek object (instance) bana rahe hain
        Solution sol = new Solution();

        // Test case 1 — LeetCode ke example jaisa
        int[] height1 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        // sol.maxArea(...) call karke result nikala aur print kiya
        System.out.println("Test 1 Output: " + sol.maxArea(height1)); // Expected: 49

        // Test case 2 — ek chhota example
        int[] height2 = {1, 1};
        System.out.println("Test 2 Output: " + sol.maxArea(height2)); // Expected: 1
    }
}
