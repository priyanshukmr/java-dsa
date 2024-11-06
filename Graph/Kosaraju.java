import java.io.*;
import java.util.*;

class Main {

    static class Graph{
        int n;
        ArrayList<Integer>[] adj;
        ArrayList<Integer>[] adj_rev;
        ArrayList<Integer> topo;
        boolean[] vis;
                
        Graph(int _n) {
            n = _n;
            adj = new ArrayList[n+1];
            adj_rev = new ArrayList[n+1];
            topo = new ArrayList<>();
            vis = new boolean[n+1];
            for(int i=0; i<=n; i++) {
                adj[i] = new ArrayList<Integer>();
                adj_rev[i] = new ArrayList<Integer>();
            } 
        }
        
        void addEdge(int u, int v) {
            adj[u].add(v);
        }
        
        void addRevEdge(int u, int v) {
            adj_rev[u].add(v);
        }
        
        void dfs(int u) {
            vis[u] = true;
            for(int v: adj[u]) {
                if(!vis[v]) {
                    dfs(v);
                }
            }
           topo.add(u);
        }  
        
        void dfs_rev(int u) {
            vis[u] = true;
            for(int v: adj_rev[u]) {
                if(!vis[v]) {
                    dfs_rev(v);
                }
            }
        }  
        
        ArrayList<Integer> kosaraju() {
            ArrayList<Integer> scc = new ArrayList<>();
            for(int i=1; i<=n; i++) {
                if(!vis[i])
                    dfs(i);
            }
            Arrays.fill(vis, false);
            for(int i=n-1; i>=0; i--) {
                int u = topo.get(i);
                if(!vis[u]) {
                    dfs_rev(u);
                    scc.add(u);
                }
            }
            return scc;
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
            graph.addRevEdge(v, u);  
        }
        ArrayList<Integer> scc = graph.kosaraju();
        if(scc.size() == 1) {
            System.out.println("YES");
            return;
        }
        System.out.println("NO");
        int scc_len = scc.size();
        System.out.println(scc.get(scc_len-1) + " " + scc.get(scc_len-2));
    }
    
    
    /* Reader class for fast-read GFG */
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
            byte[] buf = new byte[1000002]; // line length 
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
}
