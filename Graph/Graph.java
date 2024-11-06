import java.io.*;
import java.util.*;

class Main {
    
    static class Graph{
        int n;
        ArrayList<Integer>[] adj;
        
        Graph(int _n) {
            n = n;
            adj = new ArrayList[n+1];
            for(int i=0; i<=n; i++){
                adj[i] = new ArrayList<Integer>();
            }
        }
        
        void addEdge(int u, int v) {
            adj[u].add(v);
            adj[v].add(u);
        }
        
        void dfs(int u, int par) {            
            for(int v: adj[u]) {
                if(v!=par) {
                    dfs(v, u);
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
