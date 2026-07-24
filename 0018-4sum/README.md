# 18. 4Sum

LeetCode Link: https://leetcode.com/problems/4sum/

## Problem kya keh raha hai (simple bhasha mein)

Humein numbers ka ek array aur ek `target` diya hai. Humein saare **unique groups of 4
numbers** dhundhne hain jinka sum bilkul `target` ke barabar ho. Koi duplicate group
nahi chahiye.

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Yeh **[3Sum](../0015-3sum)** ka bada bhai hai. Wahan 3 coins chunke total dekhte the
(0 ke barabar), yahan **4 coins** chunke total dekhna hai (kisi bhi `target` ke barabar).

Socho: pehle 2 coins **fix** kar lo apne haath mein, phir bache hue coins mein se
**Two Pointer** (ek left se, ek right se) use karke baaki 2 coins dhundo jo total ko
sahi bana dein.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Yeh **"kSum"** pattern hai — jitne bhi numbers chahiye ho jinka sum kisi target ke
barabar ho:

| k (kitne numbers chahiye) | Technique |
|---|---|
| k=2 | Seedha Two Pointer |
| k=3 | 1 number fix + Two Pointer ([3Sum](../0015-3sum)) |
| k=4 | 2 numbers fix (nested loop) + Two Pointer (**yeh problem**) |

Formula samjho: **hamesha (k-2) nested loops** lagao numbers "fix" karne ke liye, aur
**last 2 numbers ke liye hamesha Two Pointer**.

**Naya cheez jo yahan seekhne wali hai — Integer Overflow:**
Java mein `int` ki ek maximum limit hoti hai (~2.1 billion). Agar 4 bade numbers
(jaise `10^9` jaise 4 numbers) jod diye jaayein, toh unka sum is limit se **bahar
nikal** sakta hai — isse "overflow" kehte hain, aur answer **galat** aa sakta hai.
Isko rokne ke liye hum sum ko `long` (ek bada data type, jo `int` se zyada bada
number store kar sakta hai) mein calculate karte hain.

## Dry Run — Loop ko haath se chala ke dekhte hain (real example)

Chalo `nums = [1, 0, -1, 0, -2, 2]`, `target = 0` leke karte hain.

**Step 1: Sort karo** → `nums = [-2, -1, 0, 0, 1, 2]` (index: 0,1,2,3,4,5)

### Outer loop i=0 → fixed#1 = nums[0] = -2

**Inner loop j=1 → fixed#2 = nums[1] = -1**

`left=2, right=5` → dhundo do numbers jinka sum = `0 - (-2) - (-1) = 3` ho

| `left` | `right` | `nums[left]` | `nums[right]` | `sum` (total 4 numbers) | Condition | Action |
|---|---|---|---|---|---|---|
| 2 | 5 | 0 | 2 | -2-1+0+2 = -1 | sum < 0 | `left++` |
| 3 | 5 | 0 | 2 | -2-1+0+2 = -1 | sum < 0 | `left++` |
| 4 | 5 | 1 | 2 | -2-1+1+2 = **0** ✅ | **MATCH!** | Add `[-2,-1,1,2]`, `left++, right--` |
| 5 | 4 | — | — | `left < right` FALSE | loop khatam | — |

**Inner loop j=2 → fixed#2 = nums[2] = 0**

`left=3, right=5` → dhundo do numbers jinka sum = `0 - (-2) - 0 = 2` ho

| `left` | `right` | `nums[left]` | `nums[right]` | `sum` | Condition | Action |
|---|---|---|---|---|---|---|
| 3 | 5 | 0 | 2 | -2+0+0+2 = **0** ✅ | **MATCH!** | Add `[-2,0,0,2]`, `left++, right--` |
| 4 | 4 | — | — | `left < right` FALSE | loop khatam | — |

**Inner loop j=3 → nums[3] == nums[2] (0==0)** → **DUPLICATE, skip** (`continue`)

**i=0 ke liye j loop khatam. Ab i++, i=1**

