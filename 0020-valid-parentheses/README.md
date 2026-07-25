# 20. Valid Parentheses

LeetCode Link: https://leetcode.com/problems/valid-parentheses/

## Problem kya keh raha hai (simple bhasha mein)

Humein ek string di gayi hai jisme sirf `(`, `)`, `{`, `}`, `[`, `]` characters hain.
Humein check karna hai ki yeh brackets **sahi tareeke se open aur close** hue hain
ya nahi (`true`/`false` return karna hai).

Rules:
1. Har opening bracket ka **sahi type** ka closing bracket hona chahiye
2. Brackets **sahi order** mein band hone chahiye (jo sabse recently khula, wo sabse
   pehle band ho)

## Sabse pehle: Stack kya hai?

**Stack** ek data structure hai jo **plates ke dhair (pile)** jaisa kaam karta hai:
- Sirf **top (sabse upar)** se hi cheez daal (push) sakte ho
- Sirf **top** se hi cheez nikaal (pop) sakte ho
- Beech mein se kuch access nahi kar sakte

```
push('a')  push('b')  push('c')     pop() -> 'c' nikla
   a          a  b       a  b  c        a  b
```

Isko **LIFO (Last In, First Out)** kehte hain — jo **sabse aakhir mein andar gaya**,
wahi **sabse pehle bahar aayega**.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho tum brackets ki string padh rahe ho left se right. Jab bhi tumhe **opening
bracket** (`(`, `{`, `[`) mile, use apni **plates ke dhair mein upar rakh do**.

Jab tumhe **closing bracket** (`)`, `}`, `]`) mile, tumhe **sabse upar wali plate
uthani** hai aur check karna hai:
- Kya wo plate **sahi type** ki hai? (jaise closing `)` ke liye upar wali plate
  `(` hi honi chahiye, `{` nahi)
- Agar plate hai hi nahi (dhair khaali hai), toh kuch match karne ko hai hi nahi —
  **galat!**

String ke aakhir mein, agar **dhair bilkul khaali** hai (sab plates uth chuki), toh
**valid** hai. Agar kuch plates **bachi reh gayi** (kuch opening bracket band hi
nahi hua), toh **invalid** hai.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Jab bhi sawal mein:
- **"Sabse recent/aakhri cheez ko pehle handle karo"** jaisa kaam ho
- Brackets/parentheses ka **matching aur nesting** check karna ho
- Kisi cheez ka "undo" ya "reverse in LIFO order" karna ho

...tab **Stack** data structure use karo. Bracket-matching problems (chahe validate
karna ho, ya kisi expression ko evaluate karna ho) ke liye Stack **sabse natural
fit** hai, kyunki brackets bhi **"last opened, first closed"** rule follow karte hain.

**HashMap ka use:** matching pairs (`)`→`(`, `}`→`{`, `]`→`[`) ko store karne ke liye
HashMap use kiya, taaki if-else ki jagah seedha `map.get()` se lookup ho sake.

## Dry Run — Loop ko haath se chala ke dekhte hain (real example)

Chalo `s = "([)]"` leke karte hain (yeh **invalid** hai, kyunki galat order mein band
hua — dekhte hain code isko kaise pakadta hai).

`matchingPairs = { ')':'(' , '}':'{' , ']':'[' }`

| Character | Opening ya Closing? | Stack (pehle) | Action | Stack (baad) |
|---|---|---|---|---|
| `(` | Opening | `[]` (khaali) | push('(') | `['(']` |
| `[` | Opening | `['(']` | push('[') | `['(', '[']` |
| `)` | Closing | `['(', '[']` | stack khaali nahi hai, pop() → `'['` nikla. Check: `'[' != matchingPairs.get(')')` yani `'[' != '('`? **HAA, mismatch!** | — |
| — | — | — | **return false** (turant ruk gaye, aage kuch check nahi hua) | — |

**Final answer: `false`** ✅ (kyunki `[` opening tha, lekin `)` uska galat closing bracket try kar raha tha)

### Ab ek VALID example dekhte hain: `s = "()[]{}"`

| Character | Opening ya Closing? | Stack (pehle) | Action | Stack (baad) |
|---|---|---|---|---|
| `(` | Opening | `[]` | push('(') | `['(']` |
| `)` | Closing | `['(']` | pop() → `'('`. Check: `'(' == matchingPairs.get(')')` = `'(' == '('`? **HAA match!** | `[]` |
| `[` | Opening | `[]` | push('[') | `['[']` |
| `]` | Closing | `['[']` | pop() → `'['`. Match! | `[]` |
| `{` | Opening | `[]` | push('{') | `['{']` |
| `}` | Closing | `['{']` | pop() → `'{'`. Match! | `[]` |

Loop khatam. Final check: `stack.isEmpty()`? **HAA** → **Final answer: `true`** ✅

### Real output flow (console pe kya print hota hai) — `s = "([)]"`:

```
input: s = "([)]"

char '(' -> OPENING -> push -> stack: [(]
char '[' -> OPENING -> push -> stack: [(, []
char ')' -> CLOSING -> stack not empty, pop -> got '['
              check: '[' matches ')' 's pair '(' ? NO (mismatch!)
              -> return false immediately

FINAL OUTPUT: false
```

### Notice karo yeh pattern:
- Do jagah **safety check** zaroori hai: (1) stack khaali toh nahi hai closing bracket
  aane pe, (2) jo top se nikla wo sahi type ka hai kya
- Jaise hi ek **mismatch** milta hai, hum **turant `return false`** kar dete hain —
  poori string check karne ki zaroorat nahi
- **Sabse aakhir** mein `stack.isEmpty()` check karna zaroori hai — warna
  `"((("` jaisa case galti se "valid" mil jaayega (koi mismatch nahi hua, lekin
  kuch opening brackets band hi nahi hue)

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `Map<Character, Character> matchingPairs` | Closing bracket → uska sahi opening bracket |
| `Stack<Character> stack` | Opening brackets ko track karne ke liye |
| `stack.push(c)` | Opening bracket ko dhair ke upar rakhna |
| `stack.isEmpty()` | Check: dhair khaali hai kya (closing aane se pehle) |
| `stack.pop()` | Sabse upar wali plate uthana aur nikalna |
| `topOpening != matchingPairs.get(c)` | Check: nikla hua opening, sahi type ka hai kya |
| `return stack.isEmpty();` | Final check — sab kuch match ho chuka hai kya |

## Complexity

- **Time:** O(n) — string ko sirf ek baar traverse karte hain
- **Space:** O(n) — worst case mein (sab opening brackets) stack mein poori string
  jitne characters ho sakte hain

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `"()[]{}"` | `true` | Sab brackets sahi order mein open-close hue |
| `"(]"` | `false` | `(` ka closing `)` hona chahiye tha, `]` nahi |
| `"([)]"` | `false` | Galat order — `[` khula but `)` ne use "cut" kar diya |
