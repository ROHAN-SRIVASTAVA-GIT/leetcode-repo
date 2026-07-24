# 19. Remove Nth Node From End of List

LeetCode Link: https://leetcode.com/problems/remove-nth-node-from-end-of-list/

## Problem kya keh raha hai (simple bhasha mein)

Humein ek **Linked List** di gayi hai (ek naya data structure — neeche samjhaya hai)
aur ek number `n`. Humein list ke **peeche se `n`-wa node hatana** hai, aur updated
list ka **head (shuruaat)** return karna hai.

## Sabse pehle: Linked List kya hai?

Array mein saare elements memory mein **ek line mein saath-saath** rakhe hote hain.
Linked List alag hai — yeh **chhote-chhote dabbon (nodes) ki chain** hai, jaha har
dabbe ke paas do cheezein hoti hain:

```
[value | next] -> [value | next] -> [value | next] -> null
```

- `value` = us dabbe mein rakha number
- `next` = agle dabbe ka address (aakhri dabbe ka `next` hamesha `null` hota hai —
  matlab "yahi list khatam ho gayi")

Hum list ko **sirf head (pehle dabbe) se hi access kar sakte hain**, aur `next` ke
through ek-ek dabba aage badhte hain — array jaisa **seedha `list[5]` access** nahi
kar sakte.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho ek **train** hai jisme dabbe hain: `1 -> 2 -> 3 -> 4 -> 5`. Tumhe **peeche se
2nd dabba** hatana hai (yaha wo hai `4`, kyunki peeche se: 5=1st, 4=2nd).

Dikkat: tumhe **pehle se pata nahi ki train mein kitne dabbe hain** (sirf front se
dekh sakte ho, ek-ek karke). Toh kaise pata karein "peeche se 2nd" kaunsa hai?

**Trick:** do dost train mein chalte hain, ek fix `gap` (doori) rakhke:
1. Pehla dost (**fast**) akela **`n+1` dabbe aage** chala jaata hai
2. Ab **dono dost ek saath chalna** shuru karte hain, ek-ek dabba
3. Jab **fast dost train ke bahar (end) nikal jaata hai** (null), tab **slow dost
   EXACTLY us jagah hota hai jaha se node hatana hai** — kyunki gap hamesha same tha!

**"+1" kyun kiya?** Kyunki humein `slow` ko us dabbe pe **rukwana** hai jo hatane
wale dabbe se **EK PEHLE** hai (uska "next" hi hum badalenge, taaki hatane wala dabba
"skip" ho jaaye).

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Jab bhi Linked List mein sawal ho:
- "**end se kuch distance pe** kya hai" (jaise yahan)
- "**middle** node dhundo"
- "**cycle** (loop) hai kya list mein"

...toh **"Fast & Slow Pointer"** (Two Pointer for Linked Lists) pattern try karo.
Fast ko pehle se **gap banake aage bhej do**, phir dono ko saath chalao — jab fast
apni manzil pe pahunche, slow apni manzil pe khud-ba-khud pahunch jaata hai.

**Extra trick — Dummy Node:** agar humein kabhi **head hi hatana** pad sakta hai
(edge case), toh code mein "agar head hatana hai toh alag se handle karo" jaisa
if-else likhne se bachne ke liye, ek **fake extra node (dummy)** list ke bilkul
shuru mein laga dete hain. Isse **HAR case ek jaisa** handle ho jaata hai, chahe
head hatana ho ya beech ka koi node.

## Dry Run — Loop ko haath se chala ke dekhte hain (real example)

Chalo `head = [1,2,3,4,5]`, `n = 2` leke karte hain (peeche se 2nd node = `4` hatana hai).

**Setup:** `dummy -> 1 -> 2 -> 3 -> 4 -> 5 -> null`, `fast = dummy`, `slow = dummy`

### Step 1: fast ko `n+1 = 3` dabbe aage bhejo

| Iteration (`i`) | `fast` move karne se pehle | `fast = fast.next` ke baad |
|---|---|---|
| i=0 | dummy | 1 |
| i=1 | 1 | 2 |
| i=2 | 2 | 3 |

