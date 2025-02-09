/**
use bellman ford
to print cycle, maintain a parent array which stores most optimsied parent for each node
get vertex optimised by cycle on nth iteration, it might be part of cycle or outside..but traversing parents will take us back to cycle formed 
**/
 
import java.io.*;
import java.util.*;
 
 
class Main {
    
    static int n;
    static int m;
    
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
    
    static void printCycle(int[] par, int vertexOptimisedByCyc) {
        if(vertexOptimisedByCyc==-1) {
            System.out.println("NO");
            return;
        }
        System.out.println("YES");
        ArrayList<Integer> popped = new ArrayList();
        Set<Integer> seen = new HashSet<Integer>();
        
        int cur = vertexOptimisedByCyc;
        
        while(!seen.contains(cur)) {
            popped.add(cur);
            seen.add(cur);
            cur = par[cur];
        }
        StringBuilder out = new StringBuilder();
        out.append((cur+1)+" ");
        for(int i=popped.size()-1; i>=0 && popped.get(i)!=cur; i--) {
            out.append((1+popped.get(i))+" ");
        }
        out.append((1+cur)+" ");
        System.out.println(out);
    }
    
    static void bellmanFord(Edge[] edges, int src) {
        long[] dist = new long[n];
        int[] par = new int[n];
        Arrays.fill(dist, 1_000_000_000_000_000L);
        dist[src] = 0;
        int vertexOptmisedByCyc = -1;
        
        for(int i=1; i<=n; i++) {
            for(Edge e: edges) {
                if(dist[e.to] > dist[e.from] + e.wgt) {
                    dist[e.to] = dist[e.from] + e.wgt;
                    par[e.to] = e.from;
                    if(i==n) {
                        vertexOptmisedByCyc = e.to;
                    }
                }
            }
        }
        printCycle(par, vertexOptmisedByCyc);
    }
    

 
    public static void main(String args[]) throws IOException {
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Reader r = new Reader();
        n = r.nextInt();
        m = r.nextInt();
        Edge[] edges = new Edge[m];
        
        for(int i=0; i<m; i++) {
            edges[i] = new Edge(r.nextInt()-1, r.nextInt()-1, r.nextInt());
        }
        
        bellmanFord(edges, 0);

    }
    
    
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
}
