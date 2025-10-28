import java.io.*;

/**
 * represents cannonical form function: ax+b (think as lazy function application on given segment)
 * incrementRange(val): (a,b) => (1, val)
 * setRange(val): (a,b) => (0, val)
 */

class Main {

    static class SegmentTree {
        public static class Node {
            long sum;
            long a;
            long b;
            Node() {
                this.sum = 0;
                this.a = 1;
                this.b = 0;
            }
        }

        // applying A,B of parent on top of (a,b) of child gives (A.a)x + (A.b+B)
        private void pushToChild(Node child, long A, long B) {
            child.a = A*child.a;
            child.b = A*child.b + B;
        }

        private void pushLazy(int i, int ss, int se) {
            if(tree[i].a==1 && tree[i].b==0) return;
            int len = se-ss+1;
            tree[i].sum = (tree[i].sum*tree[i].a) + len*tree[i].b;
            if(ss!=se) {
                pushToChild(tree[2*i+1], tree[i].a, tree[i].b);
                pushToChild(tree[2*i+2], tree[i].a, tree[i].b);
            }
            tree[i].a = 1;
            tree[i].b = 0;
        }

        private final int n;
        private final Node[] tree;

        SegmentTree(int n) {
            this.n = n;
            tree = new Node[4*n+10];
            for(int i=0; i<4*n+10; i++) tree[i]  = new Node();
        }

        private void update(int i, int ss, int se, int l, int r, int a, int b) {
            pushLazy(i, ss, se);
            if(ss>r || se<l) {return;}
            if(ss>=l && se<=r) {
                tree[i].a = a;
                tree[i].b = b;
                pushLazy(i, ss, se);
                return;
            }
            int mid = (ss+se)>>1;
            update(2*i+1, ss, mid, l, r, a, b);
            update(2*i+2, mid+1, se, l, r, a, b);
            tree[i].sum = tree[2*i+1].sum + tree[2*i+2].sum;
        }

        private long query(int i, int ss, int se, int l, int r) {
            pushLazy(i, ss, se);
            if(se<l || ss>r) return 0;
            if(ss>=l && se<=r) {
                return tree[i].sum;
            }
            int mid = (ss+se)>>1;
            long leftQuery = query(2*i+1, ss, mid, l, r);
            long rightQuery = query(2*i+2, mid+1, se, l, r);
            return leftQuery + rightQuery;
        }

        public void updateIncrement(int l, int r, int val) {
            update(0, 0, n-1, l, r, 1, val);
        }

        public void updateSet(int l, int r, int val) {
            update(0, 0, n-1, l, r, 0, val);
        }

        public long query(int l, int r) {
            return query(0, 0, n-1, l, r);
        }
    }


    public static void main(String args[]) throws IOException {
        Reader rd = new Reader();
        StringBuilder out = new StringBuilder();

        int n = rd.nextInt();
        int q = rd.nextInt();
        SegmentTree segmentTree  = new SegmentTree(n);
        for(int i=0; i<n; i++) {
            segmentTree.updateSet(i, i, rd.nextInt());
        }

        while(q-->0) {
            int type = rd.nextInt();
            //increase
            if(type==1) {
                int a = rd.nextInt();
                int b = rd.nextInt();
                int x = rd.nextInt();
                segmentTree.updateIncrement(a-1, b-1, x);
            }
            //set
            else if(type==2) {
                int a = rd.nextInt();
                int b = rd.nextInt();
                int x = rd.nextInt();
                segmentTree.updateSet(a-1, b-1, x);
            }
            //query
            else {
                int a = rd.nextInt();
                int b = rd.nextInt();
                out.append(segmentTree.query(a-1, b-1)).append("\n");
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
