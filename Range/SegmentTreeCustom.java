/**
 * Segment tree implementation with custom Node
 * Finds maximum subarray sum in range queries
 * 
 * Problem: https://cses.fi/problemset/task/3226/
 */

import java.io.*;

class Main {

    static class SegmentTree {

        public static class Node {
            long pre; //max prefix sum
            long suf; //max suffix sum
            long sub; //max subarray sum
            long sum; //sum of current segment

            Node(long pre, long suf, long sub, long sum) {
                this.pre = pre;
                this.suf = suf;
                this.sub = sub;
                this.sum = sum;
            }

            public void updateParent(Node left, Node right) {
                pre = Math.max(left.pre, left.sum+right.pre);
                suf = Math.max(right.suf, left.suf+right.sum);
                sum = left.sum + right.sum;
                sub = Math.max(left.sub, right.sub);
                sub = Math.max(sub, left.suf+right.pre);
            }
        }

        private final int n;
        private final Node[] tree;

        SegmentTree(int n) {
            this.n = n;
            tree = new Node[4*n+10];
            for(int i=0; i<4*n+10; i++) tree[i]  = new Node(0,0,0, 0);
        }

        private void update(int i, int ss, int se, int pos, long val) {
            if(ss>pos || se<pos) {return;}
            if(ss==se) {
                tree[i].pre = tree[i].suf = tree[i].sub = tree[i].sum = val;
                return;
            }
            int mid = (ss+se)>>1;
            update(2*i+1, ss, mid, pos, val);
            update(2*i+2, mid+1, se, pos, val);
            tree[i].updateParent(tree[2*i+1], tree[2*i+2]);
        }

        private Node query(int i, int ss, int se, int l, int r) {
            if(se<l || ss>r) return new Node(0,0,0,0);
            if(ss>=l && se<=r) {
                return tree[i];
            }
            int mid = (ss+se)/2;
            Node leftQuery = query(2*i+1, ss, mid, l, r);
            Node rightQuery = query(2*i+2, mid+1, se, l, r);
            Node current = new Node(0,0,0, 0);
            current.updateParent(leftQuery, rightQuery);
            return current;
        }

        public void update(int pos, int val) {
            update(0, 0, n-1, pos, val);
        }

        public long query(int l, int r) {
            return query(0, 0, n-1, l, r).sub;
        }
    }

    public static void main(String args[]) throws IOException {
        Reader rd = new Reader();
        StringBuilder out = new StringBuilder();

        int n = rd.nextInt();
        int q = rd.nextInt();
        SegmentTree segmentTree  = new SegmentTree(n);
        for(int i=0; i<n; i++) {
            segmentTree.update(i, rd.nextInt());
        }

        while(q-->0) {
            int a = rd.nextInt();
            int b = rd.nextInt();
            long submax = Math.max(0, segmentTree.query(a-1, b-1));
            out.append(submax).append("\n");
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
