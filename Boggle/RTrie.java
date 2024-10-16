public class RTrie {
    private final int R, toZero;
    private final BoggleNode root;

    public RTrie(int R, int toZero) {
        this.R = R;
        this.toZero = toZero;
        root = new BoggleNode(R);
    }

    public static class BoggleNode {
        public int value;
        public BoggleNode[] next;

        public BoggleNode(int size) {
            next = new BoggleNode[size];
            value = 0;
        }
    }

    public static class Result {
        public boolean prefix;
        public boolean word;
        public int score;
        public BoggleNode node;

        public Result (boolean prefix, boolean word, int score, BoggleNode node) {
            this.prefix = prefix;
            this.word = word;
            this.score = score;
            this.node = node;
        }

        public Result (boolean prefix, boolean word, BoggleNode node) {
            this.prefix = prefix;
            this.word = word;
            score = -1;
            this.node = node;
        }
    }

    public void put(String word, int value) {
        var workingNode = root;
        for (int i = 0; i < word.length(); i++) {
            var currentCharacter = word.charAt(i);
            // For example, - 10 of 'A' is 0
            var currentIndex = Character.getNumericValue(currentCharacter) - toZero;
            if (workingNode.next[currentIndex] == null) {
                workingNode.next[currentIndex] = new BoggleNode(R);
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
                return new Result(false, false, null);
            }
            workingNode = workingNode.next[currentIndex];
        }
        // If there is a value here we have a word
        if (workingNode.value > 0) {
            return new Result(true, true, workingNode.value, workingNode);
        }
        // Else we have a prefix
        else {
            return new Result(true, false, workingNode);
        }
    }

    public Result search(char character, BoggleNode node) {
        if (node == null) {
            return search(character);
        }
        var currentIndex = Character.getNumericValue(character) - toZero;
        if (node.next[currentIndex] == null) {
            return new Result(false, false, null);
        }
        var nextNode = node.next[currentIndex];
        // If there is a value here we have a word
        if (nextNode.value > 0) {
            return new Result(true, true, nextNode.value, nextNode);
        }
        // Else we have a prefix
        else {
            return new Result(true, false, nextNode);
        }
    }

    public Result search(char character) {
        var currentIndex = Character.getNumericValue(character) - toZero;
        if (root.next[currentIndex] == null) {
            return new Result(false, false, null);
        }
        var nextNode = root.next[currentIndex];
        // If there is a value here we have a word
        if (nextNode.value > 0) {
            return new Result(true, true, nextNode.value, nextNode);
        }
        // Else we have a prefix
        else {
            return new Result(true, false, nextNode);
        }
    }
}
