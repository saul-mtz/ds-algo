package common.tree;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Test for the AVL Tree data Structure
 */
public class AvlTreeTest {

    /**
     * Basic expected behaviour
     */
    @Test
    public void testDefaultBehavior() {
        AvlTree tree = new AvlTree();
        /*
          1
         / \
        2   3
         */
        tree.add(1);
        assertEquals(tree.root.data.toString(), "1");
        assertEquals(tree.getHeight(), 0);
        assertEquals(tree.toString(), "[1]");
        tree.add(3);
        assertEquals(tree.getHeight(), 1);
        assertEquals(tree.toString(), "[1, 3]");
        tree.add(2);
        assertEquals(tree.getHeight(), 1);
        assertEquals(tree.toString(), "[1, 2, 3]");
        tree.add(4);
        tree.add(5);
        tree.add(6);
        tree.add(7);
        /*
             4
           /   \
          2     6
         / \   / \
        1   3 5   7
         */
        assertEquals(tree.root.data.toString(), "4");
        assertEquals(tree.getHeight(), 2);
        assertEquals(tree.toString(), "[1, 2, 3, 4, 5, 6, 7]");

        tree.add(8);
        tree.add(9);
        tree.add(10);
        tree.add(11);
        tree.add(12);
        tree.add(13);
        tree.add(14);
        tree.add(15);
        /*
                    8
                  /   \
                 /     \
                /       \
               /         \
              /           \
             4            12
           /   \       /      \
          2     6     10      14
         / \   / \   /  \    /  \
        1   3 5   7 9    11 13  15
         */
        assertEquals(tree.root.data.toString(), "8");
        assertEquals(tree.getHeight(), 3);
        assertEquals(tree.toString(), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]");

        tree = new AvlTree();
        tree.add(5);
        tree.add(3);
        tree.add(4);
        tree.add(15);
        tree.add(14);
        tree.add(13);
        tree.add(12);
        tree.add(11);
        tree.add(10);
        tree.add(9);
        tree.add(8);
        tree.add(7);
        tree.add(6);
        tree.add(2);
        tree.add(1);

        // @TODO: The optimal solution must be the same result as the previous test
        //assertEquals(tree.getHeight(), 3);
        //assertEquals(tree.root.data.toString(), "8");
        assertTrue(tree.isBalanced());
        assertEquals(tree.toString(), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]");
    }

}
