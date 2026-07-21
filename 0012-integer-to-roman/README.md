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

## Dry Run — Loop ko haath se chala ke dekhte hain (real example ke saath)

Loop samajhne ka sabse aasaan tareeka: **khud ek chhota number leke, table banao, aur
har step pe kya ho raha hai likho.** Chalo `num = 58` leke karte hain.

Yaad rakho apna array:
```
values  = [1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1]
symbols = ["M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"]
```

**Outer loop** (`for i = 0 to 12`) ek-ek karke `values[i]` ko check karta hai.
**Inner loop** (`while num >= values[i]`) tab tak chalta hai jab tak current value fit ho.

| `i` | `values[i]` | Check: `num >= values[i]` ? | Kya hua | `num` (naya) | `result` (ab tak) |
|---|---|---|---|---|---|
| 0 | 1000 | 58 >= 1000? **NA** | inner loop chala hi nahi, i++ | 58 | "" |
| 1 | 900 | 58 >= 900? **NA** | skip, i++ | 58 | "" |
| 2 | 500 | 58 >= 500? **NA** | skip, i++ | 58 | "" |
| 3 | 400 | 58 >= 400? **NA** | skip, i++ | 58 | "" |
| 4 | 100 | 58 >= 100? **NA** | skip, i++ | 58 | "" |
| 5 | 90 | 58 >= 90? **NA** | skip, i++ | 58 | "" |
| 6 | 50 | 58 >= 50? **HAA** | 58-50=8, "L" jodo | 8 | "L" |
| 6 | 50 | 8 >= 50? **NA** | inner loop ruk gaya, i++ | 8 | "L" |
| 7 | 40 | 8 >= 40? **NA** | skip, i++ | 8 | "L" |
| 8 | 10 | 8 >= 10? **NA** | skip, i++ | 8 | "L" |
| 9 | 9 | 8 >= 9? **NA** | skip, i++ | 8 | "L" |
| 10 | 5 | 8 >= 5? **HAA** | 8-5=3, "V" jodo | 3 | "LV" |
| 10 | 5 | 3 >= 5? **NA** | inner loop ruk gaya, i++ | 3 | "LV" |
| 11 | 4 | 3 >= 4? **NA** | skip, i++ | 3 | "LV" |
| 12 | 1 | 3 >= 1? **HAA** | 3-1=2, "I" jodo | 2 | "LVI" |
| 12 | 1 | 2 >= 1? **HAA** | 2-1=1, "I" jodo | 1 | "LVII" |
| 12 | 1 | 1 >= 1? **HAA** | 1-1=0, "I" jodo | 0 | "LVIII" |
| 12 | 1 | 0 >= 1? **NA** | inner loop ruk gaya | 0 | "LVIII" |

Loop khatam (i array ke end tak pahunch gaya). **Final answer: `LVIII`** ✅ (matches expected!)

### Ab notice karo yeh pattern:
- **Outer loop (`for`)** = ek **"pointer"** hai jo values list mein **left se right** ghoomta
  hai (1000 se 1 tak) — yeh sirf **check** karta hai "abhi ki value try karni hai kya?"
- **Inner loop (`while`)** = jab bhi ek value **fit ho jaati hai**, wahi value **baar-baar**
  use hoti hai jab tak num usse chhota na ho jaaye. Isiliye "I" 3 baar repeat hua upar
  (3 → 2 → 1 → 0), kyunki 1 chhoti value hai aur 3 baar fit hui.
- Jaise hi `num` current value se chhota ho jaata hai, inner loop **ruk** jaata hai aur
  outer loop **agli chhoti value** try karta hai.
- `num == 0` hote hi, saari aage ki values (jo bachi hain) automatically skip ho jaayengi
  kyunki `0 >= koi bhi positive value` kabhi true nahi hoga.

### Real output flow (console pe kya print hota hai):

```
input:  num = 58

i=0  values[i]=1000  -> fit nahi
i=1  values[i]=900   -> fit nahi
i=2  values[i]=500   -> fit nahi
i=3  values[i]=400   -> fit nahi
i=4  values[i]=100   -> fit nahi
i=5  values[i]=90    -> fit nahi
i=6  values[i]=50    -> FIT! num: 58->8   result: "L"
i=7  values[i]=40    -> fit nahi
i=8  values[i]=10    -> fit nahi
i=9  values[i]=9     -> fit nahi
i=10 values[i]=5     -> FIT! num: 8->3    result: "LV"
i=11 values[i]=4     -> fit nahi
i=12 values[i]=1     -> FIT! num: 3->2    result: "LVI"
i=12 values[i]=1     -> FIT! num: 2->1    result: "LVII"
i=12 values[i]=1     -> FIT! num: 1->0    result: "LVIII"
i=12 values[i]=1     -> fit nahi (0 >= 1 false), loop khatam

FINAL OUTPUT: "LVIII"
```

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
