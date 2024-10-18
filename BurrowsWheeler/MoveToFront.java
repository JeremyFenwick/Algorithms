import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;
    private static final char[] SEQUENCE = new char[R];

    public static void encode() {
        generateAsciiSequence();
        while (!BinaryStdIn.isEmpty()) {
            var currentChar = BinaryStdIn.readChar();
            var index = advance(currentChar);
            System.out.println(index);
//            BinaryStdOut.write(index);
        }
        BinaryStdOut.close();
    }

    public static void decode() {
        generateAsciiSequence();
        while (!BinaryStdIn.isEmpty()) {
            var currentIndex = BinaryStdIn.readInt();
            var character = advance(currentIndex);
            BinaryStdOut.write(character);
        }
        BinaryStdOut.close();    }

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
        throw new IndexOutOfBoundsException("Character not found!");
    }

    private static char advance(int targetIndex) {
        char lastChar = SEQUENCE[0];

        for (int i = 0; i < SEQUENCE.length; i++) {
            // Replace the current index with the last character. Store the last character
            var temp = SEQUENCE[i];
            SEQUENCE[i] = lastChar;
            lastChar = temp;
            // Stop at the index match
            if (i == targetIndex) {
                SEQUENCE[0] = lastChar;
                return SEQUENCE[i];
            }
        }
        throw new IndexOutOfBoundsException("Index not found!");
    }

    public static void main(String[] args) {
        encode();
    }
}
