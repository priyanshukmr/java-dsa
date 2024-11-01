import java.io.*;
import java.util.*;


class Main {
    
    /* KMP algo O(n+m) */
    static int[] constructLps(String pattern) {
        int m = pattern.length();
        int[] LPS = new int[m];
    
        int curPreSuf = 0;
        LPS[0]=0;
        
        for(int i=1; i<m; i++){
            if(pattern.charAt(i) == pattern.charAt(curPreSuf)) {
                curPreSuf++;
                LPS[i] = curPreSuf;
                continue;
            }
            while(curPreSuf>0 && pattern.charAt(i)!=pattern.charAt(curPreSuf)) {
                curPreSuf = LPS[curPreSuf-1];
            }
            if(pattern.charAt(i) == pattern.charAt(curPreSuf)) curPreSuf++;
            LPS[i] = curPreSuf;
        }
        return LPS;
    }
    
    static Integer matchUsingKmp(String pattern, String text) {
        int[] LPS = constructLps(pattern);
        int matchSz = 0;
        int ans=0;
        
        for(int i=0; i<text.length(); i++) {
            if(text.charAt(i) == pattern.charAt(matchSz)) {
                matchSz++;
                if(matchSz==pattern.length()) {
                    ans++;
                    matchSz = LPS[matchSz-1];
                }
                continue;
            }
           
            while(matchSz>0 && text.charAt(i)!=pattern.charAt(matchSz)) {
                matchSz = LPS[matchSz-1];
            }
            if(text.charAt(i) == pattern.charAt(matchSz)) matchSz++;
        }
        return ans;
    }
    
    /* Rabin Karp O(n+m) */
    
    static long  prime1 = 293;
    static long  prime2 = 991;
    static long M = (long)1e9+7;
    
    static long hash(long[] pattern, long prime) {
        long hash=0;
        long pow = 1;
        for(int i=pattern.length-1; i>=0; i--) {
            hash = (hash + pow*(pattern[i])) % M;
            pow = (pow*prime) % M;
        }
        return hash;
    }
    
    static long[] toIntArray(String ss) {
        long[] intArr = new long[ss.length()];
        for(int i=0; i<ss.length(); i++) {
            intArr[i] = ss.charAt(i)-'0';
        }
        return intArr;
    }
    
    static int matchUsingRabinKarp(String _pattern, String _text) {
        long[] pattern = toIntArray(_pattern);
        long[] text = toIntArray(_text);
        
        int n = text.length;
        int m = pattern.length;
        if(m>n) return 0;
    
        long patternHash1 = hash(pattern, prime1);
        long patternHash2 = hash(pattern, prime2);
        
        long windowHash1 = 0;
        long windowHash2 = 0;
        long pow1 = 1;
        long pow2 = 1;
        int ans = 0;
        
        for(int i=m-1; i>=0; i--) {
            windowHash1 = (windowHash1 + pow1*(text[i])) % M;
            if(i>0)
            pow1 = (pow1*prime1)%M;
            windowHash2 = (windowHash2 + pow2*(text[i])) % M;
            if(i>0)
            pow2 = (pow2*prime2)%M;
        }
        
        for(int i=m; i<=n; i++) {
            if(windowHash1 == patternHash1) ans++;
            if(i==n) break;
            
            windowHash1 = ( (windowHash1 - text[i-m]*pow1)*prime1+ text[i] )%M;
            if(windowHash1<0) windowHash1 += M;
            
            windowHash2 = ( (windowHash2 - text[i-m]*pow2)*prime2+ text[i] )%M;
            if(windowHash2<0) windowHash2 += M;
        }
        return ans;
    }

    public static void main(String args[]) throws IOException {
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Reader r = new Reader();
        String text = r.readLine();
        String pattern = r.readLine();
        System.out.println(matchUsingRabinKarp(pattern, text));
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
            byte[] buf = new byte[1000005]; // line length 
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
