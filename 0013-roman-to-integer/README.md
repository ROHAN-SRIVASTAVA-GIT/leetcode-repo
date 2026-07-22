# 13. Roman to Integer

LeetCode Link: https://leetcode.com/problems/roman-to-integer/

## Problem kya keh raha hai (simple bhasha mein)

Humein ek **Roman numeral string** di gayi hai (jaise `"MCMXCIV"`). Humein use wapas
**integer number** mein convert karna hai (1994). Yeh **[Problem 12: Integer to Roman](../0012-integer-to-roman)**
ka ulta version hai.

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

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho tum Roman symbols ko ek line mein **left se right padh** rahe ho, jaise kisi
kahani ko padhna. Har symbol ki apni value hai, aur tumhe sabko jod ke ek **total**
banana hai.

Lekin ek chhota sa **twist** hai: kabhi-kabhi ek **chhota symbol**, ek **bade symbol se
pehle** aata hai (jaise `IV` mein `I` pehle aata hai, `V` baad mein). Jab aisa ho, iska
matlab hai wo dono symbols mil ke ek "subtraction" bana rahe hain — jaise `IV` = 5-1 = 4,
`IX` = 10-1 = 9.

Toh rule simple hai: **har symbol ko uske turant agle (next) symbol se compare karo**:
- Agar current chhota hai next se → current ko **ghatao** (subtract)
- Warna current ko **jodo** (add)

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Jab bhi sawal mein:
- Ek **string/array ko left-to-right ek baar scan** karna ho
- Har position pe decision, **current aur agle (ya pichhle) element ke comparison** pe
  depend karta ho

...tab yeh **"Single Pass with Lookahead"** pattern hai (ek hi baar traverse karo,
lekin har step pe "aage jhaank ke" dekho ki decision kya lena hai).

**Trick jo yaad rakhna hai:** subtraction sirf tab hota hai jab **current value < next
value**. Isse pehchan lo ki add karna hai ya subtract.

## Dry Run — Loop ko haath se chala ke dekhte hain (real example)

Chalo `s = "LVIII"` leke karte hain (yeh Problem 12 wale test case ka hi ulta hai — udhar
58 se LVIII bana tha, ab LVIII se wapas 58 banayenge).

Pehle values yaad karo: `L=50, V=5, I=1, I=1, I=1`

String ke characters (index se): `s[0]='L', s[1]='V', s[2]='I', s[3]='I', s[4]='I'`

| `i` | `s[i]` | `currentValue` | Next char `s[i+1]`? | Condition: `currentValue < nextValue`? | Action | `total` (naya) |
|---|---|---|---|---|---|---|
| 0 | L | 50 | V (5) | 50 < 5? **NA** | ADD: total += 50 | 50 |
| 1 | V | 5 | I (1) | 5 < 1? **NA** | ADD: total += 5 | 55 |
| 2 | I | 1 | I (1) | 1 < 1? **NA** | ADD: total += 1 | 56 |
| 3 | I | 1 | I (1) | 1 < 1? **NA** | ADD: total += 1 | 57 |
| 4 | I | 1 | (koi nahi, last char) | i+1 < length? **NA** | ADD: total += 1 | 58 |

Loop khatam. **Final answer: `58`** ✅

### Ab ek subtraction wala example dekhte hain: `s = "IX"`

| `i` | `s[i]` | `currentValue` | Next char `s[i+1]`? | Condition: `currentValue < nextValue`? | Action | `total` (naya) |
|---|---|---|---|---|---|---|
| 0 | I | 1 | X (10) | 1 < 10? **HAA** | SUBTRACT: total -= 1 | -1 |
| 1 | X | 10 | (koi nahi, last char) | i+1 < length? **NA** | ADD: total += 10 | 9 |

Loop khatam. **Final answer: `9`** ✅ (dekha, `I` pehle GHATA, phir `X` JODA — 10-1=9 hi mila!)

### Real output flow (console pe kya print hota hai) — `s = "MCMXCIV"`:

```
input: s = "MCMXCIV"   (values: M=1000, C=100, M=1000, X=10, C=100, I=1, V=5)

i=0  s[i]='M' (1000)  next='C' (100)   1000<100? NA   -> ADD    total: 0->1000
i=1  s[i]='C' (100)   next='M' (1000)  100<1000? HAA  -> SUBTRACT total: 1000->900
i=2  s[i]='M' (1000)  next='X' (10)    1000<10? NA    -> ADD    total: 900->1900
i=3  s[i]='X' (10)    next='C' (100)   10<100? HAA    -> SUBTRACT total: 1900->1890
i=4  s[i]='C' (100)   next='I' (1)     100<1? NA      -> ADD    total: 1890->1990
i=5  s[i]='I' (1)     next='V' (5)     1<5? HAA       -> SUBTRACT total: 1990->1989
i=6  s[i]='V' (5)     (last char)      -> ADD          total: 1989->1994

FINAL OUTPUT: 1994
```

### Notice karo yeh pattern:
- Loop sirf **ek baar** (single pass) string ko left se right traverse karta hai — koi
  nested loop nahi hai is baar (Integer to Roman mein tha, isme nahi)
- Har step pe sirf **ek hi decision** hota hai: add ya subtract — yeh depend karta hai
  current aur **agle** character ke comparison pe
- Jab "MC" jaisa pair aata hai (M=1000, next C=100), 1000 chhota nahi hai 100 se, toh
  simple add hota hai — subtraction sirf tab jab **current SACHMUCH chhota ho next se**

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `Map<Character, Integer> values` | Symbol → value ka lookup table (HashMap) |
| `values.get(s.charAt(i))` | Current character ki value nikalna map se |
| `for (int i = 0; ...)` | Loop — string ko left se right, ek baar traverse karta hai |
| `i + 1 < s.length()` | Check karna ki current LAST character toh nahi hai (warna next check karne pe crash ho jaayega) |
| `currentValue < values.get(s.charAt(i + 1))` | Subtraction case check — current chhota hai kya next se |
| `total -= currentValue` | Subtraction case mein total mein se ghatana |
| `total += currentValue` | Normal case mein total mein jodna |

## Complexity

- **Time:** O(n) — string ko sirf ek baar traverse karte hain (n = string ki length)
- **Space:** O(1) — HashMap ki size hamesha fix (7 symbols) hai, input size pe depend
  nahi karti

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `"III"` | `3` | Sab simple add: 1+1+1 |
| `"LVIII"` | `58` | 50+5+1+1+1 (koi subtraction nahi) |
| `"MCMXCIV"` | `1994` | M(+1000) CM(-100+1000=900) XC(-10+100=90) IV(-1+5=4) → 1000+900+90+4 |
