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
