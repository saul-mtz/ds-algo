package common.tree;

import org.junit.Test;

import static junit.framework.TestCase.*;

/**
 * Test for the Binary Search Tree Data Structure
 */
public class BinarySearchTest {

    /**
     * Basic expected behaviour
     */
    @Test
    public void testDefaultBehavior() {
        BinarySearch tree = new BinarySearch();
        tree.add(2);
        assertEquals("[2]", tree.toString());
        assertEquals(0, tree.getHeight());

        tree.add(3);
        assertEquals("[2, 3]", tree.toString());
        assertEquals(1, tree.getHeight());

        tree.add(1);
        assertEquals("[1, 2, 3]", tree.toString());
        assertEquals(1, tree.getHeight());

        tree.add(5);
        assertEquals("[1, 2, 3, 5]", tree.toString());
        assertEquals(2, tree.getHeight());

        // lca algorithm
        tree.add(4);
        tree.add(6);
        /*
          2
         / \
        1   3
             \
              5
             / \
            4   6
         */
        assertEquals("[1, 2, 3, 4, 5, 6]", tree.toString());
        assertEquals(Integer.valueOf(5), tree.lca(4, 6).data);
        assertEquals(Integer.valueOf(5), tree.lca(6, 4).data);
        assertEquals(Integer.valueOf(3), tree.lca(3, 4).data);
        assertEquals(Integer.valueOf(3), tree.lca(6, 3).data);
        assertEquals(Integer.valueOf(2), tree.lca(1, 6).data);
        assertNull(tree.lca(1, 10));
        assertNull(tree.lca(10, 1));
        assertNull(tree.lca(10, 100));
    }

}
