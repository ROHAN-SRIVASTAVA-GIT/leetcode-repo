# 23. Merge k Sorted Lists

LeetCode Link: https://leetcode.com/problems/merge-k-sorted-lists/

**Note (LeetCode pe paste karte waqt):** Is file mein `class ListNode {...}` block hai —
wo sirf apni machine pe standalone test karne ke liye hai. LeetCode ke editor mein
sirf `public class Solution {...}` wala part paste karna.

## Problem kya keh raha hai (simple bhasha mein)

Humein **`k` sorted Linked Lists** ka ek array diya hai. Humein sabko jodke ek **naya
single sorted Linked List** banana hai jisme sabhi lists ke nodes hon.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Yeh [Problem 21: Merge Two Sorted Lists](../0021-merge-two-sorted-lists) ka bada
version hai. Wahan sirf **2 sorted piles of cards** thi, ab humare paas **`k` (bahut
saari) sorted piles** hain, aur sabko milake **ek hi sorted pile** banani hai.

**Naive tareeka:** "merge 2 lists" wala function baar-baar use karo — pehli 2 ko
merge karo, phir result ko teesri se, phir chauthi se... Kaam ho jaayega, par slow hai.

**Better tareeka:** ek **special basket** socho jo hamesha **sabse chhoti cheez ko
upar rakhta hai**, chahe tum kisi order mein cheezein daalo. Isko **Min-Heap
(Priority Queue)** kehte hain.

Kaam kaise hoga:
1. Saari `k` lists ke **pehle (sabse chhote) node** ko basket mein daal do
2. Basket se **sabse chhoti value wala node nikaalo** (basket khud batata hai kaunsa
   chhota hai, poori list scan nahi karni padti)
3. Use apni final answer list mein jodo
4. Us node ki list ka **agla node** (agar hai) basket mein wapas daal do
5. Repeat karo jab tak basket khaali na ho jaaye

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho) — NAYI DATA STRUCTURE: MIN-HEAP

Jab bhi sawal ho: **"bahut saari sorted cheezein hain, baar-baar sabse chhoti
(ya sabse badi) cheez nikaalni hai"** — toh **Priority Queue / Heap** use karo.

**Heap kya hai (simple samjho):** ek aisi data structure jo apne andar rakhi
cheezon mein se **hamesha sabse chhoti (Min-Heap) ya sabse badi (Max-Heap)** ko
turant bata sakti hai — **poore data ko scan kiye bina**. Java mein iska naam
`PriorityQueue` hai.

- `offer(x)` — heap mein cheez daalna
- `poll()` — heap se sabse chhoti (ya jaisa comparator set kiya ho) cheez nikaalna
- `peek()` — sabse chhoti cheez dekhna, bina nikale

Yahan humne comparator diya: `(a, b) -> a.val - b.val` — matlab **"val" field ke
hisaab se chhota-bada compare karo**, isse yeh Min-Heap ban gaya (sabse chhoti
`val` wala node hamesha "upar" rehta hai).

**Kyun better hai "merge 2 baar-baar" karne se?** Kyunki heap **turant** bata deta
hai sabse chhota kaun hai (`O(log k)` mein), jabki baar-baar merge karne mein har
list ko baar-baar poora scan karna padta.

## Dry Run — Heap ko haath se chala ke dekhte hain (real example)

Chalo `lists = [[1,4,5], [1,3,4], [2,6]]` leke karte hain.

**Step 1: Saari lists ke pehle node heap mein daalo**

Heap mein daale: `1` (list0), `1` (list1), `2` (list2)
Heap ke andar (sabse chhota "upar"): `{1, 1, 2}`

**Step 2: Loop chalao — har baar sabse chhota nikaalo, uska next daalo**

