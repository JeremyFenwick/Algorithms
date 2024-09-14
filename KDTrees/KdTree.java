import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
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
        public Point2D point;
        public boolean vertical;
        public Node left;
        public Node right;
        public RectHV area;

        public Node(Point2D newPoint, boolean isVertical, RectHV rectangle) {
            point = newPoint;
            vertical = isVertical;
            area = rectangle;
        }
    }

    // Helper function to determine if we need to go left or right
    private boolean goRight(Node node, Point2D point) {
        if (node.vertical) {
            return point.x() >= node.point.x();
        }
        else {
            return point.y() >= node.point.y();
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
            var rectangle = new RectHV(parent.point.x(), parent.area.ymin(), parent.area.xmax(), parent.area.ymax());
            parent.right = new Node(point, false, rectangle);
        }
        // Left node, parent is a vertical
        else if (!goRight && parent.vertical) {
            var rectangle = new RectHV(parent.area.xmin(), parent.area.ymin(), parent.point.x(), parent.area.ymax());
            parent.left = new Node(point, false, rectangle);
        }
        // Right node, parent is a horizontal
        else if (goRight) {
            var rectangle = new RectHV(parent.area.xmin(), parent.point.y(), parent.area.xmax(), parent.area.ymax());
            parent.right = new Node(point, true, rectangle);
        }
        // Left node, parent is a horizontal
        else {
            var rectangle = new RectHV(parent.area.xmin(), parent.area.ymin(), parent.area.xmax(),  parent.point.y());
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
        if (newPoint == null) {
            throw new IllegalArgumentException();
        }

        var workingNode = root;
        Node parentNode = null;

        // Identify the correct parent node for insertion
        while (workingNode != null) {
            // Determine if the node already exists
            if (workingNode.point.equals(newPoint)) {
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
        if (target == null) {
            throw new IllegalArgumentException();
        }

        var workingNode = root;
        while (workingNode != null) {
            if (workingNode.point.equals(target)) {
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
        if (rectangle == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return new ArrayList<Point2D>();
        }

        var resultArray = new ArrayList<Point2D>();
        var queue = new ArrayDeque<Node>();
        queue.add(root);

        // Use bfs to traverse the tree
        while (!queue.isEmpty()) {
            var node = queue.removeFirst();
            if (rectangle.contains(node.point)) {
                resultArray.add(node.point);
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
            StdDraw.setPenRadius(0.002);
            if (node.vertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.point.x(), node.area.ymin(), node.point.x(), node.area.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.area.xmin(), node.point.y(), node.area.xmax(), node.point.y());
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.015);
            StdDraw.point(node.point.x(), node.point.y());
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    public Point2D nearest(Point2D target) {
        if (target == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return null;
        }
        return nearestRecurse(target, root.point, root);
    }

    private Point2D nearestRecurse(Point2D target, Point2D champion, Node node) {
        if (node == null) {
            return champion;
        }

        var newChampion = champion;
        if (node.area.distanceSquaredTo(target) < champion.distanceSquaredTo(target)) {
            // Potentially replace the point
            if (node.point.distanceSquaredTo(target) < champion.distanceSquaredTo(target)) {
                newChampion = node.point;
            }
            if (goRight(node, target)) {
                newChampion = nearestRecurse(target, newChampion, node.right);
                newChampion = nearestRecurse(target, newChampion, node.left);
            }
            else {
                newChampion = nearestRecurse(target, newChampion, node.left);
                newChampion = nearestRecurse(target, newChampion, node.right);
            }
        }

        return newChampion;
    }
}
