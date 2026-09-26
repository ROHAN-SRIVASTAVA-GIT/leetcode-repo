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
