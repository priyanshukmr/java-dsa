import java.io.*;
import java.util.*;

class Main {
    
    static class Graph{
        private int n;
        private ArrayList<Integer>[] adj;
        private int[] sub;

        Graph(int _n) {
            n = _n;
            adj = new ArrayList[n+1];
            sub = new int[n+1];
            for(int i=0; i<=n; i++){
                adj[i] = new ArrayList<Integer>();
            }
        }

        public void addEdge(int u, int v) {
            adj[u].add(v);
            adj[v].add(u);
        }

        private void dfs(int u, int p) {
            sub[u] = 1;
            for (int v : adj[u]) {
                if (v != p) {
                    dfs(v, u);
                    sub[u] += sub[v];
                }
            }
        }
    }

    public static void main(String args[]) throws IOException {
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Reader r = new Reader();
        int n = r.nextInt();
        int m = r.nextInt();
        Graph graph = new Graph(n);
        
        for(int i=0; i<m; i++) {
            int u = r.nextInt();
            int v = r.nextInt();
            graph.addEdge(u, v);     
        }
    }
}
