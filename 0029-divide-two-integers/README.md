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

## Dry Run — Loop ko haath se chala ke dekhte hain (real example)

Chalo `dividend = 43`, `divisor = 3` leke karte hain (dono positive hain, toh
`isNegative = false`). `dvd = 43`, `dvs = 3`, `result = 0`

### Outer loop iteration 1: `dvd(43) >= dvs(3)`? HAA

`temp = 3`, `multiple = 1`

| Check: `dvd(43) >= temp<<1`? | `temp` (pehle) | Action | `temp` (baad) | `multiple` (baad) |
|---|---|---|---|---|
| 43 >= 6? HAA | 3 | double karo | 6 | 2 |
| 43 >= 12? HAA | 6 | double karo | 12 | 4 |
| 43 >= 24? HAA | 12 | double karo | 24 | 8 |
| 43 >= 48? **NA** | 24 | inner loop RUKA | 24 | 8 |

`dvd -= temp` → `dvd = 43 - 24 = 19`. `result += multiple` → `result = 0 + 8 = 8`

### Outer loop iteration 2: `dvd(19) >= dvs(3)`? HAA

`temp = 3`, `multiple = 1`

| Check: `dvd(19) >= temp<<1`? | `temp` (pehle) | Action | `temp` (baad) | `multiple` (baad) |
|---|---|---|---|---|
| 19 >= 6? HAA | 3 | double karo | 6 | 2 |
| 19 >= 12? HAA | 6 | double karo | 12 | 4 |
| 19 >= 24? **NA** | 12 | inner loop RUKA | 12 | 4 |

`dvd -= temp` → `dvd = 19 - 12 = 7`. `result += multiple` → `result = 8 + 4 = 12`

### Outer loop iteration 3: `dvd(7) >= dvs(3)`? HAA

`temp = 3`, `multiple = 1`

| Check: `dvd(7) >= temp<<1`? | `temp` (pehle) | Action | `temp` (baad) | `multiple` (baad) |
|---|---|---|---|---|
| 7 >= 6? HAA | 3 | double karo | 6 | 2 |
| 7 >= 12? **NA** | 6 | inner loop RUKA | 6 | 2 |

`dvd -= temp` → `dvd = 7 - 6 = 1`. `result += multiple` → `result = 12 + 2 = 14`

### Outer loop iteration 4: `dvd(1) >= dvs(3)`? **NA** → outer loop RUKA

**Final answer: `result = 14`** ✅ (check: 3×14=42, 43-42=1, sahi hai — 43÷3 = 14 remainder 1)

### Real output flow (console pe simplified trace):

```
input: dividend=43, divisor=3
dvd=43, dvs=3, result=0

iter1: temp=3->6->12->24 (24*2=48 > 43, stop), multiple=8
       dvd = 43-24 = 19, result = 0+8 = 8

iter2: temp=3->6->12 (12*2=24 > 19, stop), multiple=4
       dvd = 19-12 = 7, result = 8+4 = 12

iter3: temp=3->6 (6*2=12 > 7, stop), multiple=2
       dvd = 7-6 = 1, result = 12+2 = 14

iter4: dvd(1) >= dvs(3)? NO -> loop stops

FINAL OUTPUT: 14
```

### Notice karo yeh pattern:
- Har outer iteration mein hum **sabse bada possible chunk** (jo divisor ka power-of-2
  multiple hai) ek hi baar mein ghata dete hain — isse humein 43 baar nahi, sirf
  **3 baar** loop chalana pada
- `multiple` hamesha `temp` ke saath **same rate se double** hota hai — dono ek
  saath badhte hain, kyunki `multiple` batata hai "yeh `temp`, divisor ka kitna
  guna hai"

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

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `dividend=10, divisor=3` | `3` | 10÷3 = 3 remainder 1 |
| `dividend=7, divisor=-3` | `-2` | Signs alag hain, answer negative; 7÷3=2, toh -2 |
| `dividend=Integer.MIN_VALUE, divisor=-1` | `2147483647` | Overflow case, int ki max value clamp hui |
