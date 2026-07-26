// Yeh imports PriorityQueue use karne ke liye zaroori hain
import java.util.PriorityQueue;

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
    // "ListNode" = yeh method merged list ka "head" return karega
    // "mergeKLists" = method ka naam
    // "ListNode[] lists" = "k" sorted lists ka array (har element ek list ka head hai)
    public ListNode mergeKLists(ListNode[] lists) {

        // ===== EDGE CASE CHECK =====
        // agar array hi khaali hai (koi list nahi di gayi), toh seedha null return karo
        if (lists == null || lists.length == 0) {
            return null;
        }

        // ===== MIN-HEAP (PRIORITY QUEUE) BANANA =====
        // PriorityQueue ek "special basket" hai jo hamesha SABSE CHHOTI value ko
        // "upar" (nikalne ke liye ready) rakhta hai, chahe tum kisi bhi order mein daalo
        // "(a, b) -> a.val - b.val" batata hai ki COMPARISON kaise karna hai:
        //   jiska "val" chhota hai, use "chhota" maano (isse min-heap banta hai)
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);

        // ===== SAARI LISTS KE "PEHLE" (SABSE CHHOTE) NODE KO HEAP MEIN DAALNA =====
        // ===== YEH LOOP HAI (for-each loop) =====
        for (ListNode listHead : lists) {
            // agar yeh list khaali nahi hai (null nahi hai), tabhi use heap mein daalo
            if (listHead != null) {
                minHeap.offer(listHead); // "offer" = heap mein daalna (push jaisa)
            }
        }

        // ===== DUMMY NODE BANANA (TRICK, jaise Problem 19, 21 mein) =====
        ListNode dummy = new ListNode(0);
        // "current" merged list ke aakhri bane hue node ko point karega
        ListNode current = dummy;

        // ===== YEH LOOP HAI (while loop) =====
        // jab tak heap mein kuch bacha hai
        while (!minHeap.isEmpty()) {

            // ===== HEAP SE SABSE CHHOTI VALUE WALA NODE NIKALNA =====
            // "poll" = heap se sabse chhota element nikaal ke deta hai (aur use heap se hata deta hai)
            ListNode smallest = minHeap.poll();

            // is node ko merged list mein jodo
            current.next = smallest;
            // "current" ko aage badhao, kyunki merged list mein ek node aur jud gaya
            current = current.next;

            // ===== AGAR IS NODE KI LIST MEIN AUR NODES BACHE HAIN, UNHE HEAP MEIN DAALO =====
            if (smallest.next != null) {
                minHeap.offer(smallest.next);
            }
        }

        // ===== FINAL ANSWER RETURN KARNA =====
        return dummy.next;
    }
}
