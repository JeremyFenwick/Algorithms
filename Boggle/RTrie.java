public class RTrie {
    private final int R, toZero;
    private final Node root;

    public RTrie(int R, int toZero) {
        this.R = R;
        this.toZero = toZero;
        root = new Node(R);
    }

    private static class Node {
        public int value;
        public Node[] next;

        public Node(int size) {
            next = new Node[size];
            value = 0;
        }
    }

    public static class Result {
        public boolean prefix;
        public boolean word;
        public int score;

        public Result (boolean prefix, boolean word, int score) {
            this.prefix = prefix;
            this.word = word;
            this.score = score;
        }

        public Result (boolean prefix, boolean word) {
            this.prefix = prefix;
            this.word = word;
            score = -1;
        }
    }

    public void put(String word, int value) {
        var workingNode = root;
        for (int i = 0; i < word.length(); i++) {
            var currentCharacter = word.charAt(i);
            // For example, - 10 of 'A' is 0
            var currentIndex = Character.getNumericValue(currentCharacter) - toZero;
            if (workingNode.next[currentIndex] == null) {
                workingNode.next[currentIndex] = new Node(R);
            }
            workingNode = workingNode.next[currentIndex];
        }
        // Set the value
        workingNode.value = value;
    }

    public Result search(String word) {
        var workingNode = root;
        for (int i = 0; i < word.length(); i++) {
            var currentCharacter = word.charAt(i);
            var currentIndex = Character.getNumericValue(currentCharacter) - toZero;
            // If the trie index is null, the prefix is not valid
            if (workingNode.next[currentIndex] == null) {
                return new Result(false, false);
            }
            workingNode = workingNode.next[currentIndex];
        }
        // If there is a value here we have a word
        if (workingNode.value > 0) {
            return new Result(true, true, workingNode.value);
        }
        // Else we have a prefix
        else {
            return new Result(true, false);
        }
    }
}
