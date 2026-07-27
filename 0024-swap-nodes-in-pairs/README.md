# 24. Swap Nodes in Pairs

LeetCode Link: https://leetcode.com/problems/swap-nodes-in-pairs/

**Note (LeetCode pe paste karte waqt):** Is file mein `class ListNode {...}` block hai —
wo sirf apni machine pe standalone test karne ke liye hai. LeetCode ke editor mein
sirf `public class Solution {...}` wala part paste karna.

## Problem kya keh raha hai (simple bhasha mein)

Humein ek Linked List di gayi hai. Humein **har do adjacent (paas-paas ke) nodes ko
aapas mein swap** karna hai, aur updated list ka head return karna hai.

Jaise `1 -> 2 -> 3 -> 4` ban jaayega `2 -> 1 -> 4 -> 3`.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho ek train ke dabbe hain: `1, 2, 3, 4`. Tumhe unhe **jodi-jodi (pairs) mein
pakadke ghumana** hai — pehli jodi (1,2) ko ghuma do, doosri jodi (3,4) ko ghuma do.

**Bahut important cheez:** hum **values ko copy-paste nahi kar rahe** (jaise
"node1.val = node2.val" karke value badal dena). Hum **asli dabbon ki "next" wire
ko todke naye tareeke se jodte hain** — jaise real train ke dabbe khud apni jagah
badalte hain, sirf unke andar ka saman nahi badalta.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Jab bhi Linked List mein "**nodes ko reorder/reverse/swap**" karna ho, toh yaad
rakho: tumhe hamesha **3 nodes track karne padte hain**:
1. **Pehle wala node** (jiska "next" hum badlenge)
2. **Current node** (jise hum move kar rahe hain)
3. **Agla node** (jahan connect karna hai)

Agar in teeno ko sahi order mein connect nahi kiya, toh list **"toot" (disconnect)**
ho sakti hai ya **infinite loop** ban sakta hai. Isliye hamesha **pehle naya connection
bana lo, tabhi purana todo** — ya phir dhyan se ek fix order follow karo (jaisa is
solution mein kiya hai).

**Dummy Node trick (Problem 19, 21, 23 se yaad hai):** kyunki **head khud bhi swap ho
sakta hai** (pehli jodi mein head shamil hai), dummy node use karke humein "agar head
change ho jaaye toh" jaisa special case nahi sochna padta.

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `dummy.next = head` | Dummy node ko list se jodna, taaki head-swap bhi normal case ban jaaye |
| `prev` | Current pair se PEHLE wala node (shuru mein dummy) |
| `while (prev.next != null && prev.next.next != null)` | Jab tak ek poora pair (2 nodes) bacha hai |
| `first`, `second` | Current pair ke dono nodes |
| `first.next = second.next;` | first ka next, second ke aage wale se jodna (second ko skip) |
| `second.next = first;` | second ka next, first ki taraf — swap ho gaya |
| `prev.next = second;` | Pichhle pair ko naye pair ke pehle node (second) se jodna |
| `prev = first;` | Agle pair ki taraf badhna (ab first hi "pichhla" node hai) |

## Complexity

- **Time:** O(n) — list ko sirf ek baar traverse karte hain
- **Space:** O(1) — koi naya node nahi banate, sirf existing nodes ko re-link karte hain
