import java.util.ArrayDeque;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class Solver {
    private final ArrayDeque<Board> solution;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        solution = new ArrayDeque<Board>();
        calculateSolution(initial);
    }

    private void calculateSolution(Board initial) {
        // Create two priority queues
        var priorityQueue = generateMinPq(initial);
        var twinPriorityQueue = generateMinPq(initial.twin());

        while (true) {
            // Pop nodes from both queues
            var currentNode = priorityQueue.delMin();
            var twinNode = twinPriorityQueue.delMin();

            // Check if we have a solution
            if (currentNode.board.isGoal()) {
                generateSolution(currentNode);
                break;
            }
            else if (twinNode.board.isGoal()) {
                break;
            }

            // Add neighbours to their respective queues
            addNeighboursToMinPq(currentNode, priorityQueue);
            addNeighboursToMinPq(twinNode, twinPriorityQueue);
        }
    }

    private void generateSolution(Node solutionNode) {
        while (solutionNode != null) {
            solution.addFirst(solutionNode.board);
            solutionNode = solutionNode.parent;
        }
    }

    private MinPQ<Node> generateMinPq(Board initial) {
        var priorityQueue = new MinPQ<Node>();
        var initialNode = new Node(initial, null);
        priorityQueue.insert(initialNode);
        return priorityQueue;
    }

    private void addNeighboursToMinPq(Node node, MinPQ<Node> minPQ) {
        var neighbours = node.board.neighbors();
        for (var neighbour : neighbours) {
            // We do not add a neighbour if it is the same board as its parent
            if (node.parent != null && neighbour.equals(node.parent.board)) {
                continue;
            }
            var newNode = new Node(neighbour, node);
            minPQ.insert(newNode);
        }
    }

    private static class Node implements Comparable<Node> {
        public final Board board;
        public final Node parent;
        public final int moves;
        public final int priority;

        public Node(Board board, Node parent) {
            this.board = board;
            this.parent = parent;
            moves = parent == null ? 0 : parent.moves + 1;
            this.priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(Node otherNode) {
            return priority - otherNode.priority;
        }
    }

    public boolean isSolvable() {
        return !solution.isEmpty();
    }

    public int moves() {
        return solution.isEmpty() ? -1 : solution.size() - 1;
    }

    public Iterable<Board> solution() {
        return !solution.isEmpty() ? solution : null;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }}
