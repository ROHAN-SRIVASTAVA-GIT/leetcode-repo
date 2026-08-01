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

## Dry Run — Pointers ko haath se chala ke dekhte hain (real example)

Chalo `head = [1,2,3,4,5]`, `k = 3` leke karte hain.

**Setup:** `dummy -> 1 -> 2 -> 3 -> 4 -> 5 -> null`, `groupPrev = dummy`

### Group 1 (nodes 1, 2, 3)

**Step 1 — Check:** `getKth(groupPrev=dummy, 3)` → dummy se 3 step aage = node(3).
`kth = node(3)` (null nahi hai) → poora group hai, aage badho.

`groupNext = kth.next` = node(4)

**Step 2 — Reversal setup:** `prev = groupNext(4)`, `curr = groupPrev.next` = node(1)

| Check `curr != groupNext`? | `curr` (pehle) | Action | `prev` (baad) | `curr` (baad) |
|---|---|---|---|---|
| 1 != 4? HAA | node(1) | temp=node(2); 1.next=prev(4); prev=1 | node(1) | node(2) |
| 2 != 4? HAA | node(2) | temp=node(3); 2.next=prev(1); prev=2 | node(2) | node(3) |
| 3 != 4? HAA | node(3) | temp=node(4); 3.next=prev(2); prev=3 | node(3) | node(4) |
| 4 != 4? **NA** | — | loop RUKA | — | — |

Ab group ke andar: `3 -> 2 -> 1 -> 4(groupNext, abhi tak connected nahi humare group se)`

**Step 3 — Jodna:** `temp = groupPrev.next` = node(1) (OLD pehla node, ab AAKHRI hai)
`groupPrev.next = kth(3)` → dummy ka next ab node(3)
`groupPrev = temp(1)` → agle group ke liye ready

**Ab poori list: `dummy -> 3 -> 2 -> 1 -> 4 -> 5`**, `groupPrev` node(1) pe hai.

### Group 2 (nodes 4, 5)

**Step 1 — Check:** `getKth(groupPrev=node1, 3)` → node(1) se 3 step aage: 1→4→5→**null**
`kth = null` → **INCOMPLETE GROUP!** → `break` (loop ruk gaya, kuch reverse nahi hoga)

**Final answer: `3 -> 2 -> 1 -> 4 -> 5`** ✅

### Real output flow (console pe simplified trace):

```
input: head=[1,2,3,4,5], k=3
dummy -> 1 -> 2 -> 3 -> 4 -> 5,  groupPrev = dummy

--- Group 1 ---
getKth(dummy, 3) = node(3)  -> not null, groupNext = node(4)
reverse 1->2->3:
  curr=1: temp=2, 1.next=4, prev=1, curr=2
  curr=2: temp=3, 2.next=1, prev=2, curr=3
  curr=3: temp=4, 3.next=2, prev=3, curr=4
  curr==groupNext(4) -> stop reversal
groupPrev.next = kth(3)   -> dummy -> 3 -> 2 -> 1 -> 4 -> 5
groupPrev = temp(1)       -> groupPrev now at node(1)

--- Group 2 ---
getKth(node1, 3) -> 1->4->5->null -> null!
INCOMPLETE GROUP -> break

FINAL OUTPUT: [3, 2, 1, 4, 5]
```

### Notice karo yeh pattern:
- **Reversal se pehle hamesha check** karte hain (`getKth`) — isse humein pata chal
  jaata hai ki poora group hai ya nahi, **bina kisi node ka `next` badle**
- Reversal ke andar `prev` ko `groupNext` se shuru karna ek **chhota par zaroori
  trick** hai — isse group ka pehla node (jo reverse hoke aakhri banega) seedha
  agle group se **already connected** ho jaata hai, extra step nahi lagta
- Jab incomplete group milta hai, hum **turant `break`** kar dete hain — us group ko
  bilkul haath nahi lagate, wo waisi hi reh jaati hai jaisi thi

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

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `[1,2,3,4,5], k=2` | `[2,1,4,3,5]` | (1,2) aur (3,4) reverse hue, akela 5 waise hi raha |
| `[1,2,3,4,5], k=3` | `[3,2,1,4,5]` | (1,2,3) reverse hua, (4,5) incomplete tha, waise hi raha |
| `[1,2,3,4,5], k=5` | `[5,4,3,2,1]` | Poori list hi ek group hai, poori reverse hui |
