/**
Give a matrix with each cell representing elevation, a path value is defined as maximum elevation traversed along that path.
Movement is allowed in 4 directions. 
Find the minimum possible path value for all possible paths from 0,0 to n-1, m-1.

Eg. Matrix:

  1   1    1   1
100 100  100   1
  1   1    1   1 
100   5  100 100
  1   1    1   1

Output: 5
  
Editorial:

Note: Greedy algo to always choose smallest neighbour while traversing would not work. Why?

Approach-1:
(Binary search with DFS)
For a given path path value p, we can check if there exists a path in matrix with path value<=p.
This can be done by doing DFS traversal using only the nodes where elevation does not exceed p.
Binary search over p to find minimum such p.
O(nlogn)


Approach-2:
(Modified dijktras)
use priority queue



**/
import java.io.*;
import java.util.*;
 
 
class Main {
    static int[] dr = {-1, 0, +1, 0};
    static int[] dc = {0, +1, 0, -1};
    static int m = 5;
    static int n = 4;
    static boolean[][] vis = new boolean[m][n];
    
    static boolean valid(int r, int c) {
        return r>=0 && r<m && c>=0 && c<n;
    }
    
    static boolean possiblePath(int[][] matrix, int r, int c, int p) {
        if(r==m-1 && c==n-1 && matrix[r][c]<=p)
            return true;
        vis[r][c] = true;
        boolean possible = false;
        
        for(int i=0; i<4; i++) {
            int nextR = r+dr[i];
            int nextC = c+dc[i];
            if(valid(nextR, nextC) && !vis[nextR][nextC] && matrix[nextR][nextC]<=p) {
                possible = possible || possiblePath(matrix, nextR, nextC, p);
            }
        }
        return possible;
    }
    
    static void initVisFalse() {
        for(int i=0; i<m; i++) {
            for(int j=0; j<n; j++) {
                vis[i][j] = false;
            }
        }
    }
    
    static int binarySearchPath(int[][] matrix) {
        int left=1;
        int right=1000000000;
        
        
        while(right-left>1) {
            initVisFalse();
            int mid = (left+right)/2;
            System.out.println("mid="+mid);
            if(possiblePath(matrix, 0, 0, mid)) {
                right=mid;
            }
            else {
                left=mid;
            }
        }
        if(possiblePath(matrix, 0, 0, left)) return left;
        return right;
    }
 
    public static void main(String args[]) throws IOException {
        int[][] matrix = {
            {1,   1,    1,   1},
            {100, 100,  100,   1},
            {1,   1,    1,   1}, 
            {100,   5,  100, 100},
            {1,   1,    1,   1}
        };
        System.out.println(binarySearchPath(matrix));
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
