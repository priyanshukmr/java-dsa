/* Ref: https://cp-algorithms.com/graph/kuhn_maximum_bipartite_matching.html
 * Problem: https://cses.fi/problemset/task/1130/
 * O(V*E)
 */


import java.io.*;
import java.util.*;


class Main {

    static class Reader { 
        final private int BUFFER_SIZE = 1 << 16; 
        private DataInputStream din; 
        private byte[] buffer; 
        private int bufferPointer, bytesRead; 

        public Reader() 
        { 
            din = new DataInputStream(System.in); 
            buffer = new byte[BUFFER_SIZE]; 
            bufferPointer = bytesRead = 0; 
        } 

        public Reader(String file_name) throws IOException 
        { 
            din = new DataInputStream( 
                new FileInputStream(file_name)); 
            buffer = new byte[BUFFER_SIZE]; 
            bufferPointer = bytesRead = 0; 
        } 

        public String readLine() throws IOException 
        { 
            byte[] buf = new byte[64]; // line length 
            int cnt = 0, c; 
            while ((c = read()) != -1) { 
                if (c == '\n') { 
                    if (cnt != 0) { 
                        break; 
                    } 
                    else { 
                        continue; 
                    } 
                } 
                buf[cnt++] = (byte)c; 
            } 
            return new String(buf, 0, cnt); 
        } 

        public int nextInt() throws IOException 
        { 
            int ret = 0; 
            byte c = read(); 
            while (c <= ' ') { 
                c = read(); 
            } 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
            do { 
                ret = ret * 10 + c - '0'; 
            } while ((c = read()) >= '0' && c <= '9'); 

            if (neg) 
                return -ret; 
            return ret; 
        } 

        public long nextLong() throws IOException 
        { 
            long ret = 0; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
            do { 
                ret = ret * 10 + c - '0'; 
            } while ((c = read()) >= '0' && c <= '9'); 
            if (neg) 
                return -ret; 
            return ret; 
        } 

        public double nextDouble() throws IOException 
        { 
            double ret = 0, div = 1; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 

            do { 
                ret = ret * 10 + c - '0'; 
            } while ((c = read()) >= '0' && c <= '9'); 

            if (c == '.') { 
                while ((c = read()) >= '0' && c <= '9') { 
                    ret += (c - '0') / (div *= 10); 
                } 
            } 

            if (neg) 
                return -ret; 
            return ret; 
        } 

        private void fillBuffer() throws IOException 
        { 
            bytesRead = din.read(buffer, bufferPointer = 0, 
                                 BUFFER_SIZE); 
            if (bytesRead == -1) 
                buffer[0] = -1; 
        } 

        private byte read() throws IOException 
        { 
            if (bufferPointer == bytesRead) 
                fillBuffer(); 
            return buffer[bufferPointer++]; 
        } 

        public void close() throws IOException 
        { 
            if (din == null) 
                return; 
            din.close(); 
        } 
    }

    static class Graph {
        int n;
        ArrayList<Integer>[] adj;
        ArrayList<Integer> left;
        ArrayList<Integer> right;
        
        Graph(int n) {
            this.n = n;
            adj = new ArrayList[n+1];
            left = new ArrayList<>();
            for(int i=0; i<=n; i++) {
                adj[i] = new ArrayList<>();
            }
        }
        
        void addEdge(int u, int v) {
            adj[u].add(v);
            adj[v].add(u);
        }
        
        boolean tryAugmentingPath(int u, boolean[] vis, int[] match) {
            if(vis[u]) return false;
            vis[u] = true;
            
            for(int v: adj[u]) {
                // either v is unsaturated or match[v] found a augmented path
                if(match[v]==0 || tryAugmentingPath(match[v], vis, match)) {
                    match[v] = u;
                    return true;
                }
            }
            return false;
        }
        
        int kuhnMaxMatching() {
            int matchCount = 0;
            boolean[] vis = new boolean[n+1];
            int[] match = new int[n+1];
            
            for(int u: left) {
                Arrays.fill(vis, false);
                if(tryAugmentingPath(u, vis, match)) {
                    matchCount++;
                }
            }
            return matchCount;
        }
        
        void splitDfs(int u, int parent, int level) {
            if(level%2==0) {
                left.add(u);
            } 
            for(int v: adj[u]) {
                if(v!=parent) {
                    splitDfs(v, u, level+1);
                }
            }
        }
    }
    
    public static void main(String args[]) throws IOException {
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Reader r = new Reader();
        int n = r.nextInt();
        Graph graph = new Graph(n);
        
        for(int i=0; i<n-1; i++) {
            int u = r.nextInt();
            int v = r.nextInt();
            graph.addEdge(u, v);
        }
        graph.splitDfs(1, -1, 0);
        System.out.println(graph.kuhnMaxMatching());
    }

}
