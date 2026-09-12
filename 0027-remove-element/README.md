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
