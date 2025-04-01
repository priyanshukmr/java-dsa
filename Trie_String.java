class Trie {

    static class Node {
        Node[] children;
        boolean isEnd;

        Node() {
            this.children = new Node[26];
        }
    }

    Node root;

    Trie() {
        this.root = new Node();
    }
    
    public void insert(String word) {
        Node current = root;
        for(int i=0; i<word.length(); i++) {
            int ch = word.charAt(i)-'a';
            if(current.children[ch]==null) {
                current.children[ch] = new Node();
            }
            current = current.children[ch]; 
        }
        current.isEnd = true;
    }
    
    public boolean search(String word) {
        Node current = root;
        for(int i=0; i<word.length(); i++) {
            int ch = word.charAt(i)-'a';
            if(current.children[ch]==null) {
                return false;
            }
            current = current.children[ch];
        }
        return current.isEnd;
    }
    
    public boolean startsWith(String prefix) {
        Node current = root;
        for(int i=0; i<prefix.length(); i++) {
            int ch = prefix.charAt(i)-'a';
            if(current.children[ch]==null) {
                return false;
            }
            current = current.children[ch];
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
