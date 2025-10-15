static class Matrix {
    public static long[][] mult(long[][] a, long[][] b) {
        int sz = a.length;
        long[][] prod = new long[sz][sz];
        
        for(int i=0; i<sz; i++) {
            for(int j=0; j<sz; j++) {
                for(int k=0; k<sz; k++) {
                    prod[i][j] = (prod[i][j] + (a[i][k]*b[k][j])%MOD)%MOD;
                }
            }
        }
        return prod;
    }
    
    public static long[][] exp(long[][] base, long n) {
        int sz = base.length;
        long[][] result = new long[sz][sz];
        for(int i=0; i<sz; i++) result[i][i] = 1L;
        while(n>0) {
            if((n&1L)>0) {
                result = mult(result, base);
            }
            base = mult(base, base);
            n = n>>1;
        }
        return result;
    }
}

// usage:

static public void solve(Reader rd) throws IOException {
    n = rd.nextLong();
    long[][] base = new long[][]{
            {1, 1},
            {1, 0}
    };
    long[][] res = Matrix.exp(base, n);
    out.append(res[1][0]);
}



// optimised mult impl for leveraging sequntial memory 

public static long[][] mult(long[][] a, long[][] b) {
    final int n = a.length;
    long[][] prod = new long[n][n];

    for (int i = 0; i < n; i++) {
        long[] ai = a[i];
        long[] ci = prod[i];           // write this row sequentially
        for (int k = 0; k < n; k++) {
            long aik = ai[k] % MOD;    // reuse this scalar across j
            if (aik == 0) continue;    // small skip helps when sparse
            long[] bk = b[k];          // read this row sequentially
            for (int j = 0; j < n; j++) {
                ci[j] = (ci[j] + (aik * (bk[j] % MOD)) % MOD) % MOD;
            }
        }
    }
    return prod;
}

