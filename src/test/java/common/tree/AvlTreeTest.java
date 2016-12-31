package common.tree;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.*;

/**
 * Test for the AVL Tree data Structure
 */
public class AvlTreeTest extends BinarySearchTreeTest {

    public void testInsertion() {
        super.testInsertion();
        AvlTree tree = getBigTree(8);
        TreeNode left = tree.root.getLeft();
        TreeNode right = tree.root.getRight();
        assertEquals(left.getParent(), tree.root);
        assertEquals(right.getParent(), tree.root);
        assertEquals(right.getLeft().getParent(), right);
        assertEquals(right.getRight().getParent(), right);
        assertEquals(left.getLeft().getParent(), left);
        assertEquals(left.getRight().getParent(), left);
    }

    public void testDeletion() {
        BinarySearchTree tree = getLeftLeftTree();
        tree.remove(1);
        assertEquals(tree.toString(), "[2, 3]");
        assertEquals(tree.root.indexMin, 1);
        assertEquals(tree.root.indexMax, 2);

        tree = getLeftLeftTree();
        tree.remove(2);
        assertEquals(tree.toString(), "[1, 3]");
        assertEquals(tree.root.indexMin, 1);
        assertEquals(tree.root.indexMax, 2);

        tree = getLeftLeftTree();
        tree.remove(3);
        assertEquals(tree.toString(), "[1, 2]");
        assertEquals(tree.root.indexMin, 1);
        assertEquals(tree.root.indexMax, 2);

        tree = getLeftLeftTree();
        tree.removeNode(tree.find(1), false);
        assertEquals(tree.toString(), "[2, 3]");

        tree = getLeftLeftTree();
        tree.removeNode(tree.find(2), false);
        assertEquals(tree.toString(), "[1, 3]");

        tree = getLeftLeftTree();
        tree.removeNode(tree.find(3), false);
        assertEquals(tree.toString(), "[1, 2]");

        tree = getBigTree(8);
        tree.removeNode(tree.find(2), false);
        assertEquals(tree.toString(), "[1, 3, 4, 5, 6, 7, 8]");
    }

    public void testDefault() {}

    @Test
    /*
    balanced using one single right rotation
        3
       /            2
      2     -->    / \
     /            1   3
    1
     */
    public void testLeftLeftCase() {

        AvlTree tree = (AvlTree) getLeftLeftTree();

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
    /*
    balanced using one single right rotation
    1
     \              2
      2     -->    / \
       \          1   3
        3
     */
    public void testRightRightCase() {

        AvlTree tree = (AvlTree) getRightRightTree();

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
    /*
    balanced using one single right rotation
      3
     /            2
    1     -->    / \
     \          1   3
      2
     */
    public void testLeftRightCase() {

        AvlTree tree = (AvlTree) getLeftRightTree();
        assertEquals(1, tree.getHeight());
        assertEquals(tree.root.getValue(), Integer.valueOf(2));
        assertEquals(tree.toString(), "[1, 2, 3]");
    }

    @Test
    /*
    balanced using one single right rotation
    1
     \            2
      3   -->    / \
     /          1   3
    2
     */
    public void testRightLeftCase() {
        AvlTree tree = (AvlTree) getRightLeftTree();
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

        tree = new AvlTree();
        list.clear();
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 1023, j = 1; i > 0; i --, j ++) {
            list.add(0, i);
            indexes.add(j);
            assertAdd(tree, i);
            assertEquals(tree.toString(), list.toString());
            tree.resetIndex();
            assertEquals(tree.root.inOrderIndex().toString(), indexes.toString());
        }
    }

    protected BinarySearchTree getTree() {
        return new AvlTree();
    }

    private AvlTree getBigTree(int numberNodes) {
        AvlTree tree = new AvlTree();
        for (int i = 1; i <= numberNodes; i ++) {
            assertAdd(tree, i);
        }
        return tree;
    }

    protected void assertAdd(BinarySearchTree tree, Integer n) {
        super.assertAdd(tree, n);
        assertTrue(((AvlTree)tree).isBalanced());
    }

    protected void assertRemove(BinarySearchTree tree, Integer n) {
        super.assertRemove(tree, n);
        assertTrue(((AvlTree)tree).isBalanced());
    }

}
