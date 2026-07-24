# 17. Letter Combinations of a Phone Number

LeetCode Link: https://leetcode.com/problems/letter-combinations-of-a-phone-number/

## Problem kya keh raha hai (simple bhasha mein)

Purane phone keypad mein har number pe letters likhe hote hain:

| Digit | Letters |
|---|---|
| 2 | abc |
| 3 | def |
| 4 | ghi |
| 5 | jkl |
| 6 | mno |
| 7 | pqrs |
| 8 | tuv |
| 9 | wxyz |

Humein digits ka ek string diya hai (jaise `"23"`). Humein **saari possible letter
combinations** dhundhni hain jo ban sakti hain agar har digit se **ek letter chuno**.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho digit `2` ke paas 3 dost hain: `a, b, c`. Digit `3` ke paas bhi 3 dost hain:
`d, e, f`. Tumhe **ek dost digit-2 se aur ek dost digit-3 se** chunke, unko jodke
naya "jodi ka naam" banana hai.

Saare possible jodiyan: `ad, ae, af, bd, be, bf, cd, ce, cf` — 9 jodiyan (3×3).

**Socho isko ek "decision tree" ki tarah:** pehle digit 2 ke liye 3 raaste hain
(a ya b ya c). Har raaste ke aage, digit 3 ke liye phir 3 raaste hain (d ya e ya f).

```
                (start)
           /       |       \
          a        b        c
        / | \    / | \    / | \
       d  e  f  d  e  f  d  e  f
```

Har **leaf (aakhri point)** tak pahunchne ka matlab hai ek complete combination ban gayi.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho) — NAYI TECHNIQUE: BACKTRACKING

Jab bhi sawal ho: **"saari possible combinations/permutations/subsets banao"**, aur
choices **ek ke baad ek (step-by-step)** leni ho — yeh **Backtracking** pattern hai.

Backtracking ke 3 steps hamesha yaad rakho:
1. **CHOOSE** — ek option chuno (current word mein jodo)
2. **EXPLORE** — us choice ke saath aage badho (agle digit ke liye, khud ko phir call
   karo — isko **Recursion** kehte hain)
3. **UN-CHOOSE (Backtrack)** — jab wo raasta explore ho jaaye, apni choice ko **hata
   do** (undo), taaki agla option try kar sako

### Recursion kya hai? (agar pehli baar sun rahe ho)

Recursion ka matlab hai: **ek method khud ko call kare**, chhote input ke saath, jab
tak ek "base case" (rukne ki condition) na aa jaaye. Jaise ek Russian doll (matryoshka)
— har doll ke andar ek chhoti doll hai, jab tak sabse chhoti doll (jisme aur doll nahi)
na mil jaaye.

Yahan: `backtrack(digits, index, ...)` khud ko `backtrack(digits, index+1, ...)` se
call karta hai — har baar **agle digit** pe move karte hue — jab tak `index` string ki
length ke barabar na ho jaaye (base case).

## Dry Run — Recursion ko haath se chala ke dekhte hain (real example)

Chalo `digits = "23"` leke karte hain. `KEYPAD[2] = "abc"`, `KEYPAD[3] = "def"`.

Call shuru hoti hai: `backtrack("23", index=0, current="", result=[])`

| Call | `index` | `current` (aane se pehle) | Letters try honge | Kya hua |
|---|---|---|---|---|
| Call 1 | 0 | "" | a, b, c (digit 2) | `index != length(2)`, toh loop chalega |

**Loop for index=0 (letters "abc"):**

### i=0 → letter 'a'
`current.append('a')` → `current = "a"`. Ab RECURSION call: `backtrack("23", index=1, "a", result)`

  **Andar wali call (index=1, current="a"):** letters ab "def" (digit 3)
  - `i=0` → 'd' jodo → `current="ad"` → call `backtrack("23", index=2, "ad", ...)`
    - `index(2) == length(2)` → **BASE CASE!** → `result.add("ad")` → result = `["ad"]`
    - wapas aao, `current` se last letter hatao → `current = "a"`
  - `i=1` → 'e' jodo → `current="ae"` → call `backtrack("23", index=2, "ae", ...)`
    - base case → `result.add("ae")` → result = `["ad","ae"]`
    - wapas aao → `current = "a"`
  - `i=2` → 'f' jodo → `current="af"` → call `backtrack("23", index=2, "af", ...)`
    - base case → `result.add("af")` → result = `["ad","ae","af"]`
    - wapas aao → `current = "a"`
  - andar wali call ka loop khatam, wapas jao upar

`current` se 'a' hatao (backtrack) → `current = ""`

### i=1 → letter 'b'
`current = "b"` → same tarah recursion → `result = [..., "bd", "be", "bf"]` → wapas `current = ""`

### i=2 → letter 'c'
`current = "c"` → same tarah recursion → `result = [..., "cd", "ce", "cf"]` → wapas `current = ""`

Outer loop khatam. **Final result: `["ad","ae","af","bd","be","bf","cd","ce","cf"]`** ✅ (9 combinations)

### Real output flow (console pe kya print hota hai, simplified):

```
input: digits = "23"

backtrack(index=0, current="")
  try 'a': current="a"
    backtrack(index=1, current="a")
      try 'd': current="ad"
        backtrack(index=2, current="ad")  -> BASE CASE, add "ad" to result
        undo: current="a"
      try 'e': current="ae"
        backtrack(index=2, current="ae")  -> BASE CASE, add "ae" to result
        undo: current="a"
      try 'f': current="af"
        backtrack(index=2, current="af")  -> BASE CASE, add "af" to result
        undo: current="a"
    undo: current=""
  try 'b': current="b"    (same pattern -> "bd","be","bf")
    undo: current=""
  try 'c': current="c"    (same pattern -> "cd","ce","cf")
    undo: current=""

FINAL OUTPUT: [ad, ae, af, bd, be, bf, cd, ce, cf]
```

### Notice karo yeh pattern:
- `index` hamesha **badhta jaata hai** har recursive call mein (`index + 1`) — yeh
  batata hai ki hum agle digit pe move ho rahe hain
- **Base case** (`index == digits.length()`) hi wo jagah hai jaha ek complete
  combination result mein add hoti hai
- Har recursive call **wapas aane ke baad**, `current.deleteCharAt(...)` se apni
  choice **undo** karta hai — isi se agla letter try karne ka mauka milta hai
  (yehi "backtracking" hai)

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `KEYPAD[]` | Har digit ke corresponding letters ka lookup table |
| `backtrack(...)` | Recursive helper method — khud ko baar-baar call karta hai |
| `index == digits.length()` | Base case — jab poora combination ban chuka ho |
| `current.append(letters.charAt(i))` | CHOOSE — ek letter current word mein jodna |
| `backtrack(digits, index + 1, ...)` | EXPLORE — recursion se agle digit pe jaana |
| `current.deleteCharAt(current.length()-1)` | UN-CHOOSE — apni choice wapas hatana (backtrack) |

## Complexity

- **Time:** O(4^n × n) — jahan n = digits ki length. Kyunki har digit ke max 4
  letters ho sakte hain (jaise 7="pqrs", 9="wxyz"), aur har combination banane mein
  n time lagta hai
- **Space:** O(n) — recursion call stack ki depth (result list ko chhod ke, jo answer
  ke liye zaroori hai)

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `"23"` | `[ad,ae,af,bd,be,bf,cd,ce,cf]` | 3 letters × 3 letters = 9 combinations |
| `""` | `[]` | Koi digit nahi, koi combination nahi |
| `"2"` | `[a,b,c]` | Sirf ek digit, uske teeno letters answer hain |
