// point update, range min
// Segment tree also works for non-invertible functions like min,max while fenwick does not

import java.io.*;
import java.util.*;
 
 
class SegmentTree {
    
    static int SZ = 100000;
    static int st[] = new int[8*SZ+10];
    
    static void update(int i, int pos, int ss, int se, int val) {                   
        if(ss>pos || se<pos) {return;}
        if(ss==se) {
            st[i]=val;
            return;
        }
        int mid = (ss+se)>>1;
        update(2*i, pos, ss, mid, val);
        update(2*i+1, pos, mid+1, se, val);
        st[i] = Math.min(st[2*i], st[2*i+1]);
    }
    
    static int query(int i, int ss, int se, int l, int r) {
        if(se<l || ss>r) return Integer.MAX_VALUE;
        if(ss>=l && se<=r) {
            return st[i];
        } 
        int mid = (ss+se)/2;
        int leftMin = query(2*i, ss, mid, l, r);
        int rightMin = query(2*i+1, mid+1, se, l, r);
        return Math.min(leftMin, rightMin);
    }
    
    
 
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();
        
        String inp[] = br.readLine().split(" ");
        int n = Integer.parseInt(inp[0]);
        int q = Integer.parseInt(inp[1]);
        inp = br.readLine().split(" ");
        
        for(int i=0; i<n; i++) {
            int val = Integer.parseInt(inp[i]);
            update(1, i, 0, n-1, val);
        }
        
        while(q-->0) {
            inp = br.readLine().split(" ");
            //update
            if(inp[0].equals("1")) {
                int k = Integer.parseInt(inp[1]);
                int u = Integer.parseInt(inp[2]);
                update(1, k-1, 0, n-1, u);
            }
            //query
            else {
                int a = Integer.parseInt(inp[1]);
                int b = Integer.parseInt(inp[2]);
                int minn = query(1, 0, n-1, a-1, b-1);
                out.append(String.valueOf(minn) + "\n");
            }
        }
        System.out.println(out);
    }
}
