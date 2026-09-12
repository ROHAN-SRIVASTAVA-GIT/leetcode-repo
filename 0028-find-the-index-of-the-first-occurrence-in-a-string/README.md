# 28. Find the Index of the First Occurrence in a String

LeetCode Link: https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/

## Problem kya keh raha hai (simple bhasha mein)

Humein do strings di gayi hain: `haystack` (bada string) aur `needle` (chhota
string, jise dhundhna hai). Humein batana hai `needle`, `haystack` mein **kaha se
shuru** hota hai (pehla match ka index), ya `-1` agar kahi nahi milta.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho tum ek **badi kitab (haystack)** mein ek **chhota word (needle)** dhundh rahe
ho — jaise Ctrl+F karke koi word search karte ho. Tumhe batana hai wo word **kaha se
shuru** hota hai.

**Socho aise:** ek **"window" (khidki)** lo jiski size `needle` jitni hai. Is window
ko `haystack` ke shuru se lekar end tak **ek-ek step slide karo**. Har position pe
check karo: "kya is window ke andar ka text, bilkul `needle` jaisa hai?"

Jaise `haystack="hello"`, `needle="ll"`: window ki size 2 hai.
- Position 0: "he" — match nahi
- Position 1: "el" — match nahi
- Position 2: "ll" — **MATCH!** → answer = 2

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho) — SLIDING WINDOW

Jab bhi sawal ho: **"ek bade string/array mein ek chhota pattern dhundo"** — toh
**Sliding Window** technique try karo.

**Steps:**
1. Ek **fixed-size window** (jiski size `needle` jitni hai) ko `haystack` pe **shuru
   se end tak slide karo**
2. Har starting position pe, window ke andar ka text **character-by-character
   compare** karo `needle` se
3. Agar **poora match** ho gaya, us starting index ko **turant return** kar do
4. Agar kahi bhi **mismatch** mila, is position ko chhodo, agli position try karo
5. Agar poora haystack check karne ke baad bhi match nahi mila, `-1` return karo

**Loop kaha tak chalana hai, yeh dhyan se socho:** window `i` se `i + needle.length()
- 1` tak jaati hai. Agar `i` bahut aage se shuru hui, toh poori window haystack ke
andar fit hi nahi hogi. Isliye outer loop `i <= haystack.length() - needle.length()`
tak hi chalta hai — isse aage try karna hi bekar hai.

## Dry Run — Loop ko haath se chala ke dekhte hain (real example)

Chalo `haystack = "hello"`, `needle = "ll"` leke karte hain (`n=5`, `m=2`, toh outer
loop `i=0` se `i=3` tak chalega, kyunki `n-m = 3`).

### i=0 → window "he" (index 0,1)

| `j` | `haystack.charAt(0+j)` | `needle.charAt(j)` | Match? | Action |
|---|---|---|---|---|
| 0 | 'h' | 'l' | NA | inner loop RUKA (j=0 pe hi mismatch) |

Check: `j(0) == m(2)`? **NA** → is position pe match nahi mila, i++ 

### i=1 → window "el" (index 1,2)

| `j` | `haystack.charAt(1+j)` | `needle.charAt(j)` | Match? | Action |
|---|---|---|---|---|
| 0 | 'e' | 'l' | NA | inner loop RUKA |

Check: `j(0) == m(2)`? NA → i++

### i=2 → window "ll" (index 2,3)

| `j` | `haystack.charAt(2+j)` | `needle.charAt(j)` | Match? | Action |
|---|---|---|---|---|
| 0 | 'l' | 'l' | HAA | j++ |
| 1 | 'l' | 'l' | HAA | j++ |
| 2 | — | — | `j(2) < m(2)`? NA | inner loop RUKA (poora needle check ho gaya) |

Check: `j(2) == m(2)`? **HAA!** → **MATCH MIL GAYA** → `return i` = **2**

**Final answer: `2`** ✅ (loop yahi pe turant `return` kar deta hai, aage try nahi karta)

### Real output flow (console pe kya print hota hai):

```
input: haystack="hello", needle="ll"  (n=5, m=2, i goes 0 to 3)

i=0: window="he"
  j=0: 'h' vs 'l' -> mismatch, stop inner loop
  j(0) != m(2) -> no match, try next i

i=1: window="el"
  j=0: 'e' vs 'l' -> mismatch, stop inner loop
  j(0) != m(2) -> no match, try next i

i=2: window="ll"
  j=0: 'l' vs 'l' -> match! j=1
  j=1: 'l' vs 'l' -> match! j=2
  j(2) == m(2) -> FULL MATCH! return i=2

FINAL OUTPUT: 2
```

### Notice karo yeh pattern:
- Har naye `i` pe, `j` **wapas 0 se shuru** hota hai — yeh ek **fresh comparison**
  hai, purane `i` ke comparisons se koi lena-dena nahi
- Jaise hi ek **mismatch** milta hai, inner `while` loop **turant ruk jaata hai**
  (poori window check karne ki zaroorat nahi) — isse thoda time bachta hai
- `j == m` ka check hi batata hai ki **poora needle match hua ya beech mein hi
  mismatch ho gaya**

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `for (int i = 0; i <= n - m; i++)` | Window ki har valid starting position try karna |
| `int j = 0; while (...)` | Window ke andar character-by-character compare karna |
| `haystack.charAt(i + j) == needle.charAt(j)` | Ek-ek character match check karna |
| `if (j == m) return i;` | Poora needle match hua — answer mil gaya |
| `return -1;` | Kahi bhi match nahi mila |

## Complexity

- **Time:** O(n × m) — worst case mein, har starting position (n) ke liye poora
  needle (m) compare karna pad sakta hai
- **Space:** O(1) — koi extra data structure nahi use ki

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `haystack="sadbutsad", needle="sad"` | `0` | "sad" index 0 pe hi match ho jaata hai |
| `haystack="leetcode", needle="leeto"` | `-1` | "leeto" kahi bhi match nahi karta |
| `haystack="hello", needle="ll"` | `2` | "ll" index 2 se shuru hota hai |
