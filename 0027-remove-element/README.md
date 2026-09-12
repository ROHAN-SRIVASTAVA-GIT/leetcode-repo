# 27. Remove Element

LeetCode Link: https://leetcode.com/problems/remove-element/

## Problem kya keh raha hai (simple bhasha mein)

Humein ek array `nums` aur ek number `val` diya hai. Humein array mein se **jitni
baar bhi `val` aaya hai, un sabko hatana** hai — **in-place** (koi naya array nahi
banana), aur **kitne elements bache** (jo `val` nahi hain) wo count (`k`) return
karna hai.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho ek **line mein log khade hain**, kuch **ek specific rang ki shirt** (jaise
laal) pehne hain. Tumhe **saare laal shirt wale logo ko line se nikaal ke**, baaki
logo ko line ke **shuru mein compact** kar dena hai.

Jaise `[3,2,2,3]` mein `val=3` hatana hai: `3` do jagah hai, unhe hatao, bacha
`[2,2]`.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Yeh [Problem 26: Remove Duplicates](../0026-remove-duplicates-from-sorted-array)
jaisa hi **Slow & Fast Pointer** (in-place array modification) hai — bas condition
thodi alag hai:

| | Problem 26 (Duplicates) | Problem 27 (yeh) |
|---|---|---|
| Array sorted hona zaroori? | HAA | NAHI |
| Kab element "rakhte" hain? | Jab `nums[fast] != nums[slow]` | Jab `nums[fast] != val` |

**Steps:**
1. `slow` = "agla **valid** (jo `val` nahi hai) element kahan rakhna hai"
2. `fast` = poore array ko explore karta hai
3. Jab `fast` pe koi element milta hai jo **`val` NAHI hai** → use `slow` ki jagah
   copy karo, `slow++` karo
4. Jab `fast` pe `val` **milta hai** → kuch mat karo, bas aage badho (yeh element
   "chhoot" gaya, jaisa hataya gaya)

**Yaad rakhne wali baat:** yahan hum "pichhle element se compare" nahi kar rahe
(jaisa duplicates mein karte the), balki **seedha "val" ke saath compare** kar
rahe hain — isliye array sorted hona zaroori nahi hai.

## Dry Run — Loop ko haath se chala ke dekhte hain (real example)

Chalo `nums = [0,1,2,2,3,0,4,2]`, `val = 2` leke karte hain (index: 0 se 7).

**Shuruaat:** `slow = 0`

| `fast` | `nums[fast]` | Condition: `nums[fast] != val(2)`? | Action | `slow` (baad) | Array ka "confirmed" hissa |
|---|---|---|---|---|---|
| 0 | 0 | 0!=2? HAA | nums[0]=0, slow++ | 1 | [0] |
| 1 | 1 | 1!=2? HAA | nums[1]=1, slow++ | 2 | [0,1] |
| 2 | 2 | 2!=2? **NA** | kuch nahi (yeh "2" hai, hatana hai) | 2 | [0,1] |
| 3 | 2 | 2!=2? **NA** | kuch nahi | 2 | [0,1] |
| 4 | 3 | 3!=2? HAA | nums[2]=3, slow++ | 3 | [0,1,3] |
| 5 | 0 | 0!=2? HAA | nums[3]=0, slow++ | 4 | [0,1,3,0] |
| 6 | 4 | 4!=2? HAA | nums[4]=4, slow++ | 5 | [0,1,3,0,4] |
| 7 | 2 | 2!=2? **NA** | kuch nahi | 5 | [0,1,3,0,4] |

Loop khatam (fast array ke end tak pahunch gaya). **Final: `slow = 5` = answer**

**Array ab (pehle 5 elements): `[0, 1, 3, 0, 4]`** ✅ (2 kahin nahi bacha in pehle 5 mein)

### Real output flow (console pe kya print hota hai):

```
input: nums=[0,1,2,2,3,0,4,2], val=2
slow=0

fast=0: nums[0]=0 != 2 -> KEEP, nums[0]=0, slow=1
fast=1: nums[1]=1 != 2 -> KEEP, nums[1]=1, slow=2
fast=2: nums[2]=2 == 2 -> SKIP (remove)
fast=3: nums[3]=2 == 2 -> SKIP (remove)
fast=4: nums[4]=3 != 2 -> KEEP, nums[2]=3, slow=3
fast=5: nums[5]=0 != 2 -> KEEP, nums[3]=0, slow=4
fast=6: nums[6]=4 != 2 -> KEEP, nums[4]=4, slow=5
fast=7: nums[7]=2 == 2 -> SKIP (remove)

loop ends. answer = slow = 5
array's first 5 elements: [0,1,3,0,4]

FINAL OUTPUT: k=5, array=[0,1,3,0,4]
```

### Notice karo yeh pattern:
- `slow` sirf **valid elements** ke liye badhta hai — jab bhi `val` milta hai, `slow`
  **wahi ruka rehta hai**, aur agli baar jab valid element milega, wahi **overwrite**
  ho jaayega us jagah
- Order **maintain nahi hota** zaroori nahi — jaise yahan `[0,1,3,0,4]` mein original
  order thoda alag reflect hua, lekin LeetCode is problem mein **order ki parwah
  nahi karta**, sirf yeh check karta hai ki pehle `k` elements mein sirf non-`val`
  values hon (kisi bhi order mein)

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `int slow = 0;` | Agla valid element kaha rakhna hai, uska index |
| `for (int fast = 0; ...)` | Poore array ko explore karne wala loop |
| `nums[fast] != val` | Check — yeh element rakhna hai (hatana nahi) |
| `nums[slow] = nums[fast];` | Valid element ko sahi jagah copy karna |
| `slow++;` | Valid elements ke liye "confirmed jagah" ek aage badhana |
| `return slow;` | Total valid (bache hue) elements ki ginti |

## Complexity

- **Time:** O(n) — array ko sirf ek baar traverse karte hain
- **Space:** O(1) — koi naya array nahi banaya, sab kuch in-place hua

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `nums=[3,2,2,3], val=3` | `k=2, array=[2,2]` | Dono "3" hataye, "2,2" bache |
| `nums=[0,1,2,2,3,0,4,2], val=2` | `k=5, array=[0,1,3,0,4]` | Teeno "2" hataye, 5 elements bache |
| `nums=[1,2,3], val=5` | `k=3, array=[1,2,3]` | "5" tha hi nahi array mein, sab waise hi rahe |
