package common.tree;

/**
 * AVL Tree
 *
 * @link https://en.wikipedia.org/wiki/AVL_tree
 * @author saul.martinez
 */
public class AvlTree extends BinarySearchTree {

    private boolean indexEnabled = false;

    public TreeNode add(Integer value) {

        TreeNode newTreeNode = super.add(value);
        balance(newTreeNode);
        if (indexEnabled) {
            updateIndexes(root, 1);
        }
        root.setHeight(null);
        return newTreeNode;
    }

    public TreeNode remove(Integer value) {
        TreeNode find = find(value);
        if (null != find) {
            TreeNode parent = find.getParent();
            TreeNode removed = super.remove(value);
            if (null != parent) {
                parent.setHeight(null);
                balance(parent);
            }
            return removed;
        }
        return null;
    }

    /**
     * When set to true an integer index is automatically asigned to each value of the tree
     * Default value is false
     */
    public void enableIndex() {
        indexEnabled = true;
    }

    public boolean isBalanced() {
        int balanceFactor = balanceFactor(root);
        return balanceFactor > -2 && balanceFactor < 2;
    }

    private void updateIndexes(TreeNode node, int offset) {
        if (node.hasLeft()) {
            updateIndexes(node.getLeft(), offset);
            offset += node.getLeft().getSize();
        }

        node.setIndex(offset);

        if (node.hasRight()) {
            updateIndexes(node.getRight(), offset + 1);
        }
    }

    private void balance(TreeNode node) {
        for (TreeNode ancestor : node.getAncestors()) {
            // check the balance factor and do rotations if needed
            int balanceFactor = balanceFactor(ancestor);
            if (-2 == balanceFactor || 2 == balanceFactor) {
                balance(ancestor, balanceFactor);
            }
        }
    }

    private int balanceFactor(TreeNode n) {
        if (null == root) {
            return 0;
        }
        return (null == n.getLeft() ? -1 : n.getLeft().getHeight()) - (null == n.getRight() ? -1 : n.getRight().getHeight());
    }

    private void balance(TreeNode n, int balanceFactor) {
        if (-2 == balanceFactor) {
            if (-1 == balanceFactor(n.getRight())) {
                // right right case
                rotateLeft(n);
            } else {
                // right left case
                rotateRight(n.getRight());
                rotateLeft(n);
            }
        } else {
            if (-1 == balanceFactor(n.getLeft())) {
                // left right case
                rotateLeft(n.getLeft());
                rotateRight(n);
            } else {
                // left left case
                rotateRight(n);
            }
        }
    }

    private void rotateLeft(TreeNode n) {
        TreeNode nParent = n.getParent();
        TreeNode nRight = n.getRight();

        n.setParent(n.getRight());
        n.setRight(n.getRight().getLeft());

        nRight.setParent(nParent);
        nRight.setLeft(n);

        if (null == nParent) {
            root = nRight;
        } else {
            if (null != nParent.getLeft() && nParent.getLeft().equals(n)) {
                nParent.setLeft(nRight);
            } else {
                nParent.setRight(nRight);
            }
        }
        nRight.setHeight(null);
        n.setHeight(null);
    }

    private void rotateRight(TreeNode n) {
        TreeNode nParent = n.getParent();
        TreeNode nLeft = n.getLeft();

        n.setParent(n.getLeft());
        n.setLeft(n.getLeft().getRight());

        nLeft.setParent(nParent);
        nLeft.setRight(n);

        if (null == nParent) {
            root = nLeft;
        } else {
            if (null != nParent.getLeft() && nParent.getLeft().equals(n)) {
                nParent.setLeft(nLeft);
            } else {
                nParent.setRight(nLeft);
            }
        }
        nLeft.setHeight(null);
        n.setHeight(null);
    }
}