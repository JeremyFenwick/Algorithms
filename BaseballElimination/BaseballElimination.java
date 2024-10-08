import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class BaseballElimination {
    private final HashMap<String, Integer> teams;
    private final int[] data;

    public BaseballElimination(String filename) {
        var lines = readAllLines(filename);
        var numberOfTeams = Integer.parseInt(lines[0]);
        teams = new HashMap<>();
        data = new int[(numberOfTeams * 3) + (numberOfTeams * numberOfTeams)];
        loadData(lines, numberOfTeams);
    }

    public int numberOfTeams() {
        return teams.size();
    }

    public Iterable<String> teams() {
        return teams.keySet();
    }

    public int wins(String team) {
        var placement = teams.get(team);
        var index = startIndex(placement);
        return data[index];
    }

    public int losses(String team) {
        var placement = teams.get(team);
        var index = startIndex(placement);
        return data[index + 1];
    }

    public int remaining(String team) {
        var placement = teams.get(team);
        var index = startIndex(placement);
        return data[index + 2];
    }

    public int against(String team, String opponent) {
        var placement = teams.get(team);
        var opponentIndex = teams.get(opponent);
        var index = startIndex(placement);
        return data[index + 2 + opponentIndex];
    }

    private String[] readAllLines(String filename) {
        In in = new In(filename);
        String[] lines = in.readAllLines();
        in.close();
        return lines;
    }

    private void loadData(String[] teamData, int size) {
        for (int i = 1; i <= size; i++) {
            var line = teamData[i];
            // The hashmap placement should start at 0
            loadTeam(line, i - 1);
            loadLine(line, (i - 1) * (size + 3));
        }
    }

    private void loadTeam(String line, int placement) {
        var name = "";
        // Extract the team name
        for (int i = 0; i <= line.length(); i++){
            if (Character.isLetter(line.charAt(i)) || line.charAt(i) == '_') {
                name += line.charAt(i);
            }
            else {
                break;
            }
        }
        // Load the hashtable
        teams.put(name, placement);
    }

    private void loadLine(String line, int startIndex) {
        // Load the data array
        var indexIncrement = 0;
        StringBuilder workingNumber = new StringBuilder();
        for (int i = 0; i < line.length(); i++){
            if (Character.isDigit(line.charAt(i))) {
                workingNumber.append(line.charAt(i));
            }
            else if (line.charAt(i) == ' ' && (!workingNumber.isEmpty())) {
                var number = Integer.parseInt(workingNumber.toString());
                data[startIndex + indexIncrement] = number;
                workingNumber = new StringBuilder();
                indexIncrement++;
            }
        }
        // Catch the final number
        if (!workingNumber.isEmpty()) {
            var number = Integer.parseInt(workingNumber.toString());
            data[startIndex + indexIncrement] = number;
        }
    }

    private int startIndex(int placement) {
        return placement * (numberOfTeams() + 3);
    }
}
