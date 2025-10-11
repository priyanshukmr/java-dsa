static class Graph{
    int n;
    List<Integer>[] adj;
    
    Graph(int _n) {
        n = _n;
        adj = new ArrayList[n+1];
        for(int i=0; i<=n; i++){
            adj[i] = new ArrayList<Integer>();
        }
    }
    
    void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }
    
    public boolean dfs(int u, int color, int[] colors) { 
        colors[u] = color;
        for(int v: adj[u]) {
            if(colors[v]==colors[u]) {
                return false;
            }
            else if(colors[v]==0 && !dfs(v, color==1? 2: 1, colors)) {
                return false;
            }
        }
        return true;
    }

    public boolean isBipartite() {
        int[] colors = new int[n+1];
        for(int i=0; i<n; i++) {
            if(colors[i]==0) {
                if(!dfs(i, 1, colors)) return false;
            }
        }
        return true;
    }            
}
