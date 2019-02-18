package TweetAnalyzer;

import java.util.*;

public class Huffman {

    //node for huffman
    public class HNode {

        public Words word = null;
        public int weight;
        public HNode left = null;
        public HNode right = null;
        public String code = "";

        public HNode() {
        }

        public boolean hasWord() {
            if (word == null) {
                return false;
            } else {
                return true;
            }
        }

        public boolean isLeaf() {
            return (left == null && right == null);
        }
    }

    //declarations
    protected HNode root;
    private BinaryTree a;
    private LinkedList<HNode> ll = new LinkedList();
    private PriorityQueue<Integer> hpq = new PriorityQueue();
    private HNode found;

    //constructor
    public Huffman(BinaryTree a) {
        this.a = a;

        inOrder(a.root);
        buildTree();
        preorder(root, "");
    }

    public void buildTree() {
        if (ll.size() == 1) {
            root = ll.get(0);
            return;
        }
        HNode n = new HNode();
        n.right = ll.get(0);
        n.right.code += "1";
        ll.remove();

        n.left = ll.get(0);
        n.left.code += "0";
        ll.remove();

        n.weight = n.left.weight + n.right.weight;
        ll.add(n);
        selectionSort();
        buildTree();
    }

    private void inOrder(Node node) {
        if (node == null) {
            return;
        }

        inOrder(node.left);
        HNode n = new HNode();
        n.word = node.word;
        n.weight = n.word.getTimes();
        ll.add(n);
        selectionSort();
        inOrder(node.right);
    }

    //sort linked list
    private void selectionSort() {
        int i = 0;
        if (ll.size() == 1) {
            return;
        }
        while (i < ll.size() - 2 && ll.get(i).weight > ll.get(ll.size() - 1).weight) {
            i++;
        }
        ll.add(i, ll.get(ll.size() - 1));
        ll.remove(ll.size() - 1);
    }

    //code the huffman tree
    private void preorder(HNode n, String c) {
        if (n == null) {
            return;
        }
        n.code += c;
        preorder(n.right, n.code);
        preorder(n.left, n.code);
    }

    //decode huffman tree
    public String decode(String item) {
        found = null;
        find(item, root);
        if (found == null) {
            return "Not found";
        } else {
            return reverse(found.code);
        }

    }

    private void find(String w, HNode node) {
        if (node == null) {
            return;
        }
        find(w, node.left);
        if (node.hasWord()) {
            if (node.word.getWord().compareTo(w) == 0) {
                found = node;
            }
        }
        find(w, node.right);

    }

    private String reverse(String s) {
        String ns = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            ns += s.charAt(i);
        }
        return ns;
    }
}
