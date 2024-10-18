import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private final Integer[] indices;
    private final String input;

    public CircularSuffixArray(String s) {
        indices = new Integer[s.length()];
        input = s;
        populateIndices();
        Arrays.sort(indices, suffixSort());
    }

    public int length() {
        return indices.length;
    }

    public int index(int i) {
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
            return 0;
        };
    }

    public static void main(String[] args) {
        var result = new CircularSuffixArray("MINE");
    }
}
