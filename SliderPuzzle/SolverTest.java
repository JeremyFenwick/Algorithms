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

    @Test
    void solveTest() {
        var board = generateBoard();
        var solver = new Solver(board);
        assertTrue(solver.moves() > 0);
        for (var item : solver.solution()) {
            System.out.println(item);
        }
    }
}