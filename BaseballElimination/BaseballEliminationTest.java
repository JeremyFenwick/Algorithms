import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseballEliminationTest {
    @Test
    void loadTeams4() {
        var bb = new BaseballElimination("teams4.txt");
    }

    @Test
    void atlantaWins() {
        var bb = new BaseballElimination("teams4.txt");
        var wins = bb.wins("Atlanta");
        assertEquals(83, wins);
    }

    @Test
    void philadelphiaLosses() {
        var bb = new BaseballElimination("teams4.txt");
        var losses = bb.losses("Philadelphia");
        assertEquals(79, losses);
    }

    @Test
    void montrealGamesLeft() {
        var bb = new BaseballElimination("teams4.txt");
        var remaining = bb.remaining("Montreal");
        assertEquals(3, remaining);
    }
}