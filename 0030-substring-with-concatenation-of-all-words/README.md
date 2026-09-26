# 30. Substring with Concatenation of All Words

LeetCode Link: https://leetcode.com/problems/substring-with-concatenation-of-all-words/

## Problem kya keh raha hai (simple bhasha mein)

Humein ek bada string `s`, aur ek list `words` di gayi hai jisme har word **ek jaisi
length** ka hai. Humein `s` mein **saari starting indices** dhundhni hain jaha se
`words` ke saare words ko **kisi bhi order mein jodkar** ek substring `s` mein match
ho jaaye — har word **exactly ek baar** use hona chahiye.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho tumhare paas kuch **chhote-chhote LEGO blocks** hain, jaise `"foo"` aur `"bar"`.
Tumhe ek badi string mein aisi jagah dhundhni hai jaha in dono blocks ko (kisi bhi
order mein — "foobar" ya "barfoo") **bina gap ke jod ke** rakha gaya ho.

`s = "barfoothefoobarman"`, `words = ["foo","bar"]`:
- Index `0` se: `"barfoo"` = "bar"+"foo" → **match!**
- Index `9` se: `"foobar"` = "foo"+"bar" → **match!**

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Yeh do purani techniques ka **combo** hai:

1. **Sliding Window** ([Problem 28](../0028-find-the-index-of-the-first-occurrence-in-a-string)
   se): ek window ko string pe slide karna
2. **Frequency Map / Count Matching** ([Problem 20: Valid Parentheses](../0020-valid-parentheses)
   mein HashMap use kiya tha, yahan bhi): "expected count" vs "actual count" compare
   karna — bilkul **anagram check** jaisa

**Steps:**
1. `words` ke saare words ki **expected frequency** ek HashMap mein store karo
   (jaise `{"foo":1, "bar":1}`)
2. Window ko `wordLen`-`wordLen` ke **chunks mein** slide karo (character-by-character
   nahi, kyunki words hamesha `wordLen` ke multiple pe hi start ho sakte hain)
3. Har chunk (word) ko check karo:
   - Agar `words` list mein hai hi nahi → **window "toot" gayi**, reset karo
   - Agar hai, lekin **zyada baar** aa gaya (jitni zaroorat thi usse zyada) → window
     ko **left se chhota karo** jab tak extra copies na hat jaayein
   - Agar **poore `numWords` words** window mein aa gaye → yeh ek **valid answer** hai!

**Ek zaroori trick — "offset":** words hamesha `wordLen` ke multiples pe start ho
sakte hain, par humein pata nahi kaha se poori "alignment" shuru hogi. Isliye hum
**har possible starting offset** (`0` se `wordLen-1` tak) try karte hain, taaki koi
bhi valid window miss na ho.

## Dry Run — Sliding Window ko haath se chala ke dekhte hain (real example)

Chalo `s = "barfoothefoobarman"`, `words = ["foo","bar"]` leke karte hain.

`wordLen = 3`, `numWords = 2`, `wordCount = {foo:1, bar:1}`

Simplicity ke liye sirf `offset = 0` dekhte hain (yaha isi offset se dono answers mil
jaate hain): `left = 0`, `count = 0`, `windowCount = {}`

`s` ke 3-3 character chunks (index se): `bar(0) foo(3) the(6) foo(9) bar(12) man(15)`

| `right` | `word` | `wordCount` mein hai? | Action | `count` (baad) | `left` (baad) |
|---|---|---|---|---|---|
| 0 | "bar" | HAA | windowCount={bar:1}, count=1 | 1 | 0 |
| 3 | "foo" | HAA | windowCount={bar:1,foo:1}, count=2 → **count==numWords! result.add(0)**, phir left wala "bar" hatao: windowCount={bar:0,foo:1}, count=1 | 1 | 3 |
| 6 | "the" | **NA** | Invalid word! RESET: windowCount={}, count=0 | 0 | 9 |
| 9 | "foo" | HAA | windowCount={foo:1}, count=1 | 1 | 9 |
| 12 | "bar" | HAA | windowCount={foo:1,bar:1}, count=2 → **count==numWords! result.add(9)**, phir left wala "foo" hatao: windowCount={foo:0,bar:1}, count=1 | 1 | 12 |
| 15 | "man" | **NA** | Invalid word! RESET: windowCount={}, count=0 | 0 | 18 |

