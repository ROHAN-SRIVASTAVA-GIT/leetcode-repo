# 31. Next Permutation

LeetCode Link: https://leetcode.com/problems/next-permutation/

## Problem kya keh raha hai (simple bhasha mein)

Humein numbers ka ek array diya hai — yeh un numbers ki ek **"permutation" (arrangement)**
hai. Humein array ko **in-place** modify karke uska **"next permutation"** bana dena
hai — matlab agar saari possible arrangements ko **dictionary (sorted) order** mein
likha jaaye, toh diye gaye arrangement ke **turant baad wala** kaunsa hai. Agar diya
gaya arrangement **sabse bada** hai, toh **sabse chhota** bana dena hai (wrap around).

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho ek **car ka odometer (distance meter)** — jab saare digits `9` ho jaate hain
(jaise `199`), agla number `200` banta hai — ek digit "carry" hokar peeche wala digit
badal deta hai. Yahi cheez yaha ho rahi hai, bas numbers ki jagah **poora array ka
arrangement** badal raha hai.

`[1,2,3]` ke saare arrangements dictionary order mein: `123, 132, 213, 231, 312, 321`.
`[1,2,3]` ke baad `[1,3,2]` aata hai — yehi humara answer hai.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho) — 4-STEP STANDARD ALGORITHM

Yeh ek **well-known algorithm** hai jo yaad rakhna padta hai (isko khud se derive
karna mushkil hai, lekin ek baar samajh liya toh hamesha yaad rahega):

### Step 1: "Break Point" dhundo (right se left)
Right se left chalo, **pehla aisa index `i`** dhundo jaha `nums[i] < nums[i+1]` ho
(matlab yaha se "badhna" possible hai — array yaha tak descending tha).

**Agar `i` milta hi nahi** (poora array descending hai, jaise `[3,2,1]`) — iska
matlab yeh **sabse bada arrangement** hai. Next permutation **sabse chhota** hoga,
jo poore array ko **ascending order** mein karne se milta hai (seedha reverse kar do,
kyunki descending ko reverse karne se ascending ban jaata hai).

### Step 2: Swap karne wala element dhundo (right se left)
Agar `i` mil gaya, toh right se left chalke **pehla aisa `j`** dhundo jaha
`nums[j] > nums[i]` ho — yeh `nums[i]` se **thoda sa bada** element hai (sabse
chhota possible increase ke liye zaroori).

### Step 3: Swap karo
`nums[i]` aur `nums[j]` ko **swap** kar do.

### Step 4: Baaki hissa reverse karo
`i` ke baad wala poora hissa (`i+1` se end tak) **abhi bhi descending order** mein
hai (kyunki humne sirf ek "break point" tak hi kaam kiya). Ise **reverse** karke
ascending bana do — isse **sabse chhota possible arrangement** banta hai us hisse mein.

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `int i = n - 2;` | Break point dhundhne ke liye starting position |
| `while (i >= 0 && nums[i] >= nums[i+1]) i--;` | Right se left "break point" dhundhna |
| `if (i >= 0)` | Agar break point mila (poora array descending nahi tha) |
| `while (nums[j] <= nums[i]) j--;` | Right se left, `nums[i]` se thoda bada element dhundhna |
| `swap(nums, i, j);` | Dono elements ko swap karna |
| `reverse(nums, i + 1, n - 1);` | Baaki hissa reverse karna (chahe `i=-1` ho, tab poora array reverse hoga) |

## Complexity

- **Time:** O(n) — array ko constant baar (2-3 baar) traverse karte hain
- **Space:** O(1) — sab kuch in-place, koi extra array nahi
