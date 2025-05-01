/*
https://www.hackerrank.com/challenges/heavy-light-white-falcon/problem
HLD finding max value on a path

For path distance HLD see: https://leetcode.com/problems/shortest-path-in-a-weighted-tree/
*/

import java.io.*;
import java.util.*;
 
 
public class Main {
    
    static class Node {
        int mx, ss, se;
        Node left, right; 
        
        Node(int ss, int se) {
            this.ss=ss;
            this.se=se;
            this.mx = 0;
        }
    }
    
    static class SegTree {
        Node root;
        
        SegTree(int l, int r) {
            this.root = build(l, r);
        }
        
        public void update(int idx, int val) {
            this.update(root, idx, val);
        }
        
        private void update(Node node, int idx, int val) {
            if(node.ss==node.se) {
                node.mx = val;
                return;
            }
            int mid = (node.ss+node.se)/2;
            if(idx<=mid)
                update(node.left, idx, val);
            else
                update(node.right, idx, val);
            node.mx = Math.max(node.left.mx, node.right.mx);
        }
        
        public int query(int ql, int qr) {
            return query(root, ql, qr);
        }
        
        private int query(Node node, int ql, int qr) {
            if(qr<node.ss || ql>node.se) return 0;
            if(node.ss>=ql && node.se<=qr) return node.mx;
            int lmax = query(node.left, ql, qr);
            int rmax = query(node.right, ql, qr);
            return Math.max(lmax, rmax);
        }
        
        private Node build(int ss, int se) {
            Node node = new Node(ss, se);
            if(ss<se) {
                int mid = (ss+se)/2;
                node.left = build(ss, mid);
                node.right = build(mid+1, se);
            }
            return node;
        }
    }
    
    static class Graph {
        int n;
        List<Integer>[] adj;
        
        Graph(int n) {
            this.n = n;
            par = new int[n];
            size = new int[n];
            depth = new int[n];
            pos = new int[n];
            root = new SegTree[n];
            adj = new ArrayList[n];
            chain = new ArrayList[n];
            chainIdx = new int[n];
            for(int i=0; i<n; i++) {
                adj[i] = new ArrayList<>();
                chain[i] = new ArrayList<>();
            }
        }
        
        int[] par, size, depth, pos;
        
        public void addEdge(int u, int v) {
            adj[u].add(v);
            adj[v].add(u);
        }
        
        public void dfsBuild(int u, int p, int d) {
            par[u] = p;
            depth[u] = d;
            size[u] = 1;
            
            for(int v: adj[u]) {
                if(v!=p) {
                    dfsBuild(v, u, d+1);
                    size[u]+=size[v];
                }
            }
        }
        
        List<Integer>[] chain;
        int[] chainIdx;
        SegTree[] root;
        
        private void HLD_build(int n) {
            chain[n].add(n);
            for(int i=0; i<chain[n].size(); i++) {
                int u = chain[n].get(i);
                chainIdx[u] = n;
                pos[u] = i;
                for(int v: adj[u])
                    if(v!=par[u]) {
                        if (2*size[v] >= size[u])
                            chain[n].add(v);
                        else 
                            HLD_build(v);
                    }
            }
            root[n] = new SegTree(0, chain[n].size()-1);
        }
        
        public void HLD_build() {
            dfsBuild(0, 0, 0);
            HLD_build(0);
        }
        
        public int HLD_query(int u, int v) {
            int ans = 0;
            while(chainIdx[u]!=chainIdx[v]) {
                if(depth[chainIdx[u]] > depth[chainIdx[v]]) {
                    int tmp = u;
                    u=v;
                    v=tmp;
                }
                ans = Math.max(ans, root[chainIdx[v]].query(0, pos[v]));
                v = par[chainIdx[v]];
            }
            if(pos[u]>pos[v]) {
                int tmp = u;
                u = v;
                v = tmp;
            }
            return Math.max(ans, root[chainIdx[u]].query(pos[u], pos[v]));
        }
        
        public void HLD_update(int u, int x) {
            root[chainIdx[u]].update(pos[u], x);
        }
    }
 
    public static void main(String args[]) throws IOException {
        Reader r = new Reader();
        int n = r.nextInt();
        int q = r.nextInt();
        Graph graph = new Graph(n);
        
        for(int i=0; i<n-1; i++) {
            graph.addEdge(r.nextInt(), r.nextInt());
        }
        graph.HLD_build();
        StringBuilder out = new StringBuilder();
        
        while(q-->0) {
            int type = r.nextInt();
            
            if(type==1) {
                int u = r.nextInt();
                int x = r.nextInt();
                graph.HLD_update(u, x);
            }
            else {
                int u = r.nextInt();
                int v = r.nextInt();
                out.append(graph.HLD_query(u, v)).append("\n");
            }
        }
        System.out.print(out);
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
