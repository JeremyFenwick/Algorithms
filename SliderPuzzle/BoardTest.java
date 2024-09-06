import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    public Board generateBoard() {
        var row1 = new int[]{ 8, 1, 3 };
        var row2 = new int[]{ 4, 0, 2 };
        var row3 = new int[]{ 7, 6, 5 };
        var grid = new int[][]{ row1, row2, row3 };
        return new Board(grid);
    }

    public Board generateAlternateBoard() {
        var row1 = new int[]{ 0, 3, 1 };
        var row2 = new int[]{ 8, 4, 2 };
        var row3 = new int[]{ 5, 6, 7 };
        var grid = new int[][]{ row1, row2, row3 };
        return new Board(grid);
    }

    public Board generateSolvedBoard() {
        var row1 = new int[]{ 1, 2, 3 };
        var row2 = new int[]{ 4, 5, 6 };
        var row3 = new int[]{ 7, 8, 0 };
        var grid = new int[][]{ row1, row2, row3 };
        return new Board(grid);
    }

    @Test
    void printBoardTest() {
        var board = generateBoard();
        assertEquals(board.hamming(), 5);
        assertEquals(board.manhattan(), 10);
        System.out.print(board);
    }

    @Test
    void printTwinTest() {
        var board = generateBoard();
        System.out.print(board.toString());
        System.out.print(board.twin());
    }

    @Test
    void equalityTest() {
        var board = generateBoard();
        var board2 = generateBoard();
        var result = board.equals(board2);
        assertTrue(result);
    }

    @Test
    void inequalityTest() {
        var board = generateBoard();
        var board2 = generateAlternateBoard();
        var result = board.equals(board2);
        assertFalse(result);
    }

    @Test
    void neighboursTest() {
        var board = generateBoard();
        System.out.println("Original");
        System.out.println(board);
        System.out.println("Neighbours");
        var neighbours = board.neighbors();
        for (var neighbour : neighbours) {
            System.out.println(neighbour);
        }
    }

    @Test
    void alternateNeighboursTest() {
        var board = generateAlternateBoard();
        System.out.println("Original");
        System.out.println(board);
        System.out.println("Neighbours");
        var neighbours = board.neighbors();
        for (var neighbour : neighbours) {
            System.out.println(neighbour);
        }
    }

    @Test
    void solvedBoardTest() {
        var board = generateBoard();
        assertFalse(board.isGoal());
        var board2 = generateAlternateBoard();
        assertFalse(board2.isGoal());
        var board3 = generateSolvedBoard();
        assertTrue(board3.isGoal());
    }
}