| Heap (poll se pehle) | `poll()` se nikla | Merged list mein jodo | Uska `next`? | Heap mein naya add | Heap (baad mein) |
|---|---|---|---|---|---|
| {1(L0), 1(L1), 2(L2)} | 1 (L0) | [1] | L0's next = 4 | offer(4) | {1(L1), 2(L2), 4(L0)} |
| {1(L1), 2(L2), 4(L0)} | 1 (L1) | [1,1] | L1's next = 3 | offer(3) | {2(L2), 3(L1), 4(L0)} |
| {2(L2), 3(L1), 4(L0)} | 2 (L2) | [1,1,2] | L2's next = 6 | offer(6) | {3(L1), 4(L0), 6(L2)} |
| {3(L1), 4(L0), 6(L2)} | 3 (L1) | [1,1,2,3] | L1's next = 4 | offer(4) | {4(L0), 4(L1), 6(L2)} |
| {4(L0), 4(L1), 6(L2)} | 4 (L0) | [1,1,2,3,4] | L0's next = 5 | offer(5) | {4(L1), 5(L0), 6(L2)} |
| {4(L1), 5(L0), 6(L2)} | 4 (L1) | [1,1,2,3,4,4] | L1's next = null | kuch nahi add hua | {5(L0), 6(L2)} |
| {5(L0), 6(L2)} | 5 (L0) | [1,1,2,3,4,4,5] | L0's next = null | kuch nahi | {6(L2)} |
| {6(L2)} | 6 (L2) | [1,1,2,3,4,4,5,6] | L2's next = null | kuch nahi | {} khaali |

Heap khaali ho gaya, loop ruk gaya. **Final answer: `[1,1,2,3,4,4,5,6]`** ✅

### Real output flow (console pe simplified trace):

```
input: lists = [[1,4,5],[1,3,4],[2,6]]

heap initial: {1(L0), 1(L1), 2(L2)}

poll 1(L0) -> merged=[1]        -> offer L0.next=4  -> heap: {1(L1),2(L2),4(L0)}
poll 1(L1) -> merged=[1,1]      -> offer L1.next=3  -> heap: {2(L2),3(L1),4(L0)}
poll 2(L2) -> merged=[1,1,2]    -> offer L2.next=6  -> heap: {3(L1),4(L0),6(L2)}
poll 3(L1) -> merged=[1,1,2,3]  -> offer L1.next=4  -> heap: {4(L0),4(L1),6(L2)}
poll 4(L0) -> merged=[...,4]    -> offer L0.next=5  -> heap: {4(L1),5(L0),6(L2)}
poll 4(L1) -> merged=[...,4,4]  -> L1.next=null, nothing offered -> heap: {5(L0),6(L2)}
poll 5(L0) -> merged=[...,5]    -> L0.next=null, nothing offered -> heap: {6(L2)}
poll 6(L2) -> merged=[...,6]    -> L2.next=null, nothing offered -> heap: {} EMPTY

FINAL OUTPUT: [1,1,2,3,4,4,5,6]
```

### Notice karo yeh pattern:
- Heap **hamesha khud figure out** kar leta hai sabse chhota kaun hai — humein khud
  se saari `k` lists ke fronts compare nahi karne padte (jo `k` lists ke liye slow hota)
- Jab bhi ek node **poll** hota hai, uski list ka **agla node turant heap mein wapas
  chala jaata hai** — isse heap mein hamesha "abhi tak ki saari lists ke current
  fronts" maujood rehte hain
- Jab kisi node ka `next` `null` hota hai (list khatam ho gayi), heap mein kuch add
  nahi hota — wo list "retire" ho jaati hai

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `PriorityQueue<ListNode> minHeap` | Min-Heap — hamesha sabse chhoti value wala node "upar" rakhta hai |
| `(a, b) -> a.val - b.val` | Comparator — batata hai kis field se chhota/bada compare karna hai |
| `minHeap.offer(listHead)` | Heap mein node daalna |
| `minHeap.poll()` | Heap se sabse chhota node nikaalna |
| `dummy`, `current` | Merged list banane ke liye (jaise Problem 19, 21 mein) |
| `if (smallest.next != null) minHeap.offer(smallest.next);` | Nikale gaye node ki list ka agla node wapas heap mein daalna |

## Complexity

- **Time:** O(N log k) — jahan N = saare nodes ka total count, k = lists ki ginti.
  Har node heap mein ek baar jaata hai aur nikalta hai, aur heap operations
  `O(log k)` time lete hain
- **Space:** O(k) — heap mein zyada se zyada `k` nodes hote hain (ek har list se)

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `[[1,4,5],[1,3,4],[2,6]]` | `[1,1,2,3,4,4,5,6]` | Teeno lists ke saare nodes sorted order mein merge hue |
| `[]` | `[]` | Koi list di hi nahi gayi |
| `[null]` | `[]` | Ek list di gayi, par wo khud khaali thi |
