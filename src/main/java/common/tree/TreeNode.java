package common.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Node class for a Binary Search Tree
 *
 * @author saul.mtz.v
 */
public class TreeNode {

    // internal index for the node
    private int index;
    public int indexMin;
    public int indexMax;

    // node metadata
    public boolean isLeaf;      // true if this node is a leaf (has no children)
    public boolean isLeft;      // true if this node is a left child of another node
    public boolean isRoot;      // true if this node is the root of a tree
    private int height;         // height of the node as subtree

    // data to be saved
    Integer data;

    // node relationships
    private TreeNode parent;
    private TreeNode left;
    private TreeNode right;

    TreeNode(Integer value) {
        data   = value;
        isLeaf = true;
        height = 0;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
        isRoot = null == parent;
        this.isLeft = !isRoot && null != parent.getLeft() && parent.getLeft().equals(this);
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        if (null != getLeft() && null == left) {
            getLeft().setParent(null);
        }

        this.left = left;
        int updatedHeight;
        if (null != left) {
            left.setParent(this);
            isLeaf = false;
            updatedHeight = Math.max(null == right ? -1 : right.getHeight(), left.getHeight()) + 1;
        } else if (null == right) {
            isLeaf = true;
            updatedHeight = 0;
        } else {
            updatedHeight = right.getHeight() + 1;
        }

        if (updatedHeight != height) {
            height = updatedHeight;
            if (null != parent) {
                parent.updateHeight();
            }
        }
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        if (null != getRight() && null == right) {
            getRight().setParent(null);
        }

        this.right = right;
        int updatedHeight;
        if (null != right) {
            right.setParent(this);
            isLeaf = false;
            updatedHeight = Math.max(null == left ? -1 : left.getHeight(), right.getHeight()) + 1;
        } else if (null == left) {
            isLeaf = true;
            updatedHeight = 0;
        } else {
            updatedHeight = left.getHeight() + 1;
        }

        if (updatedHeight != height) {
            height = updatedHeight;
            if (null != parent) {
                parent.updateHeight();
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public Integer getValue() {
        return data;
    }

    public int getIndex() {
        return index;
    }

    public int getSize() {
        return 1 + (null == left ? 0 : left.inOrder().size()) + (null == right ? 0 : right.inOrder().size());
    }

    public boolean equals(TreeNode node) {
        return getValue().equals(node.getValue()) && getIndex() == node.getIndex();
    }

    public String toString() {
        return inOrder().toString();
    }

    private void updateHeight() {
        int heightLeft  = null == left  ? -1 : left.getHeight();
        int heightRight = null == right ? -1 : right.getHeight();

        int updatedHeight = Math.max(heightLeft, heightRight) + 1;

        if (updatedHeight != height) {
            height = updatedHeight;
            if (null != parent) {
                parent.updateHeight();
            }
        }
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

    public List<Integer> inOrder() {

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

    public void reindex(int offset) {
        indexMin = offset;
        if (null != left) {
            left.reindex(offset);
            offset += left.getSize();
        }

        index = offset;

        if (null != right) {
            right.reindex(offset + 1);
            offset += right.getSize();
        }
        indexMax = offset;
    }

    public TreeNode findMin() {
        TreeNode minTreeNode = this;
        while (null != minTreeNode.getLeft()) {
            minTreeNode = minTreeNode.getLeft();
        }
        return minTreeNode;
    }

    public TreeNode findMax() {
        TreeNode maxTreeNode = this;
        while (null != maxTreeNode.getRight()) {
            maxTreeNode = maxTreeNode.getRight();
        }
        return maxTreeNode;
    }

    public TreeNode remove() {
        return remove(false);
    }

    public TreeNode remove(boolean removeChildren) {
        TreeNode parent = this.getParent();
        TreeNode toReplace = null;

        // three possible cases according to: https://en.wikipedia.org/wiki/Binary_search_tree#Deletion
        if (null == this.getLeft() && null == this.getRight()) {
            // case 1: node with no children

            if (!isRoot) {
                if (isLeft) {
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
            } else {
                return null;
            }
        } else if ((null != this.getLeft() && null == this.getRight()) || (null == this.getLeft()  && null != this.getRight())) {

            if (removeChildren) {
                if (null != getParent()) {
                    if (isLeft) {
                        getParent().setLeft(null);
                    } else {
                        getParent().setRight(null);
                    }
                }
            } else {
                // case 2: Nodes with only one child
                if (null != this.getLeft()) {
                    TreeNode left = this.getLeft();
                    this.setLeft(null);
                    if (!isRoot) {
                        if (isLeft) {
                            parent.setLeft(left);
                        } else {
                            parent.setRight(left);
                        }
                    } else {
                        toReplace = left;
                    }
                } else {
                    TreeNode right = this.getRight();
                    this.setRight(null);

                    if (!isRoot) {
                        if (isLeft) {
                            parent.setLeft(right);
                        } else {
                            parent.setRight(right);
                        }
                    } else {
                        toReplace = right;
                    }
                }
            }
            this.setParent(null);
        } else {
            if (removeChildren) {
                if (null != getParent()) {
                    if (isLeft) {
                        getParent().setLeft(null);
                    } else {
                        getParent().setRight(null);
                    }
                }
            } else {
                // case 3: Nodes with two children
                if (null != this.getRight()) {
                    toReplace = this.getRight().findMin();
                } else {
                    toReplace = this.getLeft().findMax();
                }

                swapWith(toReplace);
                this.remove(removeChildren);
            }
        }

        return null != toReplace && toReplace.isRoot ? toReplace : this;
    }

    private void swapWith(TreeNode node) {
        // there is nothing to do
        if (null == node || this.equals(node)) {
            return;
        }

        TreeNode thisParent = getParent();
        TreeNode nodeParent = node.getParent();

        boolean isThisLeft = isLeft;
        boolean isNodeLeft = node.isLeft;

        TreeNode thisLeft = this.getLeft();
        TreeNode thisRight = this.getRight();

        TreeNode nodeLeft = node.getLeft();
        TreeNode nodeRight = node.getRight();

        if (null != nodeParent && nodeParent.equals(this)) {
            this.setLeft(nodeLeft);
            this.setRight(nodeRight);

            if (isNodeLeft) {
                node.setLeft(this);
                node.setRight(thisRight);
            } else {
                node.setRight(this);
                node.setLeft(thisLeft);
            }

            if (null != thisParent) {
                if (isThisLeft) {
                    thisParent.setLeft(node);
                } else {
                    thisParent.setRight(node);
                }
            } else {
                node.setParent(null);
            }
            return;
        }

        if (null != thisParent) {
            if (isThisLeft) {
                thisParent.setLeft(null);
            } else {
                thisParent.setRight(null);
            }
        }

        if (null != nodeParent) {
            if (isNodeLeft) {
                nodeParent.setLeft(null);
            } else {
                nodeParent.setRight(null);
            }
        }

        this.setLeft(nodeLeft);
        this.setRight(nodeRight);
        node.setLeft(thisLeft);
        node.setRight(thisRight);

        if (null != thisParent) {
            if (isThisLeft) {
                thisParent.setLeft(node);
            } else {
                thisParent.setRight(node);
            }
        }

        if (null != nodeParent) {
            if (isNodeLeft) {
                nodeParent.setLeft(this);
            } else {
                nodeParent.setRight(this);
            }
        }
    }

    public TreeNode lca(Integer a) {
        TreeNode aNode = find(a, true, 0);

        if (null == aNode) {
            return null;
        }

        return lca(aNode);
    }

    public TreeNode lca(TreeNode node) {
        TreeNode deepestNode = this.getHeight() < node.getHeight() ? this : node;
        TreeNode highestNode = this.equals(deepestNode) ? node : this;
        List<TreeNode> ancestorsB = deepestNode.getAncestors();

        for (TreeNode ancestor : highestNode.getAncestors()) {
            if (null != ancestor && isCommon(ancestor, ancestorsB)) {
                return ancestor;
            }
        }

        return null;
    }

    public TreeNode find(Integer value, boolean ignoreIndex, int index) {
        TreeNode node = this;

        while (null != node) {
            if (ignoreIndex && node.getValue().equals(value)) {
                return node;
            } else if (node.getValue().equals(value) && node.getIndex() == index){
                return node;
            }

            if (value < node.getValue()) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }

        return null;
    }

    private boolean isCommon(TreeNode n, List<TreeNode> ancestors) {
        for (TreeNode ancestor : ancestors) {
            if (n.data == ancestor.data) {
                return true;
            }
        }
        return false;
    }

    public int getBalanceFactor() {
        int hLeft = null == getLeft() ? -1 : getLeft().getHeight();
        int hRight = null == getRight() ? -1 : getRight().getHeight();
        return hLeft - hRight;
    }

}
