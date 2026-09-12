# 29. Divide Two Integers

LeetCode Link: https://leetcode.com/problems/divide-two-integers/

## Problem kya keh raha hai (simple bhasha mein)

Humein `dividend` (jise divide karna hai) aur `divisor` (jisse divide karna hai) diye
gaye hain. Humein `dividend / divisor` ka answer nikaalna hai — **bina `*`, `/`, ya
`%` operator use kiye** (yeh problem ka rule hai). Answer ko `int` range ke andar
rehna chahiye — agar overflow ho, toh clamp kar dena hai.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho tumse pucha jaaye: "**43 ko 3 se divide karo, bina "/" use kiye.**" Sabse
simple tareeka: baar-baar `3` ghatate jao jab tak number `3` se chhota na ho jaaye,
count karo kitni baar ghataya. Lekin agar number bada ho (jaise crore), yeh **bahut
slow** hoga.

**Better tareeka — "Doubling" trick:** `3` ko double-double karte jao (3→6→12→24→48)
jab tak `43` se bada na ho jaaye. `48` bada hai, toh `24` pe ruko (kyunki 24 = 3×8).
`43` mein se `24` ghata do (bacha `19`), count mein `8` jodo. Ab yeh **poori process
`19` pe repeat** karo.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho) — DOUBLING VIA BIT SHIFT

Jab bhi sawal ho: **"bina multiply/divide operator ke, division/multiplication
karna ho"**, ya **"repeated addition/subtraction ko fast banana ho"** — toh
**Doubling via Bit Shift** technique use karo.

`x << 1` ka matlab hai `x` ko **2 se multiply karna** — bit shift operator, jo `*`
use kiye bina hota hai (yeh seedha binary representation ko ek bit "left" khisका
deta hai, jo multiply-by-2 ke barabar hai).

**Algorithm:**
1. Divisor ko **double karte jao** jab tak wo dividend se bada na ho jaaye
2. Sabse bada "fit" hone wala chunk (jo divisor ka koi power-of-2 multiple hai)
   dividend mein se **ghata do**
3. Us multiple ko **result mein jod do**
4. Bache hue dividend pe **poori process repeat** karo, jab tak dividend, divisor se
   chhota na ho jaaye

**Special cases jo handle karne padte hain:**
- **Sign (positive/negative):** dividend aur divisor ke signs alag hon toh answer
  negative hoga
- **Overflow:** `Integer.MIN_VALUE / -1` ka answer `int` mein fit nahi hota — isko
  hum `long` use karke aur ek special check lagake handle karte hain

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `dividend == Integer.MIN_VALUE && divisor == -1` | Special overflow case check |
| `isNegative = (dividend < 0) != (divisor < 0)` | Answer ka sign decide karna |
| `Math.abs((long) dividend)` | Absolute value, long mein (overflow se bachne ke liye) |
| `while (dvd >= dvs)` | Outer loop — jab tak divide karne layak bacha hai |
| `temp <<= 1; multiple <<= 1;` | Doubling — chunk aur uska multiple, dono double karna |
| `dvd -= temp;` | Sabse bada fit hone wala chunk ghatana |
| `result += multiple;` | Us chunk ka multiple result mein jodna |

## Complexity

- **Time:** O(log²n) — kyunki doubling har baar exponentially bada chunk ghatata
  hai, poore loop mein bahut kam iterations lagte hain (linear subtraction se
  bahut fast)
- **Space:** O(1) — sirf kuch variables use ho rahe hain
