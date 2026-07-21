# 12. Integer to Roman

LeetCode Link: https://leetcode.com/problems/integer-to-roman/

## Problem kya keh raha hai (simple bhasha mein)

Humein ek integer number diya hai (1 se 3999 ke beech). Humein use **Roman numeral**
(text/String) mein convert karke return karna hai.

Roman symbols aur unki values:

| Symbol | Value |
|---|---|
| I | 1 |
| V | 5 |
| X | 10 |
| L | 50 |
| C | 100 |
| D | 500 |
| M | 1000 |

Kuch special "subtraction" cases bhi hote hain (jab chhota symbol bade se pehle aata hai,
toh subtract hota hai):

| Symbol | Value |
|---|---|
| IV | 4 |
| IX | 9 |
| XL | 40 |
| XC | 90 |
| CD | 400 |
| CM | 900 |

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho tumhare paas alag-alag "notes" hain: ₹1000, ₹900, ₹500, ₹400, ₹100, ₹90, ₹50, ₹40,
₹10, ₹9, ₹5, ₹4, ₹1 (haan, aisi hi notes hain is duniya mein!).

Tumhe ek amount (jaise ₹1994) **jitni possible bada note use karke** banani hai:
- Sabse pehle sabse badi note (₹1000) uthao — 1994 mein se 1000 nikaal do → M likh do, bacha 994
- Fir agli badi note try karo jo fit ho (₹900) → CM likh do, bacha 94
- Fir ₹90 fit hoga → XC likh do, bacha 4
- Fir ₹4 fit hoga → IV likh do, bacha 0
- Done! Answer: **MCMXCIV**

Bas yahi cheez code mein ho rahi hai — hum sabse badi value se shuru karke, jab tak wo
fit hoti hai use baar-baar use karte hain, phir chhoti value pe move karte hain.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Jab bhi sawal mein:
- Ek **fixed set of denominations/values** di ho (coins, symbols, units)
- Humein ek number ko **un values se represent/build** karna ho
- Aur **"sabse kam symbols/coins mein banao"** jaisi requirement ho (ya bas correctly banao)

...tab **"Greedy Algorithm"** pattern try karo: values ko **largest se smallest** order
mein rakho, aur har value ko **jitni baar ho sake use karo** before moving to next
smaller value.

**Trick jo yaad rakhna hai:** subtraction cases (IV, IX, XL, XC, CD, CM) ko **alag se
special-case handle mat karo** — unhe bhi normal value-symbol pair ki tarah list mein
already daal do (jaise 900→"CM"). Isse code simple ho jaata hai, if-else ka jhanjhat
nahi rehta.

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `values[]` | Saari values, sabse badi se sabse chhoti (1000 → 1) |
| `symbols[]` | Har value ka corresponding Roman symbol |
| `StringBuilder result` | Answer text ko build karne ke liye (String se fast append) |
| `for (int i = 0; ...)` | Outer loop — har value-symbol pair ko check karta hai |
| `while (num >= values[i])` | Inner loop — jab tak current value fit hoti hai, use baar-baar use karo |
| `num -= values[i]` | Number mein se value ghata dena |
| `result.append(symbols[i])` | Answer mein symbol jod dena |
| `result.toString()` | StringBuilder ko final String answer mein badalna |

## Complexity

- **Time:** O(1) — kyunki values[] aur symbols[] ki length fix hai (13), aur num
  max 3999 tak hi hota hai, toh loop hamesha bounded rehta hai
- **Space:** O(1) — sirf fixed size arrays use ho rahe hain (result string ko chhod ke,
  jo output ke liye zaroori hai)

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `3749` | `MMMDCCXLIX` | 3000=MMM, 700=DCC, 40=XL, 9=IX |
| `58` | `LVIII` | 50=L, 8=VIII |
| `1994` | `MCMXCIV` | 1000=M, 900=CM, 90=XC, 4=IV |
