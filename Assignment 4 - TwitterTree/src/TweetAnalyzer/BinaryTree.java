package TweetAnalyzer;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BinaryTree<E extends Comparable<E>> {

    protected Node root;
    private int size = 0;
    private boolean addReturn;
    private PriorityQueue<String> pq = new PriorityQueue<String>(new Comparator<String>() {
        public int compare(String lhs, String rhs) {
            if (find(new Words(rhs)).word.getTimes() > find(new Words(lhs)).word.getTimes()) {
                return +1;
            }
            if (find(new Words(rhs)).word.getTimes() < find(new Words(lhs)).word.getTimes()) {
                return -1;
            }
            return 0;
        }
    });

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(Words a) {
        root = new Node(a);
        size++;
    }

    public boolean isLeaf(Node localRoot) {
        return (localRoot.left == localRoot.right && localRoot.right == null);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        inOrder(root, sb);
        return sb.toString();
    }

    public String subtree(String w) {
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(find(new Words(w)), 1, sb);
        return sb.toString();
    }

    private void inOrder(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }

        inOrder(node.left, sb);
        sb.append(" " + node + " ");
        inOrder(node.right, sb);
    }

    public PriorityQueue GetPQ() {
        inOrder(root);
        return pq;
    }

    private void inOrder(Node node) {
        if (node == null) {
            return;
        }

        inOrder(node.left);
        pq.add(node.word.getWord());
        inOrder(node.right);
    }

    private void preOrderTraverse(Node node, int depth, StringBuilder sb) {

        if (node == null) {
        } else {
            for (int i = 1; i < depth; i++) {
                sb.append(" ");
            }
            sb.append(node.toString() + "\n");
            preOrderTraverse(node.left, depth + 1, sb);
            preOrderTraverse(node.right, depth + 1, sb);
        }
    }

    public boolean add(Words item) {
        root = add(root, item);
        size++;
        return addReturn;
    }

    private Node add(Node localRoot, Words item) {
        if (localRoot == null) {
            // item is not in the tree — insert it.
            addReturn = true;
            return new Node(item);
        } else if (item.getWord().compareTo(localRoot.word.getWord()) == 0) {
            // item is equal to localRoot.data
            addReturn = false;
            return localRoot;
        } else if (item.getWord().compareTo(localRoot.word.getWord()) < 0) {
            // item is less than localRoot.data
            localRoot.left = add(localRoot.left, item);
            return localRoot;
        } else {
            // item is greater than localRoot.data
            localRoot.right = add(localRoot.right, item);
            return localRoot;
        }
    }

    public void remove(Words item) {
        root = remove(root, item);
        size--;
    }

    private Node remove(Node localRoot, Words item) {
        if (localRoot == null) { //item not found
            return null;
        }

        int comparison = item.getWord().compareTo(localRoot.word.getWord());
        if (comparison < 0) { //item is less than localRoot
            localRoot.left = remove(localRoot.left, item);
            return localRoot;
        } else if (comparison > 0) {
            localRoot.right = remove(localRoot.right, item);
            return localRoot;
        } else {
            //the item is in the localroot
            if (isLeaf(localRoot)) {
                //if the local root has no children, delete it
                return null;
            } else if (localRoot.right == null && localRoot.left != null) {
                //if localRoot only has a left child
                return localRoot.left;
            } else if (localRoot.left == null && localRoot.right != null) {
                //if localRoot only has a right child
                return localRoot.right;
            } else {
                //localroot has two children
                Node current = localRoot.right;
                while (current.left != null) {
                    current = current.left;
                }

                Words temp = localRoot.word;
                localRoot.word = current.word;
                current.word = temp;

                localRoot.right = remove(localRoot.right, item);
                return localRoot;
            }
        }
    }

    public Node find(Words item) {
        Node node;
        node = find(item, root);
        return node;

    }

    private Node find(Words item, Node localRoot) {
        while (localRoot != null) {
            int comparison = item.getWord().compareTo(localRoot.word.getWord());
            if (comparison < 0) {
                localRoot = localRoot.left;
            } else if (comparison > 0) {
                localRoot = localRoot.right;
            } else { //found
                return localRoot;
            }
        }
        //no word found
        return null;
    }

    public int size() {
        return size;
    }

}
