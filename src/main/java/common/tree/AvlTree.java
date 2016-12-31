package common.tree;

/**
 * AVL Tree
 *
 * @link https://en.wikipedia.org/wiki/AVL_tree
 * @author saul.martinez
 */
public class AvlTree extends BinarySearchTree {

    public TreeNode add(Integer value) {

        TreeNode newTreeNode = super.add(value);
        balance(newTreeNode);
        return newTreeNode;
    }

    public Integer remove(Integer value) {
        TreeNode find = root.find(value, true, 0);
        if (null != find) {
            TreeNode parent = find.getParent();
            Integer removed = super.remove(value);
            if (null != parent) {
                balance(parent);
            }
            return removed;
        }
        return null;
    }

    public boolean isBalanced() {
        return null == root || root.getBalanceFactor() > -2 && root.getBalanceFactor() < 2;
    }

    public void balance(TreeNode node) {
        while (null != node) {
            // check the balance factor and do rotations if needed
            int balanceFactor = node.getBalanceFactor();
            if (balanceFactor < -1 || balanceFactor > 1) {
                balance(node, balanceFactor);
            }

            node = node.getParent();
        }
    }

    private void balance(TreeNode n, int balanceFactor) {
        if (balanceFactor < -1) {
            if (n.getRight().getBalanceFactor() < 0) {
                // right right case
                rotateLeft(n);
            } else {
                // right left case
                rotateRight(n.getRight());
                rotateLeft(n);
            }
        } else {
            if (n.getLeft().getBalanceFactor() < 0) {
                // left right case
                rotateLeft(n.getLeft());
                rotateRight(n);
            } else {
                // left left case
                rotateRight(n);
            }
        }
    }

    /**
     * Left rotation taken from https://en.wikipedia.org/wiki/Tree_rotation#Illustration
     * @param node
     */
    private void rotateLeft(TreeNode node) {
        TreeNode pivot = node.getRight();
        TreeNode parent = node.getParent();
        boolean isLeft = node.isLeft;

        if (null == pivot.getLeft()) {
            node.setRight(null);
        } else {
            node.setRight(pivot.getLeft().remove(true));
        }
        pivot.setLeft(node);

        if (null == parent) {
            root = pivot;
            pivot.setParent(null);
        } else {
            if (isLeft) {
                parent.setLeft(pivot);
            } else {
                parent.setRight(pivot);
            }
        }
    }

    /**
     * Right node rotation, taken from https://en.wikipedia.org/wiki/Tree_rotation#Illustration
     * @param node
     */
    private void rotateRight(TreeNode node) {
        TreeNode pivot = node.getLeft();
        TreeNode parent = node.getParent();
        boolean isLeft = node.isLeft;

        if (null == pivot.getRight()) {
            node.setLeft(null);
        } else {
            node.setLeft(pivot.getRight().remove(true));
        }
        pivot.setRight(node);

        if (null == parent) {
            root = pivot;
            pivot.setParent(null);
        } else {
            if (isLeft) {
                parent.setLeft(pivot);
            } else {
                parent.setRight(pivot);
            }
        }
    }
}