package common.tree;

import org.junit.Test;

import static junit.framework.TestCase.*;

/**
 * Test for the Binary Search Tree Data Structure
 */
public class BinarySearchTreeTest {

    BinarySearchTree tree;

    @Test
    public void testEmptyTree() {
        tree = new BinarySearchTree();

        // default values when the tree is empty
        assertEquals(0, tree.getHeight());
        assertEquals("[]", tree.toString());
    }

    @Test
    public void testInsertion() {
        tree = new BinarySearchTree();

        // single node
        tree.add(1);
        assertEquals(Integer.valueOf(1), tree.root.getValue());
        assertEquals(0, tree.getHeight());
        assertEquals("[1]", tree.toString());

        /*
         1
          \
           2
            \
             3
         */
        tree = new BinarySearchTree();
        tree.add(1);
        tree.add(2);
        tree.add(3);
        assertEquals(Integer.valueOf(1), tree.root.getValue());
        assertEquals(2, tree.getHeight());
        assertEquals("[1, 2, 3]", tree.toString());

        /*
            3
           /
          2
         /
        1
         */

        tree = new BinarySearchTree();
        tree.add(3);
        tree.add(2);
        tree.add(1);
        assertEquals(Integer.valueOf(3), tree.root.getValue());
        assertEquals(2, tree.getHeight());
        assertEquals("[1, 2, 3]", tree.toString());

        /*
          2
         / \
        1   3
         */
        tree = new BinarySearchTree();
        tree.add(2);
        tree.add(1);
        tree.add(3);
        assertEquals(Integer.valueOf(2), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[1, 2, 3]", tree.toString());

    }

    @Test
    public void testDeletion() {
        tree = new BinarySearchTree();

        // delete the only tree node
        tree.add(1);
        assertRemoved(tree.remove(1), 1);
        assertEquals(0, tree.getHeight());
        assertEquals("[]", tree.toString());

        // try to delete an not existent node
        assertNull(tree.remove(2));

        tree = new BinarySearchTree();
        /*
         1          1          1          1          1          4
          \          \          \          \          \          \
           2    ->    2    ->    2    ->    4    ->    4    ->    5
            \                     \                     \
             3                     4                     5
         */
        tree.add(1);
        tree.add(2);
        tree.add(3);

        assertRemoved(tree.remove(3), 3);
        assertEquals(Integer.valueOf(1), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[1, 2]", tree.toString());

        tree.add(4);
        assertRemoved(tree.remove(2), 2);
        assertEquals(Integer.valueOf(1), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[1, 4]", tree.toString());

        tree.add(5);
        assertRemoved(tree.remove(1), 1);
        assertEquals(Integer.valueOf(4), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[4, 5]", tree.toString());

        tree = new BinarySearchTree();
        /*
             3          3          3          3          3          4
            /          /          /          /          /          /
           2    ->    2    ->    2    ->    1    ->    1    ->    0
          /                     /                     /
         1                     1                     0
         */
        tree.add(3);
        tree.add(2);
        tree.add(1);
        assertRemoved(tree.remove(1), 1);
        assertEquals(Integer.valueOf(3), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[2, 3]", tree.toString());
        tree.add(1);
        assertRemoved(tree.remove(2), 2);
        assertEquals(Integer.valueOf(3), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[1, 3]", tree.toString());
        tree.add(0);
        assertRemoved(tree.remove(3), 3);
        assertEquals(Integer.valueOf(1), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[0, 1]", tree.toString());

        tree = new BinarySearchTree();
        /*
             2              2              2              2              2              4
            / \     ->       \     ->     / \     ->     /       ->     / \     ->     /
           1   3              3          0   3          0              0   4          0
         */
        tree.add(2);
        tree.add(1);
        tree.add(3);
        assertRemoved(tree.remove(1), 1);
        assertEquals(Integer.valueOf(2), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[2, 3]", tree.toString());
        tree.add(0);
        assertRemoved(tree.remove(3), 3);
        assertEquals(Integer.valueOf(2), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[0, 2]", tree.toString());
        tree.add(4);
        assertRemoved(tree.remove(2), 2);
        assertEquals(Integer.valueOf(4), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[0, 4]", tree.toString());

        tree = new BinarySearchTree();
        /*
             4             4             4             3
            /      1      /             /      4      /
           1      -->    2      -->    2      -->    2
            \                    3      \
             2                           3
         */
        tree.add(4);
        tree.add(1);
        tree.add(2);
        assertRemoved(tree.remove(1), 1);
        assertEquals(Integer.valueOf(4), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[2, 4]", tree.toString());
        tree.add(3);
        assertRemoved(tree.remove(4), 4);
        assertEquals(1, tree.getHeight());
        assertEquals("[2, 3]", tree.toString());


        tree = new BinarySearchTree();
        /*
          1             1             1             2
           \      4      \             \      1      \
            4    -->      3    -->      3    -->      3
           /                    2      /
          3                           2
         */
        tree.add(1);
        tree.add(4);
        tree.add(3);
        tree.remove(4);
        assertEquals(Integer.valueOf(1), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[1, 3]", tree.toString());
        tree.add(2);
        tree.remove(1);
        assertEquals(1, tree.getHeight());
        assertEquals("[2, 3]", tree.toString());
    }

    private void assertRemoved(TreeNode removed, Integer value) {
        assertNotNull(removed);
        assertNull(removed.getParent());
        assertNull(removed.getLeft());
        assertNull(removed.getRight());
        assertEquals(removed.getValue(), value);
    }

    @Test
    public void testDefault() {
        tree = new BinarySearchTree();

        // lca algorithm
        tree.add(2);
        tree.add(1);
        tree.add(3);
        tree.add(5);
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
        assertNull(tree.find(0));

        TreeNode fiveNode = tree.find(5);
        assertNotNull(fiveNode);
        assertEquals(Integer.valueOf(5), fiveNode.getValue());
        assertEquals(Integer.valueOf(3), fiveNode.getParent().getValue());
        assertEquals(Integer.valueOf(4), fiveNode.getLeft().getValue());
        assertEquals(Integer.valueOf(6), fiveNode.getRight().getValue());


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
