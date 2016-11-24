package algorithms.tree;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

/**
 * Created by saul on 11/10/16.
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

        assertFalse(trie.isEmpty());
        assertEquals("[A, inn, tea, ted, ten, to]", trie.inOrder().toString());
        System.out.println(trie.toString());
    }
}
