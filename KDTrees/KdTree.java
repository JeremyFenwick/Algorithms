import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    private static class Node {
        public Point2D data;
        public boolean vertical;
        public Node left;
        public Node right;
        public RectHV area;

        public Node(Point2D point, boolean isVertical, RectHV rectangle) {
            data = point;
            vertical = isVertical;
            area = rectangle;
        }
    }

    // Helper function to determine if we need to go left or right
    private boolean goRight(Node node, Point2D point) {
        if (node.vertical) {
            return point.x() >= node.data.x();
        }
        else {
            return point.y() >= node.data.y();
        }
    }

    private void createNewNode(Node parent, Point2D point) {
        // If there is no parent the tree must be empty
        if (parent == null) {
            root = new Node(point, true, new RectHV(0, 0, 1, 1));
            size++;
            return;
        }
        var goRight = goRight(parent, point);
        // Right node, parent is a vertical
        if (goRight && parent.vertical) {
            var rectangle = new RectHV(parent.data.x(), parent.area.ymin(), parent.area.xmax(), parent.area.ymax());
            parent.right = new Node(point, false, rectangle);
        }
        // Left node, parent is a vertical
        else if (!goRight && parent.vertical) {
            var rectangle = new RectHV(parent.area.xmin(), parent.area.ymin(), parent.data.x(), parent.area.ymax());
            parent.left = new Node(point, false, rectangle);
        }
        // Right node, parent is a horizontal
        else if (goRight) {
            var rectangle = new RectHV(parent.area.xmin(), parent.data.y(), parent.area.xmax(), parent.area.ymax());
            parent.right = new Node(point, true, rectangle);
        }
        // Left node, parent is a horizontal
        else {
            var rectangle = new RectHV(parent.area.xmin(), parent.area.ymin(), parent.area.xmax(),  parent.data.y());
            parent.left = new Node(point, true, rectangle);
        }
        size++;
    }


    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D newPoint) {
        var workingNode = root;
        Node parentNode = null;

        // Identify the correct parent node for insertion
        while (workingNode != null) {
            // Determine if the node already exists
            if (workingNode.data.equals(newPoint)) {
                return;
            }
            // Make the working node the parent
            parentNode = workingNode;
            // Determine if we need to traverse left or right
            var goRight = goRight(workingNode, newPoint);
            if (goRight) {
                workingNode = workingNode.right;
            }
            else {
                workingNode = workingNode.left;
            }
        }

        createNewNode(parentNode, newPoint);
    }

    public boolean contains(Point2D target) {
        var workingNode = root;
        while (workingNode != null) {
            if (workingNode.data.equals(target)) {
                return true;
            }
            var goRight = goRight(workingNode, target);
            if (goRight) {
                workingNode = workingNode.right;
            }
            else {
                workingNode = workingNode.left;
            }
        }
        return false;
    }

    public Iterable<Point2D> range(RectHV rectangle) {
        var resultArray = new ArrayList<Point2D>();
        var queue = new ArrayDeque<Node>();
        queue.add(root);

        // Use bfs to traverse the tree
        while (!queue.isEmpty()) {
            var node = queue.removeFirst();
            if (rectangle.contains(node.data)) {
                resultArray.add(node.data);
            }
            if (node.left != null && rectangle.intersects(node.left.area)) {
                queue.add(node.left);
            }
            if (node.right != null && rectangle.intersects(node.right.area)) {
                queue.add(node.right);
            }
        }
        return resultArray;
    }

    public void draw() {
        // Draw the lines by traversing the tree with bfs
        var queue = new ArrayDeque<Node>();
        queue.add(root);

        while (!queue.isEmpty()) {
            var node = queue.removeFirst();
            StdDraw.setPenRadius(0.01);
            if (node.vertical) {
                StdDraw.setPenColor(Color.red);
                StdDraw.line(node.data.x(), node.area.ymin(), node.data.x(), node.area.ymax());
            }
            else {
                StdDraw.setPenColor(Color.blue);
                StdDraw.line(node.area.xmin(), node.data.y(), node.area.xmax(), node.data.y());
            }
            StdDraw.setPenColor(Color.black);
            StdDraw.setPenRadius(0.02);
            StdDraw.point(node.data.x(), node.data.y());
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }
}
