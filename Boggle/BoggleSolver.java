import java.util.ArrayDeque;
import java.util.HashSet;

public class BoggleSolver {
    private final HashSet<String> includedWords;
    private final RTrie dictionary;
    private int rows, cols;

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
        includedWords.clear();
        rows = board.rows();
        cols = board.cols();
        boggleSolver(board);
        return includedWords;
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

    private void boggleSolver(BoggleBoard board) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                dfs(board, row, col);
            }
        }
    }

    private void dfs(BoggleBoard board, int row, int col) {
        var stack = new ArrayDeque<BoardSearch>();
        var tileWord = board.getLetter(row, col);
        var tile = new BoardSearch(row, col, tileWord);
        stack.push(tile);

        while (!stack.isEmpty()) {
            var currentTile = stack.pop();
            // Mark the node as visited
            var currentCoordinates = coordinates(currentTile.row, currentTile.col);
            currentTile.visited[currentCoordinates] = true;
            // Compute the result
            var currentWord = currentTile.word;
            var result = dictionary.search(currentWord);
            // If the prefix is not in the dictionary, we end the search
            if (!result.prefix) {
                continue;
            }
            // If we are looking at a word, add it to the set
            if (result.word) {
                includedWords.add(currentWord);
            }
            // Push valid neighbours onto the stack
            var neighbours = generateNeighbours(currentTile, board);
            for (var neighbour : neighbours) {
                if (neighbour != null) {
                    stack.push(neighbour);
                }
            }
        }
    }

    private Integer wordPoints(String word) {
        if (word.length() < 4) {
            return 1;
        }
        else if (word.length() < 5) {
            return 2;
        }
        else if (word.length() < 6) {
            return 3;
        }
        else if (word.length() < 7) {
            return 5;
        }
        else {
            return 11;
        }
    }

    private class BoardSearch {
        public int row;
        public int col;
        public String word;
        public boolean[] visited;

        public BoardSearch(int row, int col, String word, boolean[] visited) {
            this.row = row;
            this.col = col;
            this.word = word;
            this.visited = visited;
        }

        public BoardSearch(int row, int col, char character) {
            this.row = row;
            this.col = col;
            this.word = Character.toString(character);
            visited = new boolean[rows * cols];
        }
    }

    private boolean inBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private BoardSearch[] generateNeighbours(BoardSearch tile, BoggleBoard board) {
        var neighbours = new BoardSearch[8];
        // Above
        neighbours[0] = generateNeighbour(tile.row - 1, tile.col - 1, tile, board);
        neighbours[1] = generateNeighbour(tile.row - 1, tile.col, tile, board);
        neighbours[2] = generateNeighbour(tile.row - 1, tile.col + 1, tile, board);
        // Left & Right
        neighbours[3] = generateNeighbour(tile.row, tile.col - 1, tile, board);
        neighbours[4] = generateNeighbour(tile.row, tile.col + 1, tile, board);
        // Below
        neighbours[5] = generateNeighbour(tile.row + 1, tile.col - 1, tile, board);
        neighbours[6] = generateNeighbour(tile.row + 1, tile.col, tile, board);
        neighbours[7] = generateNeighbour(tile.row + 1, tile.col + 1, tile, board);
        return neighbours;
    }

    private BoardSearch generateNeighbour(int row, int col, BoardSearch tile, BoggleBoard board) {
        var index = coordinates(row, col);
        if (!inBounds(row, col) || tile.visited[index]) {
            return null;
        }
        else {
            var tileChar = board.getLetter(row, col);
            var newWord = new StringBuffer(tile.word).append(tileChar).toString();
            return new BoardSearch(row, col, newWord, tile.visited);
        }
    }

    private int coordinates(int row, int col) {
        return (row * rows) + col;
    }
}
