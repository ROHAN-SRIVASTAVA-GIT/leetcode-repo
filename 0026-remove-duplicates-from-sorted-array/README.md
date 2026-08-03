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
