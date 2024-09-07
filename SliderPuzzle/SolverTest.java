import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {
    public Board generateBoard() {
        var row1 = new int[]{ 1, 0, 3 };
        var row2 = new int[]{ 4, 2, 5 };
        var row3 = new int[]{ 7, 8, 6 };
        var grid = new int[][]{ row1, row2, row3 };
        return new Board(grid);
    }

    public Board generateLargerBoard() {
        var row1 = new int[]{ 1, 2, 3, 4 };
        var row2 = new int[]{ 5, 6, 7, 8 };
        var row3 = new int[]{ 9, 10, 11, 0 };
        var row4 = new int[]{ 13, 14, 15, 12 };
        var grid = new int[][]{ row1, row2, row3, row4 };
        return new Board(grid);
    }


    @Test
    void solveTest() {
        var board = generateBoard();
        var solver = new Solver(board);
        assertTrue(solver.moves() > 0);
        for (var item : solver.solution()) {
            System.out.println(item);
        }
    }

    @Test
    void largerSolveTest() {
        var board = generateLargerBoard();
        var solver = new Solver(board);
        assertTrue(solver.moves() > 0);
        for (var item : solver.solution()) {
            System.out.println(item);
        }
    }
}