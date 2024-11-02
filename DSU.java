static class DSU {
    int n;
    int[] dsu;
    int[] sz;
    
    DSU(int n) {
        dsu = new int[n+1];
        sz = new int[n+1];
        for(int i=1; i<=n; i++) {
            dsu[i] = i;
            sz[i] = 1;
        }
    }
    
    void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);
        dsu[rootU] = rootV;
        sz[rootV] += sz[rootU];
    }
    
    int find(int u) {
        return (u==dsu[u]) ? u : (dsu[u] = find(dsu[u]));    
    } 
}
