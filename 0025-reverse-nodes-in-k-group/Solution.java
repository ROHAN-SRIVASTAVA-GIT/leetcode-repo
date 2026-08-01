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
    // "reverseKGroup" = method ka naam
    // "ListNode head" = list ka pehla node, "int k" = group size
    public ListNode reverseKGroup(ListNode head, int k) {

        // ===== DUMMY NODE BANANA (TRICK, jaise Problem 19, 21, 23, 24 mein) =====
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // "groupPrev" hamesha us node ko point karega JO CURRENT GROUP SE PEHLE hai
        ListNode groupPrev = dummy;

        // ===== YEH LOOP HAI (while loop) — HAR GROUP KE LIYE CHALTA HAI =====
        while (true) {

            // ===== STEP 1: CHECK KARO — kya aage POORE "k" NODES bache hain? =====
            // "getKth" helper method groupPrev se "k" step aage wala node dhundta hai
            ListNode kth = getKth(groupPrev, k);

            // agar "k"-wa node hi nahi mila (matlab list mein k se kam nodes bache),
            // toh yeh EK INCOMPLETE group hai — ise reverse mat karo, yahin ruk jao
            if (kth == null) {
                break;
            }

            // ===== GROUP KE BAAD WALA NODE YAAD RAKHNA (taaki reverse ke baad jod sakein) =====
            ListNode groupNext = kth.next;

            // ===== STEP 2: GROUP KO REVERSE KARNA (3-POINTER REVERSAL TECHNIQUE) =====
            // "prev" shuru mein groupNext hai (group ke turant baad wala) — isse
            // group ka PEHLA node (jo reverse hoke AAKHRI ban jaayega) sahi jagah connect hoga
            ListNode prev = groupNext;
            ListNode curr = groupPrev.next; // group ka pehla node

            // ===== YEH INNER LOOP HAI — group ke andar ke nodes ko ulta karta hai =====
            // jab tak "curr" group ke AAKHRI node (kth) ke AAGE wale (groupNext) tak nahi pahunchta
            while (curr != groupNext) {
                ListNode temp = curr.next; // agla node yaad rakho (kyunki next abhi badlenge)
                curr.next = prev;          // current ka next ULTA karke pichhle wale ki taraf kar do
                prev = curr;                // prev ko aage badhao
                curr = temp;                 // curr ko us "agle" node pe le jao jo yaad rakha tha
            }

            // ===== STEP 3: REVERSED GROUP KO PICHHLE GROUP SE JODNA =====
            // "temp" mein OLD group ka pehla node yaad rakho — reverse hone ke baad
            // yeh is group ka AAKHRI node ban gaya hai, isi se agla group jodenge
            ListNode temp = groupPrev.next;

            // groupPrev ka next ab "kth" (jo reverse hone ke baad NAYA PEHLA node hai)
            groupPrev.next = kth;

            // ===== AGLE GROUP KI TARAF BADHNA =====
            // "groupPrev" ab is group ke (reverse ke baad) AAKHRI node (temp) pe move hota hai
            groupPrev = temp;
        }

        // ===== FINAL ANSWER RETURN KARNA =====
        return dummy.next;
    }

    // ===== HELPER METHOD — "curr" se "k" steps aage wala node dhundhta hai =====
    // agar beech mein hi list khatam ho jaaye (null mil jaaye), toh null return karta hai
    private ListNode getKth(ListNode curr, int k) {
        while (curr != null && k > 0) {
            curr = curr.next;
            k--;
        }
        return curr;
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
        ListNode list1 = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Test 1 Output: " + listToString(sol.reverseKGroup(list1, 2))); // Expected: [2, 1, 4, 3, 5]

        // Test case 2 — k=3
        ListNode list2 = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Test 2 Output: " + listToString(sol.reverseKGroup(list2, 3))); // Expected: [3, 2, 1, 4, 5]

        // Test case 3 — k = poori list ki length
        ListNode list3 = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Test 3 Output: " + listToString(sol.reverseKGroup(list3, 5))); // Expected: [5, 4, 3, 2, 1]
    }
}
