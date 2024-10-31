import java.io.*;
import java.util.*;



class Main {
    
    static class DistNode implements Comparable<DistNode> {
        long dist;
        int dest;
        DistNode(long dist, int dest) {
            this.dist = dist;
            this.dest = dest;
        }
        
        @Override
        public int compareTo(DistNode other) {
            if(this.dist < other.dist)
                return -1;
            if(this.dist > other.dist)
                return +1;
            return 0;
        }
    }
    
    static class Edge {
        int wgt;
        int dest;
        
        Edge(int wgt, int dest) {
            this.wgt = wgt;
            this.dest = dest;
        }
    }
    
    static class Graph {
        List<Edge> adj[];
        int nodes;

        Graph(int n) {
            this.nodes=n;
            adj = new ArrayList[n+1];
            for(int i=0; i<=n; i++) {
                adj[i] = new ArrayList<Edge>();
            }
        }

        void addEdge(int src, Edge edge) {
            adj[src].add(edge);
        }

        long[] dijktra(int src) {
            long[] dist = new long[this.nodes+1];
            Arrays.fill(dist, Long.MAX_VALUE);
            dist[1] = 0;
            PriorityQueue<DistNode> pq = new PriorityQueue<>();
            pq.add(new DistNode(0, src));
            
            while(!pq.isEmpty()) {
                DistNode closest = pq.remove();
                int u = closest.dest;
                
                for(Edge e: adj[u]) {
                    if(dist[u] + e.wgt < dist[e.dest]) {
                        dist[e.dest] = dist[u] + e.wgt;
                        pq.add(new DistNode(dist[e.dest], e.dest));
                    }
                }
            }
            return dist;
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
            int wgt = r.nextInt();
            graph.addEdge(u, new Edge(wgt, v));
        }
        long[] shortestDist = graph.dijktra(1);
        StringBuilder out = new StringBuilder();
        for(int i=1; i<=n; i++) {
            out.append(shortestDist[i] + " ");
        }
        System.out.println(out);
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
