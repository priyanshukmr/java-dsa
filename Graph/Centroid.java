// Problem: https://cses.fi/problemset/task/2080/
import java.io.*;
import java.util.*;



class Main {

    private static int k;

    static class Graph{
        private int n;
        private ArrayList<Integer>[] adj;
        private  int[] sub;
        private boolean[] vis;
        private int[] cnt;
        private int maxD;
        private long ans;

        Graph(int _n) {
            n = _n;
            adj = new ArrayList[n+1];
            sub = new int[n+1];
            vis = new boolean[n+1];
            cnt = new int[n+1];

            for(int i=0; i<=n; i++){
                adj[i] = new ArrayList<>();
            }
        }

        public void addEdge(int u, int v) {
            adj[u].add(v);
            adj[v].add(u);
        }

        private void dfsSub(int u, int p) {
            sub[u] = 1;
            for (int v : adj[u]) {
                if (!vis[v] && v != p) {
                    dfsSub(v, u);
                    sub[u] += sub[v];
                }
            }
        }

        private int findCentroid(int u, int p, int halfSize) {
            for (int v : adj[u])
                if (!vis[v] && v != p && sub[v] > halfSize)
                    return findCentroid(v, u, halfSize);
            return u;
        }

        private void dfs(int u, int p, int d, boolean isCalculate) {
            if (d > k) return;

            if (isCalculate) {
                ans += cnt[k-d];
            } else {
                cnt[d]++;
            }
            maxD = Math.max(maxD, d);

            for (int v : adj[u]) {
                if (!vis[v] && v != p) {
                    dfs(v, u, d + 1, isCalculate);
                }
            }
        }

        private void centroidDecompose(int u) {
            dfsSub(u, -1);
            int centroid = findCentroid(u, -1, sub[u]>>1);
            vis[centroid] = true;

            cnt[0] = 1;
            maxD = 0;
            for (int v : adj[centroid]) {
                if (!vis[v]) {
                    dfs(v, centroid, 1, true);   // count pairs crossing through centroid
                    dfs(v, centroid, 1, false);  // add this subtree's distances
                }
            }
            Arrays.fill(cnt, 0, maxD + 1, 0);

            for (int v : adj[centroid])
                if (!vis[v])
                    centroidDecompose(v);
        }
    }

    public static void main(String[] args) throws IOException {
        Reader rd = new Reader();
        int n = rd.nextInt();
        k = rd.nextInt();
        Graph graph = new Graph(n);

        for(int i=0; i<n-1; i++) {
            graph.addEdge(rd.nextInt(), rd.nextInt());
        }
        graph.centroidDecompose(1);
        System.out.println(graph.ans);
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
