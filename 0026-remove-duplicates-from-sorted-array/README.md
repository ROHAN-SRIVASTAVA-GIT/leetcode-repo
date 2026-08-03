# 26. Remove Duplicates from Sorted Array

LeetCode Link: https://leetcode.com/problems/remove-duplicates-from-sorted-array/

## Problem kya keh raha hai (simple bhasha mein)

Humein ek **sorted array** diya hai jisme duplicates ho sakte hain. Humein array ko
**in-place modify** karna hai (koi naya array nahi banana) taaki **saare unique
elements array ke shuru mein** aa jaayein, sorted order mein, aur **unique elements
ki ginti (`k`) return** karni hai. Array ke baaki hisse mein kya hai, uska koi
matter nahi.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho ek **line mein log khade hain**, kuch log ek jaisi shirt pehne hain (duplicates),
ek ke baad ek. Jaise: `laal, laal, neela, neela, neela, hara`.

Tumhe line ko **compact** karna hai taaki sirf **ek-ek unique shirt wala** bandaa
line ke shuru mein reh jaaye: `laal, neela, hara`. Baaki jagah kaise bhi ho, farak
nahi padta.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Jab bhi sawal ho: **"sorted array mein se kuch elements hatao, in-place (bina extra
array banaye)"** — toh **Slow & Fast Pointer** (in-place array modification wala
Two Pointer) use karo.

- **`slow` pointer** = "**ab tak jitni jagah unique elements ke liye confirm ho chuki
  hai**" — hamesha wahan point karta hai jaha **agla unique element rakhna hai**
- **`fast` pointer** = poore array ko **explore** karta hai, ek-ek karke

**Rule:** jab bhi `fast` pe ek **naya (slow se alag) element** milta hai, `slow` ko
ek aage badhao aur wahan `fast` wala element **copy** kar do. Agar `fast` pe wahi
element mila jo `slow` pe hai (duplicate), toh **kuch mat karo**, bas `fast` aage
badhta rahega.

**Yeh technique kaam kyun karti hai?** Kyunki array **already sorted** hai — matlab
saare **same numbers ek saath (adjacent)** hote hain. Isliye humein sirf **"pichhle
wale se alag hai kya"** check karna kaafi hai — poori list scan karke duplicate
dhundhne ki zaroorat nahi padti.

## Dry Run — Loop ko haath se chala ke dekhte hain (real example)

Chalo `nums = [0,0,1,1,1,2,2,3,3,4]` leke karte hain (index: 0 se 9).

**Shuruaat:** `slow = 0` (nums[0]=0 hamesha unique maana)

| `fast` | `nums[fast]` | `nums[slow]` | Condition: `nums[fast] != nums[slow]`? | Action | `slow` (baad) | Array ka hissa jo "confirm" hai |
|---|---|---|---|---|---|---|
| 1 | 0 | 0 | 0!=0? NA | kuch nahi (duplicate) | 0 | [0] |
| 2 | 1 | 0 | 1!=0? HAA | slow++, nums[1]=1 | 1 | [0,1] |
| 3 | 1 | 1 | 1!=1? NA | kuch nahi (duplicate) | 1 | [0,1] |
| 4 | 1 | 1 | 1!=1? NA | kuch nahi (duplicate) | 1 | [0,1] |
| 5 | 2 | 1 | 2!=1? HAA | slow++, nums[2]=2 | 2 | [0,1,2] |
| 6 | 2 | 2 | 2!=2? NA | kuch nahi (duplicate) | 2 | [0,1,2] |
| 7 | 3 | 2 | 3!=2? HAA | slow++, nums[3]=3 | 3 | [0,1,2,3] |
| 8 | 3 | 3 | 3!=3? NA | kuch nahi (duplicate) | 3 | [0,1,2,3] |
| 9 | 4 | 3 | 4!=3? HAA | slow++, nums[4]=4 | 4 | [0,1,2,3,4] |

Loop khatam (fast, array ke end tak pahunch gaya). **Final: `slow=4`, toh `k = slow+1 = 5`**

**Array ab (pehle 5 elements): `[0, 1, 2, 3, 4]`** ✅

### Real output flow (console pe kya print hota hai):

```
input: nums=[0,0,1,1,1,2,2,3,3,4]
slow=0 (index 0 already unique)

fast=1: nums[1]=0, nums[slow]=0 -> SAME, duplicate, skip
fast=2: nums[2]=1, nums[slow]=0 -> DIFFERENT! slow=1, nums[1]=1
fast=3: nums[3]=1, nums[slow]=1 -> SAME, duplicate, skip
fast=4: nums[4]=1, nums[slow]=1 -> SAME, duplicate, skip
fast=5: nums[5]=2, nums[slow]=1 -> DIFFERENT! slow=2, nums[2]=2
fast=6: nums[6]=2, nums[slow]=2 -> SAME, duplicate, skip
fast=7: nums[7]=3, nums[slow]=2 -> DIFFERENT! slow=3, nums[3]=3
fast=8: nums[8]=3, nums[slow]=3 -> SAME, duplicate, skip
fast=9: nums[9]=4, nums[slow]=3 -> DIFFERENT! slow=4, nums[4]=4

loop ends. k = slow+1 = 5
array's first 5 elements: [0,1,2,3,4]

FINAL OUTPUT: k=5, array=[0,1,2,3,4]
```

### Notice karo yeh pattern:
- `slow` **sirf tab move hota hai** jab ek naya unique element milta hai — yeh
  hamesha **"unique elements ka aakhri confirmed index"** hota hai
- `fast` **hamesha aage badhta hai**, chahe duplicate mile ya naya element — yeh
  poore array ko explore karta rehta hai
- Array ke **baaki (index slow se aage) elements** ko humne touch hi nahi kiya — unki
  values wahi puraani hain, lekin humein unki parwah nahi (LeetCode bhi unhe ignore
  karta hai, sirf pehle `k` elements check karta hai)

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `int slow = 0;` | Agla unique element kaha rakhna hai, uska index |
| `for (int fast = 1; ...)` | Poore array ko explore karne wala loop |
| `nums[fast] != nums[slow]` | Check — naya unique element mila kya |
| `slow++;` | Unique elements ke liye "confirmed jagah" ek aage badhana |
| `nums[slow] = nums[fast];` | Naye unique element ko sahi jagah copy karna |
| `return slow + 1;` | Total unique elements ki ginti (index se count mein convert) |

## Complexity

- **Time:** O(n) — array ko sirf ek baar traverse karte hain
- **Space:** O(1) — koi naya array nahi banaya, sab kuch in-place hua

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `[1,1,2]` | `k=2, array=[1,2]` | Ek duplicate (1) hataya |
| `[0,0,1,1,1,2,2,3,3,4]` | `k=5, array=[0,1,2,3,4]` | 5 unique numbers hain |
| `[1,2,3]` | `k=3, array=[1,2,3]` | Koi duplicate tha hi nahi, sab waise hi rahe |
