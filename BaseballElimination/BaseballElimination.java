import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseballElimination {
    private final HashMap<String, Integer> teams;
    private final int[] data;
    private int maxFlow = 0;
    private double cacheFlow;
    private String cacheTeam = null;
    private final ArrayList<String> certificates;

    public BaseballElimination(String filename) {
        var lines = readAllLines(filename);
        var numberOfTeams = Integer.parseInt(lines[0]);
        teams = new HashMap<>();
        data = new int[(numberOfTeams * 3) + (numberOfTeams * numberOfTeams)];
        certificates = new ArrayList<String>();
        cacheFlow = 0;
        loadData(lines, numberOfTeams);
    }

    public int numberOfTeams() {
        return teams.size();
    }

    public Iterable<String> teams() {
        return teams.keySet();
    }

    public int wins(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        var placement = teams.get(team);
        var index = startIndex(placement);
        return data[index];
    }

    public int losses(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        var placement = teams.get(team);
        var index = startIndex(placement);
        return data[index + 1];
    }

    public int remaining(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        var placement = teams.get(team);
        var index = startIndex(placement);
        return data[index + 2];
    }

    public int against(String team, String opponent) {
        if (!teams.containsKey(team) || !teams.containsKey(opponent)) {
            throw new IllegalArgumentException();
        }
        var placement = teams.get(team);
        var opponentIndex = teams.get(opponent);
        var index = startIndex(placement);
        return data[index + 3 + opponentIndex];
    }

    public boolean isEliminated(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        if (team.equals(cacheTeam)) {
            return cacheFlow < maxFlow;
        }
        else {
            certificates.clear();
            cacheTeam = team;
            maxFlow = 0;
        }
        // Check for trivial elimination
        if (trivialElimination(team)) {
            return true;
        }
        // If there are games remaining, the team is eliminated
        var ff = generateFordFulkerson(team);
        cacheFlow = ff.value();
        return cacheFlow < maxFlow;
    }

    public Iterable<String> certificateOfElimination(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        isEliminated(team);
        if (!certificates.isEmpty()) {
            return new ArrayList<>(certificates);
        }
        else {
            return null;
        }
    }

    private boolean trivialElimination(String team) {
        var targetGames = wins(team) + remaining(team);
        var flag = false;
        for (var teamName :  teams.keySet()) {
            if (wins(teamName) > targetGames) {
                certificates.add(teamName);
                flag = true;
            }
        }
        return flag;
    }

    private FordFulkerson generateFordFulkerson(String excludedTeam) {
        var teamArray = otherTeamsArray(excludedTeam);
        // General parameters
        var uniqueMatchCount = teamArray.length * (teamArray.length - 1) / 2;
        var totalVertices = 2 + (teamArray.length) + uniqueMatchCount;
        var start = totalVertices - 2;
        var sink = totalVertices - 1;
        var network = new FlowNetwork(totalVertices);

        // Load the game and team vertices
        var currentVertex = 0;
        for (int i = 0; i < teamArray.length; i++) {
            for (int j = i + 1; j < teamArray.length; j++) {
                var gamesBetween = against(teamArray[i], teamArray[j]);
                maxFlow += gamesBetween;
                // Connect the start to the games between
                network.addEdge(new FlowEdge(start, currentVertex, gamesBetween));
                // Connect the game vertex to the first team vertex
                network.addEdge(new FlowEdge(currentVertex, i + uniqueMatchCount, Double.MAX_VALUE));
                // Connect the game vertex to the second team vertex
                network.addEdge(new FlowEdge(currentVertex, j + uniqueMatchCount, Double.MAX_VALUE));
                // Move to the next vertex
                currentVertex++;
            }
        }

        // Connect the team vertices to the sink
        var targetTeamGames = wins(excludedTeam) + remaining(excludedTeam);
        currentVertex = uniqueMatchCount;
        for (String teamName : teamArray) {
            var capacity = targetTeamGames - wins(teamName);
            network.addEdge(new FlowEdge(currentVertex, sink, capacity));
            currentVertex++;
        }
        var ff = new FordFulkerson(network, start, sink);

        // Load the certificates
        for (int i = 0; i < teamArray.length; i++) {
            var index = i + uniqueMatchCount;
            if (ff.inCut(index)) {
                certificates.add(teamArray[i]);
            }
        }
        // Return ford fulkerson
        return ff;
    }

    private String[] readAllLines(String filename) {
        In in = new In(filename);
        String[] lines = in.readAllLines();
        in.close();
        return lines;
    }

    private void loadData(String[] teamData, int size) {
        for (int i = 1; i <= size; i++) {
            var line = teamData[i].trim();
            // The hashmap placement should start at 0
            var breakpoint = loadTeam(line, i - 1);
            // The index is placement * (size + 3)
            loadLine(line.substring(breakpoint), (i - 1) * (size + 3));
        }
    }

    private int loadTeam(String line, int placement) {
        var name = "";
        var breakpoint = 0;
        // Extract the team name
        for (int i = 0; i <= line.length(); i++){
            if (line.charAt(i) != ' ') {
                name += line.charAt(i);
            }
            else {
                breakpoint = i;
                break;
            }
        }
        // Load the hashtable
        teams.put(name, placement);
        return breakpoint;
    }

    private void loadLine(String line, int startIndex) {
        // Load the data array
        var indexIncrement = 0;
        StringBuilder workingNumber = new StringBuilder();
        for (int i = 0; i < line.length(); i++){
            if (Character.isDigit(line.charAt(i))) {
                workingNumber.append(line.charAt(i));
            }
            else if (line.charAt(i) == ' ' && (workingNumber.length() != 0)) {
                var number = Integer.parseInt(workingNumber.toString());
                data[startIndex + indexIncrement] = number;
                workingNumber = new StringBuilder();
                indexIncrement++;
            }
        }
        // Catch the final number
        if (workingNumber.length() != 0) {
            var number = Integer.parseInt(workingNumber.toString());
            data[startIndex + indexIncrement] = number;
        }
    }

    private int startIndex(int placement) {
        return placement * (numberOfTeams() + 3);
    }

    private String[] otherTeamsArray(String excludedTeam) {
        var teamArray = new String[numberOfTeams() - 1];
        var index = 0;
        for (var team : teams.keySet()) {
            if (team.equals(excludedTeam)) {
                continue;
            }
            teamArray[index] = team;
            index++;
        }
        return teamArray;
    }

}
