import java.io.*;

public class Main {

    private static long MOD = 1000000007;

    private static long fastExp(long base, long exp) {
        long ans = 1L;
        while(exp>0L) {
            if((exp&1L)>0L) ans = (ans*base) % MOD;
            base = (base*base) % MOD;
            exp = exp>>1;
        }
        return ans;
    }

    private static long numberOfDivisors(long[] exp) {
        long count = 1L;

        for (int i = 0; i < exp.length; i++) {
            count = (count * (exp[i]+1L)) % MOD;
        }
        return count;
    }

    private static long productOfDivisors(long[] prime, long[] exp) {
        int n = prime.length;
        long[] lProd = new long[n];
        long[] rProd = new long[n];
        lProd[0] = (exp[0]+1) % (MOD-1);
        rProd[n-1] = (exp[n-1]+1) % (MOD-1);

        for (int i = 1; i < n; i++) {
            lProd[i] = (lProd[i-1] * (exp[i]+1)) % (MOD-1);
        }
        for (int i = n-2; i >=0; i--) {
            rProd[i] = (rProd[i+1] * (exp[i]+1)) % (MOD-1);
        }
        long productOfDiv = 1;

        for (int i = 0; i < n; i++) {
            long p1 = (i==0 ? 1L: lProd[i-1]) * (i==n-1 ? 1L : rProd[i+1]);
            p1 = p1 % (MOD-1);
            long p2 = (((exp[i]*(exp[i]+1))/2L) % (MOD-1));
            long power = (p1 * p2) % (MOD-1);
            long contribution = fastExp(prime[i], power);
            productOfDiv = (productOfDiv*contribution) % MOD;
        }
        return productOfDiv;
    }

    private static long sumOfDivisors(long[] prime, long[] exp) {
        long sum = 1L;
        for (int i = 0; i < prime.length; i++) {
            long numR = (fastExp(prime[i], exp[i]+1) - 1 + MOD) % MOD;
            long denomR = fastExp(prime[i]-1, MOD-2);
            long fraction = (numR*denomR) % MOD;
            sum = (sum * fraction) % MOD;
        }
        return sum;
    }


    public static void main(String[] args) throws IOException {
        Reader rd = new Reader();
        int n = rd.nextInt();
        long[] prime = new long[n];
        long[] exp = new long[n];

        for(int i=0; i<n; i++) {
            prime[i] = rd.nextLong();
            exp[i] = rd.nextLong();
        }
        System.out.print(numberOfDivisors(exp) + " ");
        System.out.print(sumOfDivisors(prime, exp) + " ");
        System.out.print(productOfDivisors(prime, exp) + " ");
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
            byte[] buf = new byte[3005]; // line length
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

