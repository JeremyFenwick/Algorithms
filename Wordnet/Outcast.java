import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordnet;

    public Outcast(WordNet wordnet)  {
        this.wordnet = wordnet;
    }
    public String outcast(String[] nouns) {
        String candidate = null;
        var distance = Integer.MAX_VALUE;

        for (var workingNoun : nouns) {
            var workingDistance = 0;
            for (var targetNoun : nouns) {
                if (workingNoun.equals(targetNoun)) {
                    continue;
                }
                workingDistance += wordnet.distance(workingNoun, targetNoun);
            }
            if (workingDistance < distance) {
                distance = workingDistance;
                candidate = workingNoun;
            }
        }

        return candidate;
    }
    public static void main(String[] args)  {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
