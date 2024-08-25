import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String result = "";
        for (double count = 1.0; !StdIn.isEmpty(); count += 1.0) {
            String input = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / count)) {
                result = input;
            }
        }
        System.out.println(result);
    }
}
