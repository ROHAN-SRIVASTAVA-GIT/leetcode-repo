// ===== YEH EK "NODE" (DABBA) KI DEFINITION HAI =====
// LeetCode par yeh class already unke driver code mein maujood hai —
// isliye LeetCode ke editor mein PASTE karte waqt is class ko SKIP kar dena
// (sirf "Solution" class paste karna).
class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

// Yeh poora file ek "class" hai (asli solution yaha hai)
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "ListNode" = yeh method updated list ka "head" return karega
    // "swapPairs" = method ka naam
    // "ListNode head" = list ka pehla node
    public ListNode swapPairs(ListNode head) {

        // ===== DUMMY NODE BANANA (TRICK, jaise Problem 19, 21, 23 mein) =====
        // isse humein "agar head khud swap ho jaaye toh kya karein" jaisa
        // special case sochna nahi padega
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // "prev" hamesha us node ko point karega JO CURRENT PAIR SE PEHLE hai
        // shuru mein yeh dummy pe hai (kyunki pehle pair se pehle koi asli node nahi)
        ListNode prev = dummy;

        // ===== YEH LOOP HAI (while loop) =====
        // jab tak kam se kam EK POORA PAIR (2 nodes) bacha hai swap karne ke liye
        while (prev.next != null && prev.next.next != null) {

            // ===== PAIR KE DONO NODES KO NAAM DENA =====
            ListNode first = prev.next;       // pair ka pehla node
            ListNode second = first.next;     // pair ka doosra node

            // ===== SWAP KARNA (3 "next" pointers ko re-connect karna) =====
            // Step 1: first ka next ab second ke AAGE wale node ki taraf jaayega
            //         (second ko "skip" karke, kyunki second ab first se pehle aayega)
            first.next = second.next;

            // Step 2: second ka next ab first ki taraf jaayega (swap ho gaya!)
            second.next = first;

            // Step 3: prev (pichhle pair ka aakhri node) ab NAYE pair ke pehle
            //         node (second) ki taraf point karega
            prev.next = second;

            // ===== AGLE PAIR KI TARAF BADHNA =====
            // "prev" ab "first" ban jaata hai, kyunki swap ke baad "first" is
            // pair ka AAKHRI node hai — agla pair ab first ke baad shuru hoga
            prev = first;
        }

        // ===== FINAL ANSWER RETURN KARNA =====
        return dummy.next;
    }

    // ===== YEH HELPER METHOD HAI — array se linked list banata hai (testing ke liye) =====
    private static ListNode buildList(int[] values) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int value : values) {
            current.next = new ListNode(value);
            current = current.next;
        }
        return dummy.next;
    }

    // ===== YEH HELPER METHOD HAI — linked list ko print karne layak String banata hai =====
    private static String listToString(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) {
                sb.append(", ");
            }
            head = head.next;
        }
        sb.append("]");
        return sb.toString();
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test case 1
        ListNode list1 = buildList(new int[]{1, 2, 3, 4});
        System.out.println("Test 1 Output: " + listToString(sol.swapPairs(list1))); // Expected: [2, 1, 4, 3]

        // Test case 2 — khaali list
        ListNode list2 = buildList(new int[]{});
        System.out.println("Test 2 Output: " + listToString(sol.swapPairs(list2))); // Expected: []

        // Test case 3 — odd length (aakhri node akela bacha, swap nahi hoga)
        ListNode list3 = buildList(new int[]{1, 2, 3});
        System.out.println("Test 3 Output: " + listToString(sol.swapPairs(list3))); // Expected: [2, 1, 3]
    }
}
