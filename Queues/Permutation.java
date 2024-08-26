import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        var permutations = Integer.parseInt(args[0]);
        var queue = new RandomizedQueue<String>();
        for (int i = 0; i < permutations; i++) {
            queue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < permutations; i++) {
            System.out.print(queue.dequeue());
        }
    }
}
