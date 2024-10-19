import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {
    public static void transform() {
        while (!BinaryStdIn.isEmpty()) {
            String string = BinaryStdIn.readString();
            var cSArray = new CircularSuffixArray(string);
            for (int i = 0; i < string.length(); i++) {
                if (cSArray.index(i) == 0) {
                    BinaryStdOut.write(i);
                }
            }

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

    }

    public static void main(String[] args) {
    }
}
