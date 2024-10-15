import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RTrieTest {
    @Test
    void wordEntry() {
        var dict = new RTrie<Integer>(26, 10);
        dict.put("HELLO", 2);
        dict.put("HELIUM", 3);
        var word1 = dict.get("HELLO");
        var nonExistingWord = dict.get("NO");
    }
}