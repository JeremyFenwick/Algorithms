import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;
    private static final char[] SEQUENCE = new char[R];
    private static final int BITS = 8;

    public static void encode() {
        generateAsciiSequence();
        while (!BinaryStdIn.isEmpty()) {
            var currentChar = BinaryStdIn.readChar();
            var index = advance(currentChar);
            BinaryStdOut.write(index, BITS);
        }
        BinaryStdOut.close();
    }

    public static void decode() {
        generateAsciiSequence();
        while (!BinaryStdIn.isEmpty()) {
            var currentIndex = BinaryStdIn.readInt(BITS);
            var character = advance(currentIndex);
            BinaryStdOut.write(character);
        }
        BinaryStdOut.close();
    }

    private static void generateAsciiSequence() {
        for (int i = 0; i < R; i++) {
            SEQUENCE[i] = (char) i;
        }
    }

    private static int advance(char symbol) {
        char lastChar = SEQUENCE[0];

        for (int i = 0; i < SEQUENCE.length; i++) {
            // Replace the current index with the last character. Store the last character
            var temp = SEQUENCE[i];
            SEQUENCE[i] = lastChar;
            lastChar = temp;
            // Stop at the symbol match
            if (lastChar == symbol) {
                SEQUENCE[0] = lastChar;
                return i;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    private static char advance(int targetIndex) {
        char lastChar = SEQUENCE[0];

        for (int i = 0; i < SEQUENCE.length; i++) {
            // If we are at the target index, break the sequence
            if (i == targetIndex) {
                var result = SEQUENCE[i];
                SEQUENCE[i] = lastChar;
                SEQUENCE[0] = result;
                return result;
            }
            // Replace the current index with the last character. Store the last character
            var temp = SEQUENCE[i];
            SEQUENCE[i] = lastChar;
            lastChar = temp;
        }
        throw new IndexOutOfBoundsException();
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        }
        else if (args[0].equals("+")) {
            decode();
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
