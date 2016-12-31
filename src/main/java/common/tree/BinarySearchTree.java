package common.tree;

/**
 * @link https://en.wikipedia.org/wiki/Binary_search_tree
 */
public class BinarySearchTree {

    public TreeNode root;

    public TreeNode add(Integer value) {
        if (null == root) {
            root = new TreeNode(value);
            root.setParent(null);
            return root;
        } else {
            return insert(root, root, true, value);
        }
    }

    public Integer remove(Integer value) {
        if (null == root) {
            return null;
        }
        TreeNode removed = removeTmp(value, 0, false);
        return null == removed ? null : removed.getValue();
    }

    public TreeNode removeNode(TreeNode node, boolean removeChildren) {
        if (node.isRoot) {
            root = node.remove(removeChildren);
            return node;
        } else {
            return node.remove(removeChildren);
        }
    }

    /**
     * Remove a node
     *
     * @param value     value of the node to remove
     * @param removeAll if true remove the whole subtree
     * @return
     */
    private TreeNode removeTmp(Integer value, int key, boolean removeAll) {

        TreeNode toRemove = root.find(value, true, key);

        // the node to delete does not exist
        return null == toRemove ? null : removeNode(toRemove, removeAll);
    }

    protected void resetIndex() {
        root.reindex(1);
    }

    public int getHeight() {
        return null == root ? 0 : root.getHeight();
    }

    public String toString() {
        return null == root ? "[]" : root.toString();
    }

    private TreeNode insert(TreeNode node, TreeNode parent, boolean isLeft, Integer value) {
        if (null == node) {
            node = new TreeNode(value);
            if (isLeft) {
                parent.setLeft(node);
            } else {
                parent.setRight(node);
            }
            return node;
        } else if (value < node.data) {
            return insert(node.getLeft(), node, true, value);
        } else {
            return insert(node.getRight(), node, false, value);
        }
    }

    public boolean contains(Integer value) {
        return null != root.find(value, true, 0);
    }

    public TreeNode find(Integer value) {
        return root.find(value, true, 0);
    }
}