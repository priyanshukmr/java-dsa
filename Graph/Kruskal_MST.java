import java.io.*;
import java.util.*;

class Main {
    
    static class Edge implements Comparable<Edge> {
        int wgt;
        int u;
        int v;
        
        Edge(int _u, int _v, int _wgt) {
            u = _u;
            v = _v;
            wgt = _wgt;
        }
        
        @Override
        public int compareTo(Edge other) {
            return this.wgt - other.wgt;
        }
    }
    
    static class DSU {
        int n;
        int[] dsu;
        int[] sz;
        int largest = 1;
        
        DSU(int n) {
            dsu = new int[n+1];
            sz = new int[n+1];
            for(int i=1; i<=n; i++) {
                dsu[i] = i;
                sz[i] = 1;
            }
        }
        
        void union(int u, int v) {
            int rootU = find(u);
            int rootV = find(v);
            dsu[rootU] = rootV;
            sz[rootV] += sz[rootU];
            largest = Math.max(largest, sz[rootV]);
        }
        
        int find(int u) {
            return (u==dsu[u]) ? u : (dsu[u] = find(dsu[u]));    
        }
        
    }
    
    static long kruskal(Edge[] edges, int n, int m) {
        Arrays.sort(edges);
        DSU dsu = new DSU(n);
        long cost = 0;
        int components = n;
        
        for(int i=0; i<m; i++) {
            int u = edges[i].u;
            int v = edges[i].v;
            int w = edges[i].wgt;
            if(dsu.find(u) != dsu.find(v)) {
                cost += w;
                dsu.union(u, v);
                components--;
            }
        }
        return components==1 ? cost : -1;
    }
    
    public static void main(String args[]) throws IOException {
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Reader r = new Reader();
        int n = r.nextInt();
        int m = r.nextInt();
        Edge[] edges = new Edge[m];
    
        
        for(int i=0; i<m; i++) {
            int u = r.nextInt();
            int v = r.nextInt();
            int w = r.nextInt();
            edges[i] = new Edge(u, v, w);
        }
        long reparationCost = kruskal(edges, n, m);
         
        System.out.println(reparationCost==-1 ? "IMPOSSIBLE" : reparationCost);
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