### Outer loop i=1 → fixed#1 = nums[1] = -1

**Inner loop j=2 → fixed#2 = nums[2] = 0**

`left=3, right=5` → dhundo do numbers jinka sum = `0 - (-1) - 0 = 1` ho

| `left` | `right` | `nums[left]` | `nums[right]` | `sum` | Condition | Action |
|---|---|---|---|---|---|---|
| 3 | 5 | 0 | 2 | -1+0+0+2 = 1 | sum > 0 | `right--` |
| 3 | 4 | 0 | 1 | -1+0+0+1 = **0** ✅ | **MATCH!** | Add `[-1,0,0,1]`, `left++, right--` |
| 4 | 3 | — | — | `left < right` FALSE | loop khatam | — |

Baaki combinations (i=2,3 waghera) koi naya answer nahi dete (khud try karke check kar
sakte ho — pattern same hai). **Final answer: `[[-2,-1,1,2], [-2,0,0,2], [-1,0,0,1]]`** ✅

### Real output flow (console pe kya print hota hai, key steps):

```
input: nums=[1,0,-1,0,-2,2], target=0
after sort: nums=[-2,-1,0,0,1,2]

i=0 (fixed1=-2)
  j=1 (fixed2=-1)  left=2 right=5  sum=-1 -> too small, left++
                    left=3 right=5  sum=-1 -> too small, left++
                    left=4 right=5  sum=0  -> MATCH [-2,-1,1,2]! left++, right--
                    left=5 right=4  -> stop
  j=2 (fixed2=0)   left=3 right=5  sum=0  -> MATCH [-2,0,0,2]! left++, right--
                    left=4 right=4  -> stop
  j=3 nums[3]==nums[2] -> DUPLICATE, skip

i=1 (fixed1=-1)
  j=2 (fixed2=0)   left=3 right=5  sum=1  -> too big, right--
                    left=3 right=4  sum=0  -> MATCH [-1,0,0,1]! left++, right--
                    left=4 right=3  -> stop
  ... (baaki koi naya match nahi)

FINAL OUTPUT: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
```

### Notice karo yeh pattern:
- Do **nested loops** (`i` aur `j`) numbers "fix" karte hain, phir **Two Pointer**
  (`left`, `right`) baaki 2 dhundhta hai — bilkul 3Sum jaisa, bas ek extra loop
- Duplicate-skip **teen jagah** hoti hai: `i` ke liye, `j` ke liye, aur match milne
  ke baad `left`/`right` ke liye
- `sum` ko `long` mein calculate kiya taaki bade numbers ka sum overflow na kare

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `Arrays.sort(nums)` | Array ko sort karna — Two Pointer aur duplicate-skip ke liye zaroori |
| `for (int i = 0; i < n-3; i++)` | Pehla outer loop — fixed number #1 |
| `for (int j = i+1; j < n-2; j++)` | Doosra outer loop — fixed number #2 |
| `if (i > 0 && nums[i]==nums[i-1]) continue;` | Fixed #1 ka duplicate skip |
| `if (j > i+1 && nums[j]==nums[j-1]) continue;` | Fixed #2 ka duplicate skip |
| `long sum = (long) nums[i] + ...` | Sum ko long mein calculate karna (overflow se bachne ke liye) |
| `while (left < right)` | Two Pointer inner loop |
| `sum == target` | Match mila — result mein add karna |
| `sum < target` → `left++` | Sum badhana hai |
| `sum > target` → `right--` | Sum ghatana hai |

## Complexity

- **Time:** O(n³) — sorting O(n log n), phir do nested loops O(n²), aur har combination
  ke liye Two Pointer O(n) — total O(n²) × O(n) = O(n³)
- **Space:** O(log n) to O(n) — sorting ke liye (result list ko chhod ke)

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `nums=[1,0,-1,0,-2,2], target=0` | `[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]` | Teeno combinations ka sum 0 hai |
| `nums=[2,2,2,2,2], target=8` | `[[2,2,2,2]]` | Sirf ek hi unique combination possible hai |
