package common.tree;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.*;

/**
 * Test for the Binary Search Tree Data Structure
 */
public class BinarySearchTreeTest {

    @Test
    public void testEmptyTree() {
        BinarySearchTree tree = getTree();

        // default values when the tree is empty
        assertEquals(0, tree.getHeight());
        assertEquals("[]", tree.toString());
    }

    @Test
    public void testInsertion() {
        BinarySearchTree tree = getTree();

        // single node
        tree.add(1);
        assertEquals(Integer.valueOf(1), tree.root.getValue());
        assertEquals(0, tree.getHeight());
        assertEquals("[1]", tree.toString());

        assertEquals("[1, 2, 3]", getRightRightTree().toString());
        assertEquals("[1, 2, 3]", getLeftLeftTree().toString());
        assertEquals("[1, 2, 3]", getLeftRightTree().toString());
        assertEquals("[1, 2, 3]", getRightLeftTree().toString());
        assertEquals("[1, 2, 3]", getTriangleTree().toString());

    }

    @Test
    public void testDeletion() {
        BinarySearchTree tree = getTree();

        // delete the only tree node
        tree.add(1);
        assertRemove(tree, 1);
        assertEquals(0, tree.getHeight());
        assertEquals("[]", tree.toString());

        // try to delete an not existent node
        assertNull(tree.remove(2));

        /*
         1          1          1          1          1          4
          \          \          \          \          \          \
           2    ->    2    ->    2    ->    4    ->    4    ->    5
            \                     \                     \
             3                     4                     5
         */

        tree = getRightRightTree();
        assertRemove(tree, 3);
        assertEquals(Integer.valueOf(1), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[1, 2]", tree.toString());

        tree.add(4);
        assertRemove(tree, 2);
        assertEquals(Integer.valueOf(1), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[1, 4]", tree.toString());

        tree.add(5);
        assertRemove(tree, 1);
        assertEquals(Integer.valueOf(4), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[4, 5]", tree.toString());

        tree = getLeftLeftTree();
        /*
             3          3          3          3          3          4
            /          /          /          /          /          /
           2    ->    2    ->    2    ->    1    ->    1    ->    0
          /                     /                     /
         1                     1                     0
         */
        assertRemove(tree, 1);
        assertEquals(Integer.valueOf(3), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[2, 3]", tree.toString());
        tree.add(1);
        assertRemove(tree, 2);
        assertEquals(Integer.valueOf(3), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[1, 3]", tree.toString());
        tree.add(0);
        assertRemove(tree, 3);
        assertEquals(Integer.valueOf(1), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[0, 1]", tree.toString());

        tree = getTree();
        /*
             2              2              2              2              2              4
            / \     ->       \     ->     / \     ->     /       ->     / \     ->     /
           1   3              3          0   3          0              0   4          0
         */
        tree.add(2);
        tree.add(1);
        tree.add(3);
        assertRemove(tree, 1);
        assertEquals(Integer.valueOf(2), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[2, 3]", tree.toString());
        tree.add(0);
        assertRemove(tree, 3);
        assertEquals(Integer.valueOf(2), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[0, 2]", tree.toString());
        tree.add(4);
        assertRemove(tree, 2);
        assertEquals(Integer.valueOf(4), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[0, 4]", tree.toString());

        tree = getTree();
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
        assertRemove(tree, 1);
        assertEquals(Integer.valueOf(4), tree.root.getValue());
        assertEquals(1, tree.getHeight());
        assertEquals("[2, 4]", tree.toString());
        tree.add(3);
        assertRemove(tree, 4);
        assertEquals(1, tree.getHeight());
        assertEquals("[2, 3]", tree.toString());


        tree = getTree();
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

    @Test
    public void testBigTree() {
        BinarySearchTree tree = new BinarySearchTree();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < 1024; i ++) {
            list.add(i);
            assertAdd(tree, i);
            assertEquals(tree.toString(), list.toString());
        }

        for (int i = 1023; i >= 1; i --) {
            list.remove(Integer.valueOf(i));
            assertRemove(tree, i);
            assertEquals(tree.toString(), list.toString());
        }
        assertNull(tree.root);
        assertEquals(tree.getHeight(), 0);

    }

    @Test
    public void testDefault() {
        BinarySearchTree tree = getTree();

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
        assertFalse(tree.contains(0));

        TreeNode fiveNode = tree.find(5);
        assertNotNull(fiveNode);
        assertEquals(Integer.valueOf(5), fiveNode.getValue());
        assertEquals(Integer.valueOf(3), fiveNode.getParent().getValue());
        assertEquals(Integer.valueOf(4), fiveNode.getLeft().getValue());
        assertEquals(Integer.valueOf(6), fiveNode.getRight().getValue());
    }

    protected BinarySearchTree getTree() {
        return new BinarySearchTree();
    }

    protected void assertAdd(BinarySearchTree tree, Integer n) {
        TreeNode added = tree.add(n);
        assertNotNull(added);
        assertEquals(added.getValue(), n);

        assertNotNull(tree);
        TreeNode find = tree.find(n);
        assertEquals(added, find);
    }

    protected void assertRemove(BinarySearchTree tree, Integer n) {
        Integer removed = tree.remove(n);
        assertNotNull(removed);
        assertEquals(removed, n);
    }

    /*
        3
       /
      2
     /
    1
     */
    protected BinarySearchTree getLeftLeftTree() {
        BinarySearchTree tree = getTree();
        assertAdd(tree, 3);
        assertAdd(tree, 2);
        assertAdd(tree, 1);
        tree.root.reindex(1);
        return tree;
    }

    /*
     1
      \
       2
        \
         3
     */
    protected BinarySearchTree getRightRightTree() {
        BinarySearchTree tree = getTree();
        assertAdd(tree, 1);
        assertAdd(tree, 2);
        assertAdd(tree, 3);

        return tree;
    }

    /*
      2
     / \
    1   3
     */
    protected BinarySearchTree getTriangleTree() {
        BinarySearchTree tree = getTree();
        assertAdd(tree, 2);
        assertAdd(tree, 1);
        assertAdd(tree, 3);

        return tree;
    }

    /*
      3
     /
    1
     \
      2
     */
    protected BinarySearchTree getLeftRightTree() {
        BinarySearchTree tree = getTree();
        assertAdd(tree, 3);
        assertAdd(tree, 1);
        assertAdd(tree, 2);
        return tree;
    }

    /*
    1
     \
      3
     /
    2
     */
    protected BinarySearchTree getRightLeftTree() {
        BinarySearchTree tree = getTree();
        assertAdd(tree, 1);
        assertAdd(tree, 3);
        assertAdd(tree, 2);
        return tree;
    }
}