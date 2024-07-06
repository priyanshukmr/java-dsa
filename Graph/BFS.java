import java.io.*;
import java.util.ArrayList;
import java.util.Queue;

class Graph {
    List<Integer> adj[];
    int nodes;
    
    Graph(int n) {
        this.nodes=n;
        adj = new ArrayList[n];
        for(int i=0; i<n; i++) {
            adj[i] = new ArrayList<>();
        }
    }
    
    void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }
    
    void BFS(int root) {
        Queue<Integer> q = new LinkedList<>();
        boolean vis[] = new boolean[nodes];
        q.add(root);
        vis[root] = true;
        
        while(!q.isEmpty()) {
            int front = q.poll();
            System.out.print(front+" ");
            
            for(int v: adj[front]) {
                if(!vis[v]) {
                    q.add(v);
                    vis[v] = true;
                }
            }
        }
    }
}

class Main {
    public static void main(String args[]) {
        Graph graph = new Graph(5);
        
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 4);
        
        graph.BFS(0);
    }
}

/*
0 1 2 3 4 
*/
