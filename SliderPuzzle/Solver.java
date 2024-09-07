import java.util.ArrayDeque;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class Solver {
    public ArrayDeque<Board> solution;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        solution = new ArrayDeque<Board>();
        calculateSolution(initial);
    }

    private void calculateSolution(Board initial) {
        var priorityQueue = new MinPQ<Node>();
        var initialNode = new Node(initial, null);
        priorityQueue.insert(initialNode);

        while (true) {
            var currentNode = priorityQueue.delMin();
            if (currentNode.board.isGoal()) {
                generateSolution(currentNode);
                break;
            }
            var neighbours = currentNode.board.neighbors();
            for (var neighbour : neighbours) {
                // We don't queue a neighbour if its board equals its parent
                if (neighbour.equals(currentNode.parent.board)) {
                    continue;
                }
                var newNode = new Node(neighbour, currentNode);
                priorityQueue.insert(newNode);
            }
        }
    }

    private void generateSolution(Node solutionNode) {
        var workingNode = solutionNode;
        while (workingNode != null) {
            solution.addFirst(solutionNode.board);
            workingNode = workingNode.parent;
        }
    }

    private class Node implements Comparable<Node> {
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
        return solution != null;
    }

    public int moves() {
        return solution.isEmpty() ? -1 : solution.size();
    }

    public Iterable<Board> solution() {
        return solution;
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
