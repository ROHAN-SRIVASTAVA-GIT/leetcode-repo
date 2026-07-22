# 15. 3Sum

LeetCode Link: https://leetcode.com/problems/3sum/

## Problem kya keh raha hai (simple bhasha mein)

Humein numbers ka ek array diya hai (negative aur positive dono ho sakte hain). Humein
**saare unique triplets (3 numbers ke groups)** dhundhne hain jinka **sum bilkul 0**
ho. Ek hi triplet do baar answer mein nahi aana chahiye (duplicates allowed nahi).

## ELI5 (5 saal ke bachche ko samjhaane jaisa)

Socho tumhare paas ek pocket mein kuch **coins hain** — kuch "credit" wale (positive,
jaise +2, +1) aur kuch "debit" wale (negative, jaise -1, -4). Tumhe **teen coins**
chunne hain jinko jodne par **total bilkul zero (0)** ho jaaye — na profit, na loss.

Jaise: `-1 + -1 + 2 = 0` — yeh ek valid combination hai!

Yeh Problem 11 ([Container With Most Water](../0011-container-with-most-water)) jaisa
hi **Two Pointer** wala kaam hai, bas yahan hume **do numbers ki jagah teen** chahiye.

## Kaunsi Technique use hoti hai? (Pattern pehchaanna seekho)

Jab bhi sawal ho "**N numbers ka koi target sum ke barabar dhundo**":
- Agar **N = 2** → seedha Two Pointer (jaise Container With Most Water mein tha)
- Agar **N = 3** (jaise yahan) → **"Fix One + Two Pointer on rest"** pattern:
  1. Array ko **sort** karo (chhote se bade) — Two Pointer sirf sorted array pe kaam karta hai
  2. Ek loop se **ek number ko "fix"** kar lo (socho "yeh mera pehla number hai")
  3. Baaki 2 numbers ke liye, bache hue part mein **Two Pointer** chalao — target ab
     hoga `0 - fixed_number`
  4. Sum kam hai → `left++` (bada number chahiye, sorted hai toh aage bade numbers hain)
  5. Sum zyada hai → `right--` (chhota number chahiye)
  6. Sum match → answer mil gaya, dono pointer ANDAR move karo

**Important trick — duplicates se bachna:** kyunki array sorted hai, saare same numbers
**paas-paas** aa jaate hain. Toh jab bhi ek number use kar liya, uske turant baad
wale **same** numbers ko **skip** kar do — warna same triplet baar-baar ban jaayegi.

## Dry Run — Loop ko haath se chala ke dekhte hain (real example)

Chalo `nums = [-1, 0, 1, 2, -1, -4]` leke karte hain.

**Step 1: Sort karo** → `nums = [-4, -1, -1, 0, 1, 2]` (index: 0,1,2,3,4,5)

### Outer loop: i=0 → fixed = nums[0] = -4

`left=1, right=5` → dhundo do numbers jinka sum = `4` ho (kyunki -4 + kuch + kuch = 0)

| `left` | `right` | `nums[left]` | `nums[right]` | `sum = -4+left+right` | Condition | Action |
|---|---|---|---|---|---|---|
| 1 | 5 | -1 | 2 | -4-1+2 = -3 | sum < 0 | `left++` |
| 2 | 5 | -1 | 2 | -4-1+2 = -3 | sum < 0 | `left++` |
| 3 | 5 | 0 | 2 | -4+0+2 = -2 | sum < 0 | `left++` |
| 4 | 5 | 1 | 2 | -4+1+2 = -1 | sum < 0 | `left++` |
| 5 | 5 | — | — | `left < right` FALSE | inner loop khatam | — |

Koi triplet nahi mila is `i` ke liye. **i++, ab i=1**

### Outer loop: i=1 → fixed = nums[1] = -1

`left=2, right=5` → dhundo do numbers jinka sum = `1` ho

| `left` | `right` | `nums[left]` | `nums[right]` | `sum = -1+left+right` | Condition | Action |
|---|---|---|---|---|---|---|
| 2 | 5 | -1 | 2 | -1-1+2 = 0 | **sum == 0!** ✅ | Add `[-1,-1,2]`, then `left++, right--` |
| 3 | 4 | 0 | 1 | -1+0+1 = 0 | **sum == 0!** ✅ | Add `[-1,0,1]`, then `left++, right--` |
| 4 | 3 | — | — | `left < right` FALSE (4 < 3 galat) | inner loop khatam | — |

