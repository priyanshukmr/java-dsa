/**
Give a matrix with each cell representing elevation, a path value is defined as maximum elevation traversed along that path.
Movement is allowed in 4 directions. 
Find the minimum possible path value for all possible paths from 0,0 to n-1, m-1.

Eg. Matrix:

  1   1    1   1
100 100  100   1
  1   1    1   1 
100   5  100 100
  1   1    1   1

Output: 16 
  
Editorial:

Note: Greedy algo to always choose smallest neighbour while traversing would not work. Why?

Approach-1:
(Binary search with DFS)
For a given path path value p, we can check if there exists a path in matrix with path value<=p.
This can be done by doing DFS traversal using only the nodes where elevation does not exceed p.
Binary search over p to find minimum such p.
O(nlogn)


Approach-2:
(Modified dijktras)



**/
