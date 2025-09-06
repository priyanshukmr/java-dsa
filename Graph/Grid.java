import java.io.*;
import java.util.*;


public class Main {
    
    static class Grid {
        static String[] grid;
        static int rows;
        static int columns;
        static int[] dr = {-1, 0, +1, 0};
        static int[] dc = {0, +1, 0, -1};
        
        Grid(String[] _grid, int r, int c) {
            grid = _grid;
            rows = r;
            columns = c;
        }
        
        private boolean valid(int r, int c) {
            return r>=0 && r<rows && c>=0 && c<columns && grid[r].charAt(c)!='#';
        }
        
        private boolean dfs(int r, int c, int destR, int destC, boolean[][] vis) {
            if(r==destR && c==destC) return true; 

            for(int dir=0; dir<4; dir++) {
                int nextR = r+dr[dir];
                int nextC = c+dc[dir];
                if(valid(nextR, nextC) && !vis[nextR][nextC]) {
                    vis[nextR][nextC] = true;
                    if(dfs(nextR, nextC, destR, destC, vis)) return true;
                }
            }
            return false;
        }
        
        public boolean pathExists(List<Integer> src, List<Integer> dest) {
            boolean[][] vis = new boolean[rows][columns];
            vis[src.get(0)][src.get(1)] = true;
            return dfs(src.get(0), src.get(1), dest.get(0), dest.get(1), vis);
        }
    }
    
    public static void main(String args[]) throws IOException {
        String[] g = {
            "S.#.D",
            "#...#"
        };
        
        Grid grid = new Grid(g, 2, 5);
        System.out.println(grid.pathExists(List.of(0, 0),  List.of(0, 4)));
    }
}
    
    
    
    
    
    
