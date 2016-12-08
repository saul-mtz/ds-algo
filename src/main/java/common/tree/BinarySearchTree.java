package common.tree;

import java.util.List;

/**
 * @link https://en.wikipedia.org/wiki/Binary_search_tree
 */
public class BinarySearchTree {

    protected TreeNode root;

    public TreeNode add(Integer value) {
        if (null == root) {
            root = new TreeNode(value, null);
            return root;
        } else {
            return insert(root, root, true, value);
        }
    }

    public TreeNode remove(Integer value) {
        return remove(value, false);
    }

    public TreeNode removeAll(Integer value) {
        return remove(value, true);
    }

    /**
     * Remove a node
     *
     * @param value     value of the node to remove
     * @param removeAll if true remove the whole subtree
     * @return
     */
    private TreeNode remove(Integer value, boolean removeAll) {

        TreeNode toRemove = find(value);

        // the node to delete does not exist
        if (null == toRemove) {
            return null;
        }

        // three possible cases according to: https://en.wikipedia.org/wiki/Binary_search_tree#Deletion
        if (null == toRemove.getLeft() && null == toRemove.getRight()) {
            // case 1: node with no children

            // root node
            if (null == toRemove.getParent()) {
                root = null;
            } else {
                boolean isLeft = null != toRemove.getParent().getLeft() && toRemove.getParent().getLeft().equals(toRemove);
                if (isLeft) {
                    toRemove.getParent().setLeft(null);
                } else {
                    toRemove.getParent().setRight(null);
                }
                toRemove.setParent(null);
            }

            return toRemove;
        } else if ((null != toRemove.getLeft() && null == toRemove.getRight()) || (null == toRemove.getLeft()  && null != toRemove.getRight())) {
            boolean isRoot = null == toRemove.getParent();
            boolean isLeft = !isRoot && null != toRemove.getParent().getLeft() && toRemove.getParent().getLeft().equals(toRemove);

            // case 2: Nodes with only one child
            if (null != toRemove.getLeft()) {
                if (!isRoot) {
                    if (isLeft) {
                        toRemove.getParent().setLeft(toRemove.getLeft());
                    } else {
                        toRemove.getParent().setRight(toRemove.getLeft());
                    }
                } else {
                    root = toRemove.getLeft();
                }
                toRemove.getLeft().setParent(toRemove.getParent());
                toRemove.setLeft(null);
            } else {
                if (!isRoot) {
                    if (isLeft) {
                        toRemove.getParent().setLeft(toRemove.getRight());
                    } else {
                        toRemove.getParent().setRight(toRemove.getRight());
                    }
                } else {
                    root = toRemove.getRight();
                }
                toRemove.getRight().setParent(toRemove.getParent());
                toRemove.setRight(null);
            }
            toRemove.setParent(null);
            return toRemove;
        } else {
            // case 3: Nodes with two children
            TreeNode toReplace;
            if (null != toRemove.getRight()) {
                toReplace = findMin(toRemove.getRight());
            } else {
                toReplace = findMax(toRemove.getLeft());
            }

            boolean isLeft = null != toReplace.getParent().getLeft() && toReplace.getParent().getLeft().equals(toReplace);
            swap(toRemove, toReplace);
            for (TreeNode ancestor : toReplace.getAncestors()) {
                ancestor.setHeight(null);
            }

            if (isLeft) {
                toReplace.getParent().setLeft(null);
            } else {
                toReplace.getParent().setRight(null);
            }
            toReplace.setParent(null);
            return toReplace;
        }
    }

    private TreeNode findMin(TreeNode node) {
        TreeNode minTreeNode = node;
        while (null != minTreeNode.getLeft()) {
            minTreeNode = minTreeNode.getLeft();
        }
        return minTreeNode;
    }

    private TreeNode findMax(TreeNode node) {
        TreeNode maxTreeNode = node;
        while (null != maxTreeNode.getRight()) {
            maxTreeNode = maxTreeNode.getRight();
        }
        return maxTreeNode;
    }

    private void swap(TreeNode nodeA, TreeNode nodeB) {
        // there is nothing to do
        if (null == nodeA || null == nodeB || nodeA.equals(nodeB)) {
            return;
        }

        TreeNode lca = lca(nodeA, nodeB);
        // when this is true the two elements are relatives
        // only the value need to be changed
        if (lca.equals(nodeA) || lca.equals(nodeB)) {
            Integer value = nodeA.getValue();
            nodeA.setValue(nodeB.getValue());
            nodeB.setValue(value);
            return;
        }

        // at this point the two nodes are part of a separated subtrees
        Integer value = nodeA.getValue();
        TreeNode parent = nodeA.getParent();
        TreeNode left = nodeA.getLeft();
        TreeNode right = nodeA.getRight();

        nodeA.setValue(nodeB.getValue());
        nodeA.setParent(nodeB.getParent().equals(nodeA) ? nodeB : nodeB.getParent());
        nodeA.setLeft(nodeB.getLeft());
        nodeA.setRight(nodeB.getRight());

        nodeB.setValue(value);
        nodeB.setLeft(left);
        nodeB.setRight(right);
        nodeB.setParent(parent);
    }

    public int getHeight() {
        return null == root ? 0 : root.getHeight();
    }

    public TreeNode lca(Integer a, Integer b) {
        TreeNode aNode = find(a);
        TreeNode bNode = find(b);

        if (null == aNode || null == bNode) {
            return null;
        }

        return lca(aNode, bNode);
    }

    public TreeNode lca(TreeNode nodeA, TreeNode nodeB) {
        List<TreeNode> ancestorsB = nodeB.getAncestors();

        //Collections.reverse(ancestorsA);
        for (TreeNode ancestor : nodeA.getAncestors()) {
            if (null != ancestor && isCommon(ancestor, ancestorsB)) {
                return ancestor;
            }
        }

        return null;
    }

    public String toString() {
        return null == root ? "[]" : root.toString();
    }

    private TreeNode insert(TreeNode node, TreeNode parent, boolean isLeft, Integer value) {
        TreeNode newNode;
        if (null == node) {
            newNode = new TreeNode(value, parent);
            if (isLeft) {
                parent.setLeft(newNode);
            } else {
                parent.setRight(newNode);
            }
            return newNode;
        } else if (value < node.data) {
            node.setHeight(null); // height needs to be re-calculated
            return insert(node.getLeft(), node, true, value);
        } else {
            node.setHeight(null); // height needs to be re-calculated
            return insert(node.getRight(), node, false, value);
        }
    }

    public TreeNode find(Integer value) {
        boolean found = false;
        TreeNode node = root;

        while (null != node && !found) {
            if (node.getValue().equals(value)) {
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

}