Loop khatam (`right+3 <= 18` — 18 ke baad koi valid right nahi bacha). Offset 0 se
mile: **`[0, 9]`**. Offsets 1 aur 2 pe koi naya match nahi milta (khud try kar sakte
ho — is example mein words hamesha 3 ke multiple pe hi align hote hain).

**Final answer: `[0, 9]`** ✅

### Real output flow (console pe simplified trace, offset=0):

```
input: s="barfoothefoobarman", words=["foo","bar"]
wordCount={foo:1, bar:1}, wordLen=3, numWords=2

offset=0: left=0, count=0

right=0: word="bar" -> valid, windowCount={bar:1}, count=1
right=3: word="foo" -> valid, windowCount={bar:1,foo:1}, count=2
         count==numWords -> ADD 0! shrink: remove "bar", count=1, left=3
right=6: word="the" -> INVALID -> reset everything, left=9
right=9: word="foo" -> valid, windowCount={foo:1}, count=1
right=12: word="bar" -> valid, windowCount={foo:1,bar:1}, count=2
          count==numWords -> ADD 9! shrink: remove "foo", count=1, left=12
right=15: word="man" -> INVALID -> reset, left=18

FINAL OUTPUT: [0, 9]
```

### Notice karo yeh pattern:
- Jab bhi **poora match** milta hai (`count == numWords`), hum turant answer add
  karke, window ko **ek word aage khiska dete hain** (left wala hatakar) — taaki
  agla overlapping match bhi dhund sakein
- Jab koi **invalid word** milta hai (jo `words` list mein hai hi nahi), poori
  window **reset** ho jaati hai — kyunki us word ke through koi bhi valid answer
  nahi ban sakta
- Multiple **offsets** try karna zaroori hai jab words ka alignment guaranteed na
  ho — is example mein sab kuch `offset=0` pe hi align ho gaya, lekin dusre inputs
  mein `offset=1` ya `2` pe hi asli answer mil sakta hai

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `wordCount` | `words` list mein har word ki EXPECTED frequency |
| `windowLen = wordLen * numWords` | Poori window ki size |
| `for (int offset = 0; offset < wordLen; offset++)` | Har possible starting alignment try karna |
| `windowCount` | Current window mein words ki ACTUAL frequency |
| `wordCount.containsKey(word)` | Check — yeh word list mein hai bhi ya nahi |
| `while (windowCount.get(word) > wordCount.get(word))` | Extra copies hatana (window ko left se chhota karna) |
| `count == numWords` | Poori window valid hai — answer mil gaya |
| `windowCount.clear(); left = right + wordLen;` | Invalid word milne pe window reset karna |

## Complexity

- **Time:** O(n × wordLen) — jahan `n` = string ki length. Har offset (`wordLen`
  offsets) ke liye, window O(n/wordLen) steps mein slide hoti hai, har step mein
  O(wordLen) time substring nikalne mein lagta hai
- **Space:** O(numWords × wordLen) — HashMaps mein words store karne ke liye

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `s="barfoothefoobarman", words=["foo","bar"]` | `[0, 9]` | Index 0="barfoo", index 9="foobar" |
| `s="wordgoodgoodgoodbestword", words=["word","good","best","word"]` | `[]` | "word" 2 baar chahiye tha lekin poori tarah align nahi hota |
| `s="barfoofoobarthefoobarman", words=["bar","foo","the"]` | `[6, 9, 12]` | Teeno indices se "bar","foo","the" ka koi valid arrangement match hota hai |
