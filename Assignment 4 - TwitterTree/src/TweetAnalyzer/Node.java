package TweetAnalyzer;

import java.io.Serializable;

public class Node implements Serializable{
    Words word;
    Node left;
    Node right;

    public Node(Words word) {
        this.word = word;
        this.left = null;
        this.right = null;
    }
    
    public String toString(){
        return word.getWord();
    }
    
    
}
