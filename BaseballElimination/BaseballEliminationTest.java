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

    @Test
    void atlantaVsNewYork() {
        var bb = new BaseballElimination("teams4.txt");
        var remaining = bb.against("Atlanta", "New_York");
        assertEquals(6, remaining);
    }

    @Test
    void montrealVsPhiladelphia() {
        var bb = new BaseballElimination("teams4.txt");
        var remaining = bb.against("Montreal", "Philadelphia");
        assertEquals(2, remaining);
    }

    @Test
    void createNetwork() {
        var bb = new BaseballElimination("teams4.txt");
        var result = bb.isEliminated("Montreal");
        var certs = bb.certificateOfElimination("Montreal");
    }

    @Test
    void createSimpleNetwork() {
        var bb = new BaseballElimination("simple.txt");
        var result = bb.isEliminated("Loser");
        var certs = bb.certificateOfElimination("Loser");
    }

    @Test
    void teams10() {
        var bb = new BaseballElimination("teams10.txt");
        var result = bb.isEliminated("Atlanta");
        var certs = bb.certificateOfElimination("Atlanta");
    }

    @Test
    void teams48() {
        var bb = new BaseballElimination("teams48.txt");
        var result = bb.isEliminated("Team0");
        var certs = bb.certificateOfElimination("Team0");
    }

    @Test
    void teams4b() {
        var bb = new BaseballElimination("teams4b.txt");
        var result = bb.isEliminated("Hufflepuff");
        var certs = bb.certificateOfElimination("Hufflepuff");
    }

    @Test
    void testEquality() {
        var bb = new BaseballElimination("teams4.txt");
        var name = "Atlanta";
        var nameCopy = new String(name);
        var result = bb.isEliminated(name);
        var copyResult = bb.isEliminated(nameCopy);
    }
}
