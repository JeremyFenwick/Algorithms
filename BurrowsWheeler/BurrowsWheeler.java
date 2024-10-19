import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

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
            // Load the string
            var input = new transformChar[s.length()];
            for (int i = 0; i < input.length; i++) {
                input[i] = new transformChar(s.charAt(i));
            }
            // Setup the result
            var next = new int[input.length];
            // Copy the array and sort the string
            var sortedInput = s.toCharArray();
            Arrays.sort(sortedInput);
            // Load the suffixes
            for (int i = 0; i < input.length; i++) {
                // The character from the sorted input
                var iCharacter = sortedInput[i];
                // Match it against an 'unused' character from the original
                for (int j = 0; j < input.length; j++) {
                    var nextICharacter = input[j].character;
                    if (iCharacter == nextICharacter && !input[j].used) {
                        next[i] = j;
                        input[j].used = true;
                        break;
                    }
                }
            }
            // Print the result
            var nextStep = first;
            for (int i = 0; i < next.length; i++) {
                BinaryStdOut.write(sortedInput[nextStep]);
                nextStep = next[nextStep];
            }
        }
        BinaryStdIn.close();
        BinaryStdOut.close();
    }

//    public static void inverseTransform(int first, String s) {
//        // Load the string
//        var input = new transformChar[s.length()];
//        for (int i = 0; i < input.length; i++) {
//            input[i] = new transformChar(s.charAt(i));
//        }
//        // Setup the result
//        var next = new int[input.length];
//        // Copy the array and sort the string
//        var sortedInput = s.toCharArray();
//        Arrays.sort(sortedInput);
//        // Load the suffixes
//        for (int i = 0; i < input.length; i++) {
//            // The character from the sorted input
//            var iCharacter = sortedInput[i];
//            // Match it against an 'unused' character from the original
//            for (int j = 0; j < input.length; j++) {
//                var nextICharacter = input[j].character;
//                if (iCharacter == nextICharacter && !input[j].used) {
//                    next[i] = j;
//                    input[j].used = true;
//                    break;
//                }
//            }
//        }
//        // Print the result
//        var nextStep = first;
//        for (int i = 0; i < next.length; i++) {
//            System.out.print(sortedInput[nextStep]);
//            nextStep = next[nextStep];
//        }
//    }

    private static class transformChar {
        public char character;
        public boolean used;

        public transformChar(char character) {
            this.character = character;
            used = false;
        }
    }

    public static void main(String[] args) {
//        inverseTransform(3, "ARD!RCAAAABB");
    }
}
