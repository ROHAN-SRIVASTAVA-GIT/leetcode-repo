// Yeh poora file ek "class" hai
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "int" = yeh method division ka answer (integer) return karega
    // "divide" = method ka naam
    // "int dividend" = jise divide karna hai, "int divisor" = jisse divide karna hai
    public int divide(int dividend, int divisor) {

        // ===== SPECIAL OVERFLOW CASE =====
        // Integer.MIN_VALUE (-2147483648) ko -1 se divide karne pe answer
        // 2147483648 banega, jo "int" mein fit hi nahi hoga (overflow).
        // Is special case mein hum "int" ki max possible value return kar dete hain
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // ===== SIGN NIKALNA (answer positive hoga ya negative) =====
        // agar dividend aur divisor mein se SIRF EK negative hai (dono same sign nahi hain),
        // toh final answer negative hoga
        boolean isNegative = (dividend < 0) != (divisor < 0);

        // ===== ABSOLUTE VALUES NIKALNA (LONG mein, taaki overflow na ho) =====
        // "long" use kiya kyunki Integer.MIN_VALUE ka absolute value, int range se bahar chala jaata hai
        // Math.abs((long) dividend) — pehle long banaya, phir uska absolute liya
        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);

        // yeh final answer store karega
        long result = 0;

        // ===== YEH OUTER LOOP HAI (while loop) — JAB TAK DIVIDEND, DIVISOR SE BADA/BARABAR HAI =====
        while (dvd >= dvs) {

            // "temp" = current chunk jo hum ghatane wale hain (shuru mein divisor jitna)
            long temp = dvs;
            // "multiple" = yeh chunk, divisor ka KITNA GUNA (multiple) hai
            long multiple = 1;

            // ===== YEH INNER LOOP HAI (while loop) — DOUBLING KARTE JAO JAB TAK FIT HOTA HAI =====
            // "temp << 1" = temp ko 2 se multiply karna (bit shift, "*" operator use nahi kiya)
            while (dvd >= (temp << 1)) {
                temp <<= 1;      // temp ko double karo
                multiple <<= 1;  // multiple ko bhi double karo (usi rate se badhta hai)
            }

            // ===== SABSE BADA CHUNK JO FIT HUA, USE GHATA DO =====
            dvd -= temp;

            // ===== RESULT MEIN YEH MULTIPLE JOD DO =====
            result += multiple;
        }

        // ===== SIGN LAGANA =====
        // agar answer negative hona chahiye, toh use negative kar do
        return isNegative ? (int) -result : (int) result;
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test case 1
        System.out.println("Test 1 Output: " + sol.divide(10, 3)); // Expected: 3

        // Test case 2 — negative sign wala case
        System.out.println("Test 2 Output: " + sol.divide(7, -3)); // Expected: -2

        // Test case 3 — overflow edge case
        System.out.println("Test 3 Output: " + sol.divide(Integer.MIN_VALUE, -1)); // Expected: 2147483647
    }
}
