    static class Edge {
        int from;
        int to;
        int wgt;
        
        Edge(int from, int to, int wgt) {
            this.from = from;
            this.to = to;
            this.wgt = wgt;
        }
    }
    
    static class Graph {
        ArrayList<Edge>[] adj;
        boolean[] vis;
        int n;
        int m;
        
        Graph(int n, int m) {
            this.n = n;
            this.m = m;
            adj = new ArrayList[n];
            vis = new boolean[n];
            for(int i=0; i<n; i++) {
                adj[i] = new ArrayList<>();
            }
        }
        
        void addEdge(int from, int to, int wgt) {
            adj[from].add(new Edge(from,to,wgt));
        }       
    }
