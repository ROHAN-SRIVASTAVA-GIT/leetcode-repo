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

## Dry Run — Algorithm ko haath se chala ke dekhte hain (real example)

Chalo `nums = [1,1,5]` leke karte hain (index: 0,1,2). `n = 3`

### Step 1: Break point dhundo

`i = n-2 = 1`

| Check: `i>=0 && nums[i] >= nums[i+1]`? | `nums[i]` | `nums[i+1]` | Action |
|---|---|---|---|
| `nums[1](1) >= nums[2](5)`? **NA** | 1 | 5 | loop RUKA turant (i=1 hi break point hai) |

`i = 1` mil gaya (`nums[1]=1 < nums[2]=5`)

### Step 2: `j` dhundo (right se left, `nums[i]` se bada)

`j = n-1 = 2`

| Check: `nums[j] <= nums[i]`? | `nums[j]` | `nums[i]` | Action |
|---|---|---|---|
| `nums[2](5) <= nums[1](1)`? **NA** | 5 | 1 | loop RUKA turant (j=2 hi sahi hai) |

`j = 2`

### Step 3: Swap `nums[1]` aur `nums[2]`

`nums = [1, 5, 1]` (1 aur 5 swap hue)

### Step 4: `i+1` se end tak reverse karo

`reverse(nums, 2, 2)` — sirf ek hi element hai (index 2 se 2), kuch badalta nahi

**Final answer: `[1, 5, 1]`** ✅

### Ab ek "wrap around" wala example dekhte hain: `nums = [3,2,1]`

### Step 1: Break point dhundo

`i = 1`: `nums[1](2) >= nums[2](1)`? HAA → `i--` → `i=0`
`i = 0`: `nums[0](3) >= nums[1](2)`? HAA → `i--` → `i=-1`
`i = -1`: loop condition `i>=0` hi FALSE ho gaya → loop RUKA

`i = -1` — koi break point nahi mila! (poora array descending hai)

### Step 2 & 3: SKIP (kyunki `i < 0`, `if (i >= 0)` block chalta hi nahi)

### Step 4: `i+1(=0)` se `n-1(=2)` tak POORA array reverse karo

`[3,2,1]` reverse hoke ban gaya `[1,2,3]`

**Final answer: `[1, 2, 3]`** ✅ (sabse bada arrangement se sabse chhote pe wrap around hua)

### Real output flow (console pe simplified trace) — `nums=[1,1,5]`:

```
input: nums=[1,1,5], n=3

Step1: i=1, check nums[1](1)>=nums[2](5)? NO -> stop. i=1 (break point found)

Step2: j=2, check nums[2](5)<=nums[1](1)? NO -> stop. j=2

Step3: swap(1,2) -> nums=[1,5,1]

Step4: reverse(nums, 2, 2) -> single element, no change

FINAL OUTPUT: [1, 5, 1]
```

### Notice karo yeh pattern:
- Jab `i` **turant mil jaata hai** (loop pehli hi check mein ruk jaata hai), matlab
  array ke aakhri do elements mein hi "increase" possible tha
- Jab `i = -1` mil jaata hai, iska matlab array **poora descending** tha — is case
  mein Step 2, 3 skip ho jaate hain aur Step 4 hi **poore array ko reverse** kar deta
  hai (yeh khud-ba-khud "wrap around" handle kar deta hai, koi alag if-else nahi
  chahiye)
- Reverse hamesha `i+1` se shuru hota hai — chahe `i` koi bhi ho, yeh humesha **sahi
  hissa** reverse karta hai

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

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `[1,2,3]` | `[1,3,2]` | Dictionary order mein 123 ke turant baad 132 aata hai |
| `[3,2,1]` | `[1,2,3]` | Sabse bada arrangement tha, wrap around hoke sabse chhota bana |
| `[1,1,5]` | `[1,5,1]` | Break point i=1 mila, 1 aur 5 swap hue, aakhri hissa reverse kiya |
