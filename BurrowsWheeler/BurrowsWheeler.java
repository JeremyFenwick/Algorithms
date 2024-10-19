import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Arrays;

public class BurrowsWheeler {
    public static void transform() {
        while (!BinaryStdIn.isEmpty()) {
            String string = BinaryStdIn.readString();
            var cSArray = new CircularSuffixArray(string);
            // Find the row that contains the original string
            for (int i = 0; i < string.length(); i++) {
                if (cSArray.index(i) == 0) {
                    BinaryStdOut.write(i);
                }
            }
            // Print out the transformed characters
            for (int i = 0; i < string.length(); i++) {
                var currentIndex = cSArray.index(i);
                if (currentIndex == 0) {
                    BinaryStdOut.write(string.charAt(string.length() - 1));
                }
                else {
                    BinaryStdOut.write(string.charAt(currentIndex - 1)) ;
                }
            }
        }
        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    public static void inverseTransform() {
        while (!BinaryStdIn.isEmpty()) {
            int first = BinaryStdIn.readInt();
            String s = BinaryStdIn.readString();
            // Set up the result
            var next = new int[s.length()];
            // Copy the array and sort the string
            var sortedInput = s.toCharArray();
            Arrays.sort(sortedInput);
            // Create a hashmap of characters and their locations (queues)
            var locations = new HashMap<Character, ArrayDeque<Integer>>();
            for (int i = 0; i < s.length(); i++) {
                var character = s.charAt(i);
                // If it is empty, put a new queue there
                if (!locations.containsKey(character)) {
                    locations.put(character, new ArrayDeque<>());
                }
                locations.get(character).addLast(i);
            }
            // Load the next array
            for (int i = 0; i < sortedInput.length; i++) {
                var index = sortedInput[i];
                var location = locations.get(index).removeFirst();
                next[i] = location;
            }
            // Print the result
            var nextStep = first;
            for (int i = 0; i < next.length; i++) {
                BinaryStdOut.write(sortedInput[nextStep]);
                nextStep = next[nextStep];
            }
        }
        BinaryStdOut.close();
    }


    public static void main(String[] args) {
        if (args[0].equals("-")) {
            BurrowsWheeler.transform();
        }
        if (args[0].equals("+")) {
            BurrowsWheeler.inverseTransform();
        }
    }
}
