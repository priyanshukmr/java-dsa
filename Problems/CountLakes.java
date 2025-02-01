/**

Given a matrix representation of islands in ocean, count the number of lakes inside given island cordinates.
Land is denoted by *
Water is denote by -

Example:
-----*------------------------
----*-*--**-----*-*------*----
-----*---**----*-*-*----*-*---
----------------*-*-----***---
-----********-----------*-*---
-----*---*--*-------**-*--*---
-----********------*--*---*---
-------------------********---

1,9 -> 0 lakes
0,5 -> 1 lake
1,4 -> 1 lake
1,16 -> 2 lakes
2,17 -> 2 lakes
5,5 -> 2 lakes
6,5 -> 2 lakes
7,20 -> 3 lakes
1,25 -> 3 lakes

Input: 
8 30
-----*------------------------
----*-*--**-----*-*------*----
-----*---**----*-*-*----*-*---
----------------*-*-----***---
-----********-----------*-*---
-----*---*--*-------**-*--*---
-----********------*--*---*---
-------------------********---
9
1 9
0 5
1 4
1 16
2 17
5 5
6 5
7 20
1 25

Output: 
0 1 1 2 2 2 2 3 3

Editorial: 
Go through perimeter and start DFS from any ocean cell to mark whole ocean visited. If there is no such cell, 
then whole map is an island, which will be taken care implicitly.
Go through each land and start a DFS from unvisited land. Also assign an id to each connected component 
corresponding to which we will store the lake count. When encounter a lake, increment count for that id, start another DFS for water cell till whole lake is visited. Repeat until all land is visited.
On each incoming query, output lakes for that connected component id. 

**/

import java.io.*;
import java.util.*;

 
class Main {
    static int[] dr4 = {-1, 0, +1, 0};
    static int[] dc4 = {0, +1, 0, -1};
    static int[] dr8 = {-1, -1, 0, +1, +1, +1, 0, -1};
    static int[] dc8 = {0, +1, +1, +1, 0, -1, -1, -1};
    static int n;
    static int m;
    static char[][] grid;
    static boolean[][] vis;
    static int id;
    static int[][] idOf;
    static int[] count;
    
    static boolean valid(int r, int c) {
        return r>=0 && r<n && c>=0 && c<m;
    }

    static void waterDFS(int r, int c) {
        vis[r][c] = true;
        
        for(int i=0; i<4; i++) {
            int nextR = r + dr4[i];
            int nextC = c + dc4[i];
            if(valid(nextR, nextC) && !vis[nextR][nextC] && grid[nextR][nextC]=='-') {
                waterDFS(nextR, nextC);
            }
        }
    }
    
    static void traverseOcean() {
        for(int r=0; r<n; r++) {
            if(!vis[r][0]) {
                waterDFS(r,0);
                return;
            }
            if(!vis[r][m-1]) {
                waterDFS(r,m-1);
                return;
            }
        }
        for(int c=0; c<m; c++) {
            if(!vis[0][c]) {
                waterDFS(0, c);
                return;
            }
            if(!vis[n-1][c]) {
                waterDFS(n-1,c);
                return;
            }
        }
    }
    
    static void landDFS(int r, int c, int id) {
        idOf[r][c] = id;
        vis[r][c] = true;
        
        for(int i=0; i<8; i++) {
            int nextR = r + dr8[i];
            int nextC = c + dc8[i];
            if(valid(nextR, nextC) && !vis[nextR][nextC] && grid[nextR][nextC]=='*') {
                landDFS(nextR, nextC, id);
            }
            else if(valid(nextR, nextC) && !vis[nextR][nextC] && grid[nextR][nextC]=='-') {
                count[id]++;
                waterDFS(nextR, nextC);
            }
        }
    }
    
 
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inp = br.readLine().split(" ");
        n = Integer.parseInt(inp[0]);
        m = Integer.parseInt(inp[1]);
        grid = new char[n][m];
        vis = new boolean[n][m];
        idOf = new int[n][m];
        count = new int[n*m];
        
        for(int i=0; i<n; i++) {
            grid[i] = br.readLine().toCharArray();
        }
        traverseOcean();
        
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(grid[i][j]=='*' && !vis[i][j]) {
                    landDFS(i, j, id++);
                }
            }
        }
        
        int q = Integer.parseInt(br.readLine());
        
        while(q-->0) {
            inp = br.readLine().split(" ");
            int qr = Integer.parseInt(inp[0]);
            int qc = Integer.parseInt(inp[1]);
            int componentId = idOf[qr][qc];
            System.out.print(count[componentId]+" ");
        }
    }
}
