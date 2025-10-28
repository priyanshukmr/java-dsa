import java.io.*;
import java.util.*;



class Main {

    public static class Query {
        private int L;
        private int R;
        private int idx;

        public Query(int L, int R, int idx) {
            this.L = L;
            this.R = R;
            this.idx = idx;
        }
    }

    static class MosAlgo {
        private int SQRT;
        private int n;
        private int q;
        private int[] nums;
        int[] freq;
        private int ans;

        private int[] compressInput(int[] nums) {
            int[] compressed = new int[nums.length];
            List<List<Integer>> copy = new ArrayList<>();
            for(int i=0; i<n; i++) {
                copy.add(List.of(nums[i], i));
            }
            Collections.sort(copy, (a, b)->a.get(0)-b.get(0));
            int id = 0;
            compressed[copy.get(0).get(1)] = id;
            for(int i=1; i<n; i++) {
                if(!copy.get(i).get(0).equals(copy.get(i-1).get(0))) id++;
                compressed[copy.get(i).get(1)] = id;
            }
            return compressed;
        }

        MosAlgo(int n, int q, int[] nums) {
            this.n = n;
            SQRT = (int)Math.sqrt(n*1.0);
            freq = new int[n+10];
            this.q = q;
            this.nums = compressInput(nums); // change this line if compression not needed
        }

        private void add(int pos) {
            freq[nums[pos]]++;
            if(freq[nums[pos]]==1)
                ans++;
        }

        void remove(int pos) {
            freq[nums[pos]]--;
            if(freq[nums[pos]]==0)
                ans--;
        }

        private void sortQueries(Query[] queries) {
            Arrays.sort(queries, (a, b) -> {
                if (a.L/SQRT != b.L/SQRT)
                    return a.L - b.L;
                return a.R - b.R;
            });
        }

        public int[] processQueries(Query[] queries) {
            sortQueries(queries);
            int[] queryAns = new int[q];
            int currentL=0, currentR=0;
            add(0);

            for(int i=0; i<q; i++){
                int qL=queries[i].L;
                int qR=queries[i].R;

                while(currentL < qL)
                    remove(currentL++);

                while(currentL > qL)
                    add(--currentL);

                while(currentR < qR)
                    add(++currentR);

                while(currentR > qR)
                    remove(currentR--);

                queryAns[queries[i].idx] = ans;
            }
            return queryAns;
        }
    }

    public static void main(String[] args) throws IOException {
        Reader rd = new Reader();
        StringBuilder out = new StringBuilder();
        int n = rd.nextInt();
        int q = rd.nextInt();
        int[] nums = new int[n];
        Query[] queries = new Query[q];

        for (int i = 0; i < n; i++) {
            nums[i] = rd.nextInt();
        }
        for (int i = 0; i < q; i++) {
            queries[i] = new Query(rd.nextInt()-1, rd.nextInt()-1, i);
        }
        MosAlgo mosAlgo = new MosAlgo(n, q, nums);
        int[] queryAns = mosAlgo.processQueries(queries);
        for (int i = 0; i < q; i++) {
            out.append(queryAns[i]).append("\n");
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
