# 16. 3Sum Closest

LeetCode Link: https://leetcode.com/problems/3sum-closest/

## Problem kya keh raha hai (simple bhasha mein)

Humein numbers ka ek array aur ek `target` number diya hai. Humein array mein se
**teen numbers** dhundhne hain jinka sum, `target` ke **sabse zyada paas** ho (exact
0 ya exact target nahi chahiye — bas sabse close). Us **sum ko return karna hai** (numbers
khud nahi, sirf unka sum).

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Yeh bilkul **[3Sum](../0015-3sum)** jaisa hai — bas ab humein exact `0` nahi chahiye,
balki humein `target` ke **sabse paas** wala sum chahiye. Socho tum **dart** khel rahe
ho: bull's-eye (center) pe lagana best hai, lekin agar exact center pe nahi lagta,
toh jo throw **sabse paas** ho, wahi tumhara best score hai.

Har baar jab teen numbers ka sum banate ho, check karo: "yeh sum target se kitna
door hai?" Agar yeh pehle wale se **kam door** hai, toh isko apna naya "best guess"
bana lo.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Bilkul wahi **"Sort + Fix One + Two Pointer"** pattern jo [3Sum](../0015-3sum) mein
tha:
1. Array ko **sort** karo
2. Ek number **fix** karo (outer loop)
3. Baaki do numbers ke liye **Two Pointer** (left, right) chalao
4. Sum kam hai → `left++`, sum zyada hai → `right--`

**Naya twist:** jab bhi sawal "**exact answer**" ki jagah "**closest/nearest**" answer
maange, toh iska matlab hai apne existing algorithm mein ek extra cheez add karni
padegi — **"best so far" track karna**. Har step pe naye sum ko purane best se compare
karo (`Math.abs(currentSum - target)` use karke — yeh batata hai sum, target se
kitna "door" hai, chahe sum bada ho ya chhota).

**Chhota optimization:** agar kabhi sum **bilkul target ke barabar** mil jaaye, toh
turant return kar do — isse zyada paas kuch ho hi nahi sakta!

## Dry Run — Loop ko haath se chala ke dekhte hain (real example)

Chalo `nums = [-1, 2, 1, -4]`, `target = 1` leke karte hain.

**Step 1: Sort karo** → `nums = [-4, -1, 1, 2]` (index: 0,1,2,3)

**Starting closestSum** = nums[0]+nums[1]+nums[2] = -4 + -1 + 1 = **-4**

### Outer loop: i=0 → fixed = nums[0] = -4

`left=1, right=3`

| `left` | `right` | `nums[left]` | `nums[right]` | `currentSum` | `\|currentSum-target\|` vs `\|closestSum-target\|` | Update? | Action |
|---|---|---|---|---|---|---|---|
| 1 | 3 | -1 | 2 | -4-1+2 = -3 | \|-3-1\|=4 vs \|-4-1\|=5 → 4<5 **HAA** | closestSum = **-3** | sum(-3) < target(1) → `left++` |
| 2 | 3 | 1 | 2 | -4+1+2 = -1 | \|-1-1\|=2 vs \|-3-1\|=4 → 2<4 **HAA** | closestSum = **-1** | sum(-1) < target(1) → `left++` |
| 3 | 3 | — | — | `left < right` FALSE | — | — | inner loop khatam |

**i++, ab i=1**

### Outer loop: i=1 → fixed = nums[1] = -1

`left=2, right=3`

| `left` | `right` | `nums[left]` | `nums[right]` | `currentSum` | `\|currentSum-target\|` vs `\|closestSum-target\|` | Update? | Action |
|---|---|---|---|---|---|---|---|
| 2 | 3 | 1 | 2 | -1+1+2 = 2 | \|2-1\|=1 vs \|-1-1\|=2 → 1<2 **HAA** | closestSum = **2** | sum(2) > target(1) → `right--` |
| 2 | 2 | — | — | `left < right` FALSE | — | — | inner loop khatam |

**i++, ab i=2**

### Outer loop: i=2 → fixed = nums[2] = 1

`left=3, right=3` → `left < right` FALSE turant (loop chala hi nahi)

Outer loop khatam (i, n-2=2 tak hi jaata hai). **Final answer: `2`** ✅

### Real output flow (console pe kya print hota hai):

```
input: nums=[-1,2,1,-4], target=1
after sort:  nums=[-4,-1,1,2]
starting closestSum = -4+-1+1 = -4

i=0 fixed=-4  left=1 right=3  sum=-3  |diff|=4 < old|diff|=5 -> UPDATE closestSum=-3
                                       sum<target -> left++
              left=2 right=3  sum=-1  |diff|=2 < old|diff|=4 -> UPDATE closestSum=-1
                                       sum<target -> left++
              left=3 right=3 -> loop stop (left not < right)

i=1 fixed=-1  left=2 right=3  sum=2   |diff|=1 < old|diff|=2 -> UPDATE closestSum=2
                                       sum>target -> right--
              left=2 right=2 -> loop stop (left not < right)

i=2 fixed=1   left=3 right=3 -> loop stop immediately (left not < right)

FINAL OUTPUT: 2
```

### Notice karo yeh pattern:
- `closestSum` **sirf tab update** hota hai jab naya sum, purane se **zyada paas** ho
  target ke — warna wahi purana best answer bana rehta hai
- `Math.abs()` zaroori hai kyunki sum, target se **kam bhi ho sakta hai aur zyada bhi**
  — humein sirf "kitna door hai" chahiye, "kis direction mein door hai" nahi
- Agar beech mein hi `currentSum == target` mil jaata, toh hum turant `return` kar dete
  (yahan is example mein exact match nahi mila, isliye poora loop chala)

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `Arrays.sort(nums)` | Array ko sort karna — Two Pointer ke liye zaroori |
| `closestSum = nums[0]+nums[1]+nums[2]` | Starting guess — koi bhi valid combination |
| `for (int i = 0; i < n - 2; i++)` | Outer loop — ek number "fix" karta hai |
| `int left = i+1, right = n-1;` | Two pointer setup |
| `while (left < right)` | Inner loop — Two Pointer dhundhta rehta hai |
| `Math.abs(currentSum - target)` | Current sum, target se kitna "door" hai |
| `if (... < ...) closestSum = currentSum;` | Better (closer) answer mile toh update karna |
| `if (currentSum == target) return currentSum;` | Perfect match mile toh turant return (early exit) |
| `currentSum < target` → `left++` | Sum badhana hai |
| `currentSum > target` → `right--` | Sum ghatana hai |

## Complexity

- **Time:** O(n²) — sorting O(n log n), phir outer loop O(n) × inner Two Pointer O(n)
- **Space:** O(log n) to O(n) — sirf sorting ke liye (koi extra result list nahi banani,
  kyunki sirf ek number return karna hai)

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `nums=[-1,2,1,-4], target=1` | `2` | -1+2+1=2, jo target(1) ke sabse paas hai |
| `nums=[0,0,0], target=1` | `0` | Sirf ek hi combination possible hai: 0+0+0=0 |
| `nums=[1,1,1,0], target=100` | `3` | Max possible sum 1+1+1=3, sabse paas 100 ke |