Do triplets mile: `[-1,-1,2]` aur `[-1,0,1]`. **i++, ab i=2**

### Outer loop: i=2 → nums[2] = -1

Check: `nums[2] == nums[1]`? **-1 == -1, HAA** → yeh **duplicate fixed number** hai,
toh `continue` (yeh poori iteration skip ho jaati hai, koi Two Pointer chalta hi nahi)

### Outer loop: i=3 → fixed = nums[3] = 0

`left=4, right=5` → `nums[4]=1, nums[5]=2` → sum = 0+1+2 = 3, **sum > 0** → `right--`
Ab `left=4, right=4` → `left < right` FALSE → loop khatam, kuch nahi mila

Loop poori tarah khatam (i, n-2 tak pahunch gaya). **Final answer: `[[-1,-1,2], [-1,0,1]]`** ✅

### Real output flow (console pe kya print hota hai):

```
input: nums = [-1,0,1,2,-1,-4]
after sort:   nums = [-4,-1,-1,0,1,2]

i=0 fixed=-4  left=1 right=5  sum=-3 -> too small, left++
              left=2 right=5  sum=-3 -> too small, left++
              left=3 right=5  sum=-2 -> too small, left++
              left=4 right=5  sum=-1 -> too small, left++
              left=5 right=5  -> loop stop (left not < right)

i=1 fixed=-1  left=2 right=5  sum=0  -> FOUND [-1,-1,2]! left++, right--
              left=3 right=4  sum=0  -> FOUND [-1,0,1]! left++, right--
              left=4 right=3  -> loop stop (left not < right)

i=2 nums[2]==nums[1] (-1==-1) -> DUPLICATE, skip this i entirely

i=3 fixed=0   left=4 right=5  sum=3  -> too big, right--
              left=4 right=4  -> loop stop (left not < right)

FINAL OUTPUT: [[-1,-1,2],[-1,0,1]]
```

### Notice karo yeh pattern:
- **Sorting** hi sabse important step hai — usi se Two Pointer ka "left badhao ya right
  ghatao" wala logic kaam karta hai
- `i` (fixed number) ke liye **duplicate check upar** hota hai (loop ke start mein)
- `left` aur `right` ke liye duplicate check **sirf tab hota hai jab match mil jaaye**
  (kyunki tabhi hume agla DIFFERENT combination chahiye)
- `n - 2` tak hi outer loop chalta hai kyunki hamesha 2 aur numbers (left, right) chahiye
  hote hain fixed number ke baad

## Line by Line Concept (Solution.java mein)

| Cheez | Kya hai |
|---|---|
| `Arrays.sort(nums)` | Array ko sort karna — Two Pointer ke liye zaroori |
| `for (int i = 0; i < n - 2; i++)` | Outer loop — ek number "fix" karta hai har baar |
| `if (i > 0 && nums[i] == nums[i-1]) continue;` | Fixed number ke duplicate ko skip karna |
| `int left = i + 1, right = n - 1;` | Two pointer setup — fixed ke baad se end tak |
| `while (left < right)` | Inner loop — jab tak dono pointer mile nahi |
| `sum == 0` | Answer mila — triplet ko result mein add karna |
| `sum < 0` → `left++` | Sum badhana hai — sorted array mein aage bada number milega |
| `sum > 0` → `right--` | Sum ghatana hai — sorted array mein peeche chhota number milega |
| `while (nums[left] == nums[left+1]) left++;` | Left pointer ke duplicates skip karna (match milne ke baad) |

## Complexity

- **Time:** O(n²) — sorting O(n log n), phir outer loop O(n) aur har outer step ke
  liye inner Two Pointer O(n) — total O(n) × O(n) = O(n²)
- **Space:** O(log n) to O(n) — sorting algorithm ke liye (result list ko chhod ke,
  jo answer ke liye zaroori hai)

## Test Cases

| Input | Output | Kyun |
|---|---|---|
| `[-1,0,1,2,-1,-4]` | `[[-1,-1,2],[-1,0,1]]` | Dono combinations sum = 0 dete hain |
| `[0,1,1]` | `[]` | Koi bhi 3 numbers ka sum 0 nahi banta |
| `[0,0,0]` | `[[0,0,0]]` | 0+0+0 = 0, sirf ek hi unique triplet |