Loop khatam (3 baar chala). Ab `fast` node `3` pe hai, `slow` abhi bhi `dummy` pe hai.

### Step 2: dono ko saath chalao jab tak fast null na ho

| Check: `fast != null`? | `fast` (pehle) | `slow` (pehle) | Action | `fast` (baad) | `slow` (baad) |
|---|---|---|---|---|---|
| 3 != null? HAA | 3 | dummy | dono ++ | 4 | 1 |
| 4 != null? HAA | 4 | 1 | dono ++ | 5 | 2 |
| 5 != null? HAA | 5 | 2 | dono ++ | null | 3 |
| null != null? **NA** | — | — | loop RUKA | — | — |

Loop khatam. Ab **`slow` node `3` pe khada hai** (jo hatane wale node `4` se EK PEHLE hai).

### Step 3: Node hatana

`slow.next` abhi `4` hai. `slow.next.next` = `5`. Toh:
```
slow.next = slow.next.next   // slow.next ab "4" ki jagah "5" ban gaya
```

Naya connection: `3 -> 5` (node `4` ab kisi se bhi connected nahi, "hat gaya")

**Final list (dummy.next se): `1 -> 2 -> 3 -> 5 -> null`** ✅

### Real output flow (console pe kya print hota hai):

```
input: head=[1,2,3,4,5], n=2

dummy -> 1 -> 2 -> 3 -> 4 -> 5 -> null
fast = dummy, slow = dummy

--- moving fast n+1=3 steps ---
i=0: fast: dummy -> 1
i=1: fast: 1 -> 2
i=2: fast: 2 -> 3
(fast is now at node "3")

--- moving both together until fast is null ---
fast=3 (not null): fast->4, slow: dummy->1
fast=4 (not null): fast->5, slow: 1->2
fast=5 (not null): fast->null, slow: 2->3
fast=null: STOP

slow is now at node "3" (one before the node to remove)

--- removing ---
slow.next was "4", now slow.next = slow.next.next = "5"
list becomes: 1 -> 2 -> 3 -> 5 -> null

FINAL OUTPUT: [1, 2, 3, 5]
```

### Notice karo yeh pattern:
- `fast` ko `n+1` aage bhejna (na ki sirf `n`) hi trick ka **sabse important part** hai
  — isi se `slow` "ek pehle wale" node pe rukta hai
- **Dummy node** ki wajah se agar `n` bhi list ki poori length ke barabar ho (matlab
  head hi hatana ho), tab bhi code same tareeke se kaam karta hai — koi alag if-else
  nahi chahiye
- Node ko "hatana" ka matlab hai bas uska **address kisi variable mein na rakha ho**
  aur koi bhi node uski taraf point na kare — Java khud usko memory se saaf kar
  deta hai (garbage collection)

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `class ListNode` | Ek node ka blueprint — `val` (value) aur `next` (agle node ka address) |
| `ListNode dummy = new ListNode(0)` | Fake node, head-removal edge case handle karne ke liye |
| `dummy.next = head` | Dummy ko asli list se jodna |
| `for (int i = 0; i < n + 1; i++) fast = fast.next;` | Fast ko n+1 aage bhejna (gap banana) |
| `while (fast != null) { fast=fast.next; slow=slow.next; }` | Dono ko saath chalana |
| `slow.next = slow.next.next;` | Beech wale node ko "skip" karke hatana |
| `return dummy.next;` | Asli (updated) list ka head return karna |

## Complexity

- **Time:** O(L) — jahan L = list ki length. List ko sirf ek baar traverse karte hain
  (do pointers ke saath, lekin dono total milke bhi O(L) hi hote hain)
- **Space:** O(1) — sirf 3 extra pointers use ho rahe hain (dummy, fast, slow), koi
  extra data structure nahi

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `head=[1,2,3,4,5], n=2` | `[1,2,3,5]` | Peeche se 2nd node (4) hataya |
| `head=[1], n=1` | `[]` | Sirf ek hi node tha, wahi hataya, list khaali ho gayi |
| `head=[1,2], n=2` | `[2]` | Peeche se 2nd = head node (1), dummy trick se yeh bhi asaani se handle hua |
