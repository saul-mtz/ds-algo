package common.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Node class for a Binary Search Tree
 *
 * œauthor saul.mtz.v
 */
public class TreeNode {

    // internal index for the node
    private int index;

    // data to be saved
    Integer data;

    // node relationships
    private TreeNode parent;
    private TreeNode left;
    private TreeNode right;

    // node metadata
    private Integer  height;    // height of the node as subtree

    TreeNode(Integer data, TreeNode parent) {
        this.parent = parent;
        this.data   = data;
        this.height = 0;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public int getHeight() {
        if (null == height) {
            int heightLeft  = null == left  ? -1 : left.getHeight();
            int heightRight = null == right ? -1 : right.getHeight();

            height = Math.max(heightLeft, heightRight) + 1;
        }
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean hasLeft() {
        return null != left;
    }

    public boolean hasRight() {
        return null != right;
    }

    public Integer getValue() {
        return data;
    }
    public void setValue(Integer value) {
        data = value;
    }
    public String toString() {
        return inOrder().toString();
    }

    public List<TreeNode> getAncestors() {
        List<TreeNode> ancestors = new ArrayList<>();
        TreeNode node = this;
        do {
            ancestors.add(node);
            node = node.getParent();
        } while (null != node);
        return ancestors;
    }

    public List inOrder() {

        List<Integer> values = new ArrayList<>();

        if (null != left) {
            values.addAll(left.inOrder());
        }

        values.add(data);
        if (null != right) {
            values.addAll(right.inOrder());
        }

        return values;
    }

    public List inOrderIndex() {

        List<Integer> values = new ArrayList<>();

        if (null != left) {
            values.addAll(left.inOrderIndex());
        }

        values.add(index);
        if (null != right) {
            values.addAll(right.inOrderIndex());
        }

        return values;
    }

    public int getSize() {
        return 1 + (null == left ? 0 : left.inOrder().size()) + (null == right ? 0 : right.inOrder().size());
    }
}
