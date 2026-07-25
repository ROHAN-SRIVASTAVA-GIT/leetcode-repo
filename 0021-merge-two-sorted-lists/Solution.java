// ===== YEH EK "NODE" (DABBA) KI DEFINITION HAI =====
// LeetCode par yeh class already unke driver code mein maujood hai —
// isliye LeetCode ke editor mein PASTE karte waqt is class ko SKIP kar dena
// (sirf "Solution" class paste karna). Yaha standalone testing ke liye likha hai.
class ListNode {
    int val;       // is dabbe mein rakha hua number
    ListNode next; // agle dabbe ka address (aakhri dabbe ka next null hota hai)

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

// Yeh poora file ek "class" hai (asli solution yaha hai)
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "ListNode" = yeh method naye merged list ka "head" return karega
    // "mergeTwoLists" = method ka naam
    // "ListNode list1, ListNode list2" = dono sorted lists ke heads
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        // ===== DUMMY NODE BANANA (TRICK, jaise Problem 19 mein) =====
        // ek fake node banate hain — isse merged list ka pehla asli node
        // set karne ke liye alag se special case nahi likhna padega
        ListNode dummy = new ListNode(0);

        // "current" hamesha naye (merged) list ke AAKHRI bane hue node ko point karega
        // shuru mein yeh dummy pe hi hai
        ListNode current = dummy;

        // ===== YEH LOOP HAI (while loop) =====
        // jab tak DONO lists mein kuch bacha hai (koi bhi null nahi hui)
        while (list1 != null && list2 != null) {

            // ===== COMPARE KARO: dono list ke CURRENT (front) node mein chhota kaun hai? =====
            if (list1.val <= list2.val) {
                // list1 ka value chhota (ya barabar) hai, toh use naye list mein jodo
                current.next = list1;
                // list1 ko uske agle node pe le jao (jo card upar tha wo utha liya, agla upar aaya)
                list1 = list1.next;
            } else {
                // list2 ka value chhota hai, toh use naye list mein jodo
                current.next = list2;
                // list2 ko agle node pe le jao
                list2 = list2.next;
            }

            // "current" ko bhi aage badhao, kyunki naye list mein ek node aur jud gaya
            current = current.next;
        }

        // ===== LOOP KHATAM — MATLAB EK LIST KHAALI HO GAYI =====
        // jo list bachi hai (chahe list1 ho ya list2), wo ALREADY SORTED hai
        // toh use POORA KA POORA seedha jod do — koi comparison ki zaroorat nahi
        // (agar dono null hain, toh dono lines mein kuch nahi hoga, koi farak nahi padta)
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }

        // ===== FINAL ANSWER RETURN KARNA =====
        // dummy.next hamesha merged list ka asli head hoga (dummy khud fake tha)
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
        ListNode list1a = buildList(new int[]{1, 2, 4});
        ListNode list1b = buildList(new int[]{1, 3, 4});
        System.out.println("Test 1 Output: " + listToString(sol.mergeTwoLists(list1a, list1b)));
        // Expected: [1, 1, 2, 3, 4, 4]

        // Test case 2 — dono khaali
        ListNode list2a = buildList(new int[]{});
        ListNode list2b = buildList(new int[]{});
        System.out.println("Test 2 Output: " + listToString(sol.mergeTwoLists(list2a, list2b)));
        // Expected: []

        // Test case 3 — ek khaali
        ListNode list3a = buildList(new int[]{});
        ListNode list3b = buildList(new int[]{0});
        System.out.println("Test 3 Output: " + listToString(sol.mergeTwoLists(list3a, list3b)));
        // Expected: [0]
    }
}
