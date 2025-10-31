private static long fastExp(long base, long exp) {
    long ans = 1L;
    while(exp>0L) {
        if((exp&1L)>0L) ans = (ans*base) % MOD;
        base = (base*base) % MOD;
        exp = exp>>1;
    }
    return ans;
}
