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

## Dry Run — Pointers ko haath se chala ke dekhte hain (real example)

Chalo `head = [1,2,3,4]` leke karte hain.

**Setup:** `dummy -> 1 -> 2 -> 3 -> 4 -> null`, `prev = dummy`

### Iteration 1 (pair: 1, 2)

Check: `prev.next(1) != null && prev.next.next(2) != null`? **HAA**

| Step | Action | State (arrows) |
|---|---|---|
| Naming | `first = prev.next` = node(1), `second = first.next` = node(2) | `dummy->1->2->3->4` |
| `first.next = second.next` | node(1) ka next ab node(3) | `dummy->1->3->4`, aur `2->?` abhi purana hi hai |
| `second.next = first` | node(2) ka next ab node(1) | `2 -> 1 -> 3 -> 4` ban gaya |
| `prev.next = second` | dummy ka next ab node(2) | `dummy -> 2 -> 1 -> 3 -> 4` |
| `prev = first` | prev ab node(1) pe | (agla pair dhundhne ke liye ready) |

**Ab list hai: `dummy -> 2 -> 1 -> 3 -> 4 -> null`**, `prev` node(1) pe khada hai.

### Iteration 2 (pair: 3, 4)

Check: `prev.next(3) != null && prev.next.next(4) != null`? **HAA**

| Step | Action | State (arrows) |
|---|---|---|
| Naming | `first = prev.next` = node(3), `second = first.next` = node(4) | |
| `first.next = second.next` | node(3) ka next ab null (4 ke aage kuch nahi tha) | |
| `second.next = first` | node(4) ka next ab node(3) | `4 -> 3 -> null` ban gaya |
| `prev.next = second` | node(1) ka next ab node(4) | `1 -> 4 -> 3 -> null` |
| `prev = first` | prev ab node(3) pe | |

**Ab list hai: `dummy -> 2 -> 1 -> 4 -> 3 -> null`**

### Iteration 3 — loop check

`prev.next` = null (node(3) ke aage kuch nahi) → condition `prev.next != null` **FALSE**
→ loop RUK GAYA

**Final answer (dummy.next se): `2 -> 1 -> 4 -> 3`** ✅

### Real output flow (console pe kya print hota hai):

```
input: head = [1,2,3,4]
dummy -> 1 -> 2 -> 3 -> 4 -> null,  prev = dummy

iteration 1: pair(1,2)
  first=1, second=2
  first.next = second.next  -> 1.next = 3
  second.next = first        -> 2.next = 1
  prev.next = second         -> dummy.next = 2
  prev = first                -> prev = 1
  list now: dummy -> 2 -> 1 -> 3 -> 4

iteration 2: pair(3,4)
  first=3, second=4
  first.next = second.next  -> 3.next = null
  second.next = first        -> 4.next = 3
  prev.next = second         -> 1.next = 4
  prev = first                -> prev = 3
  list now: dummy -> 2 -> 1 -> 4 -> 3

check: prev.next = null -> loop stops

FINAL OUTPUT: [2, 1, 4, 3]
```

### Notice karo yeh pattern:
- Order **bahut important** hai: pehle `first.next` badalte hain, phir `second.next`,
  phir `prev.next` — agar order galat kiya, toh koi node **"lost"** ho sakta hai
  (uski taraf koi point hi nahi karega)
- `prev` hamesha **swap ho chuke pair ke AAKHRI node** (jo ab `first` hai) pe move
  hota hai, taaki agla pair dhundh sake

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

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `[1,2,3,4]` | `[2,1,4,3]` | Dono pairs (1,2) aur (3,4) swap hue |
| `[]` | `[]` | Khaali list, kuch swap karne ko hai hi nahi |
| `[1,2,3]` | `[2,1,3]` | Pehla pair (1,2) swap hua, akela bacha 3 waise hi raha |
