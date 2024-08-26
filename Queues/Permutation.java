import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        var permutations = Integer.parseInt(args[0]);
        var queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            var input = StdIn.readString();
            queue.enqueue(input);
        }

        for (int i = 0; i < permutations; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
