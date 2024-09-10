import edu.princeton.cs.algs4.Point2D;

public class KdTree {
    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    private static class Node {
        public Point2D point;
        public boolean vertical;
        public Node left;
        public Node right;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }
}
