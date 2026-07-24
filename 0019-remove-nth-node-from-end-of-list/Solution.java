// ===== YEH EK "NODE" (DABBA) KI DEFINITION HAI =====
// LeetCode is class ko already deta hai, hum yaha khud bana rahe hain taaki
// yeh file khud se (standalone) compile aur run ho sake
// "class ListNode" ek "blueprint" hai ek dabbe ka
class ListNode {
    int val;       // is dabbe mein rakha hua number (value)
    ListNode next; // agle dabbe ka address/reference (agar aakhri dabba hai toh yeh null hoga)

    // ===== CONSTRUCTOR (jab naya node banate hain, yeh call hota hai) =====
    ListNode(int val) {
        this.val = val;   // "this.val" (dabbe ka value) ko diye gaye "val" se set karo
        this.next = null; // shuru mein next khaali (null) rakhte hain
    }
}

// Yeh poora file ek "class" hai (asli solution yaha hai)
public class Solution {

    // "public" = bahar se call ho sakta hai
    // "ListNode" = yeh method ek naye linked list ka "head" (shuruaat) return karega
    // "removeNthFromEnd" = method ka naam
    // "ListNode head" = original linked list ka pehla dabba
    // "int n" = peeche se kaunsa dabba hatana hai
    public ListNode removeNthFromEnd(ListNode head, int n) {

        // ===== DUMMY NODE BANANA (TRICK) =====
        // ek "fake" node banate hain jo asli list se PEHLE aata hai
        // isse agar humein HEAD node hi hatana pade, toh bhi special case nahi likhna padega
        ListNode dummy = new ListNode(0);

        // dummy ka "next" asli list ke head ki taraf point karta hai
        dummy.next = head;

        // ===== DO POINTERS BANA RAHE HAIN (fast aur slow) =====
        // dono shuru mein "dummy" pe hi khade hain
        ListNode fast = dummy;
        ListNode slow = dummy;

        // ===== STEP 1: FAST POINTER KO "n+1" DABBE AAGE BHEJO =====
        // "+1" isliye kiya kyunki humein "slow" ko us node pe rukwana hai
        // JO HATANE WALE NODE SE EK PEHLE hai (taaki hum use skip kar sakein)
        // ===== YEH LOOP HAI (for loop) =====
        for (int i = 0; i < n + 1; i++) {
            // fast pointer ko agle dabbe pe le jao
            fast = fast.next;
        }

        // ===== STEP 2: DONO POINTERS KO SAATH-SAATH CHALAO =====
        // jab tak fast, list ke aakhir (null) tak nahi pahunch jaata
        // ===== YEH LOOP HAI (while loop) =====
        while (fast != null) {
            // dono pointers ko ek-ek step aage badhao
            fast = fast.next;
            slow = slow.next;
        }

        // ===== STEP 3: NODE KO HATANA (REMOVE) =====
        // ab "slow" us node pe khada hai JO HATANE WALE NODE SE EK PEHLE hai
        // "slow.next.next" = hatane wale node ka "next" (jo uske BAAD wala node hai)
        // isko seedha "slow.next" bana dena, matlab beech wale node ko "skip" kar diya
        slow.next = slow.next.next;

        // ===== FINAL ANSWER RETURN KARNA =====
        // dummy.next hi hamesha asli (updated) list ka head hoga
        // (dummy khud fake node tha, use return nahi karna)
        return dummy.next;
    }

    // ===== YEH HELPER METHOD HAI — array se linked list banata hai (testing ke liye) =====
    private static ListNode buildList(int[] values) {
        ListNode dummy = new ListNode(0); // fake starting node
        ListNode current = dummy;         // "current" hamesha aakhri bane hue node ko point karega

        // array ke har value ke liye ek naya node banake list mein jodte jao
        for (int value : values) {
            current.next = new ListNode(value);
            current = current.next;
        }
        return dummy.next; // asli list ka head (dummy chhodke)
    }

    // ===== YEH HELPER METHOD HAI — linked list ko print karne layak String banata hai =====
    private static String listToString(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        while (head != null) {
            sb.append(head.val); // current node ka value jodo
            if (head.next != null) {
                sb.append(", "); // agar aur node hain, toh comma lagao
            }
            head = head.next; // agle node pe move karo
        }
        sb.append("]");
        return sb.toString();
    }

    // ===== YEH MAIN METHOD HAI — program yahi se shuru hota hai =====
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test case 1
        ListNode list1 = buildList(new int[]{1, 2, 3, 4, 5});
        ListNode result1 = sol.removeNthFromEnd(list1, 2);
        System.out.println("Test 1 Output: " + listToString(result1)); // Expected: [1, 2, 3, 5]

        // Test case 2 — sirf ek node hai, wahi hatana hai
        ListNode list2 = buildList(new int[]{1});
        ListNode result2 = sol.removeNthFromEnd(list2, 1);
        System.out.println("Test 2 Output: " + listToString(result2)); // Expected: []

        // Test case 3 — head node hi hatana hai
        ListNode list3 = buildList(new int[]{1, 2});
        ListNode result3 = sol.removeNthFromEnd(list3, 2);
        System.out.println("Test 3 Output: " + listToString(result3)); // Expected: [2]
    }
}
