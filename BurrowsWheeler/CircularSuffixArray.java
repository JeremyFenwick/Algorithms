import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private final Integer[] indices;
    private final String input;

    public CircularSuffixArray(String s) {
        if  (s == null) {
            throw new java.lang.IllegalArgumentException();
        }

        indices = new Integer[s.length()];
        input = s;
        populateIndices();
        Arrays.sort(indices, suffixSort());
    }

    public int length() {

        return indices.length;
    }

    public int index(int i) {
        if (i < 0 || i >= this.indices.length) {
            throw new java.lang.IllegalArgumentException();
        }

        return indices[i];
    }

    private void populateIndices() {
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }
    }

    private Comparator<Integer> suffixSort() {
        return (index1, index2) -> {
            while (index1 < input.length() && index2 < input.length()) {
                var firstCharacter = input.charAt(index1);
                var secondCharacter = input.charAt(index2);
                var compare = firstCharacter - secondCharacter;
                if (compare != 0) {
                    return compare;
                }
                index1++;
                index2++;
            }
            // If the characters are the same, one must be shorter
            return index2 - index1;
        };
    }

    public static void main(String[] args) {
        var result = new CircularSuffixArray("BANANA");
    }
}
