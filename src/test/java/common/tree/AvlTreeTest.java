package common.tree;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.*;

/**
 * Test for the AVL Tree data Structure
 */
public class AvlTreeTest {

    @Test
    public void testLeftLeftCase() {

        AvlTree tree = new AvlTree();
        /*
        balanced using one single right rotation
            3
           /            2
          2     -->    / \
         /            1   3
        1
         */
        assertAdd(tree, 3);
        assertAdd(tree, 2);
        assertAdd(tree, 1);

        assertEquals(1, tree.getHeight());
        assertEquals(tree.toString(), "[1, 2, 3]");

        // test the relationships
        TreeNode root = tree.root;
        TreeNode left = tree.root.getLeft();
        TreeNode right = tree.root.getRight();
        assertEquals(root.getValue(), Integer.valueOf(2));
        assertEquals(left.getParent(), root);
        assertEquals(right.getParent(), root);
        assertNull(left.getLeft());
        assertNull(left.getRight());
        assertNull(right.getLeft());
        assertNull(right.getRight());

        assertAdd(tree, 0);
        assertAdd(tree, -1);

        assertEquals(2, tree.getHeight());
        assertEquals(tree.root.getValue(), Integer.valueOf(2));
        assertEquals(tree.toString(), "[-1, 0, 1, 2, 3]");
    }

    @Test
    public void testRightRightCase() {

        AvlTree tree = new AvlTree();
        /*
        balanced using one single right rotation
        1
         \              2
          2     -->    / \
           \          1   3
            3
         */
        assertAdd(tree, 1);
        assertAdd(tree, 2);
        assertAdd(tree, 3);

        assertEquals(1, tree.getHeight());
        assertEquals(tree.toString(), "[1, 2, 3]");
        TreeNode root = tree.root;
        TreeNode left = tree.root.getLeft();
        TreeNode right = tree.root.getRight();
        assertEquals(root.getValue(), Integer.valueOf(2));
        assertEquals(left.getParent(), root);
        assertEquals(right.getParent(), root);
        assertNull(left.getLeft());
        assertNull(left.getRight());
        assertNull(right.getLeft());
        assertNull(right.getRight());

        assertAdd(tree, 4);
        assertAdd(tree, 5);

        assertEquals(2, tree.getHeight());
        assertEquals(tree.root.getValue(), Integer.valueOf(2));
        assertEquals(tree.toString(), "[1, 2, 3, 4, 5]");
    }

    @Test
    public void testLeftRightCase() {

        AvlTree tree = new AvlTree();
        /*
        balanced using one single right rotation
          3
         /            2
        1     -->    / \
         \          1   3
          2
         */
        assertAdd(tree, 3);
        assertAdd(tree, 1);
        assertAdd(tree, 2);

        assertEquals(1, tree.getHeight());
        assertEquals(tree.root.getValue(), Integer.valueOf(2));
        assertEquals(tree.toString(), "[1, 2, 3]");
    }

    @Test
    public void testRightLeftCase() {

        AvlTree tree = new AvlTree();
        /*
        balanced using one single right rotation
        1
         \            2
          3   -->    / \
         /          1   3
        2
         */
        assertAdd(tree, 1);
        assertAdd(tree, 3);
        assertAdd(tree, 2);

        assertEquals(1, tree.getHeight());
        assertEquals(tree.root.getValue(), Integer.valueOf(2));
        assertEquals(tree.toString(), "[1, 2, 3]");
    }

    @Test
    public void testBigTree() {
        AvlTree tree = new AvlTree();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < 1024; i ++) {
            list.add(i);
            assertAdd(tree, i);
            assertEquals(tree.toString(), list.toString());
        }

        assertEquals(tree.getHeight(), 9);
        for (int i = 1023; i >= 1; i --) {
            list.remove(Integer.valueOf(i));
            assertRemove(tree, i);
            assertEquals(tree.toString(), list.toString());
        }
        assertNull(tree.root);
        assertEquals(tree.getHeight(), 0);

    }

    @Test
    public void keyEnabled() {
        AvlTree tree = new AvlTree();
        ArrayList<Integer> values = new ArrayList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        tree.enableIndex();
        int limit = 10;

        for (int i = 1; i < Math.pow(2, limit); i ++) {
            int expectedHeight = (int) (Math.log(i)/Math.log(2));
            assertAddWithKeyEnabled(tree, 2);
            values.add(2);
            indexes.add(i);
            assertEquals(expectedHeight, tree.getHeight());
            assertEquals(values.toString(), tree.toString());
            assertEquals(indexes.toString(), tree.root.inOrderIndex().toString());
        }
        int test = (int) Math.pow(2, limit) - 1;
        assertEquals(tree.root.getSize(), test);
    }

    private void assertAdd(AvlTree tree, Integer n) {
        TreeNode added = tree.add(n);
        assertNotNull(added);
        assertEquals(added.getValue(), n);

        assertNotNull(tree);
        TreeNode find = tree.find(n);
        assertEquals(added, find);
        assertTrue(tree.isBalanced());
    }

    private void assertRemove(AvlTree tree, Integer n) {
        TreeNode removed = tree.remove(n);
        assertNotNull(removed);
        assertNull(removed.getParent());
        assertNull(removed.getLeft());
        assertNull(removed.getRight());
        assertEquals(removed.getValue(), n);
        assertTrue(tree.isBalanced());
    }

    private void assertAddWithKeyEnabled(AvlTree tree, Integer n) {
        TreeNode added = tree.add(n);
        assertNotNull(added);
        assertEquals(added.getValue(), n);

        assertNotNull(tree);
        assertNotNull(tree.find(n));
        assertTrue(tree.isBalanced());
    }

}
