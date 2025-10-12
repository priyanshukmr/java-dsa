static class Sieve {
    int n;
    int[] prime;

    Sieve(int _n) {
        n = _n;
        prime = new int[n+5];

        for(int p=2; p<=n; p++) {
            if(prime[p]!=0) continue;
            for(int mult=p; mult<=n; mult+=p) {
                if(prime[mult]==0)
                    prime[mult] = p;
            }
        }
    }

    List<List<Integer>> factorize(int num) {
        List<List<Integer>> factors = new ArrayList<>();;
        while(num>1) {
            int power = 0;
            int p = prime[num];
            while(num%p==0) {
                num/=p;
                power++;
            }
            factors.add(List.of(p, power));
        }
        return factors;
    }

    private int squareFreeKernel(int num) {
        int kernel = 1;
        List<List<Integer>> factors = factorize(num);
        for(List<Integer> factor: factors) {
            if(factor.get(1)%2==1) {
                kernel *= factor.get(0);
            }
        }
        return kernel;
    }

    private boolean isPrime(int num) {
        return prime[num]==num;
    }
}
