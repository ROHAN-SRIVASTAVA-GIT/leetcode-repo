# 14. Longest Common Prefix

LeetCode Link: https://leetcode.com/problems/longest-common-prefix/

## Problem kya keh raha hai (simple bhasha mein)

Humein words ka ek array diya hai (jaise `["flower", "flow", "flight"]`). Humein wo
**sabse lambi common shuruaat (prefix)** dhundhni hai jo **sabhi words mein same ho**.
Agar koi common prefix na ho, toh empty string `""` return karna hai.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho tumhare paas 3 dosto ke naam hain: `flower`, `flow`, `flight`. Tum unke naam
ek-doosre ke **upar rakh ke** dekhte ho:

```
flower
flow
flight
```

Ab left se right letter-by-letter check karo:
- Position 0: f, f, f → sab same ✅
- Position 1: l, l, l → sab same ✅
- Position 2: o, o, i → **teesra alag hai!** ❌ yahin ruk jao

Toh common prefix hai: `"fl"` (sirf pehle 2 letters).

**Socho aise:** ek **kainchi (scissors)** lo, jiski shuru mein length **poore pehle
word (`flower`) jitni** hai. Ab baaki har naam se compare karo — jahan match na ho,
kainchi se **peeche se kaat do** (ek letter chhota kar do), jab tak match na ho jaaye.
Yeh process har naam ke liye repeat karo.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Jab bhi sawal ho:
- **Multiple elements (strings/arrays)** diye hon
- Humein unme se **common/shared cheez** dhundhni ho (prefix, suffix, ya koi pattern)

...tab **"Horizontal Scanning"** technique try karo:
1. **Pehle element ko candidate answer** maan lo (poora use lo)
2. Har agle element ke saath compare karo
3. Jab tak match na ho, candidate ko **chhota karte jao** (ek-ek letter kaato peeche se)
4. Agar candidate bilkul khaali ho jaaye, matlab koi common prefix hai hi nahi — turant `""` return kar do

Yeh trick kaam aati hai jab bhi "sab mein common kya hai" type ka sawal mile.

## Dry Run — Loop ko haath se chala ke dekhte hain (real example)

Chalo `strs = ["flower", "flow", "flight"]` leke karte hain.

**Shuruaat:** `prefix = "flower"` (poora pehla word)

**Outer loop** (`for i = 1 to 2`) har baaki word ko check karta hai.
**Inner loop** (`while !strs[i].startsWith(prefix)`) tab tak prefix ko chhota karta hai
jab tak match na ho jaaye.

### i = 1 → strs[1] = "flow"

| Check: `"flow".startsWith(prefix)`? | `prefix` (pehle) | Kya hua | `prefix` (baad mein) |
|---|---|---|---|
| "flow".startsWith("flower")? **NA** (flow chhota hai, flower se shuru hi nahi hota) | "flower" | last letter kaato | "flowe" |
| "flow".startsWith("flowe")? **NA** | "flowe" | last letter kaato | "flow" |
| "flow".startsWith("flow")? **HAA** | "flow" | inner loop RUKA | "flow" |

Ab `prefix = "flow"`, outer loop agle word (i=2) pe jaata hai.

### i = 2 → strs[2] = "flight"

| Check: `"flight".startsWith(prefix)`? | `prefix` (pehle) | Kya hua | `prefix` (baad mein) |
|---|---|---|---|
| "flight".startsWith("flow")? **NA** | "flow" | last letter kaato | "flo" |
| "flight".startsWith("flo")? **NA** | "flo" | last letter kaato | "fl" |
| "flight".startsWith("fl")? **HAA** | "fl" | inner loop RUKA | "fl" |

Outer loop khatam ho gaya (sab words check ho gaye). **Final answer: `"fl"`** ✅

### Real output flow (console pe kya print hota hai):

```
input: strs = ["flower", "flow", "flight"]

prefix = "flower"  (starting point = pehla word)

--- outer loop i=1, checking "flow" ---
  "flow".startsWith("flower")? NO  -> prefix chhota: "flowe"
  "flow".startsWith("flowe")?  NO  -> prefix chhota: "flow"
  "flow".startsWith("flow")?   YES -> inner loop stop

--- outer loop i=2, checking "flight" ---
  "flight".startsWith("flow")? NO  -> prefix chhota: "flo"
  "flight".startsWith("flo")?  NO  -> prefix chhota: "fl"
  "flight".startsWith("fl")?   YES -> inner loop stop

outer loop khatam (no more words)

FINAL OUTPUT: "fl"
```

### Notice karo yeh pattern:
- `prefix` sirf **chhota hota jaata hai**, kabhi bada nahi hota — kyunki hum har word
  ke saath check karke usko "trim" karte hain
- Agar koi word bilkul bhi match na kare (jaise `"dog"` ke saath `"racecar"`), toh
  `prefix` ghat-te-ghat-te **empty (`""`) ho jaayega**, aur hum turant return kar denge
  (safety check `if (prefix.isEmpty())`)
- Best case mein (agar pehla word hi sabse chhota common prefix hai) inner loop kabhi
  chalega hi nahi — outer loop bas seedha check karke aage badh jaayega

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `prefix = strs[0]` | Shuruaati candidate answer — poora pehla word |
| `for (int i = 1; ...)` | Outer loop — baaki har word ko check karta hai |
| `strs[i].startsWith(prefix)` | Check: kya current word, prefix se shuru hota hai |
| `while (!strs[i].startsWith(prefix))` | Inner loop — jab tak match na ho, prefix chhota karo |
| `prefix.substring(0, prefix.length() - 1)` | Prefix ka last letter kaat dena |
| `if (prefix.isEmpty()) return "";` | Agar kuch bhi common na bacha, turant khaali return |

## Complexity

- **Time:** O(S) — jahan S = saare characters ka total sum sabhi strings mein (worst case
  mein har character ek baar check hota hai)
- **Space:** O(1) — koi extra data structure nahi, bas ek `prefix` string modify hoti hai

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `["flower", "flow", "flight"]` | `"fl"` | Teeno "fl" tak match, phir alag ho jaate hain |
| `["dog", "racecar", "car"]` | `""` | Pehle letter se hi mismatch (d vs r vs c) |
| `["interspecies", "interstellar", "interstate"]` | `"inters"` | Sab "inters" tak match karte hain |
