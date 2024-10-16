import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoggleSolverTest {
    public String[] readDictionary(String fileName) {
        In in = new In(fileName);
        String[] lines = in.readAllLines();
        in.close();
        return lines;
    }

    public int coordinates(int row, int cols, int col) {
        return (row * cols) + col;
    }

    @Test
    void coordinates() {
        var i1 = coordinates(0, 2, 0);
        var i2 = coordinates(0, 2, 1);
        var i3 = coordinates(0, 2, 2);
        var index = coordinates(1, 2, 0);
    }

    @Test
    void characterToInteger() {
        System.out.println(Character.getNumericValue('A'));
        System.out.println(Character.getNumericValue('Z'));
        System.out.println(Character.getNumericValue('Q'));
    }

    @Test
    void loadDictionary() {
        var dictionary = readDictionary("dictionary-yawl.txt");
        var boggleSolver = new BoggleSolver(dictionary);
        assertNotNull(boggleSolver);
    }

    @Test
    void boggle100PointTest() {
        var dictionary = readDictionary("dictionary-yawl.txt");
        var boggleSolver = new BoggleSolver(dictionary);
        var board = new BoggleBoard("board-points100.txt");
        var result = boggleSolver.getAllValidWords(board);
        var points = 0;
        for (var word : result) {
            points += boggleSolver.scoreOf(word);
        }
        assertTrue(points >= 100);
    }

    @Test
    void boggleQwertyTest() {
        var dictionary = readDictionary("dictionary-yawl.txt");
        var boggleSolver = new BoggleSolver(dictionary);
        var board = new BoggleBoard("board-qwerty.txt");
        var result = boggleSolver.getAllValidWords(board);
        var points = 0;
        for (var word : result) {
            points += boggleSolver.scoreOf(word);
        }
        assertEquals(100, points);
    }

    @Test
    void boggleVertical() {
        var dictionary = readDictionary("dictionary-yawl.txt");
        var boggleSolver = new BoggleSolver(dictionary);
        var board = new BoggleBoard("board-vertical.txt");
        var result = boggleSolver.getAllValidWords(board);
        var points = 0;
        for (var word : result) {
            points += boggleSolver.scoreOf(word);
        }
        assertEquals(100, points);
    }

    @Test
    void boggleQ() {
        var dictionary = readDictionary("dictionary-yawl.txt");
        var boggleSolver = new BoggleSolver(dictionary);
        var board = new BoggleBoard("board-q.txt");
        var result = boggleSolver.getAllValidWords(board);
    }
}