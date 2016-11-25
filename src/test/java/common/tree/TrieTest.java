package common.tree;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Test for the Trie data Structure
 */
public class TrieTest {

    Trie trie = new Trie();

    public void testDefaultBehavior() {

        trie.add("A");
        trie.add("to");
        trie.add("tea");
        trie.add("ted");
        trie.add("ten");
        trie.add("i");
        trie.add("in");
        trie.add("inn");

        // size and structure
        assertFalse(trie.isEmpty());
        assertEquals(trie.size(), 8);

        // search
        assertEquals(trie.search("A").size(), 1);
        assertEquals(trie.search("in").size(), 2);
        assertEquals(trie.search("t").size(), 4);
        assertEquals(trie.search("te").size(), 3);
        assertEquals(trie.search("to").size(), 1);

        // values tests
        assertFalse(trie.contains(""));
        assertFalse(trie.contains(null));
        assertFalse(trie.contains("O"));
        assertFalse(trie.contains("Other"));
        assertFalse(trie.contains("innn"));
        assertTrue(trie.contains("A"));
        assertTrue(trie.contains("ten"));

        // lists
        assertEquals("[i, in, inn]", trie.startsWith("i").toString());
        assertEquals("[tea, ted, ten]", trie.startsWith("te").toString());
        assertEquals("[A, i, in, inn, tea, ted, ten, to]", trie.values().toString());
    }
}
