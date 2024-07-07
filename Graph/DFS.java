import java.io.*;
import java.util.*;

class Graph {
    List<Integer> adj[];
    int nodes;
    public int cnt[];
    
    Graph(int n) {
        this.nodes=n;
        adj = new ArrayList[n];
        cnt = new int[n];
        for(int i=0; i<n; i++) {
            adj[i] = new ArrayList<>();
        }
    }
    
    void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }
    
    void DFS(int node, int parent, int level) {
        this.cnt[level]++;
        for(int v: adj[node]) {
            if(v!=parent)
            DFS(v, node, level+1);
        }
    }
}

class Main {
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Graph graph = new Graph(n);
        
        for(int i=0; i<n-1; i++) {
            String edges[] = br.readLine().split(" ");
            int u = Integer.parseInt(edges[0]);
            int v = Integer.parseInt(edges[1]);
            graph.addEdge(u-1, v-1);
        }
        int level = Integer.parseInt(br.readLine());
        graph.DFS(0, -1, 1);    
        System.out.println(graph.cnt[level]);
    }
}

// problem: https://www.hackerearth.com/practice/algorithms/graphs/breadth-first-search/tutorial/?purpose=login&source=practice&update=google
