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
