import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WordNet {
    private final HashMap<String, Set<Integer>> nouns;
    private final Set<String>[] synsets;
    private final Digraph graph;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsetsData, String hypernymsData) {
        var synsetLines = readAllLines(synsetsData);
        nouns = new HashMap<>();
        synsets = (HashSet<String>[]) new HashSet[synsetLines.length];
        graph = new Digraph(synsetLines.length);
        checkRoot();
        loadSynsets(synsetLines);
        loadHypernyms(readAllLines(hypernymsData));
        sap = new SAP(graph);
    }

    private String[] readAllLines(String filename) {
        In in = new In(filename);
        String[] lines = in.readAllLines();
        in.close();
        return lines;
    }

    private void loadSynsets(String[] array) {
        for (var line : array) {
            var splitLine = line.split(",");
            var index = Integer.parseInt(splitLine[0]);
            var nounList = splitLine[1].split(" ");
            if (synsets[index] == null) {
                synsets[index] = new HashSet<String>();
            }
            for (var noun : nounList) {
                synsets[index].add(noun);
                if (!nouns.containsKey(noun)) {
                    nouns.put(noun, new HashSet<Integer>());
                }
                nouns.get(noun).add(index);
            }
        }
    }

    private void loadHypernyms(String[] array) {
        for (var line : array) {
            var splitLine = line.split(",");
            if (splitLine.length < 2 ) {
                continue;
            }
            var index = Integer.parseInt(splitLine[0]);
            var nounSet = synsets[index];
            for (var i = 1; i < splitLine.length; i++) {
                var hypernym = Integer.parseInt(splitLine[i]);
                graph.addEdge(index, hypernym);
            }
        }
    }

    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return nouns.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    public String sap(String nounA, String nounB) {
        var ancestorIndex = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        var nounSet = synsets[ancestorIndex];
        StringBuilder result = new StringBuilder();
        for (var noun : nounSet) {
            result.append(noun).append(" ");
        }
        return result.toString();
    }

    private void checkRoot() {
        for (int i = 0; i < graph.V(); i++) {
            if (graph.indegree(i) == 0) {
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        var wordnet = new WordNet(args[0], args[1]);
        var distance = wordnet.distance("a", "o");
        System.out.println(distance);
    }
}
