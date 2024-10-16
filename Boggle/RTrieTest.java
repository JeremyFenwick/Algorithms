import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RTrieTest {
    @Test
    void wordEntry() {
        var dict = new RTrie(26, 10);
        dict.put("HELLO", 2);
        dict.put("HELIUM", 3);
        var word = dict.search("HELLO");
        var prefix = dict.search("HEL");
        var nonExistingWord = dict.search("NO");
    }

    @Test
    void qEntry() {
        var dict = new RTrie(26, 10);
        dict.put("QUICK", 2);
        var quickResult = dict.search("QUICK");
        var prefixResult = dict.search("Q");
    }
}