# 21. Merge Two Sorted Lists

LeetCode Link: https://leetcode.com/problems/merge-two-sorted-lists/

**Note (LeetCode pe paste karte waqt):** Is file mein `class ListNode {...}` block hai —
wo sirf apni machine pe standalone test karne ke liye hai. LeetCode ke editor mein
sirf `public class Solution {...}` wala part paste karna, `ListNode` class wahan
already maujood hai (jaisa [Problem 19](../0019-remove-nth-node-from-end-of-list) mein seekha tha).

## Problem kya keh raha hai (simple bhasha mein)

Humein **do sorted Linked Lists** di gayi hain (dono chhote se bade order mein hain).
Humein dono ko jodke ek **naya sorted Linked List** banana hai, jisme dono lists ke
saare nodes hon.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho tumhare paas **do sorted piles of playing cards** hain — dono pile ke andar
cards chhote se bade order mein lage hain (jaise 1,2,4 aur 1,3,4).

Tumhe dono piles ko milake **ek hi sorted pile** banani hai. Kaise karoge?

Dono piles ke **sabse upar wale card** ko dekho aur compare karo:
- Jo **chhota** hai, use utha ke naye pile mein rakh do
- Us pile ka **agla card** ab upar aa gaya

Yeh baar-baar repeat karo. Jab **ek pile poori khatam** ho jaaye (khaali ho jaaye),
toh doosri pile mein jo bhi bacha hai, use **poora ka poora seedha jod do** — kyunki
wo already sorted hai, use compare karne ki zaroorat nahi.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Jab bhi sawal ho: **"do (ya zyada) already-sorted cheezein ho, unhe ek sorted cheez
mein jodna ho"** — yeh **"Merge"** technique hai. Yehi step **Merge Sort** algorithm
ka core hissa bhi hai (isliye "Merge Step" bhi kehte hain).

**Steps:**
1. Dono lists ke **current (front) nodes compare** karo
2. Jo **chhota** hai, use naye list mein jodo, us list ko aage badhao
3. Jab tak **dono** lists mein kuch bacha hai, repeat karo
4. Jaise hi ek list **khatam (null)** ho jaaye, doosri **bachi hui poori list** ko
   seedha jod do (already sorted hai, extra kaam nahi karna)

**Dummy Node trick (Problem 19 se yaad hai):** merged list banate waqt "sabse pehla
node kaise set karein" jaisa special case avoid karne ke liye, ek **fake dummy node**
se shuru karte hain.

## Dry Run — Loop ko haath se chala ke dekhte hain (real example)

Chalo `list1 = [1,2,4]`, `list2 = [1,3,4]` leke karte hain.

**Setup:** `dummy -> null`, `current = dummy`

| Check `list1!=null && list2!=null`? | `list1.val` | `list2.val` | Compare | Action | `current.next` set hua | naya `list1` | naya `list2` |
|---|---|---|---|---|---|---|---|
| HAA | 1 | 1 | 1 <= 1 | list1 chuno | `current.next = list1(1)` | list1 → node(2) | list2 same (1) |
| HAA | 2 | 1 | 2 <= 1? NA | list2 chuno | `current.next = list2(1)` | list1 same (2) | list2 → node(3) |
| HAA | 2 | 3 | 2 <= 3 | list1 chuno | `current.next = list1(2)` | list1 → node(4) | list2 same (3) |
| HAA | 4 | 3 | 4 <= 3? NA | list2 chuno | `current.next = list2(3)` | list1 same (4) | list2 → node(4) |
| HAA | 4 | 4 | 4 <= 4 | list1 chuno | `current.next = list1(4)` | list1 → null | list2 same (4) |
| `list1 != null`? **NA** (list1 ab null hai) | — | — | — | loop RUKA | — | — | — |

Loop khatam kyunki `list1` null ho gaya. Ab **final step:**

```
if (list1 != null) ... else current.next = list2;
```
`list1` null hai, toh `current.next = list2` — jo abhi bhi node(4) pe hai (bachi hui poori list, sirf ek node).

**Final merged list (dummy.next se): `1 -> 1 -> 2 -> 3 -> 4 -> 4`** ✅

### Real output flow (console pe kya print hota hai):

```
input: list1=[1,2,4], list2=[1,3,4]

dummy -> null, current = dummy

step1: list1.val=1, list2.val=1  -> 1<=1, pick list1(1)  -> current.next=1, list1 moves to 2
step2: list1.val=2, list2.val=1  -> 2<=1? NO, pick list2(1) -> current.next=1, list2 moves to 3
step3: list1.val=2, list2.val=3  -> 2<=3, pick list1(2)  -> current.next=2, list1 moves to 4
step4: list1.val=4, list2.val=3  -> 4<=3? NO, pick list2(3) -> current.next=3, list2 moves to 4
step5: list1.val=4, list2.val=4  -> 4<=4, pick list1(4)  -> current.next=4, list1 moves to null

list1 is now null -> loop stops
remaining: list2 = [4] -> attach directly: current.next = list2

FINAL OUTPUT: [1, 1, 2, 3, 4, 4]
```

### Notice karo yeh pattern:
- `current.next = list1` (ya `list2`) **naya node banata nahi hai** — bas **existing
  node ko link kar deta hai** naye list mein. Isse hum efficient rehte hain (koi extra
  memory waste nahi)
- Jab dono values **barabar** hoti hain (jaise step5 mein 4==4), `<=` use karne ki
  wajah se hum **list1 ko priority** dete hain — dono chalega, bas ek consistent
  rule chahiye
- Loop khatam hote hi jo list bachi thi (yaha `list2 = [4]`), use **seedha attach**
  kar diya — usme comparison ki zaroorat hi nahi thi

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `ListNode dummy = new ListNode(0)` | Fake starting node, merged list banane ke liye |
| `ListNode current = dummy` | "Aakhri jode gaye node" ko track karne wala pointer |
| `while (list1 != null && list2 != null)` | Jab tak dono lists mein kuch bacha hai |
| `list1.val <= list2.val` | Compare karna — kaunsa chhota hai |
| `current.next = list1;` | Chhote node ko merged list mein jodna |
| `list1 = list1.next;` | Us list ko agle node pe badhana |
| `current = current.next;` | Merged list ke "aakhri node" pointer ko bhi aage badhana |
| `if (list1 != null) current.next = list1; else ...` | Bachi hui poori list ko seedha jodna |
| `return dummy.next;` | Merged list ka asli head return karna |

## Complexity

- **Time:** O(n + m) — jahan n aur m dono lists ki lengths hain, kyunki dono ko
  ek-ek baar hi traverse karte hain
- **Space:** O(1) — koi naya node nahi banate (sirf existing nodes ko re-link karte
  hain), sirf kuch pointers use ho rahe hain

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `list1=[1,2,4], list2=[1,3,4]` | `[1,1,2,3,4,4]` | Dono lists merge karke sorted order |
| `list1=[], list2=[]` | `[]` | Dono khaali, toh merged bhi khaali |
| `list1=[], list2=[0]` | `[0]` | list1 khaali hai, toh list2 seedha attach ho gayi |
