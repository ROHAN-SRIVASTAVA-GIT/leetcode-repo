# 25. Reverse Nodes in k-Group

LeetCode Link: https://leetcode.com/problems/reverse-nodes-in-k-group/

**Note (LeetCode pe paste karte waqt):** Is file mein `class ListNode {...}` block hai —
wo sirf apni machine pe standalone test karne ke liye hai. LeetCode ke editor mein
sirf `public class Solution {...}` wala part paste karna.

## Problem kya keh raha hai (simple bhasha mein)

Humein ek Linked List aur ek number `k` diya hai. Humein list ko **`k`-`k` nodes ke
groups mein baatna** hai, aur har **poore group ko reverse (ulta)** karna hai. Agar
aakhri group mein `k` se **kam nodes bache**, use waise hi (bina reverse kiye) chhod
dena hai.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Yeh [Problem 24: Swap Nodes in Pairs](../0024-swap-nodes-in-pairs) ka bada version
hai — wahan hamesha **2-2 ka group** reverse hota tha, ab **`k`-`k` ka group** reverse
hoga.

Socho train ke dabbon ko **`k`-`k` ke groups mein baato**. Har poore group ko **ulta
ghuma do**. Agar aakhri group mein `k` se kam dabbe bache (poora group nahi bana),
use waise hi chhod do.

Jaise `[1,2,3,4,5]`, `k=3`: pehla group `(1,2,3)` → reverse → `3,2,1`. Baaki bache
`(4,5)` — sirf 2 hain, `k=3` chahiye, toh **waise hi rehne do**. Final: `3,2,1,4,5`.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Yeh do techniques ka combo hai:

**1. "Check-then-Act" (pehle check karo, phir kaam karo):**
Reverse karne se **PEHLE** check karo ki aage `k` poore nodes hain ya nahi
(`getKth` helper method se). Agar nahi hain, toh us group ko **bilkul mat chhedo**.
Yeh galti se partial group reverse karne se bachata hai.

**2. "3-Pointer Reversal" (Linked List reverse karne ka standard tareeka):**
Kisi bhi linked list (ya uske hisse) ko reverse karne ke liye teen pointers use hote
hain: `prev` (pichhla), `curr` (current), aur ek `temp` (agla node yaad rakhne ke liye,
kyunki `curr.next` badalne se pehle uska original "next" yaad rakhna zaroori hai).
Har step pe:
```
temp = curr.next     // agla node yaad rakho
curr.next = prev      // current ka next ULTA karo (prev ki taraf)
prev = curr            // prev ko aage badhao
curr = temp              // curr ko us node pe le jao jo yaad rakha tha
```
Yeh loop group ke saare nodes ke liye chalta hai — end mein poora group ulta ho
jaata hai.

**Dummy Node trick (Problem 19, 21, 23, 24 se yaad hai):** kyunki pehla group khud
head ko badal sakta hai.

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `getKth(groupPrev, k)` | Check karta hai ki aage poore `k` nodes hain ya nahi |
| `if (kth == null) break;` | Incomplete group mila, loop rok do |
| `groupNext = kth.next` | Group ke baad wala node yaad rakhna (baad mein jodne ke liye) |
| `prev = groupNext` | Reversal shuru — prev ko group ke AAGE wale se set karna (taaki naya "last" node sahi jage jude) |
| `while (curr != groupNext)` | Group ke andar 3-pointer reversal chalana |
| `groupPrev.next = kth` | Naye group ke PEHLE node (jo pehle group ka AAKHRI tha) ko jodna |
| `groupPrev = temp` | Agle group ki taraf badhna (temp = is group ka naya AAKHRI node) |

## Complexity

- **Time:** O(n) — har node ko constant baar hi visit karte hain (getKth check +
  ek baar reversal ke andar)
- **Space:** O(1) — koi naya node nahi banate, sirf existing nodes ko re-link karte hain
