import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Basic sanity check for Trie.
 */
public class TestTrie {

    private static Trie trie;

    @Before
    public void setUp() throws Exception {
        trie = new Trie();
    }

    @Test
    public void test() {
        String word1 = "7 ele";
        String word2 = "99 cent";
        String word3 = "1300 Tenth";
        trie.insert(word1, 1, 0, 0);
        trie.insert(word2, 2, 0, 0);
        trie.insert(word3, 3, 0, 0);
        Trie.TrieNode node = trie.startsWith(" ");
        assertEquals("Should have 3 children", 3, node.extraInfo.size());
    }
}
