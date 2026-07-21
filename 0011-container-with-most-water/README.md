# 11. Container With Most Water

LeetCode Link: https://leetcode.com/problems/container-with-most-water/

## Problem kya keh raha hai (simple bhasha mein)

Humein ek array `height[]` diya hai. Har number ek **vertical line ki height** hai.
Humein **do lines choose karni hain** taaki un dono lines ke beech jo "container" (bartan)
banega, usme **sabse zyada paani** aa sake.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho fence mein bahut saare **khambhe (poles)** lage hain, sab alag-alag height ke.
Tumhe do khambhe choose karne hain aur unke beech paani bharna hai — jaise ek tub banate ho.

Paani kitna aayega, yeh 2 cheezon pe depend karta hai:

1. **Dono khambhon ke beech ki doori (width)** — jitni zyada doori, utna bada tub
2. **Chhota khambha (height)** — paani hamesha chhoti deewar tak hi rukega,
   badi deewar se upar overflow ho jaayega

**Formula:**
```
Paani = (chhoti height) x (doori/width)
```

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Jab bhi aisa sawal mile:
- Ek **array line mein diya ho**
- Humein **do elements choose karke** kuch **maximize/minimize** karna ho
- Brute force (har pair check karna) **O(n²)** slow lage

...tab ek technique try karo jiska naam hai **"Two Pointer Technique"**.

### Two Pointer kaise kaam karta hai yahan:

1. Ek pointer `left` sabse **shuru (index 0)** mein rakho
2. Doosra pointer `right` sabse **aakhir (last index)** mein rakho
3. Dono ke beech ka paani calculate karo, aur best (maximum) yaad rakho
4. Ab **jo pointer chhota hai, usko andar (beech) ki taraf move karo**
   - Kyun? Kyunki chhoti height hi rok rahi thi paani ko.
   - Badi wali ko move karne ka koi fayda nahi — width kam hogi, height improve nahi hogi.
   - Chhoti wali ko move karne se shayad ek bigger height mile jo better result de.
5. Yeh tab tak repeat karo jab tak `left` aur `right` mil na jaayein

Isse hum sirf **ek baar** array traverse karte hain (O(n) time), do baar loop lagाने
(nested loop, O(n²)) ki zaroorat nahi padti.

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `left`, `right` | Do pointers — array ke dono end se shuru |
| `while (left < right)` | Loop — jab tak dono pointer mil na jaayein |
| `width = right - left` | Dono pointers ke beech ki doori |
| `Math.min(height[left], height[right])` | Dono mein se chhoti height nikalna |
| `if (currentWater > maxWater)` | Best answer ko update karna |
| `if (height[left] < height[right])` | Decide karna kaunsa pointer move karna hai |
| `left++` / `right--` | Chhote wale pointer ko andar move karna |

## Complexity

- **Time:** O(n) — array ko sirf ek baar traverse karte hain
- **Space:** O(1) — koi extra array/data structure nahi use kiya

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `[1,8,6,2,5,4,8,3,7]` | `49` | Index 1 (height 8) aur index 8 (height 7) ke beech: width=7, height=min(8,7)=7 → 7*7=49 |
| `[1,1]` | `1` | Sirf 2 hi elements hain, width=1, height=1 → 1*1=1 |
