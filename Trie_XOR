class Solution {

    static class Node {
        Node[] children;

        Node() {
            children = new Node[2];
        }
    }

    static class Trie {
        Node root;
        
        Trie() {
            root = new Node();
        }

        void insert(int n) {
            Node current = root;
            for(int i=31; i>=0; i--) {
                int bit = ((1<<i)&n) > 0 ? 1: 0;
                if(current.children[bit]==null) {
                    current.children[bit] = new Node();
                }
                current = current.children[bit];
            }
        }

        int searchMax(int n) {
            Node current = root;
            int maxor = 0;
            for(int i=31; i>=0; i--) {
                int revBit = ((1<<i)&n) > 0 ? 0: 1;
                if(current.children[revBit]==null) 
                    revBit^=1;
                else 
                    maxor |= (1<<i);
                current = current.children[revBit];
            }
            return maxor;
        }
    }
    
    public int findMaximumXOR(int[] nums) {
        Trie trie = new Trie();
        int ans=0;
        for(int num: nums) {
            trie.insert(num);
            ans = Math.max(ans, trie.searchMax(num));
        }
        return ans;
    }
}
