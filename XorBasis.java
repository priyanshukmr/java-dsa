// https://leetcode.com/problems/maximum-xor-of-subsequences/
// https://cses.fi/problemset/result/15166097/

class Solution {

    static final int BITS = 30;

    private int[] xorBasis(int[] nums) {
        int[] basis = new int[BITS+1];
        for(int num: nums) {
            int reduced = num;
            for(int bit=BITS; bit>=0; bit--) {
                if(((1<<bit)&reduced)>0) {
                    if(basis[bit]==0) {
                        basis[bit] = reduced;
                        break;
                    }
                    reduced^=basis[bit];
                }
            }
        }
        return basis;
    }

    private int countXorSubsequences(int[] nums) {
        int[] basis = xorBasis(nums);
        int ans = 1;
        for(int i=0; i<=BITS; i++) {
            if(basis[i]!=0) ans*=2;
        }
        return ans;
    }
 
    public int maxXorSubsequences(int[] nums) {
        int maxor = 0;
        int[] basis = xorBasis(nums);
        for(int bit=BITS; bit>=0; bit--) {
            maxor = Math.max(maxor, maxor^basis[bit]);
        }
        return maxor;
    }
}
