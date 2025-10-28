// point update, range sum

import java.io.*;
import java.util.*;


public class Main {
    
    static class Fenwick {
        int n;
        long[] tree;
        
        Fenwick(int n) {
            this.n = n;
            tree = new long[n+1];
        }
        
        long query(int i) {
            long sum = 0;
            for( ; i>0; i-=i&-i)
                sum += tree[i];
            return sum;
        }
        
        void update(int i, long val) {
            for( ; i<=n; i+=i&-i)
                tree[i] += val;
        }
        
        long queryRange(int l, int r) {
            return query(r) - query(l-1);
        }
    } 
    
    public static void main(String args[]) throws IOException {
        Reader r = new Reader();
        int n = r.nextInt();
        int q = r.nextInt();
        
        Fenwick fenwick = new Fenwick(n);
        
        for(int i=1; i<=n; i++) {
            int val = r.nextInt();
            fenwick.update(i, val);
        }
        
        StringBuilder out = new StringBuilder();
        
        while(q-->0) {
            int type = r.nextInt();
            
            if(type==1) {
                int k = r.nextInt();
                int u = r.nextInt();
                long kthVal = fenwick.queryRange(k, k);
                fenwick.update(k, -kthVal);
                fenwick.update(k, +u);
            }
            if(type==2) {
                int a = r.nextInt();
                int b = r.nextInt();
                out.append(fenwick.queryRange(a, b)).append("\n");
            }
        }
        System.out.println(out);
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
            byte[] buf = new byte[1005]; // line length 
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
