static class Graph{
        
        private int n;
        
        private ArrayList<Integer>[] adj;
        
        public int[] child;
        
        Graph(int n) {
            this.n = n;
            adj = new ArrayList[n+1];
            child = new int[n+1];
            
            for(int i=0; i<=n; i++){
                adj[i] = new ArrayList<Integer>();
                child[i] = 0;
            }
        }
        
        void addEdge(int u, int v) {
            adj[u].add(v);
            adj[v].add(u);
        }
        
        void dfs(int u, int par) {
            child[u]++;
            
            for(int v: adj[u]) {
                if(v!=par) {
                    dfs(v, u);
                    child[u] += child[v];
                }
            }
        }            
    }
