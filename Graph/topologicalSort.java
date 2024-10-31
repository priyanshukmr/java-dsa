import java.io.*;
import java.util.*;



class Main {
    
    static class Graph {
        List<Integer> adj[];
        int nodes;
        ArrayList<Integer> topo;
        boolean topoPossible = true;
        boolean[] vis;
        boolean[] instack;

        Graph(int n) {
            this.nodes=n;
            adj = new ArrayList[n+1];
            topo = new ArrayList<>();
            vis = new boolean[n+1];
            instack = new boolean[n+1];
            indegree = new int[n+1];
            for(int i=0; i<=n; i++) {
                adj[i] = new ArrayList<Integer>();
            }
        }

        void addEdge(int u, int v) {
            adj[u].add(v);
        }

        void topoDfs(int u) {
            vis[u] = true;
            instack[u] = true;
            
            for(int v: adj[u]) {
                if(!vis[v]) {
                    topoDfs(v);
                }
                else if(instack[v]) {
                    topoPossible = false;
                }
            }
            topo.add(u);
            instack[u]=false;
        }
        
        /* Kahn's algo, can be used to choose topo order like lexicographically */
        int[] indegree;
        
        ArrayList<Integer> topoBfs() {
            ArrayList<Integer> topo = new ArrayList<>();
            ArrayDeque<Integer> queue = new ArrayDeque<>();
            
            for(int i=1; i<=nodes; i++)
                if(indegree[i]==0) 
                    queue.add(i);
            
            while(!queue.isEmpty()) {
                int front = queue.removeFirst();
                topo.add(front);
                
                for(int v: adj[front]) {
                    indegree[v]--;
                    if(indegree[v]==0) 
                        queue.add(v);
                }
            }
            return topo;
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
            graph.indegree[v]++;
        }
        StringBuilder out = new StringBuilder();
        
        ArrayList<Integer>  topo = graph.topoBfs();
        if(topo.size() < n) {
            System.out.println("IMPOSSIBLE");
            return;
        }
        for(int u: topo) {
            out.append(u + " ");
        }
        System.out.println(out);
        
        /*for topoDfs code*/
//         for(int i=1; i<=n; i++) {
//             if(!graph.vis[i])
//                 graph.topoDfs(i);
//         }
//         if(!graph.topoPossible) {
//             System.out.println("IMPOSSIBLE");
//             return;
//         }
        
//         Collections.reverse(graph.topo);
        
//         for(int u: graph.topo) {
//             out.append(u + " ");
//         }
//         System.out.println(out);
        
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
            byte[] buf = new byte[1002]; // line length 
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
