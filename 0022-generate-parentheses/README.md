# 22. Generate Parentheses

LeetCode Link: https://leetcode.com/problems/generate-parentheses/

## Problem kya keh raha hai (simple bhasha mein)

Humein ek number `n` diya hai. Humein `n` pairs of parentheses `()` ki **saari
possible valid combinations** banani hain. "Valid" ka matlab hai — jaisa
[Problem 20 (Valid Parentheses)](../0020-valid-parentheses) mein samjha tha, sab
brackets sahi tareeke se open-close hone chahiye.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho tumhare paas `n=3` opening brackets `(` aur `n=3` closing brackets `)` hain,
aur tumhe unhe ek-ek karke ek string mein jodna hai — lekin **result hamesha
"valid" hona chahiye** (jaise `()()()`  valid hai, lekin `)((` invalid hai).

Har step pe tumhare paas 2 choices hain: `(` daalo ya `)` daalo. Par kuch **rules**
follow karne padenge:
1. Zyada se zyada `n` hi `(` daal sakte ho (na ismse zyada)
2. `)` sirf tabhi daal sakte ho jab **kisi na kisi `(` ka jawab dena baaki ho**
   (matlab abhi tak jitne `(` daale hain, unse **kam** `)` daale hon)

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho) — BACKTRACKING (with PRUNING)

Yeh bilkul [Problem 17: Letter Combinations](../0017-letter-combinations-of-a-phone-number)
jaisa **Backtracking** hai (CHOOSE → EXPLORE → UN-CHOOSE), bas ek naya concept
add hota hai: **Pruning**.

**Pruning ka matlab:** jab tumhe pata ho ki koi choice **hamesha galat result** degi,
toh use try hi mat karo — poora explore karne ki zaroorat nahi. Isse code **fast**
ho jaata hai aur sirf **valid** combinations banti hain (invalid banti hi nahi, unko
baad mein filter karne ki zaroorat nahi padti).

**Yahan 2 pruning rules hain:**
- `openCount < n` — tabhi `(` daalo (warna zyada `(` ho jaayenge)
- `closeCount < openCount` — tabhi `)` daalo (warna koi `)` bina match ke aa jayega)

**Base case (recursion kab rukega):** jab current string ki length `2×n` ho jaaye
(saare `n` opening + `n` closing use ho gaye).

## Dry Run — Recursion ko haath se chala ke dekhte hain (real example)

Chalo `n = 2` leke karte hain (saral rakhne ke liye, n=3 wale se chhota).

Call shuru: `backtrack(result=[], current="", open=0, close=0, n=2)`

`current.length() == 2*n(=4)`? Abhi 0 hai, toh NA — aage badhenge.

### Try Choice 1: '(' daalna → `open(0) < n(2)`? HAA, allowed

`current = "("`, call `backtrack(current="(", open=1, close=0)`

  `length=1 != 4`. Checks:
  - `open(1) < n(2)`? HAA → try '(' → `current="(("`, call `backtrack("((", open=2, close=0)`

    `length=2 != 4`. Checks:
    - `open(2) < n(2)`? **NA** → '(' NAHI try hoga (pruned!)
    - `close(0) < open(2)`? HAA → try ')' → `current="(()"`, call `backtrack("(()", open=2, close=1)`

      `length=3 != 4`. Checks:
      - `open(2) < n(2)`? NA → skip
      - `close(1) < open(2)`? HAA → try ')' → `current="(())"`, call `backtrack("(())", open=2, close=2)`

        `length(4) == 2*n(4)`! **BASE CASE** → `result.add("(())")` → result = `["(())"]`
        wapas aao, undo → `current = "(()"`

      close ka loop khatam yaha, wapas jao → `current = "(("`

    close ka loop khatam yaha, wapas jao → `current = "("`

  '(' wapas try hone ke baad, ab ')' try karo (same level, `open=1, close=0` pe):
  - `close(0) < open(1)`? HAA → try ')' → `current="()"`, call `backtrack("()", open=1, close=1)`

    `length=2 != 4`. Checks:
    - `open(1) < n(2)`? HAA → try '(' → `current="()("`, call `backtrack("()(", open=2, close=1)`

      `length=3 != 4`. Checks:
      - `open(2) < n(2)`? NA → skip
      - `close(1) < open(2)`? HAA → try ')' → `current="()()"`, call `backtrack("()()", open=2, close=2)`

        `length(4) == 4`! **BASE CASE** → `result.add("()()")` → result = `["(())", "()()"]`
        wapas aao → `current = "()("`  → undo → `current = "()"`

    - `close(1) < open(1)`? **NA** → ')' nahi try hoga (pruned!)

    wapas aao → `current = "("` → undo → `current = ""`

  wapas aao top level pe → `current = ""`

Sab options khatam. **Final result: `["(())", "()()"]`** ✅ (yeh dono hi n=2 ke saare valid combinations hain)

### Real output flow (console pe simplified trace):

```
input: n=2

backtrack("", open=0, close=0)
  try '(': "(" 
    backtrack("(", open=1, close=0)
      try '(': "((" 
        backtrack("((", open=2, close=0)
          open==n, skip '('
          try ')': "(()"
            backtrack("(()", open=2, close=1)
              try ')': "(())"
                backtrack("(())", open=2, close=2) -> length==4, ADD "(())"
              undo -> "(()"
            undo -> "(("
          undo -> "("
      try ')': "()"
        backtrack("()", open=1, close=1)
          try '(': "()("
            backtrack("()(", open=2, close=1)
              try ')': "()()"
                backtrack("()()", open=2, close=2) -> length==4, ADD "()()"
              undo -> "()("
            undo -> "()"
          close==open, skip ')'
        undo -> "("
    undo -> ""

FINAL OUTPUT: ["(())", "()()"]
```

### Notice karo yeh pattern:
- Har recursive call pe **do choices try hoti hain** (agar allowed ho): `(` aur `)`
- **Pruning** ki wajah se hum kabhi bhi ek invalid path explore nahi karte — isse
  humein baad mein "valid hai kya check karo" wala kaam bhi nahi karna padta
- Base case sirf tab lagta hai jab **poori length `2n`** ban chuki ho — us point pe
  string **hamesha valid** hoti hai (pruning ki wajah se)

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `backtrack(...)` | Recursive helper method |
| `current.length() == 2 * n` | Base case — poora combination ban chuka |
| `if (openCount < n)` | Pruning rule 1 — zyada '(' mat daalo |
| `if (closeCount < openCount)` | Pruning rule 2 — bina match ke ')' mat daalo |
| `current.append('(')` / `')'` | CHOOSE — bracket jodna |
| `backtrack(result, current, ...)` | EXPLORE — recursion se aage badhna |
| `current.deleteCharAt(current.length()-1)` | UN-CHOOSE — backtrack karna |

## Complexity

- **Time:** O(4^n / sqrt(n)) — yeh "Catalan Number" formula se aata hai (valid
  combinations ki total ginti). Beginner ke liye simplified samjho: bahut kam
  hai brute-force O(2^(2n)) se, kyunki pruning invalid raaste explore hi nahi karti
- **Space:** O(n) — recursion call stack ki depth (result list ko chhod ke)

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `n=3` | `[((())), (()()), (())(), ()(()), ()()()]` | 5 valid combinations (Catalan number C(3)=5) |
| `n=1` | `[()]` | Sirf ek hi valid combination possible hai |
