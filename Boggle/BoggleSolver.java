import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

public class BoggleSolver {
    private final HashSet<String> includedWords;
    private final RTrie dictionary;
    private int rows, cols;
    private BoggleBoard board;

    public BoggleSolver(String[] dictionaryArray) {
        dictionary = new RTrie(26, 10);
        includedWords = new HashSet<>();
        rows = 0;
        cols = 0;

        for (var word : dictionaryArray) {
            if (word.length() < 3) {
                continue;
            }
            var wordPoints = wordPoints(word);
            this.dictionary.put(word, wordPoints);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        this.board = board;
        includedWords.clear();
        rows = board.rows();
        cols = board.cols();
        boggleSolver();
        return new HashSet<>(includedWords);
    }

    public int scoreOf(String word) {
        var result = dictionary.search(word);
        if (result.word) {
            return result.score;
        }
        else {
            return 0;
        }
    }

    private void boggleSolver() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                dfs(row, col);
            }
        }
    }

    private void dfs(int row, int col) {
        var stack = new ArrayDeque<BoardSearch>();
        var tileWord = board.getLetter(row, col);
        stack.push(new BoardSearch(row, col, tileWord));

        while (!stack.isEmpty()) {
            var tile = stack.pop();
            // Mark the node as visited
            var currentCoordinates = coordinates(tile.row, tile.col);
            tile.visited[currentCoordinates] = true;
            // Compute the result. If the character is Q we need to advance twice as it is a double tile
            RTrie.Result result;
            if (tile.currentChar == 'Q') {
                var tempResult = dictionary.search('Q', tile.node);
                if (!tempResult.prefix) {
                    continue;
                }
                result = dictionary.search('U', tempResult.node);
            }
            else {
                result = dictionary.search(tile.currentChar, tile.node);
            }
            // If the prefix is not in the dictionary, we end the search
            if (!result.prefix) {
                continue;
            }
            // If we are looking at a word, add it to the set
            if (result.word) {
                includedWords.add(tile.word);
            }
            // Push valid neighbours onto the stack
            var neighbours = generateNeighbours(tile, result.node);
            for (var neighbour : neighbours) {
                stack.push(neighbour);
            }
        }
    }

    private Integer wordPoints(String word) {
        if (word.length() <= 4) {
            return 1;
        }
        else if (word.length() == 5) {
            return 2;
        }
        else if (word.length() == 6) {
            return 3;
        }
        else if (word.length() == 7) {
            return 5;
        }
        else {
            return 11;
        }
    }

    private boolean inBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private ArrayList<BoardSearch> generateNeighbours(BoardSearch tile, RTrie.BoggleNode node) {
        var neighbours = new ArrayList<BoardSearch>();
        // Above
        generateNeighbour(tile.row - 1, tile.col - 1, tile, neighbours, node);
        generateNeighbour(tile.row - 1, tile.col, tile, neighbours, node);
        generateNeighbour(tile.row - 1, tile.col + 1, tile, neighbours, node);
        // Left & Right
        generateNeighbour(tile.row, tile.col - 1, tile, neighbours, node);
        generateNeighbour(tile.row, tile.col + 1, tile, neighbours, node);
        // Below
        generateNeighbour(tile.row + 1, tile.col - 1, tile, neighbours, node);
        generateNeighbour(tile.row + 1, tile.col, tile, neighbours, node);
        generateNeighbour(tile.row + 1, tile.col + 1, tile, neighbours, node);
        return neighbours;
    }

    private void generateNeighbour(int row, int col, BoardSearch tile, ArrayList<BoardSearch> list, RTrie.BoggleNode node) {
        var index = coordinates(row, col);
        if (!inBounds(row, col) || tile.visited[index]) {
            return;
        }
        else {
            var tileChar = board.getLetter(row, col);
            list.add(new BoardSearch(row, col, tileChar, tile, node));
        }
    }

    private int coordinates(int row, int col) {
        return (row * cols) + col;
    }

    private class BoardSearch {
        public int row;
        public int col;
        public String word;
        public boolean[] visited;
        public char currentChar;
        public RTrie.BoggleNode  node;

        public BoardSearch(int row, int col, char character, BoardSearch parent, RTrie.BoggleNode node) {
            this.row = row;
            this.col = col;
            this.node = node;
            if (character == 'Q') {
                this.word = new StringBuffer(parent.word).append("QU").toString();
            }
            else {
                this.word = new StringBuffer(parent.word).append(character).toString();;
            }
            this.visited = new boolean[rows * cols];
            System.arraycopy(parent.visited, 0, this.visited, 0, parent.visited.length);
            this.currentChar = character;
        }

        public BoardSearch(int row, int col, char character) {
            this.row = row;
            this.col = col;
            // Q is assumed to always be followed by a U
            if (character == 'Q') {
                this.currentChar = 'Q';
                this.word = "QU";
            } else {
                this.currentChar = character;
                this.word = Character.toString(character);
            }
            visited = new boolean[rows * cols];
            node = null;
        }
    }
}
